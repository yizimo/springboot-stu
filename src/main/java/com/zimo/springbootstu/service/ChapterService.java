package com.zimo.springbootstu.service;

import com.zimo.springbootstu.mybatis.dao.ChapterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChapterService {
    @Autowired
    ChapterMapper chapterMapper;


}
