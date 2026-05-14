package com.beezu.beezu_api.dtos;

import java.time.LocalDateTime;

import com.beezu.beezu_api.models.enums.ActivityType;

public record ActivityUpdateDTO( String title, String description, ActivityType type, LocalDateTime deadline ) {

}
