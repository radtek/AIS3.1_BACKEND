package com.digihealth.anesthesia.doc.formbean;

import java.util.List;

import com.digihealth.anesthesia.basedata.formbean.DesignedOptCodes;

public class DocOptCareOperNameFormbean
{
	//患者ID
	private String regOptId;
	//手术名称List
	List<DesignedOptCodes> operationNameList;
	
	public String getRegOptId()
	{
		return regOptId;
	}
	public void setRegOptId(String regOptId)
	{
		this.regOptId = regOptId;
	}
	public List<DesignedOptCodes> getOperationNameList()
	{
		return operationNameList;
	}
	public void setOperationNameList(List<DesignedOptCodes> operationNameList)
	{
		this.operationNameList = operationNameList;
	}
}
