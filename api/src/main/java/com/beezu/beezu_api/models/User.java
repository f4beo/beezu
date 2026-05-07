package com.beezu.beezu_api.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.beezu.beezu_api.exceptions.UserAlreadyEnrolledException;
import com.beezu.beezu_api.exceptions.UserNotEnrolledDisciplineException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	@Column(name = "honey", nullable = false)
	private Integer honey;
	
	@OneToMany(mappedBy = "user")
	private List<UserDiscipline> disciplines = new ArrayList<>();
	@OneToMany(mappedBy = "user")
	private List<UserActivity> activities = new ArrayList<>();

	
	public User() {
		
	}
	
	public User(String name, String email, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.honey = 0;
		this.createdAt = LocalDateTime.now();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Integer getHoney() {
		return honey;
	}
	
	
	public List<UserDiscipline> getDisciplines() {
		return disciplines;
	}

	public List<UserActivity> getActivities() {
		return activities;
	}

	public void enrollDiscipline(UserDiscipline enrollment) {
		if(enrollment == null) {
			throw new IllegalArgumentException("Discipline cannot be null");
		}
		if(disciplines.contains(enrollment)) {
			throw new UserAlreadyEnrolledException("User already enrolled in this discipline");
		}
		
		disciplines.add(enrollment);
	}
	
	public void leaveDiscipline(UserDiscipline discipline) {
		if(discipline == null) {
			throw new IllegalArgumentException("Discipline cannot be null");
		}
		if(!disciplines.contains(discipline)) {
			throw new UserNotEnrolledDisciplineException("User is not enrolled in this discipline");
		}
		
		disciplines.remove(discipline);
	}
	

	
	
	
	
	



}
