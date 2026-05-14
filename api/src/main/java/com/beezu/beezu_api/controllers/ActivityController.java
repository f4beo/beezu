package com.beezu.beezu_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beezu.beezu_api.dtos.ActivityRequestDTO;
import com.beezu.beezu_api.dtos.ActivityResponseDTO;
import com.beezu.beezu_api.dtos.ActivityUpdateDTO;
import com.beezu.beezu_api.services.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="Activity",description="Endpoints for managing activities")
@RestController
@RequestMapping("/activities")
public class ActivityController {
	
	private final ActivityService activityService;
	
	public ActivityController(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	@Operation(summary="Create a new activity")
	@PostMapping("/{creatorId}/{disciplineId}")
	public ResponseEntity<ActivityResponseDTO> create(@RequestBody @Valid ActivityRequestDTO request, @PathVariable Long creatorId, @PathVariable Long disciplineId){
		ActivityResponseDTO response = activityService.createActivity(request, creatorId, disciplineId);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Operation(summary="Find activity by ID")
	@GetMapping("/{id}")
	public ResponseEntity<ActivityResponseDTO> findById(@PathVariable Long id){
		ActivityResponseDTO response = activityService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(summary="Update activity data")
	@PatchMapping("/{id}")
	public ResponseEntity<ActivityResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ActivityUpdateDTO request){
		ActivityResponseDTO response = activityService.updateActivity(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(summary="Delete a activity")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		 activityService.deleteActivity(id);
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	

}
