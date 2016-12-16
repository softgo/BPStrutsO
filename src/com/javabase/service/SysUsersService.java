package com.javabase.service;

import java.util.List;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysRoles;
import com.javabase.entity.SysUsers;

/**
 * service 的接口定义(实际使用中可以自行添加)
 * 
 * @author bruce
 *
 */
 
public interface SysUsersService {

	 /**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysUsers findById( String pk );
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysUsers findByName( String objName );

	/**
	 * 通过属性查询对象.
	 * @param sysUsers
	 * @return
	 */
	public SysUsers findByProps(SysUsers sysUsers);

     /**
     * 返回分页后的数据
     * @param pageView
     * @param sysUsers
     * @return
     */
    public PageView find(PageView pageView,SysUsers sysUsers);
    
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<SysUsers> findAll();
    
	/**
	 * 返回所有数据
	 * @param sysUsers
	 * @return
	 */
	public List<SysUsers> findAllByPros(SysUsers sysUsers);
	
	/**
	 * 添加一个
	 * @param sysUsers
	 */
	public int addOne(SysUsers sysUsers);
	
    /**
     * 添加多个
     * @param sysUserss
     */
    public boolean addAll(List<SysUsers> sysUserss);
    	
	/**
	 * 修改一个.
	 * @param sysUsers
	 */
	public int updateOne(SysUsers sysUsers);
	
	/**
	 * 修改所有.
	 * @param sysUserss
	 */
	public boolean updateAll(List<SysUsers> sysUserss);
	
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
    public int getObjsByProsCount(SysUsers sysUsers);  
    
	public int countUser(String userName,String userPass);
}
