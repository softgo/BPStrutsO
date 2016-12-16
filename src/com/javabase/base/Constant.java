package com.javabase.base;

import java.util.HashMap;
import java.util.Map;

import com.javabase.entity.SysUsers;

/**
 * <b>function:</b> 公共Constant
 * 存放全局变量
 * @author bruce
 * @createDate 1970-1-1
 * @file Constant.java
 * @package com.javabase.base
 * @project JavaBase
 * @version 1.0
 */
public class Constant {
	
	//每页显示数据.
	public static int page_num = 1;

	//用来存放全局的变量.
	public static Map<Object, Object> constMap = new HashMap<Object, Object>();
	
	//用来存放日志使用.
	public static Map<String, SysUsers> userMap = new HashMap<String, SysUsers>();
	
    public static final String UTF8 = "UTF-8";
}