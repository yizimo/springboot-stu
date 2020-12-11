package com.zimo.springbootstu.service;


import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.bean.Order;
import com.zimo.springbootstu.mybatis.dao.CourseMapper;
import com.zimo.springbootstu.mybatis.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CourseMapper courseMapper;

    /**
     * 添加订单
     * @param order
     */
    public void insertOrder(Order order) {
        orderMapper.insert(order);
        orderMapper.insertUserCourse(order.getUserId(),order.getCourseId());
    }

    /**
     * 查找用户订单
     * @param userId
     * @return
     */
    public List<Order> findListOdetr(Integer userId) {
        List<Order> orders = orderMapper.selectListOder(userId);
        for (Order order : orders) {
            Course course = courseMapper.selectByPrimaryKey(order.getCourseId());
            order.setCourse(course);
            System.out.println(order.toString());
        }
        System.out.println(orders.toString());
        return orders;
    }
}
