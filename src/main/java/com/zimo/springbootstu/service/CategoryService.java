package com.zimo.springbootstu.service;

import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.mybatis.dao.CategoryMapper;
import com.zimo.springbootstu.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 首页分类展示
     * @param categoryParentId
     * @return
     */
    public List<Category> findListByParentId(Integer categoryParentId) {
        List<Category> categories = categoryMapper.findListByParentId(categoryParentId);
        for (Category category : categories) {
            category.setCategoryList(categoryMapper.findListByParentId(category.getId()));
        }
        return categories;
    }
}
