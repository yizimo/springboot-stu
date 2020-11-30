package com.zimo.springbootstu.enums;

import com.zimo.springbootstu.interceptor.BaseErrorInfoInterface;

public enum CommonEnum implements BaseErrorInfoInterface {

    // 数据定义
    SUCCESS("200", "成功"),
    BODY_NOT_MATCH("400", "请求格式不规范"),
    NOT_FOUND("404", "找不到该资源"),
    INTERNAL_SERVER_ERROR("500","服务器错误");

    // 错误码
    private String resultCode;
    // 错误描述
    private String resultMsg;


    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
