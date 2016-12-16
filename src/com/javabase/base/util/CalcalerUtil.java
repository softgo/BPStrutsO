package com.javabase.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * Calcaler 的帮助类.
 * 
 * @author admin
 *
 */
public class CalcalerUtil {

	/**
	 *  test
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getCurrentTimeMillis());
		System.out.println(getCurrentTimeMillis()+ " 0  hours now = " + format.format(calendar.getTime()));
		
		calendar.setTimeInMillis(getNHoursLaterTimeMillis(12));
		System.out.println(getNHoursLaterTimeMillis(12)+ " 12 hours later = " + format.format(calendar.getTime()));
		
		calendar.setTimeInMillis(getNHoursLaterTimeMillis(24));
		System.out.println(getNHoursLaterTimeMillis(24)+ " 24 hours later = " + format.format(calendar.getTime()));
	}
	
	/**
	 * 获得时间的毫秒数据.
	 * @param timeStr:时间串.
	 * @return
	 */
	public static String getCurrentTimeByStr(String timeStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssS");
		try {
			Date date = format.parse(timeStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return format.format(calendar.getTime());
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得当前时间串.
	 * @return
	 */
	public static String getCurrentTimeStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		return format.format(calendar.getTime());
	}
	
	/**
	 * 获得当前时间的毫秒数据
	 * @return
	 */
	public static long getCurrentTimeMillis(){
		Calendar calcaler = Calendar.getInstance();
		return calcaler.getTimeInMillis();
	}

	/**
	 * 获得  hours 小时后的时间
	 * @param hours
	 * @return
	 */
	public static long getNHoursLaterTimeMillis(int hours){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTimeInMillis();
	}


	/**
	 * 获取还有多少时间可以提取.
	 * 
	 * @param planTime
	 * @return
	 */
	public static int getRestHours(String planTime) {
		if (StringUtils.isEmpty(planTime)) {
			return 1;
		}else{
			long plantime = Long.parseLong(planTime);
			Calendar calendar = Calendar.getInstance();
			long nowtime = calendar.getTimeInMillis();
			//毫秒数
			long resttime = plantime - nowtime;
			int hours = (int) (resttime/(1000*60*60));
			return hours+1;
		}
	}
	
	/**
	 * 是否大于给定的时间.
	 * 
	 * @param planTime
	 * @param nowTime
	 * @return inputTime,nowTime 有一个为null或""返回 false;
	 * planTime < nowTime 返回  true;
	 * planTime > nowTime 返回  false;
	 */
	public static boolean ifCanWithDraw(String planTime, String nowTime) {
		if (StringUtils.isEmpty(planTime) || StringUtils.isEmpty(nowTime)) {
			return false;
		}else {
			long input = Long.parseLong(planTime);
			long now = Long.parseLong(nowTime);
			if (input < now) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	/**
	 * 是否在给定的时间内.
	 * 
	 * @param startTime
	 * @param endTime
	 * 当前时间在给定的时间内, 返回  true;
	 * 当前时间不在给定的时间内, 返回  false;
	 */
	public static boolean ifInGiveTime(Date startTime, Date endTime) {
		try {
			Date date = new Date();
			if (date.getTime() > startTime.getTime() && date.getTime() < endTime.getTime()) {
				return true;
			}else {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}
	
}
