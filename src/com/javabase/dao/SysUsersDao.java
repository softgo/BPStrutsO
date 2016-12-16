package com.javabase.dao;

import com.javabase.base.BaseDao;
import com.javabase.entity.SysUsers;

/**
 * 接口定义
 * 
 * @author bruce
 *
 */
 
public interface SysUsersDao extends BaseDao<SysUsers> {
	
	public int countUser(String userName,String userPass);
	
}
