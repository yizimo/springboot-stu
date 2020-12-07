package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.service.UserService;
import com.zimo.springbootstu.utils.PageResult;
import com.zimo.springbootstu.utils.ResultBody;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller()
@ResponseBody
@RequestMapping("/admin")
@CrossOrigin()
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    /**
     * 分页查询用户列表
     * @param page
     * @return
     */
    @GetMapping("/user/list/page")
    public ResultBody findListUserLimit(@RequestParam("page") int page) {
        PageResult pageResult = userService.findListUserByPage(page);
        return ResultBody.success(pageResult);
    }

    /**
     * 根据用户名的分页搜索
     * @param page
     * @param username
     * @return
     */
    @GetMapping("/search/user/list/page/{username}")
    public ResultBody searchUserByUsernameLimit(@RequestParam("page") int page,
                                                  @PathVariable("username") String username) {
        PageResult pageResult = userService.searchUserByUsername(username, page);
        return ResultBody.success(pageResult);
    }

    /**
     * 禁用，0， 启动   1
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/update/user/status")
    public ResultBody updateUserStatusById(@RequestParam("id") Integer id,
                                           @RequestParam("status") Integer status) {
        userService.updateUserStatusById(status,id);
        return ResultBody.success(null);
    }

    /**
     * 权限   1 用户 2  教师 3  管理员
     * @param id
     * @param type
     * @return
     */
    @PostMapping("/update/user/type")
    public ResultBody updateUserTypeById(@RequestParam("id") Integer id,
                                         @RequestParam("type") Integer type) {
        userService.updateUserTypeById(type,id);
        return ResultBody.success(null);
    }





    /**
     * 分页查询
     * @param page
     * @return
     */
    public ResultBody findListCourseByLimit(@Param("page") int page) {
        return ResultBody.success(courseService.findList(page));
    }
}
