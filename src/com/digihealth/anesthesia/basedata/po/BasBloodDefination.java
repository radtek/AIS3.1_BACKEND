package com.digihealth.anesthesia.basedata.po;

public class BasBloodDefination {
    /**
     * ID
     */
    private String bloodId;

    /**
     * 名称
     */
    private String name;

    /**
     * 拼音码
     */
    private String pinyin;

    /**
     * 剂量单位
     */
    private String dosageUnit;

    /**
     * 是否有效
     */
    private String enable;

    /**
     * 局点id
     */
    private String beid;

    public String getBloodId() {
        return bloodId;
    }

    public void setBloodId(String bloodId) {
        this.bloodId = bloodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid;
    }
}