package com.zimo.springbootstu.bean;


import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

public class Chapter {

    @Id
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String chapterName;     //章节名字
    private Integer courseId;       // 课程id
    private List<Lesson> lessons;   //  课时信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
