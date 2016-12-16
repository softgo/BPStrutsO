package com.javabase.dao;

import com.javabase.base.BaseDao;
import com.javabase.entity.SysRoles;
import com.javabase.entity.SysUsersRoles;

/**
 * 接口定义
 * 
 * @author bruce
 *
 */
 
public interface SysRolesDao extends BaseDao<SysRoles> {
	
	public void deleteUsersRoles(String userId);
	
	public void saveUsersRoles(SysUsersRoles userRoles);
	
	public SysRoles findByUserRoles(String userId);
	
}
