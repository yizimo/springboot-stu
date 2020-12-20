package com.zimo.springbootstu.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;


@Data
public class Category {

    @Id
    @Column(name = "id")
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    // 分类名字
    private String name;
    // 父类id， 一级分了默认父类id 为0
    private Integer parentId;
    private List<Category> categoryList;
    private List<Course> courses;
    private Category category;

}
