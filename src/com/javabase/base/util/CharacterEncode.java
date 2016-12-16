package com.javabase.base.util;


import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 字符编码的转换
 * @author bruce.
 *
 */
public class CharacterEncode {
	
	public static String getISO_GBK(String oldStr) {
		if(oldStr == null){
			return oldStr;
		}
		String newStr = null;
		try {
			newStr = new String(oldStr.getBytes("iso-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}
	
	public static String getISO_UTF(String oldStr) {
		if(oldStr == null){
			return oldStr;
		}
		String newStr = null;
		try {
			newStr = new String(oldStr.getBytes("iso-8859-1"), "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}
	
	public static String getUTF_ISO(String oldStr) {
		if(oldStr == null){
			return oldStr;
		}
		String newStr = null;
		try {
			newStr = new String(oldStr.getBytes("utf8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}
	
	public static String getUTF_GBK(String oldStr) {
		if(oldStr == null){
			return oldStr;
		}
		String newStr = null;
		try {
			newStr = new String(oldStr.getBytes("utf8"), "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}
	
	public static String getGBK_UTF(String oldStr) {
		if(oldStr == null){
			return oldStr;
		}
		String newStr = null;
		try {
			newStr = new String(oldStr.getBytes("gbk"), "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}
	
	public static String getGBK_ISO(String oldStr) {
		if(oldStr == null){
			return oldStr;
		}
		String newStr = null;
		try {
			newStr = new String(oldStr.getBytes("gbk"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}
	
	public  static void setParmsEncoding(ServletRequest request, ServletResponse response)
	throws Exception {
		
		
		// 设置响应模式
		response.setContentType("text/html;charset=UTF-8");
		// 判断客户端的提交方式
		if (((HttpServletRequest) request).getMethod().equalsIgnoreCase("POST")) {
			// POST方式提交
			// 设置请求字符集
			request.setCharacterEncoding("UTF-8");
		} else {
			// 处理GET //获取所有的提交参数
			Enumeration names = request.getParameterNames();
			// 遍历
			while (names.hasMoreElements()) {
				// 参数名
				String name = (String) names.nextElement();
				// 因为不知道参数是多值还是单值
				String values[] = request.getParameterValues(name);
				// 循环处理字符集转换
				for (int i = 0; i < values.length; i++) {
					values[i] = toUTF8(values[i]);
				}
			}
		}
	}
	
	// 转换方法(内部私有方法)
	private static String toUTF8(String str) {
		String rt = null;
		try {
			if (str != null) {
				// 转换新字符集
				rt = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return rt;
	}
	
}

