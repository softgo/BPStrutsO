package com.javabase.entity;

import java.util.*;
import java.util.Date;
import java.io.*;

/**
 * 实体bean
 * 
 * @author bruce
 *
 */

@SuppressWarnings("serial")
public class SysUsers implements Serializable {

	private final long serialVersionUID = 1L;	private Integer sysUserId;//   系统用户的id	private Integer parentId;//   系统用户父级id	private String sysUserName;//   系统用户的名字
	private String roleName;  //    角色名字	private String sysUserPass;//   系统登陆密码	private Integer sysUserSex;//   用户的性别: 1:男,0:女	private String sysUserAddress;//   住址	private String sysUserPhone;//   手机号码	private String sysUserMail;//   邮箱地址	private String sysUserQq;//   联系qq	private Date registerTime;//   注册时间	private Integer sysStatus;//   用户状态;1:启用,0:弃用	
	private Set<SysRoles> roles = new HashSet<SysRoles>(0);
		public Integer getSysUserId() {	    return this.sysUserId;	}	public void setSysUserId(Integer sysUserId) {	    this.sysUserId=sysUserId;	}		public Integer getParentId() {	    return this.parentId;	}	public void setParentId(Integer parentId) {	    this.parentId=parentId;	}		public String getSysUserName() {	    return this.sysUserName;	}	public void setSysUserName(String sysUserName) {	    this.sysUserName=sysUserName;	}		public String getSysUserPass() {	    return this.sysUserPass;	}	public void setSysUserPass(String sysUserPass) {	    this.sysUserPass=sysUserPass;	}		public Integer getSysUserSex() {	    return this.sysUserSex;	}	public void setSysUserSex(Integer sysUserSex) {	    this.sysUserSex=sysUserSex;	}		public String getSysUserAddress() {	    return this.sysUserAddress;	}	public void setSysUserAddress(String sysUserAddress) {	    this.sysUserAddress=sysUserAddress;	}		public String getSysUserPhone() {	    return this.sysUserPhone;	}	public void setSysUserPhone(String sysUserPhone) {	    this.sysUserPhone=sysUserPhone;	}		public String getSysUserMail() {	    return this.sysUserMail;	}	public void setSysUserMail(String sysUserMail) {	    this.sysUserMail=sysUserMail;	}		public String getSysUserQq() {	    return this.sysUserQq;	}	public void setSysUserQq(String sysUserQq) {	    this.sysUserQq=sysUserQq;	}		public Date getRegisterTime() {	    return this.registerTime;	}	public void setRegisterTime(Date registerTime) {	    this.registerTime=registerTime;	}		public Integer getSysStatus() {	    return this.sysStatus;	}	public void setSysStatus(Integer sysStatus) {	    this.sysStatus=sysStatus;	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}

