package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends TkMapper<User> {

    User selectByUserName(String username);
}
