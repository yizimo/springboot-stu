package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.Comment;
import com.zimo.springbootstu.bean.Order;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper extends TkMapper<Order> {

    @Insert("insert into `order`(`oid`,course_id,user_id,money,creat_time) values(#{oid}, #{courseId}, #{userId}, #{money},now())")
    int insert(Order order);

    @Insert("insert into user_course(user_id,course_id) values(#{userId},#{courseId})")
    int insertUserCourse(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    @Select("select * from `order` where user_id = #{userId}")
    List<Order> selectListOder(Integer userId);
}
