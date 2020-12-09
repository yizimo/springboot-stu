package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.service.CategoryService;
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

    /**
     * 根据id 查找课程,并且判断用户能否观看课时
     * @param id
     * @return
     */
    @GetMapping("/{id}/{userId}")
    public ResultBody findCourseById(@PathVariable("id") Integer id,
                                     @PathVariable("userId") Integer userId) {

        logger.info("id：" + id);
        Course course = courseService.findCourseById(id);
        Boolean userHaveCourse = courseService.findUserHaveCourse(userId, id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("course",course);
        map.put("userHaveCourse",userHaveCourse);
        return ResultBody.success(map);
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
