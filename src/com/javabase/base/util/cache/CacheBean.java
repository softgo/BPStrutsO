package com.javabase.base.util.cache;

import java.util.Date;

/**
 * 缓存对象
 * @author admin
 */
public class CacheBean<T> {
    
    /** 
     * 缓存的数据 
     */  
    private T data;  
      
    /** 
     * 最后刷新时间 
     */  
    private long refreshtime;  
      
    public CacheBean(T data) {  
        this(data, new Date().getTime());  
    }  
      
    public CacheBean(T data, long refreshtime) {  
        this.data = data;  
        this.refreshtime = refreshtime;  
    }  
      
    public T getData() {  
        return data;  
    }  
      
    public void setData(T data) {  
        this.data = data;  
    }  
      
    public long getRefreshtime() {  
        return refreshtime;  
    }  
      
    public void setRefreshtime(long refreshtime) {  
        this.refreshtime = refreshtime;  
    }  
    
}
