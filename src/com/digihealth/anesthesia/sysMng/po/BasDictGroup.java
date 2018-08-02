/*
 * DictGroup.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-22 Created
 */
package com.digihealth.anesthesia.sysMng.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="字典组对象信息")
public class BasDictGroup {
    /**
     * 码表ID
     */
	@ApiModelProperty(value="码表ID")
    private Integer id;

    /**
     * 组
     */
	@ApiModelProperty(value="组")
    private String groupId;

    /**
     * 组对应名称
     */
	@ApiModelProperty(value="码值")
    private String groupName;

    /**
     * 是否有效
     */
	@ApiModelProperty(value="是否有效")
    private Boolean enable;

    /**
     * 局点id
     */
	@ApiModelProperty(value="局点id")
    private String beid;
	
	/**
     * 局点名字
     */
    private String beName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid == null ? null : beid.trim();
    }

	public String getBeName()
	{
		return beName;
	}

	public void setBeName(String beName)
	{
		this.beName = beName;
	}
    
}