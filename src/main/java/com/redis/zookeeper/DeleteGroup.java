package com.redis.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

public class DeleteGroup extends ConnectionWatcher {

    public void delete(String groupName) throws InterruptedException{
        String path = "/" + groupName;
        List<String> children;
        try {
            children = zooKeeper.getChildren(path , false);
            for(String child : children){
                zooKeeper.delete(path + "/" + child , -1);
            }
            zooKeeper.delete(path , -1);
        } catch (KeeperException ex){
            System.out.printf("Group %s does not exist\n" , groupName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect("127.0.0.1");
        deleteGroup.delete("zk");
        deleteGroup.close();
    }

}
