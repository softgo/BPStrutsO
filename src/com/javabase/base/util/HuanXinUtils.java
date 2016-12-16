package com.javabase.base.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

/**
 * 
 * 环信信息的获取
 * 
 * @author bruce
 *
 */
public class HuanXinUtils {

	static  String token_value = null; 
	
	/**
	 * 环信通信信令.
	 * @param requestTokenUrl : token访问url
	 * @param requestParams:参数
	 * @return
	 */
	public static String getHuanXinToken(String requestTokenUrl,String requestParams){
		String result = HttpsClientUtils.httpsPostRequest(requestTokenUrl, requestParams);
		return result;
	}
	
	/**
	 * 
	 * @param requestUserUrl:用户访问的url
	 * @param HuanXinToken：环信的token
	 * @param userName:用户名
	 * @param passWord：密码
	 */
	public static String getHuanXinInfo(String requestUserUrl,String HuanXinToken,String userName,String passWord){
		HashMap<String, String> params =new HashMap<String, String>();
		params.put("username", userName);
		params.put("password", passWord);
		HashMap<String, String> header =new HashMap<String, String>();
		String contentType ="application/json";
		header.put("Authorization", "Bearer "+HuanXinToken+"");
		header.put("Content-Type", contentType);
		String charsetName = "UTF-8";
		String postBody = "{\"username\":\""+userName+"\",\"password\":\""+passWord+"\"}";
		String result = HttpsClientUtils.getContentPost(requestUserUrl, params, header, charsetName, postBody, contentType);
		System.out.println(result);
		return result;
	}
	
	/**
	 * test
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		String requestTokenUrl = "https://a1.easemob.com/zhongsou/yidongyunjiezszb/token";
		String requestParams = "{\"grant_type\":\"client_credentials\",\"client_id\":\"YXA6GzdsEDtpEeWbCkH7ZSlvbA\",\"client_secret\":\"YXA6afORfPpbcUgPybbHNXLoEm2vSa0\"}";
		
		if (token_value==null) {
			String huanXinToken = getHuanXinToken(requestTokenUrl, requestParams);
			huanXinToken = JSONUtil.getJsonData(huanXinToken, "access_token");
			token_value = huanXinToken; 
		}
		
		for (int i = 8; i <= 10; i++) {
			System.out.println(token_value);
			System.out.println("==========================================================");
			String requestUserUrl = "https://a1.easemob.com/zhongsou/yidongyunjiezszb/users";
			String password = System.currentTimeMillis()+"";
			System.out.println("-------------------------------------"+password);
			String result = getHuanXinInfo(requestUserUrl,token_value,"test"+i,password);
		}
		
		//StringToJson("123", "456");
		
	}
	
	public static String StringToJson(String username,String password) throws JSONException{
		Map<String, String> map = new HashMap<String, String>();		
		map.put("username", username);
		map.put("password", password);
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		result.put("result", map);
		return JSONUtil.toJSONString(result);
	}
	
}
