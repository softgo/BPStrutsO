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
public class SysRoles implements Serializable {

	private final long serialVersionUID = 1L;	private Integer roleId;//   角色id	private Integer parentId;//   角色父级id	private String roleName;//   角色名字	private String roleKey;//   角色key	private String description;//   角色介绍	private Integer roleStatus;//   角色状态;1:启用,0:弃用
	
	private Set<SysSources> resources = new HashSet<SysSources>(0);		public Integer getRoleId() {	    return this.roleId;	}	public void setRoleId(Integer roleId) {	    this.roleId=roleId;	}		public Integer getParentId() {	    return this.parentId;	}	public void setParentId(Integer parentId) {	    this.parentId=parentId;	}		public String getRoleName() {	    return this.roleName;	}	public void setRoleName(String roleName) {	    this.roleName=roleName;	}		public String getRoleKey() {	    return this.roleKey;	}	public void setRoleKey(String roleKey) {	    this.roleKey=roleKey;	}		public String getDescription() {	    return this.description;	}	public void setDescription(String description) {	    this.description=description;	}		public Integer getRoleStatus() {	    return this.roleStatus;	}	public void setRoleStatus(Integer roleStatus) {	    this.roleStatus=roleStatus;	}
	
}

