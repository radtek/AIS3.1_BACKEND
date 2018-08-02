package com.digihealth.anesthesia.doc.formbean;

import java.util.Date;

public class NurseInteviewRecordFormBean {
	/**
	 * 手术室护理工作访视记录
	 */
	private String id;

	/**
	 * 手术id
	 */
	private String regOptId;

	/**
	 * 患者心理状况,单选
	 */
	private Integer patientMind;

	/**
	 * 访视者签名
	 */
	private String interviewUser;

	/**
	 * 访视者名称
	 */
	private String interviewUsername;

	/**
	 * 家属
	 */
	private String interviewRelation;

	/**
	 * 访视时间
	 */
	private Date interviewTime;

	/**
	 * END NO_END
	 */
	private String processState;

	/**
	 * 查询病历资料
	 */
	private String medicalRecord;

	/**
	 * 相关情况介绍
	 */
	private String conditionIntroduce;

	/**
	 * 术前准备情况
	 */
	private String prePrepareCase;

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

	public Integer getPatientMind() {
		return patientMind;
	}

	public void setPatientMind(Integer patientMind) {
		this.patientMind = patientMind;
	}

	public String getInterviewUser() {
		return interviewUser;
	}

	public void setInterviewUser(String interviewUser) {
		this.interviewUser = interviewUser;
	}

	public String getInterviewUsername() {
		return interviewUsername;
	}

	public void setInterviewUsername(String interviewUsername) {
		this.interviewUsername = interviewUsername;
	}

	public String getInterviewRelation() {
		return interviewRelation;
	}

	public void setInterviewRelation(String interviewRelation) {
		this.interviewRelation = interviewRelation;
	}

	public Date getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public String getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(String medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public String getConditionIntroduce() {
		return conditionIntroduce;
	}

	public void setConditionIntroduce(String conditionIntroduce) {
		this.conditionIntroduce = conditionIntroduce;
	}

	public String getPreOperExplain() {
		return preOperExplain;
	}

	public void setPreOperExplain(String preOperExplain) {
		this.preOperExplain = preOperExplain;
	}

	public String getOperPressureSore() {
		return operPressureSore;
	}

	public void setOperPressureSore(String operPressureSore) {
		this.operPressureSore = operPressureSore;
	}

	public String getPreventPressureMeasure() {
		return preventPressureMeasure;
	}

	public void setPreventPressureMeasure(String preventPressureMeasure) {
		this.preventPressureMeasure = preventPressureMeasure;
	}

	public String getPrePrepareCase() {
		return prePrepareCase;
	}

	public void setPrePrepareCase(String prePrepareCase) {
		this.prePrepareCase = prePrepareCase;
	}

	/**
	 * 术前事项交代
	 */
	private String preOperExplain;

	/**
	 * 术前可能发生压疮
	 */
	private String operPressureSore;

	/**
	 * 预防压疮采取的措施
	 */
	private String preventPressureMeasure;

}
