package com.redis.zookeeper;

import com.redis.zookeeper.lock.TestMainClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Zookeeper的leader选举
 */
public class LeaderElection extends TestMainClient {

    public LeaderElection(String connectString , String root) throws KeeperException, InterruptedException {
        super(connectString);
        this.root = root;
        if(zk != null){
            Stat stat = zk.exists(root , false);
            if(stat == null){
                zk.create(root , new byte[0] , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
            }
        }
    }

    @Override
    public void process(WatchedEvent event){
        if (event.getPath().equals(root + "/leader") && event.getType() == Event.EventType.NodeCreated){
            System.out.println("得到通知");
            super.process(event);
            following();
        }
    }

    void leading(){
        System.out.println("成为领导者");
    }

    void following(){
        System.out.println("成为组成员");
    }

    void findLeader() throws KeeperException, InterruptedException, UnknownHostException {
        byte[] leader = null;
        try{
            leader = zk.getData(root + "/leader" , true , null);
        } catch (KeeperException e){
            if(e instanceof KeeperException.NoNodeException){

            }else {
                throw e;
            }
        }
        if(leader != null){
            following();
        }else {
            String newLeader = null;
            byte[] localhost = InetAddress.getLocalHost().getAddress();
            newLeader = zk.create(root + "/leader" , localhost , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL);
            if(newLeader != null){
                leading();
            }else {
                mutex.wait();
            }
        }
    }

    public static void main(String[] args) throws KeeperException, InterruptedException, UnknownHostException {
        String connectString = "localhost:2181";
        LeaderElection leaderElection = new LeaderElection(connectString , "/GroupMembers");
        leaderElection.findLeader();

    }
}
