package ${bussPackage}.${basePackage}.service.impl;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javabase.pulgin.mybatis.plugin.PageView;

import ${bussPackage}.${basePackage}.entity.${className};
import ${bussPackage}.${basePackage}.dao.${className}Dao;
import ${bussPackage}.${basePackage}.service.${className}Service;

/**
 * 接口定义实现
 * 
 * @author bruce
 *
 */
 
@Transactional
@Service("${lowerName}Service")
public class ${className}ServiceImpl implements ${className}Service {

	private final static Logger logger= Logger.getLogger(${className}Service.class);

	@Autowired
    private ${className}Dao  ${lowerName}Dao;
		
	/**
     * 通过主键查询
     * @param pk
     * @return
     */
    public ${className} findById( String pk ){
        return ${lowerName}Dao.findById(pk);
    }
    
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public ${className} findByName(String objName){
		return ${lowerName}Dao.findByName(objName);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param ${lowerName}
	 * @return
	 */
	public ${className} findByProps(${className} ${lowerName}){
		return ${lowerName}Dao.findByProps(${lowerName});
	}
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param ${lowerName}
     * @return
     */
    public PageView find(PageView pageView,${className} ${lowerName} ){
        List<${className}> list = ${lowerName}Dao.find(pageView, ${lowerName});
        pageView.setRecords(list);
        return pageView;
    }

    /**
     * 返回所有数据
     * @return
     */
    public List<${className}> findAll(){
        return ${lowerName}Dao.findAll();
    }

	/**
	 * 返回所有数据
	 * @param ${lowerName}
	 * @return
	 */
	public List<${className}> findAllByPros(${className} ${lowerName}){
		return ${lowerName}Dao.findAllByPros(${lowerName});
	}
	
	/**
     * 添加
     * @param ${lowerName}
     */
    public int addOne(${className} ${lowerName}){
        return ${lowerName}Dao.addOne(${lowerName});
    }
    
	/**
     * 添加
     * @param ${lowerName}s
     */
    public boolean addAll(List<${className}> ${lowerName}s){
         return ${lowerName}Dao.addAll(${lowerName}s);
    }
    
	/**
     * 修改一个
     * @param ${lowerName}
     */
    public int updateOne(${className} ${lowerName}){
        return ${lowerName}Dao.updateOne(${lowerName});
    }
    
    /**
     * 修改所有
     * @param pks
     */
    public boolean updateAll(List<${className}> pks){
        return ${lowerName}Dao.updateAll(pks);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
	public int deleteById(String pk){
		return ${lowerName}Dao.deleteById(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteAll(List<String> pks){
		return ${lowerName}Dao.deleteAll(pks);
	}
	
	/**
	 * 获得条数
	 */
	public int getObjsCount(){
		 return ${lowerName}Dao.getObjsCount();
	}
	
    /**
     * 获得条数
     * @param ${lowerName}
     */
    public int getObjsByProsCount(${className} ${lowerName}){
         return ${lowerName}Dao.getObjsByProsCount(${lowerName});
    }

}
