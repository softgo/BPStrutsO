package com.javabase.base.cache;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javabase.base.util.PropertiesUtils;

/**
 * 缓存的内存管理
 * 
 * @author bruce.
 *
 */
public class MCacheManager {
	
	private static Logger logger = LoggerFactory.getLogger(MCacheManager.class.getName());
	
	/**
	 * 存放自己编写的Mcache的对象.
	 */
	private Map<Object, MCache> cacheMap = new HashMap<Object, MCache>();
	
	private static String CACHE_PATH = "java.io.tmpdir";
	
	private static String MAX_MEMORY = "512m";
	
	private static final String BLOCK_MAX_MEMORY = "512m";

	private static MCacheManager instance = null;
	
	private Properties props = null;
	
	private CacheManager cacheManager = null;

	//内存中的缓存.
	private Cache memoryCache = null;
	
	private MCache dataCache = null;
	
	/**
	 * 移除的策略.
	 */
	private static final String removePolicy = "LFU";
	
	public static MCacheManager getInstance() {
		if (instance == null) {
			synchronized (MCacheManager.class) {
				if (instance == null) {
					instance = new MCacheManager();
				}
			}
		}
		return instance;
	}

	private MCacheManager() {
		super();
		double maxMemory = (Runtime.getRuntime().maxMemory() * 0.4) / (1024 * 1024);// m
		MAX_MEMORY = maxMemory + "";
	}

	/**
	 * 内存缓存.
	 * @return
	 */
	public Cache memoryCache() {
		if (memoryCache == null) {
			synchronized (this) {
				if (memoryCache == null) {
					String maxMemory = getProperty("block.cache.max.memory",BLOCK_MAX_MEMORY);
					memoryCache = getCache("__memoryCache__", maxMemory, false);
				}
			}
		}
		return memoryCache;
	}

	/**
	 * 磁盘缓存.
	 * @param cacheName
	 * @param cache
	 * @param cacheManager
	 * @return
	 */
	public MCache dataCache(String cacheName ,Cache cache , CacheManager cacheManager) {
		if (dataCache == null) {
			synchronized (this) {
				if (dataCache == null) {
					dataCache = new MCache(cacheName, cache, cacheManager);
				}
			}
		}
		return dataCache;
	}
	
	/**
	 * 获得 Cache
	 * @param cacheName
	 * @param maxMemory
	 * @return
	 */
	public Cache getCache(String cacheName, String maxMemory ) {
		Cache cache = getCacheManager().getCache(cacheName);
		if (cache == null) {
			synchronized (this) {
				cache = getCacheManager().getCache(cacheName);
				if (cache == null) {
					CacheConfiguration cacheConfig = new CacheConfiguration();
					cacheConfig.name(cacheName);
					cacheConfig.eternal(false);
					String removeTag = getProperty("cache.remove.tag", removePolicy);
					cacheConfig.setMemoryStoreEvictionPolicy(removeTag);
					cacheConfig.setOverflowToDisk(false);
					cacheConfig.setDiskPersistent(false);
					cacheConfig.setMaxBytesLocalHeap(maxMemory);
					cache = new Cache(cacheConfig);
					getCacheManager().addCache(cache);
				}
			}
		}
		return cache;
	}
	
	/**
	 * 获得Cache
	 * @param cacheName
	 * @param maxMemory
	 * @param saveDisk
	 * @return
	 */
	private Cache getCache(String cacheName, String maxMemory, boolean saveDisk) {
		Cache cache = getCacheManager().getCache(cacheName);
		if (cache == null) {
			synchronized (this) {
				cache = getCacheManager().getCache(cacheName);
				if (cache == null) {
					CacheConfiguration cacheConfig = new CacheConfiguration();
					cacheConfig.name(cacheName);
					cacheConfig.eternal(false);
					String removeTag = getProperty("cache.remove.tag", removePolicy);
					cacheConfig.setMemoryStoreEvictionPolicy(removeTag);
					cacheConfig.setOverflowToDisk(saveDisk);
					cacheConfig.setDiskPersistent(false);
					cacheConfig.setMaxBytesLocalHeap(maxMemory);
					cache = new Cache(cacheConfig);
					getCacheManager().addCache(cache);
				}
			}
		}
		return cache;
	}

	/**
	 * 获得CacheManager.
	 * @return
	 */
	public CacheManager getCacheManager() {
		if (cacheManager == null) {
			synchronized (this) {
				if (cacheManager == null) {
					Configuration configuration = new Configuration();
					DiskStoreConfiguration dscg = new DiskStoreConfiguration();
					String cacheFilePath = getProperty("cache.path.disk", CACHE_PATH);
					File file = new File(cacheFilePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					CACHE_PATH = cacheFilePath;
					dscg.setPath(cacheFilePath);
					configuration.diskStore(dscg);
					configuration.setMaxBytesLocalHeap(getProperty("cache.manager.max.memory", MAX_MEMORY));
					configuration.setDynamicConfig(true);
					configuration.setMonitoring("autodetect");
					configuration.setUpdateCheck(false);
					cacheManager = new CacheManager(configuration);
				}
			}
		}
		return cacheManager;
	}
	

	/**
	 * 添加
	 * @param mCache
	 */
	public void addCache(MCache mCache){
		cacheMap.put(mCache.getCacheName(), mCache);
	}

	/**
	 * 获得.
	 * @param cacheName
	 * @return
	 */
	public MCache get(String cacheName){
		if (cacheMap.containsKey(cacheName)) {
			return (MCache) cacheMap.get(cacheName);
		}else {
			return null;
		}
	}

	private String getProperty(String key, String defaultValue) {
		if (props == null) {
			synchronized (this) {
				if (props == null) {
					try {
						props = PropertiesUtils.getProperties("/configPros/cache.properties");
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		return props.getProperty(key, defaultValue);
	}

	
	private boolean deleteCache(){
		File file = new File(CACHE_PATH);
		File[] folderFiles  = file.listFiles();
		for (File f : folderFiles) {
			f.delete();
		}
		return true;
	}
	
}
