package com.beezu.beezu_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beezu.beezu_api.dtos.UserResponseDTO;
import com.beezu.beezu_api.dtos.UserUpdateDTO;
import com.beezu.beezu_api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="User",description="Endpoints for managing users")
@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@Operation(summary="Find user by ID")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
		UserResponseDTO response = userService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(summary="Update user data")
	@PatchMapping("/me")
	public ResponseEntity<UserResponseDTO> update( @RequestBody @Valid UserUpdateDTO request){
		UserResponseDTO response =userService.updateUser(request);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(summary="Delete a user")
	@DeleteMapping("/me")
	public ResponseEntity<Void> delete(){
		 userService.deleteUser();
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	@Operation(summary="Find my user")
	@GetMapping("/me")
	public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
	    return ResponseEntity.ok(
	            userService.getAuthenticatedUser()
	    );
	}

	
}
