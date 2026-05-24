package com.beezu.beezu_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beezu.beezu_api.dtos.ActivityResponseDTO;
import com.beezu.beezu_api.dtos.DisciplineRequestDTO;
import com.beezu.beezu_api.dtos.DisciplineResponseDTO;
import com.beezu.beezu_api.dtos.DisciplineUpdateDTO;
import com.beezu.beezu_api.exceptions.DisciplineNotFoundException;
import com.beezu.beezu_api.exceptions.ForbiddenActionException;
import com.beezu.beezu_api.exceptions.UserNotFoundException;
import com.beezu.beezu_api.mappers.ActivityMapper;
import com.beezu.beezu_api.mappers.DisciplineMapper;
import com.beezu.beezu_api.models.Activity;
import com.beezu.beezu_api.models.Discipline;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.models.UserDiscipline;
import com.beezu.beezu_api.repositories.DisciplineRepository;
import com.beezu.beezu_api.repositories.UserRepository;
import com.beezu.beezu_api.security.AuthenticatedUserUtil;

@Service
public class DisciplineService {

	private final DisciplineRepository disciplineRepository;
	private final UserRepository userRepository;

	public DisciplineService(DisciplineRepository disciplineRepository, UserRepository userRepository) {
		this.disciplineRepository = disciplineRepository;
		this.userRepository = userRepository;
	}

	public DisciplineResponseDTO createDiscipline(DisciplineRequestDTO dto) {
		if (dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();

		User creator = userRepository.findById(authenticatedUserId)
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		Discipline discipline = DisciplineMapper.toEntity(dto);

		UserDiscipline enrollment = new UserDiscipline(creator, discipline);
		enrollment.promoteToModerator();

		discipline.addEnrollment(enrollment);

		disciplineRepository.save(discipline);

		return DisciplineMapper.toResponse(discipline);
	}

	public DisciplineResponseDTO findById(Long id) {
		Discipline discipline = findEntityById(id);
		validateUserAccess(discipline);
		return DisciplineMapper.toResponse(discipline);

	}

	public DisciplineResponseDTO findByCode(String code) {
		return disciplineRepository.findByDisciplineCode(code).map(DisciplineMapper::toResponse)
				.orElseThrow(() -> new DisciplineNotFoundException("Discipline not found"));
	}

	public List<DisciplineResponseDTO> listAll() {
		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();

		List<Discipline> disciplines = disciplineRepository.findAll().stream()
				.filter(discipline -> discipline.getEnrollments().stream()
						.anyMatch(enrollment -> enrollment.getUser().getId().equals(authenticatedUserId)))
				.toList();
		return disciplines.stream().map(DisciplineMapper::toResponse).toList();
	}

	public List<ActivityResponseDTO> listAllDisciplineActivities(Long disciplineId) {
		Discipline discipline = findEntityById(disciplineId);
		validateUserAccess(discipline);
		List<Activity> disciplineActivities = discipline.getActivities();

		return disciplineActivities.stream().map(ActivityMapper::toResponse).toList();
	}

	public DisciplineResponseDTO updateDiscipline(Long id, DisciplineUpdateDTO dto) {
		if (dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		Discipline discipline = findEntityById(id);
		validateModeratorAccess(discipline);
		updateData(discipline, dto);
		disciplineRepository.save(discipline);

		return DisciplineMapper.toResponse(discipline);
	}

	public void deleteDiscipline(Long id) {
		Discipline discipline = findEntityById(id);
		validateModeratorAccess(discipline);
		disciplineRepository.delete(discipline);
	}

	private Discipline findEntityById(Long id) {
		Discipline discipline = disciplineRepository.findById(id)
				.orElseThrow(() -> new DisciplineNotFoundException("Discipline not found"));
		return discipline;
	}

	private void updateData(Discipline entity, DisciplineUpdateDTO dto) {

	    if(dto.name() != null && !dto.name().isBlank()) {
	        entity.setName(dto.name());
	    }

	    if(dto.description() != null && !dto.description().isBlank()) {
	        entity.setDescription(dto.description());
	    }

	    if(dto.professor() != null && !dto.professor().isBlank()) {
	        entity.setProfessor(dto.professor());
	    }
	}

	private void validateModeratorAccess(Discipline discipline) {

		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();

		boolean isModerator = discipline.getEnrollments().stream().anyMatch(
				enrollment -> enrollment.getUser().getId().equals(authenticatedUserId) && enrollment.isModerator());

		if (!isModerator) {
			throw new ForbiddenActionException("Only moderators can perform this action");
		}
	}

	private void validateUserAccess(Discipline discipline) {

		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();

		boolean isEnrolled = discipline.getEnrollments().stream()
				.anyMatch(enrollment -> enrollment.getUser().getId().equals(authenticatedUserId));

		if (!isEnrolled) {
			throw new ForbiddenActionException("You do not have access to this discipline");
		}
	}

}
