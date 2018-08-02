package com.digihealth.anesthesia.doc.formbean;

import java.util.Date;

public class NurseInterviewFormBean {
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
	 * 家属
	 */
	private String interviewRelation;

	/**
	 * 访视时间
	 */
	private Date interviewTime;

	private String processState;

	/**
	 * 查询病历资料 JSON对象转换
	 */
	private DocKVFilters medicalRecord;

	// private String medicalRecord;

	/**
	 * 相关情况介绍 JSON对象转换
	 */
	private DocKVFilters conditionIntroduce;
	// private String conditionIntroduce;

	/**
	 * 术前事项交代 JSON对象转换
	 */
	private DocKVFilters preOperExplain;
	// private String preOperExplain;

	/**
	 * 术前可能发生压疮
	 */
	private DocKVFilters operPressureSore;
	// private String operPressureSore;

	/**
	 * 预防压疮采取的措施
	 */
	private DocKVFilters preventPressureMeasure;

	// private String preventPressureMeasure;

	/**
	 * 术前准备情况
	 */
	private DocKVFilters prePrepareCase;

	// private String prePrepareCase;

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

	public DocKVFilters getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(DocKVFilters medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public DocKVFilters getConditionIntroduce() {
		return conditionIntroduce;
	}

	public void setConditionIntroduce(DocKVFilters conditionIntroduce) {
		this.conditionIntroduce = conditionIntroduce;
	}

	public DocKVFilters getPreOperExplain() {
		return preOperExplain;
	}

	public void setPreOperExplain(DocKVFilters preOperExplain) {
		this.preOperExplain = preOperExplain;
	}

	public DocKVFilters getOperPressureSore() {
		return operPressureSore;
	}

	public void setOperPressureSore(DocKVFilters operPressureSore) {
		this.operPressureSore = operPressureSore;
	}

	public DocKVFilters getPreventPressureMeasure() {
		return preventPressureMeasure;
	}

	public void setPreventPressureMeasure(DocKVFilters preventPressureMeasure) {
		this.preventPressureMeasure = preventPressureMeasure;
	}

	public DocKVFilters getPrePrepareCase() {
		return prePrepareCase;
	}

	public void setPrePrepareCase(DocKVFilters prePrepareCase) {
		this.prePrepareCase = prePrepareCase;
	}

}
