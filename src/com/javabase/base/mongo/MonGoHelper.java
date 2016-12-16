package com.javabase.base.mongo;

import com.javabase.base.util.PropertiesUtils;
import com.mongodb.*;
import com.mongodb.MongoClientOptions.Builder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.IOException;
import java.util.*;

/**
 * mongo 连接管理类
 * 
 * admin
 * 
 */
public abstract class MonGoHelper {

	private static final Logger log = Logger.getLogger(MonGoHelper.class.getClass().getName());

	/**
	 * mongo实例管理Map.
	 */
	private static Map<String, MongoHolder> mongoPools = new HashMap<String, MongoHolder>();

	/**
	 * 获取dataStore
	 * 
	 * @param poolName
	 * @return
	 */
	public static Datastore getDatastore(String poolName) {
		MongoHolder holder = mongoPools.get(poolName);
		if (holder == null)
			holder = internalMonGo(poolName);
		return holder.getDatastore();
	}

	/**
	 * 获取dataStore
	 * 
	 * @param poolName
	 * @param dbName
	 * @return
	 */
	public static Datastore getDatastore(String poolName, String dbName) {
		MongoHolder holder = mongoPools.get(poolName);
		if (holder == null)
			holder = internalMonGo(poolName);
		return holder.getDatastore(dbName);

	}

	/**
	 * 获取 Mongo
	 * 
	 * @param poolName
	 * @return
	 */
	public static Mongo getMongo(String poolName) {
		MongoHolder holder = internalMonGo(poolName);
		if (holder == null)
			return null;
		return holder.getMongo();
	}

	/**
	 * 初始化mongo
	 * 
	 * @param poolName
	 * @return
	 */
	private static MongoHolder internalMonGo(String poolName) {
		MongoHolder holder = mongoPools.get(poolName);
		if (holder != null)
			return holder;
		synchronized (mongoPools) {
			holder = mongoPools.get(poolName);
			if (holder == null) {
				try {
					holder = loadMonGo(poolName);
				}
				catch (IOException e) {
					throw new RuntimeException(e);
				}
				mongoPools.put(poolName, holder);
			}
		}
		return holder;
	}

	/**
	 * 获得配置文件中的关于mongo配置的所有信息.
	 * @param poolName
	 * @return
	 * @throws IOException
	 */
	private static MongoHolder loadMonGo(String poolName) throws IOException {
		/* 读取配置文件 */
		log.debug("init mongo: " + poolName);
		Properties prop = PropertiesUtils.getProperties("/configPros/javabase_mongo.properties");
		// general config
		Builder builder = MongoClientOptions.builder();
		//是否自动链接 true|false.
		String autoConnectRetry = prop.getProperty("mongo.autoConnectRetry");
		if (!StringUtils.isEmpty(autoConnectRetry)) {
			builder.autoConnectRetry(Boolean.valueOf(autoConnectRetry));
		}

		//最大自动链接的时间(ms)
		String maxAutoConnectRetryTime = prop
				.getProperty("mongo.maxAutoConnectRetryTime");
		if (!StringUtils.isEmpty(maxAutoConnectRetryTime)) {
			builder.maxAutoConnectRetryTime(Integer
					.valueOf(maxAutoConnectRetryTime));
		}

		//每个主机的链接数
		String connectionsPerHost = prop
				.getProperty("mongo.connectionsPerHost");
		if (!StringUtils.isEmpty(connectionsPerHost)) {
			builder.connectionsPerHost(Integer.valueOf(connectionsPerHost));
		}

		//最大等待时间.
		String maxWait = prop.getProperty("mongo.maxWaitTime");
		if (!StringUtils.isEmpty(maxWait)) {
			builder.maxWaitTime(Integer.valueOf(maxWait)); // 被阻塞线程从连接池获取连接的最长等待时间（ms）
		}

		//链接相应时间(ms).
		String connectTimeout = prop.getProperty("mongo.connectTimeOut");
		if (!StringUtils.isEmpty(connectTimeout)) {
			builder.connectTimeout(Integer.valueOf(connectTimeout));
		}

		//socket 链接超时时间(ms)
		String socketTimeout = prop.getProperty("mongo.socketTimeOut");
		if (!StringUtils.isEmpty(socketTimeout)) {
			builder.socketTimeout(Integer.valueOf(socketTimeout));
		}
		
		//是否一直开启socket链接.
		String socketKeepAlive = prop.getProperty("mongo.socketKeepAlive");
		if (!StringUtils.isEmpty(socketKeepAlive)) {
			builder.socketKeepAlive(Boolean.valueOf(socketKeepAlive));
		}

		//readPreference
		String readPreference = prop.getProperty("mongo.readPreference");
		if (!StringUtils.isEmpty(readPreference)) {
			builder.readPreference(ReadPreference.valueOf(readPreference));
		}

		//
		String writeConcern = prop.getProperty("mongo.writeConcern");
		if (!StringUtils.isEmpty(writeConcern)) {
			builder.writeConcern(WriteConcern.valueOf(writeConcern));
		}

		/**
		 * options 设置.
		 */
		MongoClientOptions options = builder.build();
		
		//主机 hosts; example: ip:port, ip2:port2
		String[] hosts = prop.getProperty("mongo.hosts").split(",");
		List<ServerAddress> seeds = new ArrayList<ServerAddress>(hosts.length);
		for (String host : hosts) {
			String[] info = host.split(":");
			seeds.add(new ServerAddress(info[0], Integer.valueOf(info[1])));
		}

		/**
		 * mclient 客户端.
		 */
		MongoClient mclient = new MongoClient(seeds, options);

		//链接认证; example: db:user:pswd, db2:user2:pswd2;
		String auth = prop.getProperty("mongo.auth");
		if (!StringUtils.isEmpty(auth)) {
			String[] authArr = auth.split(",");
			for (String authStr : authArr) {
				String[] info = authStr.split(":");
				String dbName = info[0];
				String user = info[1];
				char[] pswd = info[2].toCharArray();
				mclient.getDB(dbName).authenticate(user, pswd);
			}
		}
		// replication set and init mongo 初始化数据库
		String dbName = prop.getProperty("mongo.dbName");
		return new MongoHolder(mclient, dbName);
	}

	/**
	 * MongoHolder 对象.
	 * 
	 * @author bruce
	 * 
	 */
	private static class MongoHolder {
		//mongo.
		private final Mongo instance;
		//collections name.
		private final String defaultDBName;
		//data collections.
		private Map<String, Datastore> pools = new HashMap<String, Datastore>();

		//constructor.
		public MongoHolder(MongoClient mongo, String dbName) {
			this.instance = mongo;
			this.defaultDBName = dbName;
			final Morphia morphia = new Morphia();
			for (String name : mongo.getDatabaseNames()) {
				Datastore ds = morphia.createDatastore(mongo, name);
				pools.put(name, ds);
			}
		}

		/**
		 * @return the mongo
		 */
		public Mongo getMongo() {
			return instance;
		}

		public Datastore getDatastore() {
			return pools.get(defaultDBName);
		}

		public Datastore getDatastore(String dbName) {
			return pools.get(dbName);
		}
	}

}
