package com.redis.zookeeper.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Locks extends TestMainClient {

    String myZnode;

    public Locks(String connectString , String root) throws KeeperException, InterruptedException {
        super(connectString);
        this.root = root;
        if(zk != null){
            Stat stat = zk.exists(root , false);
            if(stat == null){
                zk.create(root , new byte[0] , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
            }
        }
    }

    void getLock() throws KeeperException , InterruptedException{
        List<String> list = zk.getChildren(root , false);
        String[] nodes = list.toArray(new String[list.size()]);
        Arrays.sort(nodes);
        if(myZnode.equals(root + "/" + nodes[0])){
            doAction();
        }else {
            waitForLock(nodes[0]);
        }
    }

    void waitForLock(String lower) throws InterruptedException , KeeperException{
        Stat stat = zk.exists(root + "/" + lower , true);
        if(stat != null){
            mutex.wait();
        }else {
            getLock();
        }
    }

    void check() throws InterruptedException , KeeperException{
        myZnode = zk.create(root + "/lock_" , new byte[0] , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL_SEQUENTIAL);
        getLock();
    }

    @Override
    public void process(WatchedEvent event){
        if(event.getType() == Event.EventType.NodeDataChanged){
            System.out.println("得到通知");
            super.process(event);
            doAction();
        }
    }

    private void doAction(){
        System.out.println("同步队列已经得到同步，可以开始执行后面的任务了");
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        String connectString = "localhost:2181";
        Locks locks = new Locks(connectString , "/locks");
        locks.check();
    }

}
