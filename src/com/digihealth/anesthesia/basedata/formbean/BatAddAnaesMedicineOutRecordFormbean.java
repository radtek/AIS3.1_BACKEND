/*
 * BasAnaesMedicineOutRecord.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-06-14 Created
 */
package com.digihealth.anesthesia.basedata.formbean;

import java.util.Date;
import java.util.List;

import com.digihealth.anesthesia.basedata.po.BasAnaesMedicineOutRecord;

public class BatAddAnaesMedicineOutRecordFormbean {

    /**
     * 取药日期
     */
    private Date outTime;
    /**
     * 经办人
     */
    private String operator;
    /**
     * 领用人
     */
    private String receiveName;
    /**
     * 取药类型：1 普通取药，2 手术取药
     */
    private String outType;

    private List<BasAnaesMedicineOutRecord> resList;

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public List<BasAnaesMedicineOutRecord> getResList() {
		return resList;
	}

	public void setResList(List<BasAnaesMedicineOutRecord> resList) {
		this.resList = resList;
	}

    
    
}