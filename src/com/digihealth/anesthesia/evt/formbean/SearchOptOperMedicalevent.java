package com.digihealth.anesthesia.evt.formbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.digihealth.anesthesia.evt.po.EvtMedicalEventDetail;
import com.wordnik.swagger.annotations.ApiModelProperty;

public class SearchOptOperMedicalevent {
	private String medEventId;
	private float dosage;
	private float thickness;
	private String thicknessUnit;
	private float flow;
	private String flowUnit;
	private Integer diluentQuant;
	private Date startTime;
	private Date endTime;
	private Integer ended;
	private String dosageUnit;
	private String medicineId;
	private String occurHour;
	private String color;
	private String docId;
	private Integer isContinued;
	private String spec;
	private String createUser;
	private String createUserName;
	private String name;
	private String medTakeWayId;
	private String medTakeWayName;
	private String firm;
	private String reason;
	private String reasonName;
	private String durable;
	private String priceId;
	private String pacuObsId;
	private String showOption; //流速、浓度显示标志

	private Integer pcorec;// 血浆浓度pc:0,效应室浓度ec:1
	private Float tciValue; // 血浆浓度值或效应室浓度值
	private String tciUnit; // tci单位:ug/ml,ng/ml

	private Integer docType;// 1-麻醉记录单 2-pacu复苏单
	
	private Float packageDosageAmount;
    private Float amout; //金额
    private String unit; //单位
    private Float quantity; //数量
    private Float priceMinPackage;
    /**
     * 比重
     */
    @ApiModelProperty(value = "比重")
    private String proportion; 

	private List<EvtMedicalEventDetail> medDetailList; // 用药详情list
	
	private List<Map<String,Object>> medTakeWayList = new ArrayList<Map<String,Object>>();
	
	public String getProportion()
    {
        return proportion;
    }

    public void setProportion(String proportion)
    {
        this.proportion = proportion;
    }

    public Float getPackageDosageAmount()
    {
        return packageDosageAmount;
    }

    public void setPackageDosageAmount(Float packageDosageAmount)
    {
        this.packageDosageAmount = packageDosageAmount;
    }

    public Float getAmout()
    {
        return amout;
    }

    public void setAmout(Float amout)
    {
        this.amout = amout;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public Float getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Float quantity)
    {
        this.quantity = quantity;
    }

    public Float getPriceMinPackage()
    {
        return priceMinPackage;
    }

    public void setPriceMinPackage(Float priceMinPackage)
    {
        this.priceMinPackage = priceMinPackage;
    }
    
    public List<Map<String, Object>> getMedTakeWayList()
	{
		return medTakeWayList;
	}

	public void setMedTakeWayList(List<Map<String, Object>> medTakeWayList)
	{
		this.medTakeWayList = medTakeWayList;
	}

	public String getShowOption()
    {
        return showOption;
    }

    public void setShowOption(String showOption)
    {
        this.showOption = showOption;
    }

    public SearchOptOperMedicalevent() {
		medDetailList = new ArrayList<EvtMedicalEventDetail>();
	}

	public String getPacuObsId() {
		return pacuObsId;
	}

	public void setPacuObsId(String pacuObsId) {
		this.pacuObsId = pacuObsId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getDurable() {
		return durable;
	}

	public void setDurable(String durable) {
		this.durable = durable;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getMedTakeWayName() {
		return medTakeWayName;
	}

	public void setMedTakeWayName(String medTakeWayName) {
		this.medTakeWayName = medTakeWayName;
	}

	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMedEventId() {
		return medEventId;
	}

	public void setMedEventId(String medEventId) {
		this.medEventId = medEventId;
	}

	public String getThicknessUnit() {
		return thicknessUnit;
	}

	public void setThicknessUnit(String thicknessUnit) {
		this.thicknessUnit = thicknessUnit;
	}

	public float getDosage() {
		return dosage;
	}

	public void setDosage(float dosage) {
		this.dosage = dosage;
	}

	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	public float getFlow() {
		return flow;
	}

	public void setFlow(float flow) {
		this.flow = flow;
	}

	public String getFlowUnit() {
		return flowUnit;
	}

	public void setFlowUnit(String flowUnit) {
		this.flowUnit = flowUnit;
	}

	public Integer getDiluentQuant() {
		return diluentQuant;
	}

	public void setDiluentQuant(Integer diluentQuant) {
		this.diluentQuant = diluentQuant;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public Integer getEnded() {
		return ended;
	}

	public void setEnded(Integer ended) {
		this.ended = ended;
	}

	public String getDosageUnit() {
		return dosageUnit;
	}

	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}

	public String getOccurHour() {
		return occurHour;
	}

	public void setOccurHour(String occurHour) {
		this.occurHour = occurHour;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public List<EvtMedicalEventDetail> getMedDetailList() {
		return medDetailList;
	}

	public void setMedDetailList(List<EvtMedicalEventDetail> medDetailList) {
		this.medDetailList = medDetailList;
	}

	public Integer getPcorec() {
		return pcorec;
	}

	public void setPcorec(Integer pcorec) {
		this.pcorec = pcorec;
	}

	public Float getTciValue() {
		return tciValue;
	}

	public void setTciValue(Float tciValue) {
		this.tciValue = tciValue;
	}

	public String getTciUnit() {
		return tciUnit;
	}

	public void setTciUnit(String tciUnit) {
		this.tciUnit = tciUnit;
	}

	public String getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(String medicineId) {
		this.medicineId = medicineId;
	}

	public Integer getIsContinued() {
		return isContinued;
	}

	public void setIsContinued(Integer isContinued) {
		this.isContinued = isContinued;
	}

	public String getMedTakeWayId() {
		return medTakeWayId;
	}

	public void setMedTakeWayId(String medTakeWayId) {
		this.medTakeWayId = medTakeWayId;
	}

	public String getPriceId() {
		return priceId;
	}

	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}

	public Integer getDocType() {
		return docType;
	}

	public void setDocType(Integer docType) {
		this.docType = docType;
	}

}