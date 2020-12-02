package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.utils.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("course")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;

    /**
     * 根据id 查找课程
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultBody findCourseById(@PathVariable("id") Integer id) {

        logger.info("id：" + id);
        Course course = courseService.findCourseById(id);
        return ResultBody.success(course);
    }
}
