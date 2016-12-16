package com.javabase.entity;

import java.io.*;

/**
 * 实体bean
 * 
 * @author bruce
 *
 */

@SuppressWarnings("serial")
public class SysSourcesRoles implements Serializable {

	private final long serialVersionUID = 1L;	private Integer sourceId;//   资源id	private Integer roleId;//   角色id	
	public SysSourcesRoles() {
		super();
	}
	
	public SysSourcesRoles(Integer sourceId, Integer roleId) {
		super();
		this.sourceId = sourceId;
		this.roleId = roleId;
	}
	
	public Integer getSourceId() {	    return this.sourceId;	}	public void setSourceId(Integer sourceId) {	    this.sourceId=sourceId;	}		public Integer getRoleId() {	    return this.roleId;	}	public void setRoleId(Integer roleId) {	    this.roleId=roleId;	}
	
}

