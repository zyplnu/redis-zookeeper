package com.redis.test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                countDownLatch.countDown();
                System.out.println(2);
                countDownLatch.countDown();
            }
        }).start();
//        countDownLatch.await();
        System.out.println(3);
    }

}
