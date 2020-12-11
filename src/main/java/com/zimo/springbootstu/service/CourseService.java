package com.zimo.springbootstu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimo.springbootstu.bean.*;
import com.zimo.springbootstu.mybatis.dao.*;
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

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserLessonTypeMapper userLessonTypeMapper;

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
        PageHelper.startPage(page,10);
        List<Course> courses =courseMapper.findListCourseByCateSecId(secCaId);
        PageInfo<Course> pageInfo = new PageInfo<>(courses);
        logger.info(pageInfo.toString());
        return PageUtils.getPageResult(pageInfo);
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
        return  listNameBySearch.subList(0,listNameBySearch.size() > 7 ? 7 : listNameBySearch.size());
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
    public Course findCourseById(Integer id, Integer userId) {
        Course course = courseMapper.selectByPrimaryKey(id);
        List<Chapter> chapters = findListChapterByCourseId(id, userId);
        User user = findUserByCourseId(course.getUserId());
        course.setUser(user);
        List<Comment> comments = findListCommentByCourseId(course.getId());
        course.setComments(comments);
        course.setChapters(chapters);
        logger.info(course.toString());
        return course;
    }

    /**
     * 该文章，用户是否购买
     * @param userId
     * @param id
     * @return
     */
    public Boolean findUserHaveCourse(Integer userId, Integer id) {
        List<Integer> list = courseMapper.findUserHaveCourse(userId, id);
        if(list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查找课程的发表者
     * @param userId
     * @return
     */
    public User findUserByCourseId(Integer userId) {

        User user = userMapper.selectByPrimaryKey(userId);
        return  user;
    }
    /**
     * 根据课程id 查找章节
     * @param courseId
     * @return
     */
    public List<Chapter> findListChapterByCourseId(Integer courseId, Integer userId) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andEqualTo("courseId",courseId);
        List<Chapter> chapters = chapterMapper.selectByExample(example);
        for (Chapter chapter : chapters) {
            List<Lesson> lessons = findListLessionByChapterId(chapter.getId(), userId);
            chapter.setLessons(lessons);
        }
        return chapters;
    }

    /**
     * 根据 章节 id  查找课时信息
     * @param chapterId
     * @return
     */
    public List<Lesson> findListLessionByChapterId(Integer chapterId, Integer userId) {
        Example example = new Example(Lesson.class);
        example.createCriteria().andEqualTo("chapterId",chapterId);
        List<Lesson> lessons = lessonMapper.selectByExample(example);

        for (Lesson lesson : lessons) {
            if(userId != 0) {
                List<Integer> list = courseMapper.userIsReadLesson(userId, lesson.getId());
                if(list.size() > 0) {
                    lesson.setType(1);
                } else {
                    lesson.setType(0);
                }
            }
            List<Comment> comments = findListCommentByLessonId(lesson.getId());
            lesson.setCommentList(comments);
        }
        return lessons;
    }

    /**
     * 查找课时的评论
     * @param lessonId
     * @return
     */
    private List<Comment> findListCommentByLessonId(Integer lessonId) {
        Example example = new Example(Comment.class);
        example.createCriteria()
                .andEqualTo("commentId",0)
                .andEqualTo("lessonId",lessonId);
        List<Comment> comments = commentMapper.selectByExample(example);
        for (Comment comment : comments) {
            User user = findUserByCourseId(comment.getUserId());
            comment.setUser(user);
            List<Comment> commentByCommentId = findListCommentByCommentId(comment.getId());
            for (Comment comment1 : commentByCommentId) {
                User user1 = findUserByCourseId(comment.getUserId());
                comment1.setUser(user1);
            }
            comment.setComments(commentByCommentId);
        }
        return comments;
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
            User user = findUserByCourseId(comment.getUserId());
            comment.setUser(user);
            List<Comment> commentByCommentId = findListCommentByCommentId(comment.getId());
            for (Comment comment1 : commentByCommentId) {
                User user1 = findUserByCourseId(comment.getUserId());
                comment1.setUser(user1);
            }
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
     * 查找用户的我的课程
     * @param id
     * @return
     */
    public List<Course> findUserCourseByUserId(Integer id) {
        List<Course> userCourseByUserId = courseMapper.findUserCourseByUserId(id);
        return userCourseByUserId;
    }

    /**
     * 查找用户的我的课程的搜索
     * @param id
     * @param name
     * @return
     */
    public List<Course> searchUserCourseByName(Integer id, String name) {
        name = '%' + name + '%';
        List<Course> courses = courseMapper.searchUserCourseByName(id, name);
        return courses;
    }

    /**
     * 课时以观看
     * @param userId
     * @param lessonId
     */
    public void insertUserLessonType(Integer userId, Integer lessonId) {
        UserLessonType userLessonType = new UserLessonType();
        userLessonType.setUserId(userId);
        userLessonType.setLessonId(lessonId);
        userLessonTypeMapper.insertUseGeneratedKeys(userLessonType);
    }

    /**
     * 通用分页代码
     * @return
     */
    private PageResult ListCourseByPage(int page, Example example) {
        PageHelper.startPage(page,10);
        List<Course> courses = courseMapper.selectByExample(example);
        PageInfo<Course> pageInfo = new PageInfo<>(courses);
        logger.info(pageInfo.toString());
        return PageUtils.getPageResult(pageInfo);
    }


}
