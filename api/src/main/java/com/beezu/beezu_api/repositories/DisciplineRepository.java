package com.beezu.beezu_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beezu.beezu_api.models.Discipline;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
	
	Optional<Discipline> findByDisciplineCode(String code);
	boolean existsByDisciplineCode(String code);
}
