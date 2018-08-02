package com.digihealth.anesthesia.basedata.po;

public class BasAnaesEvent {
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 事件值
     */
    private Integer eventValue;

    /**
     * 拼音码
     */
    private String pinyin;

    /**
     * 是否有效.0:否;1:是
     */
    private Integer enable;
    
    private String beid;

    public String getBeid()
    {
        return beid;
    }

    public void setBeid(String beid)
    {
        this.beid = beid;
    }

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

    public Integer getEventValue() {
        return eventValue;
    }

    public void setEventValue(Integer eventValue) {
        this.eventValue = eventValue;
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
}