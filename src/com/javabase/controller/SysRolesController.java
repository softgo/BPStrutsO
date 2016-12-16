package com.javabase.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;

import com.javabase.base.BaseController;
import com.javabase.base.util.Common;
import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysLogs;
import com.javabase.entity.SysRoles;
import com.javabase.entity.SysSources;
import com.javabase.entity.SysUsers;
import com.javabase.service.SysRolesService;
import com.javabase.service.SysSourcesService;

/**
 * 
 * Controller
 *
 * @author bruce
 * 
 */
 
@Controller
@RequestMapping("/background/sysRoles/")
public class  SysRolesController  extends BaseController {

	private final static Logger logger= Logger.getLogger(SysRolesController.class);
	
	// Servrice start
	@Autowired(required=false)
	private SysRolesService  sysRolesService; 
	@Autowired
	private SysSourcesService sysSourcesService;
	
	private SysUsers sysUsers;

	/**
	 * 跳到新增页面
	 * @return
	 */
	@RequestMapping(value="addUI")
	public String addUI(Model model){
		this.getRights(model);
		return "/background/sysRoles/add";
	} 
	
	/**
	 * 权限树
	 * @return
	 */
	@RequestMapping(value="permissio")
	public String getRights(Model model){
		List<SysSources> allRes = sysSourcesService.findAll();
		StringBuffer sb = new StringBuffer();
		sb.append("var data = [];");
		for (SysSources r : allRes) {
				sb.append("data.push({ fid: '"
						+ r.getSourceId() + "', pfid: '"
						+ r.getParentId()
						+ "', fname: '" + r.getSourceName()
						+ "'});");

			}
		model.addAttribute("resources", sb);
		return "/background/sysRoles/permissioUser";
	}
	
	@RequestMapping("find")
	public String find(Model model, SysRoles sysRoles, String pageNow,String pageSize ) {
		SysRoles tRole = (SysRoles) request.getSession().getAttribute("roleSession");
		SysUsers tUser = (SysUsers) request.getSession().getAttribute("userSession");
		String loginName = tUser.getSysUserName().trim();
		PageView pageView = null;
		//admin.
		if (loginName.equalsIgnoreCase("admin")) {
			pageView = super.findPage(pageNow, pageSize);
			pageView = sysRolesService.find(pageView, sysRoles);
		}else {
			sysRoles.setParentId(tRole.getParentId());
			sysRoles.setRoleId(tRole.getRoleId());
			pageView = super.findPage(pageNow, pageSize);
			pageView = sysRolesService.find(pageView, sysRoles);
			List<SysRoles> tempList  = pageView.getRecords();
			List<SysRoles> roleList = new ArrayList<SysRoles>();
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
		model.addAttribute("pageView", pageView);
		model.addAttribute("loginRole", tRole);
		return "/background/sysRoles/list";
	}
	
	@RequestMapping("findByName")
	public void findByName(Model model, String objName) {
		String data = null;
		try {
			SysRoles sysRoles = sysRolesService.findByName(objName);
			if (sysRoles != null) {
				data = "({msg:'Y',content:'该角色名已经存在,请重新输入!'})";
			}else {
				data = "({msg:'N',content:'可以放心使用!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
	@RequestMapping("findByProps")
	public void findByProps(Model model, String roleKey) {
		String data = null;
		SysRoles sysRoles = new SysRoles();
		sysRoles.setRoleKey(roleKey);
		try {
			SysRoles object = sysRolesService.findByProps(sysRoles);
			if (object != null) {
				data = "({msg:'Y',content:'该KEY已经存在,请重新填写!'})";
			}else {
				data = "({msg:'N',content:'可以放心使用!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
	@RequestMapping("addOne")
	public String addOne(Model model, SysRoles sysRoles) {
		SysRoles trole = (SysRoles) request.getSession().getAttribute("roleSession");
		sysRoles.setParentId(trole.getRoleId());
		sysRolesService.addOne(sysRoles);
		//记录日志
		noteOperator("添加");
		return "redirect:find.html";
	}
	
	@RequestMapping("findById")
	public String findById(Model model, String roleId, int type) {
    	SysRoles sysRoles =sysRolesService.findById(roleId);
    	model.addAttribute("role", sysRoles);
    	if(type == 1){
  			return "/background/sysRoles/edit";
  		}else{
  			return "/background/sysRoles/show";
  		}
	}
	
	@RequestMapping("deleteAll")
	public void deleteAll(Model model,String pks){
		List<String> primks= Common.removeSameItem(Arrays.asList(pks.split(",")));
		boolean result = sysRolesService.deleteAll(primks);
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
			int count = sysRolesService.deleteById(objId);
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
	public String updateById(Model model, SysRoles sysRoles) {
		sysRolesService.updateOne(sysRoles);
		//记录日志
		noteOperator("修改");
		return "redirect:find.html";
	}
	
	/**
	 * 记录日志
	 * @param tag:添加;修改;删除...
	 */
	public void noteOperator(String tag){
		//记录日志
		SysLogs sysLogs = new  SysLogs(); 
		SysUsers tUser = getSysUsers();
		sysLogs.setSysUserId(tUser.getSysUserId()+"");
		sysLogs.setSysUserName(tUser.getSysUserName());
		sysLogs.setModule("系统角色管理");
		sysLogs.setAction("系统角色管理——"+tag+"角色");
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
