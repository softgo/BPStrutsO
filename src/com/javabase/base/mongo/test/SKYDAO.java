package com.javabase.base.mongo.test;

import com.javabase.base.mongo.DataStore;
import com.javabase.base.mongo.MonGoDAO;

@DataStore(tagValue = "manager", mongoDBName = "yun_jie")
public class SKYDAO<T, K> extends MonGoDAO<T, K> {
	
	public static final int key_cardinal_number = 1000;

	@Override
	public long getNextIdValue() {
		long now = System.currentTimeMillis();
		long r = Double.valueOf(Math.random() * key_cardinal_number).longValue();
		long k = now * key_cardinal_number + r;
		return k;
	}

}
