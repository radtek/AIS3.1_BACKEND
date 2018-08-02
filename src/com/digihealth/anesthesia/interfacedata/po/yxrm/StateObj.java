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
	
	private String reservenumber;
	private String state;
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
}
