package com.zimo.springbootstu.bean;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Data
public class Lesson {
    @Id
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Integer chapterId;      // 章节id
    private String lessonName;      // 课时名字
    private String lessonInfo;      // 课时内容
    private List<Comment> commentList;
    @Transient
    private Integer type;           // 是否观看过
}
