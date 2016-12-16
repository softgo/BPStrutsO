package com.javabase.http.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.javabase.base.util.JSONUtil;

public class SouyueUtils {
	
	private static final String URL = "http://api2.souyue.mobi/d3api2/pc/user/user.info.groovy?userName=";
	private static boolean proxySet = false;
	private static String proxyHost = "127.0.0.1";
	private static int proxyPort = 101010;

	public static void main(String[] args) {
		String params = "suping123";
		String result = httpRequest(URL + params);
		sendGetRequest(URL+params, params);
		sendPostRequest(URL+params, false, params);		
		System.out.println(getSouyueUserPros(result, "nickname"));
	}
	
	/**
	 * 通过给定的json和key返回key对应的value.
	 * 
	 * @param jsonStr
	 * 
	 * @param key
	 * @return
	 */
	public static String getSouyueUserPros(String jsonStr, String key) {
		String value = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			JSONObject bodyObject = jsonObject.getJSONObject("body");
			value = bodyObject.get(key).toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * http请求获取资源
	 * 
	 * @param url
	 * @return
	 */
	public static String httpRequest(String url) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL conUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) conUrl.openConnection();
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			// 连接.
			connection.connect();
			InputStream inStream = connection.getInputStream();
			InputStreamReader inReader = new InputStreamReader(inStream,"UTF-8");
			BufferedReader reader = new BufferedReader(inReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		System.out.println(buffer.toString());
		return buffer.toString();
	}

	/**
	 * 向服务器发送请求获取信息
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendGetRequest(String url, String... params) {
		StringBuffer buffer = new StringBuffer("");
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				buffer.append(line + "\n");
			}
		}
		catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}

		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(buffer.toString());
		return buffer.toString();
	}

	/**
	 * 向服务器发送请求获取信息
	 * 
	 * @param url
	 * @param isProxy
	 * @param params
	 * @return
	 */
	public static String sendPostRequest(String url, boolean isProxy,
			String... params) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		StringBuffer buffer = new StringBuffer("");
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = null;
			if (isProxy) {
				@SuppressWarnings("static-access")
				Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP,
						new InetSocketAddress(proxyHost, proxyPort));
				conn = (HttpURLConnection) realUrl.openConnection(proxy);
			}
			else {
				conn = (HttpURLConnection) realUrl.openConnection();
			}

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();

			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

			// 发送请求参数
			out.write(params.toString());
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				buffer.append(line + "\n");
			}
		}
		catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}

		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(buffer.toString());
		return buffer.toString();
	}

}
