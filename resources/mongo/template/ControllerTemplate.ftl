package ${bussPackage}.${basePackage}.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Map<String, String> params = new  HashMap<String,String>();
        //params 为要通过条件来查询的key-value 键值对,示例如下:
        //params.put("id_@like", city.getId());
        pageView = cityService.find(pageView, params);
        model.addAttribute("pageView", pageView);
		return "/background/${lowerName}/list";
	}
	
	@RequestMapping("findById")
    public String findById(Model model, String objId, int type) {
        ObjectId objectId = new ObjectId(objId);
        ${className} ${lowerName} =${lowerName}Service.findByMongoId(objectId);
        model.addAttribute("object", ${lowerName});
        model.addAttribute("objId", objId);
        if (type == 1) {
            return "/background/${lowerName}/edit";
        } else {
            return "/background/${lowerName}/show";
        }
    }
	
	@RequestMapping("findByName")
	public void findByName(Model model, String objName) {
		String data = null;
		try {
			${className} ${lowerName} = ${lowerName}Service.findByMongoName("objPros",objName); //objPros:为要查询的字段,示例:
			//City city = cityService.findByMongoName("text", objName);		
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
    		Map<String, Object> params = new  HashMap<String,Object>();
            /* 以下为示例. 使用时候根据实际去填写.
            if (city.getId()!=null) {
                params.put("id", city.getId());
            }
            */
            ${className} object = ${lowerName}Service.findByMongoProps(params);
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
		${lowerName}Service.addMongoOne(${lowerName});
		return "redirect:find.html";
	}
	
	@RequestMapping("deleteById")
	public void deleteById(Model model, String objId) {
		String data = null;
        try {
            ObjectId objectId = new ObjectId(objId);
            int count = ${lowerName}Service.deleteByMongoId(objectId);
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
	public String updateById(Model model, ${className} ${lowerName},String _id) {
		ObjectId objectId = new ObjectId(_id);        
        ${lowerName}.set_id(objectId);
		${lowerName}Service.updateMongoOne(${lowerName});
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
