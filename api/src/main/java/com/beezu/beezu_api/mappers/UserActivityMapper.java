package com.beezu.beezu_api.mappers;

import com.beezu.beezu_api.dtos.UserActivityResponseDTO;
import com.beezu.beezu_api.models.UserActivity;

public class UserActivityMapper {
	
	public static UserActivityResponseDTO toResponse(UserActivity userActivity) {

		return new UserActivityResponseDTO(
			userActivity.getUser().getId(),
			userActivity.getActivity().getId(),
			userActivity.getActivityStatus(),
			userActivity.getCompletedAt(),
			userActivity.getEarnedHoney()
				);
	}
}
