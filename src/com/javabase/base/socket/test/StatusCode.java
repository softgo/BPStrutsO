package com.javabase.base.socket.test;

import org.apache.log4j.Logger;

/**
 * 人物库管理后台与集成管理后台通信协议状态码返回处理.
 * 
 * @date 2014-12-1.
 * 
 * @author admin
 *
 */
public class StatusCode {

	private static Logger logger = Logger.getLogger(StatusCode.class.getName()); 
	
	/**
	 * 根据状态码返回问题.
	 * @param status_code
	 * @return
	 */
	public static void getCodes(int status_code) {
		try {
			if (status_code >-29999 && status_code < -20000) {
				logger.error("管理后台出现了问题!");
			}
			if (status_code >-39999 && status_code < -30000) {
				logger.error("采集系统出现了问题!");
			}
			if (status_code >-49999 && status_code < -40000) {
				logger.error("处理系统出现了问题!");
			}
			if (status_code >-59999 && status_code < -50000) {
				logger.error("集成系统出现了问题!");
			}
			if (status_code >-69999 && status_code < -60000) {
				logger.error("链接过滤出现了问题!");
			}
			if (status_code >-99999 && status_code < -70000) {
				logger.error("索引检索出现了问题!");
			}
		} catch (Exception e) {
			logger.error("获取返回信息出错了."+e.getMessage());
		}
	}
	
	/**
	 * 根据状态码返回问题.
	 * @param status_code
	 * @return
	 */
	public static  String getFalseByCodes(int status_code) {
		try {
			if (status_code >-29999 && status_code < -20000) {
				//logger.error("管理后台出现了问题!");
				return "管理后台出现了问题!";
			}
			if (status_code >-39999 && status_code < -30000) {
				//logger.error("采集系统出现了问题!");
				return "采集系统出现了问题!";
			}
			if (status_code >-49999 && status_code < -40000) {
				//logger.error("处理系统出现了问题!");
				return "处理系统出现了问题!";
			}
			if (status_code >-59999 && status_code < -50000) {
				//logger.error("集成系统出现了问题!");
				return"集成系统出现了问题!";
			}
			if (status_code >-69999 && status_code < -60000) {
				//logger.error("链接过滤出现了问题!");
				return"链接过滤出现了问题!";
			}
			if (status_code >-99999 && status_code < -70000) {
				//logger.error("索引检索出现了问题!");
				return"索引检索出现了问题!";
			}
		} catch (Exception e) {
			return"获取返回信息出错了."+e.getMessage();
		}
		return null;
	}
	
}
