package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.bean.*;
import com.zimo.springbootstu.service.*;
import com.zimo.springbootstu.utils.Msg;
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

    @Autowired
    CommentService commentService;

    @Autowired
    CategoryService categoryService;

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
     * 获取用户的评论
     * @param userId
     * @return
     */
    @GetMapping("/comment/list/{userId}")
    public ResultBody getListCommentByUserId(@PathVariable("userId") Integer userId) {
        List<Comment> listCommentById = commentService.getListCommentById(userId);
        return ResultBody.success(listCommentById);
    }

    /**
     * 删除用户的评论
     * @param id
     * @return
     */
    @PostMapping("/delete/comment/id")
    public ResultBody deleteCommentById(Integer id) {
        System.out.println(id);
        commentService.deleteCommentBuId(id);
        return ResultBody.success(null);
    }

    /**
     * 分类管理加载分类
     * @return
     */
    @GetMapping("/get/list/category")
    public ResultBody getListCategory() {

        List<Category> categories = categoryService.findListCategoryByParentId(0);
        return ResultBody.success(categories);
    }

    /**
     * 分类修改
     * @param category
     * @return
     */
    @PostMapping("/update/category")
    public ResultBody updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return ResultBody.success(null);
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping("/insert/category")
    public ResultBody insertCategory(@RequestBody Category category) {
        categoryService.insertCategory(category);
        return ResultBody.success(null);
    }

    /**
     * 删除分类
     * @param id
     * @param type
     * @param parentId
     * @return
     */
    @PostMapping("/delete/category")
    public ResultBody deleteCategory(@RequestParam("id") Integer id,
                                      @RequestParam("type") Integer type,
                                      @RequestParam("parentId") Integer parentId) {
        Msg msg = categoryService.deleteCategory(id, type, parentId);
        if(msg.getCode() == 200) {
            return ResultBody.error("-1",msg.getExtend().get("info").toString());
        } else {
            return ResultBody.success(null);
        }
    }

    /**
     * 教师或者管理员获取文章列表
     * @param userId
     * @return
     */
    @GetMapping(value = {"/course/list/{userId}","/course/list"})
    public ResultBody getCourseListById(@PathVariable(value = "userId",required = false) Integer userId) {
        System.out.println(userId);
        List<Course> courses = courseService.findListCourseByAdmin(userId);
        return ResultBody.success(courses);
    }

    /**
     * 管理搜索
     * @param userId
     * @param name
     * @return
     */
    @GetMapping(value = {"/search/course/list/{userId}","/search/course/list"})
    public ResultBody searchCourseListAdmin(@PathVariable(value = "userId",required = false) Integer userId,
                                            @RequestParam(value = "name",required = false) String name) {
        if(userId == null && name == null) {
            List<Course> courses = courseService.findListCourseByAdmin(userId);
            return ResultBody.success(courses);
        } else {
            List<Course> courses = courseService.searchListCourseByNameAdmin(name, userId);
            return ResultBody.success(courses);
        }
    }

    /**
     * 修改othertype 或者上下架,修改文章信息
     * @param course
     * @return
     */
    @PostMapping("/update/other/or/type")
    public ResultBody updateCourseOtherTypeOrTypeById(@RequestBody Course course) {
        courseService.updateCourseOtherTypeAdmin(course);
        return ResultBody.success(null);
    }

    /**
     * 查找文章购买人
     * @param courseId
     * @return
     */
    @GetMapping("/course/list/user/{courseId}")
    public ResultBody findListUserByCourseIdAdmin(@PathVariable("courseId") Integer courseId) {
        List<Order> orders = orderService.findListOrderByCourseIdAdmin(courseId);
        return ResultBody.success(orders);
    }

    /**
     * 查找文章详情
     * @param courseId
     * @return
     */
    @GetMapping("/course/one/{courseId}")
    public ResultBody findCourseByCourseIdAdmin(@PathVariable("courseId") Integer courseId) {
        Course course = courseService.findCourseByCourseIdAdmin(courseId);
        return ResultBody.success(course);
    }

    /**
     * 根据父类id 查找分类
     * @param parentId
     * @return
     */
    @GetMapping("/course/list/category/{parentId}")
    public ResultBody findListCategoryByParentId(@PathVariable("parentId") Integer parentId) {
        List<Category> categories = categoryService.findListCategoryByParentIdAdmin(parentId);
        return ResultBody.success(categories);
    }

    /**
     * 添加文章
     * @param course
     * @return
     */
    @PostMapping("/course/insert")
    public ResultBody insertCourse(@RequestBody Course course) {
        courseService.insertCourse(course);
        return ResultBody.success(null);
    }

    /**
     * 根据文章id 获取章节
     * @param courseId
     * @return
     */
    @GetMapping("/list/charpter/{courseId}")
    public ResultBody findListChapterByCourseId(@PathVariable("courseId") Integer courseId) {
        List<Chapter> chapters = courseService.getListChapterAdmin(courseId);
        return ResultBody.success(chapters);
    }

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @PostMapping("/insert/chapter")
    public ResultBody insertChapter(@RequestBody Chapter chapter) {
        courseService.insertChapterAdmin(chapter);
        return ResultBody.success(null);
    }

    /**
     * 更新章节
     * @param chapter
     * @return
     */
    @PostMapping("/update/chapter")
    public ResultBody updateChapter(@RequestBody Chapter chapter) {
        courseService.updateChapterAdmin(chapter);
        return ResultBody.success(null);
    }

    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    @GetMapping("/delete/chapter/{chapterId}")
    public ResultBody deleteChapter(@PathVariable("chapterId") Integer chapterId) {
        courseService.deleteChapterByIdAdmin(chapterId);
        return ResultBody.success(null);
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
