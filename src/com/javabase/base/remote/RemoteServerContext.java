package com.javabase.base.remote;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 远程访问回来的内容.
 * 
 * @author bruce
 *
 */
public class RemoteServerContext {

    private static ThreadLocal<RemoteServerContext> contexts = new ThreadLocal<RemoteServerContext>();
    
    private Map<String, Object> values = new HashMap<String, Object>();

    private RemoteServerContext() {
    }

    public static RemoteServerContext getContext() {
    	RemoteServerContext ctx = contexts.get();
        if (ctx == null) {
            ctx = new RemoteServerContext();
            contexts.set(ctx);
        }
        return ctx;
    }

    public void addValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public JSONObject getParams() throws Exception {
        JSONObject json = JSON.parseObject(String.valueOf(getValue(IRemoteService.PARAMETER_P)));
        if (json == null) {
            json = new JSONObject();
        }
        this.clear();
        return json;
    }

    public void clear() {
    	contexts.set(null);
        contexts.remove();
    }


}
