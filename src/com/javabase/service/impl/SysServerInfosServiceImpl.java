package com.javabase.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javabase.pulgin.mybatis.plugin.PageView;

import com.javabase.entity.SysServerInfos;
import com.javabase.dao.SysServerInfosDao;
import com.javabase.service.SysServerInfosService;

/**
 * 接口定义实现
 * 
 * @author bruce
 *
 */
 
@Transactional
@Service("sysServerInfosService")
public class SysServerInfosServiceImpl  implements  SysServerInfosService {

	private final static Logger logger= Logger.getLogger(SysServerInfosService.class);

	@Autowired
    private SysServerInfosDao  sysServerInfosDao;
		
	/**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysServerInfos findById( String pk ){
        return sysServerInfosDao.findById(pk);
    }
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysServerInfos findByName(String objName){
		return sysServerInfosDao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param sysServerInfos
	 * @return
	 */
	public SysServerInfos findByProps(SysServerInfos sysServerInfos){
		return sysServerInfosDao.findByProps(sysServerInfos);
	}
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param sysServerInfos
     * @return
     */
    public PageView find(PageView pageView,SysServerInfos sysServerInfos ){
        List<SysServerInfos> list = sysServerInfosDao.find(pageView, sysServerInfos);
        pageView.setRecords(list);
        return pageView;
    }
	
	/**
     * 添加
     * @param sysServerInfos
     */
    public int addOne(SysServerInfos sysServerInfos){
        return sysServerInfosDao.addOne(sysServerInfos);
    }
    
	/**
     * 添加
     * @param sysServerInfoss
     */
    public boolean addAll(List<SysServerInfos> sysServerInfoss){
         return sysServerInfosDao.addAll(sysServerInfoss);
    }
    
	/**
     * 修改一个
     * @param sysServerInfos
     */
    public int updateOne(SysServerInfos sysServerInfos){
        return sysServerInfosDao.updateOne(sysServerInfos);
    }
    
    /**
     * 修改所有
     * @param pks
     */
    public boolean updateAll(List<SysServerInfos> pks){
        return sysServerInfosDao.updateAll(pks);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk){
		return sysServerInfosDao.deleteById(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks){
		return sysServerInfosDao.deleteAll(pks);
	}
	
	/**
	 * 获得条数
	 */
	public int getObjsCount(){
		 return sysServerInfosDao.getObjsCount();
	}
	
    /**
     * 获得条数
     * @param sysServerInfos
     */
    public int getObjsByProsCount(SysServerInfos sysServerInfos){
         return sysServerInfosDao.getObjsByProsCount(sysServerInfos);
    }

	@Override
	public List<SysServerInfos> findAll() {
		return sysServerInfosDao.findAll();
	}

	@Override
	public List<SysServerInfos> findAllByPros(SysServerInfos sysServerInfos) {
		return sysServerInfosDao.findAllByPros(sysServerInfos);
	}

}
