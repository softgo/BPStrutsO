package com.javabase.dao.impl;

import org.springframework.stereotype.Repository;
import com.javabase.base.impl.BaseDaoImpl;

import com.javabase.entity.SysServerInfos;
import com.javabase.dao.SysServerInfosDao;

/**
 * 接口实现.
 * 
 * @author bruce
 *
 */
 
 @Repository("sysServerInfosDao")
public class SysServerInfosDaoImpl extends BaseDaoImpl<SysServerInfos> implements SysServerInfosDao{
	
}
