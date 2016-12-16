package com.javabase.base.cache;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.javabase.base.util.PropertiesUtils;

/**
 * 磁盘cache的实现
 * 		将所有的缓存文件都存放在磁盘上,使用的时候再去磁盘上取得.
 * @author bruce.
 *
 */
public class DiskCache {

	/**
	 * 磁盘缓存标记
	 */
	private static Map<String, File> diskCache = new ConcurrentHashMap<String, File>();
	
	/**
	 * 已经使用的标记 
	 */
	private static Map<String, Integer> usedMap = new HashMap<String, Integer>();
	
	private static LinkedList<String> putOrder = new LinkedList<String>();

	/**
	 * 总的磁盘大小
	 */
	private static long Total_Disk_Size = 10*1024*1024*1024 ;
	
	/**
	 * 大小
	 */
	private static long Change_Disk_Size = 0L ;
	
	/**
	 * 缓存的地址.
	 */
	private static String Cache_Path = "java.io.tmpdir";
	
	/**
	 * 移除策略
	 */
	private static String Remove_Strategy = "FIFO";
	
	private Properties props = null;
	
	private static DiskCache instance;

	
	public static DiskCache getInstance() {
		if (instance == null) {
			synchronized (DiskCache.class) {
				if (instance == null) {
					instance = new DiskCache();
				}
			}
		}
		return instance;
	}

	private DiskCache() {
		String cacheSize = getProperty("cache.disk.size", "");
		if (cacheSize!=null && !cacheSize.equals("")) {
			Total_Disk_Size = Integer.parseInt(cacheSize)*1024*1024*1024;
		}
		Cache_Path = getProperty("cache.path.disk", Cache_Path);
		File file = new File(Cache_Path);
		if (!file.exists()) {
			file.mkdirs();
		}
		Remove_Strategy = getProperty("cache.remove.tag", Remove_Strategy);
	}

	/**
	 * 通过key获取磁盘value.
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		try {
			if (diskCache.get(key) != null) {
				File file = diskCache.get(key);
				InputStream is  =  new FileInputStream(file);
				DataInputStream dis = new DataInputStream(is);
				String className = dis.readUTF();
				WriteableObject wObject = (WriteableObject)Class.forName(className).newInstance();
				wObject.read(is);
				if (usedMap.containsKey(key)) {
					usedMap.put(key, usedMap.get(key)+1);
				}else {
					usedMap.put(key, 1);
				}
				return wObject;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 把key,value 都放到磁盘上去.
	 * @param key
	 * @param object
	 * @return
	 */
	public boolean put(String key, Object object) {
		long objByteSize = SizeOf.estimate(object);
		Change_Disk_Size += objByteSize;
		boolean result = false;
		
		if (Total_Disk_Size > Change_Disk_Size) {
			writeFile(key,object,result);
		}else {
			if (Remove_Strategy.equalsIgnoreCase("FIFO")) {
				String remove_key = putOrder.getFirst();
				File remove_file = new File(Cache_Path +"//"+ remove_key + ".dat");
				remove_file.delete();
				putOrder.remove(remove_key);
				diskCache.remove(remove_key);
				if (usedMap.containsKey(remove_key)) {
					usedMap.remove(remove_key);
				}
				Change_Disk_Size -= objByteSize;
				writeFile(key,object,result);
				result = true;
			} else if (usedMap.size()>0 || Remove_Strategy.equalsIgnoreCase("LFU")) {
				String remove_key = getLessKey();
				putOrder.remove(remove_key);
				diskCache.remove(remove_key);
				if (usedMap.containsKey(remove_key)) {
					usedMap.remove(remove_key);
				}
				File remove_file = new File(Cache_Path+"//"+ remove_key + ".dat");
				remove_file.delete();
				Change_Disk_Size -= objByteSize;
				writeFile(key,object,result);
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 写文件.
	 * @param key
	 * @param object
	 * @param result
	 */
	private static void writeFile(String key , Object object,boolean result){
		try {
			WriteableObject wObject = (WriteableObject)object ;
			File file = new File(Cache_Path +"//"+ key + ".dat");
			OutputStream outputStream = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(outputStream);
			dos.writeUTF(wObject.getClass().getName());
			wObject.write(outputStream);
			dos.flush();
			dos.close();
			outputStream.flush();
			outputStream.close();
			diskCache.put(key, file);
			putOrder.add(key);
			result = true;
		}catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
	}
	
	private static String getLessKey(){
		String result_key = null;
		int result_value = 0 ;
		int index = 0 ;
		for (Map.Entry<String, Integer> entry : usedMap.entrySet()) {
			String temp_key = entry.getKey();
			int temp_value = entry.getValue();
			if (index==0) {
				result_key = temp_key;
				result_value = temp_value;
			}else {
				 if (result_value > temp_value) {
					result_key = temp_key;
					result_value = temp_value;
				}
			}
			index ++;
		}
		return result_key;
	}
	
	private String getProperty(String key, String defaultValue) {
		if (props == null) {
			synchronized (this) {
				if (props == null) {
					try {
						props = PropertiesUtils.getProperties("/configPros/cache.properties");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return props.getProperty(key, defaultValue);
	}
}
