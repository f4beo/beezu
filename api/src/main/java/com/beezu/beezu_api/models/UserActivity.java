package com.beezu.beezu_api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.beezu.beezu_api.models.enums.ActivityStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_user_activity")
public class UserActivity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private UserActivityId id;
	@Enumerated(EnumType.STRING)
	@Column(name = "activity_status", nullable = false)
	private ActivityStatus activityStatus;
	@Column(name = "completed_at")
	private LocalDateTime completedAt;
	@Column(name = "earned_honey")
	private Integer earnedHoney;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@MapsId("userId")
	private User user;
	@ManyToOne
	@JoinColumn(name = "activity_id")
	@MapsId("activityId")
	private Activity activity;
	
	public UserActivity() {
		
	}
	
	public UserActivity(User user, Activity activity,ActivityStatus activityStatus) {
		this.user = user;
		this.activity = activity;
		this.activityStatus = activityStatus;
	}
	public void markAsCompleted() {
		if(activityStatus == ActivityStatus.COMPLETED) return;
		activityStatus = ActivityStatus.COMPLETED;
		completedAt = LocalDateTime.now();
		earnedHoney = calculateEarnedHoney();
	}

	private Integer calculateEarnedHoney() {
		if(activityStatus == ActivityStatus.COMPLETED) {
			return 50;
		}
		return 0;
	}

	public ActivityStatus getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(ActivityStatus activityStatus) {
		this.activityStatus = activityStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public UserActivityId getId() {
		return id;
	}

	public LocalDateTime getCompletedAt() {
		return completedAt;
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
		UserActivity other = (UserActivity) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
