package com.javabase.base.execl.demo;

public class DataThree {
	
	private String realName;
	private String roleName;
	private String permissionName;
	
	public DataThree() {
		super();
	}
	public DataThree(String realName, String roleName, String permissionName) {
		super();
		this.realName = realName;
		this.roleName = roleName;
		this.permissionName = permissionName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	
	
}