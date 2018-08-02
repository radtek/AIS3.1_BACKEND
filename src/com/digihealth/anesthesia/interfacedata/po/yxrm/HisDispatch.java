package com.digihealth.anesthesia.interfacedata.po.yxrm;

import java.io.Serializable;

public class HisDispatch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1717938975291198941L;
	// 手术室id
	private Integer operRoomId;
	// 手术室名称
	private String operRoomName;
	// 巡回护士1
	private String circunurseId1;
	// 巡回护士2
	private String circunurseId2;
	// 器械护士1
	private String instrnurseId1;
	// 器械护士2
	private String instrnurseId2;
	// 麻醉医生id
	private String anesthetistId;
	// 开始时间
	private String startTime;
	// 台次
	private Integer pcs;

	public Integer getOperRoomId() {
		return operRoomId;
	}

	public void setOperRoomId(Integer operRoomId) {
		this.operRoomId = operRoomId;
	}

	public String getOperRoomName() {
		return operRoomName;
	}

	public void setOperRoomName(String operRoomName) {
		this.operRoomName = operRoomName;
	}

	public String getCircunurseId1() {
		return circunurseId1;
	}

	public void setCircunurseId1(String circunurseId1) {
		this.circunurseId1 = circunurseId1;
	}

	public String getCircunurseId2() {
		return circunurseId2;
	}

	public void setCircunurseId2(String circunurseId2) {
		this.circunurseId2 = circunurseId2;
	}

	public String getInstrnurseId1() {
		return instrnurseId1;
	}

	public void setInstrnurseId1(String instrnurseId1) {
		this.instrnurseId1 = instrnurseId1;
	}

	public String getInstrnurseId2() {
		return instrnurseId2;
	}

	public void setInstrnurseId2(String instrnurseId2) {
		this.instrnurseId2 = instrnurseId2;
	}

	public String getAnesthetistId() {
		return anesthetistId;
	}

	public void setAnesthetistId(String anesthetistId) {
		this.anesthetistId = anesthetistId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getPcs() {
		return pcs;
	}

	public void setPcs(Integer pcs) {
		this.pcs = pcs;
	}

}
