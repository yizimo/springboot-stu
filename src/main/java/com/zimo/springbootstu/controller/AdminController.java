package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.Advertise;
import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.service.AdvertiseService;
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

    @Autowired
    AdvertiseService advertiseService;

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
     * 广告的分页获取
     * @param page
     * @return
     */
    @GetMapping("/advertise/list/limit")
    public ResultBody findListAdvertiseLimit(@RequestParam("page")int page) {
        PageResult list = advertiseService.getList(page);
        return ResultBody.success(list);
    }

    /**
     * 搜索广告分页展示
     * @param page
     * @param title
     * @return
     */
    @GetMapping("/search/advertise/list/limit")
    public ResultBody searchAdvertiseByTitleLimit(@RequestParam("page") int page, @RequestParam("title") String title) {
        PageResult pageResult = advertiseService.searchTitleByLimit(page, title);
        return ResultBody.success(pageResult);
    }

    /**
     * 添加广告
     * @param advertise
     * @return
     */
    @PostMapping("/insert/advertise")
    public ResultBody insertAdvertise(@RequestBody Advertise advertise) {

        advertiseService.insertAdvertise(advertise);
        return ResultBody.success(null);
    }

    /**
     * 广告上下架，广告修改，
     * @param advertise
     * @return
     */
    @PostMapping("/update/advertise")
    public ResultBody updateAdvertise(@RequestBody Advertise advertise) {
        advertiseService.updateAdvertise(advertise);
        return ResultBody.success(null);
    }

    @PostMapping("/delete/advertise")
    public ResultBody deleteAdvertise(Integer id) {
        advertiseService.deleteAdvertise(id);
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
