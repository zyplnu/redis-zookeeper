package com.redis.zookeeper.api;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;

/**
 * 模拟服务器加入zookeeper中的组
 */
public class JoinGroup extends ConnectionWatcher{

    public void join(String groupName , String memberName) throws KeeperException, InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        String createPath = zooKeeper.create(path , null , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL);
        System.out.println("Created:" + createPath);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect("localhost:2181");
        joinGroup.join("zk" , "pig");
        Thread.sleep(10000);
    }
}
