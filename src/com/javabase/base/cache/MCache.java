package com.javabase.base.cache;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

/**
 * 
 * 自己的cache对象
 * 
 *  里面包含了一个的缓存cache文件对象.
 * 
 * @author bruce
 *
 */
public class MCache implements CacheEventListener {
	
	private String cacheName;
	
	private Cache cache;
	
	private String filePath;
	
	/**
	 * 文件存放的路径.
	 */
	public Map<String, File> selfCache = new HashMap<String, File>();

	/**
	 * 构建一个cache 对象.
	 * 
	 * @param cacheName
	 * @param cache
	 * @param cacheManager
	 */
	public MCache(String cacheName, Cache cache, CacheManager cacheManager) {
		this.cacheName = cacheName;
		this.cache = cache;
		cache.getCacheEventNotificationService().registerListener(this);
		filePath = cacheManager.getConfiguration().getDiskStoreConfiguration().getPath(); 
	}

	/**
	 * 通过key获值.
	 * 如果内存的cache中没有,那么就去文件中获取.
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		try {
			if (cache.get(key) != null) {
				return cache.get(key);
			} else {
				if (selfCache.get(key) != null) {
					File file = selfCache.get(key);
					InputStream is  =  new FileInputStream(file);
					DataInputStream dis = new DataInputStream(is);
					//class
					String className = dis.readUTF();
					WriteableObject wObject = (WriteableObject) Class.forName(className).newInstance();
					wObject.read(is);
					put(key, wObject);
					return wObject;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 放入 到cache中去.
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean put(String key, Object value) {
		try {
			cache.put(new Element(key, value));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element)
			throws CacheException {
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element)
			throws CacheException {
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element)
			throws CacheException {
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
	}

	/**
	 * 将文件放到磁盘上去.
	 */
	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		WriteableObject wObject = (WriteableObject) element.getObjectValue();
		String key = (String) element.getObjectKey();
		File file = new File(filePath + key + ".dat");
		selfCache.put((String) element.getObjectKey(), file);
		try {
			OutputStream outputStream = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(outputStream);
			try {
				dos.writeUTF(wObject.getClass().getName());
				wObject.write(outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
	}

	@Override
	public void dispose() {
		
	}

	public Object clone() {
		return this;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

}
