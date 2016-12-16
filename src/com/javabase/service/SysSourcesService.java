package com.javabase.service;

import java.util.List;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysSources;
import com.javabase.entity.SysSourcesRoles;

/**
 * service 的接口定义(实际使用中可以自行添加)
 * 
 * @author bruce
 *
 */
 
public interface SysSourcesService {

	 /**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysSources findById( String pk );
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysSources findByName( String objName );

	/**
	 * 通过属性查询对象.
	 * @param sysSources
	 * @return
	 */
	public SysSources findByProps(SysSources sysSources);

     /**
     * 返回分页后的数据
     * @param pageView
     * @param sysSources
     * @return
     */
    public PageView find(PageView pageView,SysSources sysSources);
    
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<SysSources> findAll();

	/**
	 * 返回所有数据
	 * @param sysSources
	 * @return
	 */
	public List<SysSources> findAllByPros(SysSources sysSources);
	
	/**
	 * 添加一个
	 * @param sysSources
	 */
	public int addOne(SysSources sysSources);
	
    /**
     * 添加多个
     * @param sysSourcess
     */
    public boolean addAll(List<SysSources> sysSourcess);
    	
	/**
	 * 修改一个.
	 * @param sysSources
	 */
	public int updateOne(SysSources sysSources);
	
	/**
	 * 修改所有.
	 * @param sysSourcess
	 */
	public boolean updateAll(List<SysSources> sysSourcess);
	
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
    public int getObjsByProsCount(SysSources sysSources);  
    
	
	//根据用户Id获取该用户的权限
	public List<SysSources> getUserSysSources(String userId);
	//根据角色Id获取该角色的权限
	public List<SysSources> getSysSourcesRoles(String roleId);
	//根据用户名获取该用户的权限
	public List<SysSources> getSysSourcesByUserName(String userName);
	
	public void saveSysSourcesRoles(String roleId,List<String> list);
	
	public void deleteSysSourcesRoles(String roleId);
	
}
