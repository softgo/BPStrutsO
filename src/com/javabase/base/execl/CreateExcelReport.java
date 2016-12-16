package com.javabase.base.execl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

public class CreateExcelReport {

	public String titleType = "";
	//多少条 先存盘 再次 创建工作薄
	private int savecount=1000;
	//工作簿的个数
	private int workbooknum=0;
	//是否结束
	private String status="start";
	
	private HSSFWorkbook workbook=new HSSFWorkbook();
	
	/**
	 * 创建excel表格
	 * @param title 表头的信息
	 * @param listSize 一次传入的大小
	 * @param list 
	 * @return
	 */
	public String createExcelByFile(String[] title, int listSize, List list) {
		
		/*
		String	reportName = this.getNowTime();
		System.out.println("结果条数:"+list.size());
		while("start".equals(status)){
			createExcel(title,list,reportName);
		}
		//打成zip包
		try {
			ZipUtil.zip(StaticSession.filename_filezip+reportName+".zip",StaticSession.filename_file+reportName+"/"+reportName+".xls");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return reportName;
		*/
		return "";
	}
	private boolean  createExcel(String[]  title,List list,String reportName){
	
		/*
		String outputFile = "";
		String getReportPath = StaticSession.filename_file;
		File alramlog = new File(StaticSession.filename_file);
		if(!alramlog.exists()){
			alramlog.mkdir();
		}
		//创建二级目录
		File myalramlog=new File(StaticSession.filename_file+reportName);
		if(!myalramlog.exists()){
			myalramlog.mkdir();
		}
		//生成excuel
		FileOutputStream fos=null;
		try{
			fos=new FileOutputStream(StaticSession.filename_file+reportName+"/"+reportName+".xls");
			HSSFSheet s=workbook.createSheet();
			this.createHead(title,s);
			this.createValue(list,s);
			workbook.write(fos);
			fos.close();
			return true;
		}catch(FileNotFoundException e){
			e.printStackTrace();
			return false;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}finally{
			if(fos!=null){
				try{
					fos.close();
				}catch(IOException e){
					e.printStackTrace();
					return false;
				}
			}
		}
		*/
		return false;
	}
	
	private void createValue(List list, HSSFSheet s) {
		
		int flag=1; 					/*从1开始0用来显示列		*/
		HSSFRow row=null;				/*临时存储创建行			*/
		HSSFCell cell=null;				/*临时存储创建的列			*/
		//VgapFlowId vga=null;			/*临时存数流量对象			*/
		Long longtime=null;             /*处理 将秒的时间转化为Date	*/
		Date mydate=null;				/*临时存数创建的时间		*/
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		
		/*
		for(int i=workbooknum*savecount;i<list.size();i++)
		{
			//写死了对象 可以用反射就对象写活
			vga=(VgapFlowId)list.get(i);
			row=s.createRow(flag);
			cell=row.createCell(0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(vga.getUsername());
			cell=row.createCell(1);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			//将源端IP转换成 x.x.x.x 形式
			if (vga.getSourceip()!=null) {
				cell.setCellValue(Ip2Long.long2ip(vga.getSourceip()));
			}
			
			cell=row.createCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(vga.getSourceport());
			cell=row.createCell(3);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			//将源端IP转换成 x.x.x.x 形式
			if (vga.getDesip()!=null) {
				cell.setCellValue(Ip2Long.long2ip(vga.getDesip()));
			}
			
			cell=row.createCell(4);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(vga.getDesport());
			cell=row.createCell(5);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			longtime=new Long(vga.getStarttime()+"000");
			mydate=new Date(longtime);
			cell.setCellValue(format.format(mydate));
			cell=row.createCell(6);
			longtime=new Long(vga.getEndtime()+"000");
			mydate=new Date(longtime);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(format.format(mydate));
			cell=row.createCell(7);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			DecimalFormat myformat1 = new DecimalFormat("0"); //0.00
			if (vga.getFlowvalue()!=null) {
				cell.setCellValue(myformat1.format( vga.getFlowvalue()/1024/1024));
			}
			//达到最大存储个数
			if(i==list.size()-1){
				status="end";
				break;
			}
			if(flag>savecount){
				break;
			}
			flag++;
		}
		//创建工作
		workbooknum++;
		*/
	}

	private void createHead(String[] title, HSSFSheet s) {
		HSSFRow row=s.createRow(0);
		HSSFCell cell=null;
		for(int i=0;i<title.length;i++){
			cell=row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title[i]);
		}
	}

	/**
	 * 数据的单元格样式
	 * 
	 * @return
	 */
	public HSSFCellStyle getGenCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 单元格内容右对齐.
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 设置上下左右边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}

	/**
	 * 创建单元格的标注
	 */
	public HSSFCellStyle getTitleCellStyle(HSSFWorkbook workbook, HSSFFont hf) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		hf.setFontHeight((short) 300);
		hf.setColor(HSSFFont.COLOR_NORMAL);
		hf.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 设置粗体.

		cellStyle.setFont(hf); // 粗体

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 单元格内容中间对齐
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 设置上下左右边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}

	/**
	 * 返回报表头的样式
	 */
	public void getHeadRow(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow row,
			HSSFFont hf, short num) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFCell cell = row.createCell((short) 0);
		hf.setFontHeight((short) 1);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) num));
		cellStyle.setFont(hf);// 
		cellStyle.setAlignment((short) HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(cellStyle);
		//cell.setEncoding((short) HSSFCell.ENCODING_UTF_16);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(titleType);
	}
	/**
	 * 返回报表头的样式
	 */
	public void getHeadRow(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow row,
			HSSFFont hf) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		row = sheet.createRow((short) 0);
		HSSFCell cell = row.createCell((short) 0);
		hf.setFontHeight((short) 1);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 7));
		cellStyle.setFont(hf);// 
		cellStyle.setAlignment((short) HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(cellStyle);
		//cell.setEncoding((short) HSSFCell.ENCODING_UTF_16);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(titleType);
	}
	/**
	 * 定义时间类型的单元格
	 */
	public HSSFCellStyle getDateCellStyle(HSSFWorkbook workbook) {

		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 单元格内容右对齐.
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 设置上下左右边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

		return cellStyle;
	}

	/**
	 * 定义获取当前时间
	 */
	public static String getNowTime() {
		SimpleDateFormat timeFormart = new SimpleDateFormat("yyMMddhhmmssS");
		String time = timeFormart.format(new Date(System.currentTimeMillis()));
		return time;
	}
	public static void main(String[] args) {
		
		System.out.println(getNowTime());
		Thread thread=new Thread();
		
		try {
			thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(getNowTime());
	}
}

