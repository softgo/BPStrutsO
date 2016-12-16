package com.javabase.base.mongo.test;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.javabase.base.util.PropertiesUtils;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MonGoInfoTest {
	
	/**
	 * 
	 * mongo 测试,得到链接和数据库的数据.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Properties prop = PropertiesUtils.getProperties("/configPros/javabase_mongo.properties");
		String hosts = prop.getProperty("mongo.hosts");
		String host = hosts.split(",")[0].split(":")[0];
		int port = Integer.parseInt(hosts.split(",")[0].split(":")[1]);
		System.out.println("host = "+host+",port = "+port);
		try {
			Mongo mongo = new Mongo(host,port);
	        DB db = mongo.getDB("yun_jie");
	        //查询所有的聚集集合
	        for (String name : db.getCollectionNames()) {
	            System.out.println("collectionName: " + name);
	        }
	        DBCollection users = db.getCollection("city");
	        //查询所有的数据
	        DBCursor dbCursor = users.find();
	        DBObject obj = dbCursor.next();
			Set<String> cloumns = obj.keySet();
			for (String str : cloumns) {
				System.out.println(str);
			}
	        Iterator<DBObject> ites = dbCursor.iterator();
	        for (DBObject object : dbCursor) {
	        	Set<String> sets = object.keySet();
	        	for (String string : sets) {
					System.out.println(string);
				}
	        	Map<String, Object> maps = object.toMap();
	        	for (Map.Entry<String, Object> entry:maps.entrySet()) {
					System.out.println(entry.getKey()+","+entry.getValue());
				}
	        	System.out.println(object.toString());
	        }
	        while (dbCursor.hasNext()) {
	            System.out.println(dbCursor.next());
	        }
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}

