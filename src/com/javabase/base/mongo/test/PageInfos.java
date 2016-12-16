package com.javabase.base.mongo.test;

import java.util.List;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.javabase.base.mongo.test.City;
import com.javabase.base.mongo.test.ICityDao;

public class PageInfos {

	public static void main(String[] args) {
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{
				"classpath:configXml/spring-security.xml",
	            "classpath:configXml/spring-application.xml",
	            "classpath:configXml/spring-jobtask.xml",
	            "classpath:configXml/spring-servlet.xml"
		});
		
		ICityDao service = (ICityDao) ctx.getBean("cityDao");
		List<City> cities =	service.query(null, 10, 10);
		int index = 0;
		for (City city : cities) {
			System.out.println("id = "+city.getId()+ ",text= "+ city.getText()+",code = "+city.getCode());
			index++;
		}
		System.out.println(index);

		/*
		SKYDAO<City, ObjectId> dao =  new SKYDAO<City, ObjectId>();
		Map<String, Object> params = new HashMap<String, Object>();
		List<City> cities = dao.query(params, 10, 20);
		int index = 0;
		for (City city : cities) {
			System.out.println(city.getText());
			index++;
		}
		System.out.println(index);
		*/
	}	
}
