package com.beezu.beezu_api.models;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.beezu.beezu_api.exceptions.ActivityNotExistsException;
import com.beezu.beezu_api.exceptions.EnrollmentNotExistsException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_discipline")
public class Discipline {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "professor", nullable = false)
	private String professor;
	@Column(name = "discipline_code", nullable = false, unique = true)
	private String disciplineCode;
	
	@OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL,  orphanRemoval = true)
	private List<Activity> activities = new ArrayList<>();
	@OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserDiscipline> enrollments = new ArrayList<>();

	
	public Discipline() {
		
	}
	
	public Discipline(String name, String description, String professor) {
		this.name = name;
		this.professor = professor;
		this.description = description;
		this.disciplineCode = generateAccessCode();
	}
	
	public void addActivity(Activity activity) {
		if(activity == null) {
			throw new IllegalArgumentException("The activity cannot be null");
		}
		activities.add(activity);
		activity.setDiscipline(this);
	}
	public void removeActivity(Activity activity) {
		if(activity == null) {
			throw new IllegalArgumentException("The activity cannot be null");
		}
		if(!activities.contains(activity)) {
			throw new ActivityNotExistsException("The activity does not exist");
		}
		activities.remove(activity);
		activity.setDiscipline(null);
	}
	public void addEnrollment(UserDiscipline enrollment) {
		if(enrollment == null) {
			throw new IllegalArgumentException("The enrollment cannot be null");
		}
		if(enrollments.contains(enrollment)) {
			throw new EnrollmentAlreadyExistsException("The enrollment already exist");
		}
		enrollments.add(enrollment);
		enrollment.setDiscipline(this);
	}
	
	public void removeEnrollment(UserDiscipline enrollment) {
		if(enrollment == null) {
			throw new IllegalArgumentException("The enrollment cannot be null");
		}
		if(!enrollments.contains(enrollment)) {
			throw new EnrollmentNotExistsException("The enrollment does not exist");
		}
		enrollments.remove(enrollment);
		enrollment.setDiscipline(null);
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public Long getId() {
		return id;
	}

	public String getDisciplineCode() {
		return disciplineCode;
	}

	
	public List<Activity> getActivities() {
		return activities;
	}

	public List<UserDiscipline> getEnrollments() {
		return enrollments;
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
		Discipline other = (Discipline) obj;
		return Objects.equals(id, other.id);
	}

	private String generateAccessCode() {
		String alphabet = "23456789ABCDEFGHJKMNPQRSTUVWXYZ";
	    SecureRandom random = new SecureRandom();
	    StringBuilder code = new StringBuilder();

	    for (int i = 0; i < 7; i++) {
	        int index = random.nextInt(alphabet.length());
	        code.append(alphabet.charAt(index));
	    }

	    return code.toString();
	}
	

}
