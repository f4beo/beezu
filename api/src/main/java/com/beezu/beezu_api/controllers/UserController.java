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

import com.beezu.beezu_api.dtos.UserRequestDTO;
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
	
	@Operation(summary="Create a new user")
	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO request){
		UserResponseDTO response = userService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	@Operation(summary="Find user by ID")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
		UserResponseDTO response = userService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(summary="Update user data")
	@PatchMapping("/{id}")
	public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO request){
		UserResponseDTO response =userService.updateUser(id, request);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(summary="Delete a user")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		 userService.deleteUser(id);
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
