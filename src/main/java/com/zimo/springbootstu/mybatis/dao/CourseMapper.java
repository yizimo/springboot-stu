package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.Course;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper extends TkMapper<Course> {

    // 查找标记为热门等的课程
    List<Course> findListByOtherType(Integer otherType);

    // 智能补全
    List<String> findListNameBySearch(String name);

    // 搜索结果
    List<Course> findListCourseBySearch(String name);

    //  根据分类id 查找文章
    List<Course> findListCourseByCategoryId(Integer categoryId);

    // 二级分类查找
    List<Course> findListCourseByCateSecId(Integer SecCaId);

    // 删除二级分类，修改课程的所属二级分类
    void updateCourseSecIdBySecId(@Param("secCaId") Integer secCaId,@Param("id")Integer id);

    // 删除三级分类，修改课程所属三级分类
    void updateCourseCategoryIdByCategoryId(@Param("categoryId") Integer categoryId, @Param("id") Integer id);

    List<Integer> findUserHaveCourse(@Param("userId") Integer userId,
                                     @Param("id") Integer id);

    // 查找用户的课程
    List<Course> findUserCourseByUserId(Integer id);

    // 查找用户课程根据名字搜索
    List<Course> searchUserCourseByName(@Param("id") Integer id,
                                        @Param("name" ) String name);

    // 判断用户是否阅读过
    List<Integer> userIsReadLesson(@Param("userId") Integer userId,
                                   @Param("lessonId") Integer lessonId);
}
