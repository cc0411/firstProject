package com.firstProject.pojo;

public class Exchange {
    private Integer id;

    private String name;

    private String time;

    private String address;

    private String expect;

    private Double price;

    private String imageurl;

    private Integer userId;

    private Integer exchangeUserId;

    private Integer statu;

    public Exchange(Integer id, String name, String time, String address, String expect, Double price, String imageurl, Integer userId, Integer exchangeUserId, Integer statu) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.address = address;
        this.expect = expect;
        this.price = price;
        this.imageurl = imageurl;
        this.userId = userId;
        this.exchangeUserId = exchangeUserId;
        this.statu = statu;
    }

    public Exchange() {
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

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect == null ? null : expect.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl == null ? null : imageurl.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExchangeUserId() {
        return exchangeUserId;
    }

    public void setExchangeUserId(Integer exchangeUserId) {
        this.exchangeUserId = exchangeUserId;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }
}