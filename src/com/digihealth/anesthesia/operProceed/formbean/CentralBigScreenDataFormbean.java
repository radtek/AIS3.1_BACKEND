package com.digihealth.anesthesia.operProceed.formbean;


public class CentralBigScreenDataFormbean {
	
	private String operRoomId;
	private String name;
	private String regOptId;
	private String circunurseName;
	private String anesthetistName;
	
	private Integer respValue;
	private Integer respState;
	
	private Integer hrValue;
	private Integer hrState;
	
	private Integer prValue;
	private Integer prState;
	
	private Integer nibpSysValue;
	private Integer nibpSysState;
	
	private Integer nibpDiasValue;
	private Integer nibpDiasState;
	
	private Integer abpSysValue;
	private Integer abpSysState;
	
	private Integer abpDiasValue;
	private Integer abpDiasState;
	
	
	//private Map<String, RealTimeDataFormBean> monitorList;
	
	
	
	public String getOperRoomId() {
		return operRoomId;
	}

	public Integer getPrValue() {
		return prValue;
	}

	public void setPrValue(Integer prValue) {
		this.prValue = prValue;
	}

	public Integer getPrState() {
		return prState;
	}

	public void setPrState(Integer prState) {
		this.prState = prState;
	}

	public Integer getAbpSysValue() {
		return abpSysValue;
	}

	public void setAbpSysValue(Integer abpSysValue) {
		this.abpSysValue = abpSysValue;
	}

	public Integer getAbpSysState() {
		return abpSysState;
	}

	public void setAbpSysState(Integer abpSysState) {
		this.abpSysState = abpSysState;
	}

	public Integer getAbpDiasValue() {
		return abpDiasValue;
	}

	public void setAbpDiasValue(Integer abpDiasValue) {
		this.abpDiasValue = abpDiasValue;
	}

	public Integer getAbpDiasState() {
		return abpDiasState;
	}

	public void setAbpDiasState(Integer abpDiasState) {
		this.abpDiasState = abpDiasState;
	}

	public Integer getRespValue() {
		return respValue;
	}

	public void setRespValue(Integer respValue) {
		this.respValue = respValue;
	}

	public Integer getRespState() {
		return respState;
	}

	public void setRespState(Integer respState) {
		this.respState = respState;
	}

	public Integer getHrValue() {
		return hrValue;
	}

	public void setHrValue(Integer hrValue) {
		this.hrValue = hrValue;
	}

	public Integer getHrState() {
		return hrState;
	}

	public void setHrState(Integer hrState) {
		this.hrState = hrState;
	}


	public Integer getNibpSysValue() {
		return nibpSysValue;
	}

	public void setNibpSysValue(Integer nibpSysValue) {
		this.nibpSysValue = nibpSysValue;
	}

	public Integer getNibpSysState() {
		return nibpSysState;
	}

	public void setNibpSysState(Integer nibpSysState) {
		this.nibpSysState = nibpSysState;
	}

	public Integer getNibpDiasValue() {
		return nibpDiasValue;
	}

	public void setNibpDiasValue(Integer nibpDiasValue) {
		this.nibpDiasValue = nibpDiasValue;
	}

	public Integer getNibpDiasState() {
		return nibpDiasState;
	}

	public void setNibpDiasState(Integer nibpDiasState) {
		this.nibpDiasState = nibpDiasState;
	}

	public void setOperRoomId(String operRoomId) {
		this.operRoomId = operRoomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegOptId() {
		return regOptId;
	}

	public void setRegOptId(String regOptId) {
		this.regOptId = regOptId;
	}

	public String getCircunurseName() {
		return circunurseName;
	}

	public void setCircunurseName(String circunurseName) {
		this.circunurseName = circunurseName;
	}

	public String getAnesthetistName() {
		return anesthetistName;
	}

	public void setAnesthetistName(String anesthetistName) {
		this.anesthetistName = anesthetistName;
	}

//	public Map<String, RealTimeDataFormBean> getMonitorList() {
//		return monitorList;
//	}
//
//	public void setMonitorList(Map<String, RealTimeDataFormBean> monitorList) {
//		this.monitorList = monitorList;
//	}
	
}
