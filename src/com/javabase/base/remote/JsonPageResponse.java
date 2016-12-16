package com.javabase.base.remote;

import java.util.ArrayList;

/**
 * 
 * 返回 Page.
 * 
 * @author bruce.
 */
public class JsonPageResponse {
    
	private long total;
    
    private Object rows;

    public JsonPageResponse(long total, Object rows) {
        this.total = total;
        if (rows == null) {
            rows = new ArrayList<String>();
        }
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }
}
