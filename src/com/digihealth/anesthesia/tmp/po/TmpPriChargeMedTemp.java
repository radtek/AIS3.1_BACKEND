/*
 * ChargeMedTemp.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-05-11 Created
 */
package com.digihealth.anesthesia.tmp.po;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class TmpPriChargeMedTemp {
    /**
     * 模板ID
     */
    private String chargeMedTempId;

    private String createTime;

    private String createUser;

    private String tempName;

    private String pinyin;

    private String createName;

    /**
     * 模板级别：1，个人；2，科室
     */
    private Integer type;

    /**
     * 模板描述
     */
    private String remark;

    /**
     * 模板类型 1 麻醉医生 2 护士
     */
    private Integer tempType;
    
    @ApiModelProperty(value="beid")
    private String beid;

    

    public String getBeid() {
		return beid;
	}

	public void setBeid(String beid) {
		this.beid = beid;
	}

	public String getChargeMedTempId() {
		return chargeMedTempId;
	}

	public void setChargeMedTempId(String chargeMedTempId) {
		this.chargeMedTempId = chargeMedTempId;
	}

	public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getTempType() {
        return tempType;
    }

    public void setTempType(Integer tempType) {
        this.tempType = tempType;
    }
}