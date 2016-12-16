package com.javabase.base.mongo.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
@Service("iCityService")
 */
public class CityServiceImpl implements ICityService {

    @Autowired
    private ICityDao iCityDao;

    public List<City> query() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("order", "code");
        return iCityDao.query(params, -1, -1);
    }

}
