package com.redis.aop;

import com.redis.advice.GreetingAroundAdvice;
import com.redis.advice.GreetingBeforeAndAfterAdvice;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {

    @Test
    public void client1(){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new GreetingImpl());
//        proxyFactory.addAdvice(new GreetingBeforeAndAfterAdvice());
        proxyFactory.addAdvice(new GreetingAroundAdvice());
        Greeting greeting = (Greeting) proxyFactory.getProxy();
        greeting.sayHello("jack");
    }

    @Test
    public void client2(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        Greeting greeting = (Greeting) ctx.getBean("greetingProxy");
        greeting.sayHello("jack");
    }

    @Test
    public void client3(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/beans.xml");
        GreetingImpl greeting = (GreetingImpl) ctx.getBean("greetingProxy");
        greeting.sayHello("jack");

        Love love = (Love) greeting;
        love.display("tom");
    }

    @Test
    public void client4(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/beans.xml");
        Math math = (Math) ctx.getBean("math");
        int n1 = 10 , n2 = 20;
        math.add(n1 , n2);
    }

    @Test
    public void client5(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/beans.xml");
        Math math = ctx.getBean("math" , Math.class);
        int n1 = 10 , n2 = 20;
        math.add(n1 , n2);
    }

    @Test
    public void client6(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Math math = ctx.getBean("math" , Math.class);
        int n1 = 10 , n2 = 20;
        math.add(n1 , n2);
        User user = (User) ctx.getBean("getUser");
        user.show();
    }

}
