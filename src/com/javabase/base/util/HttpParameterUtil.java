package com.javabase.base.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * http 传输的json的接收解析工具类.
 * 
 * @author bruce
 *
 */
public class HttpParameterUtil {

    public static int getParameterInt(HttpServletRequest request, String parameter) {
        try {
            return Integer.valueOf(request.getParameter(parameter));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long getParameterLong(HttpServletRequest request, String parameter) {
        try {
            return Long.valueOf(request.getParameter(parameter));
        } catch (NumberFormatException e) {
            return 0l;
        }
    }
    
    public static double getParameterDouble(HttpServletRequest request, String parameter) {
        try {
            return Double.valueOf(request.getParameter(parameter));
        } catch (Exception e) {
            return 0d;
        }
    }

	public static String[] getParameterArrays(HttpServletRequest request, String parameter) {
		try {
			String[] array = request.getParameterValues(parameter);
			if(array == null){
				array = new String[]{};
			}
			return array;
		} catch (Exception e) {
			return new String[]{};
		}
	}

    public static Date getParameterDate(HttpServletRequest request, String parameter) {
        return parseDate(request.getParameter(parameter));
    }

    public static String getParameter(HttpServletRequest request, String parameter) {
        String s = request.getParameter(parameter);
        return s == null ? "" : s;
    }
    
    /**
     * 时间转换.
     * 
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        try {
            String[] parsePatterns = {
                    "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
                    "yyyy:MM:dd HH:mm:ss", "yyyy-MM-dd",
                    "dd.MM.yy HH:mm", "yyyyMMdd HHmmss",
                    "yyyyMMdd HHmm", "MM/dd/yy hh:mm a",
                    "HH:mm:ss dd.MM.yyyy", "yyyy:MM:dd",
                    "yyyy:MM:dd HH:mm", "dd.MM.yy", "yyyyMMdd",
                    "EEE, dd MMM yyyy HH:mm:ss", "MM/dd/yy",
                    "yyyy:MM:dd HH:mm:sss",
                    "yyyy/MM/dd"
            };
            return DateUtils.parseDate(dateStr, parsePatterns);
        } catch (Exception e) {
            return null;
        }
    }

}
