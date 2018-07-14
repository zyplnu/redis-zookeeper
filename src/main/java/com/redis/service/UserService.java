package com.redis.service;

import com.redis.pojo.User;
import com.redis.util.CacheManager;

/**
 * 用户查询的服务类
 */
public class UserService {

    private CacheManager<User> cacheManager;

    public UserService() {
        //构造一个缓存管理器
        cacheManager = new CacheManager<User>();
    }

    /**
     * 1、首先查询缓存，是否存在该userId，如果存在，则直接返回结果
     * 2、如果缓存中不存在，则去数据库查询，同时更新缓存
     * @param userId
     * @return
     */
    public User getUserById(String userId){
        User result = cacheManager.getValue(userId);
        if(result != null){
            System.out.println("get from cache..." + userId);
            return result;
        }
        result = getFromDB(userId);
        if(result != null){
            cacheManager.addOrUpdatCache(userId , result);
        }
        return result;
    }

    public void reload(){
        cacheManager.evictCache();
    }

    private User getFromDB(String userId){
        System.out.println("real querying db..." + userId);
        return new User(userId);
    }
}
