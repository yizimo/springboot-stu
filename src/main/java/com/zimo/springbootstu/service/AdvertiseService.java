package com.zimo.springbootstu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zimo.springbootstu.bean.Advertise;
import com.zimo.springbootstu.mybatis.dao.AdvertiseMapper;
import com.zimo.springbootstu.utils.PageResult;
import com.zimo.springbootstu.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AdvertiseService {

    private static final Logger logger = LoggerFactory.getLogger(AdvertiseService.class);

    @Autowired
    AdvertiseMapper advertiseMapper;

    /**
     * 启动的广告列表
     * @return
     */
    public List<Advertise> getList() {
        Example example = new Example(Advertise.class);
        example.createCriteria().andEqualTo("status",1);
        List<Advertise> advertises = advertiseMapper.selectByExample(example);
        logger.info("getList:" + advertises.size());
        return advertises;
    }

    /**
     * 广告列表分页
     * @return
     */
    public PageResult getList(int page) {
        List<Advertise> advertises = advertiseMapper.selectAll();
        PageResult pageResult = pageByAdvertise(advertises, page);
        return pageResult;
    }

    /**
     * 分页模糊搜索广告
     * @param page
     * @param title
     * @return
     */
    public PageResult searchTitleByLimit(int page, String title) {
        title = '%' + title +'%';
        logger.info("page:" + page + ",title" + title);
        Example example = new Example(Advertise.class);
        example.createCriteria().andLike("title",title);
        List<Advertise> advertises = advertiseMapper.selectByExample(example);
        PageResult pageResult = pageByAdvertise(advertises, page);
        return pageResult;
    }

    /**
     * 插入广告
     * @param advertise
     */
    public void insertAdvertise(Advertise advertise) {
        advertise.setStatus(0);
        advertiseMapper.insert(advertise);
        logger.info("广告插入成功:" + advertise.getId());
    }

    /**
     * 更新广告
     * @param advertise
     */
    public void updateAdvertise(Advertise advertise) {
        advertiseMapper.updateByPrimaryKeySelective(advertise);
        logger.info("广告更新成功：");
    }

    /**
     * 删除广告
     * @param id
     */
    public void deleteAdvertise(Integer id) {
        advertiseMapper.deleteByPrimaryKey(id);
        logger.info("广告删除成功");
    }

    /**
     * 通用分页代码
     * @param advertises
     * @param page
     * @return
     */
    private PageResult pageByAdvertise(List<Advertise> advertises, int page) {
        PageHelper.startPage(page,10);
        PageInfo<Advertise> pageInfo = new PageInfo<>(advertises);
        return PageUtils.getPageResult(pageInfo);
    }
}
