package com.digihealth.anesthesia.operProceed.formbean;

import java.util.Date;
import java.util.List;

import com.digihealth.anesthesia.operProceed.po.BasMonitorDisplay;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="查询参数对象")
public class DisplayDateChangeFormBean {

    
    @ApiModelProperty(value="患者ID")
    private String regOptId;
	
    @ApiModelProperty(value="开始时间")
	private Date startTime; // 开始时间

    @ApiModelProperty(value="结束时间")
	private Date endTime; // 结束时间

    private List<BasMonitorDisplay> observeList;
    

	public List<BasMonitorDisplay> getObserveList() {
		return observeList;
	}

	public void setObserveList(List<BasMonitorDisplay> observeList) {
		this.observeList = observeList;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRegOptId() {
		return regOptId;
	}

	public void setRegOptId(String regOptId) {
		this.regOptId = regOptId;
	}

}
