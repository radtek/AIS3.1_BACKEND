package com.digihealth.anesthesia.doc.po;

import java.util.Date;

public class DocDifficultCaseDiscuss {
    private String id;

    private String regOptId;
    
    /**
     * END,NO_END
     */
    private String processState;

    /**
     * 主持人
     */
    private String host;

    /**
     * 职称
     */
    private String jobTitle;

    /**
     * 职务
     */
    private String position;

    /**
     * 讨论时间
     */
    private Date discussTime;

    /**
     * 讨论地点
     */
    private String discussPlace;

    /**
     * 参加讨论人员及职称
     */
    private String discussants;

    /**
     * 病情摘要
     */
    private String diseaseSummary;

    /**
     * 入院诊断
     */
    private String diagnosis;

    /**
     * 讨论目的
     */
    private String discussPurpose;

    /**
     * 参加讨论人员意见
     */
    private String opinion;

    /**
     * 主持人小结
     */
    private String hostSummary;

    /**
     * 日期
     */
    private Date recordDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId;
    }

    public String getProcessState()
    {
        return processState;
    }

    public void setProcessState(String processState)
    {
        this.processState = processState;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        this.discussPlace = discussPlace;
    }

    public String getDiscussants() {
        return discussants;
    }

    public void setDiscussants(String discussants) {
        this.discussants = discussants;
    }

    public String getDiseaseSummary() {
        return diseaseSummary;
    }

    public void setDiseaseSummary(String diseaseSummary) {
        this.diseaseSummary = diseaseSummary;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiscussPurpose() {
        return discussPurpose;
    }

    public void setDiscussPurpose(String discussPurpose) {
        this.discussPurpose = discussPurpose;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getHostSummary() {
        return hostSummary;
    }

    public void setHostSummary(String hostSummary) {
        this.hostSummary = hostSummary;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}