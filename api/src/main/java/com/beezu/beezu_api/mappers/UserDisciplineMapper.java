package com.beezu.beezu_api.mappers;

import com.beezu.beezu_api.dtos.UserDisciplineResponseDTO;
import com.beezu.beezu_api.models.UserDiscipline;

public class UserDisciplineMapper {
	
	public static UserDisciplineResponseDTO toResponse(UserDiscipline userDiscipline) {
		return new UserDisciplineResponseDTO(
				userDiscipline.getUser().getId(),
				userDiscipline.getUser().getName(),
				userDiscipline.getDiscipline().getId(),
				userDiscipline.getDiscipline().getName(),
				userDiscipline.isModerator()
				);
				
	}

}
