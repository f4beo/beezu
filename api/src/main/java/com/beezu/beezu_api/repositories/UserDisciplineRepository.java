package com.beezu.beezu_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beezu.beezu_api.models.UserDiscipline;
import com.beezu.beezu_api.models.constraints.UserDisciplineId;

public interface UserDisciplineRepository extends JpaRepository<UserDiscipline, UserDisciplineId> {
	
	List<UserDiscipline> findByUser_Id(Long userId);
	Optional<UserDiscipline> findByUser_IdAndDiscipline_Id(Long userId, Long disciplineId);

}
