package com.zimo.springbootstu.bean;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;

public class User {

    @Id
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String username;
    private String telephone;
    private String password;
    private String info;
    private Integer type; // 1 用户 2  教师 3  管理员

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                ", info='" + info + '\'' +
                ", type=" + type +
                '}';
    }
}