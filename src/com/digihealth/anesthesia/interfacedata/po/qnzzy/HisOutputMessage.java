package com.digihealth.anesthesia.interfacedata.po.qnzzy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HIS_MESSAGE")
public class HisOutputMessage
{
    private String returnCode;
    private String errorMessage;
    private Data data;
    
    @XmlElement(name = "ReturnCode")
    public String getReturnCode()
    {
        return returnCode;
    }
    public void setReturnCode(String returnCode)
    {
        this.returnCode = returnCode;
    }
    
    @XmlElement(name = "ErrorMessage")
    public String getErrorMessage()
    {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
    
    @XmlElement(name = "Data")
    public Data getData()
    {
        return data;
    }
    public void setData(Data data)
    {
        this.data = data;
    }
}
