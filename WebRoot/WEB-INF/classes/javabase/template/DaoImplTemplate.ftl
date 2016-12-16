package ${bussPackage}.${basePackage}.dao.impl;

import org.springframework.stereotype.Repository;
import com.javabase.base.impl.BaseDaoImpl;

import ${bussPackage}.${basePackage}.entity.${className};
import ${bussPackage}.${basePackage}.dao.${className}Dao;

/**
 * 接口实现.
 * 
 * @author bruce
 *
 */
 
 @Repository("${lowerName}Dao")
public class ${className}DaoImpl extends BaseDaoImpl<${className}> implements ${className}Dao{
	
}
