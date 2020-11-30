package com.zimo.springbootstu.controller;

import com.zimo.springbootstu.enums.CommonEnum;
import com.zimo.springbootstu.utils.ResultBody;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotFoundController implements ErrorController {
    @Override
    public String getErrorPath() {

        return "/error";
    }

    @RequestMapping(value = {"/error"})
    @ResponseBody
    public ResultBody error(HttpServletRequest request) {

        return ResultBody.error(CommonEnum.NOT_FOUND);
    }
}
