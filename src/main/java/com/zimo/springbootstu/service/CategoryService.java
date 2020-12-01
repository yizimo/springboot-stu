package com.zimo.springbootstu.service;

import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.mybatis.dao.CategoryMapper;
import com.zimo.springbootstu.mybatis.dao.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CourseMapper courseMapper;

    /**
     * 根据父类id 查找分类
     * @param categoryParentId
     * @return
     */
    public List<Category> findListByParentId(Integer categoryParentId) {
        List<Category> categories = categoryMapper.findListByParentId(categoryParentId);
        for (Category category : categories) {
            category.setCategoryList(categoryMapper.findListByParentId(category.getId()));
        }
        return categories;
    }

    /**
     * 展示下级分类的课程
     * @param id
     * @return
     */
    public List<Category> findListAndCourseById(Integer id) {
        // 查找下级分类
        List<Category> categories = categoryMapper.findListByParentId(id);

        // 下级分类填充课程
        for (Category category : categories) {
            Example example = new Example(Course.class);
            example.createCriteria().andEqualTo("categoryId",id);
            List<Course> courses = courseMapper.selectByExample(example);
            category.setCourses(courses.subList(0,courses.size() > 5 ? 5 : courses.size()));
        }
        return categories;
    }



}
