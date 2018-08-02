package com.digihealth.anesthesia.doc.po;

import java.util.Date;

public class DocPatRescurRecord {
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
     * 抢救时间
     */
    private Date rescueTime;

    /**
     * 抢救结果
     */
    private Integer rescueResult;

    /**
     * 临床诊断
     */
    private String diagnosis;

    /**
     * 参加抢救人员及职称
     */
    private String rescueWorker;

    /**
     * 抢救原因
     */
    private String rescueReason;

    /**
     * 抢救主要措施及经过
     */
    private String measureAndAfter;

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

    public Date getRescueTime() {
        return rescueTime;
    }

    public void setRescueTime(Date rescueTime) {
        this.rescueTime = rescueTime;
    }

    public Integer getRescueResult() {
        return rescueResult;
    }

    public void setRescueResult(Integer rescueResult) {
        this.rescueResult = rescueResult;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getRescueWorker() {
        return rescueWorker;
    }

    public void setRescueWorker(String rescueWorker) {
        this.rescueWorker = rescueWorker;
    }

    public String getRescueReason() {
        return rescueReason;
    }

    public void setRescueReason(String rescueReason) {
        this.rescueReason = rescueReason;
    }

    public String getMeasureAndAfter() {
        return measureAndAfter;
    }

    public void setMeasureAndAfter(String measureAndAfter) {
        this.measureAndAfter = measureAndAfter;
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