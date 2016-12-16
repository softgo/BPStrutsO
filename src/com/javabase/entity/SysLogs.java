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
public class SysLogs implements Serializable {

	private final long serialVersionUID = 1L;	private Integer logId;//   log ID	private String sysUserId;//   系统用户登陆id	private String sysUserName;//   系统用户登陆名字	private String module;//   操作哪个模块	private String action;//   操作详述	private String userIp;//   登陆ip	private Date operatorTime;//   操作时间	private Integer logStatus;//   日志状态;1:可见,0:不可见		public Integer getLogId() {	    return this.logId;	}	public void setLogId(Integer logId) {	    this.logId=logId;	}		public String getSysUserId() {	    return this.sysUserId;	}	public void setSysUserId(String sysUserId) {	    this.sysUserId=sysUserId;	}		public String getSysUserName() {	    return this.sysUserName;	}	public void setSysUserName(String sysUserName) {	    this.sysUserName=sysUserName;	}		public String getModule() {	    return this.module;	}	public void setModule(String module) {	    this.module=module;	}		public String getAction() {	    return this.action;	}	public void setAction(String action) {	    this.action=action;	}		public String getUserIp() {	    return this.userIp;	}	public void setUserIp(String userIp) {	    this.userIp=userIp;	}		public Date getOperatorTime() {	    return this.operatorTime;	}	public void setOperatorTime(Date operatorTime) {	    this.operatorTime=operatorTime;	}		public Integer getLogStatus() {	    return this.logStatus;	}	public void setLogStatus(Integer logStatus) {	    this.logStatus=logStatus;	}
	
}

