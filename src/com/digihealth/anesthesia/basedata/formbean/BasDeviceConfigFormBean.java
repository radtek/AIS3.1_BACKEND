package com.digihealth.anesthesia.basedata.formbean;

import com.digihealth.anesthesia.basedata.po.BasDeviceConfig;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "设备与监测项配置参数对象")
public class BasDeviceConfigFormBean {

	@ApiModelProperty(value = "是否启用 1是 0否")
	private String checked;
	
	@ApiModelProperty(value = "床旁设备")
	private BasDeviceConfig basDeviceConfig;
	
	@ApiModelProperty(value = "局点ID")
	private String beid;


	public String getBeid() {
		return beid;
	}

	public void setBeid(String beid) {
		this.beid = beid;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public BasDeviceConfig getBasDeviceConfig() {
		return basDeviceConfig;
	}

	public void setBasDeviceConfig(BasDeviceConfig basDeviceConfig) {
		this.basDeviceConfig = basDeviceConfig;
	}


}
