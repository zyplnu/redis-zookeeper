package com.redis.service;

import com.redis.pojo.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 使用spring的cache，不需要自定义cache管理器
 */
@Service
public class UserService2 {

    //使用一个名为users的缓存
    @Cacheable(cacheNames = "users")
    @CacheEvict(cacheNames = "users" , allEntries = true)
    public User getUserById(String userId){
        //方法内部实现不考虑缓存器，直接实现业务
        System.out.println("real query user." + userId);
        return getFromDB(userId);
    }

    public User getFromDB(String userId){
        System.out.println("real querying db..." + userId);
        return new User(userId);
    }

}
