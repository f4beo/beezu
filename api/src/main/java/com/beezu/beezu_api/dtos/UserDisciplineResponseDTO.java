package com.beezu.beezu_api.dtos;

public record UserDisciplineResponseDTO(Long userId, String userName, Long disciplineId, String disciplineName, boolean isModerator) {

}
