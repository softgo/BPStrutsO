package ${bussPackage}.${basePackage}.service.impl;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public ${className} findByMongoId(ObjectId pk) {
        return ${lowerName}Dao.findByMongoId(pk);
    }

	/**
	 * 通过单个属性查询
	 * @param objName
	 * @return
	 */
    @Override
	public ${className} findByMongoName(String proKey, String proValue){
		return ${lowerName}Dao.findByMongoName(proKey, proValue);
	}
	
	/**
	 * 通过对象查询对象.
	 * @param ${lowerName}
	 * @return
	 */
    @Override
	public ${className} findByMongoProps(Map<String, ?> params){
		return ${lowerName}Dao.findByMongoProps(params);
	}
	
    @Override
    public List<${className}> findMongoList(PageView pageView, Map<String, ?> params) {
        return ${lowerName}Dao.findMongoList(pageView, params);
    }
	
    /**
     * 返回分页后的数据
     * @param pageView
     * @param ${lowerName}
     * @return
     */
    @Override
    public PageView find(PageView pageView, Map<String, ?> params ){
        return ${lowerName}Dao.find(pageView, params);
    }

    /**
     * 返回所有数据
     * @return
     */
    @Override
    public List<${className}> findMongoAll(){
        return ${lowerName}Dao.findMongoAll();
    }

	/**
	 * 返回所有数据
	 * @param params
	 * @return
	 */
    @Override
	public List<${className}> findMongoAllByPros( Map<String, ?> params ){
		return ${lowerName}Dao.findMongoAllByPros(params);
	}
	
	/**
     * 添加
     * @param ${lowerName}
     */
    @Override  
    public int addMongoOne(${className} ${lowerName}){
        return ${lowerName}Dao.addMongoOne(${lowerName});
    }
    
	/**
     * 添加
     * @param ${lowerName}s
     */
    @Override
    public boolean addMongoAll(List<${className}> ${lowerName}s){
         return ${lowerName}Dao.addMongoAll(${lowerName}s);
    }
    
     /**
     * 修改
     * @param t
     */
    @Override
    public int updateMongoOne(ObjectId pk,Map<String, ?> params){
        return ${lowerName}Dao.updateMongoOne(pk,params);
    }
    
	/**
     * 修改一个
     * @param ${lowerName}
     */
    public int updateMongoOne(${className} ${lowerName}){
        return ${lowerName}Dao.updateMongoOne(${lowerName});
    }
    
    /**
    * 修改一个
    * @param ${lowerName}
    */
    @Override
    public UpdateResults updateMongoOne(Query<${className}> query,
            UpdateOperations<${className}> ops) {
        return ${lowerName}Dao.updateMongoOne(query,ops);
    }
    
    
    /**
     * 修改所有
     * @param pks
     *  @param ts
     */
    @Override
    public boolean updateMongoAll(List<ObjectId> pks, List<Map<String, ?>> ts){
        return ${lowerName}Dao.updateMongoAll(pks,ts);
    }
	
	/**
	 * 删除一个
	 * @param pk
	 */
    @Override
	public int deleteByMongoId(ObjectId pk){
		return ${lowerName}Dao.deleteByMongoId(pk);
	}
	
	/**
	 * 删除所有
	 * @param pks
	 */
    @Override
	public boolean deleteMongoAll(List<ObjectId> pks){
		return ${lowerName}Dao.deleteMongoAll(pks);
	}
	
	/**
	 * 获得条数
	 */
    @Override
	public long getObjsMongoCount(){
		 return ${lowerName}Dao.getObjsMongoCount();
	}
	
    /**
     * 获得条数
     * @param params
     */
    @Override
    public long getObjsByMongoProsCount(Map<String, ?> params){
         return ${lowerName}Dao.getObjsByMongoProsCount(params);
    }

}
