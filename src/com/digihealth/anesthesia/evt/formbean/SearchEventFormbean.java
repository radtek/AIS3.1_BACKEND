package com.digihealth.anesthesia.evt.formbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SearchEventFormbean
{
    private String id;
    private String name;
    private String eventId;
    private String type;
    private String subType;
    private String spec;
    private String firm;
    private String reason;
    private Float dosage;
    private String dosageUnit;
    private String way;
    private Float thickness;
    private String thicknessUnit;
    private Float flow;
    private String flowUnit;
    private Date startTime;
    private Date endTime;
    private String priceId;
    private Integer durable; 
    private String pinyin;
    
    private List<String> wayList = new ArrayList<String>();
    
    
    
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public Integer getDurable() {
		return durable;
	}
	public void setDurable(Integer durable) {
		this.durable = durable;
	}
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
	public List<String> getWayList()
    {
        return wayList;
    }
    public void setWayList(List<String> wayList)
    {
        this.wayList = wayList;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getEventId()
    {
        return eventId;
    }
    public void setEventId(String eventId)
    {
        this.eventId = eventId;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getSubType()
    {
        return subType;
    }
    public void setSubType(String subType)
    {
        this.subType = subType;
    }
    public String getSpec()
    {
        return spec;
    }
    public void setSpec(String spec)
    {
        this.spec = spec;
    }
    public String getFirm()
    {
        return firm;
    }
    public void setFirm(String firm)
    {
        this.firm = firm;
    }
    public String getReason()
    {
        return reason;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }
    public Float getDosage()
    {
        return dosage;
    }
    public void setDosage(Float dosage)
    {
        this.dosage = dosage;
    }
    public String getDosageUnit()
    {
        return dosageUnit;
    }
    public void setDosageUnit(String dosageUnit)
    {
        this.dosageUnit = dosageUnit;
    }
    public String getWay()
    {
        return way;
    }
    public void setWay(String way)
    {
        this.way = way;
    }
    public Float getThickness()
    {
        return thickness;
    }
    public void setThickness(Float thickness)
    {
        this.thickness = thickness;
    }
    public String getThicknessUnit()
    {
        return thicknessUnit;
    }
    public void setThicknessUnit(String thicknessUnit)
    {
        this.thicknessUnit = thicknessUnit;
    }
    public Float getFlow()
    {
        return flow;
    }
    public void setFlow(Float flow)
    {
        this.flow = flow;
    }
    public String getFlowUnit()
    {
        return flowUnit;
    }
    public void setFlowUnit(String flowUnit)
    {
        this.flowUnit = flowUnit;
    }
    public Date getStartTime()
    {
        return startTime;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    public Date getEndTime()
    {
        return endTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
}
