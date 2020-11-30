package com.zimo.springbootstu.utils;

import com.alibaba.fastjson.JSONObject;
import com.zimo.springbootstu.interfac.BaseErrorInfoInterface;
import com.zimo.springbootstu.enums.CommonEnum;

public class ResultBody {

    // 响应代码
    private String code;
    // 响应消息
    private String message;
    // 响应结果
    private Object result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultBody success(Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(CommonEnum.SUCCESS.getResultCode());
        rb.setMessage(CommonEnum.SUCCESS.getResultMsg());
        rb.setResult(data);
        return rb;
    }

    /**
     * 失败
     * @param infoInterface
     * @return
     */
    public static ResultBody error(BaseErrorInfoInterface infoInterface) {
        ResultBody rb = new ResultBody();
        rb.setCode(infoInterface.getResultCode());
        rb.setMessage(infoInterface.getResultMsg());
        rb.setResult(null);
        return rb;
    }

    public static ResultBody error(String code, String msg) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(msg);
        rb.setResult(null);
        return rb;
    }

    public static ResultBody error(String msg) {
        ResultBody rb = new ResultBody();
        rb.setCode("-1");
        rb.setMessage(msg);
        rb.setResult(null);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSON(this).toString();
    }
}
