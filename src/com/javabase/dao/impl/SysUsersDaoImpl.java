package com.javabase.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javabase.base.impl.BaseDaoImpl;
import com.javabase.entity.SysUsers;
import com.javabase.dao.SysUsersDao;

/**
 * 接口实现.
 * 
 * @author bruce
 *
 */
 
 @Repository("sysUsersDao")
public class SysUsersDaoImpl extends BaseDaoImpl<SysUsers> implements SysUsersDao{

	@Override
	public int countUser(String userName, String userPass) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("sysUserName", userName);
		map.put("sysUserPass", userPass);
		return getSqlSession().selectOne("sysusers.countUser", map);
	}

}
