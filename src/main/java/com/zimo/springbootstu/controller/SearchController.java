package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.service.CategoryService;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.utils.PageResult;
import com.zimo.springbootstu.utils.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@ResponseBody
@RequestMapping("/search")
@CrossOrigin("*")
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    CourseService courseService;

    /**
     * 二级分类所拥有的课程分页展示
     * @param page
     * @param secCaId
     * @return
     */
    @GetMapping("/category/course")
    public ResultBody searchCourseBySecCaId(@RequestParam("page")int page, @RequestParam("secCaId")Integer secCaId) {
        PageResult<Course> pageResult =
                courseService.findListBySecCaId(page, secCaId);
        return ResultBody.success(pageResult);
    }

    /**
     * 智能补全
     * @param name
     * @return
     */
    @GetMapping("/course/completion")
    public ResultBody searchNameCompletion(String name) {
        List<String> strings = courseService.searchNameCompletion(name);
        return ResultBody.success(strings);
    }

    /**
     * 搜索结果
     * @param name
     * @return
     */
    @GetMapping("/course/{name}")
    public ResultBody searchByName(@PathVariable String name) {
        List<Course> courses = courseService.searchByName(name);
        logger.info(courses.toString());
        return ResultBody.success(courses);
    }
}
