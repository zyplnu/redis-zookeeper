package com.redis.zookeeper.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用来测试线程中interrupt（）的类
 */
public class Interruption implements Runnable{
    private volatile static boolean on = false;
    @Override
    public void run() {
        while (!on){
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                System.out.println("caught execption:" + e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Interruption() , "interruputin");
        thread.start();
        Thread.sleep(1000);
        Interruption.on = true;
        thread.interrupt();
        System.out.println("main end");

    }
}
