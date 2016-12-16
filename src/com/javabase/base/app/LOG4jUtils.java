package com.javabase.base.app;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.javabase.base.util.PropertiesUtils;

/**
 * 日志记录的方式
 * 
 * @author bruce
 *
 */
public class LOG4jUtils {

	private static String propsFilePath = "/configPros/logger.properties";
	private static Properties properties = null;
	static{
		if (properties==null) {
			synchronized (properties) {
				properties = PropertiesUtils.getProperties(propsFilePath);
			}
		}
	}
	
	private static Logger auditLogger = Logger.getLogger("auditLogger");
	private static Logger errorLogger = Logger.getLogger("errorLogger");
	private static Logger payLogger = Logger.getLogger("payLogger");
	private static Logger payErrLogger = Logger.getLogger("payErrLogger");
	private static Logger tracerLogger = Logger.getLogger("tracerLogger");
	private static Logger asyncLogger = Logger.getLogger("asyncLogger");	
	private static Logger outsystemAuditLogger = Logger.getLogger("outsystemAuditLogger");
	private static Logger outsystemErrorLogger = Logger.getLogger("outsystemErrorLogger");
	
	
	public static void audit(String message) {
		auditLogger.info(message);
	}
	
	public static void error(String message) {
		errorLogger.error(message);
	}
	
	public static void error(String message, Throwable throwable) {
		errorLogger.error(message, throwable);
	}
	
	public static void payLog(String message) {
		payLogger.info(message);
	}
	
	public static void payErrLog(String message, Throwable throwable) {
		payErrLogger.error(message, throwable);
	}
	
	public static void tracer(String message) {
		tracerLogger.info(message);
	}
	
	public static void asyncLog(String message) {
		asyncLogger.info(message);
	}
	
	public static void outsystemAuditLog(String message) {
		outsystemAuditLogger.info(message);
	}
	
	public static void outsystemErrorLog(String message) {
		outsystemErrorLogger.info(message);
	}
	
	public static void outsystemErrorLog(String message, Throwable e) {
		outsystemErrorLogger.info(message, e);
	}
	
}
