package ${bussPackage}.${basePackage}.service;

import java.util.List;

import com.javabase.pulgin.mybatis.plugin.PageView;
import ${bussPackage}.${basePackage}.entity.${className};

/**
 * service 的接口定义(实际使用中可以自行添加)
 * 
 * @author bruce
 *
 */
 
public interface ${className}Service {

	 /**
     * 通过主键查询
     * @param pk
     * @return
     */
    public ${className} findById( String pk );
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public ${className} findByName( String objName );

	/**
	 * 通过属性查询对象.
	 * @param ${lowerName}
	 * @return
	 */
	public ${className} findByProps(${className} ${lowerName});

     /**
     * 返回分页后的数据
     * @param pageView
     * @param ${lowerName}
     * @return
     */
    public PageView find(PageView pageView,${className} ${lowerName});

    /**
     * 返回所有数据
     * @return
     */
    public List<${className}> findAll();
  
	/**
	 * 返回所有数据
	 * @param ${lowerName}
	 * @return
	 */
	public List<${className}> findAllByPros(${className} ${lowerName});

	/**
	 * 添加一个
	 * @param ${lowerName}
	 */
	public int addOne(${className} ${lowerName});
	
    /**
     * 添加多个
     * @param ${lowerName}s
     */
    public boolean addAll(List<${className}> ${lowerName}s);
    	
	/**
	 * 修改一个.
	 * @param ${lowerName}
	 */
	public int updateOne(${className} ${lowerName});
	
	/**
	 * 修改所有.
	 * @param ${lowerName}s
	 */
	public boolean updateAll(List<${className}> ${lowerName}s);
	
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
    public int getObjsByProsCount(${className} ${lowerName});  
    
}
