package com.javabase.base.execl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.javabase.base.util.DBCommandUtil;

public class CreateExcel {
	
	public static DBCommandUtil manager=new DBCommandUtil();
	/**
	 * 插入数据
	 */
	public static void insertData(){
		
		String sqlString=null;
		for (int i = 0; i < 100000; i++) {
			if (i%2==0) {
				sqlString="insert into testexcel(test_name,test_pass,test_sex,test_age,test_phone,test_address) values('张山1','zhangsan1','男',22,'13696385223','北京市海淀区')";
			}else {
				sqlString="insert into testexcel(test_name,test_pass,test_sex,test_age,test_phone,test_address) values('张山0','zhangsan0','女',20,'13696385222','北京市朝阳区')";
			}
			//插入。
			manager.executeSQL(sqlString);
		}		
	}
	
	/**
	 * 创建cxcel表格
	 */
	public  static void createExcel() {
		String sql="select * from testexcel ";
		List<UserBean> userList=new ArrayList<UserBean>();
		ResultSet rs=manager.selectSQL(sql);
		try {
			while (rs.next()) {
				UserBean userBean=new UserBean();
				userBean.setTest_id(Integer.parseInt(rs.getString("test_id")));
				userBean.setTest_name(rs.getString("test_name"));
				userBean.setTest_pass(rs.getString("test_pass"));
				userBean.setTest_sex(rs.getString("test_sex"));
				userBean.setTest_age(Integer.parseInt(rs.getString("test_age")));
				userBean.setTest_phone(rs.getString("test_phone"));
				userBean.setTest_address(rs.getString("test_address"));
				//add to the list.
				userList.add(userBean);
			}
		} catch (SQLException e) {
			System.out.println("sql 语法出错了！"+e.getMessage());
		}
		
		
		//生成文件名字
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String fileName=format.format(date)+".xls";
		
		//如果文件夹存在就删除,否则就创建
		String filePath="F:\\temp\\";
		File file=new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		//赋值
		file=null;
		fileName=filePath+fileName;
		System.out.println("文件路径是："+fileName);
		
		//构建对象
		CreateCurrencyExcelReport createport=new CreateCurrencyExcelReport();
		//构建 map 集合
		Map<String,ParameterMap> map=new LinkedHashMap<String, ParameterMap>();
		map.put("编号", new ParameterMap("test_id","int"));
		map.put("名字", new ParameterMap("test_name","string"));
		map.put("密码", new ParameterMap("test_pass","string"));
		map.put("性别", new ParameterMap("test_sex","string"));
		map.put("年龄", new ParameterMap("test_age","int"));
		map.put("电话号码", new ParameterMap("test_phone","string"));
		map.put("联系地址", new ParameterMap("test_address","string"));
		map.put("名字", new ParameterMap("test_name","string"));
		
		//执行创建.
		boolean result=createport.createExcelByFile(map, userList, fileName);
		//结果判断.
		if (result) {
			System.out.println("生成excel表格成功!");
		}else {
			System.out.println("生成excel表格失败!");
		}
	}
		
	/**
	 * test it.
	 * @param args
	 */
	public static void main(String[] args) {
		//insertData();
		createExcel();
	}

}
