package com.redis.zookeeper.queue;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class Synchronizing extends TestMainClient {

    int size;
    String name;

    /**
     *
     * @param connectString
     */
    public Synchronizing(String connectString , String root , int size) throws UnknownHostException {
        super(connectString);
        this.root = root;
        this.size = size;
        if(zk != null){
            try {
                Stat stat = zk.exists(root , false);
                if(stat == null){
                    zk.create(root , new byte[0] , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        name = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
    }

    /**
     * 加入队列
     * @throws KeeperException
     * @throws InterruptedException
     */
    void addQueue() throws KeeperException, InterruptedException {
        zk.exists(root + "/start" , true);
        zk.create(root + "/" + name , new byte[0] , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL_SEQUENTIAL);
        synchronized (mutex){
            List<String> list = zk.getChildren(root , false);
            if(list.size() < this.size){
                mutex.wait();
            }else {
                zk.create(root + "/start" , new byte[0] , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
            }
        }
    }

    @Override
    public void process(WatchedEvent event){
        if (event.getPath().equals(root + "/start") && event.getType() == Event.EventType.NodeCreated){
            System.out.println("得到通知");
            super.process(event);
            doAction();
        }
    }

    public void doAction(){
        System.out.println("ok , begin...");
    }

    public static void main(String[] args) throws UnknownHostException, KeeperException, InterruptedException {
        String conn = "localhost:2181";
        int size = 1;
        Synchronizing synchronizing = new Synchronizing(conn , "/synchronizing" , size);
        synchronizing.addQueue();
    }
}
