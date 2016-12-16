package com.javabase.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javabase.pulgin.mybatis.plugin.PageView;

import com.javabase.entity.LoginLogs;
import com.javabase.dao.LoginLogsDao;
import com.javabase.service.LoginLogsService;

/**
 * 接口定义实现
 * 
 * @author bruce
 *
 */
 
@Transactional
@Service("loginLogsService")
public class LoginLogsServiceImpl  implements  LoginLogsService {

	private final static Logger logger= Logger.getLogger(LoginLogsService.class);

	@Autowired
    private LoginLogsDao  loginLogsDao;
		
	/**
     * 通过主键查询
     * @param pk
     * @return
     */
    public LoginLogs findById( String pk ){
        return loginLogsDao.findById(pk);
    }
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public LoginLogs findByName(String objName){
		return loginLogsDao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param loginLogs
	 * @return
	 */
	public LoginLogs findByProps(LoginLogs loginLogs){
		return loginLogsDao.findByProps(loginLogs);
	}
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param loginLogs
     * @return
     */
    public PageView find(PageView pageView,LoginLogs loginLogs ){
        List<LoginLogs> list = loginLogsDao.find(pageView, loginLogs);
        pageView.setRecords(list);
        return pageView;
    }
	
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<LoginLogs> findAll( ){
		return loginLogsDao.findAll();
	}
	
	/**
     * 添加
     * @param loginLogs
     */
    public int addOne(LoginLogs loginLogs){
        return loginLogsDao.addOne(loginLogs);
    }
    
	/**
     * 添加
     * @param loginLogss
     */
    public boolean addAll(List<LoginLogs> loginLogss){
         return loginLogsDao.addAll(loginLogss);
    }
    
	/**
     * 修改一个
     * @param loginLogs
     */
    public int updateOne(LoginLogs loginLogs){
        return loginLogsDao.updateOne(loginLogs);
    }
    
    /**
     * 修改所有
     * @param pks
     */
    public boolean updateAll(List<LoginLogs> pks){
        return loginLogsDao.updateAll(pks);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk){
		return loginLogsDao.deleteById(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks){
		return loginLogsDao.deleteAll(pks);
	}
	
	/**
	 * 获得条数
	 */
	public int getObjsCount(){
		 return loginLogsDao.getObjsCount();
	}
	
    /**
     * 获得条数
     * @param loginLogs
     */
    public int getObjsByProsCount(LoginLogs loginLogs){
         return loginLogsDao.getObjsByProsCount(loginLogs);
    }

	@Override
	public List<LoginLogs> findAllByPros(LoginLogs loginLogs) {
		return loginLogsDao.findAllByPros(loginLogs);
	}

}
