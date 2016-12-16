package com.javabase.entity;

import java.util.Date;
import java.io.*;

/**
 * 实体bean
 * 
 * @author bruce
 *
 */

@SuppressWarnings("serial")
public class SysServerInfos implements Serializable {

	private final long serialVersionUID = 1L;	private Integer infoId;//   系统id	private String cpuUsage;//   cpu内存	private String setCpuUsage;//   设置cpu内存	private String jvmUsage;//   jvm内存	private String setJvmUsage;//   设置jvm内存	private String ramUsage;//   内存使用	private String setRamUsage;//   设置内存使用	private String managerEmail;//   管理的email	private Date operatorTime;//   操作时间	private String mark;//   系统备注		public Integer getInfoId() {	    return this.infoId;	}	public void setInfoId(Integer infoId) {	    this.infoId=infoId;	}		public String getCpuUsage() {	    return this.cpuUsage;	}	public void setCpuUsage(String cpuUsage) {	    this.cpuUsage=cpuUsage;	}		public String getSetCpuUsage() {	    return this.setCpuUsage;	}	public void setSetCpuUsage(String setCpuUsage) {	    this.setCpuUsage=setCpuUsage;	}		public String getJvmUsage() {	    return this.jvmUsage;	}	public void setJvmUsage(String jvmUsage) {	    this.jvmUsage=jvmUsage;	}		public String getSetJvmUsage() {	    return this.setJvmUsage;	}	public void setSetJvmUsage(String setJvmUsage) {	    this.setJvmUsage=setJvmUsage;	}		public String getRamUsage() {	    return this.ramUsage;	}	public void setRamUsage(String ramUsage) {	    this.ramUsage=ramUsage;	}		public String getSetRamUsage() {	    return this.setRamUsage;	}	public void setSetRamUsage(String setRamUsage) {	    this.setRamUsage=setRamUsage;	}		public String getManagerEmail() {	    return this.managerEmail;	}	public void setManagerEmail(String managerEmail) {	    this.managerEmail=managerEmail;	}		public Date getOperatorTime() {	    return this.operatorTime;	}	public void setOperatorTime(Date operatorTime) {	    this.operatorTime=operatorTime;	}		public String getMark() {	    return this.mark;	}	public void setMark(String mark) {	    this.mark=mark;	}
	
}

