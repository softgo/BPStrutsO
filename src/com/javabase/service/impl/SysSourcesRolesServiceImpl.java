package com.javabase.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javabase.pulgin.mybatis.plugin.PageView;

import com.javabase.entity.SysSourcesRoles;
import com.javabase.dao.SysSourcesRolesDao;
import com.javabase.service.SysSourcesRolesService;

/**
 * 接口定义实现
 * 
 * @author bruce
 *
 */
 
@Transactional
@Service("sysSourcesRolesService")
public class SysSourcesRolesServiceImpl  implements  SysSourcesRolesService {

	private final static Logger logger= Logger.getLogger(SysSourcesRolesService.class);

	@Autowired
    private SysSourcesRolesDao  sysSourcesRolesDao;
		
	/**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysSourcesRoles findById( String pk ){
        return sysSourcesRolesDao.findById(pk);
    }
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysSourcesRoles findByName(String objName){
		return sysSourcesRolesDao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param sysSourcesRoles
	 * @return
	 */
	public SysSourcesRoles findByProps(SysSourcesRoles sysSourcesRoles){
		return sysSourcesRolesDao.findByProps(sysSourcesRoles);
	}
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param sysSourcesRoles
     * @return
     */
    public PageView find(PageView pageView,SysSourcesRoles sysSourcesRoles ){
        List<SysSourcesRoles> list = sysSourcesRolesDao.find(pageView, sysSourcesRoles);
        pageView.setRecords(list);
        return pageView;
    }
	
	/**
     * 添加
     * @param sysSourcesRoles
     */
    public int addOne(SysSourcesRoles sysSourcesRoles){
        return sysSourcesRolesDao.addOne(sysSourcesRoles);
    }
    
	/**
     * 添加
     * @param sysSourcesRoless
     */
    public boolean addAll(List<SysSourcesRoles> sysSourcesRoless){
         return sysSourcesRolesDao.addAll(sysSourcesRoless);
    }
    
	/**
     * 修改一个
     * @param sysSourcesRoles
     */
    public int updateOne(SysSourcesRoles sysSourcesRoles){
        return sysSourcesRolesDao.updateOne(sysSourcesRoles);
    }
    
    /**
     * 修改所有
     * @param pks
     */
    public boolean updateAll(List<SysSourcesRoles> pks){
        return sysSourcesRolesDao.updateAll(pks);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk){
		return sysSourcesRolesDao.deleteById(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks){
		return sysSourcesRolesDao.deleteAll(pks);
	}
	
	/**
	 * 获得条数
	 */
	public int getObjsCount(){
		 return sysSourcesRolesDao.getObjsCount();
	}
	
    /**
     * 获得条数
     * @param sysSourcesRoles
     */
    public int getObjsByProsCount(SysSourcesRoles sysSourcesRoles){
         return sysSourcesRolesDao.getObjsByProsCount(sysSourcesRoles);
    }

	@Override
	public List<SysSourcesRoles> findAll() {
		return sysSourcesRolesDao.findAll();
	}

	@Override
	public List<SysSourcesRoles> findAllByPros(SysSourcesRoles sysSourcesRoles) {
		return sysSourcesRolesDao.findAllByPros(sysSourcesRoles);
	}

}
