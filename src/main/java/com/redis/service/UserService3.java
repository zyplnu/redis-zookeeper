package com.redis.service;

import com.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service(value = "initUserService")
public class UserService3 {

    private Map<Integer , User> users = new HashMap<>();

    {
        users.put(1 , new User("1" , "w1" , 20));
        users.put(2 , new User("2" , "w2" , 30));
    }

    private CacheManager cacheManager;

    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @PostConstruct
    public void setup(){
        Cache cache = cacheManager.getCache("users");
        for(Integer key : users.keySet()){
            cache.put(key , users.get(key));
        }
    }

    @Cacheable(value = "users")
    public User getUser(int id){
        System.out.println("User with id: " + id + " requested");
        return users.get(id);
    }
}
