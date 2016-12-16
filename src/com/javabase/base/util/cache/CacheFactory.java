package com.javabase.base.util.cache;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存的实现.
 * @author admin
 * @param <T>
 */
public class CacheFactory<T> implements Cache<T>{
    
    /** 
     * 缓存数据索引 
     */  
    private final Map<String, CacheBean<T>> cache = new ConcurrentHashMap<String, CacheBean<T>>();  
      
    /** 
     * 缓存超时时间，单位：毫秒 
     */  
    private Long expired = 0L;  
      
    /**
     * 30分钟默认.
     */
    public CacheFactory() {  
        this(30 * 1000 * 60L);  
    }  
      
    public CacheFactory(Long expired) {  
        this.expired = expired;  
    }  
  
    @Override  
    public void putCache(String key, T target) {  
        synchronized (cache) {
            if (cache.containsKey(key)) {  
                cache.remove(key);  
            }  
            cache.put(key, new CacheBean<T>(target));    
        } 
    }  
  
    @Override  
    public T getCache(String key) {  
        synchronized (cache) {
            if (!this.exist(key)) {  
                return null;  
            }  
            return cache.get(key).getData();   
        }  
    }  
  
    @Override  
    public Boolean isExpired(String key) {  
        synchronized (cache) {
            if (!this.exist(key)) {  
                return null;  
            }  
            long currtime = new Date().getTime();  
            long lasttime = cache.get(key).getRefreshtime();  
            return (currtime - lasttime) > expired;
        }
    }  
  
    @Override  
    public void setExpired(Long millsec) {  
        synchronized (cache) {
            this.expired = millsec;  
        }
    }  
  
    @Override  
    public Boolean exist(String key) {  
        synchronized (cache) {
            return cache.containsKey(key);
        }  
    }  
    
}
