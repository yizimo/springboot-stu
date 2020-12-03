package com.zimo.springbootstu.bean;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@Data
public class Chapter {

    @Id
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String chapterName;     //章节名字
    private Integer courseId;       // 课程id
    private List<Lesson> lessons;   //  课时信息


}
