package com.beezu.beezu_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beezu.beezu_api.dtos.UserDisciplineResponseDTO;
import com.beezu.beezu_api.exceptions.DisciplineNotFoundException;
import com.beezu.beezu_api.exceptions.EnrollmentAlreadyExistsException;
import com.beezu.beezu_api.exceptions.EnrollmentDoesNotExistException;
import com.beezu.beezu_api.exceptions.UserIsNotAModeratorException;
import com.beezu.beezu_api.exceptions.UserNotFoundException;
import com.beezu.beezu_api.mappers.UserDisciplineMapper;
import com.beezu.beezu_api.models.Discipline;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.models.UserDiscipline;
import com.beezu.beezu_api.repositories.DisciplineRepository;
import com.beezu.beezu_api.repositories.UserDisciplineRepository;
import com.beezu.beezu_api.repositories.UserRepository;

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

	public void joinDisciplineByCode(String disciplineCode, Long userId) {

		if (disciplineCode == null || userId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}

		Discipline discipline = findDisciplineByCode(disciplineCode);
		User user = findUserById(userId);

		boolean alreadyEnrolled = user.getDisciplines().stream()
				.anyMatch(d -> d.getDiscipline().getId().equals(discipline.getId()));

		if (alreadyEnrolled) {
			throw new EnrollmentAlreadyExistsException("User already enrolled in this discipline");
		}

		UserDiscipline enrollment = new UserDiscipline(user, discipline);
		discipline.addEnrollment(enrollment);
		user.enrollDiscipline(enrollment);
		userDisciplineRepository.save(enrollment);
	}

	public void leaveDiscipline(Long disciplineId, Long userId) {
		if (disciplineId == null || userId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}

		Discipline discipline = findDisciplineById(disciplineId);
		User user = findUserById(userId);

		UserDiscipline enrollment = userDisciplineRepository.findByUser_IdAndDiscipline_Id(userId, disciplineId)
				.orElseThrow(() -> new EnrollmentDoesNotExistException("The user is not enrolled"));

		discipline.removeEnrollment(enrollment);
		user.leaveDiscipline(enrollment);
		userDisciplineRepository.delete(enrollment);
	}

	public List<UserDisciplineResponseDTO> listUserDiscipline(Long userId) {
		
		User user = findUserById(userId);
		return user.getDisciplines().stream().map(UserDisciplineMapper::toResponse).toList();
	}
	
	public List<UserDisciplineResponseDTO> listDisciplineMembers(Long disciplineId) {
		
		Discipline discipline = findDisciplineById(disciplineId);
		return discipline.getEnrollments().stream().map(UserDisciplineMapper::toResponse).toList();
	}
	

	public void promoteToModerator(Long userModeratorId, Long userToGetPromotedId, Long disciplineId) {

		if (userModeratorId == null || userToGetPromotedId == null || disciplineId == null) {

			throw new IllegalArgumentException("The request cannot be null");
		}

		findUserById(userModeratorId);
		findUserById(userToGetPromotedId);

		Discipline discipline = findDisciplineById(disciplineId);

		List<UserDiscipline> disciplineUsers = discipline.getEnrollments();

		boolean isModerator = disciplineUsers.stream()
				.anyMatch(u -> u.getUser().getId().equals(userModeratorId) && u.isModerator());

		if (!isModerator) {
			throw new UserIsNotAModeratorException("The user is not a moderator");
		}

		UserDiscipline enrollment = disciplineUsers.stream()
				.filter(d -> d.getUser().getId().equals(userToGetPromotedId)).findFirst()
				.orElseThrow(() -> new EnrollmentDoesNotExistException(
						"The user to be promoted is not enrolled in this discipline"));

		enrollment.promoteToModerator();
	}
	
	
	public void revokeModerator(Long userModeratorId, Long targetUserId, Long disciplineId) {

		if (userModeratorId == null || targetUserId == null || disciplineId == null) {

			throw new IllegalArgumentException("The request cannot be null");
		}

		findUserById(userModeratorId);
		findUserById(targetUserId);

		Discipline discipline = findDisciplineById(disciplineId);

		List<UserDiscipline> disciplineUsers = discipline.getEnrollments();

		boolean isModerator = disciplineUsers.stream()
				.anyMatch(u -> u.getUser().getId().equals(userModeratorId) && u.isModerator());

		if (!isModerator) {
			throw new UserIsNotAModeratorException("The user is not a moderator");
		}
		
		if(userModeratorId.equals(targetUserId)) {
			throw new IllegalArgumentException("A moderator cannot revoke their own role");
		}

		UserDiscipline enrollment = disciplineUsers.stream()
				.filter(d -> d.getUser().getId().equals(targetUserId)).findFirst()
				.orElseThrow(() -> new EnrollmentDoesNotExistException(
						"The user is not enrolled in this discipline"));

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
}
