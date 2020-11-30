package com.zimo.springbootstu.utils;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Msg {

    private int code;
    private String msg;
    private Map<String,Object> extend = new HashMap<>();

    public Msg() {
        super();
    }

    public Msg(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public static Msg success() {
        return new Msg(100, "Successful！");
    }

    public static Msg fail() {
        return new Msg(200, "failed！");
    }

    /**
     * 追加数据
     *
     * @param key
     * @param value
     * @return
     */
    public Msg add(String key, Object value) {
        this.getExtend().put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return JSONObject.toJSON(this).toString();
    }
}
