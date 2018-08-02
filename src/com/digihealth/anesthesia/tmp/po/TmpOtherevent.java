/*
 * TmpOthereventTemp.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * 2018-04-06 Created
 */
package com.digihealth.anesthesia.tmp.po;

import java.util.Date;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="其他事件模板对象")
public class TmpOtherevent {
    /**
     * 科研模板id
     */
    @ApiModelProperty(value="其他事件模板id")
    private String id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String createUser;

    /**
     * 模板名称
     */
    @ApiModelProperty(value="模板名称")
    private String tmpName;

    /**
     * 模板级别：1，个人；2，科室
     */
    @ApiModelProperty(value="模板级别")
    private Integer type;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="基线id")
    private String beid;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBeid() {
		return beid;
	}

	public void setBeid(String beid) {
		this.beid = beid;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getTmpName() {
		return tmpName;
	}

	public void setTmpName(String tmpName) {
		this.tmpName = tmpName;
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

}