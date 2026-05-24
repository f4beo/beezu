package com.beezu.beezu_api.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beezu.beezu_api.dtos.RegisterUserRequestDTO;
import com.beezu.beezu_api.dtos.RegisterUserResponseDTO;
import com.beezu.beezu_api.dtos.UserResponseDTO;
import com.beezu.beezu_api.dtos.UserUpdateDTO;
import com.beezu.beezu_api.exceptions.EmailAlreadyRegisteredException;
import com.beezu.beezu_api.exceptions.UserNotFoundException;
import com.beezu.beezu_api.mappers.RegisterUserMapper;
import com.beezu.beezu_api.mappers.UserMapper;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.repositories.UserRepository;
import com.beezu.beezu_api.security.AuthenticatedUserUtil;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO dto) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		if(userRepository.existsByEmail(dto.email())) {
			throw new EmailAlreadyRegisteredException("Email already registered");
		}
		User user = RegisterUserMapper.toEntity(dto);
		user.setPassword(passwordEncoder.encode(dto.password()));
		user = userRepository.save(user);
		
		return RegisterUserMapper.toResponse(user);
	}
	
	public UserResponseDTO findById(Long id) {
		User user = findEntityById(id);			
		return UserMapper.toResponse(user);		
	}
	
	public UserResponseDTO findByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));	
		return UserMapper.toResponse(user);		
	}
	
	public UserResponseDTO updateUser( UserUpdateDTO dto) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();

		
		userRepository.findByEmail(dto.email())
        .ifPresent(existingUser -> {
            if (!existingUser.getId().equals(authenticatedUserId)) {
                throw new EmailAlreadyRegisteredException("Email already registered");
            }
        });

		User user = findEntityById(authenticatedUserId);	
		updateData(user, dto);
		userRepository.save(user);
		return UserMapper.toResponse(user);
	}
	
	public void deleteUser() {
		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();
		User user = findEntityById(authenticatedUserId);	
		userRepository.delete(user);
	}
	
	public UserResponseDTO getAuthenticatedUser() {

	    Long authenticatedUserId =
	            AuthenticatedUserUtil.getAuthenticatedUserId();

	    User user = findEntityById(authenticatedUserId);

	    return UserMapper.toResponse(user);
	}


    private void updateData(User entity, UserUpdateDTO dto) {
    	if(dto.name() != null && !dto.name().isBlank()) {
    		entity.setName(dto.name());	
    	}
    	if(dto.email() != null && !dto.email().isBlank()) {
    		entity.setEmail(dto.email());
    	}   	
  
	}
    
    private User findEntityById(Long id) {
    	User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    	return user;
    }
}
