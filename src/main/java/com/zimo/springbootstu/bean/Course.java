package com.zimo.springbootstu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
public class Course {

    @Id
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Integer userId;   // 教师id
    private Integer categoryId;     // 分类id
    private Integer secCaId;        // 二级分类id
    private String courseName;      // 标题
    private String courseInfo;      // 简介
    private String url;             // 图片路径
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;         // 创建时间
    private Double money;           // 价格
    private Integer type; // 1 上架， 2 下架
    private Integer otherType; // 1 热卖   2 新品   3 其他

    private User user;
    private List<Chapter> chapters;
    private List<Comment> comments;

}
