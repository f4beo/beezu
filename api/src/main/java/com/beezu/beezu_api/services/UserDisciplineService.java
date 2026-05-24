package com.beezu.beezu_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beezu.beezu_api.dtos.UserDisciplineResponseDTO;
import com.beezu.beezu_api.exceptions.DisciplineNotFoundException;
import com.beezu.beezu_api.exceptions.EnrollmentAlreadyExistsException;
import com.beezu.beezu_api.exceptions.EnrollmentDoesNotExistException;
import com.beezu.beezu_api.exceptions.ForbiddenActionException;
import com.beezu.beezu_api.exceptions.UserNotFoundException;
import com.beezu.beezu_api.mappers.UserDisciplineMapper;
import com.beezu.beezu_api.models.Discipline;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.models.UserDiscipline;
import com.beezu.beezu_api.repositories.DisciplineRepository;
import com.beezu.beezu_api.repositories.UserDisciplineRepository;
import com.beezu.beezu_api.repositories.UserRepository;
import com.beezu.beezu_api.security.AuthenticatedUserUtil;

@Service
public class UserDisciplineService {

	private final UserDisciplineRepository userDisciplineRepository;
	private final DisciplineRepository disciplineRepository;
	private final UserRepository userRepository;

	public UserDisciplineService(UserDisciplineRepository userDisciplineRepository, UserRepository userRepository,
			DisciplineRepository disciplineRepository) {
		this.userDisciplineRepository = userDisciplineRepository;
		this.disciplineRepository = disciplineRepository;
		this.userRepository = userRepository;

	}

	public void joinDisciplineByCode(String disciplineCode) {

		if (disciplineCode == null || disciplineCode.isBlank()) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();
		Discipline discipline = findDisciplineByCode(disciplineCode);
		User user = findUserById(authenticatedUserId);

		boolean alreadyEnrolled = user.getDisciplines().stream()
				.anyMatch(d -> d.getDiscipline().getId().equals(discipline.getId()));

		if (alreadyEnrolled) {
			throw new EnrollmentAlreadyExistsException("User already enrolled in this discipline");
		}

		UserDiscipline enrollment = new UserDiscipline(user, discipline);
		userDisciplineRepository.save(enrollment);
	}

	public void leaveDiscipline(Long disciplineId) {
		if (disciplineId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();

		Discipline discipline = findDisciplineById(disciplineId);
		User user = findUserById(authenticatedUserId);

		UserDiscipline enrollment = userDisciplineRepository
				.findByUser_IdAndDiscipline_Id(authenticatedUserId, disciplineId)
				.orElseThrow(() -> new EnrollmentDoesNotExistException("The user is not enrolled"));


		userDisciplineRepository.delete(enrollment);
	}

	public List<UserDisciplineResponseDTO> listUserDiscipline() {
		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();
		User user = findUserById(authenticatedUserId);

		return user.getDisciplines().stream().map(UserDisciplineMapper::toResponse).toList();
	}

	public List<UserDisciplineResponseDTO> listDisciplineMembers(Long disciplineId) {

		Discipline discipline = findDisciplineById(disciplineId);
		validateUserAccess(discipline);
		return discipline.getEnrollments().stream().map(UserDisciplineMapper::toResponse).toList();
	}

	public void promoteToModerator(Long targetUserId, Long disciplineId) {

		if (targetUserId == null || disciplineId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		Discipline discipline = findDisciplineById(disciplineId);
		validateModeratorAccess(discipline);

		UserDiscipline enrollment = discipline.getEnrollments().stream()
				.filter(d -> d.getUser().getId().equals(targetUserId)).findFirst()
				.orElseThrow(() -> new EnrollmentDoesNotExistException("The user is not enrolled in this discipline"));

		enrollment.promoteToModerator();
	}

	public void revokeModerator(Long targetUserId, Long disciplineId) {

		if (targetUserId == null || disciplineId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}

		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();

		if (authenticatedUserId.equals(targetUserId)) {
			throw new IllegalArgumentException("A moderator cannot revoke their own role");
		}

		Discipline discipline = findDisciplineById(disciplineId);

		validateModeratorAccess(discipline);

		UserDiscipline enrollment = discipline.getEnrollments().stream()
				.filter(d -> d.getUser().getId().equals(targetUserId)).findFirst()
				.orElseThrow(() -> new EnrollmentDoesNotExistException("The user is not enrolled in this discipline"));

		enrollment.revokeModerator();
	}

	private Discipline findDisciplineByCode(String code) {
		Discipline discipline = disciplineRepository.findByDisciplineCode(code)
				.orElseThrow(() -> new DisciplineNotFoundException("Discipline not found"));
		return discipline;
	}

	private User findUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
		return user;
	}

	private Discipline findDisciplineById(Long id) {
		Discipline discipline = disciplineRepository.findById(id)
				.orElseThrow(() -> new DisciplineNotFoundException("Discipline not found"));
		return discipline;
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
