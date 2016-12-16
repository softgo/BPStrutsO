package com.javabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;

import com.javabase.base.BaseController;
import com.javabase.pulgin.mybatis.plugin.PageView;

import com.javabase.entity.SysUsersRoles;
import com.javabase.service.SysUsersRolesService;

/**
 * 
 * Controller
 *
 * @author bruce
 * 
 */
 
@Controller
@RequestMapping("/background/sysUsersRoles/")
public class  SysUsersRolesController  extends BaseController {

	private final static Logger logger= Logger.getLogger(SysUsersRolesController.class);
	
	// Servrice start
	@Autowired(required=false)
	private SysUsersRolesService  sysUsersRolesService; 

	@RequestMapping("find")
	public String find(Model model, SysUsersRoles sysUsersRoles, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = sysUsersRolesService.find(pageView, sysUsersRoles);
		model.addAttribute("pageView", pageView);
		return "/background/sysUsersRoles/list";
	}
	
	@RequestMapping("findByName")
	public void findByName(Model model, String objName ) {
		String data = null;
		try {
			SysUsersRoles sysUsersRoles = sysUsersRolesService.findByName(objName);
			if (sysUsersRoles != null) {
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
	
	@RequestMapping("findByProps")
	public void findByProps(Model model, SysUsersRoles sysUsersRoles) {
		String data = null;
		try {
			SysUsersRoles object = sysUsersRolesService.findByProps(sysUsersRoles);
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
	public String add(Model model, SysUsersRoles sysUsersRoles) {
		sysUsersRolesService.addOne(sysUsersRoles);
		return "redirect:find.html";
	}
	
	@RequestMapping("findById")
	public String findById(Model model, String objId, int type) {
    	 SysUsersRoles sysUsersRoles =sysUsersRolesService.findById(objId);
    	  model.addAttribute("sysUsersRoles", sysUsersRoles);
    	 return "/background/sysUsersRoles/show";
	}
	
	@RequestMapping("deleteById")
	public String deleteById(Model model, String objId) {
		sysUsersRolesService.deleteById(objId);
		return "redirect:find.html";
	}
	
	@RequestMapping("updateById")
	public String updateById(Model model, SysUsersRoles sysUsersRoles) {
		sysUsersRolesService.updateOne(sysUsersRoles);
		return "redirect:find.html";
	}
	
}
