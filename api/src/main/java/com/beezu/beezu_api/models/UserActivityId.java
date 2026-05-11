package com.beezu.beezu_api.models;

import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserActivityId implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long activityId;

    public UserActivityId(Long userId, Long activityId) {
        this.userId = userId;
        this.activityId = activityId;
    }
    public UserActivityId(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserActivityId that = (UserActivityId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(activityId, that.activityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, activityId);
    }
}
