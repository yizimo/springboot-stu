package com.zimo.springbootstu.exception;

import com.zimo.springbootstu.interfac.BaseErrorInfoInterface;

public class ZimoException extends RuntimeException{

    // 错误码
    protected String errorCode;
    // 错误描述
    protected String errorMsg;

    public ZimoException() {
        super();
    }

    public ZimoException(BaseErrorInfoInterface baseErrorInfoInterface) {
        super(baseErrorInfoInterface.getResultCode());
    }

    public ZimoException(BaseErrorInfoInterface baseErrorInfoInterface, Throwable cause) {
        super(baseErrorInfoInterface.getResultCode(),cause);
        this.errorCode = baseErrorInfoInterface.getResultCode();
        this.errorMsg = baseErrorInfoInterface.getResultMsg();
    }

    public ZimoException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public ZimoException(String errorCode,String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ZimoException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
