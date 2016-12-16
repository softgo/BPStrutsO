package ${bussPackage}.${basePackage}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.log4j.Logger;

import com.javabase.base.BaseController;
import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysUsers;
import com.javabase.entity.SysLogs;
import com.javabase.base.util.Common;

import ${bussPackage}.${basePackage}.entity.${className};
import ${bussPackage}.${basePackage}.service.${className}Service;

/**
 * 
 * Controller
 *
 * @author bruce
 * 
 */
 
@Controller
@RequestMapping("/background/${lowerName}/")
public class  ${className}Controller  extends BaseController {

	private final static Logger logger= Logger.getLogger(${className}Controller.class);
	
	// Servrice start
	@Autowired
	private ${className}Service  ${lowerName}Service; 
    
    private SysUsers sysUsers;

	@RequestMapping("find")
	public String find(Model model, ${className} ${lowerName}, String pageNow,String pageSize ) {
		PageView pageView = super.findPage(pageNow, pageSize);
		pageView = ${lowerName}Service.find(pageView, ${lowerName});
		model.addAttribute("pageView", pageView);
		return "/background/${lowerName}/list";
	}
	
	@RequestMapping("findByName")
	public void findByName(Model model, String objName ) {
		String data = null;
		try {
			${className} ${lowerName} = ${lowerName}Service.findByName(objName);
			if (${lowerName} != null) {
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
	public void findByProps(Model model, ${className} ${lowerName}) {
		String data = null;
		try {
			${className} object = ${lowerName}Service.findByProps(${lowerName});
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
	
    @RequestMapping("addUI")
    public String addUI() {
        return "/background/${lowerName}/add";
    }
	
	@RequestMapping("addOne")
	public String addOne(Model model, ${className} ${lowerName}) {
		${lowerName}Service.addOne(${lowerName});
		return "redirect:find.html";
	}
	
	@RequestMapping("findById")
	public String findById(Model model, String objId, int type) {
    	${className} ${lowerName} =${lowerName}Service.findById(objId);
    	model.addAttribute("object", ${lowerName});
    	model.addAttribute("objId", objId);
    	if (type == 1) {
            return "/background/${lowerName}/edit";
        } else {
            return "/background/${lowerName}/show";
        }
	}
	
	@RequestMapping("deleteById")
	public void deleteById(Model model, String objId) {
		String data = null;
        try {
            int count = ${lowerName}Service.deleteById(objId);
            if (count >0 ) {
                data = "({msg:'Y',content:'删除成功!'})";
            }else {
                data = "({msg:'N',content:'删除失败!'})";
            }
            //返回.
            ajaxJson(data);
        } catch (Exception e) {
            logger.info("查找出错!"+e.getLocalizedMessage());
        }
	}
	
	@RequestMapping("updateById")
	public String updateById(Model model, ${className} ${lowerName}) {
		${lowerName}Service.updateOne(${lowerName});
		return "redirect:find.html";
	}
	
	public SysUsers getSysUsers(){
        if (sysUsers==null) {
            sysUsers = (SysUsers) request.getSession().getAttribute("userSession");
        }
        return sysUsers;
    }
    
    /**
     * 记录日志
     * @param tag:添加;修改;删除...
     */
    public void noteOperator(String tag){
        //记录日志
        SysLogs sysLogs = new SysLogs(); 
        SysUsers tUser = getSysUsers();
        sysLogs.setSysUserId(tUser.getSysUserId()+"");
        sysLogs.setSysUserName(tUser.getSysUserName());
        sysLogs.setModule("根据业务添加");
        sysLogs.setAction("根据业务添加——");
        sysLogs.setUserIp(Common.toIpAddr(request));
        noteOperator(sysLogs);
    }
}
