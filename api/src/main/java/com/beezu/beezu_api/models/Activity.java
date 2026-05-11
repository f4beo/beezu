package com.beezu.beezu_api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.beezu.beezu_api.models.enums.ActivityType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_activity")
public class Activity  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "description")
	private String description;
	@Enumerated(EnumType.STRING)
	@Column(name = "activity_type", nullable = false)
	private ActivityType activityType;
	@Column(name = "deadline")
	private LocalDateTime deadline;
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	@ManyToOne
	private Discipline discipline;
	@ManyToOne
	private User createdBy;
	
	public Activity() {
		
	}
	public Activity(String description, ActivityType activityType, LocalDateTime deadline, String title) {
		this.description = description;
		this.activityType = activityType;
		this.deadline = deadline;
		this.title = title;
		this.createdAt = LocalDateTime.now();
	}
	
	public boolean isOverdue() {
		return this.deadline != null && LocalDateTime.now().isAfter(deadline);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	public LocalDateTime getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	public Discipline getDiscipline() {
		return discipline;
	}
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}
	public Long getId() {
		return id;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	

}
