package com.javabase.base.socket.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.javabase.base.socket.ByteManageAbstractUtil;
import com.javabase.base.util.DataConvert;


/**
 * 人物库管理后台与集成管理后台通信协议的处理.
 * 
 * 
 * @author bruce
 *
 */
public class QueryIOReader extends ByteManageAbstractUtil {
	
	public static final Logger logger = Logger.getLogger(QueryIOReader.class);
	//返回标识.
	private static final int SUCCESS_CODE = 0;
	private static final int FAILURE_CODE = -22222;	
	

	/**
	 * 读取从socket中过来的流信息.
	 * 
	 * @param reader:流操作信息.
	 * 
	 * @param client：Client 客户端.
	 */
	public static void parseByteReader(BufferedInputStream reader,Socket client) {
		// 8字节【验证码】
		byte[] byte_identifying_code = new byte[8]; 
		int identifying_code_result = 0;
		try {
			identifying_code_result = reader.read(byte_identifying_code, 0, 8);
		} catch (IOException e) {
			logger.error("通过socket流获取验证码信息出错了."+e.getMessage());
		}
		if (identifying_code_result < 0) {
			logger.error(("对不起，读取标准头的前8字节时报错!!"));
		}
		String identifying_code  = new String(byte_identifying_code); //解析出验证码信息. 
		//logger.info("通过socket读取流信息得到的验证码是：" + identifying_code);

		//后续的数据包总长度
		byte[] byte_next_allldata = new byte[4];  
		int next_data_result = 0;
		try {
			next_data_result = reader.read(byte_next_allldata, 0, 4);
		} catch (IOException e) {
			logger.error("读取随后数据长度异常了."+e.getMessage());
		}
		if (next_data_result < 0) {
			logger.error("对不起，读取标准头的后4个标识长度字节时报错！！");
		}
		int next_data_len = DataConvert.bytesToInt(byte_next_allldata);
		//logger.info("通过socket读取流信息得到的随后的数据包长度：" + next_data_len);
		
		//协议类型.
		byte[] byte_protocol_type = new byte[2];  
		int protocol_data_len = 0;
		try {
			protocol_data_len = reader.read(byte_protocol_type, 0, 2);
		} catch (IOException e) {
			logger.error("读取协议类型异常了."+e.getMessage());
		}
		if (protocol_data_len < 0) {
			logger.error("对不起，读取协议类型的2个标识长度字节时报错！！");
		}
		short protocol_result = DataConvert.bytesToShort(byte_protocol_type);
		//logger.info("通过socket读取协议类型得到的值是：" + protocol_result);
		
		//保留字段
		byte[] byte_retain_hands = new byte[16];
		int retain_hands_result = 0;
		try {
			retain_hands_result = reader.read(byte_retain_hands, 0, 16);
		} catch (IOException e) {
			logger.error("通过socket流获保留字段信息出错了."+e.getMessage());
		}
		if (retain_hands_result < 0) {
			logger.error(("对不起，读取保留字段的12字节时报错!!"));
		}
		String retain_hands  = new String(byte_retain_hands); 
		//logger.info("通过socket读取保留字段得到的是：" + retain_hands);
		
		//命令类型.
		byte[] byte_command_code = new byte[2];
		int command_code_result = 0 ;
		try {
			command_code_result = reader.read(byte_command_code, 0, 2);
		} catch (Exception e) {
			logger.error("通过socket流获命令类型信息出错了."+e.getMessage());
		}
		if (command_code_result<0) {
			logger.error(("对不起，读取命令类型的2字节时报错!!"));
		}
		short command_type = DataConvert.bytesToShort(byte_command_code);
		//logger.info("通过socket读取流信息得到的命令类型数据是：" + command_type);
		
		//操作类型.0添加，1删除
		byte[] byte_operator_code = new byte[2];
		int operator_code_result = 0 ;
		try {
			operator_code_result = reader.read(byte_operator_code, 0, 2);
		} catch (Exception e) {
			logger.error("通过socket流获操作类型信息出错了."+e.getMessage());
		}
		if (operator_code_result<0) {
			logger.error(("对不起，读取操作类型的2字节时报错!!"));
		}
		short operator_type = DataConvert.bytesToShort(byte_operator_code);
		//logger.info("通过socket读取流信息得到的操作类型数据是：" + operator_type);
		
		//通过命令类型来判断.
		String result = dealWithByCommand(reader,command_type,operator_type);
		byte[] bytes = null;
		if (result.equals("0")) {
			//处理成功了
			bytes = ComSystemIOIDUtil.send_IDS_HaeadProtocol(SUCCESS_CODE, 0);		
		}else {
			bytes = ComSystemIOIDUtil.send_IDS_HaeadProtocol(FAILURE_CODE, 0);		
		}
		//返回并关闭连接.
		ComsystemUtil.returnBackData(bytes,client);

		
	}
	
	/**
	 * 根据类型来取值处理.
	 * 
	 * @param reader:流对象.
	 * @param command_type:命令类型.
	 * @param operator_type:操作类型.
	 * 
	 */
	private static String dealWithByCommand(BufferedInputStream reader,short command_type,short operator_type) {
		String result = null;	
		try {
				switch (command_type) {
				case 1:
					//pid，lid关系对照.
					result = pid_lid_done(reader,operator_type);
					break;
				case 2:
					//pid人名关系对照表.
					result = pid_name_done(reader, operator_type);
					break;
				case 3:
					//pid,pid关系对照表
					result = pid_pid_done(reader, operator_type);
					break;
				case 4:
					//lid,人名,pid关系对照表.
					result = lid_name_pid_done(reader, operator_type);
					break;
				case 5:
					//lid,md5,人名对照表
					result = lid_md5_name_done(reader, operator_type);
					break;
				}
			} catch (Exception e) {
				logger.error("根据类型处理出错了."+e.getMessage()+",command_type= "+command_type+",operator_type= "+operator_type);
				return result;
			}
		  //返回.
		  return result;
	}
	
	/**
	 * pid，lid关系对照
	 * 
	 * @param reader:
	 * @param operator_type：操作类型.0添加，1删除
	 */
	private static String pid_lid_done(BufferedInputStream reader,short operator_type) {
			//32字节pid
			byte[] byte_pid_str = new byte[32]; 
			int pid_str_result = 0;
			try {
				pid_str_result = reader.read(byte_pid_str, 0, 32);
			} catch (IOException e) {
				logger.error("通过socket流获取pid信息出错了."+e.getMessage());
			}
			if (pid_str_result < 0) {
				logger.error(("对不起，读取标准头的前32字节时报错!!"));
			}
			String pid  = new String(byte_pid_str); 
			//logger.info("通过socket读取流信息得到的pid是：" + pid);
			
			//32字节lid
			byte[] byte_lid_str = new byte[32]; 
			int lid_str_result = 0;
			try {
				lid_str_result = reader.read(byte_lid_str, 0, 32);
			} catch (IOException e) {
				logger.error("通过socket流获取lid信息出错了."+e.getMessage());
			}
			if (lid_str_result < 0) {
				logger.error(("对不起，读取标准头的前32字节时报错!!"));
			}
			String lid  = new String(byte_lid_str); 
			//logger.info("通过socket读取流信息得到的lid是：" + lid);

			//4字节.
			byte[] byte_del_tag = new byte[4];  
			int del_tag_result = -1;
			try {
				del_tag_result = reader.read(byte_del_tag, 0, 4);
			} catch (IOException e) {
				logger.error("读取随后数据长度异常了."+e.getMessage());
			}
			if (del_tag_result < 0) {
				logger.error("对不起，读取标准头的后4个标识长度字节时报错！！");
			}
			int is_del = DataConvert.bytesToInt(byte_del_tag);
			//logger.info("通过socket读取流信息得到的删除标识的数据是：" + is_del);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			return null;
	}
	
	
	/**
	 * pid人名关系对照表
	 * @param reader
	 * @param operator_type：操作类型.0添加，1删除
	 */
	private static String pid_name_done(BufferedInputStream reader,short operator_type) {
		//32字节pid.
		byte[] byte_pid_str = new byte[32]; 
		int pid_str_result = 0;
		try {
			pid_str_result = reader.read(byte_pid_str, 0, 32);
		} catch (IOException e) {
			logger.error("通过socket流获取pid信息出错了."+e.getMessage());
		}
		if (pid_str_result < 0) {
			logger.error(("对不起，读取标准头的前32字节时报错!!"));
		}
		String pid  = new String(byte_pid_str); 
		//logger.info("通过socket读取流信息得到的pid是：" + pid);
		
		//100字节名字.
		byte[] byte_name_str = new byte[100]; 
		int name_str_result = 0;
		try {
			name_str_result = reader.read(byte_name_str, 0, 100);
		} catch (IOException e) {
			logger.error("通过socket流获取name信息出错了."+e.getMessage());
		}
		if (name_str_result < 0) {
			logger.error(("对不起，读取标准头的前100字节时报错!!"));
		}
		String name  = new String(byte_name_str); 
		//logger.info("通过socket读取流信息得到的name是：" + name);
		
		//4字节.
		byte[] byte_del_tag = new byte[4];  
		int del_tag_result = -1;
		try {
			del_tag_result = reader.read(byte_del_tag, 0, 4);
		} catch (IOException e) {
			logger.error("读取随后数据长度异常了."+e.getMessage());
		}
		if (del_tag_result < 0) {
			logger.error("对不起，读取标准头的后4个标识长度字节时报错！！");
		}
		int is_del = DataConvert.bytesToInt(byte_del_tag);
		//logger.info("通过socket读取流信息得到的删除标识的数据是：" + is_del);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//构建对象.
		return null;
	}
	
	
	/**
	 * pid,pid关系对照表
	 * @param reader
	 * @param operator_type：操作类型.0添加，1删除
	 */
	private static String pid_pid_done(BufferedInputStream reader,short operator_type) {
		//32字节pid_top
		byte[] byte_pid_top_str = new byte[32]; 
		int pid_top_str_result = 0;
		try {
			pid_top_str_result = reader.read(byte_pid_top_str, 0, 32);
		} catch (IOException e) {
			logger.error("通过socket流获取pid信息出错了."+e.getMessage());
		}
		if (pid_top_str_result < 0) {
			logger.error(("对不起，读取标准头的前32字节时报错!!"));
		}
		String pid_top  = new String(byte_pid_top_str); 
		logger.info("通过socket读取流信息得到的pid_top是：" + pid_top);
		
		//32字节pid_esc
		byte[] byte_pid_esc_str = new byte[32]; 
		int pid_esc_str_result = 0;
		try {
			pid_esc_str_result = reader.read(byte_pid_esc_str, 0, 32);
		} catch (IOException e) {
			logger.error("通过socket流获取pid信息出错了."+e.getMessage());
		}
		if (pid_esc_str_result < 0) {
			logger.error(("对不起，读取标准头的前32字节时报错!!"));
		}
		String pid_esc  = new String(byte_pid_esc_str); 
		logger.info("通过socket读取流信息得到的pid_esc是：" + pid_esc);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//4字节.
		byte[] byte_del_tag = new byte[4];  
		int del_tag_result = -1;
		try {
			del_tag_result = reader.read(byte_del_tag, 0, 4);
		} catch (IOException e) {
			logger.error("读取随后数据长度异常了."+e.getMessage());
		}
		if (del_tag_result < 0) {
			logger.error("对不起，读取标准头的后4个标识长度字节时报错！！");
		}
		int is_del = DataConvert.bytesToInt(byte_del_tag);
		logger.info("通过socket读取流信息得到的删除标识的数据是：" + is_del);
		//构建对象.
		return null;
	}
	
	
	/**
	 * lid,人名,pid关系对照表.
	 * 
	 * @param reader
	 * @param operator_type：操作类型.0添加，1删除
	 */
	private static String lid_name_pid_done(BufferedInputStream reader,short operator_type) {
		//32字节pid_top
		byte[] byte_lid_str = new byte[32]; 
		int lid_str_result = 0;
		try {
			lid_str_result = reader.read(byte_lid_str, 0, 32);
		} catch (IOException e) {
			logger.error("通过socket流获取lid信息出错了."+e.getMessage());
		}
		if (lid_str_result < 0) {
			logger.error(("对不起，读取标准头的前32字节时报错!!"));
		}
		String lid  = new String(byte_lid_str); 
		//logger.info("通过socket读取流信息得到的lid是：" + lid);
		
		// 100字节名字
		byte[] byte_name_str = new byte[100]; 
		int name_str_result = 0;
		try {
			name_str_result = reader.read(byte_name_str, 0, 100);
		} catch (IOException e) {
			logger.error("通过socket流获取name信息出错了."+e.getMessage());
		}
		if (name_str_result < 0) {
			logger.error(("对不起，读取标准头的前100字节时报错!!"));
		}
		String name  = new String(byte_name_str); 
		//logger.info("通过socket读取流信息得到的name是：" + name);
		
		// 32字节pid
		byte[] byte_pid_str = new byte[32]; 
		int pid_str_result = 0;
		try {
			pid_str_result = reader.read(byte_pid_str, 0, 32);
		} catch (IOException e) {
			logger.error("通过socket流获取pid信息出错了."+e.getMessage());
		}
		if (pid_str_result < 0) {
			logger.error(("对不起，读取标准头的前32字节时报错!!"));
		}
		String pid  = new String(byte_pid_str); 
		//logger.info("通过socket读取流信息得到的pid是：" + pid);
		
		//4字节.
		byte[] byte_del_tag = new byte[4];  
		int del_tag_result = 0;
		try {
			del_tag_result = reader.read(byte_del_tag, 0, 4);
		} catch (IOException e) {
			logger.error("读取随后数据长度异常了."+e.getMessage());
		}
		if (del_tag_result < 0) {
			logger.error("对不起，读取标准头的后4个标识长度字节时报错！！");
		}
		int del_tag = DataConvert.bytesToInt(byte_del_tag);
		//logger.info("通过socket读取流信息得到的删除标识的数据是：" + del_tag);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//构建对象.
		return null;
	}
	
	
	/**
	 * lid,md5,人名对照表
	 * @param reader
	 * @param operator_type：操作类型.0添加，1删除
	 */
	private static String lid_md5_name_done(BufferedInputStream reader,short operator_type) {
		// 32字节lid,md5
		byte[] byte_lid_str = new byte[32]; 
		int md5_str_result = 0;
		try {
			md5_str_result = reader.read(byte_lid_str, 0, 32);
		} catch (IOException e) {
			logger.error("通过socket流获取md5信息出错了."+e.getMessage());
		}
		if (md5_str_result < 0) {
			logger.error(("对不起，读取标准头的前32字节时报错!!"));
		}
		String md5  = new String(byte_lid_str); 
		//logger.info("通过socket读取流信息得到的md5是：" + md5);		
		
		// 100字节名字
		byte[] byte_name_str = new byte[100]; 
		int name_str_result = 0;
		try {
			name_str_result = reader.read(byte_name_str, 0, 100);
		} catch (IOException e) {
			logger.error("通过socket流获取name信息出错了."+e.getMessage());
		}
		if (name_str_result < 0) {
			logger.error(("对不起，读取标准头的前100字节时报错!!"));
		}
		String name  = new String(byte_name_str); 
		//logger.info("通过socket读取流信息得到的name是：" + name);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//4字节.
		byte[] byte_del_tag = new byte[4];  
		int del_tag_result = -1;
		try {
			del_tag_result = reader.read(byte_del_tag, 0, 4);
		} catch (IOException e) {
			logger.error("读取随后数据长度异常了."+e.getMessage());
		}
		if (del_tag_result < 0) {
			logger.error("对不起，读取标准头的后4个标识长度字节时报错！！");
		}
		int is_del = DataConvert.bytesToInt(byte_del_tag);
		//构建对象.
		return null;
	}


}