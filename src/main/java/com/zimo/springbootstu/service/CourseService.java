package com.zimo.springbootstu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimo.springbootstu.bean.Chapter;
import com.zimo.springbootstu.bean.Comment;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.bean.Lesson;
import com.zimo.springbootstu.mybatis.dao.ChapterMapper;
import com.zimo.springbootstu.mybatis.dao.CommentMapper;
import com.zimo.springbootstu.mybatis.dao.CourseMapper;
import com.zimo.springbootstu.mybatis.dao.LessonMapper;
import com.zimo.springbootstu.utils.PageResult;
import com.zimo.springbootstu.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    LessonMapper lessonMapper;

    /**
     * 分页查找课程
     * @param page
     * @return
     */
    public PageResult<Course> findList(int page) {

        Example example = new Example(Course.class);
        PageResult pageResult = ListCourseByPage(page, example);
        return pageResult;
    }

    /**
     * 分类搜索
     * @param page
     * @param secCaId
     * @return
     */
    public PageResult<Course> findListBySecCaId(int page, Integer secCaId) {

        Example example = new Example(Course.class);
        example.createCriteria().andEqualTo("secCaId",secCaId);
        PageResult pageResult = ListCourseByPage(page, example);
        return pageResult;
    }

    /**
     * 查找热门，新品等的课程
     * @param otherTpye
     * @return
     */
    public List<Course> findListByOtherType(Integer otherTpye) {

        List<Course> courses = courseMapper.findListByOtherType(otherTpye);
        return courses;
    }

    /**
     * 智能补全
     * @param name
     * @return
     */
    public List<String> searchNameCompletion(String name) {
        List<String> listNameBySearch = courseMapper.findListNameBySearch("%" + name + "%");
        return  listNameBySearch;
    }

    /**
     * 搜索
     * @param name
     * @return
     */
    public List<Course> searchByName(String name) {
        List<Course> listCourseBySearch = courseMapper.findListCourseBySearch("%" + name + "%");
        return listCourseBySearch;
    }

    /**
     * 根据id 查找课程
     * @param id
     * @return
     */
    public Course findCourseById(Integer id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        List<Chapter> chapters = findListChapterByCourseId(id);
        course.setChapters(chapters);
        List<Comment> comments = findListCommentByCourseId(id);
        course.setComments(comments);
        logger.info(course.toString());
        return course;
    }

    /**
     * 根据课程id 查找章节
     * @param courseId
     * @return
     */
    public List<Chapter> findListChapterByCourseId(Integer courseId) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andEqualTo("courseId",courseId);
        List<Chapter> chapters = chapterMapper.selectByExample(example);
        for (Chapter chapter : chapters) {
            List<Lesson> lessons = findListLessionByChapterId(chapter.getId());
            chapter.setLessons(lessons);
        }
        return chapters;
    }

    /**
     * 根据 章节 id  查找课时信息
     * @param chapterId
     * @return
     */
    public List<Lesson> findListLessionByChapterId(Integer chapterId) {
        Example example = new Example(Lesson.class);
        example.createCriteria().andEqualTo("chapterId",chapterId);
        List<Lesson> lessons = lessonMapper.selectByExample(example);
        return lessons;
    }

    /**
     * 根据课程id 查找评论
     * @param courseId
     * @return
     */
    private List<Comment> findListCommentByCourseId(Integer courseId) {
        Example example = new Example(Comment.class);
        example.createCriteria()
                .andEqualTo("commentId",0)
                .andEqualTo("courseId",courseId);
        List<Comment> comments = commentMapper.selectByExample(example);
        for (Comment comment : comments) {
            List<Comment> commentByCommentId = findListCommentByCommentId(comment.getId());
            comment.setComments(commentByCommentId);
        }
        return comments;
    }

    /**
     * 根据评论id ，查找回复
     * @param commentId
     * @return
     */
    private List<Comment> findListCommentByCommentId(Integer commentId) {
        Example example = new Example(Comment.class);
        example.createCriteria()
                .andEqualTo("commentId",commentId);
        List<Comment> comments = commentMapper.selectByExample(example);
        return comments;
    }

    /**
     * 通用分页代码
     * @return
     */
    private PageResult ListCourseByPage(int page, Example example) {
        PageHelper.startPage(page,10);
        List<Course> courses = courseMapper.selectByExample(example);
        PageInfo<Course> pageInfo = new PageInfo<>();
        logger.info(pageInfo.toString());
        return PageUtils.getPageResult(pageInfo);
    }
}
