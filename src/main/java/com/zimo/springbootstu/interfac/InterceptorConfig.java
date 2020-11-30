package com.zimo.springbootstu.interfac;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public com.zimo.springbootstu.interfac.AuthorizationInterceptor authorizationInterceptor() {
        return new com.zimo.springbootstu.interfac.AuthorizationInterceptor();
    }
}
