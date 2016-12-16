package com.javabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;

import com.javabase.base.BaseController;
import com.javabase.pulgin.mybatis.plugin.PageView;

import com.javabase.entity.SysLogs;
import com.javabase.service.SysLogsService;

/**
 * 
 * Controller
 *
 * @author bruce
 * 
 */
 
@Controller
@RequestMapping("/background/systemLogs/")
public class  SysLogsController extends BaseController {

	private final static Logger logger= Logger.getLogger(SysLogsController.class);
	
	// Servrice start
	@Autowired(required=false)
	private SysLogsService  sysLogsService; 

	@RequestMapping("find")
	public String find(Model model, SysLogs sysLogs, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = sysLogsService.find(pageView, sysLogs);
		model.addAttribute("pageView", pageView);
		return "/background/systemLogs/list";
	}
	
	@RequestMapping("findSysByName")
	public void findSysByName(Model model, String objName ) {
		String data = null;
		try {
			SysLogs sysLogs = sysLogsService.findByName(objName);
			if (sysLogs != null) {
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
	
	@RequestMapping("findSysByProps")
	public void findSysByProps(Model model, SysLogs sysLogs) {
		String data = null;
		try {
			SysLogs object = sysLogsService.findByProps(sysLogs);
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
	public String addOne(Model model, SysLogs sysLogs) {
		sysLogsService.addOne(sysLogs);
		return "redirect:find.html";
	}
	
	@RequestMapping("findById")
	public String findById(Model model, String objId, int type) {
    	 SysLogs sysLogs =sysLogsService.findById(objId);
    	  model.addAttribute("sysLogs", sysLogs);
    	 return "/background/systemLogs/show";
	}
	
	@RequestMapping("deleteById")
	public String deleteById(Model model, String objId) {
		sysLogsService.deleteById(objId);
		return "redirect:find.html";
	}
	
	@RequestMapping("updateOne")
	public String updateOne(Model model, SysLogs sysLogs) {
		sysLogsService.updateOne(sysLogs);
		return "redirect:find.html";
	}
	
}
