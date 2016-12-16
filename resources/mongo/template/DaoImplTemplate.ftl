package ${bussPackage}.${basePackage}.dao.impl;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.stereotype.Repository;
import com.javabase.mongo.MonGoSettingManager;
import com.javabase.base.util.StringUtil;
import com.mongodb.WriteResult;

import ${bussPackage}.${basePackage}.entity.${className};
import ${bussPackage}.${basePackage}.dao.${className}Dao;

/**
 * 接口实现.
 * 
 * @author bruce
 *
 */
 
@Repository("${lowerName}Dao")
public class ${className}DaoImpl extends MonGoSettingManager<${className},ObjectId> implements ${className}Dao{
	
	public int updateMongoOne(${className} ${lowerName}){
	   //根据实际情况去实现.如下是一个示例:
	   /*
	    Query<City> query = createQuery().filter("_id =", city.get_id());
        UpdateOperations<City> upOperations = createUpdateOperations();
        if (StringUtil.isNotBlank(city.getCode())) {
            upOperations.set("code", city.getCode());
        }
        if (StringUtil.isNotBlank(city.getType() + "")) {
            upOperations.set("type", city.getType());
        }
        if (StringUtil.isNotBlank(city.getText())) {
            upOperations.set("text", city.getText());
        }
        if (StringUtil.isNotBlank(city.getId())) {
            upOperations.set("id", city.getId());
        }
        WriteResult wr = super.update(query, upOperations).getWriteResult();
        int result = wr.getN();
        System.out.println("得到结果是： " + result);
	   */
	   return -1;
	}
}
