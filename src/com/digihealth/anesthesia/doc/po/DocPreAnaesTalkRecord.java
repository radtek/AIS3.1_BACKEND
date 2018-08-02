/*
 * DocPreAnaesTalkRecord.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-07-31 Created
 */
package com.digihealth.anesthesia.doc.po;

import java.util.Date;

public class DocPreAnaesTalkRecord {
    private String id;

    private String regOptId;

    private String processState;

    /**
     * 特殊病史
     */
    private String specialMedicalHis;

    /**
     * 麻醉医师签名
     */
    private String anesthetistSign;

    /**
     * 日期时间
     */
    private Date recordTime;

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

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState == null ? null : processState.trim();
    }

    public String getSpecialMedicalHis() {
        return specialMedicalHis;
    }

    public void setSpecialMedicalHis(String specialMedicalHis) {
        this.specialMedicalHis = specialMedicalHis == null ? null : specialMedicalHis.trim();
    }

    public String getAnesthetistSign() {
        return anesthetistSign;
    }

    public void setAnesthetistSign(String anesthetistSign) {
        this.anesthetistSign = anesthetistSign == null ? null : anesthetistSign.trim();
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
}