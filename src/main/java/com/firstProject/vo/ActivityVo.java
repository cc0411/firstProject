package com.firstProject.vo;

import java.util.List;

/**
 * created by 吴家俊 on 2018/7/23.
 */
public class ActivityVo {

    //活动信息
    private int activityId;
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
    private String createrSignature;
    //参与者信息
    private List<UserVo> userVoList;
    //参与人名字
    private String userNameAll;

    public ActivityVo(int activityId, String activityName, String activityTime, String address, String introduction, String activityImageurl, Integer status, Integer peopleNumber, String createrName, String createrPhone, String createrImageurl, String createrSignature, List<UserVo> userVoList, String userNameAll) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityTime = activityTime;
        Address = address;
        Introduction = introduction;
        this.activityImageurl = activityImageurl;
        this.status = status;
        this.peopleNumber = peopleNumber;
        this.createrName = createrName;
        this.createrPhone = createrPhone;
        this.createrImageurl = createrImageurl;
        this.createrSignature = createrSignature;
        this.userVoList = userVoList;
        this.userNameAll = userNameAll;
    }

    public ActivityVo() {

    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
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

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
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

    public String getCreaterSignature() {
        return createrSignature;
    }

    public void setCreaterSignature(String createrSignature) {
        this.createrSignature = createrSignature;
    }

    public List<UserVo> getUserVoList() {
        return userVoList;
    }

    public void setUserVoList(List<UserVo> userVoList) {
        this.userVoList = userVoList;
    }

    public String getUserNameAll() {
        return userNameAll;
    }

    public void setUserNameAll(String userNameAll) {
        this.userNameAll = userNameAll;
    }
}