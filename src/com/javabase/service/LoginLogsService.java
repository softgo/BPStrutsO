package com.javabase.service;

import java.util.List;

import com.javabase.pulgin.mybatis.plugin.PageView;
import com.javabase.entity.LoginLogs;

/**
 * service 的接口定义(实际使用中可以自行添加)
 * 
 * @author bruce
 *
 */
 
public interface LoginLogsService {

	 /**
     * 通过主键查询
     * @param pk
     * @return
     */
    public LoginLogs findById( String pk );
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public LoginLogs findByName( String objName );

	/**
	 * 通过属性查询对象.
	 * @param loginLogs
	 * @return
	 */
	public LoginLogs findByProps(LoginLogs loginLogs);

     /**
     * 返回分页后的数据
     * @param pageView
     * @param loginLogs
     * @return
     */
    public PageView find(PageView pageView,LoginLogs loginLogs);
    
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<LoginLogs> findAll();
	
	/**
	 * 返回所有数据
	 * @param loginLogs
	 * @return
	 */
	public List<LoginLogs> findAllByPros(LoginLogs loginLogs);

	/**
	 * 添加一个
	 * @param loginLogs
	 */
	public int addOne(LoginLogs loginLogs);
	
    /**
     * 添加多个
     * @param loginLogss
     */
    public boolean addAll(List<LoginLogs> loginLogss);
    	
	/**
	 * 修改一个.
	 * @param loginLogs
	 */
	public int updateOne(LoginLogs loginLogs);
	
	/**
	 * 修改所有.
	 * @param loginLogss
	 */
	public boolean updateAll(List<LoginLogs> loginLogss);
	
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
    public int getObjsByProsCount(LoginLogs loginLogs);  
    
}
