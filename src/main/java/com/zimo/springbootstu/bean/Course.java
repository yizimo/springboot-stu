package com.zimo.springbootstu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Course {

    @Id
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Integer userId;   // 用户id
    private Integer categoryId;     // 分类id
    private Integer secCaId;        // 二级分类id
    private String courseName;      // 标题
    private String courseInfo;      // 简介
    private String url;             // 图片路径
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;         // 创建时间
    private Integer type; // 1 上架， 2 下架
    private Integer otherType; // 1 热卖   2 新品   3 其他

    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSecCaId() {
        return secCaId;
    }

    public void setSecCaId(Integer secCaId) {
        this.secCaId = secCaId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOtherType() {
        return otherType;
    }

    public void setOtherType(Integer otherType) {
        this.otherType = otherType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", secCaId=" + secCaId +
                ", courseName='" + courseName + '\'' +
                ", courseInfo='" + courseInfo + '\'' +
                ", url='" + url + '\'' +
                ", creatTime=" + creatTime +
                ", type=" + type +
                ", otherType=" + otherType +
                ", user=" + user +
                '}';
    }
}
