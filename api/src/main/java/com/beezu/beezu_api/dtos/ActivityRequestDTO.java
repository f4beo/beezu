package com.beezu.beezu_api.dtos;

import java.time.LocalDateTime;

import com.beezu.beezu_api.models.enums.ActivityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActivityRequestDTO( @NotBlank String title, String description,@NotNull ActivityType type, LocalDateTime deadline ) {

}
