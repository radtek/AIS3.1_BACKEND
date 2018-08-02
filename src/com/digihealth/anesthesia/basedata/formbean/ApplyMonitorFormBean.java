package com.digihealth.anesthesia.basedata.formbean;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "采集指标绑定对象")
public class ApplyMonitorFormBean {
	
	@ApiModelProperty(value = "设备ID")
	private String deviceId;
	@ApiModelProperty(value = "监测ID")
	private String eventId;
	@ApiModelProperty(value = "是否采集")
	private String optional;
	@ApiModelProperty(value = "参数ID")
	private String paraId;
	@ApiModelProperty(value = "参数名称")
	private String paraName;
	@ApiModelProperty(value = "局点列表")
	private String beidLs;
	
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getOptional() {
		return optional;
	}
	public void setOptional(String optional) {
		this.optional = optional;
	}
	public String getParaId() {
		return paraId;
	}
	public void setParaId(String paraId) {
		this.paraId = paraId;
	}
	public String getParaName() {
		return paraName;
	}
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	public String getBeidLs() {
		return beidLs;
	}
	public void setBeidLs(String beidLs) {
		this.beidLs = beidLs;
	}
	
	
	
}
