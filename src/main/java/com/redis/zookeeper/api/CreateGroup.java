package com.redis.zookeeper.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

/**
 * api包用来模拟服务器列表服务
 */
public class CreateGroup implements Watcher {

    private static final int SESSION_TIMEOUT = 5000;
    private ZooKeeper zooKeeper = null;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        if(event.getState() == SyncConnected){
            countDownLatch.countDown();
        }
    }

    private void close() throws InterruptedException {
        zooKeeper.close();
    }

    private void connect(String hosts) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(hosts , SESSION_TIMEOUT , this);
        countDownLatch.await();
    }

    private void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        if(zooKeeper.exists(path , false) == null){
            zooKeeper.create(path , null , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT_SEQUENTIAL);
        }
        System.out.println("Created:" + path);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect("localhost:2181");
        createGroup.create("test");
    }
}
