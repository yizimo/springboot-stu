package com.zimo.springbootstu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.mybatis.dao.CourseMapper;
import com.zimo.springbootstu.utils.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    CourseMapper courseMapper;

    public PageResult<Course> findList(int page, int size) {

        PageHelper.startPage(page,size);
        Example example = new Example(Course.class);
        List<Course> courses = courseMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(courses)) {
            return null;
        }
        PageInfo<Course> pageInfo = new PageInfo<>();
        logger.info(pageInfo.toString());
        return new PageResult<>(pageInfo.getTotal(),courses);
    }
}
