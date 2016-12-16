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

	private final long serialVersionUID = 1L;
	public SysSourcesRoles() {
		super();
	}
	
	public SysSourcesRoles(Integer sourceId, Integer roleId) {
		super();
		this.sourceId = sourceId;
		this.roleId = roleId;
	}
	
	public Integer getSourceId() {
	
}
