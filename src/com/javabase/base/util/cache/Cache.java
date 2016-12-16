package com.javabase.base.util.cache;

/**
 * Cache 接口.
 * @author admin
 */
public interface Cache<T> {
    
    /** 
     * 存放缓存数据 
     *  
     * @param key 缓存key 
     * @param target 新数据 
     */  
    public void putCache(String key, T target);  
      
    /** 
     * 获取缓存 
     *  
     * @param key 缓存key 
     * @return 缓存数据 
     */  
    public T getCache(String key);  
      
    /** 
     * 判断缓存是否过期 
     *  
     * @param key 缓存key 
     * @return 如果缓存过期返回true， 否则返回false 
     */  
    public Boolean isExpired(String key);  
      
    /** 
     * 设置缓存过期时间 
     *  
     * @param key 缓存key 
     * @param millsec 缓存过期时间，单位：毫秒 
     */  
    public void setExpired(Long millsec);  
      
    /** 
     * 是否存在缓存对象 
     *  
     * @param key 缓存key 
     * @return 存在返回true，不存在返回false 
     */  
    public Boolean exist(String key); 
    
}
