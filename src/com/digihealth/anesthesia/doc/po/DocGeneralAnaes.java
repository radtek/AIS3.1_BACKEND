/*
 * DocGeneralAnaes.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-30 Created
 */
package com.digihealth.anesthesia.doc.po;

import java.util.HashMap;
import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "全身麻醉对象")
public class DocGeneralAnaes {
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键id")
	private String generalAnaesId;

	/**
	 * 麻醉总结Id
	 */
	@ApiModelProperty(value = "麻醉总结Id")
	private String anaSumId;

	/**
	 * 诱导
	 */
	@ApiModelProperty(value = "诱导")
	private String induce;

	/**
	 * 清醒插管是否顺利
	 */
	@ApiModelProperty(value = "清醒插管是否顺利")
	private String isSoberIntubation;

	/**
	 * 声门暴露分级
	 */
	@ApiModelProperty(value = "声门暴露分级")
	private String glottisExpClass;

	/**
	 * 插管是否顺利
	 */
	@ApiModelProperty(value = "插管是否顺利")
	private String isIntubation;

	/**
	 * 导管ID
	 */
	@ApiModelProperty(value = "导管ID")
	private String catheterId;
	@ApiModelProperty(value = "导管ID")
	private Map<String, Object> catheterIdMap;

	@ApiModelProperty(value = "succCnt")
	private String succCnt;

	/**
	 * steward评分
	 */
	@ApiModelProperty(value = "steward评分")
	private String steward;

	/**
	 * 麻醉效果
	 */
	@ApiModelProperty(value = "麻醉效果")
	private String anaesEffect;

	/**
	 * 苏醒是否顺利
	 */
	@ApiModelProperty(value = "苏醒是否顺利")
	private String isWake;

	/**
	 * 咽反射恢复
	 */
	@ApiModelProperty(value = "咽反射恢复")
	private String pharyngealReflex;

	/**
	 * 发绀
	 */
	@ApiModelProperty(value = "发绀")
	private String cyanosis;

	/**
	 * 能回答
	 */
	@ApiModelProperty(value = "能回答")
	private String canAnswer;

	/**
	 * 激动
	 */
	@ApiModelProperty(value = "激动")
	private String excitement;

	/**
	 * 呕吐
	 */
	@ApiModelProperty(value = "呕吐")
	private String vomiting;

	/**
	 * '可视镜'
	 */
	private String sightGlass;

	/**
	 * '加强管'
	 */
	private String extraStrongPipe;
	
	@ApiModelProperty(value = "是否使用气管导管")
	private Integer catheterFlag;
	
	@ApiModelProperty(value = "是否使用双腔气管导管")
	private Integer doubleCavityFlag;
	
	@ApiModelProperty(value = "是否使用喉罩")
	private Integer laryngealMaskFlag;

	@ApiModelProperty(value = "双腔气管导管")
	private String doubleCavityCatheter;
	@ApiModelProperty(value = "双腔气管导管")
	private Map<String, Object> doubleCavityCatheterMap;

	@ApiModelProperty(value = "喉罩")
	private String laryngealMask;
	@ApiModelProperty(value = "喉罩")
	private Map<String, Object> laryngealMaskMap;

	@ApiModelProperty(value = "插管深度")
	private String intubationDepth;
	@ApiModelProperty(value = "插管深度")
	private Map<String, Object> intubationDepthMap;
	
	public Integer getCatheterFlag()
    {
        return catheterFlag;
    }

    public void setCatheterFlag(Integer catheterFlag)
    {
        this.catheterFlag = catheterFlag;
    }

    public Integer getDoubleCavityFlag()
    {
        return doubleCavityFlag;
    }

    public void setDoubleCavityFlag(Integer doubleCavityFlag)
    {
        this.doubleCavityFlag = doubleCavityFlag;
    }

    public Integer getLaryngealMaskFlag()
    {
        return laryngealMaskFlag;
    }

    public void setLaryngealMaskFlag(Integer laryngealMaskFlag)
    {
        this.laryngealMaskFlag = laryngealMaskFlag;
    }

    public Map<String, Object> getCatheterIdMap() {
		return null == catheterIdMap ? new HashMap<String, Object>() : catheterIdMap;
	}

	public void setCatheterIdMap(Map<String, Object> catheterIdMap) {
		this.catheterIdMap = catheterIdMap;
	}

	public Map<String, Object> getDoubleCavityCatheterMap() {
		return null == doubleCavityCatheterMap ? new HashMap<String, Object>() : doubleCavityCatheterMap;
	}

	public void setDoubleCavityCatheterMap(
			Map<String, Object> doubleCavityCatheterMap) {
		this.doubleCavityCatheterMap = doubleCavityCatheterMap;
	}

	public Map<String, Object> getLaryngealMaskMap() {
		return null == laryngealMaskMap ? new HashMap<String, Object>() : laryngealMaskMap;
	}

	public void setLaryngealMaskMap(Map<String, Object> laryngealMaskMap) {
		this.laryngealMaskMap = laryngealMaskMap;
	}

	public Map<String, Object> getIntubationDepthMap() {
		return null == intubationDepthMap ? new HashMap<String, Object>() : intubationDepthMap;
	}

	public void setIntubationDepthMap(Map<String, Object> intubationDepthMap) {
		this.intubationDepthMap = intubationDepthMap;
	}

	public String getDoubleCavityCatheter() {
		return doubleCavityCatheter;
	}

	public void setDoubleCavityCatheter(String doubleCavityCatheter) {
		this.doubleCavityCatheter = doubleCavityCatheter;
	}

	public String getLaryngealMask() {
		return laryngealMask;
	}

	public void setLaryngealMask(String laryngealMask) {
		this.laryngealMask = laryngealMask;
	}

	public String getIntubationDepth() {
		return intubationDepth;
	}

	public void setIntubationDepth(String intubationDepth) {
		this.intubationDepth = intubationDepth;
	}

	public String getSightGlass() {
		return sightGlass;
	}

	public void setSightGlass(String sightGlass) {
		this.sightGlass = sightGlass;
	}

	public String getExtraStrongPipe() {
		return extraStrongPipe;
	}

	public void setExtraStrongPipe(String extraStrongPipe) {
		this.extraStrongPipe = extraStrongPipe;
	}

	public String getGeneralAnaesId() {
		return generalAnaesId;
	}

	public void setGeneralAnaesId(String generalAnaesId) {
		this.generalAnaesId = generalAnaesId == null ? null : generalAnaesId
				.trim();
	}

	public String getAnaSumId() {
		return anaSumId;
	}

	public void setAnaSumId(String anaSumId) {
		this.anaSumId = anaSumId == null ? null : anaSumId.trim();
	}

	public String getInduce() {
		return induce;
	}

	public void setInduce(String induce) {
		this.induce = induce == null ? null : induce.trim();
	}

	public String getIsSoberIntubation() {
		return isSoberIntubation;
	}

	public void setIsSoberIntubation(String isSoberIntubation) {
		this.isSoberIntubation = isSoberIntubation == null ? null
				: isSoberIntubation.trim();
	}

	public String getGlottisExpClass() {
		return glottisExpClass;
	}

	public void setGlottisExpClass(String glottisExpClass) {
		this.glottisExpClass = glottisExpClass == null ? null : glottisExpClass
				.trim();
	}

	public String getIsIntubation() {
		return isIntubation;
	}

	public void setIsIntubation(String isIntubation) {
		this.isIntubation = isIntubation == null ? null : isIntubation.trim();
	}

	public String getCatheterId() {
		return catheterId;
	}

	public void setCatheterId(String catheterId) {
		this.catheterId = catheterId == null ? null : catheterId.trim();
	}

	public String getSuccCnt() {
		return succCnt;
	}

	public void setSuccCnt(String succCnt) {
		this.succCnt = succCnt == null ? null : succCnt.trim();
	}

	public String getSteward() {
		return steward;
	}

	public void setSteward(String steward) {
		this.steward = steward == null ? null : steward.trim();
	}

	public String getAnaesEffect() {
		return anaesEffect;
	}

	public void setAnaesEffect(String anaesEffect) {
		this.anaesEffect = anaesEffect == null ? null : anaesEffect.trim();
	}

	public String getIsWake() {
		return isWake;
	}

	public void setIsWake(String isWake) {
		this.isWake = isWake == null ? null : isWake.trim();
	}

	public String getPharyngealReflex() {
		return pharyngealReflex;
	}

	public void setPharyngealReflex(String pharyngealReflex) {
		this.pharyngealReflex = pharyngealReflex == null ? null
				: pharyngealReflex.trim();
	}

	public String getCyanosis() {
		return cyanosis;
	}

	public void setCyanosis(String cyanosis) {
		this.cyanosis = cyanosis == null ? null : cyanosis.trim();
	}

	public String getCanAnswer() {
		return canAnswer;
	}

	public void setCanAnswer(String canAnswer) {
		this.canAnswer = canAnswer == null ? null : canAnswer.trim();
	}

	public String getExcitement() {
		return excitement;
	}

	public void setExcitement(String excitement) {
		this.excitement = excitement == null ? null : excitement.trim();
	}

	public String getVomiting() {
		return vomiting;
	}

	public void setVomiting(String vomiting) {
		this.vomiting = vomiting == null ? null : vomiting.trim();
	}
}