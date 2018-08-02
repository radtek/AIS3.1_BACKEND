package com.digihealth.anesthesia.interfacedata.formbean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
public class HisResponse {
	
    private String resultCode;
    private String resultMessage;
    private String reservenumber;//急诊时需要返回手术预约号
	 
	 
	 
	public String getReservenumber() {
		return reservenumber;
	}



	public void setReservenumber(String reservenumber) {
		this.reservenumber = reservenumber;
	}



	public String getResultCode() {
		return resultCode;
	}



	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}



	public String getResultMessage() {
		return resultMessage;
	}



	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}



	public HisResponse()
	{
			super();
	}
     
     
}
