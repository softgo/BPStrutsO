package com.javabase.base.socket.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 人物库管理后台与集成管理后台通信协议的处理入口.
 * 
 * @author bruce
 *
 */

public class ReceiveRelationshipThread implements Runnable {
	public final Logger logger = Logger.getLogger(ReceiveRelationshipThread.class);
	private ServerSocket server = null;  
	public static ThreadPoolExecutor executor = null;
	
	/**
	 * 初始化方法，工程启动时调用
	 */
	public void initServers(){
		  try {
		  executor = new ThreadPoolExecutor(200,300,1,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(300),
				  new ThreadPoolExecutor.CallerRunsPolicy()
		  );
		  logger.debug("ReceiveRelationshipThread start to run");  
		  server = new ServerSocket(Integer.parseInt(getOperatePort("receiveRelationshipPort","26666")));
		  ReceiveRelationshipThread recv = new ReceiveRelationshipThread();
		  recv.setServer(server);
		  Thread thread = new Thread(recv);
		  thread.start(); 		 
		} catch (IOException e) {
			logger.error("IO 操作异常!" + e.getMessage());
		} catch (Exception ex) {
			logger.error("数据处理异常." + ex.getMessage());
		}
	}
	
	public void run() {
		Socket client = null;	
		while(true){
			try {
				logger.debug("ReceiveRelationshipThread befor server.accept()");
			    client = this.getServer().accept();
			    client.setSoTimeout(60000*10);
			    logger.debug("ReceiveRelationshipThread after server.accept()");
			    //接收数据库同步内容
			    QueryRelationshipIOReader.parseByteReader(new BufferedInputStream(client.getInputStream()), client);
			} catch (IOException e) {
				logger.error("IO 操作异常!" + e.getMessage());
			} catch (Exception ex) {
				logger.error("数据处理异常." + ex.getMessage());
			  }
          }
	}
	
	/**
	  * 读配置文件取接收端口
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	private String getOperatePort(String property,String defaultValue){
		Properties prop = new Properties();
		InputStream fis = getClass().getClassLoader().getResourceAsStream("taskConfig.properties");
		try {
			prop.load(fis);
		} catch (IOException e) {
			logger.error("通过配置文件获取端口出错了."+e.getMessage());
		}
		String value = prop.getProperty(property,defaultValue).trim();
		//logger.debug(property+" value=="+value);
	    return value;
	}
	
	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}
}
