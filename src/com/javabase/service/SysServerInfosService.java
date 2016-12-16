package com.javabase.service;

import java.util.List;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.SysServerInfos;

/**
 * service 的接口定义(实际使用中可以自行添加)
 * 
 * @author bruce
 *
 */
 
public interface SysServerInfosService {

	 /**
     * 通过主键查询
     * @param pk
     * @return
     */
    public SysServerInfos findById( String pk );
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public SysServerInfos findByName( String objName );

	/**
	 * 通过属性查询对象.
	 * @param sysServerInfos
	 * @return
	 */
	public SysServerInfos findByProps(SysServerInfos sysServerInfos);

     /**
     * 返回分页后的数据
     * @param pageView
     * @param sysServerInfos
     * @return
     */
    public PageView find(PageView pageView,SysServerInfos sysServerInfos);
    
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<SysServerInfos> findAll();

	/**
	 * 返回所有数据
	 * @param sysServerInfos
	 * @return
	 */
	public List<SysServerInfos> findAllByPros(SysServerInfos sysServerInfos);
	
	/**
	 * 添加一个
	 * @param sysServerInfos
	 */
	public int addOne(SysServerInfos sysServerInfos);
	
    /**
     * 添加多个
     * @param sysServerInfoss
     */
    public boolean addAll(List<SysServerInfos> sysServerInfoss);
    	
	/**
	 * 修改一个.
	 * @param sysServerInfos
	 */
	public int updateOne(SysServerInfos sysServerInfos);
	
	/**
	 * 修改所有.
	 * @param sysServerInfoss
	 */
	public boolean updateAll(List<SysServerInfos> sysServerInfoss);
	
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
    public int getObjsByProsCount(SysServerInfos sysServerInfos);  
    
}
