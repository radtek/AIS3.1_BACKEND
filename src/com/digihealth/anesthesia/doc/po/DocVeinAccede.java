package com.digihealth.anesthesia.doc.po;

import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "静脉麻醉知情同意书对象")
public class DocVeinAccede {
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
     * 麻醉医师
     */
    @ApiModelProperty(value = "麻醉医师")
    private String anaesDoctor;

    /**
     * 其他
     */
    @ApiModelProperty(value = "其他")
    private String other;
    
    /**
     * 高危因素
     */
    @ApiModelProperty(value = "高危因素")
    private String highRisk;

    /**
     * 检查/治疗内容
     */
    @ApiModelProperty(value = "检查/治疗内容")
    private String checkContent;

    /**
     * 病史
     */
    @ApiModelProperty(value = "病史")
    private String briefHis;

    /**
     * 中心神经系统
     */
    @ApiModelProperty(value = "中心神经系统")
    private Integer nervous;

    /**
     * 中心神经系统详情
     */
    @ApiModelProperty(value = "中心神经系统详情")
    private String nervousDetail;

    /**
     * 中心神经系统其他
     */
    @ApiModelProperty(value = "中心神经系统其他")
    private String nervousOther;

    /**
     * 呼吸系统
     */
    @ApiModelProperty(value = "呼吸系统")
    private Integer breath;

    /**
     * 呼吸系统详情
     */
    @ApiModelProperty(value = "呼吸系统详情")
    private String breathDetail;

    /**
     * 呼吸系统其他
     */
    @ApiModelProperty(value = "呼吸系统其他")
    private String breathOther;

    /**
     * 吸烟频率
     */
    @ApiModelProperty(value = "吸烟频率")
    private Integer smokingFreq;

    /**
     * 吸烟时间
     */
    @ApiModelProperty(value = "吸烟时间")
    private Integer smokingTime;

    /**
     * 心血管系统
     */
    @ApiModelProperty(value = "心血管系统")
    private Integer heartBool;

    /**
     * 心血管系统详情
     */
    @ApiModelProperty(value = "心血管系统详情")
    private String heartBoolDetail;

    /**
     * 遗传代谢
     */
    @ApiModelProperty(value = "遗传代谢")
    private Integer hereditary;

    /**
     * 遗传代谢详情
     */
    @ApiModelProperty(value = "遗传代谢详情")
    private String hereditaryDetail;

    /**
     * 遗传代谢其他
     */
    @ApiModelProperty(value = "遗传代谢其他")
    private String hereditaryOther;

    /**
     * 其他疾病详情
     */
    @ApiModelProperty(value = "其他疾病详情")
    private String otherDetail;

    /**
     * 手术麻醉史
     */
    @ApiModelProperty(value = "手术麻醉史")
    private Integer anaes;

    /**
     * 手术麻醉时间
     */
    @ApiModelProperty(value = "手术麻醉时间")
    private String anaesDate;

    /**
     * 特殊情况
     */
    @ApiModelProperty(value = "特殊情况")
    private Integer specialCase;

    /**
     * 药物服用情况
     */
    @ApiModelProperty(value = "药物服用情况")
    private Integer drugTaking;

    /**
     * 药/食物过敏史
     */
    @ApiModelProperty(value = "药/食物过敏史")
    private Integer allergic;

    /**
     * 身高
     */
    @ApiModelProperty(value = "身高")
    private Float height;

    /**
     * 体重
     */
    @ApiModelProperty(value = "体重")
    private Float weight;

    /**
     * 呼吸道评估
     */
    @ApiModelProperty(value = "呼吸道评估")
    private Integer breatheTract;

    /**
     * 呼吸道评估详情
     */
    @ApiModelProperty(value = "呼吸道评估详情")
    private String breatheTractDetail;

    /**
     * 牙齿详情
     */
    @ApiModelProperty(value = "牙齿详情")
    private String toothDetail;

    /**
     * 气道评估Mallampatis分级
     */
    @ApiModelProperty(value = "气道评估Mallampatis分级")
    private Integer mallampatis;

    /**
     * 神经系统
     */
    @ApiModelProperty(value = "神经系统")
    private Integer nervousSys;

    /**
     * 神经系统异常详情
     */
    @ApiModelProperty(value = "神经系统异常详情")
    private String nervousSysDetail;

    /**
     * 呼吸系统
     */
    @ApiModelProperty(value = "呼吸系统")
    private Integer breathSys;

    /**
     * 呼吸系统异常详情
     */
    @ApiModelProperty(value = "呼吸系统异常详情")
    private String breathSysDetail;

    /**
     * 心血管系统
     */
    @ApiModelProperty(value = "心血管系统")
    private Integer heartBoolSys;

    /**
     * 心血管系统异常详情
     */
    @ApiModelProperty(value = "心血管系统异常详情")
    private String heartBoolSysDetail;

    /**
     * 心率R
     */
    @ApiModelProperty(value = "心率R")
    private Integer heartrate;

    /**
     * 血压BP
     */
    @ApiModelProperty(value = "血压BP")
    private String bloodPre;

    /**
     * 实验室检查
     */
    @ApiModelProperty(value = "实验室检查")
    private Integer laboratCheck;

    /**
     * 实验室检查异常详情
     */
    @ApiModelProperty(value = "实验室检查异常详情")
    private String laboratCheckDetail;

    /**
     * 心电图
     */
    @ApiModelProperty(value = "心电图")
    private Integer ecg;

    /**
     * 心电图异常详情
     */
    @ApiModelProperty(value = "心电图异常详情")
    private String ecgDetail;

    /**
     * 胸片
     */
    @ApiModelProperty(value = "胸片")
    private Integer rabat;

    /**
     * 胸片异常详情
     */
    @ApiModelProperty(value = "胸片异常详情")
    private String rabatDetail;

    /**
     * ASA分级
     */
    @ApiModelProperty(value = "ASA分级")
    private Integer asa;

    /**
     * 综合ASA E分级
     */
    @ApiModelProperty(value = "综合ASA E分级")
    private Integer asaE;

    /**
     * 访视医生签名
     */
    @ApiModelProperty(value = "访视医生签名")
    private String visitDoctor;

    /**
     * 签订日期
     */
    @ApiModelProperty(value = "签订日期")
    private String date;
    
    @ApiModelProperty(value = "高危因素")
    private Map<String, Object> highRiskMap;
    
    @ApiModelProperty(value = "中心神经系统详情")
    private Map<String, Object> nervousMap;
    
    @ApiModelProperty(value = "呼吸系统详情")
    private Map<String, Object> breathMap;
    
    @ApiModelProperty(value = "心血管系统详情")
    private Map<String, Object> heartBoolMap;
    
    @ApiModelProperty(value = "遗传代谢详情")
    private Map<String, Object> hereditaryMap;
    
    @ApiModelProperty(value = "其他疾病详情")
    private Map<String, Object> otherMap;

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

    public String getAnaesDoctor()
    {
        return anaesDoctor;
    }

    public void setAnaesDoctor(String anaesDoctor)
    {
        this.anaesDoctor = anaesDoctor;
    }

    public String getOther()
    {
        return other;
    }

    public void setOther(String other)
    {
        this.other = other;
    }

    public String getHighRisk() {
        return highRisk;
    }

    public void setHighRisk(String highRisk) {
        this.highRisk = highRisk;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public String getBriefHis() {
        return briefHis;
    }

    public void setBriefHis(String briefHis) {
        this.briefHis = briefHis;
    }

    public Integer getNervous() {
        return nervous;
    }

    public void setNervous(Integer nervous) {
        this.nervous = nervous;
    }

    public String getNervousDetail() {
        return nervousDetail;
    }

    public void setNervousDetail(String nervousDetail) {
        this.nervousDetail = nervousDetail;
    }

    public String getNervousOther() {
        return nervousOther;
    }

    public void setNervousOther(String nervousOther) {
        this.nervousOther = nervousOther;
    }

    public Integer getBreath() {
        return breath;
    }

    public void setBreath(Integer breath) {
        this.breath = breath;
    }

    public String getBreathDetail() {
        return breathDetail;
    }

    public void setBreathDetail(String breathDetail) {
        this.breathDetail = breathDetail;
    }

    public String getBreathOther() {
        return breathOther;
    }

    public void setBreathOther(String breathOther) {
        this.breathOther = breathOther;
    }

    public Integer getSmokingFreq() {
        return smokingFreq;
    }

    public void setSmokingFreq(Integer smokingFreq) {
        this.smokingFreq = smokingFreq;
    }

    public Integer getSmokingTime() {
        return smokingTime;
    }

    public void setSmokingTime(Integer smokingTime) {
        this.smokingTime = smokingTime;
    }

    public Integer getHeartBool() {
        return heartBool;
    }

    public void setHeartBool(Integer heartBool) {
        this.heartBool = heartBool;
    }

    public String getHeartBoolDetail() {
        return heartBoolDetail;
    }

    public void setHeartBoolDetail(String heartBoolDetail) {
        this.heartBoolDetail = heartBoolDetail;
    }

    public Integer getHereditary() {
        return hereditary;
    }

    public void setHereditary(Integer hereditary) {
        this.hereditary = hereditary;
    }

    public String getHereditaryDetail() {
        return hereditaryDetail;
    }

    public void setHereditaryDetail(String hereditaryDetail) {
        this.hereditaryDetail = hereditaryDetail;
    }

    public String getHereditaryOther() {
        return hereditaryOther;
    }

    public void setHereditaryOther(String hereditaryOther) {
        this.hereditaryOther = hereditaryOther;
    }

    public String getOtherDetail() {
        return otherDetail;
    }

    public void setOtherDetail(String otherDetail) {
        this.otherDetail = otherDetail;
    }

    public Integer getAnaes() {
        return anaes;
    }

    public void setAnaes(Integer anaes) {
        this.anaes = anaes;
    }

    public String getAnaesDate() {
        return anaesDate;
    }

    public void setAnaesDate(String anaesDate) {
        this.anaesDate = anaesDate;
    }

    public Integer getSpecialCase() {
        return specialCase;
    }

    public void setSpecialCase(Integer specialCase) {
        this.specialCase = specialCase;
    }

    public Integer getDrugTaking() {
        return drugTaking;
    }

    public void setDrugTaking(Integer drugTaking) {
        this.drugTaking = drugTaking;
    }

    public Integer getAllergic() {
        return allergic;
    }

    public void setAllergic(Integer allergic) {
        this.allergic = allergic;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getBreatheTract() {
        return breatheTract;
    }

    public void setBreatheTract(Integer breatheTract) {
        this.breatheTract = breatheTract;
    }

    public String getBreatheTractDetail() {
        return breatheTractDetail;
    }

    public void setBreatheTractDetail(String breatheTractDetail) {
        this.breatheTractDetail = breatheTractDetail;
    }

    public String getToothDetail() {
        return toothDetail;
    }

    public void setToothDetail(String toothDetail) {
        this.toothDetail = toothDetail;
    }

    public Integer getMallampatis() {
        return mallampatis;
    }

    public void setMallampatis(Integer mallampatis) {
        this.mallampatis = mallampatis;
    }

    public Integer getNervousSys() {
        return nervousSys;
    }

    public void setNervousSys(Integer nervousSys) {
        this.nervousSys = nervousSys;
    }

    public String getNervousSysDetail() {
        return nervousSysDetail;
    }

    public void setNervousSysDetail(String nervousSysDetail) {
        this.nervousSysDetail = nervousSysDetail;
    }

    public Integer getBreathSys() {
        return breathSys;
    }

    public void setBreathSys(Integer breathSys) {
        this.breathSys = breathSys;
    }

    public String getBreathSysDetail() {
        return breathSysDetail;
    }

    public void setBreathSysDetail(String breathSysDetail) {
        this.breathSysDetail = breathSysDetail;
    }

    public Integer getHeartBoolSys() {
        return heartBoolSys;
    }

    public void setHeartBoolSys(Integer heartBoolSys) {
        this.heartBoolSys = heartBoolSys;
    }

    public String getHeartBoolSysDetail() {
        return heartBoolSysDetail;
    }

    public void setHeartBoolSysDetail(String heartBoolSysDetail) {
        this.heartBoolSysDetail = heartBoolSysDetail;
    }

    public Integer getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(Integer heartrate) {
        this.heartrate = heartrate;
    }

    public String getBloodPre() {
        return bloodPre;
    }

    public void setBloodPre(String bloodPre) {
        this.bloodPre = bloodPre;
    }

    public Integer getLaboratCheck() {
        return laboratCheck;
    }

    public void setLaboratCheck(Integer laboratCheck) {
        this.laboratCheck = laboratCheck;
    }

    public String getLaboratCheckDetail() {
        return laboratCheckDetail;
    }

    public void setLaboratCheckDetail(String laboratCheckDetail) {
        this.laboratCheckDetail = laboratCheckDetail;
    }

    public Integer getEcg() {
        return ecg;
    }

    public void setEcg(Integer ecg) {
        this.ecg = ecg;
    }

    public String getEcgDetail() {
        return ecgDetail;
    }

    public void setEcgDetail(String ecgDetail) {
        this.ecgDetail = ecgDetail;
    }

    public Integer getRabat() {
        return rabat;
    }

    public void setRabat(Integer rabat) {
        this.rabat = rabat;
    }

    public String getRabatDetail() {
        return rabatDetail;
    }

    public void setRabatDetail(String rabatDetail) {
        this.rabatDetail = rabatDetail;
    }

    public Integer getAsa() {
        return asa;
    }

    public void setAsa(Integer asa) {
        this.asa = asa;
    }

    public Integer getAsaE() {
        return asaE;
    }

    public void setAsaE(Integer asaE) {
        this.asaE = asaE;
    }

    public String getVisitDoctor() {
        return visitDoctor;
    }

    public void setVisitDoctor(String visitDoctor) {
        this.visitDoctor = visitDoctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Object> getHighRiskMap()
    {
        return highRiskMap;
    }

    public void setHighRiskMap(Map<String, Object> highRiskMap)
    {
        this.highRiskMap = highRiskMap;
    }

    public Map<String, Object> getNervousMap()
    {
        return nervousMap;
    }

    public void setNervousMap(Map<String, Object> nervousMap)
    {
        this.nervousMap = nervousMap;
    }

    public Map<String, Object> getBreathMap()
    {
        return breathMap;
    }

    public void setBreathMap(Map<String, Object> breathMap)
    {
        this.breathMap = breathMap;
    }

    public Map<String, Object> getHeartBoolMap()
    {
        return heartBoolMap;
    }

    public void setHeartBoolMap(Map<String, Object> heartBoolMap)
    {
        this.heartBoolMap = heartBoolMap;
    }

    public Map<String, Object> getHereditaryMap()
    {
        return hereditaryMap;
    }

    public void setHereditaryMap(Map<String, Object> hereditaryMap)
    {
        this.hereditaryMap = hereditaryMap;
    }

    public Map<String, Object> getOtherMap()
    {
        return otherMap;
    }

    public void setOtherMap(Map<String, Object> otherMap)
    {
        this.otherMap = otherMap;
    }
}