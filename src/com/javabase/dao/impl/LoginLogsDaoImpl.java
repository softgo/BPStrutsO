package com.javabase.dao.impl;

import org.springframework.stereotype.Repository;
import com.javabase.base.impl.BaseDaoImpl;

import com.javabase.entity.LoginLogs;
import com.javabase.dao.LoginLogsDao;

/**
 * 接口实现.
 * 
 * @author bruce
 *
 */
 
@Repository("loginLogsDao")
public class LoginLogsDaoImpl extends BaseDaoImpl<LoginLogs> implements LoginLogsDao{
	
}
