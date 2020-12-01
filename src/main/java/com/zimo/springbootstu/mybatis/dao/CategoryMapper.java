package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends TkMapper<Category> {

    // 根据父类id 查找子类 集合
    List<Category> findListByParentId(Integer parentId);
}
