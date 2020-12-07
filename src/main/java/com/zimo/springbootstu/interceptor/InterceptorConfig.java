package com.zimo.springbootstu.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("进来了拦截期");
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public com.zimo.springbootstu.interceptor.AuthorizationInterceptor authorizationInterceptor() {
        return new com.zimo.springbootstu.interceptor.AuthorizationInterceptor();
    }
}
