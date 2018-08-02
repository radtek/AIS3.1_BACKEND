package com.digihealth.anesthesia.doc.po;

import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "手术病人术前风险评估及预防护理记录单对象")
public class DocRiskEvaluatPreventCare {
    
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private String id;

    /**
     * 患者id
     */
    @ApiModelProperty(value = "患者id")
    private String regOptId;

    /**
     * 完成状态;NO_END:未完成,END:完成
     */
    @ApiModelProperty(value = "完成状态")
    private String processState;

    /**
     * 手术部位
     */
    @ApiModelProperty(value = "手术部位")
    private String surgeryPosition;

    /**
     * 意识
     */
    @ApiModelProperty(value = "意识")
    private Integer conscious;

    /**
     * 意识其他
     */
    @ApiModelProperty(value = "意识其他")
    private String consciousOther;

    /**
     * 心理
     */
    @ApiModelProperty(value = "心理")
    private Integer mental;

    /**
     * 体内有无植入物
     */
    @ApiModelProperty(value = "体内有无植入物")
    private Integer implant;

    /**
     * 植入物详情
     */
    @ApiModelProperty(value = "植入物详情")
    private String implantDetail;

    /**
     * 植入物其他
     */
    @ApiModelProperty(value = "植入物其他")
    private String implantOther;

    /**
     * 管道
     */
    @ApiModelProperty(value = "管道")
    private Integer pipeline;

    /**
     * 管道详情
     */
    @ApiModelProperty(value = "管道详情")
    private String pipelineDetail;

    /**
     * 管道状态
     */
    @ApiModelProperty(value = "管道状态")
    private Integer pipelineStatus;

    /**
     * 低体温预防
     */
    @ApiModelProperty(value = "低体温预防")
    private String lowTemp;

    /**
     * 低体温预防其他
     */
    @ApiModelProperty(value = "低体温预防其他")
    private String lowTempOther;

    /**
     * 脱管滑脱预防
     */
    @ApiModelProperty(value = "脱管滑脱预防")
    private String disengage;

    /**
     * 脱管滑脱预防其他
     */
    @ApiModelProperty(value = "脱管滑脱预防其他")
    private String disengageOther;

    /**
     * 压疮评分
     */
    @ApiModelProperty(value = "压疮评分")
    private Integer braden;

    /**
     * BMI
     */
    @ApiModelProperty(value = "BMI")
    private String bmi;
    
    @ApiModelProperty(value = "BMI分数")
    private String bmiSco;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Integer sex;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 皮肤类型
     */
    @ApiModelProperty(value = "皮肤类型")
    private String skinType;

    /**
     * 手术体位
     */
    @ApiModelProperty(value = "手术体位")
    private String optBody;

    /**
     * 预计手术中施加的外力
     */
    @ApiModelProperty(value = "预计手术中施加的外力")
    private String expectForce;

    /**
     * 预计手术时间
     */
    @ApiModelProperty(value = "预计手术时间")
    private String expectTime;

    /**
     * 特殊手术原因
     */
    @ApiModelProperty(value = "特殊手术原因")
    private String specialReason;

    /**
     * 心血管及全身状况
     */
    @ApiModelProperty(value = "心血管及全身状况")
    private String generalCond;

    @ApiModelProperty(value = "压疮评分时间")
    private String bradenDate;
    
    /**
     * 有无压疮
     */
    @ApiModelProperty(value = "有无压疮")
    private Integer isBraden;

    /**
     * 压疮部位及分期
     */
    @ApiModelProperty(value = "压疮部位及分期")
    private String bradenPosition;

    /**
     * 压疮护理措施
     */
    @ApiModelProperty(value = "压疮护理措施")
    private String bradenCare;

    /**
     * 压疮护理措施其他
     */
    @ApiModelProperty(value = "压疮护理措施其他")
    private String bradenCareOther;

    /**
     * 跌倒评分
     */
    @ApiModelProperty(value = "跌倒评分")
    private Integer fall;
    
    /**
     * 跌倒评分年龄
     */
    @ApiModelProperty(value = "跌倒评分年龄")
    private Integer fallAge;

    /**
     * 跌倒/坠床史
     */
    @ApiModelProperty(value = "跌倒/坠床史")
    private String fallHis;

    /**
     * 平衡能力
     */
    @ApiModelProperty(value = "平衡能力")
    private String balance;

    /**
     * 意识状态
     */
    @ApiModelProperty(value = "意识状态")
    private String awareness;

    /**
     * 视力
     */
    @ApiModelProperty(value = "视力")
    private String vision;

    /**
     * 特殊药物
     */
    @ApiModelProperty(value = "特殊药物")
    private String specialMed;

    /**
     * 慢性病
     */
    @ApiModelProperty(value = "慢性病")
    private String chronic;

    /**
     * 相关症状
     */
    @ApiModelProperty(value = "相关症状")
    private String symptoms;

    /**
     * 尿失禁
     */
    @ApiModelProperty(value = "尿失禁")
    private String incontinence;

    /**
     * 陪护照顾
     */
    @ApiModelProperty(value = "陪护照顾")
    private String accompany;
    
    @ApiModelProperty(value = "跌倒评分时间")
    private String fallDate;

    /**
     * 跌倒护理措施
     */
    @ApiModelProperty(value = "跌倒护理措施")
    private String fallCare;

    /**
     * 跌倒护理措施其他
     */
    @ApiModelProperty(value = "跌倒护理措施其他")
    private String fallCareOther;

    /**
     * 评估护士签名
     */
    @ApiModelProperty(value = "评估护士签名")
    private String evaluatNurse;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "植入物详情")
    private Map<String, Object> implantMap;
    
    @ApiModelProperty(value = "管道详情")
    private Map<String, Object> pipelineMap;
    
    @ApiModelProperty(value = "低体温预防")
    private Map<String, Object> lowTempMap;
    
    @ApiModelProperty(value = "脱管滑脱预防")
    private Map<String, Object> disengageMap;
    
    @ApiModelProperty(value = "压疮护理措施")
    private Map<String, Object> bradenCareMap;

    @ApiModelProperty(value = "跌倒护理措施")
    private Map<String, Object> fallCareMap;
    
    public String getBmiSco()
    {
        return bmiSco;
    }

    public void setBmiSco(String bmiSco)
    {
        this.bmiSco = bmiSco;
    }

    public String getBradenDate()
    {
        return bradenDate;
    }

    public void setBradenDate(String bradenDate)
    {
        this.bradenDate = bradenDate;
    }

    public Integer getFallAge()
    {
        return fallAge;
    }

    public void setFallAge(Integer fallAge)
    {
        this.fallAge = fallAge;
    }

    public String getFallDate()
    {
        return fallDate;
    }

    public void setFallDate(String fallDate)
    {
        this.fallDate = fallDate;
    }

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

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getSurgeryPosition() {
        return surgeryPosition;
    }

    public void setSurgeryPosition(String surgeryPosition) {
        this.surgeryPosition = surgeryPosition;
    }

    public Integer getConscious() {
        return conscious;
    }

    public void setConscious(Integer conscious) {
        this.conscious = conscious;
    }

    public String getConsciousOther() {
        return consciousOther;
    }

    public void setConsciousOther(String consciousOther) {
        this.consciousOther = consciousOther;
    }

    public Integer getMental() {
        return mental;
    }

    public void setMental(Integer mental) {
        this.mental = mental;
    }

    public Integer getImplant() {
        return implant;
    }

    public void setImplant(Integer implant) {
        this.implant = implant;
    }

    public String getImplantDetail() {
        return implantDetail;
    }

    public void setImplantDetail(String implantDetail) {
        this.implantDetail = implantDetail;
    }

    public String getImplantOther() {
        return implantOther;
    }

    public void setImplantOther(String implantOther) {
        this.implantOther = implantOther;
    }

    public Integer getPipeline() {
        return pipeline;
    }

    public void setPipeline(Integer pipeline) {
        this.pipeline = pipeline;
    }

    public String getPipelineDetail() {
        return pipelineDetail;
    }

    public void setPipelineDetail(String pipelineDetail) {
        this.pipelineDetail = pipelineDetail;
    }

    public Integer getPipelineStatus() {
        return pipelineStatus;
    }

    public void setPipelineStatus(Integer pipelineStatus) {
        this.pipelineStatus = pipelineStatus;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getLowTempOther() {
        return lowTempOther;
    }

    public void setLowTempOther(String lowTempOther) {
        this.lowTempOther = lowTempOther;
    }

    public String getDisengage() {
        return disengage;
    }

    public void setDisengage(String disengage) {
        this.disengage = disengage;
    }

    public String getDisengageOther() {
        return disengageOther;
    }

    public void setDisengageOther(String disengageOther) {
        this.disengageOther = disengageOther;
    }

    public Integer getBraden() {
        return braden;
    }

    public void setBraden(Integer braden) {
        this.braden = braden;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSkinType() {
        return skinType;
    }

    public void setSkinType(String skinType) {
        this.skinType = skinType;
    }

    public String getOptBody() {
        return optBody;
    }

    public void setOptBody(String optBody) {
        this.optBody = optBody;
    }

    public String getExpectForce() {
        return expectForce;
    }

    public void setExpectForce(String expectForce) {
        this.expectForce = expectForce;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public String getSpecialReason() {
        return specialReason;
    }

    public void setSpecialReason(String specialReason) {
        this.specialReason = specialReason;
    }

    public String getGeneralCond() {
        return generalCond;
    }

    public void setGeneralCond(String generalCond) {
        this.generalCond = generalCond;
    }

    public Integer getIsBraden() {
        return isBraden;
    }

    public void setIsBraden(Integer isBraden) {
        this.isBraden = isBraden;
    }

    public String getBradenPosition() {
        return bradenPosition;
    }

    public void setBradenPosition(String bradenPosition) {
        this.bradenPosition = bradenPosition;
    }

    public String getBradenCare() {
        return bradenCare;
    }

    public void setBradenCare(String bradenCare) {
        this.bradenCare = bradenCare;
    }

    public String getBradenCareOther() {
        return bradenCareOther;
    }

    public void setBradenCareOther(String bradenCareOther) {
        this.bradenCareOther = bradenCareOther;
    }

    public Integer getFall() {
        return fall;
    }

    public void setFall(Integer fall) {
        this.fall = fall;
    }

    public String getFallHis() {
        return fallHis;
    }

    public void setFallHis(String fallHis) {
        this.fallHis = fallHis;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAwareness() {
        return awareness;
    }

    public void setAwareness(String awareness) {
        this.awareness = awareness;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getSpecialMed() {
        return specialMed;
    }

    public void setSpecialMed(String specialMed) {
        this.specialMed = specialMed;
    }

    public String getChronic() {
        return chronic;
    }

    public void setChronic(String chronic) {
        this.chronic = chronic;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getIncontinence() {
        return incontinence;
    }

    public void setIncontinence(String incontinence) {
        this.incontinence = incontinence;
    }

    public String getAccompany() {
        return accompany;
    }

    public void setAccompany(String accompany) {
        this.accompany = accompany;
    }

    public String getFallCare() {
        return fallCare;
    }

    public void setFallCare(String fallCare) {
        this.fallCare = fallCare;
    }

    public String getFallCareOther() {
        return fallCareOther;
    }

    public void setFallCareOther(String fallCareOther) {
        this.fallCareOther = fallCareOther;
    }

    public String getEvaluatNurse() {
        return evaluatNurse;
    }

    public void setEvaluatNurse(String evaluatNurse) {
        this.evaluatNurse = evaluatNurse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Object> getImplantMap()
    {
        return implantMap;
    }

    public void setImplantMap(Map<String, Object> implantMap)
    {
        this.implantMap = implantMap;
    }

    public Map<String, Object> getPipelineMap()
    {
        return pipelineMap;
    }

    public void setPipelineMap(Map<String, Object> pipelineMap)
    {
        this.pipelineMap = pipelineMap;
    }

    public Map<String, Object> getLowTempMap()
    {
        return lowTempMap;
    }

    public void setLowTempMap(Map<String, Object> lowTempMap)
    {
        this.lowTempMap = lowTempMap;
    }

    public Map<String, Object> getDisengageMap()
    {
        return disengageMap;
    }

    public void setDisengageMap(Map<String, Object> disengageMap)
    {
        this.disengageMap = disengageMap;
    }

    public Map<String, Object> getBradenCareMap()
    {
        return bradenCareMap;
    }

    public void setBradenCareMap(Map<String, Object> bradenCareMap)
    {
        this.bradenCareMap = bradenCareMap;
    }

    public Map<String, Object> getFallCareMap()
    {
        return fallCareMap;
    }

    public void setFallCareMap(Map<String, Object> fallCareMap)
    {
        this.fallCareMap = fallCareMap;
    }
}