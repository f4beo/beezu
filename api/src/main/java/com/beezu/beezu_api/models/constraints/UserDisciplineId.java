package com.beezu.beezu_api.models.constraints;

import java.util.Objects;


import jakarta.persistence.Embeddable;


@Embeddable
public class UserDisciplineId {

	private Long userId;
	private Long disciplineId;
	
	
	public UserDisciplineId(Long userId, Long disciplineId) {
		this.userId = userId;
		this.disciplineId = disciplineId;
	}
	public UserDisciplineId() {
		
	}


	public Long getUserId() {
		return userId;
	}


	public Long getDisciplineId() {
		return disciplineId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(disciplineId, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDisciplineId other = (UserDisciplineId) obj;
		return Objects.equals(disciplineId, other.disciplineId) && Objects.equals(userId, other.userId);
	}


	

	
	
}
