package com.zimo.springbootstu.service;

import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.mybatis.dao.UserMapper;
import com.zimo.springbootstu.utils.Md5Utils;
import com.zimo.springbootstu.utils.Msg;
import com.zimo.springbootstu.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 注册
     * @param username
     * @param password
     * @param code
     * @param telePhone
     * @return
     */
    public Msg register(String username, String password, String code, String telePhone) {

        String teleCode = (String) redisUtil.get(telePhone);
        if(code.equals(teleCode)) {
            Msg.fail().add("info","手机验证码错误");
        }
        User user = userMapper.selectByUserName(username);
        if(user != null) {
            Msg.fail().add("info","用户名重复");
        }
        User user1 = new User();
        password = Md5Utils.string2Md5(password);
        logger.info("注册功能password:" + password);
        user1.setPassword(password);
        user1.setTelephone(telePhone);
        user1.setUsername(username);
        userMapper.insert(user);
        return Msg.success();
    }


}
