package com.javabase.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;

import com.javabase.base.BaseController;
import com.javabase.base.util.Common;
import com.javabase.base.util.PropertiesUtils;
import com.javabase.base.util.StringUtil;
import com.javabase.code.gengerate.def.FtlDef;
import com.javabase.code.gengerate.factory.CodeGenerateFactory;
import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.ServerStatus;
import com.javabase.entity.SysLogs;
import com.javabase.entity.SysServerInfos;
import com.javabase.entity.SysUsers;
import com.javabase.service.SysServerInfosService;
import com.mongo.code.gengerate.factory.MonGoCodeGenerateFactory;

/**
 * 
 * Controller
 *
 * @author bruce
 * 
 */
 
@Controller
@RequestMapping("/background/sysServerInfos/")
public class  SysServerInfosController  extends BaseController {

	private final static Logger logger= Logger.getLogger(SysServerInfosController.class);
	
	// Servrice start
	@Autowired(required=false)
	private SysServerInfosService  sysServerInfosService; 

	private SysUsers sysUsers;
	
	@RequestMapping("find")
	public String find(Model model, SysServerInfos sysServerInfos, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = sysServerInfosService.find(pageView, sysServerInfos);
		model.addAttribute("pageView", pageView);
		return "/background/sysServerInfos/list";
	}
	
	@RequestMapping(value="show")
	public String show() {
		return "/background/sysServerInfos/show";
	}

	@RequestMapping(value="forecast")
	public String forecast() {
		return "/background/sysServerInfos/forecast";
	}

	@RequestMapping(value="generate")
	public void generate(String tableName,String codeName,String codePrefix,String entityPackage,String isAuto,String dbTag) {
		String data = null;
		String success = "({msg:'Y',content:'生成完成,请重新部署服务查看效果!'})";
		String error = "({msg:'N',content:'生成生成失败,请找相关开发人员检查原因!'})";
		try {
			if (StringUtil.isNotBlank(isAuto) && isAuto.equalsIgnoreCase("AUTO")) {
				if (StringUtil.isNotBlank(dbTag) && dbTag.equalsIgnoreCase("COMMONDB")) {  //关系型数据库.
					try {
						//主键自动生成.
						CodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage, FtlDef.KEY_TYPE_AUTO);
						CodeGenerateFactory.addMenus(tableName,codeName,codePrefix);
						data = success;
					}
					catch (Exception e) {
						data = error;
					}
				}else { //mongo库.
					 try {
						MonGoCodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage, FtlDef.KEY_TYPE_AUTO);
						MonGoCodeGenerateFactory.addMenus(tableName, codeName, codePrefix);
						data = success;
					}
					catch (Exception e) {
						data = error;
					}
				}
			}else{
				if (StringUtil.isNotBlank(dbTag) && dbTag.equalsIgnoreCase("MONGO")) {  //关系型数据库.
					 try {
						MonGoCodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage, FtlDef.KEY_TYPE_MAN);
						MonGoCodeGenerateFactory.addMenus(tableName, codeName, codePrefix);
						data = success;
					}
					catch (Exception e) {
						data = error;
					}
				}else { //mongo库.
					 try {
						CodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage, FtlDef.KEY_TYPE_MAN);
						CodeGenerateFactory.addMenus(tableName,codeName,codePrefix);
						data = success;
					}
					catch (Exception e) {
						data = error;
					}
				}
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="codes")
	public String codes() {
		return "/background/sysServerInfos/codes";
	}
	
	/**
	 * 获取服务器基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="info")
	public Map<String, Object> serverBaseInfo() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ServerStatus status = Common.getServerStatus();
		dataMap.put("data", status);
		return dataMap;
	}
	
	/**
	 * 预警监控信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/warnInfo")
	public Map<String, Object> warnInfo(HttpServletRequest request) throws Exception {
		ServerStatus status = Common.getServerStatus();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		String cpuUsage = status.getCpuUsage();
		long FreeMem = status.getFreeMem();
		long useMem = status.getUsedMem();
		long TotalMem = status.getTotalMem();
		String serverUsage = Common.fromUsage(useMem, TotalMem);
		dataMap.put("cpuUsage", cpuUsage);
		dataMap.put("FreeMem", FreeMem);
		dataMap.put("TotalMem", TotalMem);
		dataMap.put("serverUsage", serverUsage);
		long JvmFreeMem = status.getJvmFreeMem();
		long JvmTotalMem = status.getJvmTotalMem();
		String JvmUsage = Common.fromUsage(JvmTotalMem - JvmFreeMem, JvmTotalMem);
		dataMap.put("JvmFreeMem", JvmFreeMem);
		dataMap.put("JvmTotalMem", JvmTotalMem);
		dataMap.put("JvmUsage", JvmUsage);
		dataMap.put("cpu", PropertiesUtils.findPropertiesKey("cpu",PropertiesUtils.propsFilePath));
		dataMap.put("jvm", PropertiesUtils.findPropertiesKey("jvm",PropertiesUtils.propsFilePath));
		dataMap.put("ram", PropertiesUtils.findPropertiesKey("ram",PropertiesUtils.propsFilePath));
		dataMap.put("toEmail", PropertiesUtils.findPropertiesKey("toEmail",PropertiesUtils.propsFilePath));
		dataMap.put("diskInfos", status.getDiskInfos());
		return dataMap;
	}
	
	/**
	 * 修改配置　
	 * @param request
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
    @ResponseBody
	@RequestMapping("/modifySer")
    public Map<String, Object> modifySer(HttpServletRequest request,String key,String value) throws Exception{
    	Map<String, Object> dataMap = new HashMap<String,Object>();
    	try {
		// 从输入流中读取属性列表（键和元素对）
    		PropertiesUtils.modifyProperties(key, value,PropertiesUtils.propsFilePath);
		} catch (Exception e) {
			dataMap.put("flag", false);
		}
    	dataMap.put("flag", true);
    	
    	//系统设置管理.
    	SysLogs sysLogs = new  SysLogs(); 
		SysUsers tUser = getSysUsers();
		sysLogs.setSysUserId(tUser.getSysUserId()+"");
		sysLogs.setSysUserName(tUser.getSysUserName());
		sysLogs.setModule("服务器管理");
		sysLogs.setAction("服务器管理——修改服务配置");
		sysLogs.setUserIp(Common.toIpAddr(request));
		noteOperator(sysLogs);
    	
		return dataMap;
    }
	
	@RequestMapping("findByName")
	public void findByName(Model model, String objName ) {
		String data = null;
		try {
			SysServerInfos sysServerInfos = sysServerInfosService.findByName(objName);
			if (sysServerInfos != null) {
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
	public void findByProps(Model model, SysServerInfos sysServerInfos) {
		String data = null;
		try {
			SysServerInfos object = sysServerInfosService.findByProps(sysServerInfos);
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
	public String addOne(Model model, SysServerInfos sysServerInfos) {
		sysServerInfosService.addOne(sysServerInfos);
		return "redirect:find.html";
	}
	
	@RequestMapping("findById")
	public String findById(Model model, String objId, int type) {
    	 SysServerInfos sysServerInfos =sysServerInfosService.findById(objId);
    	  model.addAttribute("sysServerInfos", sysServerInfos);
    	 return "/background/sysServerInfos/show";
	}
	
	@RequestMapping("deleteById")
	public String deleteById(Model model, String objId) {
		sysServerInfosService.deleteById(objId);
		
		return "redirect:find.html";
	}
	
	@RequestMapping("updateById")
	public String updateById(Model model, SysServerInfos sysServerInfos) {
		sysServerInfosService.updateOne(sysServerInfos);
		
		return "redirect:find.html";
	}
	
	public SysUsers getSysUsers(){
		if (sysUsers==null) {
			sysUsers = (SysUsers) request.getSession().getAttribute("userSession");
		}
		return sysUsers;
	}
}
