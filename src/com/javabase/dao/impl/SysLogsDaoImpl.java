package com.javabase.dao.impl;

import org.springframework.stereotype.Repository;
import com.javabase.base.impl.BaseDaoImpl;

import com.javabase.entity.SysLogs;
import com.javabase.dao.SysLogsDao;

/**
 * 接口实现.
 * 
 * @author bruce
 *
 */
 
 @Repository("sysLogsDao")
public class SysLogsDaoImpl extends BaseDaoImpl<SysLogs> implements SysLogsDao{
	
}
