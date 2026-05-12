package com.beezu.beezu_api.mappers;

import com.beezu.beezu_api.dtos.ActivityRequestDTO;
import com.beezu.beezu_api.dtos.ActivityResponseDTO;
import com.beezu.beezu_api.models.Activity;

public class ActivityMapper {

	public static Activity toEntity(ActivityRequestDTO dto) {
		return new Activity(
				dto.description(),
				dto.type(),
				dto.deadline(),
				dto.title()
				);
	
	}
	
	public static ActivityResponseDTO toResponse(Activity activity) {
		return new ActivityResponseDTO(
				activity.getId(),
				activity.getTitle(),
				activity.getDescription(),
				activity.getActivityType(),
				activity.getCreatedAt(),
				activity.getDeadline()
				);
	}
}
