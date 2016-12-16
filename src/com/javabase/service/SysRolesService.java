package com.javabase.service;

import java.util.List;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysRoles;
import com.javabase.entity.SysUsersRoles;

/**
 * service 的接口定义(实际使用中可以自行添加)
 * 
 * @author bruce
 *
 */
 
public interface SysRolesService {

	 /**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysRoles findById( String pk );
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysRoles findByName( String objName );

	/**
	 * 通过属性查询对象.
	 * @param sysRoles
	 * @return
	 */
	public SysRoles findByProps(SysRoles sysRoles);

     /**
     * 返回分页后的数据
     * @param pageView
     * @param sysRoles
     * @return
     */
    public PageView find(PageView pageView,SysRoles sysRoles);
    
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<SysRoles> findAll();

	/**
	 * 返回所有数据
	 * @param sysRoles
	 * @return
	 */
	public List<SysRoles> findAllByPros(SysRoles sysRoles);
	
	/**
	 * 添加一个
	 * @param sysRoles
	 */
	public int addOne(SysRoles sysRoles);
	
    /**
     * 添加多个
     * @param sysRoless
     */
    public boolean addAll(List<SysRoles> sysRoless);
    	
	/**
	 * 修改一个.
	 * @param sysRoles
	 */
	public int updateOne(SysRoles sysRoles);
	
	/**
	 * 修改所有.
	 * @param sysRoless
	 */
	public boolean updateAll(List<SysRoles> sysRoless);
	
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
    public int getObjsByProsCount(SysRoles sysRoles);  
 
	public void deleteUsersRoles(String userId);
	
	public void saveUsersRoles(SysUsersRoles userRoles);
	
	public SysRoles findByUserRoles(String userId);
	
	
}
