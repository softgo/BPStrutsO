package com.javabase.base.execl.demo;

public class DataOne {
	
	private int roleId;
	private String roleName;
	private String permissionName;
	
	public DataOne() {
		super();
	}

	public DataOne(int roleId, String roleName, String permissionName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.permissionName = permissionName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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