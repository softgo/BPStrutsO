package com.javabase.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * 执行一个进程
 * 
 * @author bruce.
 *
 */
public class CmdExecutor extends Thread {
	
	/**
	 * 执行一个进程命令 
	 * 
	 * @param cmd
	 *            要执行的命令
	 * @return 外部命令执行的返回值
	 * @throws Exception
	 *             错误信息
	 */
	public synchronized static String exeCmd(String cmd) throws Exception {
		String result = "";
		Process proc = null;
		InputStream stderr = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			Runtime rt = Runtime.getRuntime();
			proc = rt.exec(cmd);
			stderr = proc.getInputStream();
			isr = new InputStreamReader(stderr);
			br = new BufferedReader(isr);
			String line = null;
			StringBuffer strbuff = new StringBuffer();
			while ((line = br.readLine()) != null) {
				strbuff.append(line).append("\n");
			}
			result = strbuff.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
			if (isr != null)
				try {
					isr.close();
				} catch (IOException e) {
				}
			if (stderr != null)
				try {
					stderr.close();
				} catch (IOException e) {
				}
			if (proc != null)
				proc.destroy();
		}
		return result;
	}

	/**
	 * 打印100个数据，每隔一秒一次，每10个输出。
	 */
	public void run() {
		for (int i = 0; i < 100; i++) {
			if ((i) % 10 == 0) {
				System.out.println("=======" + i);
			}
			System.out.println(i);
			try {
				Thread.sleep(1);
				System.out.print("睡眠时间是1秒\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getEncoding(String oldStr){
		String newStr=null; 
		if (oldStr==null) {
			return null;
		}else{
			try {
				newStr=new String(oldStr.getBytes("ISO-8859-1"),"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return newStr;
	}
	
	/**
	 * test it .
	 * @param args
	 */
	public static void main(String[] args) {
		new CmdExecutor().start();
	}
	
}
