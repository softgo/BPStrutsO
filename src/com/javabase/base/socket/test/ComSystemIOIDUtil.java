package com.javabase.base.socket.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.javabase.base.util.DataConvert;


/**
 * 人物库管理后台与集成管理后台通信协议的处理.
 * 
 * @author bruce
 *
 */
public class ComSystemIOIDUtil extends ComsystemUtil {

	public static final Logger logger = Logger.getLogger(ComSystemIOIDUtil.class);
	//12位保留字段
	public static byte[] RETAIN_FIELD_12 = new byte[12];

	/**
	 * 协议头拼装.
	 * 
	 * ID 分配系统->管理系统.
	 * 
	 * @param protocol_type:协议类型.
	 * @param status_code:状态码.
	 * @param total_data_len:数据总长.
	 * @return
	 */
	public static byte[] send_IDS_HaeadProtocol(short protocol_type,int status_code,int total_data_len){
		try {
			List<byte[]> protoList = new ArrayList<byte[]>();
			//验证码
			byte[] verify_code_byte = IDENTIFYING_CODE.getBytes();//[8]验证码
			byte[] data_len_byte= DataConvert.intToByteArray(total_data_len + 18);//[4]数据总长.
			byte[] protocol_type_byte = DataConvert.shortToBytes(protocol_type);//[2]协议类型.
			byte[] status_code_byte = DataConvert.intToByteArray(status_code);//[4]状态码.
			
			protoList.add(verify_code_byte);//[8]验证码
			protoList.add(data_len_byte);//[4]数据总长
			protoList.add(protocol_type_byte);//[2]协议类型
			protoList.add(RETAIN_FIELD_12);//[12]保留字段
			protoList.add(status_code_byte);//[4]命令类型
			
			byte[] proto_header = DataConvert.sysCopy(protoList);
			//logger.info("请求数据包头的信息是："+new String(proto_header));
			return proto_header;			
		} catch (Exception e) {
			logger.error("发送的协议头信息失败."+e.getMessage());
			return null;
		}
	}	
	
	/**
	 * 协议头拼装.
	 * 
	 * ID 分配系统->管理系统.
	 * 
	 * @param status_code:状态码.
	 * @param total_data_len:数据总长.
	 * @return
	 */
	public static byte[] send_IDS_HaeadProtocol(int status_code,int total_data_len){
		try {
			List<byte[]> protoList = new ArrayList<byte[]>();
			//验证码
			byte[] verify_code_byte = IDENTIFYING_CODE.getBytes();//[8]验证码
			byte[] data_len_byte= DataConvert.intToByteArray(total_data_len + 18);//[4]数据总长.
			short protocol_type = 7;
			byte[] protocol_type_byte = DataConvert.shortToBytes(protocol_type);//[2]协议类型.
			byte[] status_code_byte = DataConvert.intToByteArray(status_code);//[4]状态码.
			
			protoList.add(verify_code_byte);//[8]验证码
			protoList.add(data_len_byte);//[4]数据总长
			protoList.add(protocol_type_byte);//[2]协议类型
			protoList.add(RETAIN_FIELD_12);//[12]保留字段
			protoList.add(status_code_byte);//[4]命令类型
			
			byte[] proto_header = DataConvert.sysCopy(protoList);
			//logger.info("请求数据包头的信息是："+new String(proto_header));
			return proto_header;			
		} catch (Exception e) {
			logger.error("发送的协议头信息失败."+e.getMessage());
			return null;
		}
	}	
}
