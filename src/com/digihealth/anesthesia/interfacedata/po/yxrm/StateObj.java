package com.digihealth.anesthesia.interfacedata.po.yxrm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
public class StateObj  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5416929154276349287L;
	//手术预约号
	private String reservenumber;
	//手术状态
	private String state;
	//手术等级
	//private String operLevel;
	//切口等级
	private String incisionLevel;
	//是否急诊 0非急诊 1急诊
	private Integer emergency;
	//实际手术名称(可能多个逗号进行分隔)
	private String realOperationName;
	//实际手术CODE(可能多个逗号进行分隔)
	private String realOperationCode;
    //手术医生名称
	private String operatorName;
	//助手1名称
	private String assistantName;
	//助手2名称
	private String assistantName2;
	//麻醉医生名字
	private String anesthetistName;
	//手术开始时间
    private String operStartTime;
    //手术结束时间
    private String operEndTime;
    //实际麻醉方法(可能多个逗号进行分隔)
    private String anaesMethodName;

	
	public String getReservenumber()
	{
		return reservenumber;
	}
	public void setReservenumber(String reservenumber)
	{
		this.reservenumber = reservenumber;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getIncisionLevel()
	{
		return incisionLevel;
	}
	public void setIncisionLevel(String incisionLevel)
	{
		this.incisionLevel = incisionLevel;
	}
	public Integer getEmergency()
	{
		return emergency;
	}
	public void setEmergency(Integer emergency)
	{
		this.emergency = emergency;
	}
	public String getRealOperationName()
	{
		return realOperationName;
	}
	public void setRealOperationName(String realOperationName)
	{
		this.realOperationName = realOperationName;
	}
	public String getRealOperationCode()
	{
		return realOperationCode;
	}
	public void setRealOperationCode(String realOperationCode)
	{
		this.realOperationCode = realOperationCode;
	}
	public String getOperatorName()
	{
		return operatorName;
	}
	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}
	public String getAssistantName()
	{
		return assistantName;
	}
	public void setAssistantName(String assistantName)
	{
		this.assistantName = assistantName;
	}
	public String getAssistantName2()
	{
		return assistantName2;
	}
	public void setAssistantName2(String assistantName2)
	{
		this.assistantName2 = assistantName2;
	}
	public String getAnesthetistName()
	{
		return anesthetistName;
	}
	public void setAnesthetistName(String anesthetistName)
	{
		this.anesthetistName = anesthetistName;
	}
	public String getOperStartTime()
	{
		return operStartTime;
	}
	public void setOperStartTime(String operStartTime)
	{
		this.operStartTime = operStartTime;
	}
	public String getOperEndTime()
	{
		return operEndTime;
	}
	public void setOperEndTime(String operEndTime)
	{
		this.operEndTime = operEndTime;
	}
	public String getAnaesMethodName()
	{
		return anaesMethodName;
	}
	public void setAnaesMethodName(String anaesMethodName)
	{
		this.anaesMethodName = anaesMethodName;
	}
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
}
