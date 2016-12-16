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

	private final long serialVersionUID = 1L;
	public LoginLogs(){
		
	} 
	
	public LoginLogs(Integer sysUserId,String sysUserName,String loginIp){
		this.sysUserId = sysUserId;
		this.sysUserName = sysUserName;
		this.loginIp = loginIp;
	} 
	
	    return this.loginTime;
	
}
