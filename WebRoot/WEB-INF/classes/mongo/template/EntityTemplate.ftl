package ${bussPackage}.${basePackage}.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * 实体bean
 * 
 * @author bruce
 *
 */

@Entity(value = "${tableName}", noClassnameStored = true)
public class ${className} implements Serializable {

	${feilds}
	
}

