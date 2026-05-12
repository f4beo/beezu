package com.beezu.beezu_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beezu.beezu_api.models.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
