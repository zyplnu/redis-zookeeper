package com.redis.aop;

import org.springframework.stereotype.Component;

@Component
public class Math {

    public int add(int n1 , int n2){
        int result = n1 + n2;
        System.out.println("add:" + result);
        return result;
    }

}
