package com.javabase.base.remote;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

@Transactional
@Service("RemoteTestService")
public class RemoteTestService implements IRemoteService{
	
	public synchronized Object test(){
		JSONObject json = null;
        try {
            json = RemoteServerContext.getContext().getParams();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name = json.getString("name"); //IOS,Android客户端送来的.
        String pass = json.getString("pass"); //系统时间的value.
        System.out.println(name + " " + pass);
        System.out.println("调用完成了...");
        
        return "{\"name\":\"123\"}";
	}
}
