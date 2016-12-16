package com.javabase.dao;

import java.util.List;

import com.javabase.base.BaseDao;
import com.javabase.entity.SysSources;
import com.javabase.entity.SysSourcesRoles;

/**
 * 接口定义
 * 
 * @author bruce
 *
 */
 
public interface SysSourcesDao extends BaseDao<SysSources> {
	//根据用户Id获取该用户的权限
	public List<SysSources> getUserSysSources(String userId);
	//根据角色Id获取该角色的权限
	public List<SysSources> getSysSourcesRoles(String roleId);
	//根据用户名获取该用户的权限
	public List<SysSources> getSysSourcesByUserName(String userName);
	
	public void saveSysSourcesRoles(SysSourcesRoles sourceRoles);
	
	public void deleteSysSourcesRoles(String roleId);
	
}