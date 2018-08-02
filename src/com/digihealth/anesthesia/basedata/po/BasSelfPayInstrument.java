package com.digihealth.anesthesia.basedata.po;

public class BasSelfPayInstrument {
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 单价
     */
    private Float price;

    /**
     * 拼音码
     */
    private String pinYin;

    /**
     * 是否有效
     */
    private Integer enable;

    /**
     * 基线id
     */
    private String beid;
    
    /**
     * 类型
     */
    private Integer type;

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
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