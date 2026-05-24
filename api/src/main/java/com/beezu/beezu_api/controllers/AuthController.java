package com.beezu.beezu_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beezu.beezu_api.dtos.LoginResponseDTO;
import com.beezu.beezu_api.dtos.LoginUserRequestDTO;
import com.beezu.beezu_api.dtos.RegisterUserRequestDTO;
import com.beezu.beezu_api.dtos.RegisterUserResponseDTO;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.security.TokenConfig;
import com.beezu.beezu_api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final TokenConfig tokenConfig;
	
	public AuthController(UserService userService, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.tokenConfig = tokenConfig;
	}
	
	@PostMapping("/register")
	public ResponseEntity<RegisterUserResponseDTO> register(@Valid @RequestBody RegisterUserRequestDTO request){
		return ResponseEntity.status(HttpStatus.CREATED).body((userService.registerUser(request)));
	}
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginUserRequestDTO request){
		UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
		Authentication authentication = authenticationManager.authenticate(userAndPass);
		
		User user = (User) authentication.getPrincipal();
		String token = tokenConfig.generateToken(user);
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
}
