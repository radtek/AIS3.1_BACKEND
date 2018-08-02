/*
 * DocOptNurse.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-30 Created
 */
package com.digihealth.anesthesia.doc.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digihealth.anesthesia.basedata.formbean.DesignedOptCodes;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "手术清点单对象")
public class DocOptNurse {
	@ApiModelProperty(value = "主键id")
    private String optNurseId;

	@ApiModelProperty(value = "患者id")
    private String regOptId;

    /**
     * 标本名称
     */
	@ApiModelProperty(value = "标本名称")
    private String specimenName;

    /**
     * 输液
     */
	@ApiModelProperty(value = "输液")
    private Integer infusion;

    /**
     * 出血量
     */
	@ApiModelProperty(value = "出血量")
    private Integer bleeding;

    /**
     * 尿量
     */
	@ApiModelProperty(value = "尿量")
    private Integer urine;

    /**
     * 血型
     */
	@ApiModelProperty(value = "血型")
    private String bloodType;

    /**
     * 血液成分
     */
	@ApiModelProperty(value = "血液成分")
    private String bloodComponents;

    /**
     * 血量
     */
	@ApiModelProperty(value = "血量")
    private Integer blood;

	@ApiModelProperty(value = "手术医生id")
    private String operatorId;

	@ApiModelProperty(value = "手术医生名字")
    private String operatorName;

	@ApiModelProperty(value = "")
    private String instrnuseId;

	@ApiModelProperty(value = "")
    private String circunurseId;

    /**
     * 医生确认
     */
	@ApiModelProperty(value = "医生确认")
    private String doctorConfirm;

    /**
     * END,NO_END
     */
	@ApiModelProperty(value = "是否完成")
    private String processState;

	@ApiModelProperty(value = "仅手术室时间")
    private Date inOperRoomTime;

	@ApiModelProperty(value = "出手术室时间")
    private Date outOperRoomTime;

    /**
     * 无菌外包指示卡
     */
	@ApiModelProperty(value = "无菌外包指示卡")
    private Integer asepticPackage;

    /**
     * 包内化学指示卡
     */
	@ApiModelProperty(value = "包内化学指示卡")
    private Integer bagChemistry;

    /**
     * 植入物
     */
	@ApiModelProperty(value = "植入物")
    private Integer implant;

    /**
     * 生物监测结果
     */
	@ApiModelProperty(value = "生物监测结果")
    private Integer biologicalMonitor;

    /**
     * 特殊情况说明
     */
	@ApiModelProperty(value = "特殊情况说明")
    private String excepCase;

    /**
     * 术前洗手护士ID
     */
	@ApiModelProperty(value = "术前洗手护士id")
    private String preInstrnurseId;

    /**
     * 术后洗手护士ID
     */
	@ApiModelProperty(value = "术后洗手护士id")
    private String postInstrnurseId;

    /**
     * 术中洗手护士ID
     */
	@ApiModelProperty(value = "术中洗手护士id")
    private String midInstrnurseId;

    /**
     * 术前巡回护士ID
     */
	@ApiModelProperty(value = "术前巡回护士id")
    private String preCircunurseId;

    /**
     * 术后巡回护士ID
     */
	@ApiModelProperty(value = "术后巡回护士id")
    private String postCircunurseId;

    /**
     * 术中巡回护士ID
     */
	@ApiModelProperty(value = "术中巡回护士id")
    private String midCircunurseId;

	@ApiModelProperty(value = "")
	private int shuHouState;
	
	/**
     * 麻醉方式ID
     */
	@ApiModelProperty(value = "麻醉方式ID")
    private String anaesMethodId;

    /**
     * 术前皮肤是否完整
     */
	@ApiModelProperty(value = "术前皮肤是否完整")
    private Integer preOperSkin;

    /**
     * 术前皮肤详情
     */
	@ApiModelProperty(value = "术前皮肤详情")
    private String preOperSkinDetails;

    /**
     * 导尿
     */
	@ApiModelProperty(value = "导尿")
    private Integer catheterization;

    /**
     * 体位
     */
	@ApiModelProperty(value = "体位")
    private String optBody;

    /**
     * 冰冻
     */
	@ApiModelProperty(value = "冰冻")
    private Integer frozen;

    /**
     * 术后病理
     */
	@ApiModelProperty(value = "术后病理")
    private Integer postOperPathology;

    /**
     * 引流
     */
	@ApiModelProperty(value = "引流")
    private Integer drainage;

    /**
     * 术后皮肤是否完整
     */
	@ApiModelProperty(value = "术后皮肤是否完整")
    private Integer postOperSkin;

    /**
     * 术后皮肤详情
     */
	@ApiModelProperty(value = "术后皮肤详情")
    private String postOperSkinDetails;

    /**
     * 术后送回
     */
	@ApiModelProperty(value = "术后送回")
    private Integer leaveTo;

    /**
     * 出病房时间
     */
	@ApiModelProperty(value = "出病房时间")
    private Date outSickroomTime;

    /**
     * 回病房时间
     */
	@ApiModelProperty(value = "回病房时间")
    private Date backSickroomTime;
	
	/**
     * 手术医生
     */
    @ApiModelProperty(value = "手术医生")
	private String operDoctor;
    
    /**
     * 接班洗手护士
     */
    @ApiModelProperty(value = "接班洗手护士")
    private String shiftInstrnuseId;

    /**
     * 接班巡回护士
     */
    @ApiModelProperty(value = "接班巡回护士")
    private String shiftCircunurseId;

    /**
     * 洗手护士交接班时间
     */
    @ApiModelProperty(value = "洗手护士交接班时间")
    private Date shiftInstrnuseTime;

    /**
     * 巡回护士交接班时间
     */
    @ApiModelProperty(value = "巡回护士交接班时间")
    private Date shiftCircunurseTime;
    
    /**
     * 去向其他
     */
    @ApiModelProperty(value = "去向其他")
    private String leaveToOther;

    /**
     * 电刀
     */
    @ApiModelProperty(value = "电刀")
    private Integer electricKnife;

    /**
     * 负极板位置
     */
    @ApiModelProperty(value = "负极板位置")
    private String negativePlate;

    /**
     * 负极板位置其他
     */
    @ApiModelProperty(value = "负极板位置其他")
    private String negativePlateOther;

    /**
     * 电刀灼伤
     */
    @ApiModelProperty(value = "电刀灼伤")
    private Integer burning;

    /**
     * 灼伤部位
     */
    @ApiModelProperty(value = "灼伤部位")
    private String burningPlace;

    /**
     * 灼伤面积
     */
    @ApiModelProperty(value = "灼伤面积")
    private String burningArea;

    /**
     * 止血带
     */
    @ApiModelProperty(value = "止血带")
    private Integer tourniquet;

    /**
     * 止血带部位
     */
    @ApiModelProperty(value = "止血带部位")
    private String tourniquetPlace;

    /**
     * 止血带部位其他
     */
    @ApiModelProperty(value = "止血带部位其他")
    private String tourniquetPlaceOther;

    /**
     * 加热装置
     */
    @ApiModelProperty(value = "加热装置")
    private Integer heatDevice;

    /**
     * 加热装置详情
     */
    @ApiModelProperty(value = "加热装置详情")
    private String heatDeviceDetail;

    /**
     * 加热装置其他
     */
    @ApiModelProperty(value = "加热装置其他")
    private String heatDeviceOther;

    /**
     * 留置胃管
     */
    @ApiModelProperty(value = "留置胃管")
    private Integer stomachTube;

    /**
     * 病理标本
     */
    @ApiModelProperty(value = "病理标本")
    private Integer specimen;

    /**
     * 标本数量
     */
    @ApiModelProperty(value = "标本数量")
    private Integer specimenAmount;

    /**
     * 其他
     */
    @ApiModelProperty(value = "其他")
    private String other;
    
    @ApiModelProperty(value = "麻醉时间")
    private Date anaesTime;

	@ApiModelProperty(value = "术前巡回护士集合")
    private List<String> preCircunurseList;

	@ApiModelProperty(value = "术后巡回护士集合")
    private List<String> postCircunurseList;

	@ApiModelProperty(value = "术中巡回护士集合")
    private List<String> midCircunurseList;
	
	@ApiModelProperty(value = "术前洗手护士集合")
    private List<String> preInstrnurseList;

	@ApiModelProperty(value = "术中洗手护士集合")
    private List<String> midInstrnurseList;
	
	@ApiModelProperty(value = "术后洗手护士集合")
    private List<String> postInstrnurseList;
	
	@ApiModelProperty(value = "器械护士集合")
    private List<String> instrnuseList;

    @ApiModelProperty(value = "巡回护士集合")
    private List<String> circunurseList;
    
    @ApiModelProperty(value = "接班器械护士集合")
    private List<String> shiftInstrnuseList;

    @ApiModelProperty(value = "接班巡回护士集合")
    private List<String> shiftCircunurseList;

    @ApiModelProperty(value = "手术名称集合")
    private List<DesignedOptCodes> operationNameList;
    
    @ApiModelProperty(value = "麻醉方式集合")
    private List<String> anaesMethodList;
    
    @ApiModelProperty(value = "手术体位集合")
    private List<String> optBodyList;
    
    @ApiModelProperty(value = "手术医生集合")
    private List<String> operDoctorList;
    
    @ApiModelProperty(value = "负极板位置")
    private Map<String, Object> negativePlateMap;
    
    @ApiModelProperty(value = "止血带部位")
    private Map<String, Object> tourniquetPlaceMap;
    
    @ApiModelProperty(value = "加热装置详情")
    private Map<String, Object> heatDeviceMap;
    
    public Date getAnaesTime()
    {
        return anaesTime;
    }

    public void setAnaesTime(Date anaesTime)
    {
        this.anaesTime = anaesTime;
    }

    public String getLeaveToOther()
    {
        return leaveToOther;
    }

    public void setLeaveToOther(String leaveToOther)
    {
        this.leaveToOther = leaveToOther;
    }

    public Integer getElectricKnife()
    {
        return electricKnife;
    }

    public void setElectricKnife(Integer electricKnife)
    {
        this.electricKnife = electricKnife;
    }

    public String getNegativePlate()
    {
        return negativePlate;
    }

    public void setNegativePlate(String negativePlate)
    {
        this.negativePlate = negativePlate;
    }

    public String getNegativePlateOther()
    {
        return negativePlateOther;
    }

    public void setNegativePlateOther(String negativePlateOther)
    {
        this.negativePlateOther = negativePlateOther;
    }

    public Integer getBurning()
    {
        return burning;
    }

    public void setBurning(Integer burning)
    {
        this.burning = burning;
    }

    public String getBurningPlace()
    {
        return burningPlace;
    }

    public void setBurningPlace(String burningPlace)
    {
        this.burningPlace = burningPlace;
    }

    public String getBurningArea()
    {
        return burningArea;
    }

    public void setBurningArea(String burningArea)
    {
        this.burningArea = burningArea;
    }

    public Integer getTourniquet()
    {
        return tourniquet;
    }

    public void setTourniquet(Integer tourniquet)
    {
        this.tourniquet = tourniquet;
    }

    public String getTourniquetPlace()
    {
        return tourniquetPlace;
    }

    public void setTourniquetPlace(String tourniquetPlace)
    {
        this.tourniquetPlace = tourniquetPlace;
    }

    public String getTourniquetPlaceOther()
    {
        return tourniquetPlaceOther;
    }

    public void setTourniquetPlaceOther(String tourniquetPlaceOther)
    {
        this.tourniquetPlaceOther = tourniquetPlaceOther;
    }

    public Integer getHeatDevice()
    {
        return heatDevice;
    }

    public void setHeatDevice(Integer heatDevice)
    {
        this.heatDevice = heatDevice;
    }

    public String getHeatDeviceDetail()
    {
        return heatDeviceDetail;
    }

    public void setHeatDeviceDetail(String heatDeviceDetail)
    {
        this.heatDeviceDetail = heatDeviceDetail;
    }

    public String getHeatDeviceOther()
    {
        return heatDeviceOther;
    }

    public void setHeatDeviceOther(String heatDeviceOther)
    {
        this.heatDeviceOther = heatDeviceOther;
    }

    public Integer getStomachTube()
    {
        return stomachTube;
    }

    public void setStomachTube(Integer stomachTube)
    {
        this.stomachTube = stomachTube;
    }

    public Integer getSpecimen()
    {
        return specimen;
    }

    public void setSpecimen(Integer specimen)
    {
        this.specimen = specimen;
    }

    public Integer getSpecimenAmount()
    {
        return specimenAmount;
    }

    public void setSpecimenAmount(Integer specimenAmount)
    {
        this.specimenAmount = specimenAmount;
    }

    public String getOther()
    {
        return other;
    }

    public void setOther(String other)
    {
        this.other = other;
    }

    public Map<String, Object> getNegativePlateMap()
    {
        return null == negativePlateMap ? new HashMap<String, Object>() : negativePlateMap;
    }

    public void setNegativePlateMap(Map<String, Object> negativePlateMap)
    {
        this.negativePlateMap = negativePlateMap;
    }

    public Map<String, Object> getTourniquetPlaceMap()
    {
        return null == tourniquetPlaceMap ? new HashMap<String, Object>() : tourniquetPlaceMap;
    }

    public void setTourniquetPlaceMap(Map<String, Object> tourniquetPlaceMap)
    {
        this.tourniquetPlaceMap = tourniquetPlaceMap;
    }

    public Map<String, Object> getHeatDeviceMap()
    {
        return null == heatDeviceMap ? new HashMap<String, Object>() : heatDeviceMap;
    }

    public void setHeatDeviceMap(Map<String, Object> heatDeviceMap)
    {
        this.heatDeviceMap = heatDeviceMap;
    }

    public List<String> getShiftInstrnuseList()
    {
        return null == shiftInstrnuseList ? new ArrayList<String>() : shiftInstrnuseList;
    }

    public void setShiftInstrnuseList(List<String> shiftInstrnuseList)
    {
        this.shiftInstrnuseList = shiftInstrnuseList;
    }

    public List<String> getShiftCircunurseList()
    {
        return null == shiftCircunurseList ? new ArrayList<String>() : shiftCircunurseList;
    }

    public void setShiftCircunurseList(List<String> shiftCircunurseList)
    {
        this.shiftCircunurseList = shiftCircunurseList;
    }

    public String getShiftInstrnuseId()
    {
        return shiftInstrnuseId;
    }

    public void setShiftInstrnuseId(String shiftInstrnuseId)
    {
        this.shiftInstrnuseId = shiftInstrnuseId;
    }

    public String getShiftCircunurseId()
    {
        return shiftCircunurseId;
    }

    public void setShiftCircunurseId(String shiftCircunurseId)
    {
        this.shiftCircunurseId = shiftCircunurseId;
    }

    public Date getShiftInstrnuseTime()
    {
        return shiftInstrnuseTime;
    }

    public void setShiftInstrnuseTime(Date shiftInstrnuseTime)
    {
        this.shiftInstrnuseTime = shiftInstrnuseTime;
    }

    public Date getShiftCircunurseTime()
    {
        return shiftCircunurseTime;
    }

    public void setShiftCircunurseTime(Date shiftCircunurseTime)
    {
        this.shiftCircunurseTime = shiftCircunurseTime;
    }

    public List<String> getOperDoctorList()
    {
        return operDoctorList == null ? new ArrayList<String>() : operDoctorList;
    }

    public void setOperDoctorList(List<String> operDoctorList)
    {
        this.operDoctorList = operDoctorList;
    }

    public String getOperDoctor()
    {
        return operDoctor;
    }

    public void setOperDoctor(String operDoctor)
    {
        this.operDoctor = operDoctor;
    }

    public List<String> getOptBodyList()
    {
        return optBodyList == null ? new ArrayList<String>() : optBodyList;
    }

    public void setOptBodyList(List<String> optBodyList)
    {
        this.optBodyList = optBodyList;
    }

    public List<String> getInstrnuseList()
    {
        return instrnuseList == null ? new ArrayList<String>() : instrnuseList;
    }

    public void setInstrnuseList(List<String> instrnuseList)
    {
        this.instrnuseList = instrnuseList;
    }

    public List<String> getCircunurseList()
    {
        return circunurseList == null ? new ArrayList<String>() : circunurseList;
    }

    public void setCircunurseList(List<String> circunurseList)
    {
        this.circunurseList = circunurseList;
    }

    public List<DesignedOptCodes> getOperationNameList()
    {
        return operationNameList == null ? new ArrayList<DesignedOptCodes>() : operationNameList;
    }

    public void setOperationNameList(List<DesignedOptCodes> operationNameList)
    {
        this.operationNameList = operationNameList;
    }

    public List<String> getAnaesMethodList()
    {
        return anaesMethodList == null ? new ArrayList<String>() : anaesMethodList;
    }

    public void setAnaesMethodList(List<String> anaesMethodList)
    {
        this.anaesMethodList = anaesMethodList;
    }

    public String getOptNurseId() {
        return optNurseId;
    }

    public void setOptNurseId(String optNurseId) {
        this.optNurseId = optNurseId == null ? null : optNurseId.trim();
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId == null ? null : regOptId.trim();
    }

    public String getSpecimenName() {
        return specimenName;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName == null ? null : specimenName.trim();
    }

    public Integer getInfusion() {
        return infusion;
    }

    public void setInfusion(Integer infusion) {
        this.infusion = infusion;
    }

    public Integer getBleeding() {
        return bleeding;
    }

    public void setBleeding(Integer bleeding) {
        this.bleeding = bleeding;
    }

    public Integer getUrine() {
        return urine;
    }

    public void setUrine(Integer urine) {
        this.urine = urine;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType == null ? null : bloodType.trim();
    }

    public String getBloodComponents() {
        return bloodComponents;
    }

    public void setBloodComponents(String bloodComponents) {
        this.bloodComponents = bloodComponents == null ? null : bloodComponents.trim();
    }

    public Integer getBlood() {
        return blood;
    }

    public void setBlood(Integer blood) {
        this.blood = blood;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public String getInstrnuseId() {
        return instrnuseId;
    }

    public void setInstrnuseId(String instrnuseId) {
        this.instrnuseId = instrnuseId == null ? null : instrnuseId.trim();
    }

    public String getCircunurseId() {
        return circunurseId;
    }

    public void setCircunurseId(String circunurseId) {
        this.circunurseId = circunurseId == null ? null : circunurseId.trim();
    }

    public String getDoctorConfirm() {
        return doctorConfirm;
    }

    public void setDoctorConfirm(String doctorConfirm) {
        this.doctorConfirm = doctorConfirm == null ? null : doctorConfirm.trim();
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState == null ? null : processState.trim();
    }

    public Date getInOperRoomTime() {
        return inOperRoomTime;
    }

    public void setInOperRoomTime(Date inOperRoomTime) {
        this.inOperRoomTime = inOperRoomTime;
    }

    public Date getOutOperRoomTime() {
        return outOperRoomTime;
    }

    public void setOutOperRoomTime(Date outOperRoomTime) {
        this.outOperRoomTime = outOperRoomTime;
    }

    public Integer getAsepticPackage() {
        return asepticPackage;
    }

    public void setAsepticPackage(Integer asepticPackage) {
        this.asepticPackage = asepticPackage;
    }

    public Integer getBagChemistry() {
        return bagChemistry;
    }

    public void setBagChemistry(Integer bagChemistry) {
        this.bagChemistry = bagChemistry;
    }

    public Integer getImplant() {
        return implant;
    }

    public void setImplant(Integer implant) {
        this.implant = implant;
    }

    public Integer getBiologicalMonitor() {
        return biologicalMonitor;
    }

    public void setBiologicalMonitor(Integer biologicalMonitor) {
        this.biologicalMonitor = biologicalMonitor;
    }

    public String getExcepCase() {
        return excepCase;
    }

    public void setExcepCase(String excepCase) {
        this.excepCase = excepCase == null ? null : excepCase.trim();
    }

    public String getPreInstrnurseId() {
        return preInstrnurseId;
    }

    public String getAnaesMethodId()
    {
        return anaesMethodId;
    }

    public void setAnaesMethodId(String anaesMethodId)
    {
        this.anaesMethodId = anaesMethodId;
    }

    public Integer getPreOperSkin()
    {
        return preOperSkin;
    }

    public void setPreOperSkin(Integer preOperSkin)
    {
        this.preOperSkin = preOperSkin;
    }

    public String getPreOperSkinDetails()
    {
        return preOperSkinDetails;
    }

    public void setPreOperSkinDetails(String preOperSkinDetails)
    {
        this.preOperSkinDetails = preOperSkinDetails;
    }

    public Integer getCatheterization()
    {
        return catheterization;
    }

    public void setCatheterization(Integer catheterization)
    {
        this.catheterization = catheterization;
    }

    public String getOptBody()
    {
        return optBody;
    }

    public void setOptBody(String optBody)
    {
        this.optBody = optBody;
    }

    public Integer getFrozen()
    {
        return frozen;
    }

    public void setFrozen(Integer frozen)
    {
        this.frozen = frozen;
    }

    public Integer getPostOperPathology()
    {
        return postOperPathology;
    }

    public void setPostOperPathology(Integer postOperPathology)
    {
        this.postOperPathology = postOperPathology;
    }

    public Integer getDrainage()
    {
        return drainage;
    }

    public void setDrainage(Integer drainage)
    {
        this.drainage = drainage;
    }

    public Integer getPostOperSkin()
    {
        return postOperSkin;
    }

    public void setPostOperSkin(Integer postOperSkin)
    {
        this.postOperSkin = postOperSkin;
    }

    public String getPostOperSkinDetails()
    {
        return postOperSkinDetails;
    }

    public void setPostOperSkinDetails(String postOperSkinDetails)
    {
        this.postOperSkinDetails = postOperSkinDetails;
    }

    public Integer getLeaveTo()
    {
        return leaveTo;
    }

    public void setLeaveTo(Integer leaveTo)
    {
        this.leaveTo = leaveTo;
    }

    public Date getOutSickroomTime()
    {
        return outSickroomTime;
    }

    public void setOutSickroomTime(Date outSickroomTime)
    {
        this.outSickroomTime = outSickroomTime;
    }

    public Date getBackSickroomTime()
    {
        return backSickroomTime;
    }

    public void setBackSickroomTime(Date backSickroomTime)
    {
        this.backSickroomTime = backSickroomTime;
    }

    public void setPreInstrnurseId(String preInstrnurseId) {
        this.preInstrnurseId = preInstrnurseId == null ? null : preInstrnurseId.trim();
    }

    public String getPostInstrnurseId() {
        return postInstrnurseId;
    }

    public void setPostInstrnurseId(String postInstrnurseId) {
        this.postInstrnurseId = postInstrnurseId == null ? null : postInstrnurseId.trim();
    }

    public String getMidInstrnurseId() {
        return midInstrnurseId;
    }

    public void setMidInstrnurseId(String midInstrnurseId) {
        this.midInstrnurseId = midInstrnurseId == null ? null : midInstrnurseId.trim();
    }

    public String getPreCircunurseId() {
        return preCircunurseId;
    }

    public void setPreCircunurseId(String preCircunurseId) {
        this.preCircunurseId = preCircunurseId == null ? null : preCircunurseId.trim();
    }

    public String getPostCircunurseId() {
        return postCircunurseId;
    }

    public void setPostCircunurseId(String postCircunurseId) {
        this.postCircunurseId = postCircunurseId == null ? null : postCircunurseId.trim();
    }

    public String getMidCircunurseId() {
        return midCircunurseId;
    }

    public void setMidCircunurseId(String midCircunurseId) {
        this.midCircunurseId = midCircunurseId == null ? null : midCircunurseId.trim();
    }

	public int getShuHouState() {
		return shuHouState;
	}

	public void setShuHouState(int shuHouState) {
		this.shuHouState = shuHouState;
	}

	public List<String> getPreCircunurseList() {
		return preCircunurseList == null ? new ArrayList<String>() : preCircunurseList;
	}

	public void setPreCircunurseList(List<String> preCircunurseList) {
		this.preCircunurseList = preCircunurseList;
	}

	public List<String> getPostCircunurseList() {
		return postCircunurseList == null ? new ArrayList<String>() : postCircunurseList;
	}

	public void setPostCircunurseList(List<String> postCircunurseList) {
		this.postCircunurseList = postCircunurseList;
	}

	public List<String> getMidCircunurseList() {
		return midCircunurseList == null ? new ArrayList<String>() : midCircunurseList;
	}

	public void setMidCircunurseList(List<String> midCircunurseList) {
		this.midCircunurseList = midCircunurseList;
	}

	public List<String> getPreInstrnurseList()
	{
		return preInstrnurseList == null ? new ArrayList<String>() : preInstrnurseList;
	}

	public void setPreInstrnurseList(List<String> preInstrnurseList)
	{
		this.preInstrnurseList = preInstrnurseList;
	}

	public List<String> getMidInstrnurseList()
	{
		return midInstrnurseList == null ? new ArrayList<String>() : midInstrnurseList;
	}

	public void setMidInstrnurseList(List<String> midInstrnurseList)
	{
		this.midInstrnurseList = midInstrnurseList;
	}

	public List<String> getPostInstrnurseList()
	{
		return postInstrnurseList == null ? new ArrayList<String>() : postInstrnurseList;
	}

	public void setPostInstrnurseList(List<String> postInstrnurseList)
	{
		this.postInstrnurseList = postInstrnurseList;
	}
    
}