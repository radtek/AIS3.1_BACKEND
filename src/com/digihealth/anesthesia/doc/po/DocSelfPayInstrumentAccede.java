package com.digihealth.anesthesia.doc.po;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class DocSelfPayInstrumentAccede {
    private String id;

    private String regOptId;

    /**
     * 完成状态;NO_END:未完成,END:完成
     */
    private String processState;

    /**
     * 选中项
     */
    private String select;

    /**
     * 麻醉科医师签名
     */
    private String anaestheitistId;
    
	@ApiModelProperty(value = "责任护士")
    private String nurseId;

    /**
     * 签订日期
     */
    private String date;

    
    
    public String getNurseId() {
		return nurseId;
	}

	public void setNurseId(String nurseId) {
		this.nurseId = nurseId;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getAnaestheitistId() {
        return anaestheitistId;
    }

    public void setAnaestheitistId(String anaestheitistId) {
        this.anaestheitistId = anaestheitistId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}