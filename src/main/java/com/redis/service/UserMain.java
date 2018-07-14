package com.redis.service;

import com.redis.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserMain {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserService3 userService3 = (UserService3) ctx.getBean("initUserService");

        User u1 = userService3.getUser(1);
        System.out.println(u1);

        User u2 = userService3.getUser(2);
        System.out.println(u2);

    }

}
