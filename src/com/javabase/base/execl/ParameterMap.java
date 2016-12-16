package com.javabase.base.execl;

public class ParameterMap {
	/*属性名称*/
	private String propertyname;
	/*属性类别*/
	private String propertype;      //string  time ip
	
	public ParameterMap(String propertyname, String propertype) {
		super();
		this.propertyname = propertyname;
		this.propertype = propertype;
	}
	public String getPropertyname() {
		return propertyname;
	}
	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}
	public String getPropertype() {
		return propertype;
	}
	public void setPropertype(String propertype) {
		this.propertype = propertype;
	}
}
