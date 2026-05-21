package com.beezu.beezu_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beezu.beezu_api.dtos.UserDisciplineResponseDTO;
import com.beezu.beezu_api.services.UserDisciplineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name="UserDiscipline",description="Endpoints for managing user disciplines")
@RestController
@RequestMapping("/userdisciplines")
public class UserDisciplineController {
	
	private final UserDisciplineService userDisciplineService;
	
	public UserDisciplineController(UserDisciplineService userDisciplineService) {
		this.userDisciplineService = userDisciplineService;
	}
	
	@Operation(summary="Join discipline by code")
	@PostMapping("/users/{userId}/join/{disciplineCode}")
	public ResponseEntity<Void> joinDiscipline(@PathVariable String disciplineCode, @PathVariable Long userId){
		userDisciplineService.joinDisciplineByCode(disciplineCode, userId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary="Leave discipline by id")
	@DeleteMapping("/users/{userId}/disciplines/{disciplineId}")
	public ResponseEntity<Void> leaveDiscipline(@PathVariable Long disciplineId, @PathVariable Long userId){
		userDisciplineService.leaveDiscipline(disciplineId, userId);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary="List user disciplines")
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<UserDisciplineResponseDTO>> listUserDisciplines(@PathVariable Long userId){
		return ResponseEntity.ok(userDisciplineService.listUserDiscipline(userId));
	}
	
	@Operation(summary="List discipline members ")
	@GetMapping("/disciplines/{disciplineId}/members")
	public ResponseEntity<List<UserDisciplineResponseDTO>> listDisciplineMembers(@PathVariable Long disciplineId){
		return ResponseEntity.ok(userDisciplineService.listDisciplineMembers(disciplineId));
	}
	
	@Operation(summary="Promote user to moderator ")
	@PatchMapping("/{userModeratorId}/{targetUserId}/{disciplineId}/promote")
	public ResponseEntity<Void> promoteToModerator( @PathVariable Long userModeratorId, @PathVariable Long targetUserId,@PathVariable Long disciplineId){
		userDisciplineService.promoteToModerator(userModeratorId, targetUserId, disciplineId);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary="Revoke user moderator status ")
	@PatchMapping("/{userModeratorId}/{targetUserId}/{disciplineId}/revoke")
	public ResponseEntity<Void> revokeModerator( @PathVariable Long userModeratorId, @PathVariable Long targetUserId,@PathVariable Long disciplineId){
		userDisciplineService.revokeModerator(userModeratorId, targetUserId, disciplineId);
		return ResponseEntity.noContent().build();
	}
	
	

}
