package com.zimo.springbootstu.service;

import com.zimo.springbootstu.bean.Comment;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.bean.Lesson;
import com.zimo.springbootstu.mybatis.dao.CommentMapper;
import com.zimo.springbootstu.mybatis.dao.CourseMapper;
import com.zimo.springbootstu.mybatis.dao.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CommentService {
    
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    LessonMapper lessonMapper;

    @Autowired
    CourseMapper courseMapper;

    /**
     * 获取用户的评论
     * @param userId
     * @return
     */
    public List<Comment> getListCommentById(Integer userId) {
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<Comment> comments = commentMapper.selectByExample(example);
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
}
