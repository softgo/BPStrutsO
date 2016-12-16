package com.javabase.base.mongo;

import com.javabase.base.impl.MonGoBaseDaoImpl;

/**
 * 
 * @author admin
 *
 * @param <T>
 * @param <K>
 */
@DataStore(tagValue = "mongo", mongoDBName = "yun_jie")
public class MonGoSettingManager<T,K> extends MonGoBaseDaoImpl<T,K> {
	
	public static final int key_auto_number = 10000;
	
	@Override
	public long getNextIdValue() {
		long now = System.currentTimeMillis();
		long r = Double.valueOf(Math.random() * key_auto_number).longValue();
		long k = now * key_auto_number + r;
		return k;
	}
	
}
