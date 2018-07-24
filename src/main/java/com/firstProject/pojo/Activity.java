package com.firstProject.pojo;

public class Activity {
    private Integer id;

    private String name;

    private String time;

    private String address;

    private String introduction;

    private Integer userId;

    private String imageurl;

    private Integer statu;

    public Activity(Integer id, String name, String time, String address, String introduction, Integer userId, String imageurl, Integer statu) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.address = address;
        this.introduction = introduction;
        this.userId = userId;
        this.imageurl = imageurl;
        this.statu = statu;
    }

    public Activity() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl == null ? null : imageurl.trim();
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }
}