package com.javabase.base.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检测   IP 是不是有效的
 * @author bruce.
 *
 */
public class CheckIp {
	
	/**
	 * 检测ip的值
	 * @param ips
	 * @param ip
	 * @return
	 */
	public boolean checkIP(List<String> ips, String ip) {
		// 判断ip是不是合法的IP地址
		if (!isIp(ip))
			return false;
		
		// 如果配置*、0.0.0.0和127.0.0.1返回true
		String sip = "0.0.0.0";
		if ((ips.contains("x")) || (ips.contains("*")) || (ips.contains(sip))
				|| ("127.0.0.1".equals(ip)))
		{
			return true;
		}
		if (ips.contains(ip))
		{
			return true;
		}
		String[] str = ip.split("\\.");
		for (String string : str) {
			sip = sip.replaceFirst("0", string);
			if (ips.contains(sip))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 使用正则表达式验证IP地址的合法性
	 * 
	 * @param ipAddress
	 * @return
	 */
	private boolean isIp(String ipAddress) {
		String test = "([1-9]|[1-9]\\d|1\\d{2}|2[0-1]\\d|22[0-3])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pattern = Pattern.compile(test);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}
	
	
	/**
	 * 是不是内部ip
	 * @param ipAddress
	 * @return
	 */
	public static boolean isInnerIP(String ipAddress) {
		
		boolean isInnerIp = false;
		
		long ipNum = getIpNum(ipAddress);
		/**
		 *检测  IP 是否有效。 
		 */
		long aBegin = getIpNum("10.0.0.0");
		long aEnd = getIpNum("10.255.255.255");
		long bBegin = getIpNum("172.16.0.0");
		long bEnd = getIpNum("172.31.255.255");
		long cBegin = getIpNum("192.168.0.0");
		long cEnd = getIpNum("192.168.255.255");
		isInnerIp = isInner(ipNum, aBegin, aEnd)
				|| isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd)
				|| ipAddress.equals("127.0.0.1");
		return isInnerIp;
	}

	/**
	 * 获得ip的值
	 * @param ipAddress
	 * @return
	 */
	public static long getIpNum(String ipAddress) {
		String[] ip = ipAddress.split("\\.");
		long a = Integer.parseInt(ip[0]);
		long b = Integer.parseInt(ip[1]);
		long c = Integer.parseInt(ip[2]);
		long d = Integer.parseInt(ip[3]);

		long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		return ipNum;
	}

	public static boolean isInner(long userIp, long begin, long end) {
		return (userIp >= begin) && (userIp <= end);
	}

}
