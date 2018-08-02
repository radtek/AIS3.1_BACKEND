package com.digihealth.anesthesia.basedata.po;

public class BasAnaesDoctorAmount {
    /**
     * 记录月份
     */
    private String recordMonth;

    /**
     * 基线id
     */
    private String beid;

    /**
     * 麻醉医生数量
     */
    private Integer amount;

    public String getRecordMonth() {
        return recordMonth;
    }

    public void setRecordMonth(String recordMonth) {
        this.recordMonth = recordMonth;
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}