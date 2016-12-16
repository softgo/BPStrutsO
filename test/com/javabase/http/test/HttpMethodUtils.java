package com.javabase.http.test;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


/**
 * 
 * nono http请求结果.
 * 
 * @author admin
 *
 */
public class HttpMethodUtils {
	
	/**
	 * http doPost 请求发送方式.
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private static JSONObject doPost(String url) throws Exception{
		HttpPost httpRequest = new HttpPost();
		URI uri = new URI(url); 
		httpRequest.setURI(uri);
		httpRequest.setHeader("Content-Type", "application/json");
		HttpClient client = HttpClients.createDefault();
		HttpResponse response = client.execute(httpRequest);
		String responseStr =  EntityUtils.toString(response.getEntity());
		JSONObject json = JSONObject.parseObject(responseStr);
		return json;
	}
	
	/**
	 * 查询账户余额接口.
	 * @param requestURL
	 * @param merchantUserId
	 * @param merchantType
	 * @return
	 * @throws Exception 
	 */
	public static String getBalanceOfAccount(String requestURL,String merchantUserId,String merchantType) throws Exception {
		requestURL = requestURL+"merchantUserId="+merchantUserId+"&merchantType="+merchantType;
		JSONObject jsonObject = doPost(new String((requestURL).getBytes("UTF8"),"UTF8"));
		if (jsonObject.getIntValue("code")!=0) {
			return null;
		}else {
			return jsonObject.toJSONString();
		}
	}
	
	public static Long getUserIdByMerchantUserIdAndMerchantType(Long merchantUserId, String merchantType) throws Exception {
		//获取userId
		String url = "http://172.16.0.94:8080/nono-user/internal/accountRelationQuery?";
		url = url+"merchantUserId="+merchantUserId+"&merchantType="+merchantType;
		JSONObject json = null;
		json = doPost(new String((url).getBytes("UTF8"),"UTF8"));
		//失败   0：表示成功
		if(json.getIntValue("code")!=0){
			return null;
		}
		Long userId = json.getLong("result");
		return userId;
	}
	
	
	public static Long getUserIdByMerchantUserName(String merchantUserName) throws Exception {
		//获取userId
		String url = "http://172.16.0.94:8080/nono-user/internal/userInfoQuery/queryByUserName?";
		url = url+"userName="+merchantUserName;
		JSONObject json = null;
		json = doPost(new String((url).getBytes("UTF8"),"UTF8"));
		//失败   0：表示成功
		if(json.getIntValue("code")!=0){
			return null;
		}
		Long userId = json.getLong("result");
		return userId;
	}
	
	
	public static void main(String[] args) throws Exception {
		//String str = "http://172.16.0.94:8080/nono-user/internal/accountRelationQuery?"+"merchantUserId="+1000033382+"&merchantType="+18;
		//Long id = HttpMethodUtils.doPost(new String((str).getBytes("UTF8"),"UTF8")).getLong("result");
		String requestURL="http://192.168.1.78:8088/nono-web/internal/financeAccount/queryBalanceOfAccount";
		String merchantUserId = "20023711";
		String merchantType = "18";
		String result = getBalanceOfAccount(requestURL, merchantUserId, merchantType);
		
		System.out.println(result);
	}
}
