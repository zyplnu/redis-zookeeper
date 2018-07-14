package com.redis.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 自己编写的cache测试类
 */
public class UserServiceTest {

    /**
     * 自定义CacheManager测试方法
     * @param args
     */
    /*public static void main(String[] args) {
        UserService userService = new UserService();
        userService.getUserById("001001");
        userService.getUserById("001001");

        //重置缓存
        userService.reload();
        System.out.println("after reload...");

        userService.getUserById("001001");
        userService.getUserById("001001");
    }*/

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserService2 userService2 = (UserService2) ctx.getBean("userService2");

        //第一次查询数据库
        System.out.println("first query...");
        userService2.getUserById("somebody");

        //第二次查询，查询缓存
        System.out.println("second query");
        userService2.getUserById("somebody");
    }
}
