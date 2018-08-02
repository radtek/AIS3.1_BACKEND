/*
 * DictItem.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2016-05-04 Created
 */
package com.digihealth.anesthesia.sysMng.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="字典对象信息")
public class BasDictItem {
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
     * 码值
     */
	@ApiModelProperty(value="码值")
    private String codeValue;

    /**
     * 码值名称
     */
	@ApiModelProperty(value="码值名称")
    private String codeName;

    /**
     * 排序
     */
	@ApiModelProperty(value="排序")
    private Integer order;

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

	public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName == null ? null : codeName.trim();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

	public String getBeid()
	{
		return beid;
	}

	public void setBeid(String beid)
	{
		this.beid = beid;
	}
    
}