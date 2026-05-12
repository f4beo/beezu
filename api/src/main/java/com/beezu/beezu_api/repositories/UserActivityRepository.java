package com.beezu.beezu_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beezu.beezu_api.models.UserActivity;
import com.beezu.beezu_api.models.UserActivityId;

public interface UserActivityRepository extends JpaRepository<UserActivity, UserActivityId> {
	
	List<UserActivity> findByUser_Id(Long userId);
	Optional<UserActivity> findByUser_IdAndActivity_Id(Long userId, Long activityId);
}
