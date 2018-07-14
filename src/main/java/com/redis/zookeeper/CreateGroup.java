package com.redis.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper中创建一个group
 */
public class CreateGroup implements Watcher{

    public static final int SESSION_TIMEOUT = 5000;
    private ZooKeeper zooKeeper;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        if(event.getState() == Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }

    private void close() throws InterruptedException{
        zooKeeper.close();
    }

    private void create(String groupName) throws KeeperException , InterruptedException{
        String path = "/" + groupName;
        if(zooKeeper.exists(path , false) == null){
            zooKeeper.create(path , null , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
        }
        System.out.println("created:" + path);
    }

    private void connect(String hosts) throws IOException , InterruptedException{
        zooKeeper = new ZooKeeper(hosts , SESSION_TIMEOUT , this);
        countDownLatch.await();
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect("localhost");
        createGroup.create("zk");
        createGroup.close();
    }
}
