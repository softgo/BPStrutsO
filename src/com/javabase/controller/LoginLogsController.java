package com.javabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;

import com.javabase.base.BaseController;
import com.javabase.pulgin.mybatis.plugin.PageView;

import com.javabase.entity.LoginLogs;
import com.javabase.service.LoginLogsService;

/**
 * 
 * Controller
 *
 * @author bruce
 * 
 */
 
@Controller
@RequestMapping("/background/systemLogs/")
public class  LoginLogsController  extends BaseController {

	private final static Logger logger= Logger.getLogger(LoginLogsController.class);
	
	// Servrice start
	@Autowired(required=false)
	private LoginLogsService  loginLogsService;

	@RequestMapping("logins")
	public String logins(Model model, LoginLogs loginLogs, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = loginLogsService.find(pageView, loginLogs);
		model.addAttribute("pageView", pageView);
		return "/background/systemLogs/logins";
	}
	
	@RequestMapping("findLoginByName")
	public void findLoginByName(Model model, String objName ) {
		String data = null;
		try {
			LoginLogs loginLogs = loginLogsService.findByName(objName);
			if (loginLogs != null) {
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
	
	@RequestMapping("findLoginByProps")
	public void findLoginByProps(Model model, LoginLogs loginLogs) {
		String data = null;
		try {
			LoginLogs object = loginLogsService.findByProps(loginLogs);
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
	
	@RequestMapping("addLoginOne")
	public String addLoginOne(Model model, LoginLogs loginLogs) {
		loginLogsService.addOne(loginLogs);
		return "redirect:find.html";
	}
	
	@RequestMapping("findLoginById")
	public String findLoginById(Model model, String objId, int type) {
    	 LoginLogs loginLogs =loginLogsService.findById(objId);
    	  model.addAttribute("loginLogs", loginLogs);
    	 return "/background/systemLogs/show";
	}
	
	@RequestMapping("deleteLoginById")
	public String deleteLoginById(Model model, String objId) {
		loginLogsService.deleteById(objId);
		return "redirect:find.html";
	}
	
	@RequestMapping("updateLoginById")
	public String updateLoginById(Model model, LoginLogs loginLogs) {
		loginLogsService.updateOne(loginLogs);
		return "redirect:find.html";
	}
	
}
