package com.digihealth.anesthesia.operProceed.formbean;

import java.util.List;

import com.digihealth.anesthesia.operProceed.po.BasAnaesMonitorConfig;

public class UpdAnaesMonitorConfigFormbean
{
    private String regOptId;
    private List<BasAnaesMonitorConfig> checkList;
    public String getRegOptId()
    {
        return regOptId;
    }
    public void setRegOptId(String regOptId)
    {
        this.regOptId = regOptId;
    }
    public List<BasAnaesMonitorConfig> getCheckList()
    {
        return checkList;
    }
    public void setCheckList(List<BasAnaesMonitorConfig> checkList)
    {
        this.checkList = checkList;
    }
}
