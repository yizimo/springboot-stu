package com.zimo.springbootstu.mybatis.dao;

import com.zimo.springbootstu.bean.Comment;
import com.zimo.springbootstu.mybatis.TkMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper extends TkMapper<Comment> {
}
