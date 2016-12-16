package com.javabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;

import com.javabase.base.BaseController;
import com.javabase.pulgin.mybatis.plugin.PageView;

import com.javabase.entity.SysSourcesRoles;
import com.javabase.service.SysSourcesRolesService;

/**
 * 
 * Controller
 *
 * @author bruce
 * 
 */
 
@Controller
@RequestMapping("/background/sysSourcesRoles/")
public class  SysSourcesRolesController  extends BaseController {

	private final static Logger logger= Logger.getLogger(SysSourcesRolesController.class);
	
	// Servrice start
	@Autowired(required=false)
	private SysSourcesRolesService  sysSourcesRolesService; 

	@RequestMapping("find")
	public String find(Model model, SysSourcesRoles sysSourcesRoles, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = sysSourcesRolesService.find(pageView, sysSourcesRoles);
		model.addAttribute("pageView", pageView);
		return "/background/sysSourcesRoles/list";
	}
	
	@RequestMapping("findByName")
	public void findByName(Model model, String objName ) {
		String data = null;
		try {
			SysSourcesRoles sysSourcesRoles = sysSourcesRolesService.findByName(objName);
			if (sysSourcesRoles != null) {
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
	public void findByProps(Model model, SysSourcesRoles sysSourcesRoles) {
		String data = null;
		try {
			SysSourcesRoles object = sysSourcesRolesService.findByProps(sysSourcesRoles);
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
	public String addOne(Model model, SysSourcesRoles sysSourcesRoles) {
		sysSourcesRolesService.addOne(sysSourcesRoles);
		return "redirect:find.html";
	}
	
	@RequestMapping("findById")
	public String findById(Model model, String objId, int type) {
    	 SysSourcesRoles sysSourcesRoles =sysSourcesRolesService.findById(objId);
    	  model.addAttribute("sysSourcesRoles", sysSourcesRoles);
    	 return "/background/sysSourcesRoles/show";
	}
	
	@RequestMapping("deleteById")
	public String deleteById(Model model, String objId) {
		sysSourcesRolesService.deleteById(objId);
		return "redirect:find.html";
	}
	
	@RequestMapping("updateById")
	public String updateById(Model model, SysSourcesRoles sysSourcesRoles) {
		sysSourcesRolesService.updateOne(sysSourcesRoles);
		return "redirect:find.html";
	}
	
}
