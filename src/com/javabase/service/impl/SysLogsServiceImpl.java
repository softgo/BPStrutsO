package com.javabase.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javabase.pulgin.mybatis.plugin.PageView;

import com.javabase.entity.SysLogs;
import com.javabase.dao.SysLogsDao;
import com.javabase.service.SysLogsService;

/**
 * 接口定义实现
 * 
 * @author bruce
 *
 */
 
@Transactional
@Service("sysLogsService")
public class SysLogsServiceImpl  implements  SysLogsService {

	private final static Logger logger= Logger.getLogger(SysLogsService.class);

	@Autowired
    private SysLogsDao  sysLogsDao;
		
	/**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysLogs findById( String pk ){
        return sysLogsDao.findById(pk);
    }
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysLogs findByName(String objName){
		return sysLogsDao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param sysLogs
	 * @return
	 */
	public SysLogs findByProps(SysLogs sysLogs){
		return sysLogsDao.findByProps(sysLogs);
	}
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param sysLogs
     * @return
     */
    public PageView find(PageView pageView,SysLogs sysLogs ){
        List<SysLogs> list = sysLogsDao.find(pageView, sysLogs);
        pageView.setRecords(list);
        return pageView;
    }
	
	/**
     * 添加
     * @param sysLogs
     */
    public int addOne(SysLogs sysLogs){
        return sysLogsDao.addOne(sysLogs);
    }
    
	/**
     * 添加
     * @param sysLogss
     */
    public boolean addAll(List<SysLogs> sysLogss){
         return sysLogsDao.addAll(sysLogss);
    }
    
	/**
     * 修改一个
     * @param sysLogs
     */
    public int updateOne(SysLogs sysLogs){
        return sysLogsDao.updateOne(sysLogs);
    }
    
    /**
     * 修改所有
     * @param pks
     */
    public boolean updateAll(List<SysLogs> pks){
        return sysLogsDao.updateAll(pks);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk){
		return sysLogsDao.deleteById(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks){
		return sysLogsDao.deleteAll(pks);
	}
	
	/**
	 * 获得条数
	 */
	public int getObjsCount(){
		 return sysLogsDao.getObjsCount();
	}
	
    /**
     * 获得条数
     * @param sysLogs
     */
    public int getObjsByProsCount(SysLogs sysLogs){
         return sysLogsDao.getObjsByProsCount(sysLogs);
    }

	@Override
	public List<SysLogs> findAll() {
		return sysLogsDao.findAll();
	}

	@Override
	public List<SysLogs> findAllByPros(SysLogs sysLogs) {
		return sysLogsDao.findAllByPros(sysLogs);
	}

}
