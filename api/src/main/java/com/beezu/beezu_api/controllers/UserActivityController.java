package com.beezu.beezu_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beezu.beezu_api.dtos.UserActivityResponseDTO;
import com.beezu.beezu_api.models.enums.ActivityStatus;
import com.beezu.beezu_api.services.UserActivityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserActivity", description = "Endpoints for managing user activities")
@RestController
@RequestMapping("/users/activities")
public class UserActivityController {

	private final UserActivityService userActivityService;

	public UserActivityController(UserActivityService userActivityService) {
		this.userActivityService = userActivityService;
	}


	@Operation(summary = "Update a activity status")
	@PatchMapping("/my/activities/{activityId}/status/{status}")
	public ResponseEntity<UserActivityResponseDTO> updateActivityStatus(
			@PathVariable Long activityId, @PathVariable ActivityStatus status) {
		UserActivityResponseDTO updatedActivity = userActivityService.updateActivityStatus(activityId, status);
		return ResponseEntity.ok(updatedActivity);
	}

	@Operation(summary = "List all user activities")
	@GetMapping("/my/activities")
	public ResponseEntity<List<UserActivityResponseDTO>> listUserActivities() {
		return ResponseEntity.ok(userActivityService.listUserActivities());
	}

	@Operation(summary = "List all completed user activities")
	@GetMapping("/my/activities/completed")
	public ResponseEntity<List<UserActivityResponseDTO>> listCompletedActivities() {
		return ResponseEntity.ok(userActivityService.listCompletedActivities());
	}

	@Operation(summary = "List all pending user activities")
	@GetMapping("/my/activities/pending")
	public ResponseEntity<List<UserActivityResponseDTO>> listPendingActivities(){
		return ResponseEntity.ok(userActivityService.listPendingActivities());
	}

	@Operation(summary = "List all overdue user activities")
	@GetMapping("/my/activities/overdue")
	public ResponseEntity<List<UserActivityResponseDTO>> listOverdueActivities() {
		return ResponseEntity.ok(userActivityService.listOverdueActivities());
	}

}
