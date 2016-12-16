package com.javabase.base.socket.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

import org.apache.log4j.Logger;

import com.javabase.base.socket.ByteManageAbstractUtil;
import com.javabase.base.util.DataConvert;
import com.javabase.base.util.DateStyle;
import com.javabase.base.util.DateTime;

/**
 * 人物库管理后台与集成管理后台通信协议的处理.
 * 
 * 
 * @author bruce
 *
 */
public class QueryRelationshipIOReader extends ByteManageAbstractUtil {
	
	public static final Logger logger = Logger.getLogger(QueryRelationshipIOReader.class);
	//返回标识.
	private static final int SUCCESS_CODE = 0;
	private static final int FAILURE_CODE = -1;	
	

	/**
	 * 读取从socket中过来的流信息.
	 * 
	 * @param reader:流操作信息.
	 * 
	 * @param client：Client 客户端.
	 * @throws IOException 
	 */
	public static void parseByteReader(BufferedInputStream reader,Socket client) throws IOException {
		
		String path = "/data01/liuhq/comsystemdata/";
		String savefilename = "";
		int bytesRead = 0;
		int cur_read_len = 4096;
		byte[] xmlArr = null;
		byte[] tmpArr = null;
		byte[] bytes = null;
		// 8字节【验证码】
		byte[] byte_identifying_code = new byte[8]; 
		int identifying_code_result = 0;
		try {
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
		
		//数据库表类型
		byte[] data_type_code = new byte[2];
		
		int data_type_result = 0 ;
		try {
			data_type_result = reader.read(data_type_code, 0, 2);
		} catch (Exception e) {
			logger.error("通过socket流获数据库表类型信息出错了."+e.getMessage());
		}
		if (data_type_result<0) {
			logger.error(("对不起，读取数据库表类型的2字节时报错!!"));
		}
		short data_type = DataConvert.bytesToShort(data_type_code);
		//logger.info("数据库表类型是：" + data_type);
		
		
		//文件内容长度
		byte[] byte_file_data = new byte[4];  
		int file_data_result = 0;
		try {
			file_data_result = reader.read(byte_file_data, 0, 4);
		} catch (IOException e) {
			logger.error("读取随后数据库表长度异常了."+e.getMessage());
		}
		if (file_data_result < 0) {
			logger.error("对不起，读取数据库表的后4个标识长度字节时报错！！");
		}
		int file_data_len = DataConvert.bytesToInt(byte_file_data);
		//logger.info("读取流信息得到的随后的数据库表长度：" + file_data_len);
		
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		if(data_type==1){
			savefilename = "pidlid";
		}else if(data_type==2){
			savefilename = "pidname";
		}else if(data_type==3){
			savefilename = "pidpid";
		}else if(data_type==4){
			savefilename = "lidnamepid";
		}else if(data_type==5){
			savefilename = "lidmd5";
		}
		String filename = savefilename + DateTime.yesterDateMid() + ".dat";
		File file = new File(path,filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		while (true) {
        	if(file_data_len - bytesRead > 4096)
        	{
        		cur_read_len = 4096;
        	}else{
        		cur_read_len = file_data_len - bytesRead;
        	}
        	xmlArr = new byte[cur_read_len+1];
            int bytesReadLength = 0;
			bytesReadLength = reader.read(xmlArr, 0, cur_read_len);
			tmpArr = new  byte[bytesReadLength];
			xmlArr[bytesReadLength] = 0;
			//logger.info("bytesReadLength==="+bytesReadLength);
			if(bytesReadLength==-1)
				break;
			if(bytesReadLength!=cur_read_len){
				//logger.info("bytesReadLength==="+bytesReadLength);
			}
			for(int i=0;i<bytesReadLength;i++){
				tmpArr[i] = xmlArr[i];
			}
			 fos.write(tmpArr);  //写出流
		     fos.flush();  //更新写出的结果
			bytesRead+=bytesReadLength;
			//logger.info("bytesRead==="+bytesRead);
			
			if (bytesRead >= file_data_len) {// end of InputStream
				break;
			}
			//logger.info("also in getResXml while(true)");
          }
		
		fos.close();
		//处理成功了
		bytes = ComSystemIOIDUtil.send_IDS_HaeadProtocol(SUCCESS_CODE, 0);	
	    //insertDB(filename,data_type);
		
		}catch (FileNotFoundException e1) {
			bytes = ComSystemIOIDUtil.send_IDS_HaeadProtocol(FAILURE_CODE, 0);	
			logger.error(e1.getMessage());
		} catch (IOException e) {
			bytes = ComSystemIOIDUtil.send_IDS_HaeadProtocol(FAILURE_CODE, 0);	
			logger.error(e.getMessage());
		}finally {
			//返回并关闭连接.
			ComsystemUtil.returnBackData(bytes,client);
			
		}
	}
	
	/**
	 * 推送的数据类型： 
	 * 1：people_pid_lid_base (Pid-lid关系对照表) 
	 * 2：people_pid_name_base (Pid-name关系对照表)
	 * 3: people_pid_pid_base (Pid-pid关系对照表)
	 * 4: people_lid_name_pid_base (Lid_name_pid关系对照表)  
	 * 5: people_md5_name_base (Md5_name关系对照表)
	 * @param type
	 * @return
	 */
	public static String getExecuteTableName(short type) {
		switch (type) {
		case 1:
			return "people_pid_lid_base";
		case 2:
			return "people_pid_name_base";
		case 3:
			return "people_pid_pid_base";
		case 4:
			return "people_lid_name_pid_base";
		case 5:
			return "people_md5_name_base";
		default:
			break;
		}
		return null;
	}
	
	public static String buildSql(short type,String tableName,String nowDate,String[] item) {
		String sql = "";
		switch (type) {
		case 1:
			sql = "insert into "+tableName+" (pid, lid, is_del, create_time, update_time, temp0, temp1, temp2) values("+"'"+
			item[1]+"','"+item[0]+"','"+item[2]+"','"+nowDate+"','"+nowDate+"',null,null,null);";
			break;
		case 2:
			//sql = "people_pid_name_base";
			sql = "insert into "+tableName+" (pid, name, is_del, create_time, update_time, temp0, temp1, temp2) values("+"'"+
			item[0]+"','"+item[1].replace("'", "\\'")+"','"+item[2]+"','"+nowDate+"','"+nowDate+"',null,null,null);";
			break;
		case 3:
			//sql = "people_pid_pid_base";
			sql = "insert into "+tableName+" (pid_top, pid_esc, is_del, create_time, update_time, temp0, temp1, temp2) values("+"'"+
			item[1]+"','"+item[0]+"','0','"+nowDate+"','"+nowDate+"',null,null,null);";
			break;
		case 4:
			//sql = "people_lid_name_pid_base";
			sql = "insert into "+tableName+" (pid, lid,name, is_del, create_time, update_time, temp0, temp1, temp2) values("+"'"+
			item[2]+"','"+item[0]+"','"+item[1].replace("'", "\\'")+"','0','"+nowDate+"','"+nowDate+"',null,null,null);";
			break;
		case 5:
			//sql = "people_md5_name_base";
			sql = "insert into "+tableName+" (md5, name, is_del, create_time, update_time, temp0, temp1, temp2) values("+"'"+
			item[0]+"','"+item[1].replace("'", "\\'")+"','0','"+nowDate+"','"+nowDate+"',null,null,null);";
			break;
		default:
			break;
		}
		return sql;
	}
	
}