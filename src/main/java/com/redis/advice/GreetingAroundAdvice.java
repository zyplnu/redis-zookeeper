package com.redis.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

@Component
public class GreetingAroundAdvice implements MethodInterceptor {

    private void before(){
        System.out.println("Before");
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        before();
        Object result = invocation.proceed();
        after();
        return result;
    }

    private void after(){
        System.out.println("After");
    }
}
