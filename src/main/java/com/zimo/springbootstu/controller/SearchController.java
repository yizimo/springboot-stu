package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.service.CategoryService;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.utils.PageResult;
import com.zimo.springbootstu.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("search")
public class SearchController {

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


}
