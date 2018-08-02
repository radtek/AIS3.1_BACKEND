/*
 * DocAnaesMethodChangeRecord.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-03-13 Created
 */
package com.digihealth.anesthesia.doc.po;

import java.util.Date;
import java.util.List;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class DocAnaesMethodChangeRecord {
    /**
     * 麻醉方法变更表主键
     */
    private String id;

    private String regOptId;

    /**
     * 变更后麻醉方法名称
     */
    private String anaesMethodName;

    /**
     * 更改后麻醉方法CODE
     */
    private String anaesMethodCode;
    
    
    @ApiModelProperty(value = "麻醉方法对象")
	private List<String> anaesMethodCodes;
    
    /**
     * 变更原因
     */
    private String reason;

    /**
     * 麻醉效果评定 (1满意 2欠佳)
     */
    private String anaesEffect;

    /**
     * 麻醉医生
     */
    private String anesthetistId;
    /**
     * 麻醉医生名称
     */
    private String anesthetistName;

    /**
     * 上级医师
     */
    private String superiorPhysician;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * END,NO_END
     */
    private String processState;

    
    public List<String> getAnaesMethodCodes() {
		return anaesMethodCodes;
	}

	public void setAnaesMethodCodes(List<String> anaesMethodCodes) {
		this.anaesMethodCodes = anaesMethodCodes;
	}

	public String getAnesthetistName() {
		return anesthetistName;
	}

	public void setAnesthetistName(String anesthetistName) {
		this.anesthetistName = anesthetistName;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId == null ? null : regOptId.trim();
    }

    public String getAnaesMethodName() {
        return anaesMethodName;
    }

    public void setAnaesMethodName(String anaesMethodName) {
        this.anaesMethodName = anaesMethodName == null ? null : anaesMethodName.trim();
    }

    public String getAnaesMethodCode() {
        return anaesMethodCode;
    }

    public void setAnaesMethodCode(String anaesMethodCode) {
        this.anaesMethodCode = anaesMethodCode == null ? null : anaesMethodCode.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getAnaesEffect() {
        return anaesEffect;
    }

    public void setAnaesEffect(String anaesEffect) {
        this.anaesEffect = anaesEffect == null ? null : anaesEffect.trim();
    }

    public String getAnesthetistId() {
        return anesthetistId;
    }

    public void setAnesthetistId(String anesthetistId) {
        this.anesthetistId = anesthetistId == null ? null : anesthetistId.trim();
    }

    public String getSuperiorPhysician() {
        return superiorPhysician;
    }

    public void setSuperiorPhysician(String superiorPhysician) {
        this.superiorPhysician = superiorPhysician == null ? null : superiorPhysician.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState == null ? null : processState.trim();
    }
}