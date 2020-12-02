package com.zimo.springbootstu.bean;

public class Lesson {

    private Integer id;
    private Integer chapterId;      // 章节id
    private String lessonName;      // 课时名字
    private String lessonInfo;      // 课时内容

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonInfo() {
        return lessonInfo;
    }

    public void setLessonInfo(String lessonInfo) {
        this.lessonInfo = lessonInfo;
    }
}
