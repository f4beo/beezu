package com.beezu.beezu_api.mappers;

import com.beezu.beezu_api.dtos.RegisterUserRequestDTO;
import com.beezu.beezu_api.dtos.RegisterUserResponseDTO;
import com.beezu.beezu_api.models.User;

public class RegisterUserMapper {
	
	public static User toEntity(RegisterUserRequestDTO dto) {
		return new User(
				dto.name(),
				dto.email(),
				dto.password()
				);
	
	}
	
	public static RegisterUserResponseDTO toResponse(User user) {
		return new RegisterUserResponseDTO(
				user.getName(),
				user.getEmail()
				);
	}
}
