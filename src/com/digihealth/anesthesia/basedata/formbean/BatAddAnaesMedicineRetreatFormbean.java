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

import com.digihealth.anesthesia.basedata.po.BasAnaesMedicineRetreatRecord;

/**
 * 批量退药
 * @author dell
 *
 */
public class BatAddAnaesMedicineRetreatFormbean {
	/**
     * 退药日期
     */
    private Date retreatTime;

    /**
     * 经办人
     */
    private String operator;

    /**
     * 退药人
     */
    private String retreatName;

    /**
     * 退药理由
     */
    private String retreatreason;
    /**
     * 退药类型：1 普通取药，2 手术取药
     */
    private String retreatType;


    private List<BasAnaesMedicineRetreatRecord> resList;

    
    
	public String getRetreatType() {
		return retreatType;
	}

	public void setRetreatType(String retreatType) {
		this.retreatType = retreatType;
	}

	public Date getRetreatTime() {
		return retreatTime;
	}

	public void setRetreatTime(Date retreatTime) {
		this.retreatTime = retreatTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRetreatName() {
		return retreatName;
	}

	public void setRetreatName(String retreatName) {
		this.retreatName = retreatName;
	}

	public String getRetreatreason() {
		return retreatreason;
	}

	public void setRetreatreason(String retreatreason) {
		this.retreatreason = retreatreason;
	}

	public List<BasAnaesMedicineRetreatRecord> getResList() {
		return resList;
	}

	public void setResList(List<BasAnaesMedicineRetreatRecord> resList) {
		this.resList = resList;
	}


    
    
}