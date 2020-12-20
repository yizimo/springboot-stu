package com.zimo.springbootstu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimo.springbootstu.bean.Category;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.mybatis.dao.CategoryMapper;
import com.zimo.springbootstu.mybatis.dao.CourseMapper;
import com.zimo.springbootstu.utils.Msg;
import com.zimo.springbootstu.utils.PageResult;
import com.zimo.springbootstu.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CourseMapper courseMapper;

    /**
     * 根据id 查找分类
     * @param id
     * @return
     */
    public Category findCategoryById(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        return category;
    }

    /**
     *  根据父类id 查找分类
     * @param parentId
     * @return
     */
    public List<Category> findListCategoryByParentIdAdmin(Integer parentId) {
        List<Category> categories = categoryMapper.findListByParentId(parentId);
        return categories;
    }

    /**
     * 根据父类id 查找分类
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

    /**
     * 展示下级分类的课程
     * @param id
     * @return
     */
    public List<Category> findListAndCourseById(Integer id) {
        // 查找下级分类
        List<Category> categories = categoryMapper.findListByParentId(id);

        // 下级分类填充课程
        for (Category category : categories) {
            List<Course> courses = courseMapper.findListCourseByCategoryId(category.getId());
            category.setCourses(courses.subList(0,courses.size() > 5 ? 5 : courses.size()));
        }
        return categories;
    }


    /**
     * 分类添加
     * @param category
     */
    public void insertCategory(Category category) {
        int insert = categoryMapper.insert(category);
        log.info(category.toString());
        return ;
    }

    /**
     *
     * @param id    主键
     * @param type 1   删除一级分类， 2 删除二级分类，  3 删除三级分类
     * @param parentId    父类id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Msg deleteCategory(Integer id, Integer type,Integer parentId) {

        List<Category> categoryList = categoryMapper.findListByParentId(parentId);
        log.info("分类长度：" + categoryList.size());
        if(categoryList.size() <= 1) {
            return Msg.fail().add("info","分类最少存在一个");
        }
        for (Category category : categoryList) {
            if(!category.getId().equals(id)) {
                // 修改待删除分类的下属分类的所属上级分类
                if(type == 1 || type == 2) {
                    categoryMapper.updateCategoryParentIdByParentId(category.getId(),id);
                }
                //  二级分类删除，修改课程所属该二级分类的二级分类id
                if(type == 2) {
                    courseMapper.updateCourseSecIdBySecId(category.getId(),id);
                } else if(type == 3) {
                    // 三级分类删除，修改其所拥有课程的分类id
                    courseMapper.updateCourseCategoryIdByCategoryId(category.getId(),id);
                }
                categoryMapper.deleteByPrimaryKey(id);
            }
        }
        return Msg.success();
    }


    /**
     * 三级分类按级排列 分页
     * @param parentId
     * @return
     */
    public List<Category> findListCategoryByParentId(Integer parentId) {
        List<Category> listByParentId = categoryMapper.findListByParentId(parentId);
        for (Category category : listByParentId) {
            List<Category> parentId1 = categoryMapper.findListByParentId(category.getId());
            for (Category category1 : parentId1) {
                List<Category> listByParentId1 = categoryMapper.findListByParentId(category1.getId());
                category1.setCategoryList(listByParentId1);
            }
            category.setCategoryList(parentId1);
        }
        return listByParentId;
    }

    /**
     * 分页搜索
     * @param name
     * @param page
     * @return
     */
    public PageResult searchListByCategoryName(String name, int page) {
        name = '%' + name + '%';
        List<Category> categories = categoryMapper.searchListByCategoryName(name);
        PageResult pageResult = categoryListPageResult(categories, page);
        return pageResult;
    }

    /**
     * 分类修改
     * @param category
     */
    public void updateCategory(Category category) {
        int i = categoryMapper.updateByPrimaryKeySelective(category);
    }



    /**
     * 通用分页代码
     * @param list
     * @param page
     * @return
     */
    private PageResult categoryListPageResult(List<Category> list, int page) {
        PageHelper.startPage(page,10);
        PageInfo<Category> categoryPageInfo = new PageInfo<>(list);
        return PageUtils.getPageResult(categoryPageInfo);
    }





}
