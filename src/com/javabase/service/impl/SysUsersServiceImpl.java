package com.javabase.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysUsers;
import com.javabase.dao.SysUsersDao;
import com.javabase.service.SysUsersService;

/**
 * 接口定义实现
 * 
 * @author bruce
 *
 */
 
@Transactional
@Service("sysUsersService")
public class SysUsersServiceImpl  implements  SysUsersService {

	private final static Logger logger= Logger.getLogger(SysUsersService.class);

	@Autowired
    private SysUsersDao  sysUsersDao;
		
	/**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysUsers findById( String pk ){
        return sysUsersDao.findById(pk);
    }
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysUsers findByName(String objName){
		return sysUsersDao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param sysUsers
	 * @return
	 */
	public SysUsers findByProps(SysUsers sysUsers){
		return sysUsersDao.findByProps(sysUsers);
	}
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param sysUsers
     * @return
     */
    public PageView find(PageView pageView,SysUsers sysUsers ){
        List<SysUsers> list = sysUsersDao.find(pageView, sysUsers);
        pageView.setRecords(list);
        return pageView;
    }
	
	/**
     * 添加
     * @param sysUsers
     */
    public int addOne(SysUsers sysUsers){
        return sysUsersDao.addOne(sysUsers);
    }
    
	/**
     * 添加
     * @param sysUserss
     */
    public boolean addAll(List<SysUsers> sysUserss){
         return sysUsersDao.addAll(sysUserss);
    }
    
	/**
     * 修改一个
     * @param sysUsers
     */
    public int updateOne(SysUsers sysUsers){
        return sysUsersDao.updateOne(sysUsers);
    }
    
    /**
     * 修改所有
     * @param pks
     */
    public boolean updateAll(List<SysUsers> pks){
        return sysUsersDao.updateAll(pks);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk){
		return sysUsersDao.deleteById(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks){
		return sysUsersDao.deleteAll(pks);
	}
	
	/**
	 * 获得条数
	 */
	public int getObjsCount(){
		 return sysUsersDao.getObjsCount();
	}
	
    /**
     * 获得条数
     * @param sysUsers
     */
    public int getObjsByProsCount(SysUsers sysUsers){
         return sysUsersDao.getObjsByProsCount(sysUsers);
    }

	@Override
	public int countUser(String userName, String userPass) {
		return sysUsersDao.countUser(userName, userPass);
	}

	@Override
	public List<SysUsers> findAll() {
		return sysUsersDao.findAll();
	}

	@Override
	public List<SysUsers> findAllByPros(SysUsers sysUsers) {
		return sysUsersDao.findAllByPros(sysUsers);
	}

}
