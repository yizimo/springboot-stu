package com.zimo.springbootstu.controller;

import com.aliyuncs.exceptions.ClientException;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.bean.Ends;
import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.interceptor.AuthorizationInterceptor;
import com.zimo.springbootstu.interceptor.Token;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.service.UserService;
import com.zimo.springbootstu.utils.Msg;
import com.zimo.springbootstu.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller()
@ResponseBody
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @GetMapping("/info")
    @Token
    public ResultBody getUserInfo() {
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        User user = userService.getUserInfo(userId);
        return ResultBody.success(user);
    }

    @PostMapping("/update/info")
    public ResultBody updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return ResultBody.success(null);
    }

    @PostMapping("/update/password")
    public ResultBody updateUserPassword(@RequestParam("id")Integer id,
                                         @RequestParam("password") String password,
                                         @RequestParam("newPassword") String newPassword) {
        Msg msg = userService.updatePassword(id, password, newPassword);
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

    @GetMapping("/user/course/userid")
    @Token
    public ResultBody findUserCourseByUserId() {
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        List<Course> courses = courseService.findUserCourseByUserId(userId);
        return ResultBody.success(courses);
    }

    @GetMapping("/search/user/course/userid/{name}")
    @Token
    public ResultBody searchUserCourseName(@PathVariable("name") String name) {
        Integer userId = Integer.valueOf(AuthorizationInterceptor.get());
        List<Course> courses = courseService.searchUserCourseByName(userId, name);
        return ResultBody.success(courses);
    }
}
