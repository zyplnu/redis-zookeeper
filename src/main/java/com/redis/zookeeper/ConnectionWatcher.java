package com.redis.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ConnectionWatcher implements Watcher {

    public static final int SESSION_TIMEOUT = 5000;

    protected ZooKeeper zooKeeper;

    CountDownLatch countDownLatch = new CountDownLatch(1);

    public void connect(String hosts) throws IOException , InterruptedException{
        zooKeeper = new ZooKeeper(hosts , SESSION_TIMEOUT , this);
        countDownLatch.await();
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getState() == Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }

    public void close() throws InterruptedException{
        zooKeeper.close();
    }
}
