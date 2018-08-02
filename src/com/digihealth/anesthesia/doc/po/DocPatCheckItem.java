package com.digihealth.anesthesia.doc.po;

import java.util.Date;

public class DocPatCheckItem {
    private String id;

    /**
     * 检查记录表主键
     */
    private String recId;

    /**
     * 检查方法
     */
    private String checkMethod;

    /**
     * 诊断意见
     */
    private String option;

    /**
     * 医嘱项
     */
    private String advice;

    /**
     * 住院号
     */
    private String zyhm;

    /**
     * 患者ID
     */
    private String regOptId;

    /**
     * 报告日期
     */
    private Date date;

    /**
     * 检查部位
     */
    private String checkPart;

    /**
     * 检查所见
     */
    private String checkSituation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getZyhm() {
        return zyhm;
    }

    public void setZyhm(String zyhm) {
        this.zyhm = zyhm;
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCheckPart() {
        return checkPart;
    }

    public void setCheckPart(String checkPart) {
        this.checkPart = checkPart;
    }

    public String getCheckSituation() {
        return checkSituation;
    }

    public void setCheckSituation(String checkSituation) {
        this.checkSituation = checkSituation;
    }
}