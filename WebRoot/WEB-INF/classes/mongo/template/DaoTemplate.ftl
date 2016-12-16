package ${bussPackage}.${basePackage}.dao;

import org.bson.types.ObjectId;

import ${bussPackage}.${basePackage}.entity.${className};
import com.javabase.base.MonGoBaseDao;

/**
 * 接口定义
 * 
 * @author bruce
 *
 */
 
public interface ${className}Dao extends MonGoBaseDao<${className},ObjectId > {

	public int updateMongoOne(${className} ${lowerName});
}
