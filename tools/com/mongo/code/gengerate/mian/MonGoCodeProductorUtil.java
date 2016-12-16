package com.mongo.code.gengerate.mian;

import com.mongo.code.gengerate.def.FtlDef;
import com.mongo.code.gengerate.factory.MonGoCodeGenerateFactory;

/**
 * 
 * @author bruce
 *
 */

public class MonGoCodeProductorUtil {

	/**
	 * test it .
	 * @param args
	 */
	public static void main(String[] args) {
		String tableName = "city";
	    String codeName = "城市管理";
	    String codePrefix = "城市";
	    String entityPackage = "zhongsou\\yjmanager\\"; //如果是目录就用"\\",否则就不用.
	    //String entityPackage = "test\\"; //如果是目录就用"\\",否则就不用.
	    //MonGoCodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage,FtlDef.KEY_TYPE_AUTO);
	    MonGoCodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage,FtlDef.KEY_TYPE_MAN);
	    MonGoCodeGenerateFactory.addMenus(tableName,codeName,codePrefix);
	}
}
