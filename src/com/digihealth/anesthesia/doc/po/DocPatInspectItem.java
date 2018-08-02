package com.digihealth.anesthesia.doc.po;

import java.util.Date;

public class DocPatInspectItem {
    private String id;

    /**
     * 检验检测记录Id
     */
    private String recId;

    /**
     * 编号
     */
    private Integer no;

    /**
     * 检验名称
     */
    private String name;

    /**
     * 值
     */
    private String val;

    /**
     * 参考值
     */
    private String refVal;

    /**
     * 单位
     */
    private String unit;

    /**
     * 培养结果
     */
    private String result;

    /**
     * 患者ID
     */
    private String regOptId;

    /**
     * 报告日期
     */
    private Date date;

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

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getRefVal() {
        return refVal;
    }

    public void setRefVal(String refVal) {
        this.refVal = refVal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}