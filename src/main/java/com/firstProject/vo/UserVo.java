package com.firstProject.vo;

/**
 * created by 吴家俊 on 2018/7/24.
 */
public class UserVo {

    private String name;
    private String signature;
    private String phone;
    private String imageurl;
    private String userName;

    public UserVo(String name, String signature, String phone, String imageurl, String userName) {
        this.name = name;
        this.signature = signature;
        this.phone = phone;
        this.imageurl = imageurl;
        this.userName = userName;
    }

    public UserVo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}