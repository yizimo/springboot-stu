package com.zimo.springbootstu.bean;

import java.util.Date;
import java.util.List;

public class Comment {

    private Integer id;
    private Integer commentId;      // 评论id 0 代表评论   >0 代表回复
    private Integer courseId;       // 课程id
    private Integer userId;         // 用户id
    private String commentInfo;     // 评论内容
    private Date commentTime;       // 评论时间
    private int commentLike;        // 点赞
    private int commentDislike;     // 踩
    private List<Comment> comments; // 回复

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public int getCommentLike() {
        return commentLike;
    }

    public void setCommentLike(int commentLike) {
        this.commentLike = commentLike;
    }

    public int getCommentDislike() {
        return commentDislike;
    }

    public void setCommentDislike(int commentDislike) {
        this.commentDislike = commentDislike;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
