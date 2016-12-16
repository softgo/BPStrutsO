package com.javabase.service;

import java.util.List;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysSourcesRoles;

/**
 * service 的接口定义(实际使用中可以自行添加)
 * 
 * @author bruce
 *
 */
 
public interface SysSourcesRolesService {

	 /**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysSourcesRoles findById( String pk );
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysSourcesRoles findByName( String objName );

	/**
	 * 通过属性查询对象.
	 * @param sysSourcesRoles
	 * @return
	 */
	public SysSourcesRoles findByProps(SysSourcesRoles sysSourcesRoles);

     /**
     * 返回分页后的数据
     * @param pageView
     * @param sysSourcesRoles
     * @return
     */
    public PageView find(PageView pageView,SysSourcesRoles sysSourcesRoles);
    
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<SysSourcesRoles> findAll();

	/**
	 * 返回所有数据
	 * @param sysSourcesRoles
	 * @return
	 */
	public List<SysSourcesRoles> findAllByPros(SysSourcesRoles sysSourcesRoles);
	
	/**
	 * 添加一个
	 * @param sysSourcesRoles
	 */
	public int addOne(SysSourcesRoles sysSourcesRoles);
	
    /**
     * 添加多个
     * @param sysSourcesRoless
     */
    public boolean addAll(List<SysSourcesRoles> sysSourcesRoless);
    	
	/**
	 * 修改一个.
	 * @param sysSourcesRoles
	 */
	public int updateOne(SysSourcesRoles sysSourcesRoles);
	
	/**
	 * 修改所有.
	 * @param sysSourcesRoless
	 */
	public boolean updateAll(List<SysSourcesRoles> sysSourcesRoless);
	
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
    public int getObjsByProsCount(SysSourcesRoles sysSourcesRoles);  
    
}
