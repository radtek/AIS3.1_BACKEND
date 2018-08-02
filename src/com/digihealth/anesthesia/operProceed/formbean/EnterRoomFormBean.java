package com.digihealth.anesthesia.operProceed.formbean;

import java.util.Date;

public class EnterRoomFormBean {
	private String regOptId;// 手术id
	private Date inTime;// 入室时间
	private String docId;// 麻醉记录单
	private Integer code; // 入室code 为1
	private Date operTime; // 手术命令开始时间
	private String anaEventId; // 麻醉事件id
	private String beid;
	private String isPacu;//1是复苏记录单
	private Integer docType; //文书类型

	
	public Integer getDocType()
    {
        return docType;
    }

    public void setDocType(Integer docType)
    {
        this.docType = docType;
    }

    public String getIsPacu() {
		return isPacu;
	}

	public void setIsPacu(String isPacu) {
		this.isPacu = isPacu;
	}
	public String getRegOptId() {
		return regOptId;
	}

	public void setRegOptId(String regOptId) {
		this.regOptId = regOptId;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getAnaEventId() {
		return anaEventId;
	}

	public void setAnaEventId(String anaEventId) {
		this.anaEventId = anaEventId;
	}

	public String getBeid() {
		return beid;
	}

	public void setBeid(String beid) {
		this.beid = beid;
	}

}
