package com.digihealth.anesthesia.doc.po;

import java.util.Date;

public class DocPatInspectRecord {
    private String id;

    /**
     * HIS系统检查id
     */
    private String inspectId;

    /**
     * 医嘱Id
     */
    private String docId;

    /**
     * 患者Id
     */
    private String regOptId;

    /**
     * 申请Id
     */
    private String no;

    /**
     * 申请日期
     */
    private Date date;

    /**
     * 申请科室
     */
    private String dep;

    /**
     * 样本
     */
    private String samp;

    /**
     * 大类
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 说明
     */
    private String instruction;

    /**
     * 状态
     */
    private String state;

    /**
     * 报告日期
     */
    private Date reportDate;

    /**
     * 申请医生
     */
    private String reqDoctorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getSamp() {
        return samp;
    }

    public void setSamp(String samp) {
        this.samp = samp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReqDoctorId() {
        return reqDoctorId;
    }

    public void setReqDoctorId(String reqDoctorId) {
        this.reqDoctorId = reqDoctorId;
    }
}