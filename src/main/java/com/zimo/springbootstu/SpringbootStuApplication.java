package com.zimo.springbootstu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.zimo.springbootstu.mybatis.dao")
@EnableScheduling
public class SpringbootStuApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStuApplication.class, args);
    }

}
