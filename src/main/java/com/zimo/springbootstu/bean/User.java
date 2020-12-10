package com.zimo.springbootstu.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class User {

    @Id
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String username;
    private String telephone;
    private String password;
    private String info;
    private String nickName;    // 昵称
    private Integer sex;  // 0 保密， 1 男， 2 女
    private Integer type; // 1 用户 2  教师 3  管理员
    private Integer status;   // 状态   0 禁用，  1 启用
    private String url;
}
