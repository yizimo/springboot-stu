package com.zimo.springbootstu.service;


import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.bean.Order;
import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.mybatis.dao.CourseMapper;
import com.zimo.springbootstu.mybatis.dao.OrderMapper;
import com.zimo.springbootstu.mybatis.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    UserMapper userMapper;

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
            User user = userMapper.selectByPrimaryKey(order.getUserId());
            order.setUser(user);
            order.setCourse(course);
            System.out.println(order.toString());
        }
        System.out.println(orders.toString());
        return orders;
    }

    /**
     * 教师查看订单
     * @param userId
     * @return
     */
    public List<Order> getListOrderByTeacher(Integer userId) {
        Example example = new Example(Course.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<Course> courses = courseMapper.selectByExample(example);
        List<Order> orders = new ArrayList<>();
        for (Course cours : courses) {

            List<Order> orders1 = orderMapper.findListByCourseId(cours.getId());
            for (Order order : orders1) {
                order.setCourse(cours);
                User user = userMapper.selectByPrimaryKey(order.getUserId());
                order.setUser(user);
            }
            orders.addAll(orders1);
        }
        return orders;
    }

    /**
     * 获取全部订单
     * @return
     */
    public List<Order> findListAllOrder() {
        List<Order> orders = orderMapper.findListAll();
        for (Order order : orders) {
            Course course = courseMapper.selectByPrimaryKey(order.getCourseId());
            order.setCourse(course);
            User user = userMapper.selectByPrimaryKey(order.getUserId());
            order.setUser(user);
            System.out.println(order.toString());
        }
        System.out.println(orders.toString());
        return orders;
    }

    /**
     * 查找文章的购买人
     * @param courseId
     * @return
     */
    public List<Order> findListOrderByCourseIdAdmin(Integer courseId) {
        List<Order> orders = orderMapper.findListByCourseId(courseId);
        for (Order order : orders) {
            User user = userMapper.selectByPrimaryKey(order.getUserId());
            order.setUser(user);
            System.out.println(order.toString());
        }
        return orders;
    }

}
