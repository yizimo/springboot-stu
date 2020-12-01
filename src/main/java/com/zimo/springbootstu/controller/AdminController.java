package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.utils.ResultBody;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    CourseService courseService;

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public ResultBody findListCourseByLimit(@Param("page") int page, @Param("size") int size) {
        return ResultBody.success(courseService.findList(page));
    }
}
