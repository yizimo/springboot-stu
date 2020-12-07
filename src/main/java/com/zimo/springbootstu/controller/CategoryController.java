package com.zimo.springbootstu.controller;


import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.service.CategoryService;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller()
@ResponseBody
@RequestMapping("/category")
@CrossOrigin()
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CourseService courseService;
    /**
     * 分类页面的分类
     * @param parentId
     * @return
     */
    @GetMapping("/list/{parentId}")
    public ResultBody findListCategoryCourseByIdAndParentId( @PathVariable(name = "parentId") Integer parentId) {
        Category category = categoryService.findCategoryById(parentId);
        List<Category> categoriesByParentId = categoryService.findListByParentId(parentId);
        HashMap<String ,Object> map = new HashMap<>();
        map.put("category",category);
        map.put("categoriesByParentId",categoriesByParentId);
        return ResultBody.success(map);
    }


}
