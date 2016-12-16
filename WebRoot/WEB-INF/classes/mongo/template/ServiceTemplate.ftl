package ${bussPackage}.${basePackage}.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
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
    public ${className} findByMongoId(ObjectId pk);
    
    /**
     * 通过名字查询
     * @param proKey,proValue
     * @return
     */
    public ${className} findByMongoName(String proKey,String proValue);
    
    /**
     * 通过对象查找对象
     * @param params
     * @return
     */
    public ${className} findByMongoProps(Map<String, ?> params);
    
    /**
     * 返回分页后的数据
     * @param pageView
     * @param params
     * @return
     */
    public List<${className}> findMongoList(PageView pageView,Map<String, ?> params);
    
    /**
     * 返回分页后的数据
     * @param pageView
     * @param params
     * @return
     */
    public PageView find(PageView pageView,Map<String, ?> params);
    
    /**
     * 返回所有数据
     * @return
     */
    public List<${className}> findMongoAll();
    
    /**
     * 返回所有数据
     * @param params
     * @return
     */
    public List<${className}> findMongoAllByPros(Map<String, ?> params);
    
    /**
     * 添加
     * @param t
     */
    public int addMongoOne(${className} ${lowerName});
    
    /**
     * 添加所有
     * @param ts
     */
    public boolean addMongoAll(List<${className}> ts);
    
    /**
     * 修改
     * @param params
     */
    public int updateMongoOne(ObjectId pk,Map<String, ?> params);  
    
    
     /**
     * 修改
     * @param t
     */
    public int updateMongoOne(${className} ${lowerName});  
    
    /**
     * 修改
     * @param t
     */
    public UpdateResults updateMongoOne(Query<${className}> query, UpdateOperations<${className}> ops);
    
    /**
     * 修改所有属性.
     * @param ts
     */
    public boolean updateMongoAll(List<ObjectId> pks,List<Map<String, ?>> ts);
    
    /**
     * 删除
     * @param pk
     */
    public int deleteByMongoId(ObjectId pk);

    /**
     * 删除所有
     * @param pks
     */
    public boolean deleteMongoAll(List<ObjectId> pks); 

    /**
     * 获得总条数.
     * @return
     */
    public long getObjsMongoCount();    
    
    /**
     * 通过条件获得总条数.
     * @return
     */
    public long getObjsByMongoProsCount(Map<String, ?> params); 
    
}
