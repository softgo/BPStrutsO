package com.javabase.base.app;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * app 端请求数据的工具类.
 * 
 * 其他类都必须继承自这个类才可以在 app 端正常使用.
 * 
 * 在 org.apache.commons.lang3.包下.
 * 
 * @author bruce.
 *
 */
public class RequestData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 当前所在session的id
	private String sessionId;
	
	// 用户标识 code 根据实际情况去定
	private Object userCode;
	
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * @param sessionId
	 *     the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the userCode
	 */
    public Object getUserCode() {
	    return userCode;
    }

	/**
	 * @param userCode the userCode to set
	 */
    public void setUserCode(Object userCode) {
	    this.userCode = userCode;
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
