/*
 * DocPatPrevisitRecord.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-08-27 Created
 */
package com.digihealth.anesthesia.doc.po;

public class DocPatPrevisitRecord {
    /**
     * 患者访视单ID
     */
    private String patVisitId;

    /**
     * 患者ID
     */
    private String regOptId;

    /**
     * 意识
     */
    private String conscious;

    /**
     * 意识说明
     */
    private String consciousOther;

    /**
     * 过敏史
     */
    private String anaphylaxis;

    /**
     *  过敏史说明
     */
    private String anaphylaxisOther;

    /**
     * 手术史
     */
    private String surgeryHis;

    /**
     * 手术史说明
     */
    private String surgeryHisOther;

    /**
     * 植入物
     */
    private String implants;

    /**
     * 植入物说明
     */
    private String implantsOther;

    /**
     * 心脏起搏器
     */
    private String pacemaker;

    /**
     * 乙肝
     */
    private String hepatitis;

    /**
     * 其他床染病
     */
    private String infectionOther;

    /**
     * 皮肤
     */
    private String skin;

    /**
     * 部位
     */
    private String position;

    /**
     * 性质
     */
    private String nature;

    /**
     * 皮肤（其他）
     */
    private String skinOther;

    /**
     * 营养状态
     */
    private String nutritionState;

    /**
     * 活动状态
     */
    private String activityState;

    /**
     * 肢体感觉
     */
    private String limbsFeel;

    /**
     * 肢体部位
     */
    private String limbsPosition;

    /**
     * 肢体性质
     */
    private String limbsNature;

    /**
     * 心理状态
     */
    private String psychologyState;

    /**
     * 患者访视（其他）
     */
    private String preVisitOther;

    /**
     * 术后恢复状态
     */
    private String recoveryState;

    /**
     * 术后心理状态
     */
    private String postPsychologyState;

    /**
     * 伤口疼痛
     */
    private String wound;

    /**
     * 伤口情况
     */
    private String woundSituation;

    /**
     * 并发症
     */
    private String complication;

    /**
     * 并发症说明
     */
    private String complicationContent;

    /**
     * 工作意见
     */
    private String workOpinion;
    /**
     * 状态
     */
    private String processState;
    

    public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public String getPatVisitId() {
        return patVisitId;
    }

    public void setPatVisitId(String patVisitId) {
        this.patVisitId = patVisitId == null ? null : patVisitId.trim();
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId == null ? null : regOptId.trim();
    }

    public String getConscious() {
        return conscious;
    }

    public void setConscious(String conscious) {
        this.conscious = conscious == null ? null : conscious.trim();
    }

    public String getConsciousOther() {
        return consciousOther;
    }

    public void setConsciousOther(String consciousOther) {
        this.consciousOther = consciousOther == null ? null : consciousOther.trim();
    }

    public String getAnaphylaxis() {
        return anaphylaxis;
    }

    public void setAnaphylaxis(String anaphylaxis) {
        this.anaphylaxis = anaphylaxis == null ? null : anaphylaxis.trim();
    }

    public String getAnaphylaxisOther() {
        return anaphylaxisOther;
    }

    public void setAnaphylaxisOther(String anaphylaxisOther) {
        this.anaphylaxisOther = anaphylaxisOther == null ? null : anaphylaxisOther.trim();
    }

    public String getSurgeryHis() {
        return surgeryHis;
    }

    public void setSurgeryHis(String surgeryHis) {
        this.surgeryHis = surgeryHis == null ? null : surgeryHis.trim();
    }

    public String getSurgeryHisOther() {
        return surgeryHisOther;
    }

    public void setSurgeryHisOther(String surgeryHisOther) {
        this.surgeryHisOther = surgeryHisOther == null ? null : surgeryHisOther.trim();
    }

    public String getImplants() {
        return implants;
    }

    public void setImplants(String implants) {
        this.implants = implants == null ? null : implants.trim();
    }

    public String getImplantsOther() {
        return implantsOther;
    }

    public void setImplantsOther(String implantsOther) {
        this.implantsOther = implantsOther == null ? null : implantsOther.trim();
    }

    public String getPacemaker() {
        return pacemaker;
    }

    public void setPacemaker(String pacemaker) {
        this.pacemaker = pacemaker == null ? null : pacemaker.trim();
    }

    public String getHepatitis() {
        return hepatitis;
    }

    public void setHepatitis(String hepatitis) {
        this.hepatitis = hepatitis == null ? null : hepatitis.trim();
    }

    public String getInfectionOther() {
        return infectionOther;
    }

    public void setInfectionOther(String infectionOther) {
        this.infectionOther = infectionOther == null ? null : infectionOther.trim();
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin == null ? null : skin.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature == null ? null : nature.trim();
    }

    public String getSkinOther() {
        return skinOther;
    }

    public void setSkinOther(String skinOther) {
        this.skinOther = skinOther == null ? null : skinOther.trim();
    }

    public String getNutritionState() {
        return nutritionState;
    }

    public void setNutritionState(String nutritionState) {
        this.nutritionState = nutritionState == null ? null : nutritionState.trim();
    }

    public String getActivityState() {
        return activityState;
    }

    public void setActivityState(String activityState) {
        this.activityState = activityState == null ? null : activityState.trim();
    }

    public String getLimbsFeel() {
        return limbsFeel;
    }

    public void setLimbsFeel(String limbsFeel) {
        this.limbsFeel = limbsFeel == null ? null : limbsFeel.trim();
    }

    public String getLimbsPosition() {
        return limbsPosition;
    }

    public void setLimbsPosition(String limbsPosition) {
        this.limbsPosition = limbsPosition == null ? null : limbsPosition.trim();
    }

    public String getLimbsNature() {
        return limbsNature;
    }

    public void setLimbsNature(String limbsNature) {
        this.limbsNature = limbsNature == null ? null : limbsNature.trim();
    }

    public String getPsychologyState() {
        return psychologyState;
    }

    public void setPsychologyState(String psychologyState) {
        this.psychologyState = psychologyState == null ? null : psychologyState.trim();
    }

    public String getPreVisitOther() {
        return preVisitOther;
    }

    public void setPreVisitOther(String preVisitOther) {
        this.preVisitOther = preVisitOther == null ? null : preVisitOther.trim();
    }

    public String getRecoveryState() {
        return recoveryState;
    }

    public void setRecoveryState(String recoveryState) {
        this.recoveryState = recoveryState == null ? null : recoveryState.trim();
    }

    public String getPostPsychologyState() {
        return postPsychologyState;
    }

    public void setPostPsychologyState(String postPsychologyState) {
        this.postPsychologyState = postPsychologyState == null ? null : postPsychologyState.trim();
    }

    public String getWound() {
        return wound;
    }

    public void setWound(String wound) {
        this.wound = wound == null ? null : wound.trim();
    }

    public String getWoundSituation() {
        return woundSituation;
    }

    public void setWoundSituation(String woundSituation) {
        this.woundSituation = woundSituation == null ? null : woundSituation.trim();
    }

    public String getComplication() {
        return complication;
    }

    public void setComplication(String complication) {
        this.complication = complication == null ? null : complication.trim();
    }

    public String getComplicationContent() {
        return complicationContent;
    }

    public void setComplicationContent(String complicationContent) {
        this.complicationContent = complicationContent == null ? null : complicationContent.trim();
    }

    public String getWorkOpinion() {
        return workOpinion;
    }

    public void setWorkOpinion(String workOpinion) {
        this.workOpinion = workOpinion == null ? null : workOpinion.trim();
    }
}