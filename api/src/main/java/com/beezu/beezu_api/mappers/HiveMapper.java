package com.beezu.beezu_api.mappers;

import com.beezu.beezu_api.dtos.HiveResponseDTO;
import com.beezu.beezu_api.models.Hive;

public class HiveMapper {
	
	public static HiveResponseDTO toResponse(Hive hive) {
		return new HiveResponseDTO(
				hive.getHealth(),
				hive.getHoneyLevel()
				);
	}
}
