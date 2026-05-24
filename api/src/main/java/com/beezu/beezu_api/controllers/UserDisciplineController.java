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
@RequestMapping("/users/disciplines")
public class UserDisciplineController {
	
	private final UserDisciplineService userDisciplineService;
	
	public UserDisciplineController(UserDisciplineService userDisciplineService) {
		this.userDisciplineService = userDisciplineService;
	}
	
	@Operation(summary="Join discipline by code")
	@PostMapping("/join/{disciplineCode}")
	public ResponseEntity<Void> joinDiscipline(@PathVariable String disciplineCode){
		userDisciplineService.joinDisciplineByCode(disciplineCode);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary="Leave discipline by id")
	@DeleteMapping("/disciplines/{disciplineId}")
	public ResponseEntity<Void> leaveDiscipline(@PathVariable Long disciplineId){
		userDisciplineService.leaveDiscipline(disciplineId);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary="List user disciplines")
	@GetMapping("/my/disciplines")
	public ResponseEntity<List<UserDisciplineResponseDTO>> listUserDisciplines(){
		return ResponseEntity.ok(userDisciplineService.listUserDiscipline());
	}
	
	@Operation(summary="List discipline members ")
	@GetMapping("/disciplines/{disciplineId}/members")
	public ResponseEntity<List<UserDisciplineResponseDTO>> listDisciplineMembers(@PathVariable Long disciplineId){
		return ResponseEntity.ok(userDisciplineService.listDisciplineMembers(disciplineId));
	}
	
	@Operation(summary="Promote user to moderator ")
	@PatchMapping("/{disciplineId}/promote/target/{targetUserId}")
	public ResponseEntity<Void> promoteToModerator(@PathVariable Long targetUserId,@PathVariable Long disciplineId){
		userDisciplineService.promoteToModerator(targetUserId, disciplineId);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary="Revoke user moderator status ")
	@PatchMapping("/{disciplineId}/revoke/target/{targetUserId}")
	public ResponseEntity<Void> revokeModerator( @PathVariable Long targetUserId,@PathVariable Long disciplineId){
		userDisciplineService.revokeModerator( targetUserId, disciplineId);
		return ResponseEntity.ok().build();
	}
	
	

}
