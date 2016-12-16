package com.javabase.http.test;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * soap 请求.
 * 
 * 测试需要绑定host,否则不能运行
 * 
 * 测试：103.29.134.115    usercenter.zhongsou.com
 * 
 * 正式：103.7.220.85    usercenter.zhongsou.com
 * 
 * @author admin
 *
 */
public class SOAPClient {
	
	static String AUTH_USER_NAME ="ADMINapi13122";
	static String AUTH_PWD ="540A50B2638992EB668762B0A5BFF84F";
	static String AUTH_SIGN ="40c1802fa2226494a3d41ee034c6e449";
	static String request_url = "http://usercenter.zhongsou.com/usercenterapi";

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getSouyueUserInfo("fzbtest1000", null));;
		System.out.println(CheckSouyueUserPropertyExists("user_name", "fzbtest1000")); 
		System.out.println(CheckSouyueUserPropertyExists("email", "fzbtest1000@common.zhongsou.com"));; 
		System.out.println(CheckSouyueUserPropertyExists("phone", "18810056855"));;   
	}

    /**
     * 检查登陆.
     * 
     * @param user_name
     * @param user_pwd
     * @return
     */
    public static String checkSouyueLogin(String user_name,String user_pwd){
		 
    	StringBuilder sb=new StringBuilder();
	        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://usercenter.zhongsou.com/soap\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">");
	        sb.append("<SOAP-ENV:Header>");
		        sb.append("<ns1:Auth SOAP-ENV:actor=\"http://schemas.xmlsoap.org/soap/actor/next\">");
			        sb.append("<item>");
			        sb.append("<key>user_name</key>");
			        sb.append("<value>"+AUTH_USER_NAME+"</value>");
			        sb.append("</item>");
			        sb.append("<item>");
			        sb.append("<key>pwd</key>");
			        sb.append("<value>"+AUTH_PWD+"</value>");
			        sb.append("</item>");
			        sb.append("<item>");
			        sb.append("<key>sign</key>");
			        sb.append("<value>"+AUTH_SIGN+"</value>");
			        sb.append("</item>");
			    sb.append("</ns1:Auth>");
		     sb.append("</SOAP-ENV:Header>");
		     sb.append("<SOAP-ENV:Body>");
				sb.append("<ns1:Login>");
					sb.append("<param0 xsi:type=\"ns2:Map\">");
				    sb.append("<item>");
				    sb.append("<key xsi:type=\"xsd:string\">user_name</key>");
				    sb.append("<value xsi:type=\"xsd:string\">"+user_name+"</value>");
				    sb.append("</item>");
				    sb.append("<item>");
				    sb.append("<key xsi:type=\"xsd:string\">user_pwd</key>");
				    sb.append("<value xsi:type=\"xsd:string\">"+user_pwd+"</value>");
				    sb.append("</item>");
				    sb.append("</param0>");
				sb.append("</ns1:Login>");
		     sb.append("</SOAP-ENV:Body>");
		sb.append("</SOAP-ENV:Envelope>");
		
		String requestBody = sb.toString();
		try {
			return SendPostRequest(requestBody, request_url);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 获得搜悦用户信息
     * 
     * @param login_name
     * 
     * @param type：
     * 
     * phone 是通过手机号;
     * email 是通过邮箱;
     * user_id是通过uid获取默认为用户名
     * 
     * @return
     */
    public static String getSouyueUserInfo(String login_name,String type) {

    	StringBuilder sb=new StringBuilder();
	        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://usercenter.zhongsou.com/soap\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">");
	        sb.append("<SOAP-ENV:Header>");
		        sb.append("<ns1:Auth SOAP-ENV:actor=\"http://schemas.xmlsoap.org/soap/actor/next\">");
			        sb.append("<item>");
			        sb.append("<key>user_name</key>");
			        sb.append("<value>"+AUTH_USER_NAME+"</value>");
			        sb.append("</item>");
			        sb.append("<item>");
			        sb.append("<key>pwd</key>");
			        sb.append("<value>"+AUTH_PWD+"</value>");
			        sb.append("</item>");
			        sb.append("<item>");
			        sb.append("<key>sign</key>");
			        sb.append("<value>"+AUTH_SIGN+"</value>");
			        sb.append("</item>");
			    sb.append("</ns1:Auth>");
		     sb.append("</SOAP-ENV:Header>");
		     sb.append("<SOAP-ENV:Body>");
				sb.append("<ns1:getUserInfo>");
					sb.append("<param0 xsi:type=\"ns2:Map\">");
				    sb.append("<item>");
				    sb.append("<key xsi:type=\"xsd:string\">user_name</key>");
				    sb.append("<value xsi:type=\"xsd:string\">"+login_name+"</value>");
				    sb.append("</item>");
				    if (type==null) {
				    	sb.append("<item>");
					    sb.append("<key xsi:type=\"xsd:string\">type</key>");
					    sb.append("<value xsi:type=\"xsd:string\">pt</value>");
					    sb.append("</item>");
					}else {
						sb.append("<item>");
					    sb.append("<key xsi:type=\"xsd:string\">type</key>");
					    sb.append("<value xsi:type=\"xsd:string\">"+type+"</value>");
					    sb.append("</item>");
					}
				    sb.append("</param0>");
				sb.append("</ns1:getUserInfo>");
		     sb.append("</SOAP-ENV:Body>");
		sb.append("</SOAP-ENV:Envelope>");
		
		String requestBody = sb.toString();
		try {
			return SendPostRequest(requestBody, request_url);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 检查搜悦用户属性是否存在
     * 
     * @param propKey ... email,phone,user_name 这三个
     * @param propValue ...值
     * @return false:可用;true：不可用.
     */
    public static boolean CheckSouyueUserPropertyExists(String propKey, String propValue) {

    	StringBuilder sb=new StringBuilder();
	        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://usercenter.zhongsou.com/soap\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">");
	        sb.append("<SOAP-ENV:Header>");
		        sb.append("<ns1:Auth SOAP-ENV:actor=\"http://schemas.xmlsoap.org/soap/actor/next\">");
			        sb.append("<item>");
			        sb.append("<key>user_name</key>");
			        sb.append("<value>"+AUTH_USER_NAME+"</value>");
			        sb.append("</item>");
			        sb.append("<item>");
			        sb.append("<key>pwd</key>");
			        sb.append("<value>"+AUTH_PWD+"</value>");
			        sb.append("</item>");
			        sb.append("<item>");
			        sb.append("<key>sign</key>");
			        sb.append("<value>"+AUTH_SIGN+"</value>");
			        sb.append("</item>");
			    sb.append("</ns1:Auth>");
		     sb.append("</SOAP-ENV:Header>");
		     sb.append("<SOAP-ENV:Body>");
		     	if (propKey.equalsIgnoreCase("email")) {
		     		sb.append("<ns1:CheckEmailExists>");
		     			sb.append("<value>"+propValue+"</value>");
				    sb.append("</ns1:CheckEmailExists>");
				}else if (propKey.equalsIgnoreCase("phone")) {
					sb.append("<ns1:CheckPhoneExists>");
				    	sb.append("<value>"+propValue+"</value>");
				    sb.append("</ns1:CheckPhoneExists>");
				}else {
					sb.append("<ns1:CheckUserNameExists>");
						sb.append("<value>"+propValue+"</value>");
				    sb.append("</ns1:CheckUserNameExists>");
				}
		     sb.append("</SOAP-ENV:Body>");
		 sb.append("</SOAP-ENV:Envelope>");
		
		String requestBody = sb.toString();
		System.out.println(requestBody);
		
		boolean result = false;
		try {
			String jsonStr  = SendPostRequest(requestBody, request_url);
			System.out.println("jsonStr = "+jsonStr);
			result =  checkIfExists(jsonStr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    
    
    /**
     * soap 请求.
     * 
     * @param requestBody
     * @param requestUrl
     * @return
     */
    public static final CloseableHttpClient client = HttpClients.createDefault();
    private static String SendPostRequest(String requestBody,String requestUrl) {
        HttpPost post = new HttpPost(requestUrl);
        try {
            post.setEntity(new StringEntity(requestBody));
        } catch (UnsupportedEncodingException ignored) {
        }
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = client.execute(post);
            String data = IOUtils.toString(response.getEntity().getContent());
            int begin = data.indexOf("{");
            int end = data.lastIndexOf("}");
            result = data.substring(begin, end+1);
        } catch (IOException ignored) {
        	ignored.printStackTrace();
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }
    
    /**
     * 检测是不是存在的.
     * 
     * @param jsonStr
     * @return
     */
    public static boolean checkIfExists(String jsonStr) {
    	try {
    		JSONObject jsonObject = new JSONObject(jsonStr);
    		String status = jsonObject.get("status").toString();
    		if (status.equalsIgnoreCase("0001")) {
				return false;
			}else {
				return true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
