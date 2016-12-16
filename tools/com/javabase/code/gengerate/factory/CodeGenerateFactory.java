package com.javabase.code.gengerate.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import com.javabase.code.gengerate.CommonPageParser;
import com.javabase.code.gengerate.CreateBean;
import com.javabase.code.gengerate.def.CodeResourceUtil;
import com.javabase.entity.SysSources;
import com.javabase.entity.SysSourcesRoles;

/**
 * 
 * 数据列实例化对象
 * 
 * @author bruce.
 * 
 */

public class CodeGenerateFactory {

	private static final Log logger = LogFactory.getLog(CodeGenerateFactory.class);
	private static String url = CodeResourceUtil.URL;
	private static String username = CodeResourceUtil.USERNAME;
	private static String passWord = CodeResourceUtil.PASSWORD;
	private static CreateBean createBean = null;
	private static String buss_package = CodeResourceUtil.bussiPackage;
	private static String projectPath = getProjectPath();

	/**
	 * 初始化数据bean.
	 */
	private static void initCreateBean(){
		if (createBean==null) {
			createBean = new CreateBean();
			createBean.setMysqlInfo(url, username, passWord);//设置数据库连接
		}
	}
	
	/**
	  * 生成相应代码的地方。
	  * 
	  * @param tableName:表名字.
	  * @param codeName:模块名字.
	  * @param entityPackage:包名后边需要加上"\\".
	  * @param keyType:1手动加入;2自动添加.
	  */
	public static void codeGenerate(String tableName, String codeName,
			String entityPackage, String autoType ) {
		
		initCreateBean();

		String className = createBean.getTablesNameToClassName(tableName);
		
		String lowerName = className.substring(0, 1).toLowerCase()+ className.substring(1, className.length());

		String srcPath = projectPath + CodeResourceUtil.source_root_package+ "\\";

		String pckPath = srcPath + CodeResourceUtil.bussiPackageUrl + "\\";

		String pagePath = projectPath+CodeResourceUtil.web_root_package+"//WEB-INF//jsp//background//";

		String beanPath = entityPackage + "entity\\" + className + ".java";
		
		String mapperPath = entityPackage +  "dao\\" + className + "Dao.java";
		
		String mapperImplPath = entityPackage +  "dao\\impl\\" + className + "DaoImpl.java";
		
		String servicePath = entityPackage +  "service\\" + className + "Service.java";
		
		String serviceImplPath = entityPackage +  "service\\impl\\" + className + "ServiceImpl.java";
		
		String controllerPath = entityPackage +  "controller\\" + className + "Controller.java";
		
		String sqlMapperPath = entityPackage +  "entity\\" + className.toLowerCase()+ "-mapper.xml";

		String listSavePath = lowerName+"\\list.jsp";
		
		String addSavePath = lowerName+"\\add.jsp";
		
		String editSavePath = lowerName+"\\edit.jsp";
		
		String showSavePath = lowerName+"\\show.jsp";
		
		VelocityContext context = new VelocityContext();
		context.put("className", className);
		context.put("lowerName", lowerName);
		context.put("codeName", codeName);
		context.put("tableName", tableName);
		context.put("nameSpaceName", className.toLowerCase());
		context.put("bussPackage", buss_package);
		context.put("entityPackage", entityPackage);
		context.put("keyType", autoType);
		//包名的设置.
		
		String basePackage = null;
		if (entityPackage.replace("\"", "\\").contains("\\")) {
			basePackage = entityPackage.replace("\"", "\\").replace("\\", ".").substring(0, entityPackage.length()-1);
			context.put("basePackage", basePackage);
		}else {
			context.put("basePackage", entityPackage);
		}
		
		try {
			context.put("feilds", createBean.getBeanFeilds(tableName));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Map sqlMap = createBean.getAutoCreateSql(tableName,autoType,lowerName);
			context.put("SQL", sqlMap);
		} catch (Exception sqlMap) {
			sqlMap.printStackTrace();
			return;
		}

		CommonPageParser.WriterPage(context, "EntityTemplate.ftl", pckPath,beanPath);
		CommonPageParser.WriterPage(context, "DaoTemplate.ftl", pckPath,mapperPath);
		CommonPageParser.WriterPage(context, "DaoImplTemplate.ftl", pckPath,mapperImplPath);
		CommonPageParser.WriterPage(context, "ServiceTemplate.ftl", pckPath,servicePath);
		CommonPageParser.WriterPage(context, "ServiceImplTemplate.ftl", pckPath,serviceImplPath);
		CommonPageParser.WriterPage(context, "MapperTemplate.xml", pckPath,sqlMapperPath);
		CommonPageParser.WriterPage(context, "ControllerTemplate.ftl", pckPath,controllerPath);
		//界面生成.
		CommonPageParser.WriterPage(context, "JavaBeanListTemplate.ftl", pagePath,listSavePath);
		CommonPageParser.WriterPage(context, "JavaBeanAddTemplate.ftl", pagePath,addSavePath);
		CommonPageParser.WriterPage(context, "JavaBeanEditTemplate.ftl", pagePath,editSavePath);
		CommonPageParser.WriterPage(context, "JavaBeanShowTemplate.ftl", pagePath,showSavePath);
		
		//生成住注册的,可控性不好,不做自动生成.		
		logger.info("----------------------------代码生成完毕---------------------------");
		System.out.println("恭喜,项目中的工具类代码生成完成!");
	}

	public static String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}

	/**
	 * 完善功能项.
	 * @param tableName
	 * @param codeName
	 * @param codePrefix
	 */
	public static void addMenus(String tableName,String codeName,String codePrefix) {
		try {
			
			initCreateBean();
			
			List<SysSources> sysSourcess = new ArrayList<SysSources>();
			List<SysSourcesRoles> ssrRoles = new ArrayList<SysSourcesRoles>();
			int index = getMaxSid();
			String className = createBean.getTablesNameToClassName(tableName);
			String lowerName = className.substring(0, 1).toLowerCase()+ className.substring(1, className.length());//文件的路径.
			//资源.
			SysSources source1 = new SysSources(index+1,codeName,1010,tableName,"0",tableName,index+1,codeName);
			SysSources source2 = new SysSources(index+2,codePrefix+"列表",index+1,tableName+"_find","1","/background/"+lowerName+"/find.html",index+2,codePrefix+"列表");
			SysSources source3 = new SysSources(index+3,"添加"+codePrefix,index+2,tableName+"_addUI","2","/background/"+lowerName+"/addUI.html",index+3,"添加"+codePrefix);
			SysSources source4 = new SysSources(index+4,"编辑"+codePrefix,index+2,tableName+"_edit","2","/background/"+lowerName+"/findById.html",index+4,"编辑"+codePrefix);
			SysSources source5 = new SysSources(index+5,"删除"+codePrefix,index+2,tableName+"_delete","2","/background/"+lowerName+"/deleteById.html",index+5,"删除"+codePrefix);
			SysSources source6 = new SysSources(index+6,"详细信息",index+2,tableName+"_info","2",tableName+"_info",index+6,"详细信息");
			sysSourcess.add(source1); sysSourcess.add(source2);	sysSourcess.add(source3);
			sysSourcess.add(source4); sysSourcess.add(source5);	sysSourcess.add(source6);
			
			//关联.
			SysSourcesRoles ssrRoles1 = new SysSourcesRoles(index+1,1);
			SysSourcesRoles ssrRoles2 = new SysSourcesRoles(index+2,1);
			SysSourcesRoles ssrRoles3 = new SysSourcesRoles(index+3,1);
			SysSourcesRoles ssrRoles4 = new SysSourcesRoles(index+4,1);
			SysSourcesRoles ssrRoles5 = new SysSourcesRoles(index+5,1);
			SysSourcesRoles ssrRoles6 = new SysSourcesRoles(index+6,1);
			ssrRoles.add(ssrRoles1); ssrRoles.add(ssrRoles2); ssrRoles.add(ssrRoles3);
			ssrRoles.add(ssrRoles4); ssrRoles.add(ssrRoles5); ssrRoles.add(ssrRoles6);
			
			boolean result = addSources(sysSourcess);
			if (result) {
				addSourcesRoles(ssrRoles);
			}
			System.out.println("注册功能表单完成!");
		}
		catch (Exception e) {
			logger.error("添加表单功能实现!");
		}		
	}
	
	/**
	 * 获得资源表最大的资源id
	 * @return
	 */
	private static int getMaxSid(){
		try {
			Connection connection = createBean.getConnection();
			String sql = "select max(source_id) from sys_sources ";
			Statement stmt= connection.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			if (res.next()) {
				return res.getInt(1);
			}
		}
		catch (Exception e) {
			logger.error("获得最大资源表的id失败!"+e.getMessage());
			return -1;
		}
		return -1;
	}
	
	/**
	 * 添加资源的 sql 拼装.
	 * @param sysSources
	 * @return
	 */
	private static boolean addSources(List<SysSources> sysSourcess) {
		try {
			Connection connection = createBean.getConnection();
			Statement stmt = connection.createStatement();
			for (SysSources sysSources : sysSourcess) {
				StringBuffer sql = new StringBuffer( "insert into sys_sources (source_id,source_name,parent_id,source_key,source_type,source_url,source_level,description) values (");
				sql.append(sysSources.getSourceId()+",");
				sql.append("'"+sysSources.getSourceName()+"',");
				sql.append(sysSources.getParentId()+",");
				sql.append("'"+sysSources.getSourceKey()+"',");
				sql.append("'"+sysSources.getSourceType()+"',");
				sql.append("'"+sysSources.getSourceUrl()+"',");
				sql.append(sysSources.getSourceLevel()+",");
				sql.append("'"+sysSources.getDescription()+"'");
				sql.append(");");
				stmt.addBatch(sql.toString());
			}
			stmt.executeBatch();
			stmt.close();
			connection.close();
			
			return true;
		}
		catch (Exception e) {
			logger.error("添加资源失败!");
			return false;
		}
	}
	
	/**
	 * 添加资源角色的 sql 拼装.
	 * @param sysSources
	 * @return
	 */
	private static void addSourcesRoles(List<SysSourcesRoles> ssrRoles) {
		try {
			Connection connection = createBean.getConnection();
			Statement stmt = connection.createStatement();
			for (SysSourcesRoles ssrRole : ssrRoles) {
				StringBuffer sql = new StringBuffer( "insert into sys_sources_roles (source_id,role_id) values (");
				sql.append(ssrRole.getSourceId()+",");
				sql.append(ssrRole.getRoleId());
				sql.append(");");
				stmt.addBatch(sql.toString());
			}
			
			stmt.executeBatch();
			stmt.close();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("添加资源和角色关系出错了.");
		}
	}
	
}
