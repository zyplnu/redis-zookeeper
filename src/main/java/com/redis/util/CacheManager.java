package com.redis.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存类
 * @param <T>
 */
public class CacheManager<T> {

    private Map<String , T> cache = new ConcurrentHashMap<String , T>();

    public T getValue(Object key){
        return cache.get(key);
    }

    public void addOrUpdatCache(String key , T value){
        cache.put(key , value);
    }

    public void evictCache(String key){
        if(cache.containsKey(key)){
            cache.remove(key);
        }
    }

    public void evictCache(){
        cache.clear();
    }

}
