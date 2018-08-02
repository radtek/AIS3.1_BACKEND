package com.digihealth.anesthesia.evt.formbean;

import java.util.List;

public class BatchEventOprFormbean {
	
	private String docId;
	private String regOptId;
	private String createUser;
	private String type;//如果type不为空则删除对应Type表的数据 ,否则删除所有类型的数据
	List<SearchEventFormbean> eventList;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getRegOptId() {
		return regOptId;
	}
	public void setRegOptId(String regOptId) {
		this.regOptId = regOptId;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public List<SearchEventFormbean> getEventList() {
		return eventList;
	}
	public void setEventList(List<SearchEventFormbean> eventList) {
		this.eventList = eventList;
	}
	
	
	
}
