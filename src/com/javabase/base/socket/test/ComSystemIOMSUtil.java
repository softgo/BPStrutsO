package com.javabase.base.socket.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.javabase.base.util.DataConvert;
import com.javabase.base.util.StringUtil;


/**
 * 人物库管理后台与集成管理后台通信协议的处理.
 * 
 * @author bruce
 *
 */
public class ComSystemIOMSUtil extends ComsystemUtil {

	public static final Logger logger = Logger.getLogger(ComSystemIOMSUtil.class);
	//14位保留字段
	public static byte[] RETAIN_FIELD_14 = new byte[14];
	private static int HEAD_SIZE = 20;
	
	/**
	 * 管理系统->ID 分配系统
	 */
	private static final short ADD_PID = 2; //添加-pid.
	private static final short DEL_PID_LID = 3; //删除PID|LID|PID-LID 关系.
	private static final short COM_LID_TO_PID = 4; //合并-LID 到 PID.
	private static final short COM_PID_TO_PID = 5; //合并-PID 到 PID.
	private static final short ADD_DEL_LID_PID = 8; //添加/删除-lid 下多值PID
	
	//先不处理.
	private static final short CHANGE_STATUS_PID = 6; //给pid置状态.
	private static final short CHANGE_NAME_PID = 7; //改掉PID名字.
	private static final short OTHER_SYS_DEL = 10; //第三方系统删除数据.
	
	
	/**************************************************************发送请求的实现**************************************************************/
	
	/**
	 * 给人物添加pid操作.
	 * 
	 * @param people_name：传入的新人物的名字.
	 * 
	 * @return:返回pid.
	 */
	public static String add_people_pid(String people_name) {
		byte[] send_datas = add_pid_bytes(people_name);
		String xmlResult = ComsystemUtil.baseMSForSocketData(send_datas);
		//返回pid
		return xmlResult;
	}

	/**
	 * 删除pid下的lid操作.
	 * 
	 * @param pid
	 * @param lid
	 * @return:0成功，非0失败!
	 */
	public static String delete_pid_lid(String pid,String lid) {
		byte[] send_datas = del_pid_lid_bytes(pid, lid);
		return ComsystemUtil.baseMSForSocketData(send_datas);
	}
	/**
	 * 恢复删除pid、lid操作.
	 * 
	 * @param pid
	 * @param lid
	 * @return:0成功，非0失败!
	 */
	public static String restore_pid_lid(String pid,String lid) {
		byte[] send_datas = restore_pid_lid_bytes(pid, lid);
		return ComsystemUtil.baseMSForSocketData(send_datas);
	}
	
	
	/**
	 * 合并lid到pid下
	 * @param new_pid
	 * @param old_pid
	 * @param lid
	 * @return:0成功，非0失败!
	 */
	public static String combine_lid_to_pid(String new_pid,String old_pid,String lid) {
		byte[] send_datas = com_lid_to_pid_bytes(new_pid, old_pid, lid);
		return ComsystemUtil.baseMSForSocketData(send_datas);
	}
	
	/**
	 * 报警需要走的协议
	 * @param new_pid
	 * @param old_pid
	 * @param lid
	 * @return:0成功，非0失败!
	 */
	public static String waring_lid_to_pid(short data_type,String new_pid,String old_pid,String lid) {
		byte[] send_datas = waring_lid_to_pid_bytes(data_type,new_pid, old_pid, lid);
		return ComsystemUtil.baseMSForSocketData(send_datas);
	}
	
	/**
	 * 报警需要走的协议----图片带xml
	 * @param new_pid
	 * @param old_pid
	 * @param lid
	 * @return:0成功，非0失败!
	 */
	public static String waring_lid_to_pid_xml(short data_type,String new_pid,String old_pid,String lid,String xmlStr) {
		byte[] send_datas = waring_lid_to_pid_xml_bytes(data_type,new_pid, old_pid, lid,xmlStr);
		return ComsystemUtil.baseMSForSocketData(send_datas);
	}
	
	
	/**
	 * 合并pid到pid下.
	 * @param operator_type
	 * @param target_pid
	 * @param source_pid
	 * @return:0成功，非0失败!
	 */
	public static  String combine_pid_to_pid(int operator_type, String target_pid,String source_pid) {
		byte[] send_datas = com_pid_to_pid_bytes(operator_type, target_pid, source_pid);
		return ComsystemUtil.baseMSForSocketData(send_datas);
	}
	
	/**
	 * 添加/删除lid下面多值的pid
	 * @param operator_type
	 * @param lid
	 * @param xmlStr
	 * @return:0成功，非0失败!
	 */
	public static String add_del_lid_pid(short operator_type, String lid,String xmlStr) {
		byte[] send_datas = add_del_lid_pid_bytes(operator_type, lid, xmlStr);
		return ComsystemUtil.baseMSForSocketData(send_datas);
	}
	
	/**
	 * 审核系统删除关系.
	 * @param operator_type
	 * @param lid
	 * @param xmlStr
	 * @return:0成功，非0失败!
	 */
	public static String removerelation(short operator_type, String lid,String xmlStr) {
		byte[] send_datas = add_del_lid_pid_bytes(operator_type, lid, xmlStr);
		return ComsystemUtil.remoeRelationSocketData(send_datas);
	}
	/**
	 * 删除图片.
	 * @param operator_type
	 * @param lid
	 * @param xmlStr
	 * @return:0成功，非0失败!
	 */
	public static String removepic(short operator_type, String lid,String xmlStr) {
		byte[] send_datas = del_lid_pid_pic_bytes(operator_type, lid, xmlStr);
		return ComsystemUtil.remoePicSocketData(send_datas);
	}
	/**
	 * 报警走的协议添加/删除lid下面多值的pid
	 * @param operator_type
	 * @param lid
	 * @param xmlStr
	 * @return:0成功，非0失败!
	 */
	public static String waring_add_del_lid_pid(short data_type,short operator_type, String lid,String xmlStr) {
		byte[] send_datas = waring_add_del_lid_pid_bytes(data_type,operator_type, lid, xmlStr);
		return ComsystemUtil.baseMSForSocketData(send_datas);
	}
	
	/**************************************************************拼接协议的过程**************************************************************/
	
	
	/**
	 * 给人物添加pid的字节组合.
	 * @return
	 */
	private static byte[] add_pid_bytes(String people_name) {
		try {
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();

			//pid 32
			byte[] pid_bytes = createBaseTag();
			total_data_len += 32;
			
			//数据内容.
			String add_pid_xml = null;
			Document document = null ;
			if (document!=null) {
				add_pid_xml = document.asXML();
			}
			byte[] xml_data_bytes = add_pid_xml.getBytes();
			int length = xml_data_bytes.length;
			//logger.info("xml的长度是："+length);
			total_data_len += length;
			
			//数据长度.
			int data_len = length;
			byte[] data_len_bytes = DataConvert.intToByteArray(data_len);
			total_data_len += data_len_bytes.length;
			
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += 4;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(ADD_PID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(pid_bytes);
			protoList.add(data_len_bytes);
			protoList.add(xml_data_bytes);
			protoList.add(extends_bytes);
			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("添加pid的操作失败."+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 恢复删除pid下的lid字节组合.
	 * @param pid
	 * @param lid
	 * @return
	 */
	private static byte[] restore_pid_lid_bytes(String pid,String lid) {
		try {
			if (!StringUtil.isNotBlank(pid) && !StringUtil.isNotBlank(lid)) {
				logger.error("传入的pid，lid不能都为空!");
				return null;
			}
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();
			
			//pid
			byte[] pid_bytes = null;
			if (StringUtil.isNotBlank(pid) && pid.equals("0")) {
				pid_bytes = createBaseTag();
			}else {
				pid_bytes = trunTo32Bytes(pid);
			}
			total_data_len += 32;
			
			//lid
			byte[] lid_bytes = null;
			if (StringUtil.isNotBlank(lid) && lid.equals("0")) {
				lid_bytes = createBaseTag();
			}else {
				lid_bytes = trunTo32Bytes(lid);
			}
			total_data_len += 32;
			
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += 4;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(CHANGE_STATUS_PID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(pid_bytes);
			protoList.add(lid_bytes);
			protoList.add(extends_bytes);
			
			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("删除pid下的lid字节组合的操作失败."+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 删除pid下的lid字节组合.
	 * @param pid
	 * @param lid
	 * @return
	 */
	private static byte[] del_pid_lid_bytes(String pid,String lid) {
		try {
			if (!StringUtil.isNotBlank(pid) && !StringUtil.isNotBlank(lid)) {
				logger.error("传入的pid，lid不能都为空!");
				return null;
			}
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();
			
			//pid
			byte[] pid_bytes = null;
			if (StringUtil.isNotBlank(pid) && pid.equals("0")) {
				pid_bytes = createBaseTag();
			}else {
				pid_bytes = trunTo32Bytes(pid);
			}
			total_data_len += 32;
			
			//lid
			byte[] lid_bytes = null;
			if (StringUtil.isNotBlank(lid) && lid.equals("0")) {
				lid_bytes = createBaseTag();
			}else {
				lid_bytes = trunTo32Bytes(lid);
			}
			total_data_len += 32;
			
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += 4;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(DEL_PID_LID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(pid_bytes);
			protoList.add(lid_bytes);
			protoList.add(extends_bytes);
			
			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("删除pid下的lid字节组合的操作失败."+e.getMessage());
			return null;
		}
	}
	/**
	 * 报警需要走的协议
	 * @param new_pid：新PID，目标PID，不为0
	 * @param old_pid:旧PID，LID 原来所对应的PID(没有则填0)
	 * @param lid:LID,文本（不足部分用空格填充）不为0
	 * @return
	 */
	private static byte[] waring_lid_to_pid_bytes(short data_type,String new_pid,String old_pid,String lid) {
		try {
			if ( !StringUtil.isNotBlank(new_pid) && !new_pid.equals("0")) {
				logger.error("传入的new_pid不能为空,不能为0!");
				return null;
			}
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();
			//原始数据类型.
			byte[] data_type_bytes = DataConvert.shortToBytes(data_type);
			total_data_len += 2;
			//new_pid
			byte[] new_pid_bytes = null;
			if (StringUtil.isNotBlank(new_pid) && new_pid.equals("0")) {
				new_pid_bytes = createBaseTag();
			}else {
				new_pid_bytes = trunTo32Bytes(new_pid);
			}
			total_data_len += 32;
			
			//old_pid
			byte[] old_pid_bytes = null;
			if (StringUtil.isNotBlank(old_pid) && old_pid.equals("0")) {
				old_pid_bytes = createBaseTag();
			}else {
				old_pid_bytes = trunTo32Bytes(old_pid);
			}
			total_data_len += 32;
			
			//lid
			byte[] lid_bytes = null;
			if (StringUtil.isNotBlank(lid) && lid.equals("0")) {
				lid_bytes = createBaseTag();
			}else {
				lid_bytes = trunTo32Bytes(lid);
			}
			total_data_len += 32;
			
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += extends_bytes.length;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(COM_LID_TO_PID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(data_type_bytes);
			protoList.add(new_pid_bytes);
			protoList.add(old_pid_bytes);
			protoList.add(lid_bytes);
			protoList.add(extends_bytes);
			
			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("合并-lid 到PID字节组合的操作失败."+e.getMessage());
			return null;
		}
	}
	/**
	 * 报警需要走的协议---图片带xml
	 * @param new_pid：新PID，目标PID，不为0
	 * @param old_pid:旧PID，LID 原来所对应的PID(没有则填0)
	 * @param lid:LID,文本（不足部分用空格填充）不为0
	 * @return
	 */
	private static byte[] waring_lid_to_pid_xml_bytes(short data_type,String new_pid,String old_pid,String lid,String xmlStr) {
		try {
			if ( !StringUtil.isNotBlank(new_pid) && !new_pid.equals("0")) {
				logger.error("传入的new_pid不能为空,不能为0!");
				return null;
			}
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();
			//原始数据类型.
			byte[] data_type_bytes = DataConvert.shortToBytes(data_type);
			total_data_len += 2;
			//new_pid
			byte[] new_pid_bytes = null;
			if (StringUtil.isNotBlank(new_pid) && new_pid.equals("0")) {
				new_pid_bytes = createBaseTag();
			}else {
				new_pid_bytes = trunTo32Bytes(new_pid);
			}
			total_data_len += 32;
			//old_pid
			byte[] old_pid_bytes = null;
			if (StringUtil.isNotBlank(old_pid) && old_pid.equals("0")) {
				old_pid_bytes = createBaseTag();
			}else {
				old_pid_bytes = trunTo32Bytes(old_pid);
			}
			total_data_len += 32;
			
			//lid
			byte[] lid_bytes = null;
			if (StringUtil.isNotBlank(lid) && lid.equals("0")) {
				lid_bytes = createBaseTag();
			}else {
				lid_bytes = trunTo32Bytes(lid);
			}
			total_data_len += 32;
			
			//xml_str
			byte[] xml_str_bytes = xmlStr.getBytes();
			total_data_len += xml_str_bytes.length+4;
			byte[] xml_length_bytes =DataConvert.intToByteArray(xml_str_bytes.length);
			
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += extends_bytes.length;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(COM_LID_TO_PID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(data_type_bytes);
			protoList.add(new_pid_bytes);
			protoList.add(old_pid_bytes);
			protoList.add(lid_bytes);
			protoList.add(xml_length_bytes);
			protoList.add(xml_str_bytes);
			protoList.add(extends_bytes);
			
			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("合并-lid 到PID字节组合的操作失败."+e.getMessage());
			return null;
		}
	}
	/**
	 * 合并-lid 到PID字节组合.
	 * @param new_pid：新PID，目标PID，不为0
	 * @param old_pid:旧PID，LID 原来所对应的PID(没有则填0)
	 * @param lid:LID,文本（不足部分用空格填充）不为0
	 * @return
	 */
	private static byte[] com_lid_to_pid_bytes(String new_pid,String old_pid,String lid) {
		try {
			if ( !StringUtil.isNotBlank(new_pid) && !new_pid.equals("0")) {
				logger.error("传入的new_pid不能为空,不能为0!");
				return null;
			}
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();
			
			//new_pid
			byte[] new_pid_bytes = null;
			if (StringUtil.isNotBlank(new_pid) && new_pid.equals("0")) {
				new_pid_bytes = createBaseTag();
			}else {
				new_pid_bytes = trunTo32Bytes(new_pid);
			}
			total_data_len += 32;
			
			//old_pid
			byte[] old_pid_bytes = null;
			if (StringUtil.isNotBlank(old_pid) && old_pid.equals("0")) {
				old_pid_bytes = createBaseTag();
			}else {
				old_pid_bytes = trunTo32Bytes(old_pid);
			}
			total_data_len += 32;
			
			//lid
			byte[] lid_bytes = null;
			if (StringUtil.isNotBlank(lid) && lid.equals("0")) {
				lid_bytes = createBaseTag();
			}else {
				lid_bytes = trunTo32Bytes(lid);
			}
			total_data_len += 32;
			int xml_len = 0;
			byte[] xml_length_bytes = DataConvert.intToByteArray(xml_len);
			total_data_len += 4;
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += extends_bytes.length;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(COM_LID_TO_PID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			short data_type = 0;
			//原始数据类型.
			byte[] data_type_bytes = DataConvert.shortToBytes(data_type);
			
			
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(data_type_bytes);
			protoList.add(new_pid_bytes);
			protoList.add(old_pid_bytes);
			protoList.add(lid_bytes);
			protoList.add(xml_length_bytes);
			protoList.add(extends_bytes);
			
			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("合并-lid 到PID字节组合的操作失败."+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 合并-pid 到PID字节组合.
	 * 
	 * @param operator_type:操作类型0:添加1：删除
	 * @param target_pid:PID,文本，不为0
	 * @param source_pid:PID,文本，不为0
	 * @return
	 */
	private static byte[] com_pid_to_pid_bytes(int operator_type, String target_pid,String source_pid) {
		try {
			if ( !StringUtil.isNotBlank(target_pid) && !StringUtil.isNotBlank(source_pid)) {
				logger.error("传入的pid不能为空!");
				return null;
			}
			if (target_pid.equals("0") && source_pid.equals("0")) {
				logger.error("传入的pid都不能为0!");
				return null;
			}
			
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();
			
			//操作类型.
			byte[] operator_bytes = DataConvert.intToByteArray(operator_type);
			total_data_len += 4;
			
			//target_pid
			byte[] target_pid_bytes = trunTo32Bytes(target_pid);
			total_data_len += 32;
			
			//source_pid
			byte[] source_pid_bytes = trunTo32Bytes(source_pid);
			total_data_len += 32;
			
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += 4;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(COM_PID_TO_PID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(operator_bytes);
			protoList.add(target_pid_bytes);
			protoList.add(source_pid_bytes);
			protoList.add(extends_bytes);
			
			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("合并-pid 到PID字节组合的操作失败."+e.getMessage());
			return null;
		}
	}
	/**
	 * 报警走的协议添加/删除-lid 下多值PID
	 * 
	 * @param operator_type:操作类型0:添加1：删除
	 * @param lid
	 * @param xmlStr:内容.
	 * @return
	 */
	private static byte[] waring_add_del_lid_pid_bytes(short data_type,short operator_type, String lid,String xmlStr ) {
		try {
			if ( !StringUtil.isNotBlank(lid) && !StringUtil.isNotBlank(lid)) {
				logger.error("传入的lid不能为空!");
				return null;
			}
						
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();
			
			//操作类型.
			byte[] operator_bytes = DataConvert.shortToBytes(operator_type);
			total_data_len += 2;
			//原始数据类型.
			byte[] data_type_bytes = DataConvert.shortToBytes(data_type);
			total_data_len += 2;
			//lid
			byte[] lid_bytes = trunTo32Bytes(lid);
			total_data_len += 32;
			
			//xml_str
			byte[] xml_str_bytes = xmlStr.getBytes();
			total_data_len += xml_str_bytes.length+4;
			byte[] xml_length_bytes =DataConvert.intToByteArray(xml_str_bytes.length);
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += 4;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(ADD_DEL_LID_PID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(operator_bytes);
			protoList.add(data_type_bytes);
			protoList.add(lid_bytes);
			protoList.add(xml_length_bytes);
			protoList.add(xml_str_bytes);
			protoList.add(extends_bytes);

			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("合并-pid 到PID字节组合的操作失败."+e.getMessage());
			return null;
		}
	}
	/**
	 * 删除-lid 下的pic
	 * 
	 * @param operator_type:操作类型0:添加1：删除
	 * @param lid
	 * @param xmlStr:内容.
	 * @return
	 */
	private static byte[] del_lid_pid_pic_bytes(short operator_type, String pid,String xmlStr ) {
		try {
			if ( !StringUtil.isNotBlank(pid) && !StringUtil.isNotBlank(pid)) {
				logger.error("传入的pid不能为空!");
				return null;
			}
						
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();
			
			//操作类型.
			byte[] operator_bytes = DataConvert.shortToBytes(operator_type);
			total_data_len += 2;
			
			//lid
			byte[] pid_bytes = trunTo32Bytes(pid);
			total_data_len += 32;
			total_data_len += 32;
			//xml_str
			byte[] xml_str_bytes = xmlStr.getBytes();
			
			byte[] xml_lengthbytes = DataConvert.intToByteArray(xml_str_bytes.length);
			total_data_len += 4;
			
			
			total_data_len += xml_str_bytes.length;
			
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += 4;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(DEL_PID_LID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(operator_bytes);
			protoList.add(pid_bytes);
			protoList.add(trunTo32Bytes("0"));
			protoList.add(xml_lengthbytes);
			protoList.add(xml_str_bytes);
			protoList.add(extends_bytes);

			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("合并-pid 到PID字节组合的操作失败."+e.getMessage());
			return null;
		}
	}
	/**
	 * 添加/删除-lid 下多值PID
	 * 
	 * @param operator_type:操作类型0:添加1：删除
	 * @param lid
	 * @param xmlStr:内容.
	 * @return
	 */
	private static byte[] add_del_lid_pid_bytes(short operator_type, String lid,String xmlStr ) {
		try {
			if ( !StringUtil.isNotBlank(lid) && !StringUtil.isNotBlank(lid)) {
				logger.error("传入的lid不能为空!");
				return null;
			}
						
			int total_data_len = 0;
			List<byte[]> protoList = new ArrayList<byte[]>();
			
			//操作类型.
			byte[] operator_bytes = DataConvert.shortToBytes(operator_type);
			total_data_len += 2;
			
			//lid
			byte[] lid_bytes = trunTo32Bytes(lid);
			total_data_len += 32;
			
			//xml_str
			byte[] xml_str_bytes = xmlStr.getBytes();
			
			byte[] xml_lengthbytes = DataConvert.intToByteArray(xml_str_bytes.length);
			total_data_len += 4;
			
			
			total_data_len += xml_str_bytes.length;
			
			//扩展长度
			int extends_len = 0;
			byte[] extends_bytes = DataConvert.intToByteArray(extends_len);
			total_data_len += 4;
			
			//协议头.
			byte[] headProto = send_ManS_HaeadProtocol(ADD_DEL_LID_PID, total_data_len);
			//logger.info("数据包头的长度是："+headProto.length+",协议长度是："+total_data_len);
			
			//数据包头
			protoList.add(headProto);
			//子数据部分
			protoList.add(operator_bytes);
			protoList.add(lid_bytes);
			protoList.add(xml_lengthbytes);
			protoList.add(xml_str_bytes);
			protoList.add(extends_bytes);

			//最终的字节文件.
			byte[] protocol_bytes = DataConvert.sysCopy(protoList);
			//返回.
			return protocol_bytes;
		} catch (Exception e) {
			logger.error("合并-pid 到PID字节组合的操作失败."+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 协议头拼装.
	 * 
	 * 管理系统->ID 分配系统.
	 * 
	 * @param protocol_type:协议类型.
	 * @param type:类型.
	 * @param command_type:命令类型.2.给人物添加pid;3.删除pid下的lid;4.合并-lid 到PID;5.合并-pid 到PID;8.添加/删除-lid 下多值PID
	 * @param total_data_len:数据总长.
	 * @return
	 */
	private static byte[] send_ManS_HaeadProtocol(short protocol_type,short type,short command_type,int total_data_len){
		try {
			List<byte[]> protoList = new ArrayList<byte[]>();
			//验证码
			byte[] verify_code_byte = IDENTIFYING_CODE.getBytes();//[8]验证码
			//byte[] data_len_byte= DataConvert.intToByteArray(total_data_len + 32);//[4]数据总长
			byte[] data_len_byte= DataConvert.intToByteArray(total_data_len + HEAD_SIZE);//[4]数据总长
			byte[] protocol_type_byte = DataConvert.shortToBytes(protocol_type);//[2]协议类型
			byte[] type_byte = DataConvert.shortToBytes(type);//[2]类型
			byte[] command_type_byte = DataConvert.shortToBytes(command_type);//[2]命令类型

			protoList.add(verify_code_byte);//[8]验证码
			protoList.add(data_len_byte);//[4]数据总长
			protoList.add(protocol_type_byte);//[2]协议类型
			protoList.add(type_byte); //type.
			protoList.add(RETAIN_FIELD_14);//[14]保留字段
			protoList.add(command_type_byte);//[2]命令类型
			
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
	 * 管理系统->ID 分配系统.
	 * 
	 * @param command_type:命令类型.2.给人物添加pid;3.删除pid下的lid;4.合并-lid 到PID;5.合并-pid 到PID;8.添加/删除-lid 下多值PID
	 * 
	 * @param total_data_len:数据总长.
	 * @return
	 */
	private static byte[] send_ManS_HaeadProtocol(short command_type,int total_data_len){
		try {
			List<byte[]> protoList = new ArrayList<byte[]>();
			//验证码
			byte[] verify_code_byte = IDENTIFYING_CODE.getBytes();//[8]验证码
			//byte[] data_len_byte= DataConvert.intToByteArray(total_data_len + 32);//[4]数据总长
			byte[] data_len_byte= DataConvert.intToByteArray(total_data_len + HEAD_SIZE);//[4]数据总长
			//logger.info("数据总长是："+(total_data_len + HEAD_SIZE));
			short protocl_type = 3;
			byte[] protocol_type_byte = DataConvert.shortToBytes(protocl_type);//[2]协议类型
			short type = 0;
			byte[] type_byte = DataConvert.shortToBytes(type);//[2]类型
			byte[] command_type_byte = DataConvert.shortToBytes(command_type);//[2]命令类型
			protoList.add(verify_code_byte);//[8]验证码
			protoList.add(data_len_byte);//[4]数据总长
			protoList.add(protocol_type_byte);//[2]协议类型
			protoList.add(type_byte);
			protoList.add(RETAIN_FIELD_14);//[14]保留字段
			protoList.add(command_type_byte);//[2]命令类型
			byte[] proto_header = DataConvert.sysCopy(protoList);
			//logger.info("请求数据包头的信息是："+new String(proto_header));
			return proto_header;			
		} catch (Exception e) {
			logger.error("发送的协议头信息失败."+e.getMessage());
			return null;	
		}
	} 
	
}
