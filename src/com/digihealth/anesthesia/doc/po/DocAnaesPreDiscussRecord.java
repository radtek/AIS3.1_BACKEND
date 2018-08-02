/*
 * DocAnaesPreDiscussRecord.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-03-13 Created
 */
package com.digihealth.anesthesia.doc.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class DocAnaesPreDiscussRecord {
    /**
     * 主键
     */
    private String preDiscussId;

    /**
     * 患者ID
     */
    private String regOptId;

    /**
     * 讨论时间
     */
    private Date discussTime;

    /**
     * 讨论地点
     */
    private String discussPlace;

    /**
     * 主持人
     */
    private String host;

    /**
     * 病例报告人
     */
    private String reporter;

    /**
     * 参加人员及职称
     */
    private String participant;

    /**
     * 拟施麻醉方法名称
     */
    private String designedAnaesMethodName;

    /**
     * 拟施麻醉方法CODE
     */
    private String designedAnaesMethodCode;
    
    @ApiModelProperty(value = "麻醉方法对象")
	private List<String> designedAnaesMethodCodes;

    
    /**
     * 拟施手术时间
     */
    private Date designedTime;
    
    /**
     * 病例摘要
     */
    private String caseSummary;

    /**
     * 麻醉要点
     */
    private String anaesEssential;

    /**
     * 记录人
     */
    private String recorder;

    /**
     * 记录日期
     */
    private Date recordTime;

    /**
     * END,NO_END
     */
    private String processState;

    
    
    public List<String> getDesignedAnaesMethodCodes() {
		return null == designedAnaesMethodCodes ? new ArrayList<String>() : designedAnaesMethodCodes;
	}

	public void setDesignedAnaesMethodCodes(List<String> designedAnaesMethodCodes) {
		this.designedAnaesMethodCodes = designedAnaesMethodCodes;
	}

	public Date getDesignedTime() {
		return designedTime;
	}

	public void setDesignedTime(Date designedTime) {
		this.designedTime = designedTime;
	}

	public String getPreDiscussId() {
        return preDiscussId;
    }

    public void setPreDiscussId(String preDiscussId) {
        this.preDiscussId = preDiscussId == null ? null : preDiscussId.trim();
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId == null ? null : regOptId.trim();
    }

    public Date getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(Date discussTime) {
        this.discussTime = discussTime;
    }

    public String getDiscussPlace() {
        return discussPlace;
    }

    public void setDiscussPlace(String discussPlace) {
        this.discussPlace = discussPlace == null ? null : discussPlace.trim();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter == null ? null : reporter.trim();
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant == null ? null : participant.trim();
    }

    public String getDesignedAnaesMethodName() {
        return designedAnaesMethodName;
    }

    public void setDesignedAnaesMethodName(String designedAnaesMethodName) {
        this.designedAnaesMethodName = designedAnaesMethodName == null ? null : designedAnaesMethodName.trim();
    }

    public String getDesignedAnaesMethodCode() {
        return designedAnaesMethodCode;
    }

    public void setDesignedAnaesMethodCode(String designedAnaesMethodCode) {
        this.designedAnaesMethodCode = designedAnaesMethodCode == null ? null : designedAnaesMethodCode.trim();
    }

    public String getCaseSummary() {
        return caseSummary;
    }

    public void setCaseSummary(String caseSummary) {
        this.caseSummary = caseSummary == null ? null : caseSummary.trim();
    }

    public String getAnaesEssential() {
        return anaesEssential;
    }

    public void setAnaesEssential(String anaesEssential) {
        this.anaesEssential = anaesEssential == null ? null : anaesEssential.trim();
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder == null ? null : recorder.trim();
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState == null ? null : processState.trim();
    }
}