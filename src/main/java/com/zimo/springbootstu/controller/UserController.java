package com.zimo.springbootstu.controller;

import com.aliyuncs.exceptions.ClientException;
import com.zimo.springbootstu.bean.*;
import com.zimo.springbootstu.interceptor.AuthorizationInterceptor;
import com.zimo.springbootstu.interceptor.Token;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.service.OrderService;
import com.zimo.springbootstu.service.UserService;
import com.zimo.springbootstu.utils.Msg;
import com.zimo.springbootstu.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller()
@ResponseBody
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    OrderService orderService;

    /**
     * 获取个人信息
     * @return
     */
    @GetMapping("/info")
    @Token
    public ResultBody getUserInfo() {
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        User user = userService.getUserInfo(userId);
        return ResultBody.success(user);
    }

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @PostMapping("/update/info")
    public ResultBody updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return ResultBody.success(null);
    }

    /**
     * 修改密码
     * @param pas
     * @return
     */
    @PostMapping("/update/password")
    public ResultBody updateUserPassword(@RequestBody Pas pas) {
        Msg msg = userService.updatePassword(pas.getId(), pas.getPassword(), pas.getNewPassword());
        if(msg.getCode() == 200) { // 失败
            return ResultBody.error("-1", (String) msg.getExtend().get("info"));
        }
        return ResultBody.success(null);
    }

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
   public ResultBody login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
       Msg msg = userService.login(username, password);
       if(msg.getCode() == 100) {
           return ResultBody.success(msg.getExtend());
       } else {
           return ResultBody.error("-1",msg.getExtend().get("info").toString());
       }
   }

    /**
     * 获取手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/phone/code")
    public ResultBody phoneCode(@RequestParam("phone") String phone) throws ClientException {
        userService.phoneCode(phone);
        return ResultBody.success(null);
    }

    /**
     * 根据手机号重置密码
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("/phone/update/password")
    public ResultBody updatePasswordByPhone(@RequestParam("phone") String phone,
                                            @RequestParam("code") Integer code) {
        Msg msg = userService.updatePasswordByPhone(phone, code);
        if(msg.getCode() == 100) {
            return ResultBody.success(null);
        }
        return ResultBody.error("-1",msg.getExtend().get("info").toString());
    }

    /**
     * 注册
     * @return
     */
    @PostMapping("/register")
    public ResultBody register(@RequestBody Ends user) {
        Msg msg = userService.register(user.getUsername(),user.getPassword(),user.getCode(),user.getPhone());
        if(msg.getCode() == 100) {
            return ResultBody.success(null);
        } else {
            return ResultBody.error("-1",msg.getExtend().get("info").toString());
        }
    }

    /**
     * 我的课程
     * @return
     */
    @GetMapping("/user/course/userid")
    @Token
    public ResultBody findUserCourseByUserId() {
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        List<Course> courses = courseService.findUserCourseByUserId(userId);
        return ResultBody.success(courses);
    }

    /**
     * 我的课程搜索
     * @param name
     * @return
     */
    @GetMapping("/search/user/course/userid/{name}")
    @Token
    public ResultBody searchUserCourseName(@PathVariable("name") String name) {
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        List<Course> courses = courseService.searchUserCourseByName(userId, name);
        return ResultBody.success(courses);
    }

    /**
     * 生成订单
     * @param courseId
     * @param money
     * @return
     */
    @GetMapping("/insert/order/{id}/{money}")
    @Token
    public ResultBody insertOrder(@PathVariable("id") Integer courseId, @PathVariable("money") double money) {

        String uid = getUUID();
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        Date creatTime = new Date();
        Order order = new Order();
        System.out.println(creatTime);
        order.setCourseId(courseId);
        order.setCreatTime(new Date());
        order.setMoney(money);
        order.setOid(uid);
        order.setUserId(userId);
        orderService.insertOrder(order);
        return ResultBody.success(null);
    }

    @GetMapping("/user/list/order")
    @Token
    public ResultBody findListOrder() {
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        List<Order> orders = orderService.findListOdetr(userId);
        return ResultBody.success(orders);
    }


    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
}
