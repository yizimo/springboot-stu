package com.zimo.springbootstu.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.Column;
import javax.persistence.Id;
@Data
public class Advertise {
    @Id
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    // 主题
    private String title;
    // 图片路径
    private String url;
    // 0 下架， 1 上架
    private Integer status;
}