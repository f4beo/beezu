package com.beezu.beezu_api.models;

import com.beezu.beezu_api.models.constraints.UserDisciplineId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user_discipline")
public class UserDiscipline {
	
	@EmbeddedId
	private UserDisciplineId id;
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@MapsId("disciplineId")
	@JoinColumn(name = "discipline_id")
	private Discipline discipline;
	
	private boolean isModerator;
	
	public UserDiscipline() {
		
	}

	public UserDiscipline(User user, Discipline discipline) {
		this.user = user;
		this.discipline = discipline;
		this.isModerator = false;
	}
	
	public void promoteToModerator() {
		if(this.isModerator) return;
		this.isModerator = true;
	}
	
	public void revokeModerator() {
		if(!this.isModerator) return;
		this.isModerator = false;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public UserDisciplineId getId() {
		return id;
	}

	public boolean isModerator() {
		return isModerator;
	}
	
	
	
	
	
	
}
