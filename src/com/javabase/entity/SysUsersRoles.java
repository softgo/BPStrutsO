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

	private final long serialVersionUID = 1L;	private Integer sysUserId;//   	private Integer roleId;//   	private String appId;//   应用id	private String channelId;//   频道id	private String shopId;//   商铺id	
	public SysUsersRoles(){
		
	}
	
	public SysUsersRoles(Integer sysUserId,Integer roleId){
		this.sysUserId = sysUserId;
		this.roleId = roleId;
	}
		public Integer getSysUserId() {	    return this.sysUserId;	}	public void setSysUserId(Integer sysUserId) {	    this.sysUserId=sysUserId;	}		public Integer getRoleId() {	    return this.roleId;	}	public void setRoleId(Integer roleId) {	    this.roleId=roleId;	}		public String getAppId() {	    return this.appId;	}	public void setAppId(String appId) {	    this.appId=appId;	}		public String getChannelId() {	    return this.channelId;	}	public void setChannelId(String channelId) {	    this.channelId=channelId;	}		public String getShopId() {	    return this.shopId;	}	public void setShopId(String shopId) {	    this.shopId=shopId;	}
	
}

