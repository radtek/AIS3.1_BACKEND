package com.digihealth.anesthesia.doc.po;

import java.util.Map;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class DocBloodTransRecord {
    /**
     * 主键
     */
    private String bloodTransId;

    private String regOptId;
    
    /**
     * 完成状态;NO_END:未完成,END:完成
     */
    @ApiModelProperty(value = "完成状态")
    private String processState;

    /**
     * ABO血型选项为（A、B、AB、O）
     */
    private String bloodType;

    /**
     * Rh（D）血型选项为（阴性、阳性）
     */
    private String rhType;

    /**
     * 不规则抗体筛查 阴性 阳性
     */
    private String antScr;

    /**
     * 直接抗人球蛋白实验：阴性 阳性
     */
    private String antGlo;

    /**
     * 发血人
     */
    private String sendUser;

    /**
     * 输血单生成时间
     */
    private String createTime;

    /**
     * 台次
     */
    private Integer pcs;

    /**
     * 取血人
     */
    private String getUser;
    
    /**
     * 既往输血史
     */
    private Integer bloodHis;

    /**
     * 输血时间
     */
    private String transTime;
    
    /**
     * 血液成分1
     */
    private String composition1;

    /**
     * 血液成分1剂量
     */
    private String dosage1;

    /**
     * 血液成分2
     */
    private String composition2;

    /**
     * 血液成分2剂量
     */
    private String dosage2;

    /**
     * 发生反应时间
     */
    private String reactTime;

    /**
     * 有无反应
     */
    private Integer isReact;

    /**
     * 溶血反应1
     */
    private String hemolysisReact1;
    
    /**
     * 溶血反应2
     */
    private String hemolysisReact2;

    /**
     * 溶血反应3
     */
    private String hemolysisReact3;

    /**
     * 过敏性反应
     */
    private String allergicReact;

    /**
     * 发热反应
     */
    private String feverReact;

    /**
     * 其他反应
     */
    private String otherReact;
    
    private String anaestheitistId;
    
    private Map<String, Object> hemolysisReact1Map;
    private Map<String, Object> hemolysisReact2Map;
    private Map<String, Object> hemolysisReact3Map;
    private Map<String, Object> allergicReactMap;
    private Map<String, Object> feverReactMap;

    public String getAnaestheitistId()
    {
        return anaestheitistId;
    }

    public void setAnaestheitistId(String anaestheitistId)
    {
        this.anaestheitistId = anaestheitistId;
    }

    public String getComposition1()
    {
        return composition1;
    }

    public void setComposition1(String composition1)
    {
        this.composition1 = composition1;
    }

    public String getDosage1()
    {
        return dosage1;
    }

    public void setDosage1(String dosage1)
    {
        this.dosage1 = dosage1;
    }

    public String getComposition2()
    {
        return composition2;
    }

    public void setComposition2(String composition2)
    {
        this.composition2 = composition2;
    }

    public String getDosage2()
    {
        return dosage2;
    }

    public void setDosage2(String dosage2)
    {
        this.dosage2 = dosage2;
    }

    public String getProcessState()
    {
        return processState;
    }

    public void setProcessState(String processState)
    {
        this.processState = processState;
    }

    public Map<String, Object> getHemolysisReact1Map()
    {
        return hemolysisReact1Map;
    }

    public void setHemolysisReact1Map(Map<String, Object> hemolysisReact1Map)
    {
        this.hemolysisReact1Map = hemolysisReact1Map;
    }

    public Map<String, Object> getHemolysisReact2Map()
    {
        return hemolysisReact2Map;
    }

    public void setHemolysisReact2Map(Map<String, Object> hemolysisReact2Map)
    {
        this.hemolysisReact2Map = hemolysisReact2Map;
    }

    public Map<String, Object> getHemolysisReact3Map()
    {
        return hemolysisReact3Map;
    }

    public void setHemolysisReact3Map(Map<String, Object> hemolysisReact3Map)
    {
        this.hemolysisReact3Map = hemolysisReact3Map;
    }

    public Map<String, Object> getAllergicReactMap()
    {
        return allergicReactMap;
    }

    public void setAllergicReactMap(Map<String, Object> allergicReactMap)
    {
        this.allergicReactMap = allergicReactMap;
    }

    public Map<String, Object> getFeverReactMap()
    {
        return feverReactMap;
    }

    public void setFeverReactMap(Map<String, Object> feverReactMap)
    {
        this.feverReactMap = feverReactMap;
    }

    public Integer getBloodHis()
    {
        return bloodHis;
    }

    public void setBloodHis(Integer bloodHis)
    {
        this.bloodHis = bloodHis;
    }

    public String getTransTime()
    {
        return transTime;
    }

    public void setTransTime(String transTime)
    {
        this.transTime = transTime;
    }

    public String getReactTime()
    {
        return reactTime;
    }

    public void setReactTime(String reactTime)
    {
        this.reactTime = reactTime;
    }

    public Integer getIsReact()
    {
        return isReact;
    }

    public void setIsReact(Integer isReact)
    {
        this.isReact = isReact;
    }

    public String getHemolysisReact1()
    {
        return hemolysisReact1;
    }

    public void setHemolysisReact1(String hemolysisReact1)
    {
        this.hemolysisReact1 = hemolysisReact1;
    }

    public String getHemolysisReact2()
    {
        return hemolysisReact2;
    }

    public void setHemolysisReact2(String hemolysisReact2)
    {
        this.hemolysisReact2 = hemolysisReact2;
    }

    public String getHemolysisReact3()
    {
        return hemolysisReact3;
    }

    public void setHemolysisReact3(String hemolysisReact3)
    {
        this.hemolysisReact3 = hemolysisReact3;
    }

    public String getAllergicReact()
    {
        return allergicReact;
    }

    public void setAllergicReact(String allergicReact)
    {
        this.allergicReact = allergicReact;
    }

    public String getFeverReact()
    {
        return feverReact;
    }

    public void setFeverReact(String feverReact)
    {
        this.feverReact = feverReact;
    }

    public String getOtherReact()
    {
        return otherReact;
    }

    public void setOtherReact(String otherReact)
    {
        this.otherReact = otherReact;
    }

    public String getBloodTransId() {
        return bloodTransId;
    }

    public void setBloodTransId(String bloodTransId) {
        this.bloodTransId = bloodTransId;
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRhType() {
        return rhType;
    }

    public void setRhType(String rhType) {
        this.rhType = rhType;
    }

    public String getAntScr() {
        return antScr;
    }

    public void setAntScr(String antScr) {
        this.antScr = antScr;
    }

    public String getAntGlo() {
        return antGlo;
    }

    public void setAntGlo(String antGlo) {
        this.antGlo = antGlo;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getPcs() {
        return pcs;
    }

    public void setPcs(Integer pcs) {
        this.pcs = pcs;
    }

    public String getGetUser() {
        return getUser;
    }

    public void setGetUser(String getUser) {
        this.getUser = getUser;
    }
}