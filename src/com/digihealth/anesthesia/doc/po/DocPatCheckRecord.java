package com.digihealth.anesthesia.doc.po;

import java.util.Date;

public class DocPatCheckRecord {
    private String id;

    /**
     * HIS系统检查id
     */
    private String checkId;

    /**
     * 患者Id
     */
    private String regOptId;

    /**
     * 申请Id
     */
    private String no;

    /**
     * 检查时间
     */
    private Date checkTime;

    /**
     * 送检科室
     */
    private String dep;

    /**
     * 样本
     */
    private String samp;

    /**
     * 大类 1B超 2 内窥镜 3 PACS影像
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 检查名称
     */
    private String checkName;

    /**
     * 状态
     */
    private String state;

    /**
     * 报告日期
     */
    private Date reportDate;

    /**
     * 送检医生
     */
    private String reqDoctorId;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private Date examTime;

    /**
     * 执行科室
     */
    private String exeDept;

    /**
     * 报告人
     */
    private String reporter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
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

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
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

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public String getExeDept() {
        return exeDept;
    }

    public void setExeDept(String exeDept) {
        this.exeDept = exeDept;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }
}