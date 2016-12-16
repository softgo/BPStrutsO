package com.javabase.controller;

import java.net.InetAddress;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javabase.base.Constant;
import com.javabase.base.util.Common;
import com.javabase.dao.SysLogsDao;
import com.javabase.entity.SysLogs;
import com.javabase.entity.SysUsers;


/**
 * AOP注解方法实现日志管理 利用spring AOP 切面技术记录日志 定义切面类（这个是切面类和切入点整天合在一起的),
 * 这种情况是共享切入点情况;
 * 
 * 
 * 
 * @author bruce
 * 
 * @version 1.0v
 */

@Aspect
// 该注解标示该类为切面类
@Component
public class SysLogsAOPAction {

	@Autowired
	private SysLogsDao sysLogsDao;

	/**
	 * 这个方式不太好使,舍弃不用.
	 * @param point
	 * @return
	 */
	//@Around("execution(* com.javabase.dao.impl.*.* (..))")
	public Object noteSysLogs(ProceedingJoinPoint point) {
		Object result = null;
		// 执行方法名
		String methodName = point.getSignature().getName(); 
		String className = point.getTarget().getClass().getSimpleName();
		String user = null;
		String ip = null;
		// 当前用户
		try {
			// ip
			ip = InetAddress.getLocalHost().getHostAddress();
			// 登录名
			result = point.proceed();
			user = Common.getAuthenticatedUsername();
		} catch (Throwable e) {
			// e.printStackTrace();
		}
		String name = null;
		// 操作范围
		if (className.contains("SysSources")) {
			name = "资源管理";
		} else if (className.contains("SysRoles")) {
			name = "角色管理";
		} else if (className.contains("SysUsers")) {
			name = "用户管理";
		}
		// 操作类型
		String opertype = "";
		if (methodName.contains("saveUsersRoles") ) {
			opertype = "update用户的角色";
		} else if (methodName.contains("saveSysSourcesRoles") ) {
			opertype = "update角色的权限";
		} else if (methodName.contains("addOne") || methodName.contains("save")) {
			opertype = "save操作";
		} else if (methodName.contains("updateById") || methodName.contains("modify")) {
			opertype = "update操作";
		} else if (methodName.contains("deleteById") || methodName.contains("del")) {
			opertype = "delete操作";
		}
		
		if(!Common.isEmpty(opertype) && !className.contains("LoginLogs")){
			SysLogs log = new SysLogs();
			SysUsers users = (SysUsers) Constant.userMap.get(user);
			if (users!=null) {
				log.setSysUserId(users.getSysUserId()+"");
			}
			log.setSysUserName(user);
			log.setModule(name);
			log.setAction(opertype);
			log.setOperatorTime(new Date());
			log.setUserIp(ip);
			sysLogsDao.addOne(log);
		}
		return result;
	}
}
