package com.javabase.base.socket.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.javabase.base.socket.SocketUtil;


/**
 * 人物库管理后台与集成管理后台通信协议的处理.
 * 
 * 
 * @author bruce
 *
 */
public class ComsystemUtil {

	private static Logger logger = Logger.getLogger(ComsystemUtil.class.getName());
	//八字节的验证码.
	public static final String IDENTIFYING_CODE = "VIDEO2.0";   
	
	/**
	 * 发送数据的基础函数，管理系统到id分配.
	 * 
	 * @param send_datas
	 * @return
	 */
	public static String baseMSForSocketData(byte[] send_datas) {
		try {
			SocketUtil socket = new SocketUtil("127.0.0.1",10001,"utf-8");
			socket.connect();
			socket.sendByte(send_datas);
			String result = socket.returnMSSocketBackData();
			socket.close();
			return result;			
		} catch (Exception e) {
			logger.error("发送数据失败了."+e.getMessage());
			return "连接服务器失败";
		}
	}
	/**
	 * 人物图片删除.
	 * 
	 * @param send_datas
	 * @return
	 */
	public static String remoePicSocketData(byte[] send_datas) {
		try {
			SocketUtil socket = new SocketUtil("127.0.0.1",10001,"utf-8");
			socket.connect();
			socket.sendByte(send_datas);
			String result = socket.returnRemoeRelationData();
			socket.close();
			return result;			
		} catch (Exception e) {
			logger.error("发送数据失败了."+e.getMessage());
			return "连接服务器失败";
		}
	}
	/**
	 * 审核系统删除关系.
	 * 
	 * @param send_datas
	 * @return
	 */
	public static String remoeRelationSocketData(byte[] send_datas) {
		try {
			SocketUtil socket = new SocketUtil("127.0.0.1",10001,"utf-8");
			socket.connect();
			socket.sendByte(send_datas);
			String result = socket.returnRemoeRelationData();
			socket.close();
			return result;			
		} catch (Exception e) {
			logger.error("发送数据失败了."+e.getMessage());
			return "连接服务器失败";
		}
	}
	/**
	 * 返回响应.
	 * @param proto
	 * @param client
	 */
	public static void returnBackData(byte[] proto, Socket client) {
		try {
			BufferedOutputStream bfOut =  new BufferedOutputStream(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议
			//logger.info("将要发给客户端的数据是："+new String(proto));
			bfOut.write(proto);
			bfOut.flush();
			bfOut.close();
			if (client != null && !client.isClosed()) {
				client.close();
				client = null;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("连接不到主机."+e.getMessage());
		} catch (IOException e) {
			logger.error("IO流异常了."+e.getMessage());
		}
	}
	
	/**
	 * pid ； lid为空的时候传入的值。
	 * 
	 * @return
	 */
	public static byte[] createBaseTag(){
		StringBuffer buffer = new StringBuffer("0");
		for (int i = 0; i < 31; i++) {
			buffer.append(" ");
		}
		return buffer.toString().getBytes();
	}
	
	/**
	 * char32 由数据库整数转换为byte[32]
	 * @param id
	 * @return
	 */
	public static byte[] trunTo32Bytes(String id) {
		String str = id + "";
		if (str.length() == 32) {
			return str.getBytes();
		}else {
			StringBuffer buff = new StringBuffer("");
			buff.append(str);
			for (int i = 0; i < 32 - str.length(); i++) {
				buff.append(" ");
			}
			return buff.toString().getBytes();
		}
	}
	
	/**
	 * 读取内容
	 * @param reader
	 * @param length
	 * @return
	 */
	public static byte[] getReturnStr(BufferedInputStream reader, int length){
		byte[] allArr = new byte[length];
		int bytesRead = 0;
		int cur_read_len = 204800;//每次200K数据
		try { //如果出现异常 退出循环
         while (true) {				
        	if(length - bytesRead > 204800)
        	{
        		cur_read_len = 204800;
        	}else{
        		cur_read_len = length - bytesRead;  //1---1583
        	}
            int bytesReadLength = 0;
			bytesReadLength = reader.read(allArr, bytesRead, cur_read_len);
			
			if(bytesReadLength==-1){
				break;
			}
			
			bytesRead+=bytesReadLength;
			
			if (bytesRead >= length) {// end of InputStream
				break;
			}
          }
		} catch (IOException e) {
			logger.info("读取流文件出错了,错误是："+e.getMessage());
		} 
		return allArr;
	}
	
}
