package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper extends TkMapper<Course> {

    // 查找标记为热门等的课程
    List<Course> findListByOtherType(Integer otherType);

    // 智能补全
    List<String> findListNameBySearch(String name);

    // 搜索结果
    List<Course> findListCourseBySearch(String name);

    //  根据分类id 查找文章
    List<Course> findListCourseByCategoryId(Integer categoryId);

    // 二级分类查找
    List<Course> findListCourseByCateSecId(Integer SecCaId);
}
