package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends TkMapper<Category> {

    // 根据父类id 查找子类 集合
    List<Category> findListByParentId(Integer parentId);
    // 根据父类id 查找然后修改父类id
    void updateCategoryParentIdByParentId(@Param("parentId") Integer parentId,@Param("id") Integer id);

    // 搜索
    List<Category> searchListByCategoryName(String name);
}
