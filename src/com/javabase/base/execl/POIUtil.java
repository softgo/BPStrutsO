package com.javabase.base.execl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIUtil {
	public static void main(String[] args) {
		String filename = "D:\\NONOBANK\\CSYY\\DaETiXian\\data.xlsx";
		try {
			List<String[]> list = new ArrayList<String[]>();
			InputStream is = new FileInputStream(filename);
			 HSSFWorkbook hwk = new HSSFWorkbook(is);// 将is流实例到 一个excel流里
			 HSSFSheet sh = hwk.getSheetAt(0);// 得到book第一个工作薄sheet
			 int rows = sh.getLastRowNum()+1 - sh.getFirstRowNum();  // 总行数
			 for(int i=1; i<rows; i++){
			      HSSFRow row = sh.getRow(i);
			      int cols = row.getLastCellNum()+1 - row.getFirstCellNum();  // 该行的总列数
			      String[] str = new String[cols];   // 用来存放该行每一列的值
			      for (int j = 0; j < 2; j++) {
			          Object col = row.getCell((short)j).getStringCellValue();
			          System.out.print(col.toString() + "|");
			          str[j] = col.toString();
			      }
			      System.out.println();
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
