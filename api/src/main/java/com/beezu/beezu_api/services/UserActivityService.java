package com.beezu.beezu_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beezu.beezu_api.dtos.UserActivityResponseDTO;
import com.beezu.beezu_api.exceptions.ActivityIsAlreadyCompletedException;
import com.beezu.beezu_api.exceptions.ActivityNotFoundException;
import com.beezu.beezu_api.exceptions.UserActivityNotFoundException;
import com.beezu.beezu_api.exceptions.UserNotEnrolledDisciplineException;
import com.beezu.beezu_api.exceptions.UserNotFoundException;
import com.beezu.beezu_api.mappers.UserActivityMapper;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.models.UserActivity;
import com.beezu.beezu_api.models.enums.ActivityStatus;
import com.beezu.beezu_api.repositories.UserActivityRepository;
import com.beezu.beezu_api.repositories.UserRepository;

@Service
public class UserActivityService {
	
	private final UserRepository userRepository;
	private final UserActivityRepository userActivityRepository;
	
	public UserActivityService( UserRepository userRepository, UserActivityRepository userActivityRepository) {
		this.userRepository = userRepository;
		this.userActivityRepository = userActivityRepository;
	}
	
	public void completeActivity(Long activityId, Long userId) {
		if(activityId == null || userId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
	    User user = findUserById(userId);
		UserActivity targetActivity = findByUserIdAndActivityId(userId, activityId);
		
		boolean enrolled = user.getDisciplines().stream()
				.anyMatch(d -> d.getDiscipline().getId()
				.equals(targetActivity.getActivity().getDiscipline().getId()));
		if(!enrolled) {
			throw new UserNotEnrolledDisciplineException("User is not enrolled in this discipline");
		}
		
		if(targetActivity.getActivityStatus() != ActivityStatus.PENDING) {
			throw new ActivityIsAlreadyCompletedException("The activity is already completed");
		}
		targetActivity.markAsCompleted();
		
		userActivityRepository.save(targetActivity);
	}
	
	public UserActivityResponseDTO updateActivityStatus(Long activityId,  Long userId, ActivityStatus status) {

	    if (userId == null || activityId == null || status == null) {
	        throw new IllegalArgumentException("The request cannot be null");
	    }
	    
	    User user = findUserById(userId);	    
	    UserActivity targetActivity = findByUserIdAndActivityId(userId, activityId);
	    
	    boolean enrolled = user.getDisciplines().stream()
	            .anyMatch(d -> d.getDiscipline().getId().equals(targetActivity.getActivity().getDiscipline().getId()));

	    if (!enrolled) {
	        throw new UserNotEnrolledDisciplineException("User is not enrolled in this discipline");
	    }

	    if (status == ActivityStatus.COMPLETED) {

	        if (targetActivity.getActivityStatus() == ActivityStatus.COMPLETED) {
	            throw new ActivityIsAlreadyCompletedException("The activity is already completed");
	        }
	        targetActivity.markAsCompleted();

	    } else if (status == ActivityStatus.PENDING) {
	        targetActivity.setActivityStatus(ActivityStatus.PENDING);
	    } else {
	        throw new IllegalArgumentException("Unknown status type");
	    }

	    userActivityRepository.save(targetActivity);

	    return UserActivityMapper.toResponse(targetActivity);
	}
	
	public List<UserActivityResponseDTO> listUserActivities(Long userId){
		if(userId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		
		User user = findUserById(userId);	
		return user.getActivities().stream().map(UserActivityMapper::toResponse).toList();
	}
	public List<UserActivityResponseDTO> listCompletedActivities(Long userId){
		if(userId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		
		User user = findUserById(userId);	
		return user.getActivities().stream().filter(a -> a.getActivityStatus() == ActivityStatus.COMPLETED)
				.map(UserActivityMapper::toResponse).toList();
	}
	public List<UserActivityResponseDTO> listPendingActivities(Long userId){
		if(userId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		
		User user = findUserById(userId);	
		return user.getActivities().stream().filter(a -> a.getActivityStatus() == ActivityStatus.PENDING)
				.map(UserActivityMapper::toResponse).toList();
	}
	public List<UserActivityResponseDTO> listOverdueActivities(Long userId){
		if(userId == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		User user = findUserById(userId);	
		return user.getActivities().stream().filter(a -> a.getActivity().isOverdue())
				.map(UserActivityMapper::toResponse).toList();
	}
	
	
    private UserActivity findByUserIdAndActivityId(Long userId, Long activityId) {
    	UserActivity activity = userActivityRepository.findByUser_IdAndActivity_Id(userId, activityId).orElseThrow(() -> new UserActivityNotFoundException("Activity not found for this user"));
    	return activity;
    }
	
    private User findUserById(Long id) {
    	User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    	return user;
    }


}
