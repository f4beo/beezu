package com.beezu.beezu_api.dtos;

import java.time.LocalDateTime;

import com.beezu.beezu_api.models.enums.ActivityType;

public record ActivityResponseDTO(Long id, String title, String description, ActivityType type, LocalDateTime createdAt, LocalDateTime deadline) {

}
