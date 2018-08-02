package com.digihealth.anesthesia.doc.po;

import java.util.Date;

public class DocBloodTransItem {
    /**
     * 主键
     */
    private String id;

    /**
     * 输血单表主键值
     */
    private String bloodTransId;

    /**
     * 血液品种
     */
    private String bloodId;

    /**
     * 血液名称
     */
    private String bloodName;

    /**
     * 输血剂量
     */
    private Float bloodDosage;

    /**
     * 单位
     */
    private String bloodUnit;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 血液申请状态 （1未申请、2申请成功）
     */
    private String status;

    /**
     * 取血时间
     */
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBloodTransId() {
        return bloodTransId;
    }

    public void setBloodTransId(String bloodTransId) {
        this.bloodTransId = bloodTransId;
    }

    public String getBloodId() {
        return bloodId;
    }

    public void setBloodId(String bloodId) {
        this.bloodId = bloodId;
    }

    public String getBloodName() {
        return bloodName;
    }

    public void setBloodName(String bloodName) {
        this.bloodName = bloodName;
    }

    public Float getBloodDosage() {
        return bloodDosage;
    }

    public void setBloodDosage(Float bloodDosage) {
        this.bloodDosage = bloodDosage;
    }

    public String getBloodUnit() {
        return bloodUnit;
    }

    public void setBloodUnit(String bloodUnit) {
        this.bloodUnit = bloodUnit;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}