package com.javabase.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javabase.pulgin.mybatis.plugin.PageView;

import com.javabase.entity.SysUsersRoles;
import com.javabase.dao.SysUsersRolesDao;
import com.javabase.service.SysUsersRolesService;

/**
 * 接口定义实现
 * 
 * @author bruce
 *
 */
 
@Transactional
@Service("sysUsersRolesService")
public class SysUsersRolesServiceImpl  implements  SysUsersRolesService {

	private final static Logger logger= Logger.getLogger(SysUsersRolesService.class);

	@Autowired
    private SysUsersRolesDao  sysUsersRolesDao;
		
	/**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysUsersRoles findById( String pk ){
        return sysUsersRolesDao.findById(pk);
    }
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysUsersRoles findByName(String objName){
		return sysUsersRolesDao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param sysUsersRoles
	 * @return
	 */
	public SysUsersRoles findByProps(SysUsersRoles sysUsersRoles){
		return sysUsersRolesDao.findByProps(sysUsersRoles);
	}
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param sysUsersRoles
     * @return
     */
    public PageView find(PageView pageView,SysUsersRoles sysUsersRoles ){
        List<SysUsersRoles> list = sysUsersRolesDao.find(pageView, sysUsersRoles);
        pageView.setRecords(list);
        return pageView;
    }
	
	/**
     * 添加
     * @param sysUsersRoles
     */
    public int addOne(SysUsersRoles sysUsersRoles){
        return sysUsersRolesDao.addOne(sysUsersRoles);
    }
    
	/**
     * 添加
     * @param sysUsersRoless
     */
    public boolean addAll(List<SysUsersRoles> sysUsersRoless){
         return sysUsersRolesDao.addAll(sysUsersRoless);
    }
    
	/**
     * 修改一个
     * @param sysUsersRoles
     */
    public int updateOne(SysUsersRoles sysUsersRoles){
        return sysUsersRolesDao.updateOne(sysUsersRoles);
    }
    
    /**
     * 修改所有
     * @param pks
     */
    public boolean updateAll(List<SysUsersRoles> pks){
        return sysUsersRolesDao.updateAll(pks);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk){
		return sysUsersRolesDao.deleteById(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks){
		return sysUsersRolesDao.deleteAll(pks);
	}
	
	/**
	 * 获得条数
	 */
	public int getObjsCount(){
		 return sysUsersRolesDao.getObjsCount();
	}
	
    /**
     * 获得条数
     * @param sysUsersRoles
     */
    public int getObjsByProsCount(SysUsersRoles sysUsersRoles){
         return sysUsersRolesDao.getObjsByProsCount(sysUsersRoles);
    }

	@Override
	public List<SysUsersRoles> findAll() {
		return sysUsersRolesDao.findAll();
	}

	@Override
	public List<SysUsersRoles> findAllByPros(SysUsersRoles sysUsersRoles) {
		return sysUsersRolesDao.findAllByPros(sysUsersRoles);
	}

}
