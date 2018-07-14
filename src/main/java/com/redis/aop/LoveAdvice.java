package com.redis.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;

@Component
public class LoveAdvice extends DelegatingIntroductionInterceptor implements Love {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable{
        return super.invoke(invocation);
    }

    @Override
    public void display(String name) {
        System.out.println("You are my heart:" + name);
    }
}
