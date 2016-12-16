package com.javabase.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 一些日期处理的辅助类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) compnone 2003
 * </p>
 * <p>
 * Company: cmpnone
 * </p>
 * 
 * @author wuxk
 * @version 1.0
 */
public class DateTime {
	private DateTime() {
	}
	/**
	 * 从字符串中取得日期，比如字符串2004-9-9转化成日期型
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String sDate) throws ParseException {
		sDate = sDate.replace('/', '-');
		java.util.Date ltime = DateFormat.getDateInstance().parse(sDate);
		return new Date(ltime.getTime());
	}
	
	public static long getFormatDate(String sDate){
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime()/1000;
	}
	
	/**
	 * 获取昨天的日期
	 * @param args
	 */
	public static String yesterDateMid(){
		  Date as = new Date(new Date().getTime()-12*60*60*1000);
		  SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
		  String time = matter1.format(as);
		  return time;
	}
	/**
	 * 将日期转换成字符串表示，如2004-09-09 17:09:09
	 * @param d
	 * @return
	 */
	public static String getFormatDate(Date d){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
	}
	/**
	 * 将日期转换成字符串表示，如2004-09-09 17:09:09
	 * @param d
	 * @return
	 */
	public static String getFormatDay(Date d){
		return new SimpleDateFormat("yyyy/MM/dd").format(d);
	}

	/**
	 * @return 返回字符串型式表示的当前时间如: 2002-12-10 12:10:18
	 */
	public static String now() {
		GregorianCalendar calenda = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(calenda.getTime());
	}

	/**
	 * @return 带上时分秒的当前时间
	 */
	public static Date currentTime() {
		GregorianCalendar calenda = new GregorianCalendar();
		return calenda.getTime();
	}

	/**
	 * 获得当前日期的时间格式
	 * 
	 * @return
	 */
	public static String currentDate() {
		GregorianCalendar calenda = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calenda.getTime());
	}

	/**
	 * 获取昨天的日期
	 * @return
	 */
	public static String yesterDate(){
		DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		String etime = dformat.format(c.getTime());
		return etime;
	}
	
	/**
	 * 获取明天的日期
	 * @return
	 */
	public static String tomorrowDate(){
		DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 1);
		String etime = dformat.format(c.getTime());
		return etime;
	}
	/**
	 * 获取昨天的日期
	 * 
	 * @return
	 */
	public static String yesterDateNext(){
		DateFormat dformat = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		String etime = dformat.format(c.getTime());
		return etime;
	}
	/**
	 * 获取今天的日期
	 * 
	 * @return
	 */
	public static String currentDateNext() {
		DateFormat dformat = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 0);
		String etime = dformat.format(c.getTime());
		return etime;
	}
	
	public static void main(String[] args){
		System.out.println(new Date().getTime()/1000);
		System.out.println(currentDate());
	}
}