package com.digihealth.anesthesia.interfacedata.formbean.sybx;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
public class HisResponse {
	private String resultCode;
	private String resultMessage;

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

	public HisResponse() {
		super();
	}

}
