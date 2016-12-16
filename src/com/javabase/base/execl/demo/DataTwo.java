package com.javabase.base.execl.demo;

public class DataTwo {
	
	private int roleId;
	
	private String realName;

	public DataTwo(int roleId, String realName) {
		super();
		this.roleId = roleId;
		this.realName = realName;
	}

	public DataTwo() {
		super();
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	};
	
	
}