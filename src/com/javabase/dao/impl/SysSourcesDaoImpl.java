package com.javabase.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.javabase.base.impl.BaseDaoImpl;
import com.javabase.entity.SysSources;
import com.javabase.entity.SysSourcesRoles;
import com.javabase.dao.SysSourcesDao;

/**
 * 接口实现.
 * 
 * @author bruce
 *
 */
 
 @Repository("sysSourcesDao")
public class SysSourcesDaoImpl extends BaseDaoImpl<SysSources> implements SysSourcesDao{

	@Override
	public List<SysSources> getUserSysSources(String userId) {
		return getSqlSession().selectList("syssources.getUserSysSources",userId);
	}

	@Override
	public List<SysSources> getSysSourcesByUserName(String userName) {
		return getSqlSession().selectList("syssources.getSysSourcesByUserName",userName);
	}

	@Override
	public void saveSysSourcesRoles(SysSourcesRoles sourceRoles) {
		getSqlSession().insert("syssources.saveSysSourcesRoles",sourceRoles);
	}

	@Override
	public List<SysSources> getSysSourcesRoles(String roleId) {
		return getSqlSession().selectList("syssources.getSysSourcesRoles", roleId);
	}

	@Override
	public void deleteSysSourcesRoles(String roleId) {
		getSqlSession().delete("syssources.deleteSysSourcesRoles",roleId);
	}
	
}
