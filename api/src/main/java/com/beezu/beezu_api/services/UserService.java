package com.beezu.beezu_api.services;

import org.springframework.stereotype.Service;

import com.beezu.beezu_api.dtos.UserRequestDTO;
import com.beezu.beezu_api.dtos.UserResponseDTO;
import com.beezu.beezu_api.exceptions.EmailAlreadyRegisteredException;
import com.beezu.beezu_api.exceptions.UserNotFoundException;
import com.beezu.beezu_api.mappers.UserMapper;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserResponseDTO createUser(UserRequestDTO dto) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		if(userRepository.existsByEmail(dto.email())) {
			throw new EmailAlreadyRegisteredException("Email already registered");
		}
		User user = UserMapper.toEntity(dto);
		user = userRepository.save(user);
		
		return UserMapper.toResponse(user);
	}
	
	public UserResponseDTO findById(Long id) {
		User user = findEntityById(id);			
		return UserMapper.toResponse(user);		
	}
	
	public UserResponseDTO findByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));	
		return UserMapper.toResponse(user);		
	}
	
	public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		
		userRepository.findByEmail(dto.email())
        .ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new EmailAlreadyRegisteredException("Email already registered");
            }
        });

		User user = findEntityById(id);	
		updateData(user, dto);
		userRepository.save(user);
		return UserMapper.toResponse(user);
	}
	
	public void deleteUser(Long id) {
		User user = findEntityById(id);	
		userRepository.delete(user);
	}

    private void updateData(User entity, UserRequestDTO user) {
    	if (user.email() != null && !user.email().isBlank()) {
		entity.setName(user.name());
    	}
    	if (user.email() != null && !user.email().isBlank()) {
		entity.setEmail(user.email());
    	}
	}
    
    private User findEntityById(Long id) {
    	User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    	return user;
    }
}
