package com.redis.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.redis")
@EnableAspectJAutoProxy(proxyTargetClass = true)

public class ApplicationConfig {

    @Bean
    public User getUser(){
        return new User();
    }

}
