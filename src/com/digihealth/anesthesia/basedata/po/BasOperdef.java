/*
 * BasOperdef.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-31 Created
 */
package com.digihealth.anesthesia.basedata.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="手术名称对象")
public class BasOperdef {
    
    @ApiModelProperty(value="主键id")
    private String operdefId;

    /**
     * 代码
     */
    @ApiModelProperty(value="代码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 拼音
     */
    @ApiModelProperty(value="拼音")
    private String pinYin;

    /**
     * 有效标志;1-有效，0-无效
     */
    @ApiModelProperty(value="有效标志;1-有效，0-无效")
    private Integer enable;
    
    /**
     * 手术等级
     */
    @ApiModelProperty(value="手术等级")
    private String optLevel;
    
    /**
     * 切口等级
     */
    @ApiModelProperty(value="切口等级")
    private Integer cutLevel;

    /**
     * 基线id
     */
    @ApiModelProperty(value="基线id")
    private String beid;

    public String getOperdefId() {
        return operdefId;
    }

    public void setOperdefId(String operdefId) {
        this.operdefId = operdefId == null ? null : operdefId.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin == null ? null : pinYin.trim();
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid == null ? null : beid.trim();
    }

	public String getOptLevel()
	{
		return optLevel;
	}

	public void setOptLevel(String optLevel)
	{
		this.optLevel = optLevel == null ? null : optLevel.trim();
	}

	public Integer getCutLevel()
	{
		return cutLevel;
	}

	public void setCutLevel(Integer cutLevel)
	{
		this.cutLevel = cutLevel;
	}
    
}