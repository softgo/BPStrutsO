package com.javabase.service.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysSources;
import com.javabase.entity.SysSourcesRoles;
import com.javabase.base.util.Common;
import com.javabase.dao.SysSourcesDao;
import com.javabase.service.SysSourcesService;

/**
 * 接口定义实现
 * 
 * @author bruce
 *
 */
 
@Transactional
@Service("sysSourcesService")
public class SysSourcesServiceImpl  implements  SysSourcesService {

	private final static Logger logger= Logger.getLogger(SysSourcesService.class);

	@Autowired
    private SysSourcesDao  sysSourcesDao;
		
	/**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysSources findById( String pk ){
        return sysSourcesDao.findById(pk);
    }
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysSources findByName(String objName){
		return sysSourcesDao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param sysSources
	 * @return
	 */
	public SysSources findByProps(SysSources sysSources){
		return sysSourcesDao.findByProps(sysSources);
	}
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param sysSources
     * @return
     */
    public PageView find(PageView pageView,SysSources sysSources ){
        List<SysSources> list = sysSourcesDao.find(pageView, sysSources);
        pageView.setRecords(list);
        return pageView;
    }
	
	/**
     * 添加
     * @param sysSources
     */
    public int addOne(SysSources sysSources){
        return sysSourcesDao.addOne(sysSources);
    }
    
	/**
     * 添加
     * @param sysSourcess
     */
    public boolean addAll(List<SysSources> sysSourcess){
         return sysSourcesDao.addAll(sysSourcess);
    }
    
	/**
     * 修改一个
     * @param sysSources
     */
    public int updateOne(SysSources sysSources){
        return sysSourcesDao.updateOne(sysSources);
    }
    
    /**
     * 修改所有
     * @param pks
     */
    public boolean updateAll(List<SysSources> pks){
        return sysSourcesDao.updateAll(pks);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk){
		return sysSourcesDao.deleteById(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks){
		return sysSourcesDao.deleteAll(pks);
	}
	
	/**
	 * 获得条数
	 */
	public int getObjsCount(){
		 return sysSourcesDao.getObjsCount();
	}
	
    /**
     * 获得条数
     * @param sysSources
     */
    public int getObjsByProsCount(SysSources sysSources){
         return sysSourcesDao.getObjsByProsCount(sysSources);
    }

	@Override
	public List<SysSources> getUserSysSources(String userId) {
		return sysSourcesDao.getUserSysSources(userId);
	}

	@Override
	public List<SysSources> getSysSourcesByUserName(String userName) {
		return sysSourcesDao.getSysSourcesByUserName(userName);
	}

	@Override
	public List<SysSources> findAll() {
		return sysSourcesDao.findAll();
	}

	@Override
	public List<SysSources> findAllByPros(SysSources sysSources) {
		return sysSourcesDao.findAllByPros(sysSources);
	}

	@Override
	public List<SysSources> getSysSourcesRoles(String roleId) {
		return sysSourcesDao.getSysSourcesRoles(roleId);
	}

	@Override
	public void deleteSysSourcesRoles(String roleId) {
		 sysSourcesDao.deleteSysSourcesRoles(roleId);
	}

	@Override
	public void saveSysSourcesRoles(String roleId, List<String> list) {
		sysSourcesDao.deleteSysSourcesRoles(roleId);
		for (String rId : list) {
			if(!Common.isEmpty(rId)){
				SysSourcesRoles ssr = new SysSourcesRoles();
				ssr.setRoleId(Integer.parseInt(roleId));
				ssr.setSourceId(Integer.parseInt(rId));
				sysSourcesDao.saveSysSourcesRoles(ssr);
			}
		}
	}

}
