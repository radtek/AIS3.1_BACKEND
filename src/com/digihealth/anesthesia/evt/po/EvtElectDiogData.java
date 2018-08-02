package com.digihealth.anesthesia.evt.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

/**
 * 心电图数据采集
 * 
 * @author liukui
 * 
 */
public class EvtElectDiogData implements Serializable {
    private static final long serialVersionUID = 1001L;
    
	private String docId;
	private String time;
	private Date operaDate;
	private String useType;
	@Length(max=100,message="长度不能大于100")
	private String value;
    private int position;
    private String observeName;
    
    
	public String getObserveName() {
		return observeName;
	}
	public void setObserveName(String observeName) {
		this.observeName = observeName;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public Date getOperaDate() {
		return operaDate;
	}
	public void setOperaDate(Date operaDate) {
		this.operaDate = operaDate;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
    
	
}