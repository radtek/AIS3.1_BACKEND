package com.digihealth.anesthesia.doc.po;

public class DocSelfPayInstrumentItem {
    private String id;

    private String accedeId;

    /**
     * 单价
     */
    private Float price;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否选中
     */
    private Integer isSelect;

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

    public String getAccedeId() {
        return accedeId;
    }

    public void setAccedeId(String accedeId) {
        this.accedeId = accedeId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }
}