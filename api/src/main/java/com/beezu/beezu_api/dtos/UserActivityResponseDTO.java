package com.beezu.beezu_api.dtos;

import java.time.LocalDateTime;

import com.beezu.beezu_api.models.enums.ActivityStatus;

public record UserActivityResponseDTO(Long userId,Long activityId, ActivityStatus activityStatus, LocalDateTime completedAt, Integer earnedHoney) {

}
