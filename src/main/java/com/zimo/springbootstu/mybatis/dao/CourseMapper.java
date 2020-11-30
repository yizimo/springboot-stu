package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMapper extends TkMapper<Course> {
}
