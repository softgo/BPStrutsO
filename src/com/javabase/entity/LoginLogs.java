package com.javabase.entity;

import java.util.*;
import java.io.*;

/**
 * 实体bean
 * 
 * @author bruce
 *
 */

@SuppressWarnings("serial")
public class LoginLogs implements Serializable {

	private final long serialVersionUID = 1L;	private Integer loginId;//   登陆id	private Integer sysUserId;//   系统登陆用户id	private String sysUserName;//   系统登陆用户名	private Date loginTime;//   登陆时间,默认是当前时间	private String loginIp;//   登陆ip	private Integer loginStatus;//   日志状态;1:可见,0:不可见	
	public LoginLogs(){
		
	} 
	
	public LoginLogs(Integer sysUserId,String sysUserName,String loginIp){
		this.sysUserId = sysUserId;
		this.sysUserName = sysUserName;
		this.loginIp = loginIp;
	} 
		public Integer getLoginId() {	    return this.loginId;	}	public void setLoginId(Integer loginId) {	    this.loginId=loginId;	}		public Integer getSysUserId() {	    return this.sysUserId;	}	public void setSysUserId(Integer sysUserId) {	    this.sysUserId=sysUserId;	}		public String getSysUserName() {	    return this.sysUserName;	}	public void setSysUserName(String sysUserName) {	    this.sysUserName=sysUserName;	}		public Date getLoginTime() {
	    return this.loginTime;	}	public void setLoginTime(Date loginTime) {	    this.loginTime=loginTime;	}		public String getLoginIp() {	    return this.loginIp;	}	public void setLoginIp(String loginIp) {	    this.loginIp=loginIp;	}		public Integer getLoginStatus() {	    return this.loginStatus;	}	public void setLoginStatus(Integer loginStatus) {	    this.loginStatus=loginStatus;	}
	
}

