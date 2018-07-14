package com.redis.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;

public class JoinGroup extends ConnectionWatcher {

    public void join(String groupName , String memberName) throws KeeperException , InterruptedException{
        String path = "/" + groupName + "/" + memberName;
        String createPath = zooKeeper.create(path , null , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL);
        System.out.println("created:" + createPath);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect("127.0.0.1");
        joinGroup.join("zk" , "cow");
        joinGroup.join("zk" , "goat");
        joinGroup.join("zk" , "duck");
        Thread.sleep(Long.MAX_VALUE);
    }

}
