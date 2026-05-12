package com.beezu.beezu_api.mappers;

import com.beezu.beezu_api.dtos.UserRequestDTO;
import com.beezu.beezu_api.dtos.UserResponseDTO;
import com.beezu.beezu_api.models.User;

public class UserMapper {
	
	public static User toEntity(UserRequestDTO dto) {
		return new User(
				dto.name(),
				dto.email(),
				dto.password()
				);
	
	}
	
	public static UserResponseDTO toResponse(User user) {
		return new UserResponseDTO(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getHoney()
				);
	}
}
