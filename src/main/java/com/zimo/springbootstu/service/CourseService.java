package com.zimo.springbootstu.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.mybatis.dao.CourseMapper;
import com.zimo.springbootstu.utils.PageResult;
import com.zimo.springbootstu.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    CourseMapper courseMapper;

    /**
     * 分页查找课程
     * @param page
     * @return
     */
    public PageResult<Course> findList(int page) {

        PageHelper.startPage(page,10);
        Example example = new Example(Course.class);
        List<Course> courses = courseMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(courses)) {
            return null;
        }
        PageInfo<Course> pageInfo = new PageInfo<>();
        logger.info(pageInfo.toString());
        return PageUtils.getPageResult(pageInfo)    ;
    }

    /**
     * 分类搜索
     * @param page
     * @param secCaId
     * @return
     */
    public PageResult<Course> findListBySecCaId(int page, Integer secCaId) {

        PageHelper.startPage(page, 10);
        Example example = new Example(Course.class);
        example.createCriteria().andEqualTo("secCaId",secCaId);
        List<Course> courses = courseMapper.selectByExample(example);
        PageInfo<Course> pageInfo = new PageInfo<>();
        logger.info(pageInfo.toString());
        return PageUtils.getPageResult(pageInfo);
    }

    /**
     * 查找热门，新品等的课程
     * @param otherTpye
     * @return
     */
    public List<Course> findListByOtherType(Integer otherTpye) {

        List<Course> courses = courseMapper.findListByOtherType(otherTpye);
        return courses;
    }

    /**
     * 智能补全
     * @param name
     * @return
     */
    public List<String> findListSearchByName(String name) {
        List<String> listNameBySearch = courseMapper.findListNameBySearch("%" + name + "%");
        return  listNameBySearch;
    }

    /**
     * 搜索
     * @param name
     * @return
     */
    public List<Course> findListCourseByName(String name) {
        List<Course> listCourseBySearch = courseMapper.findListCourseBySearch("%" + name + "%");
        return listCourseBySearch;
    }
}
