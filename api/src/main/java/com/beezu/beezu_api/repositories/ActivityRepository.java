package com.beezu.beezu_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beezu.beezu_api.models.Activity;
import com.beezu.beezu_api.models.enums.ActivityType;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
	
	List<Activity> findByDisciplineId(Long disciplineID);
	List<Activity> findByActivityType(ActivityType type);

}
