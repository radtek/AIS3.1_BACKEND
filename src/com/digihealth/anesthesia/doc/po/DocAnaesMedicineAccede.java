package com.digihealth.anesthesia.doc.po;

public class DocAnaesMedicineAccede {
    private String id;

    private String regOptId;

    /**
     * 完成状态;NO_END:未完成,END:完成
     */
    private String processState;

    /**
     * 医保自费药
     */
    private String insuredMedicine;

    /**
     * 麻醉科医师签名
     */
    private String anaestheitistId;

    /**
     * 签订日期
     */
    private String date;

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

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getInsuredMedicine() {
        return insuredMedicine;
    }

    public void setInsuredMedicine(String insuredMedicine) {
        this.insuredMedicine = insuredMedicine;
    }

    public String getAnaestheitistId() {
        return anaestheitistId;
    }

    public void setAnaestheitistId(String anaestheitistId) {
        this.anaestheitistId = anaestheitistId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}