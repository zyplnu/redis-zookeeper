package com.redis.zookeeper.api;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * 删除组及组成员
 */
public class DeleteGroup extends ConnectionWatcher{

    private List<String> groupList = null;

    public void delete(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        groupList = zooKeeper.getChildren(path , false);
        if(groupList == null){
            zooKeeper.delete(path , -1);
            System.out.println("none child");
            System.exit(1);
        }
        for(String member : groupList){
            zooKeeper.delete(path + "/" + member , -1);
            System.out.println(member + " deleted...");
        }
        zooKeeper.delete(path , -1);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect("localhost:2181");
        deleteGroup.delete("zk");
        deleteGroup.close();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("haah");
            }
        }).interrupt();
    }

}
