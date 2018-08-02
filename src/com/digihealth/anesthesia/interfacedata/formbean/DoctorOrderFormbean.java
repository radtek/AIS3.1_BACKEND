package com.digihealth.anesthesia.interfacedata.formbean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

public class DoctorOrderFormbean implements Serializable{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupId;//分组ID
	private String zyhm;//住院号
    /**
     * 医嘱执行时间
     */
    private String excuteTime;
    
    private String excutorId;
    /**
     * 执行者
     */
    private String excutor;
	

	public String getExcutorId() {
		return excutorId;
	}
	public void setExcutorId(String excutorId) {
		this.excutorId = excutorId;
	}
	public String getExcuteTime() {
		return excuteTime;
	}
	public void setExcuteTime(String excuteTime) {
		this.excuteTime = excuteTime;
	}
	public String getExcutor() {
		return excutor;
	}
	public void setExcutor(String excutor) {
		this.excutor = excutor;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getZyhm() {
		return zyhm;
	}
	public void setZyhm(String zyhm) {
		this.zyhm = zyhm;
	}
	
	
}