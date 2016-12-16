package com.javabase.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysRoles;
import com.javabase.entity.SysUsersRoles;
import com.javabase.dao.SysRolesDao;
import com.javabase.service.SysRolesService;

/**
 * 接口定义实现
 * 
 * @author bruce
 *
 */
 
@Transactional
@Service("sysRolesService")
public class SysRolesServiceImpl  implements  SysRolesService {

	private final static Logger logger= Logger.getLogger(SysRolesService.class);

	@Autowired
    private SysRolesDao  sysRolesDao;
		
	/**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysRoles findById( String pk ){
        return sysRolesDao.findById(pk);
    }
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysRoles findByName(String objName){
		return sysRolesDao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param sysRoles
	 * @return
	 */
	public SysRoles findByProps(SysRoles sysRoles){
		return sysRolesDao.findByProps(sysRoles);
	}
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param sysRoles
     * @return
     */
    public PageView find(PageView pageView,SysRoles sysRoles ){
        List<SysRoles> list = sysRolesDao.find(pageView, sysRoles);
        pageView.setRecords(list);
        return pageView;
    }
	
	/**
     * 添加
     * @param sysRoles
     */
    public int addOne(SysRoles sysRoles){
        return sysRolesDao.addOne(sysRoles);
    }
    
	/**
     * 添加
     * @param sysRoless
     */
    public boolean addAll(List<SysRoles> sysRoless){
         return sysRolesDao.addAll(sysRoless);
    }
    
	/**
     * 修改一个
     * @param sysRoles
     */
    public int updateOne(SysRoles sysRoles){
        return sysRolesDao.updateOne(sysRoles);
    }
    
    /**
     * 修改所有
     * @param pks
     */
    public boolean updateAll(List<SysRoles> pks){
        return sysRolesDao.updateAll(pks);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk){
		return sysRolesDao.deleteById(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks){
		return sysRolesDao.deleteAll(pks);
	}
	
	/**
	 * 获得条数
	 */
	public int getObjsCount(){
		 return sysRolesDao.getObjsCount();
	}
	
    /**
     * 获得条数
     * @param sysRoles
     */
    public int getObjsByProsCount(SysRoles sysRoles){
         return sysRolesDao.getObjsByProsCount(sysRoles);
    }

	@Override
	public void saveUsersRoles(SysUsersRoles userRoles) {
		sysRolesDao.deleteUsersRoles(userRoles.getSysUserId()+"");
		sysRolesDao.saveUsersRoles(userRoles);
	}

	@Override
	public List<SysRoles> findAll() {
		return sysRolesDao.findAll();
	}

	@Override
	public List<SysRoles> findAllByPros(SysRoles sysRoles) {
		return sysRolesDao.findAllByPros(sysRoles);
	}

	@Override
	public void deleteUsersRoles(String userId) {
		sysRolesDao.deleteUsersRoles(userId);
	}

	@Override
	public SysRoles findByUserRoles(String userId) {
		return sysRolesDao.findByUserRoles(userId);
	}

}
