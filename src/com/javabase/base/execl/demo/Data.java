package com.javabase.base.execl.demo;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.javabase.base.execl.CreateCurrencyExcelReport;
import com.javabase.base.execl.ParameterMap;

public class Data {

	//roleName permissName permissName
	static Map<String, Map<String, String>> rolePermis = new HashMap<String, Map<String, String>>();
	
	//roleId roleName
	static Map<Integer, String> roleIdroleNames = new HashMap<Integer, String>();
	
	// roleId roleId realName
	static Map<Integer, Map<String, String>> roleIdrealNames = new HashMap<Integer, Map<String, String>>();
	
	public static void main(String[] args) {
			roles();
			getData();
		List<DataThree > dataThrees = new ArrayList<>();
		for (Map.Entry<Integer, Map<String, String>> entry : roleIdrealNames.entrySet()) {
			for (Map.Entry<String, String> vEntry : entry.getValue().entrySet()) {
				DataThree three = new DataThree(vEntry.getValue(), roleIdroleNames.get(entry.getKey()), getStr(roleIdroleNames.get(entry.getKey())));
				dataThrees.add(three);
			}
		}
		
		System.out.println(dataThrees.size());
		
		// 生成文件名字
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String fileName = format.format(date) + ".xls";

		// 如果文件夹存在就删除,否则就创建
		String filePath = "/Users/rocky/Desktop/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		// 赋值
		file = null;
		fileName = filePath + fileName;
		System.out.println("文件路径是：" + fileName);

		// 构建对象
		CreateCurrencyExcelReport createport = new CreateCurrencyExcelReport();
		// 构建 map 集合
		Map<String, ParameterMap> map = new LinkedHashMap<String, ParameterMap>();
		map.put("姓名", new ParameterMap("realName", "string"));
		map.put("其所拥有的所有角色", new ParameterMap("roleName", "string"));
		map.put("每个角色的所属页面", new ParameterMap("permissionName", "string"));
		// 执行创建.
		boolean result = createport.createExcelByFile(map, dataThrees, fileName);
		// 结果判断.
		if (result) {
			System.out.println("生成excel表格成功!");
		}
		else {
			System.out.println("生成excel表格失败!");
		}
	}

	private static String getStr(String roleName) {
		StringBuilder builder = new StringBuilder();
		Map<String, String> teMap = rolePermis.get(roleName);
		if (teMap!=null && teMap.size()>0) {
			for (Map.Entry<String, String> entry : teMap.entrySet()) {
				builder.append(entry.getValue()+"\n\t");
			}
		}
		return builder.toString();
	}
	
	private static List<DataTwo> roles() {
		List<DataTwo> result = new ArrayList<DataTwo>();
		String sql = "select sur.role_id roleId,su.realname realName from SYS_USER su inner join SYS_USER_ROLE sur on su.id = sur.user_id order by sur.role_id ";
		DBUtil util = new DBUtil();
		ResultSet set = util.selectSQL(sql);
		try {
			while (set.next()) {
				DataTwo one = new DataTwo(set.getInt("roleId"), set.getString("realName"));
				result.add(one);
				Map<String, String> tmp= null;
				if (!roleIdrealNames.containsKey(set.getInt("roleId"))) {
					tmp=new HashMap<>();
					tmp.put(set.getInt("roleId")+set.getString("realName"), set.getString("realName"));
				}else{
					tmp= roleIdrealNames.get(set.getInt("roleId"));
					tmp.put(set.getInt("roleId")+set.getString("realName"), set.getString("realName"));
				}
				roleIdrealNames.put(set.getInt("roleId"),tmp);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		util.closeAll();
		return result;
	}

	private static List<DataOne> getData() {
		List<DataOne> result = new ArrayList<DataOne>();
		String sql = "select srp.role_id roleId ,sr.role_name roleName, sp.permission_name permissionName from SYS_ROLE_PERMISSION srp inner join SYS_PERMISSION sp on srp.permission_id=sp.id inner join SYS_ROLE sr on sr.id = srp.role_id";
		DBUtil util = new DBUtil();
		ResultSet set = util.selectSQL(sql);
		try {
			while (set.next()) {
				Integer roleId=set.getInt("roleId");
				String roleName = set.getString("roleName");
				String permissionName = set.getString("permissionName");
				DataOne one = new DataOne(roleId,roleName ,permissionName);
				result.add(one);
				Map<String, String> tmp= null;
				if (!rolePermis.containsKey(roleName)) {
					tmp=new HashMap<>();
					tmp.put(permissionName, permissionName);
				}else{
					tmp= rolePermis.get(roleName);
					tmp.put(permissionName, permissionName);
				}
				rolePermis.put(roleName, tmp);
				roleIdroleNames.put(roleId, roleName);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		util.closeAll();
		return result;
	}

}
