package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.Advertise;
import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.service.AdvertiseService;
import com.zimo.springbootstu.service.CategoryService;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("index")
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    AdvertiseService advertiseService;

    @Autowired
    CourseService courseService;

    /**
     * 首页分类
     * @return
     */
    @GetMapping("/category/{parentId}")
    public ResultBody indexCategory(@PathVariable("parentId") Integer parentId) {
        List<Category> categories = categoryService.findListByParentId(parentId);
        return ResultBody.success(categories.subList(0,categories.size() > 7 ? 7 : categories.size()));
    }

    /**
     * 首页轮播
     * @return
     */
    @GetMapping("/advertise")
    public ResultBody indexAdvertise() {

        List<Advertise> advertises = advertiseService.getList();
        return ResultBody.success(advertises);
    }

    /**
     * 首页 热门，新品等展示
     * @param otherType
     * @return
     */
    @GetMapping("/course/{otherType}")
    public ResultBody indexCourseOtherType(@PathVariable(name = "otherType") Integer otherType) {

        List<Course> courses = courseService.findListByOtherType(otherType);
        return ResultBody.success(courses);
    }
}
