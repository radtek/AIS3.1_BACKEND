/*
 * DocPrePublicity.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-30 Created
 */
package com.digihealth.anesthesia.doc.po;

import java.util.Date;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "麻醉手术室术前宣教对象")
public class DocPrePublicity {

	@ApiModelProperty(value = "主键id")
    private String id;

    /**
     * 完成状态
     */
	@ApiModelProperty(value = "完成状态")
    private String processState;

    /**
     * 手术ID
     */
	@ApiModelProperty(value = "手术ID")
    private String regOptId;

    /**
     * 护士签名
     */
	@ApiModelProperty(value = "护士签名")
    private String nurseId;

	@ApiModelProperty(value = "签名日期")
    private Date date;

    /**
     * 备注
     */
	@ApiModelProperty(value = "备注")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId == null ? null : regOptId.trim();
    }

	public String getNurseId() {
		return nurseId;
	}

	public void setNurseId(String nurseId) {
		this.nurseId = nurseId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}