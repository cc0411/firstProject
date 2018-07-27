package com.firstProject.vo;

/**
 * created by 吴家俊 on 2018/7/23.
 */
public class ActivityVoU {

    //活动信息
    private String activityName;
    private String activityTime;
    private String Address;
    private String Introduction;
    private String activityImageurl;
    private Integer status;
    //使用activity_member表查询人数
    private Integer peopleNumber;
    //发起人信息
    private String createrName;
    private String createrPhone;
    private String createrImageurl;
    //参与者信息
    private String userNameAll;

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getUserNameAll() {
        return userNameAll;
    }

    public void setUserNameAll(String userNameAll) {
        this.userNameAll = userNameAll;
    }

    public ActivityVoU(String activityName, String activityTime, String address, String introduction, String activityImageurl, Integer status, Integer peopleNumber, String craeterName, String createrPhone, String createrImageurl) {
        this.activityName = activityName;
        this.activityTime = activityTime;
        Address = address;
        Introduction = introduction;
        this.activityImageurl = activityImageurl;
        this.status = status;
        this.peopleNumber = peopleNumber;
        this.createrName = craeterName;
        this.createrPhone = createrPhone;
        this.createrImageurl = createrImageurl;
    }

    public ActivityVoU() {

    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getActivityImageurl() {
        return activityImageurl;
    }

    public void setActivityImageurl(String activityImageurl) {
        this.activityImageurl = activityImageurl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getCraeterName() {
        return createrName;
    }

    public void setCraeterName(String craeterName) {
        this.createrName = craeterName;
    }

    public String getCreaterPhone() {
        return createrPhone;
    }

    public void setCreaterPhone(String createrPhone) {
        this.createrPhone = createrPhone;
    }

    public String getCreaterImageurl() {
        return createrImageurl;
    }

    public void setCreaterImageurl(String createrImageurl) {
        this.createrImageurl = createrImageurl;
    }
}