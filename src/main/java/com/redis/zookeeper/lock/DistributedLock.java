package com.redis.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 使用Zookeeper实现分布式锁，有两种方式：
 * 1、利用zookeeper中节点名的唯一性
 * 2、利用zookeeper中顺序节点实现共享锁，顺序号小的获取锁
 * 编着编着不想编了……
 */
public class DistributedLock{

    private ZooKeeper zk;
    private String root = "/locks";//根
    private String lockName;//竞争资源的标志
    private String waitNode;//等待前一个锁
    private String myZnode;//当前锁
    private CountDownLatch latch;//计数器
    private int sessionTimeout = 30000;
    private List<Exception> exceptions = new ArrayList<>();

}
