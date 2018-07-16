package com.redis.zookeeper.queue;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * 建立zookeeper服务连接的辅助类
 */
public class TestMainClient implements Watcher{

    protected static ZooKeeper zk = null;
    protected static Integer mutex;
    int sessionTimeout = 10000;
    protected String root;

    public TestMainClient(String connectString) {
        if(zk == null){
            System.out.println("创建一个新连接");
            try {
                zk = new ZooKeeper(connectString , sessionTimeout , this);
                mutex = new Integer(-1);
            } catch (IOException e) {
                zk = null;
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        synchronized (mutex){
            mutex.notify();
        }
    }
}
