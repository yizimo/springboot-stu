package com.zimo.springbootstu.service;

import com.zimo.springbootstu.bean.Comment;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.bean.Lesson;
import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.mybatis.dao.CommentMapper;
import com.zimo.springbootstu.mybatis.dao.CourseMapper;
import com.zimo.springbootstu.mybatis.dao.LessonMapper;
import com.zimo.springbootstu.mybatis.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    LessonMapper lessonMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 获取用户的评论
     * @param userId
     * @return
     */
    public List<Comment> getListCommentById(Integer userId) {
        List<Comment> comments = new ArrayList<>();
        if (userId == 0) {
            comments = commentMapper.selectAll();
        } else {
            Example example = new Example(Comment.class);
            example.createCriteria().andEqualTo("userId",userId);
            comments = commentMapper.selectByExample(example);
        }
        for (Comment comment : comments) {
            Lesson lesson = lessonMapper.selectByPrimaryKey(comment.getLessonId());
            comment.setLesson(lesson);
            Course course = courseMapper.selectByPrimaryKey(comment.getCourseId());
            comment.setCourse(course);
        }
        return comments;
    }

    /**
     * 删除评论
     * @param id
     */
    public void deleteCommentBuId(Integer id ) {
        commentMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据课时内容获取评论
     * @param lessonId
     * @return
     */
    public List<Comment> getListCommentByLessonId(Integer lessonId) {
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("lessonId",lessonId);
        List<Comment> comments = commentMapper.selectByExample(example);
        for (Comment comment : comments) {
            User user = userMapper.selectByPrimaryKey(comment.getUserId());
            comment.setUser(user);
        }
        return comments;
    }
}
