package com.firstProject.pojo;

public class Activity_memberKey {
    private Integer activityId;

    private Integer userId;

    public Activity_memberKey(Integer activityId, Integer userId) {
        this.activityId = activityId;
        this.userId = userId;
    }

    public Activity_memberKey() {
        super();
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}