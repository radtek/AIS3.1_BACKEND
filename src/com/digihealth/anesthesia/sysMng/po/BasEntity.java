package com.digihealth.anesthesia.sysMng.po;

import java.util.List;

public class BasEntity
{
    private String id;
    private String value;
    
    private List<BasEntity> entityList;
    public List<BasEntity> getEntityList()
    {
        return entityList;
    }
    public void setEntityList(List<BasEntity> entityList)
    {
        this.entityList = entityList;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
}
