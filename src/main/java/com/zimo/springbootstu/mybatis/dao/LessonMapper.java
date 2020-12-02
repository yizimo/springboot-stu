package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.Lesson;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonMapper extends TkMapper<Lesson> {
}
