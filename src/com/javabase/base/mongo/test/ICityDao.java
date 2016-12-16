package com.javabase.base.mongo.test;

import java.util.List;
import java.util.Map;

public interface ICityDao {

    /**
     * 查询城市
     * @param params
     * @param offset
     * @param limit
     * @return
     */
    List<City> query(Map<String, ?> params, int offset, int limit);
    
}
