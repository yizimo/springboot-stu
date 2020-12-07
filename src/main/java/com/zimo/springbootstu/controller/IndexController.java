package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.Advertise;
import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.service.AdvertiseService;
import com.zimo.springbootstu.service.CategoryService;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@ResponseBody
@RequestMapping("/index")
@CrossOrigin()
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
    public ResultBody indexCategory(@PathVariable(name = "parentId") Integer parentId) {
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
