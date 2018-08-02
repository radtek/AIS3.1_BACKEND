package com.digihealth.anesthesia.interfacedata.po;

import javax.xml.bind.annotation.XmlElement;

public class ChargItem
{
    private String itemType; //项目类别 0：非药品 1：药品
    private String itemCode; //项目编码
    private String itemName; //项目名称
    private String itemQty; //数量（整数）
    private String itemId;
    
    @XmlElement(name = "pk_interfaceID")
    public String getItemId()
    {
        return itemId;
    }
    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }
    public String getItemType()
    {
        return itemType;
    }
    public void setItemType(String itemType)
    {
        this.itemType = itemType;
    }
    public String getItemCode()
    {
        return itemCode;
    }
    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }
    public String getItemName()
    {
        return itemName;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    public String getItemQty()
    {
        return itemQty;
    }
    public void setItemQty(String itemQty)
    {
        this.itemQty = itemQty;
    }
    
}
