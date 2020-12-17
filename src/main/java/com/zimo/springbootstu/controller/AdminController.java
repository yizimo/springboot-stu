package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.Advertise;
import com.zimo.springbootstu.bean.Order;
import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.service.AdvertiseService;
import com.zimo.springbootstu.service.CourseService;
import com.zimo.springbootstu.service.OrderService;
import com.zimo.springbootstu.service.UserService;
import com.zimo.springbootstu.utils.PageResult;
import com.zimo.springbootstu.utils.ResultBody;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @Autowired
    OrderService orderService;

    /**
     * 分页查询用户列表
     * @return
     */
    @GetMapping("/user/list/page")
    public ResultBody findListUserLimit() {
        List<User> pageResult = userService.findListUserByPage();
        return ResultBody.success(pageResult);
    }

    /**
     * 根据用户名的分页搜索
     * @param username
     * @return
     */
    @GetMapping(value = {"/search/user/list/page/{username}","/search/user/list/page"})
    public ResultBody searchUserByUsernameLimit(@PathVariable(value = "username" , required = false) String username) {
        if(username == " " || username == null) {
            List<User> pageResult = userService.findListUserByPage();
            return ResultBody.success(pageResult);
        } else {
            List<User> pageResult = userService.searchUserByUsername(username);
            return ResultBody.success(pageResult);
        }

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
     * @return
     */
    @GetMapping("/advertise/list/limit")
    public ResultBody findListAdvertiseLimit() {
        List<Advertise> list = advertiseService.getListAll();
        return ResultBody.success(list);
    }

    /**
     * 搜索广告分页展示
     * @param title
     * @return
     */
    @GetMapping(value = "/search/advertise/list/limit")
    public ResultBody searchAdvertiseByTitleLimit(@RequestParam(value = "title", required = false) String title) {
        if(title == null) {
            List<Advertise> list = advertiseService.getListAll();
            return ResultBody.success(list);
        } else {
            List<Advertise> pageResult = advertiseService.searchTitleByLimit(title);
            return ResultBody.success(pageResult);
        }
    }

    /**
     * 添加广告
     * @return
     */
    @PostMapping("/insert/advertise")
    public ResultBody insertAdvertise(@RequestParam("title") String title,@RequestParam("url") String url) {
        Advertise advertise = new Advertise();
        advertise.setTitle(title);
        advertise.setUrl(url);
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
        logger.info(advertise.toString());
        advertiseService.updateAdvertise(advertise);
        return ResultBody.success(null);
    }

    /**
     * 广告删除
     * @param id
     * @return
     */
    @PostMapping("/delete/advertise")
    public ResultBody deleteAdvertise(Integer id) {
        logger.info("deleteAdvertise, id = " + id);
        advertiseService.deleteAdvertise(id);
        return ResultBody.success(null);
    }

    /**
     * 教师或者后台管理获取订单
     * @param userId
     * @return
     */
    @GetMapping("/order/list")
    public ResultBody findListOrderByAllOrUserId(Integer userId) {
        if(userId == 0) {
            List<Order> orders = orderService.findListAllOrder();
            return ResultBody.success(orders);
        } else {
            List<Order> orders = orderService.findListOdetr(userId);
            return ResultBody.success(orders);
        }
    }




    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public ResultBody findListCourseByLimit(@Param("page") int page, @Param("size") int size) {
        return ResultBody.success(courseService.findList(page));
    }
}
