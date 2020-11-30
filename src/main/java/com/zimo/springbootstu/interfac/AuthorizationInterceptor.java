package com.zimo.springbootstu.interfac;

import com.zimo.springbootstu.exception.ZimoException;
import com.zimo.springbootstu.utils.RedisUtil;
import com.zimo.springbootstu.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,X-Nideshop-Token,X-URL-PATH");
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));

        String token = request.getHeader("token");

        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(Token.class)) {
            Token annotation = method.getAnnotation(Token.class);
            if(annotation != null) {
                logger.info("token:" + token);
                if(token == null || "".equals(token)) {
                    throw new ZimoException("-1","不存在token");
                }
                // 校验token 正确性
                String username = TokenUtils.getInfo(token, "username");
                logger.info("token 中 username:" + username);
                if("" == username)  {
                    throw new ZimoException("-1","token校验失败");
                }
                String value = redisUtil.get(username,String.class);
                if(value == null || "".equals(value)) {
                    throw new ZimoException("-1","token 不存在");
                }
                if(!token.equals(value)) {
                    throw new ZimoException("-1","token失效");
                }
                if(method.isAnnotationPresent(UserType.class)) {
                    UserType annotation1 = method.getAnnotation(UserType.class);
                    if (annotation1 != null) {
                        Integer perNum = Integer.valueOf(TokenUtils.getInfo(token, "perNum"));
                        String validate = annotation1.validate();
                        String perN = perNum + "";
                        if (validate.equals(perN)) {
                            return true;
                        }
                        throw new ZimoException("-1","权限不够");
                    }

                }
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

}
