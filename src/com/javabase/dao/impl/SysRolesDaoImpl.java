package com.javabase.dao.impl;

import org.springframework.stereotype.Repository;

import com.javabase.base.impl.BaseDaoImpl;
import com.javabase.entity.SysRoles;
import com.javabase.entity.SysUsersRoles;
import com.javabase.dao.SysRolesDao;

/**
 * 接口实现.
 * 
 * @author bruce
 *
 */
 
 @Repository("sysRolesDao")
public class SysRolesDaoImpl extends BaseDaoImpl<SysRoles> implements SysRolesDao{

	@Override
	public void deleteUsersRoles(String userId) {
		getSqlSession().delete("sysroles.deleteUsersRoles",userId);
	}

	@Override
	public void saveUsersRoles(SysUsersRoles userRoles) {
		getSqlSession().insert("sysroles.saveUsersRoles",userRoles);
	}

	@Override
	public SysRoles findByUserRoles(String userId) {
		return getSqlSession().selectOne("sysroles.findByUserRoles",userId);
	}
}
