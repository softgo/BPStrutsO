package com.javabase.code.gengerate.def;

import java.util.ResourceBundle;

/**
 * 
 * 获得数据资源
 * 
 * @author bruce.
 * 
 */

public class CodeResourceUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("javabase/javabase_database");
	
	private static final ResourceBundle bundlePath = ResourceBundle.getBundle("javabase/javabase_config");

	public static String DIVER_NAME = "com.mysql.jdbc.Driver";

	public static String URL = "jdbc:mysql://localhost:3306/javabase?useUnicode=true&characterEncoding=UTF-8";

	public static String USERNAME = "root";

	public static String PASSWORD = "mysql";

	public static String DATABASE_NAME = "model";

	public static String DATABASE_TYPE = "mysql";

	public static String DATABASE_TYPE_MYSQL = "mysql";

	public static String DATABASE_TYPE_ORACLE = "oracle";

	public static String JAVABASE_UI_FIELD_REQUIRED_NUM = "4";

	public static String JAVABASE_UI_FIELD_SEARCH_NUM = "3";

	public static String web_root_package = "WebRoot";

	public static String source_root_package = "src";

	public static String bussiPackage = "sun";

	public static String bussiPackageUrl = "sun";

	public static String entity_package = "entity";

	public static String page_package = "page";

	public static String ENTITY_URL;

	public static String PAGE_URL;

	public static String ENTITY_URL_INX;

	public static String PAGE_URL_INX;

	public static String TEMPLATEPATH;

	public static String CODEPATH;

	public static String JSPPATH;

	public static String JAVABASE_GENERATE_TABLE_ID;

	public static String JAVABASE_GENERATE_UI_FILTER_FIELDS;

	public static String SYSTEM_ENCODING;

	/**
	 * 初始化数字操作。 
	 */
	static {
		
		DIVER_NAME = getDIVER_NAME();
		
		URL = getURL();
		
		USERNAME = getUSERNAME();
		
		PASSWORD = getPASSWORD();
		
		DATABASE_NAME = getDATABASE_NAME();

		SYSTEM_ENCODING = getSYSTEM_ENCODING();
		
		TEMPLATEPATH = getTEMPLATEPATH();
		
		source_root_package = getSourceRootPackage();
		
		web_root_package = getWebRootPackage();
		
		bussiPackage = getBussiPackage();
		
		bussiPackageUrl = bussiPackage.replace(".", "/");

		JAVABASE_GENERATE_TABLE_ID = getJavaBase_generate_table_id();

		JAVABASE_UI_FIELD_SEARCH_NUM = getJavaBase_ui_search_filed_num();

		/**
		 * 设置数据库。
		 */
		if ((URL.indexOf("mysql") >= 0) || (URL.indexOf("MYSQL") >= 0))
			DATABASE_TYPE = DATABASE_TYPE_MYSQL;
		else if ((URL.indexOf("oracle") >= 0) || (URL.indexOf("ORACLE") >= 0)) {
			DATABASE_TYPE = DATABASE_TYPE_ORACLE;
		}

		source_root_package = source_root_package.replace(".", "/");
		
		web_root_package = web_root_package.replace(".", "/");

		ENTITY_URL = source_root_package + "/" + bussiPackageUrl + "/"+ entity_package + "/";

		PAGE_URL = source_root_package + "/" + bussiPackageUrl + "/"+ page_package + "/";

		ENTITY_URL_INX = bussiPackage + "." + entity_package + ".";

		PAGE_URL_INX = bussiPackage + "." + page_package + ".";

		CODEPATH = source_root_package + "/" + bussiPackageUrl + "/";

		JSPPATH = web_root_package + "/" + bussiPackageUrl + "/";
	}

	private void ResourceUtil() {}

	public static final String getDIVER_NAME() {
		return bundle.getString("diver_name");
	}

	public static final String getURL() {
		return bundle.getString("url");
	}

	public static final String getUSERNAME() {
		return bundle.getString("username");
	}

	public static final String getPASSWORD() {
		return bundle.getString("password");
	}

	public static final String getDATABASE_NAME() {
		return bundle.getString("database_name");
	}

	private static String getBussiPackage() {
		return bundlePath.getString("bussi_package");
	}

	public static final String getEntityPackage() {
		return bundlePath.getString("entity_package");
	}

	public static final String getPagePackage() {
		return bundlePath.getString("page_package");
	}

	public static final String getTEMPLATEPATH() {
		return bundlePath.getString("templatepath");
	}

	public static final String getSourceRootPackage() {
		return bundlePath.getString("source_root_package");
	}

	public static final String getWebRootPackage() {
		return bundlePath.getString("webroot_package");
	}

	public static final String getSYSTEM_ENCODING() {
		return bundlePath.getString("system_encoding");
	}

	public static final String getJavaBase_generate_table_id() {
		return bundlePath.getString("javabase_generate_table_id");
	}

	public static final String getJavaBase_generate_ui_filter_fields() {
		return bundlePath.getString("javabase_generate_ui_filter_fields");
	}

	public static final String getJavaBase_ui_search_filed_num() {
		return bundlePath.getString("javabase_ui_search_filed_num");
	}

	public static final String getJavaBase_ui_field_required_num() {
		return bundlePath.getString("javabase_ui_field_required_num");
	}

}
