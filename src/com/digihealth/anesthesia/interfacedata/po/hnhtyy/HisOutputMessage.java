package com.digihealth.anesthesia.interfacedata.po.hnhtyy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "RESPONSE")
public class HisOutputMessage
{
    private String data;
    private Data recordList;
    
    
    @XmlElement(name = "RECORDLIST")
    public Data getRecordList() {
		return recordList;
	}
	public void setRecordList(Data recordList) {
		this.recordList = recordList;
	}
    
    @XmlElement(name = "DATA")
    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
    
}
