package com.redis.zookeeper.configService;

import com.redis.zookeeper.ConnectionWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class ChangedActiveKeyValueStore extends ConnectionWatcher {

    public static final Charset CHARSET = Charset.forName("UTF-8");
    public static final int MAX_RETRIES = 5;
    public static final long RETRY_PERIOD_SECONDS = 5;

    public void write(String path , String value) throws InterruptedException , KeeperException{
        int retries = 0;
        while (true){
            try {
                Stat stat = zooKeeper.exists(path , false);
                if(stat == null){
                    zooKeeper.create(path , value.getBytes(CHARSET) , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
                }else {
                    zooKeeper.setData(path , value.getBytes(CHARSET) , stat.getVersion());
                }
            } catch (KeeperException.SessionExpiredException e){
                throw e;
            } catch (KeeperException e){
                if(retries++ == MAX_RETRIES){
                    throw e;
                }
                TimeUnit.SECONDS.sleep(RETRY_PERIOD_SECONDS);
            }
        }
    }

    public String read(String path , Watcher watcher) throws KeeperException , InterruptedException{
        byte[] data = zooKeeper.getData(path , watcher , null);
        return new String(data , CHARSET);
    }

}
