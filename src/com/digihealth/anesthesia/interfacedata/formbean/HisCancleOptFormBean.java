package com.digihealth.anesthesia.interfacedata.formbean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
public class HisCancleOptFormBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7562638870069336973L;
	
	private String reservenumber;
	private String state;
	public String getReservenumber() {
		return reservenumber;
	}
	public void setReservenumber(String reservenumber) {
		this.reservenumber = reservenumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
