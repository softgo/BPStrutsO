package com.javabase.code.gengerate.mian;

import com.javabase.code.gengerate.def.FtlDef;
import com.javabase.code.gengerate.factory.CodeGenerateFactory;

/**
 * 
 * @author bruce
 *
 */

public class CodeProductorUtil {
	
	/**
	 * test it .
	 * @param args
	 */
	public static void main(String[] args) {
		
		String tableName = "user_share_records";
	    
		String codeName = "风险用户管理列表";
	    
	    String codePrefix = "风险用户列表";
	    
	    String entityPackage = "test\\"; //如果是目录就用"\\",否则就不用.
	    
	    //CodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage, FtlDef.KEY_TYPE_AUTO);
	    
	    CodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage, FtlDef.KEY_TYPE_AUTO);
	    
	    CodeGenerateFactory.addMenus(tableName,codeName,codePrefix);
	    
	}
}
