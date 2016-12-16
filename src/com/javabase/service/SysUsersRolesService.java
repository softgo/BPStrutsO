package com.javabase.service;

import java.util.List;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysUsersRoles;

/**
 * service 的接口定义(实际使用中可以自行添加)
 * 
 * @author bruce
 *
 */
 
public interface SysUsersRolesService {

	 /**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysUsersRoles findById( String pk );
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysUsersRoles findByName( String objName );

	/**
	 * 通过属性查询对象.
	 * @param sysUsersRoles
	 * @return
	 */
	public SysUsersRoles findByProps(SysUsersRoles sysUsersRoles);

     /**
     * 返回分页后的数据
     * @param pageView
     * @param sysUsersRoles
     * @return
     */
    public PageView find(PageView pageView,SysUsersRoles sysUsersRoles);
    
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<SysUsersRoles> findAll();

    
	/**
	 * 返回所有数据
	 * @param sysUsersRoles
	 * @return
	 */
	public List<SysUsersRoles> findAllByPros(SysUsersRoles sysUsersRoles);
	
	/**
	 * 添加一个
	 * @param sysUsersRoles
	 */
	public int addOne(SysUsersRoles sysUsersRoles);
	
    /**
     * 添加多个
     * @param sysUsersRoless
     */
    public boolean addAll(List<SysUsersRoles> sysUsersRoless);
    	
	/**
	 * 修改一个.
	 * @param sysUsersRoles
	 */
	public int updateOne(SysUsersRoles sysUsersRoles);
	
	/**
	 * 修改所有.
	 * @param sysUsersRoless
	 */
	public boolean updateAll(List<SysUsersRoles> sysUsersRoless);
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk);
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks);	

	/**
	 * 获得总条数.
	 * @return
	 */
	public int getObjsCount();	
	
    /**
     * 通过条件获得总条数.
     * @return
     */
    public int getObjsByProsCount(SysUsersRoles sysUsersRoles);  
    
}
