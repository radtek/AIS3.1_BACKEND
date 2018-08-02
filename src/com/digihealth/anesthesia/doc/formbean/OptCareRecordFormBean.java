package com.digihealth.anesthesia.doc.formbean;

import java.util.Date;
import java.util.List;

import com.digihealth.anesthesia.basedata.formbean.DesignedOptCodes;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "手术护理记录参数对象")
public class OptCareRecordFormBean {
	@ApiModelProperty(value = "主键id")
	private String id;

	/**
	 * 手术id
	 */
	@ApiModelProperty(value = "手术id")
	private String regOptId;

	@ApiModelProperty(value = "是否完成")
	private String processState;

	/**
	 * 入室时间
	 */
	@ApiModelProperty(value = "入室时间")
	private String inOperRoomTime;

	/**
	 * 出室时间
	 */
	@ApiModelProperty(value = "出室时间")
	private String outOperRoomTime;

	/**
	 * 药物过敏
	 */
	@ApiModelProperty(value = "药物过敏")
	private Integer allergic;

	@ApiModelProperty(value = "手术CODE")
	private String operationCode;

	/**
	 * 手术名称
	 */
	@ApiModelProperty(value = "手术名称")
	private String operationName;

	@ApiModelProperty(value = "手术名称集合")
	List<DesignedOptCodes> operationNameList;

	/**
	 * 神志
	 */
	@ApiModelProperty(value = "神志")
	private String senses;

	/**
	 * 术前静脉输液
	 */
	@ApiModelProperty(value = "术前静脉输液")
	private Integer venousInfusion1;

	/**
	 * 深静脉穿刺
	 */
	@ApiModelProperty(value = "深静脉穿刺")
	private Integer venipuncture;

	/**
	 * x线片
	 */
	@ApiModelProperty(value = "x线片")
	private Integer xray;

	/**
	 * CT片
	 */
	@ApiModelProperty(value = "CT片")
	private Integer CT;

	/**
	 * MRI片
	 */
	@ApiModelProperty(value = "MRI片")
	private Integer MRI;

	/**
	 * 手术体位
	 */
	@ApiModelProperty(value = "手术体位")
	private String optbody;

	/**
	 * 手术体位集合
	 */
	@ApiModelProperty(value = "手术体位集合")
	private List<String> optbodys;

	/**
	 * 高频电刀
	 */
	@ApiModelProperty(value = "高频电刀")
	private Integer elecKnife;

	/**
	 * 标本
	 */
	@ApiModelProperty(value = "标本")
	private Integer specimen;

	/**
	 * 送检
	 */
	@ApiModelProperty(value = "送检")
	private String inspection;

	/**
	 * 标本名称
	 */
	@ApiModelProperty(value = "标本名称")
	private String specimenName;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 手术室交班护士
	 */
	@ApiModelProperty(value = "手术室交班护士")
	private String shiftChangedNurse;

	/**
	 * 手术室接班护士(部分局点用于病房接班)
	 */
	@ApiModelProperty(value = "手术室接班护士(部分局点用于病房接班)")
	private String shiftChangeNurse;

	@ApiModelProperty(value = "手术室交班护士")
	private List<String> shiftChangedNurseList;

	@ApiModelProperty(value = "手术室接班护士")
	private List<String> shiftChangeNurseList;

	/**
	 * 管道
	 */
	@ApiModelProperty(value = "管道")
	private String pipeline;

	/**
	 * 术前皮肤情况
	 */
	@ApiModelProperty(value = "术前皮肤情况")
	private DocKVFilters skin1;

	/**
	 * 负极板位置
	 */
	@ApiModelProperty(value = "负极板位置")
	private DocKVFilters negativePosition;

	/**
	 * 止血带
	 */
	@ApiModelProperty(value = "止血带")
	private DocKVFilters tourniquet;

	/**
	 * 体位支持用物
	 */
	@ApiModelProperty(value = "体位支持用物")
	private DocKVFilters supportMaterial;

	/**
	 * 体内植入物
	 */
	@ApiModelProperty(value = "体内植入物")
	private DocKVFilters implants;

	/**
	 * 送至
	 */
	@ApiModelProperty(value = "送至")
	private String leaveTo;

	/**
	 * 送至
	 */
	@ApiModelProperty(value = "其他")
	private String leaveToOther;
	
	/**
	 * 术后静脉输液
	 */
	@ApiModelProperty(value = "术后静脉输液")
	private DocKVFilters venousInfusion2;

	/**
	 * 引流管
	 */
	@ApiModelProperty(value = "引流管")
	private DocKVFilters drainageTube;

    /**
     * 引流管(南华局点用)
     */
    @ApiModelProperty(value = "引流管")
    private String drainageTube2;

	/**
	 * 术后皮肤情况
	 */
	@ApiModelProperty(value = "术后皮肤情况")
	private DocKVFilters skin2;

	/**
	 * 洗手护士ID
	 */
	@ApiModelProperty(value = "洗手护士ID")
	private String instrnurseId;

	@ApiModelProperty(value = "洗手护士集合")
	private List<String> instrnurseList;

	/**
	 * 交班时间
	 */
	@ApiModelProperty(value = "交班时间")
	private Date shiftTime;
    
    @ApiModelProperty(value = "管道（有无）")
    private String pipeState;
    
    @ApiModelProperty(value = "送检 快速")
    private Integer inspSpeedy;
    
    @ApiModelProperty(value = "麻醉方法名称")
    private String anaesMethodName;
    
    @ApiModelProperty(value = "麻醉方法CODE")
    private String anaesMethodCode;
    
    @ApiModelProperty(value = "交班巡回护士")
    private String shiftCircunurse;
    
    @ApiModelProperty(value = "接班巡回护士")
    private String takeCircunurse;
    
    @ApiModelProperty(value = "过敏史")
    private String allergicContents;
    
    @ApiModelProperty(value = "是否使用负极板")
    private Integer negativeFlag;
    
    @ApiModelProperty(value = "是否使用体位支持用物")
    private Integer supportMaterialFlag;
    
    @ApiModelProperty(value = "是否使用引流管")
    private Integer drainageTubeFlag;
    
    @ApiModelProperty(value = "管道其他")
    private String pipelineOther;
    
    @ApiModelProperty(value = "体内植入物其他")
    private String implantsOther;
    
    /**
     * 病房交班护士
     */
    @ApiModelProperty(value = "病房交班护士")
    private String wardShiftsNurse;

    /**
     * 病房接班护士
     */
    @ApiModelProperty(value = "病房接班护士")
    private String wardSuccessionNurse;
    
    private List<String> anaesMethodList;
    
    /**
     * 血型
     */
    @ApiModelProperty(value = "血型")
    private String bloodType;

    /**
     * 血液成分
     */
    @ApiModelProperty(value = "血液成分")
    private String bloodConstituent;

    /**
     * 输血量
     */
    @ApiModelProperty(value = "输血量")
    private String bloodTransfusion;

    /**
     * 出血量
     */
    @ApiModelProperty(value = "出血量")
    private Integer bleedingAmount;

    /**
     * 输液量
     */
    @ApiModelProperty(value = "输液量")
    private Integer infusionVolume;

    /**
     * 尿量
     */
    @ApiModelProperty(value = "尿量")
    private Integer urineVolume;

    /**
     * 术前用药
     */
    @ApiModelProperty(value = "术前用药")
    private Integer preoperativeMedication;

    /**
     * 术前用药已用未用
     */
    private Integer medicationUsed;
    
    /**
     * 腕带
     */
    @ApiModelProperty(value = "腕带")
    private Integer wristband;

    /**
     * 体表标志
     */
    @ApiModelProperty(value = "体表标志")
    private Integer bodySurface;

    /**
     * 术前禁饮：1 选中，0没有选中
     */
    @ApiModelProperty(value = "术前禁饮")
    private Integer banDrinking;

    /**
     * 术前禁食：1选中，0没有选中
     */
    @ApiModelProperty(value = "术前禁食")
    private Integer fasting;

    /**
     * 手术用物灭菌指示标记:1选中，0没有选中
     */
    @ApiModelProperty(value = "手术用物灭菌指示标记")
    private Integer sterilizationMark;
    
    public String getPipelineOther()
    {
        return pipelineOther;
    }

    public void setPipelineOther(String pipelineOther)
    {
        this.pipelineOther = pipelineOther;
    }

    public String getImplantsOther()
    {
        return implantsOther;
    }

    public void setImplantsOther(String implantsOther)
    {
        this.implantsOther = implantsOther;
    }

    public Integer getNegativeFlag()
    {
        return negativeFlag;
    }

    public void setNegativeFlag(Integer negativeFlag)
    {
        this.negativeFlag = negativeFlag;
    }

    public Integer getSupportMaterialFlag()
    {
        return supportMaterialFlag;
    }

    public void setSupportMaterialFlag(Integer supportMaterialFlag)
    {
        this.supportMaterialFlag = supportMaterialFlag;
    }

    public Integer getDrainageTubeFlag()
    {
        return drainageTubeFlag;
    }

    public void setDrainageTubeFlag(Integer drainageTubeFlag)
    {
        this.drainageTubeFlag = drainageTubeFlag;
    }

    public List<String> getAnaesMethodList()
    {
        return anaesMethodList;
    }

    public void setAnaesMethodList(List<String> anaesMethodList)
    {
        this.anaesMethodList = anaesMethodList;
    }

	public String getPipeState()
    {
        return pipeState;
    }

    public void setPipeState(String pipeState)
    {
        this.pipeState = pipeState;
    }

    public Integer getInspSpeedy()
    {
        return inspSpeedy;
    }

    public void setInspSpeedy(Integer inspSpeedy)
    {
        this.inspSpeedy = inspSpeedy;
    }

    public String getAnaesMethodName()
    {
        return anaesMethodName;
    }

    public void setAnaesMethodName(String anaesMethodName)
    {
        this.anaesMethodName = anaesMethodName;
    }

    public String getAnaesMethodCode()
    {
        return anaesMethodCode;
    }

    public void setAnaesMethodCode(String anaesMethodCode)
    {
        this.anaesMethodCode = anaesMethodCode;
    }

    public String getShiftCircunurse()
    {
        return shiftCircunurse;
    }

    public void setShiftCircunurse(String shiftCircunurse)
    {
        this.shiftCircunurse = shiftCircunurse;
    }

    public String getTakeCircunurse()
    {
        return takeCircunurse;
    }

    public void setTakeCircunurse(String takeCircunurse)
    {
        this.takeCircunurse = takeCircunurse;
    }

    public String getAllergicContents()
    {
        return allergicContents;
    }

    public void setAllergicContents(String allergicContents)
    {
        this.allergicContents = allergicContents;
    }

    public String getDrainageTube2()
    {
        return drainageTube2;
    }

    public void setDrainageTube2(String drainageTube2)
    {
        this.drainageTube2 = drainageTube2;
    }

    public List<String> getShiftChangeNurseList() {
		return shiftChangeNurseList;
	}

	public void setShiftChangeNurseList(List<String> shiftChangeNurseList) {
		this.shiftChangeNurseList = shiftChangeNurseList;
	}

	public String getInstrnurseId() {
		return instrnurseId;
	}

	public void setInstrnurseId(String instrnurseId) {
		this.instrnurseId = instrnurseId;
	}

	public List<String> getInstrnurseList() {
		return instrnurseList;
	}

	public void setInstrnurseList(List<String> instrnurseList) {
		this.instrnurseList = instrnurseList;
	}

	public Date getShiftTime() {
		return shiftTime;
	}

	public void setShiftTime(Date shiftTime) {
		this.shiftTime = shiftTime;
	}

	public List<String> getShiftChangedNurseList() {
		return shiftChangedNurseList;
	}

	public void setShiftChangedNurseList(List<String> shiftChangedNurseList) {
		this.shiftChangedNurseList = shiftChangedNurseList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSkin1(DocKVFilters skin1) {
		this.skin1 = skin1;
	}

	public void setNegativePosition(DocKVFilters negativePosition) {
		this.negativePosition = negativePosition;
	}

	public void setTourniquet(DocKVFilters tourniquet) {
		this.tourniquet = tourniquet;
	}

	public void setSupportMaterial(DocKVFilters supportMaterial) {
		this.supportMaterial = supportMaterial;
	}

	public void setImplants(DocKVFilters implants) {
		this.implants = implants;
	}

	public void setVenousInfusion2(DocKVFilters venousInfusion2) {
		this.venousInfusion2 = venousInfusion2;
	}

	public void setDrainageTube(DocKVFilters drainageTube) {
		this.drainageTube = drainageTube;
	}

	public void setSkin2(DocKVFilters skin2) {
		this.skin2 = skin2;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public String getInOperRoomTime() {
		return inOperRoomTime;
	}

	public void setInOperRoomTime(String inOperRoomTime) {
		this.inOperRoomTime = inOperRoomTime;
	}

	public String getOutOperRoomTime() {
		return outOperRoomTime;
	}

	public void setOutOperRoomTime(String outOperRoomTime) {
		this.outOperRoomTime = outOperRoomTime;
	}

	public Integer getAllergic() {
		return allergic;
	}

	public void setAllergic(Integer allergic) {
		this.allergic = allergic;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getSenses() {
		return senses;
	}

	public void setSenses(String senses) {
		this.senses = senses;
	}

	public Integer getVenousInfusion1() {
		return venousInfusion1;
	}

	public void setVenousInfusion1(Integer venousInfusion1) {
		this.venousInfusion1 = venousInfusion1;
	}

	public Integer getVenipuncture() {
		return venipuncture;
	}

	public void setVenipuncture(Integer venipuncture) {
		this.venipuncture = venipuncture;
	}

	public Integer getXray() {
		return xray;
	}

	public void setXray(Integer xray) {
		this.xray = xray;
	}

	public Integer getCT() {
		return CT;
	}

	public void setCT(Integer CT) {
		this.CT = CT;
	}

	public Integer getMRI() {
		return MRI;
	}

	public void setMRI(Integer MRI) {
		this.MRI = MRI;
	}

	public String getOptbody() {
		return optbody;
	}

	public void setOptbody(String optbody) {
		this.optbody = optbody;
	}

	public List<String> getOptbodys() {
		return optbodys;
	}

	public void setOptbodys(List<String> optbodys) {
		this.optbodys = optbodys;
	}

	public Integer getElecKnife() {
		return elecKnife;
	}

	public void setElecKnife(Integer elecKnife) {
		this.elecKnife = elecKnife;
	}

	public Integer getSpecimen() {
		return specimen;
	}

	public void setSpecimen(Integer specimen) {
		this.specimen = specimen;
	}

	public String getInspection()
	{
		return inspection;
	}

	public void setInspection(String inspection)
	{
		this.inspection = inspection;
	}

	public String getSpecimenName() {
		return specimenName;
	}

	public void setSpecimenName(String specimenName) {
		this.specimenName = specimenName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShiftChangedNurse() {
		return shiftChangedNurse;
	}

	public void setShiftChangedNurse(String shiftChangedNurse) {
		this.shiftChangedNurse = shiftChangedNurse;
	}

	public String getShiftChangeNurse() {
		return shiftChangeNurse;
	}

	public void setShiftChangeNurse(String shiftChangeNurse) {
		this.shiftChangeNurse = shiftChangeNurse;
	}

	public String getPipeline() {
		return pipeline;
	}

	public void setPipeline(String pipeline) {
		this.pipeline = pipeline;
	}

	public String getRegOptId() {
		return regOptId;
	}

	public void setRegOptId(String regOptId) {
		this.regOptId = regOptId;
	}

	public List<DesignedOptCodes> getOperationNameList() {
		return operationNameList;
	}

	public void setOperationNameList(List<DesignedOptCodes> operationNameList) {
		this.operationNameList = operationNameList;
	}

	public DocKVFilters getSkin1() {
		return skin1;
	}

	public DocKVFilters getNegativePosition() {
		return negativePosition;
	}

	public DocKVFilters getTourniquet() {
		return tourniquet;
	}

	public DocKVFilters getSupportMaterial() {
		return supportMaterial;
	}

	public DocKVFilters getImplants() {
		return implants;
	}

	public DocKVFilters getVenousInfusion2() {
		return venousInfusion2;
	}

	public DocKVFilters getDrainageTube() {
		return drainageTube;
	}

	public DocKVFilters getSkin2() {
		return skin2;
	}

	public String getLeaveTo() {
		return leaveTo;
	}

	public void setLeaveTo(String leaveTo) {
		this.leaveTo = leaveTo;
	}

	public String getLeaveToOther() {
		return leaveToOther;
	}

	public void setLeaveToOther(String leaveToOther) {
		this.leaveToOther = leaveToOther;
	}

	public String getWardShiftsNurse()
	{
		return wardShiftsNurse;
	}

	public void setWardShiftsNurse(String wardShiftsNurse)
	{
		this.wardShiftsNurse = wardShiftsNurse;
	}

	public String getWardSuccessionNurse()
	{
		return wardSuccessionNurse;
	}

	public void setWardSuccessionNurse(String wardSuccessionNurse)
	{
		this.wardSuccessionNurse = wardSuccessionNurse;
	}
	public String getBloodType()
	{
		return bloodType;
	}

	public void setBloodType(String bloodType)
	{
		this.bloodType = bloodType == null ? null : bloodType.trim();
	}

	public String getBloodConstituent()
	{
		return bloodConstituent;
	}

	public void setBloodConstituent(String bloodConstituent)
	{
		this.bloodConstituent = bloodConstituent == null ? null
				: bloodConstituent.trim();
	}

	public String getBloodTransfusion()
	{
		return bloodTransfusion;
	}

	public void setBloodTransfusion(String bloodTransfusion)
	{
		this.bloodTransfusion = bloodTransfusion == null ? null
				: bloodTransfusion.trim();
	}

	public Integer getBleedingAmount()
	{
		return bleedingAmount;
	}

	public void setBleedingAmount(Integer bleedingAmount)
	{
		this.bleedingAmount = bleedingAmount;
	}

	public Integer getInfusionVolume()
	{
		return infusionVolume;
	}

	public void setInfusionVolume(Integer infusionVolume)
	{
		this.infusionVolume = infusionVolume;
	}

	public Integer getUrineVolume()
	{
		return urineVolume;
	}

	public void setUrineVolume(Integer urineVolume)
	{
		this.urineVolume = urineVolume;
	}

	public Integer getPreoperativeMedication()
	{
		return preoperativeMedication;
	}

	public void setPreoperativeMedication(Integer preoperativeMedication)
	{
		this.preoperativeMedication = preoperativeMedication;
	}

	public Integer getMedicationUsed()
	{
		return medicationUsed;
	}

	public void setMedicationUsed(Integer medicationUsed)
	{
		this.medicationUsed = medicationUsed;
	}

	public Integer getWristband()
	{
		return wristband;
	}

	public void setWristband(Integer wristband)
	{
		this.wristband = wristband;
	}

	public Integer getBodySurface()
	{
		return bodySurface;
	}

	public void setBodySurface(Integer bodySurface)
	{
		this.bodySurface = bodySurface;
	}

	public Integer getBanDrinking()
	{
		return banDrinking;
	}

	public void setBanDrinking(Integer banDrinking)
	{
		this.banDrinking = banDrinking;
	}

	public Integer getFasting()
	{
		return fasting;
	}

	public void setFasting(Integer fasting)
	{
		this.fasting = fasting;
	}

	public Integer getSterilizationMark()
	{
		return sterilizationMark;
	}

	public void setSterilizationMark(Integer sterilizationMark)
	{
		this.sterilizationMark = sterilizationMark;
	}
}