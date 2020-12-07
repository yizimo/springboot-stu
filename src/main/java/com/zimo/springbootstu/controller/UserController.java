package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.service.UserService;
import com.zimo.springbootstu.utils.Msg;
import com.zimo.springbootstu.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

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

    public ResultBody findListCourseByUserId(Integer userId) {

        return ResultBody.success(null);
    }
}
