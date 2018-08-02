package com.digihealth.anesthesia.basedata.po;

public class BasOtherEvent {
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 拼音码
     */
    private String pinyin;

    /**
     * 是否有效.0:否;1:是
     */
    private Integer enable;

    /**
     * 局点id
     */
    private String beid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid;
    }
}