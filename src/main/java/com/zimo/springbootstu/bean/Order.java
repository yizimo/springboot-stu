package com.zimo.springbootstu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private Integer id;
    private Integer courseId;
    private double money;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;
    private String oid;    // 订单号
    private Integer type;   // 状态

}
