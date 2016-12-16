package com.javabase.entity;

import java.io.*;

/**
 * 实体bean
 * 
 * @author bruce
 *
 */

@SuppressWarnings("serial")
public class SysUsersRoles implements Serializable {

	private final long serialVersionUID = 1L;
	public SysUsersRoles(){
		
	}
	
	public SysUsersRoles(Integer sysUserId,Integer roleId){
		this.sysUserId = sysUserId;
		this.roleId = roleId;
	}
	
	
}
