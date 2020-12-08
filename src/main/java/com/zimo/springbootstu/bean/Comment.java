package com.zimo.springbootstu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Comment {

    private Integer id;
    private Integer commentId;      // 评论id 0 代表评论   >0 代表回复
    private Integer courseId;       // 课程id
    private Integer lessonId;       // 课时id
    private Integer userId;         // 用户id
    private String commentInfo;     // 评论内容
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentTime;       // 评论时间
    private int commentLike;        // 点赞
    private int commentDislike;     // 踩
    private List<Comment> comments; // 回复
    private User user;
}
