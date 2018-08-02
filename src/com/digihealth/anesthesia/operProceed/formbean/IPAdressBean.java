package com.digihealth.anesthesia.operProceed.formbean;

import java.io.Serializable;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="IPAdressBean")
public class IPAdressBean implements Serializable {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="ip")
	private String ip;

    @ApiModelProperty(value="port")
	private Integer port;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

   
}
