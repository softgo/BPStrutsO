package com.javabase.base.execl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.javabase.base.util.Ip2Long;

public class CreateCurrencyExcelReport {
	
	private Logger log=Logger.getLogger(CreateCurrencyExcelReport.class);
	
	private HSSFWorkbook wb=new HSSFWorkbook();
	
	private int savecount=500;
	//多少条后重新创建excel
	private int createnewcount=20000;
	//文件下标
	private int gropcount=0;
	
	private String filepath;
	/**
	 * 
	 * @param propertymap  	列头 -- 属性 映射 加上列类型
	 * @param list	 	数据集合
	 * @param filepath	文件存储路径
	 * @return			生成是否成功
	 * 
	 */
	public boolean createExcelByFile(Map<String,ParameterMap> propertymap, List list ,String filepath) {
		//创建二级目录
		//生成excuel
		this.filepath=filepath;
		
		HSSFSheet ssh=wb.createSheet();
		
		this.createHead(propertymap,ssh);
		
		return this.createValue(propertymap,list,ssh);
	}
	
	/**
	 *  保存文件.
	 * @param myfilepath 文件路径.
	 * @return
	 */
	public boolean saveWorkbook(String myfilepath){
		
		System.out.println("保存操作传入的是："+myfilepath);
		
		FileOutputStream fos=null;
		
		try{
			fos=new FileOutputStream(myfilepath);
			
			wb.write(fos);
			
			fos.close();
			
			return true;
		}catch(FileNotFoundException e){
			log.error("文件没有找到",e);
			return false;
		}catch(IOException e){
			log.error("其他IO错误!",e);
			return false;
		}finally{
			if(fos!=null){
				try{
					fos.close();
				}catch(IOException e){
					log.error("IO关闭错误!",e);
					return false;
				}
			}
		}
	}
	
	/**
	 * 创建文件头
	 * @param propertymap ： 属性列表
	 * @param list ：list数据集合
	 * @param ssh ：excel表格列
	 * @return
	 */
	private boolean createValue(Map<String,ParameterMap> propertymap,List list, HSSFSheet ssh) {
		int flag=1; 					/*从1开始0用来显示列		*/
		HSSFRow row=null;				/*临时存储创建行			*/
		HSSFCell cell=null;				/*临时存储创建的列			*/
		Object object=null;				/*临时存数据对象			*/
		Long longtime=null;             /*处理 将秒的时间转化为Date	*/
		Date mydate=null;				/*临时存数创建的时间		*/
		String keyname=null;			/*临时存储键值				*/
		ParameterMap paramerter=null;	/*存储键对应的值			*/
		Class myclass=null;				/*临时存储对象class对象	*/
		String methodname=null;			/*方法名称*/
		Method method=null;				/*临时存储方法*/
		int j=0;
		Object propertyvalue=null;		/*存储方法调用的返回结果*/
		log.debug("整个的大小"+list.size());
		//定义多少条重新生成文件
		int count=0;
		for(int i=0;i<list.size();i++)
		{
			count++;
			//重新重新生成文件
			if((i+1)%createnewcount==0){
				if(gropcount==0){
					boolean ff= this.saveWorkbook(this.filepath);
					if(!ff){
						return false;
					}
					gropcount++;
				}else{
					boolean tt= this.saveWorkbook(this.filepath.substring(0,this.filepath.length()-4)+gropcount+".xls");
					if(!tt){
						return false;
					}
					gropcount++;
				}
				wb=new HSSFWorkbook();
				HSSFSheet newsheet=wb.createSheet();
				this.createHead(propertymap, newsheet);
				count=1;
				flag=1;
			}
			if(count%savecount==0){
				log.debug("进入 重新创建一个工作表");
				ssh=wb.createSheet();
				this.createHead(propertymap,ssh);
				flag=1;
			}
			object=list.get(i);
			//根据键查询对应的方法 获取对应的属性
			myclass=object.getClass();
			row=ssh.createRow(flag);
			j=0;
			for (Iterator iterator = propertymap.keySet().iterator(); iterator.hasNext();) {
				 keyname = (String) iterator.next();
				 paramerter= propertymap.get(keyname);
				 //获取
				 methodname=paramerter.getPropertyname(); //test_id ;
				 
				 methodname="get"+methodname.substring(0,1).toUpperCase()+methodname.substring(1); //getTest_id;
				 
			 	 try {
					method= myclass.getMethod(methodname, null);
					System.out.println("The method ="+method);
				} catch (SecurityException e) {
					log.error("反射获取方法错误:",e);
				} catch (NoSuchMethodException e) {
					log.error("没有找到这个方法",e);
				}
			 	 
			 	 cell=row.createCell(j);
			 	 
			 	 try {
			 		// 通过调用类对应的方法,来获得它对应属性的值 .
					propertyvalue= method.invoke(object, null);
				} catch (IllegalArgumentException e) {
					log.error("调用方法参数错误",e);
				} catch (IllegalAccessException e) {
					log.error("非法访问错误",e);
				} catch (InvocationTargetException e) {
					log.error("对象调用错误",e);
				}
				
				/**
				 * 对于一个cell,一个对应的值有一个对应的 type 和对应的 value .
				 */
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				 // 对 propertyvalue 的值类型的判断 .
			 	 if("time".equals(paramerter.getPropertype())){
			 		 if(propertyvalue==null||"".equals(propertyvalue)){
			 			propertyvalue="";
			 		 }else{
			 			propertyvalue=this.changeStringToDate(propertyvalue.toString());
			 		 }
			 	 }
			 	 if("ip".equals(paramerter.getPropertype())){
			 		 if(propertyvalue==null||"".equals(propertyvalue)){
				 			propertyvalue="";
				 		 }else{
				 			propertyvalue=Ip2Long.long2ip(new Long(propertyvalue.toString()));
				 		 }
			 	 }
			 	 if("Date".equals(paramerter.getPropertype())){
			 		 if(propertyvalue==null){
			 			propertyvalue="";
			 		 }else{
			 			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 			propertyvalue=format.format(propertyvalue);
			 		 }
			 	 }
			 	 if("int".equals(paramerter.getPropertype())){
			 		 //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC); //存放的是：Integer类型的数据
			 		 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			 	 }
			 	 if("long".equals(paramerter.getPropertype())){
			 		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			 	 }
				  System.out.println("The propertyvalue="+propertyvalue);
			 	 cell.setCellValue(propertyvalue==null?"":propertyvalue.toString());
			 	 j++;
			}
			
			flag++;
		}
		
		//判断最后的执行操作
		if(list.size()%createnewcount!=0){
			boolean ff= this.saveWorkbook(this.filepath.substring(0,this.filepath.length()-4)+gropcount+".xls");
			if(!ff){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 将面转化成 时间
	 * @param secondString 秒
	 * @return
	 */
	private String changeStringToDate(String secondString){
		if(secondString.indexOf("-")!=-1){
			return secondString;
		}
		secondString=secondString+"000";
		Date date=new Date(new Long(secondString));
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		return format.format(date);
	}
	
	/**
	 * 创建excel 的表头文件
	 * @param propertymap ：表头信息
	 * @param s ： 表格信息
	 */
	private void createHead(Map<String,ParameterMap> propertymap, HSSFSheet ssh) {
		HSSFRow row=ssh.createRow(0);
		HSSFCell cell=null;
		int index=0;
		for (Iterator iterator = propertymap.keySet().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			cell=row.createCell(index);
			cell.setCellValue(name);
			index++;
		}
	}
	
	/**
	 *  顺序的测试 . 
	 * @param args
	 */
	public static void main(String[] args) {
		Map< String , String > map=new LinkedHashMap<String, String>();
		//Map< String , String > map=new HashMap<String, String>();
		map.put("id", "编号");
		map.put("name", "名字");
		map.put("pass", "密码");
		map.put("sex", "性别");
		map.put("age", "年龄");
		map.put("phone", "电话");
		map.put("address", "地址");
		
		Set<String> set=map.keySet();
		if (set!=null) {
			Iterator<String> iterator=set.iterator();
			while (iterator.hasNext()) {
				String key=iterator.next();
				String value=map.get(key);
				System.out.println("The key="+key+",value="+value);
			}
		}
	}
}
