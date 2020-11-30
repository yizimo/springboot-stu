package com.zimo.springbootstu.service;

import com.zimo.springbootstu.bean.Advertise;
import com.zimo.springbootstu.mybatis.dao.AdvertiseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertiseService {

    private static final Logger logger = LoggerFactory.getLogger(AdvertiseService.class);

    @Autowired
    AdvertiseMapper advertiseMapper;

    /**
     * 广告列表
     * @return
     */
    public List<Advertise> getList() {
        return advertiseMapper.selectAll();
    }

    /**
     * 插入广告
     * @param advertise
     */
    public void insertAdvertise(Advertise advertise) {
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
}
