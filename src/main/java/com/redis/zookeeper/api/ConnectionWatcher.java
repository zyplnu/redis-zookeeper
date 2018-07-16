package com.redis.zookeeper.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 用于连接zookeeper服务器端的辅助类
 */
public class ConnectionWatcher implements Watcher{

    private static final int SESSION_TIMEOUT = 5000;
    protected ZooKeeper zooKeeper;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        if(event.getState() == Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }

    public void connect(String hosts) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(hosts, SESSION_TIMEOUT , this);
        countDownLatch.await();
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }
}
