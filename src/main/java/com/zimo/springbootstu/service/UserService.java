package com.zimo.springbootstu.service;

import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.mybatis.dao.UserMapper;
import com.zimo.springbootstu.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserMapper userMapper;


    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取验证码
     * @param phone
     */
    public void phoneCode(String phone) throws ClientException {
        Integer code = AliyunSmsUtils.getCode();
        redisUtil.setByTime(phone,code+"");
        AliyunSmsUtils.sendSms(phone,code);
    }

    /**
     * 注册
     * @param username
     * @param password
     * @param code
     * @param telePhone
     * @return
     */
    public Msg register(String username, String password, String code, String telePhone) {

        logger.info("username:" + username + ",password:" + password + ",code:" + code + ",teltephone" + telePhone);
        String teleCode = (String) redisUtil.get(telePhone);
        if(!code.equals(teleCode)) {
            return Msg.fail().add("info","手机验证码错误");
        }
        List<User> user = userMapper.selectByUserName(username);
        if(user.size() > 0) {
            return Msg.fail().add("info","用户名重复");
        }
        User user1 = new User();
        password = Md5Utils.string2Md5(password);
        logger.info("注册功能password:" + password);
        user1.setPassword(password);
        user1.setTelephone(telePhone);
        user1.setUsername(username);
        user1.setType(1);
        user1.setStatus(1);
        user1.setSex(3);
        user1.setNickName(username);
        userMapper.insertUseGeneratedKeys(user1);
        return Msg.success();
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public Msg login(String username, String  password) {
        List<User> users = userMapper.selectByUserName(username);

        if(users.size() == 0 ) {
           return Msg.fail().add("info","用户名错误");
        }
        User user = users.get(0);
        logger.info("登录：" + user.toString());
        if(user.getStatus() == 0) {
            return Msg.fail().add("info","账号被禁用");
        }
        password = Md5Utils.string2Md5(password);
        if(password.equals(user.getPassword())) {
            String token = TokenUtils.token(user.getId(), user.getType());
            logger.info("token:" + token);
            redisUtil.setByTime(user.getId()+"",token);
            return Msg.success().add("user",user).add("token",token);
        }
        return Msg.fail().add("info","密码错误");
    }

    /**
     * 根据手机号更新密码
     * @param phone
     * @param code
     * @return
     */
    public Msg updatePasswordByPhone(String phone, Integer code) {
        String string = (String) redisUtil.get(phone);
        logger.info("code:" + string);
        Integer c = Integer.valueOf(string);
        if(code.equals(c)) {
            String password = Md5Utils.string2Md5("123456");
            userMapper.updatePasswordByPhone(phone,password);
            return Msg.success();
        }
        return Msg.fail().add("info","验证码错误");
    }

    /**
     * 根据主键查找用户
     * @param userId
     * @return
     */
    public User getUserInfo(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    /**
     * 修改简介
     * @param user
     * @return
     */
    public Msg updateUserInfo(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return Msg.success();
    }

    /**
     * 更新密码
     * @param id
     * @param password
     * @param newPassword
     * @return
     */
    public Msg updatePassword(Integer id, String password, String newPassword) {
        User user = userMapper.selectByPrimaryKey(id);
        password = Md5Utils.string2Md5(password);
        if(user.getPassword().equals(password)) {
            User user1 = new User();
            user1.setId(id);
            newPassword = Md5Utils.string2Md5(newPassword);
            user1.setPassword(newPassword);
            userMapper.updateByPrimaryKeySelective(user1);
            return  Msg.success();
        }
        return Msg.fail().add("info","旧密码错误");
    }

    /**
     * 分页查询用户列表
     * @return
     */
    public List<User> findListUserByPage() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    /**
     * 根据用户名搜索，并分页
     * @param username
     * @return
     */
    public List<User> searchUserByUsername(String username) {
        username = '%' + username + '%';
        logger.info("searchUserByUsername:" + username);
        List<User> users = userMapper.findUserByUserName(username);
        logger.info(users.toString());
        return users;
    }

    /**
     * 禁用，启用
     * @param status
     * @param id
     */
    public void updateUserStatusById(Integer status, Integer id) {
        logger.info("updateUserStatusById, status: " + status + ", id:" + id);
        int i = userMapper.updateUserStatusById(status, id);
    }

    /**
     * 更改权限
     * @param type
     * @param id
     */
    public void updateUserTypeById(Integer type, Integer id) {
        logger.info("updateUserTypeById: type:" + type + ",id:" + id);
        userMapper.updateUserTypeById(type,id);
    }

    /**
     * 通用分页代码
     * @param users
     * @param page
     * @return
     */
    private PageResult pageGeneral(List<User> users,int page) {
        PageHelper.startPage(page,10);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return PageUtils.getPageResult(userPageInfo);
    }


}
