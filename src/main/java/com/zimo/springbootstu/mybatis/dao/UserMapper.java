package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.User;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends TkMapper<User> {

    // 根据用户查找用户
    List<User> selectByUserName(String username);
    // 更改用户状态
    int updateUserStatusById(@Param("status") Integer status, @Param("id") Integer id);
    // 更改权限
    int updateUserTypeById(@Param("type")int type, @Param("id") Integer id);
    // 搜索
    List<User> findUserByUserName(String username);
    // 验证码修改密码
    int updatePasswordByPhone(@Param("phone") String phone,
                              @Param("password") String password);
}
