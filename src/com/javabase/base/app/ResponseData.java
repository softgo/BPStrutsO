package com.javabase.base.app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * app 端获取数据的工具类.
 * 
 * 其他类都必须继承自这个类才可以在 app 端正常使用.
 * 
 * 在 org.apache.commons.lang3.包下.
 * 
 * @author bruce.
 *
 */
public class ResponseData implements Serializable{

	private static final long serialVersionUID = 1L;
	//app端返回的状态判断标识.
	private String flag; 
	//消息提示信息.
	private String msg;
	//返回的数据集合.
	private Map<String, Object> data = new HashMap<String, Object>();
	
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	
	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	
	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * @return the data
	 */
	public Map<String, Object> getData() {
		return data;
	}
	
	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	/**
	 * add data
	 * 
	 * @param key
	 * @param value
	 */
	public void addData(String key, Object value) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
	}
	
	/**
	 * get data
	 * @param key
	 * @return
	 */
	public Object getDataByKey(String key) {
		return data.get(key);
	}
	
	/**
	 * toString()
	 * 
	 */
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
		        ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
