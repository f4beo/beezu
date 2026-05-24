package com.beezu.beezu_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.beezu.beezu_api.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	List<User>findByName(String name);
	Optional<UserDetails> findUserByEmail(String name);
}
