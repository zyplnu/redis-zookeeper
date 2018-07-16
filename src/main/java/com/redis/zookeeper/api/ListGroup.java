package com.redis.zookeeper.api;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * 列出组中所有成员
 */
public class ListGroup extends ConnectionWatcher{

    private List<String> memberList = null;

    public void list(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        memberList = zooKeeper.getChildren(path , false);
        if (memberList.isEmpty()){
            System.out.println("none member.");
            System.exit(1);
        }
        for(String member : memberList){
            System.out.println(member);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ListGroup listGroup = new ListGroup();
        listGroup.connect("localhost:2181");
        listGroup.list("zk");
        listGroup.close();
    }
}
