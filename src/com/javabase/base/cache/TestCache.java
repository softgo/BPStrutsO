package com.javabase.base.cache;

import java.util.ArrayList;
import java.util.List;

public class TestCache {

	static int index = 0;
	public static void main(String[] args) {
		
		long size1 = ObjBytesSizeUtil.printTypes(ObjBytesSizeUtil.sizeof(beanInstance())); 
		
		long size2 = SizeOf.estimate(beanInstance());
		
		System.out.println(size1/1024/1024);
		System.out.println(size2/1024/1024);
	
		DiskCache cache = DiskCache.getInstance();
		List<String> keyList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			String key = "myCache"+index;
			cache.put(key, beanInstance());
			keyList.add(key);
			index++;
		}
		
		for (String key : keyList) {
			 People people = (People) cache.get(key);
			 if (people!=null) {
				 System.out.println("key = "+key+",name = "+people.getName()+",size = "+people.getList().size());
			}
		}
		
	}
	/**
	 * 放入数据到集合.
	 * 
	 * @return
	 */
	private static People beanInstance() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 20000; i++) {
			list.add("JavaCache-EHCache系列之计算实例占用的内存大小(SizeOf引擎),基本数据的类型的大小是固定的，这里就不多说了。对于非基本类型的Java对象，其大小就值得商榷。加入的key是:"
					+ i);
		}
		return new People("PersonBean"+index,list);
	}
	
}
