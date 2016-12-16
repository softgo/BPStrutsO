package com.javabase.controller;

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
import com.javabase.base.util.StringUtil;
import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysLogs;
import com.javabase.entity.SysRoles;
import com.javabase.entity.SysSources;
import com.javabase.entity.SysUsers;
import com.javabase.service.SysSourcesService;

/**
 * 
 * Controller
 *
 * @author bruce
 * 
 */
 
@Controller
@RequestMapping("/background/sysSources/")
public class  SysSourcesController  extends BaseController {

	private final static Logger logger= Logger.getLogger(SysSourcesController.class);
	
	// Servrice start
	@Autowired(required=false)
	private SysSourcesService  sysSourcesService; 

	private SysUsers sysUsers;
	
	@RequestMapping("find")
	public String find(Model model, SysSources sysSources, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = sysSourcesService.find(pageView, sysSources);
		model.addAttribute("pageView", pageView);
		return "/background/sysSources/list";
	}
	
	@RequestMapping("findByName")
	public void findByName(Model model, String objName ) {
		String data = null;
		try {
			SysSources sysSources = sysSourcesService.findByName(objName);
			if (sysSources != null) {
				data = "({msg:'Y',content:'该资源名字已经存在,请重新添加!'})";
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
	public void findByProps(Model model, SysSources sysSources) {
		String data = null;
		try {
			SysSources object = sysSourcesService.findByProps(sysSources);
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
	
	/**
	 * 某个角色拥有的权限
	 * @return
	 */
	@RequestMapping(value="permissioRole")
	public String permissioRole(Model model,String roleId){
		SysRoles tRole = (SysRoles) request.getSession().getAttribute("roleSession");
		SysUsers tUsers = (SysUsers) request.getSession().getAttribute("userSession"); //login user .
		List<SysSources> resources = sysSourcesService.getSysSourcesRoles(roleId);
		boolean tag = false; //没有分配过权限.
		
		StringBuffer sb = new StringBuffer();
		sb.append("var data = [];");
		String userName = tUsers.getSysUserName().trim();
		
		//admin.
		if (userName.equalsIgnoreCase("admin")) {
			//judge it.
			if (resources.size()==0 || resources== null) {
				resources = sysSourcesService.getSysSourcesRoles(tRole.getRoleId()+"");
				tag = true;
			}
			List<SysSources> allRes = sysSourcesService.findAll();
			//loop.
			for (SysSources father : allRes) {
				boolean flag = false;
				if (tag) {
					sb.append("data.push({ fid: '"
							+ father.getSourceId() + "', pfid: '"
							+ father.getParentId()
							+ "', fname: '" + father.getSourceName()
							+ "'});");
				}else {
					for (SysSources child : resources) {//用户拥有的权限
						if (child.getSourceId().equals(father.getSourceId())) {
							sb.append("data.push({ fid: '"
									+ father.getSourceId() + "', pfid: '"
									+ father.getParentId()
									+ "', fname: '" + father.getSourceName()
									+ "',ischecked: true});");
							flag = true;
						}
					}
					if (!flag) {
						sb.append("data.push({ fid: '"
								+ father.getSourceId() + "', pfid: '"
								+ father.getParentId()
								+ "', fname: '" + father.getSourceName()
								+ "'});");
					}
				}
			}
		}else {
		//someone
			List<SysSources> allRes = sysSourcesService.getSysSourcesRoles(tRole.getRoleId()+"");
			if (resources.size()==0 || resources== null) {
				tag = true;
			}
			for (SysSources father : allRes) {
				boolean flag = false;
				if (tag) {
					sb.append("data.push({ fid: '"
							+ father.getSourceId() + "', pfid: '"
							+ father.getParentId()
							+ "', fname: '" + father.getSourceName()
							+ "'});");
				}else {
					for (SysSources child : resources) {//用户拥有的权限
						if (child.getSourceId().equals(father.getSourceId())) {
							sb.append("data.push({ fid: '"
									+ father.getSourceId() + "', pfid: '"
									+ father.getParentId()
									+ "', fname: '" + father.getSourceName()
									+ "',ischecked: true});");
							flag = true;
						}
					}
					if (!flag) {
						sb.append("data.push({ fid: '"
								+ father.getSourceId() + "', pfid: '"
								+ father.getParentId()
								+ "', fname: '" + father.getSourceName()
								+ "'});");
					}
				}
			}
		}
		
		model.addAttribute("roleId", roleId);
		model.addAttribute("resources", sb);
		return "/background/sysSources/permissioUser";
		
	}
	
	@ResponseBody
	@RequestMapping(value="saveSysSourcesRoles")
	public String saveSysSourcesRoles(String roleId,String rescId){
		String errorCode = "1000";
		List<String> list = Common.removeSameItem(Arrays.asList(rescId.split(",")));
		List<SysSources> sysSources = sysSourcesService.findAll();
		for (SysSources source : sysSources) {
			if (list.contains(source.getSourceId()+"") && !list.contains(source.getParentId()+"")) {
				list.add(source.getParentId()+"");
			}
		}
		
		try {
			sysSourcesService.saveSysSourcesRoles(roleId, list);
		} catch (Exception e) {
			e.printStackTrace();
			errorCode="1001";
		}
		
		//记录日志
		noteOperator("分配");
		
		return errorCode;
	}
	
	@RequestMapping("addOne")
	public String addOne(Model model, SysSources sysSources) {
		sysSourcesService.addOne(sysSources);
		//记录日志
		noteOperator("添加");
		
		return "redirect:find.html";
	}
	
	@RequestMapping("findById")
	public String findById(Model model, String sourcesId, int type) {
    	 SysSources sysSources =sysSourcesService.findById(sourcesId);
    	 model.addAttribute("resources", sysSources);
    	 List<SysSources> resLists = sysSourcesService.findAll();
    	 model.addAttribute("resLists", resLists);
    	 if(type == 1){
  			return "/background/sysSources/edit";
  		}else{
  			return "/background/sysSources/show";
  		}
	}
	
	@RequestMapping("findByKey")
	public void findByKey(Model model,String resKey){
		SysSources sources = new SysSources();
		sources.setSourceKey(resKey);
		String data = null;
		try {
			SysSources object = sysSourcesService.findByProps(sources);
			if (object != null) {
				data = "({msg:'Y',content:'该资源KEY已经存在,请重新添加!'})";
			}else {
				data = "({msg:'N',content:'可以放心使用该KEY!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
	@RequestMapping("findByUrl")
	public void findByUrl(Model model,String resUrl){
		SysSources sources = new SysSources();
		sources.setSourceUrl(resUrl);
		String data = null;
		try {
			SysSources object = sysSourcesService.findByProps(sources);
			if (object != null) {
				data = "({msg:'Y',content:'该资源URL已经存在,请重新添加!'})";
			}else {
				data = "({msg:'N',content:'可以放心使用该URL!'})";
			}
			//返回.
			ajaxJson(data);
		} catch (Exception e) {
			logger.info("查找出错!"+e.getLocalizedMessage());
		}
	}
	
	@RequestMapping("deleteAll")
	public void deleteAll(Model model,String pks){
		List<String> primks= Common.removeSameItem(Arrays.asList(pks.split(",")));
		boolean result = sysSourcesService.deleteAll(primks);
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
		int count = sysSourcesService.deleteById(objId);
		//记录日志
		String data = null;
		if (count>0 ) {
			data = "({msg:'Y',content:'删除成功!'})";
		}else {
			data = "({msg:'N',content:'删除失败!'})";
		}
		//记录日志
		noteOperator("删除选择的删除项.");
		//返回.
		ajaxJson(data);
	}
	
	@RequestMapping("updateById")
	public String updateById(Model model, SysSources sysSources) {
		sysSourcesService.updateOne(sysSources);
		
		noteOperator("修改");
		
		return "redirect:find.html";
	}
	
	/**
	 * 跑到新增界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI(Model model,SysSources sysSources,String tag) {
		if(StringUtil.isNotBlank(tag)){
			if (sysSources==null) {
				sysSources = new SysSources();
			}
			sysSources.setParentId(Integer.parseInt("1010"));
			List<SysSources> resLists = sysSourcesService.findAllByPros(sysSources);
			model.addAttribute("tag", "forSelf");
			model.addAttribute("resLists", resLists);
			return "/background/sysSources/add";
		}else {
			model.addAttribute("tag", "new");
			return "/background/sysSources/add";			
		}
	}
	
	/**
	 * 记录日志
	 * @param tag:分配,添加,删除,修改
	 */
	public void noteOperator(String tag) {
		//记录日志
		SysLogs sysLogs = new  SysLogs(); 
		SysUsers tUser = getSysUsers();
		sysLogs.setSysUserId(tUser.getSysUserId()+"");
		sysLogs.setSysUserName(tUser.getSysUserName());
		sysLogs.setModule("系统资源管理");
		sysLogs.setAction("系统资源管理——"+tag+"资源");
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
