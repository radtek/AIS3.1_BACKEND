package com.digihealth.anesthesia.interfacedata.po.syzxyy;

import javax.xml.bind.annotation.XmlElement;


public class Result {
	
	private String status;
	private String MSG;
	

	public String getMSG() {
		return MSG;
	}
	public void setMSG(String mSG) {
		MSG = mSG;
	}
	
	@XmlElement(name = "Status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
