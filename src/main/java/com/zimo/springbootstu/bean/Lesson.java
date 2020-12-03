package com.zimo.springbootstu.bean;


import lombok.Data;

@Data
public class Lesson {

    private Integer id;
    private Integer chapterId;      // 章节id
    private String lessonName;      // 课时名字
    private String lessonInfo;      // 课时内容

}
