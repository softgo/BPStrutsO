package com.javabase.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;

import com.javabase.base.BaseController;
import com.javabase.base.util.Common;
import com.javabase.base.util.MD5Util;
import com.javabase.base.util.StringUtil;
import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysLogs;
import com.javabase.entity.SysRoles;
import com.javabase.entity.SysUsers;
import com.javabase.entity.SysUsersRoles;
import com.javabase.service.SysRolesService;
import com.javabase.service.SysUsersRolesService;
import com.javabase.service.SysUsersService;

/**
 * 
 * Controller
 *
 * @author bruce
 * 
 */
 
@Controller
@RequestMapping("/background/sysUsers/")
public class  SysUsersController extends BaseController {

	private final static Logger logger= Logger.getLogger(SysUsersController.class);
	
	// Servrice start
	@Autowired(required=false)
	private SysUsersService  sysUsersService; 
	@Autowired
	private SysRolesService sysRolesService;
	@Autowired
	private SysUsersRolesService sysUsersRolesService;
	
	private SysUsers sysUsers;

	@RequestMapping("find")
	public String find(Model model, SysUsers sysUsers, String pageNow,String pageSize ) {
		SysUsers tUser = (SysUsers) request.getSession().getAttribute("userSession");
		String loginName = tUser.getSysUserName().trim();
		PageView pageView = null;
		//admin.
		if (loginName.equalsIgnoreCase("admin")) {
			pageView = super.findPage(pageNow, pageSize);
			pageView = sysUsersService.find(pageView, sysUsers);
		}else {
		//someone.
			sysUsers.setSysUserId(tUser.getSysUserId());
			sysUsers.setParentId(tUser.getParentId());
			pageView = super.findPage(pageNow, pageSize);
			pageView = sysUsersService.find(pageView, sysUsers);
			List<SysUsers> tempUList = pageView.getRecords();
			List<SysUsers> userList = new ArrayList<SysUsers>();
			for (SysUsers user : tempUList) {
				if (StringUtil.isNotBlank(user.getRoleName())) {
					if (user.getRoleName().equalsIgnoreCase(tUser.getRoleName())) {
						userList.add(user);
					}
				}
				if (user.getParentId() == tUser.getSysUserId()) {
					userList.add(user);
				}
			}
			pageView.setRecords(userList);
			pageView.setRowCount(userList.size());
		}
		model.addAttribute("pageView", pageView);
		return "/background/sysUsers/list";
	}
	
	@RequestMapping("findByName")
	public void findByName(Model model, String objName ) {
		String data = null;
		try {
			SysUsers sysUsers = sysUsersService.findByName(objName);
			if (sysUsers != null) {
				data = "({msg:'Y',content:'该用户名已经存在,请重新填入!'})";
			}else {
				data = "({msg:'N',content:'该用户名可以正常使用!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
	@RequestMapping("findByProps")
	public void findByProps(Model model, SysUsers sysUsers) {
		String data = null;
		try {
			SysUsers object = sysUsersService.findByProps(sysUsers);
			if (object != null) {
				data = "({msg:'Y',content:'按需求填写!'})";
			}else {
				data = "({msg:'N',content:'按需求填写!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
	@RequestMapping("addOne")
	public String addOne(Model model, SysUsers sysUsers) {
		 SysUsers tUser = (SysUsers) request.getSession().getAttribute("userSession");
		if (tUser!=null) {
			sysUsers.setParentId(tUser.getSysUserId());
		}
		//密码加密.
		String password = sysUsers.getSysUserPass();
		password = MD5Util.encrypt(password);
		sysUsers.setSysUserPass(password);		
		sysUsersService.addOne(sysUsers);
		//记录日志
		noteOperator("添加");
		
		return "redirect:find.html";
	}
	
	/**
	 * 修改密码.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("updatePassUI")
	public String updatePassUI(Model model,String userId ) {
		SysUsers user = sysUsersService.findById(userId);
		model.addAttribute("user", user);
		return "/background/sysUsers/updatePass";
	}

	/**
	 * 跑到新增界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI() {
		return "/background/sysUsers/add";
	}

	/**
	 * 修改界面
	 * 
	 * @param model
	 * @param videoTypeIds
	 * @return
	 */
	@RequestMapping("findById")
	public String findById(Model model, String userId, int type) {
		SysUsers user = sysUsersService.findById(userId);
		String enPass = user.getSysUserPass();
		user.setSysUserPass(MD5Util.decrypt(enPass));
		model.addAttribute("user", user);
		List<SysRoles> roles=sysRolesService.findAll();
		model.addAttribute("roles", roles);
		if (type == 1) {
			return "/background/sysUsers/edit";
		} else {
			return "/background/sysUsers/show";
		}
	}
	
	/**
	 * 给用户分配角色界面
	 * @return
	 */
	@RequestMapping("userRole")
	public String userRole(Model model,String userId,String pageNow,String pageSize){
		SysUsers user = sysUsersService.findById(userId);
		SysRoles tRole = (SysRoles) request.getSession().getAttribute("roleSession");
		SysUsers tUser = (SysUsers) request.getSession().getAttribute("userSession");
		String loginName = tUser.getSysUserName().trim();
		
		SysRoles roles = new SysRoles();
		PageView pageView = null;
		//admin.
		if (loginName.equalsIgnoreCase("admin")) {
			pageView = super.findPage(pageNow, pageSize);
			pageView = sysRolesService.find(pageView, roles);
		}else{
		//someone
			roles.setRoleId(tRole.getRoleId());
			roles.setParentId(tRole.getParentId());
			pageView = super.findPage(pageNow, pageSize);
			pageView = sysRolesService.find(pageView, roles);
			List<SysRoles> tempList = pageView.getRecords();
			ArrayList<SysRoles> roleList = new ArrayList<SysRoles>();
			for (SysRoles role : tempList) {
				if (tRole.getRoleId()==role.getParentId()) {
					roleList.add(role);
				}
				if (tRole.getRoleName().equalsIgnoreCase(role.getRoleName())) {
					roleList.add(role);					
				}
			}
			pageView.setRecords(roleList);
			pageView.setRowCount(roleList.size());
		}
		
		model.addAttribute("user", user);
		model.addAttribute("pageView", pageView);
		model.addAttribute("loginRole", tRole);
		return "/background/sysUsers/userRole";
	}

	/**
	 * 保存用户分配角色
	 * @return
	 */
	@ResponseBody
	@RequestMapping("allocation")
	public String allocation(Model model,String userId,String roleId){
		String errorCode = "1000";
		try {
			SysUsersRoles userRoles = new SysUsersRoles();
			userRoles.setRoleId(Integer.parseInt(roleId));
			userRoles.setSysUserId(Integer.parseInt(userId));
			//记录日志
			noteOperator("分配角色");
			sysRolesService.saveUsersRoles(userRoles);
		} catch (Exception e) {
			e.printStackTrace();
			errorCode="1001";
		}
		return errorCode;
	}	
	
	@RequestMapping("deleteAll")
	public void deleteAll(Model model,String pks){
		List<String> primks= Common.removeSameItem(Arrays.asList(pks.split(",")));
		boolean result = sysUsersService.deleteAll(primks);
		String data = null;
		if (result ) {
			data = "({msg:'Y',content:'删除成功!'})";
		}else {
			data = "({msg:'N',content:'删除失败!'})";
		}
		//记录日志
		noteOperator("删除选择的删除项.");
		//返回.
		ajaxJson(data);
	}
	
	@RequestMapping("deleteById")
	public void deleteById(Model model, String objId) {
		String data = null;
		try {
			int count = sysUsersService.deleteById(objId);
			if (count >0 ) {
				data = "({msg:'Y',content:'删除成功!'})";
			}else {
				data = "({msg:'N',content:'删除失败!'})";
			}
			//记录日志
			noteOperator("删除");
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
	@RequestMapping("updateById")
	public String updateById(Model model, SysUsers sysUsers) {
		sysUsersService.updateOne(sysUsers);
		String roleName = sysUsers.getRoleName();
		SysRoles roles = sysRolesService.findByName(roleName);
		SysUsersRoles sysUsersRoles = new SysUsersRoles(sysUsers.getSysUserId(),roles.getRoleId());
		sysUsersRolesService.updateOne(sysUsersRoles);
		//记录日志
		noteOperator("修改");
		
		return "redirect:find.html";
	}
	
	public void noteOperator(String tag) {
		SysLogs sysLogs = new  SysLogs(); 
		SysUsers tUser = getSysUsers();
		sysLogs.setSysUserId(tUser.getSysUserId()+"");
		sysLogs.setSysUserName(tUser.getSysUserName());
		sysLogs.setModule("系统用户管理");
		sysLogs.setAction("系统用户管理——"+tag+"用户");
		sysLogs.setUserIp(Common.toIpAddr(request));
		noteOperator(sysLogs);
	}
	
	public SysUsers getSysUsers(){
		if (sysUsers==null) {
			sysUsers = (SysUsers) request.getSession().getAttribute("userSession");
		}
		return sysUsers;
	}
}
