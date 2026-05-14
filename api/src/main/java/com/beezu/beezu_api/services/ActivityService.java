package com.beezu.beezu_api.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.beezu.beezu_api.dtos.ActivityRequestDTO;
import com.beezu.beezu_api.dtos.ActivityResponseDTO;
import com.beezu.beezu_api.exceptions.ActivityNotFoundException;
import com.beezu.beezu_api.exceptions.UserNotFoundException;
import com.beezu.beezu_api.exceptions.DisciplineNotFoundException;
import com.beezu.beezu_api.exceptions.InvalidActivityDeadlineException;
import com.beezu.beezu_api.mappers.ActivityMapper;
import com.beezu.beezu_api.models.Activity;
import com.beezu.beezu_api.models.Discipline;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.repositories.ActivityRepository;
import com.beezu.beezu_api.repositories.DisciplineRepository;
import com.beezu.beezu_api.repositories.UserRepository;

@Service
public class ActivityService {
	
	private final ActivityRepository activityRepository;
	private final UserRepository userRepository;
	private final DisciplineRepository disciplineRepository;
	
	public ActivityService(ActivityRepository activityRepository, UserRepository userRepository, DisciplineRepository disciplineRepository) {
		this.activityRepository = activityRepository;
		this.userRepository = userRepository;
		this.disciplineRepository = disciplineRepository;
	}
	
	public ActivityResponseDTO createActivity(ActivityRequestDTO dto, Long creatorId, Long disciplineId) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		if(dto.deadline() != null && dto.deadline().isBefore(LocalDateTime.now())) {
			throw new InvalidActivityDeadlineException("The activity deadline cannot be in the past");
		}
		
		User creator = userRepository.findById(creatorId).orElseThrow(() -> new UserNotFoundException("User not found"));
		Discipline discipline = disciplineRepository.findById(disciplineId).orElseThrow(()-> new DisciplineNotFoundException("Discipline not found"));
		
		Activity activity = ActivityMapper.toEntity(dto);
		
		activity.setCreatedBy(creator);
		
		discipline.addActivity(activity);
		
		activityRepository.save(activity);
		
		return ActivityMapper.toResponse(activity);
		
	}
	
	public ActivityResponseDTO findById(Long id) {
		Activity activity = findEntityById(id);
		return ActivityMapper.toResponse(activity);
	}
	
	public ActivityResponseDTO updateActivity(Long id, ActivityRequestDTO dto) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		if(dto.deadline() != null && dto.deadline().isBefore(LocalDateTime.now())) {
			throw new InvalidActivityDeadlineException("The activity deadline cannot be in the past");
		}
		Activity activity = findEntityById(id);
		updateData(activity, dto);
		activityRepository.save(activity);
		
		return ActivityMapper.toResponse(activity);
	}
	
	public void deleteActivity(Long id) {
		Activity activity = findEntityById(id);
		activity.getDiscipline().removeActivity(activity);

		activityRepository.delete(activity);
	}
    private Activity findEntityById(Long id) {
    	Activity activity = activityRepository.findById(id).orElseThrow(() -> new ActivityNotFoundException("Activity not found"));
    	return activity;
    }
    
    private void updateData(Activity entity, ActivityRequestDTO activity) {
		entity.setTitle(activity.title());
		entity.setDescription(activity.description());	
		entity.setActivityType(activity.type());
		entity.setDeadline(activity.deadline());
	}

}
