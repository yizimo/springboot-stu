package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.bean.Comment;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.bean.Lesson;
import com.zimo.springbootstu.interceptor.AuthorizationInterceptor;
import com.zimo.springbootstu.interceptor.Token;
import com.zimo.springbootstu.service.CategoryService;
import com.zimo.springbootstu.service.CommentService;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.utils.PageResult;
import com.zimo.springbootstu.utils.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller()
@ResponseBody
@RequestMapping("/course")
@CrossOrigin()
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CommentService commentService;

    /**
     * 根据id 查找课程,并且判断用户能否观看课时
     * @param id
     * @return
     */
    @GetMapping("/{id}/{userId}")
    public ResultBody findCourseById(@PathVariable(value = "id") Integer id ,
                                     @PathVariable(value = "userId") Integer userId) {
        Course course = courseService.findCourseById(id, userId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("course",course);
        if(id == 0) {
            map.put("userHaveCourse",false);
        } else {
            Boolean userHaveCourse = courseService.findUserHaveCourse(userId, id);
            map.put("userHaveCourse",userHaveCourse);
        }
        return ResultBody.success(map);
    }

    /**
     * 课时以观看
     * @param id
     * @return
     */
    @GetMapping("/read/lesson/{id}")
    @Token
    public ResultBody readLessonById(@PathVariable(value = "id") Integer id) {
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        courseService.insertUserLessonType(userId,id);
        return ResultBody.success(null);
    }

    /**
     * 进入章节详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Token
    public ResultBody findCourseById(@PathVariable(value = "id") Integer id ) {
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        Course course = courseService.findCourseById(id,userId);
        return ResultBody.success(course);
    }

    /**
     * 根据课时id 获取课时内容和评论
     * @param lessonId
     * @return
     */
    @GetMapping("/lesson/comment/{lessonId}")
    @Token
    public ResultBody findLessonAndCommentByLessonId(@PathVariable(value = "lessonId") Integer lessonId) {
        Lesson lesson = courseService.getLessonAndCommentByLessonId(lessonId);
        return ResultBody.success(lesson);
    }

    @PostMapping("/insert/comments")
    public ResultBody insertComment(@RequestBody Comment comment) {

        commentService.insertComment(comment);
        return ResultBody.success(null);
    }

    /**
     * 分页页面的分类的文章
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    public ResultBody findListCourseById(@PathVariable(name = "id") Integer id) {
        List<Category> categoriesAndCourseById = categoryService.findListAndCourseById(id);
        return ResultBody.success(categoriesAndCourseById);
    }

    /**
     * 分页页面的二级分类的文章
     * @param secCaId
     * @return
     */
    @GetMapping("/category/{secCaId}/{page}")
    public ResultBody findListCourseBySecCaId(@PathVariable(name = "secCaId") Integer secCaId,
                                              @PathVariable(name = "page") int page) {
        PageResult<Course> courseByCategory = courseService.findListBySecCaId(page, secCaId);
        return ResultBody.success(courseByCategory);
    }
}
