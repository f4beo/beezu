package com.beezu.beezu_api.mappers;

import com.beezu.beezu_api.dtos.DisciplineRequestDTO;
import com.beezu.beezu_api.dtos.DisciplineResponseDTO;
import com.beezu.beezu_api.models.Discipline;

public class DisciplineMapper {
	
	public static Discipline toEntity(DisciplineRequestDTO dto) {
		return new Discipline(
				dto.name(),
				dto.description(),
				dto.professor()
				);
	
	}
	
	public static DisciplineResponseDTO toResponse(Discipline discipline) {
		return new DisciplineResponseDTO(
				discipline.getId(),
				discipline.getName(),
				discipline.getDescription(),
				discipline.getProfessor(),
				discipline.getDisciplineCode(),
				HiveMapper.toResponse(discipline.getHive())
				);
	}
}
