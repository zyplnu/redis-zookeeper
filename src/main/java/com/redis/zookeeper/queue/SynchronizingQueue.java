package com.redis.zookeeper.queue;

import com.redis.zookeeper.lock.TestMainClient;
import org.apache.log4j.Logger;

/**
 *Zookeeper的同步队列
 */
public class SynchronizingQueue extends TestMainClient{

    int size;
    String name;
    public static final Logger logger = Logger.getLogger(SynchronizingQueue.class);

    /**
     *
     * @param connectString
     * @param root
     * @param size
     */
    public SynchronizingQueue(String connectString , String root , int size) {
        super(connectString);

    }
}
