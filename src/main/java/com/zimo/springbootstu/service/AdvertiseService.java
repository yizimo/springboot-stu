package com.zimo.springbootstu.service;

import com.zimo.springbootstu.mybatis.dao.AdvertiseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertiseService {

    @Autowired
    AdvertiseMapper advertiseMapper;


}
