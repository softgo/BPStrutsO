package com.javabase.entity;

import java.util.*;
import java.io.*;

/**
 * 实体bean
 * 
 * @author bruce
 *
 */

@SuppressWarnings("serial")
public class SysSources implements Serializable {

	private final long serialVersionUID = 1L;	private Integer sourceId;//   资源id	private String sourceName;//   资源名称	private Integer parentId;//   资源父级Id
	private String parentName;//  资源父级名称	private String sourceKey;//   资源key,是唯一的访问Key	private String sourceType;//   资源类型,0：菜单,1:按钮,2:链接	private String sourceUrl;//   资源访问的url	private Integer sourceLevel;//   资源等级,从 1 开始...	private String description;//   资源介绍	private Integer sourceStatus;//   资源状态;1:启用,0:弃用
	private Integer isIframe;//   是否是iframe;1:是,0:否
	
	private Set<SysRoles> roles = new HashSet<SysRoles>(0);
	private Set<SysSources> childs = new HashSet<SysSources>(0);	
	public SysSources() {
		super();
	}
	
	public SysSources(Integer sourceId, String sourceName, Integer parentId,
			String sourceKey, String sourceType,String sourceUrl, Integer sourceLevel,String description) {
		super();
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.parentId = parentId;
		this.sourceKey = sourceKey;
		this.sourceType = sourceType;
		this.sourceUrl = sourceUrl;
		this.sourceLevel = sourceLevel;
		this.description = description;
	}

	public SysSources(Integer sourceId, String sourceName, Integer parentId,
			String parentName, String sourceKey, String sourceType,
			String sourceUrl, Integer sourceLevel, String description,
			Integer sourceStatus, Integer isIframe) {
		super();
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.parentId = parentId;
		this.parentName = parentName;
		this.sourceKey = sourceKey;
		this.sourceType = sourceType;
		this.sourceUrl = sourceUrl;
		this.sourceLevel = sourceLevel;
		this.description = description;
		this.sourceStatus = sourceStatus;
		this.isIframe = isIframe;
	}

	public SysSources(Integer sourceId, String sourceName, Integer parentId,
			String parentName, String sourceKey, String sourceType,
			String sourceUrl, Integer sourceLevel, String description,
			Integer sourceStatus, Integer isIframe, Set<SysRoles> roles,
			Set<SysSources> childs) {
		super();
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.parentId = parentId;
		this.parentName = parentName;
		this.sourceKey = sourceKey;
		this.sourceType = sourceType;
		this.sourceUrl = sourceUrl;
		this.sourceLevel = sourceLevel;
		this.description = description;
		this.sourceStatus = sourceStatus;
		this.isIframe = isIframe;
		this.roles = roles;
		this.childs = childs;
	}
	
	public Integer getSourceId() {	    return this.sourceId;	}	public void setSourceId(Integer sourceId) {	    this.sourceId=sourceId;	}		public String getSourceName() {	    return this.sourceName;	}	public void setSourceName(String sourceName) {	    this.sourceName=sourceName;	}		public Integer getParentId() {	    return this.parentId;	}	public void setParentId(Integer parentId) {	    this.parentId=parentId;	}		public String getSourceKey() {	    return this.sourceKey;	}	public void setSourceKey(String sourceKey) {	    this.sourceKey=sourceKey;	}		public String getSourceType() {	    return this.sourceType;	}	public void setSourceType(String sourceType) {	    this.sourceType=sourceType;	}		public String getSourceUrl() {	    return this.sourceUrl;	}	public void setSourceUrl(String sourceUrl) {	    this.sourceUrl=sourceUrl;	}		public Integer getSourceLevel() {	    return this.sourceLevel;	}	public void setSourceLevel(Integer sourceLevel) {	    this.sourceLevel=sourceLevel;	}		public String getDescription() {	    return this.description;	}	public void setDescription(String description) {	    this.description=description;	}		public Integer getSourceStatus() {	    return this.sourceStatus;	}	public void setSourceStatus(Integer sourceStatus) {	    this.sourceStatus=sourceStatus;	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public Integer getIsIframe() {
		return isIframe;
	}
	public void setIsIframe(Integer isIframe) {
		this.isIframe = isIframe;
	}
}

