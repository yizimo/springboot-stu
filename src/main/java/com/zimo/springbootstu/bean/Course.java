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
    private String courseName;      // 简介
    private String courseInfo;      // 内容
    private String courseTitle;     // 标题
    private String url;             // 图片路径
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;         // 创建时间
    private Double money;           // 价格
    private Integer type; // 1 上架， 2 下架
    private Integer otherType; // 1 热卖   2 新品   3 其他

    private User user;              // 发表人
    private Category category;      // 分类
    private List<Chapter> chapters;  // 拥有章节
    private List<Comment> comments;   // 拥有评论

}
