/*
 * DocPrevisitAccessexam.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-30 Created
 */
package com.digihealth.anesthesia.doc.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "术前访视常规检查对象")
public class DocPrevisitAccessexam {
	@ApiModelProperty(value = "主键id")
	private String id;

	/**
	 * 外键，关联pre_visit_id
	 */
	@ApiModelProperty(value = "外键，关联pre_visit_id")
	private String preVisitId;

	@ApiModelProperty(value = "患者id")
	private String regOptId;

	/**
	 * 血常规
	 */
	@ApiModelProperty(value = "血常规")
	private Integer routBloodTest;

	/**
	 * 血常规情况
	 */
	@ApiModelProperty(value = "血常规情况")
	private String routBloodTestRes;

	/**
	 * 尿常规
	 */
	@ApiModelProperty(value = "尿常规")
	private Integer routUrineTest;

	/**
	 * 尿常规情况
	 */
	@ApiModelProperty(value = "尿常规情况")
	private String routUrineTestRes;

	/**
	 * 胸片
	 */
	@ApiModelProperty(value = "胸片")
	private Integer rabat;

	/**
	 * 胸片情况
	 */
	@ApiModelProperty(value = "胸片情况")
	private String rabatRes;

	/**
	 * 肺功能
	 */
	@ApiModelProperty(value = "肺功能")
	private Integer lungFunc;

	/**
	 * 肺功能情况
	 */
	@ApiModelProperty(value = "肺功能情况")
	private String lungFuncRes;

	/**
	 * ecg
	 */
	@ApiModelProperty(value = "ecg")
	private Integer ecg;

	/**
	 * ecg情况
	 */
	@ApiModelProperty(value = "ecg情况")
	private String ecgRes;

	/**
	 * 超声心电图
	 */
	@ApiModelProperty(value = "超声心电图")
	private Integer ucg;

	/**
	 * 超声心电图情况
	 */
	@ApiModelProperty(value = "超声心电图情况")
	private String ucgRes;

	/**
	 * 肝功能
	 */
	@ApiModelProperty(value = "肝功能")
	private Integer liverFunc;

	/**
	 * 肝功能
	 */
	@ApiModelProperty(value = "肝功能")
	private String liverFuncRes;

	/**
	 * 肾功能
	 */
	@ApiModelProperty(value = "肾功能")
	private Integer renalFunc;

	/**
	 * 肾功能
	 */
	@ApiModelProperty(value = "肾功能")
	private String renalFuncRes;

	/**
	 * 凝血功能
	 */
	@ApiModelProperty(value = "凝血功能")
	private Integer coagulFunc;

	/**
	 * 凝血功能
	 */
	@ApiModelProperty(value = "凝血功能")
	private String coagulFuncRes;

	/**
	 * 电解质
	 */
	@ApiModelProperty(value = "电解质")
	private Integer electrolytic;

	/**
	 * 电解质
	 */
	@ApiModelProperty(value = "电解质")
	private String electrolyticRes;

	@ApiModelProperty(value = "")
	private String other;
	
	/**
     * 肺部疾患
     */
    private String lungDisease;

    /**
     * 胸片
     */
    private String chestPic;

    /**
     * ALT
     */
    private String alt;

    /**
     * AKP
     */
    private String akp;
    
    /**
     * AST
     */
    private String ast;

    /**
     * 总胆红素
     */
    private String totalBilirubin;

    /**
     * 直接胆红素
     */
    private String directBilirubin;

    /**
     * 总蛋白
     */
    private String totalProtein;

    /**
     * 白蛋白
     */
    private String albumin;

    /**
     * 尿素氮
     */
    private String ureaNitrogen;

    /**
     * 肌酐
     */
    private String creatinine;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * Rh
     */
    private String rh;

    /**
     * PT
     */
    private String pt;

    /**
     * APTT
     */
    private String aptt;

    /**
     * WBC
     */
    private String wbc;

    /**
     * RBC
     */
    private String rbc;

    /**
     * HB
     */
    private String hb;

    /**
     * 血小板
     */
    private String platelets;

    /**
     * 钾
     */
    private String k;

    /**
     * 钠
     */
    private String na;

    /**
     * 氯
     */
    private String cl;

    /**
     * 镁
     */
    private String mg;

    /**
     * 血糖
     */
    private String bloodSugar;

	public String getLungDisease()
    {
        return lungDisease;
    }

    public void setLungDisease(String lungDisease)
    {
        this.lungDisease = lungDisease;
    }

    public String getChestPic()
    {
        return chestPic;
    }

    public void setChestPic(String chestPic)
    {
        this.chestPic = chestPic;
    }

    public String getAlt()
    {
        return alt;
    }

    public void setAlt(String alt)
    {
        this.alt = alt;
    }

    public String getAkp()
    {
        return akp;
    }

    public void setAkp(String akp)
    {
        this.akp = akp;
    }

    public String getAst()
    {
        return ast;
    }

    public void setAst(String ast)
    {
        this.ast = ast;
    }

    public String getTotalBilirubin()
    {
        return totalBilirubin;
    }

    public void setTotalBilirubin(String totalBilirubin)
    {
        this.totalBilirubin = totalBilirubin;
    }

    public String getDirectBilirubin()
    {
        return directBilirubin;
    }

    public void setDirectBilirubin(String directBilirubin)
    {
        this.directBilirubin = directBilirubin;
    }

    public String getTotalProtein()
    {
        return totalProtein;
    }

    public void setTotalProtein(String totalProtein)
    {
        this.totalProtein = totalProtein;
    }

    public String getAlbumin()
    {
        return albumin;
    }

    public void setAlbumin(String albumin)
    {
        this.albumin = albumin;
    }

    public String getUreaNitrogen()
    {
        return ureaNitrogen;
    }

    public void setUreaNitrogen(String ureaNitrogen)
    {
        this.ureaNitrogen = ureaNitrogen;
    }

    public String getCreatinine()
    {
        return creatinine;
    }

    public void setCreatinine(String creatinine)
    {
        this.creatinine = creatinine;
    }

    public String getBloodType()
    {
        return bloodType;
    }

    public void setBloodType(String bloodType)
    {
        this.bloodType = bloodType;
    }

    public String getRh()
    {
        return rh;
    }

    public void setRh(String rh)
    {
        this.rh = rh;
    }

    public String getPt()
    {
        return pt;
    }

    public void setPt(String pt)
    {
        this.pt = pt;
    }

    public String getAptt()
    {
        return aptt;
    }

    public void setAptt(String aptt)
    {
        this.aptt = aptt;
    }

    public String getWbc()
    {
        return wbc;
    }

    public void setWbc(String wbc)
    {
        this.wbc = wbc;
    }

    public String getRbc()
    {
        return rbc;
    }

    public void setRbc(String rbc)
    {
        this.rbc = rbc;
    }

    public String getHb()
    {
        return hb;
    }

    public void setHb(String hb)
    {
        this.hb = hb;
    }

    public String getPlatelets()
    {
        return platelets;
    }

    public void setPlatelets(String platelets)
    {
        this.platelets = platelets;
    }

    public String getK()
    {
        return k;
    }

    public void setK(String k)
    {
        this.k = k;
    }

    public String getNa()
    {
        return na;
    }

    public void setNa(String na)
    {
        this.na = na;
    }

    public String getCl()
    {
        return cl;
    }

    public void setCl(String cl)
    {
        this.cl = cl;
    }

    public String getMg()
    {
        return mg;
    }

    public void setMg(String mg)
    {
        this.mg = mg;
    }

    public String getBloodSugar()
    {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar)
    {
        this.bloodSugar = bloodSugar;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getPreVisitId() {
		return preVisitId;
	}

	public void setPreVisitId(String preVisitId) {
		this.preVisitId = preVisitId == null ? null : preVisitId.trim();
	}

	public String getRegOptId() {
		return regOptId;
	}

	public void setRegOptId(String regOptId) {
		this.regOptId = regOptId == null ? null : regOptId.trim();
	}

	public Integer getRoutBloodTest() {
		return routBloodTest;
	}

	public void setRoutBloodTest(Integer routBloodTest) {
		this.routBloodTest = routBloodTest;
	}

	public String getRoutBloodTestRes() {
		return routBloodTestRes;
	}

	public void setRoutBloodTestRes(String routBloodTestRes) {
		this.routBloodTestRes = routBloodTestRes == null ? null
				: routBloodTestRes.trim();
	}

	public Integer getRoutUrineTest() {
		return routUrineTest;
	}

	public void setRoutUrineTest(Integer routUrineTest) {
		this.routUrineTest = routUrineTest;
	}

	public String getRoutUrineTestRes() {
		return routUrineTestRes;
	}

	public void setRoutUrineTestRes(String routUrineTestRes) {
		this.routUrineTestRes = routUrineTestRes == null ? null
				: routUrineTestRes.trim();
	}

	public Integer getRabat() {
		return rabat;
	}

	public void setRabat(Integer rabat) {
		this.rabat = rabat;
	}

	public String getRabatRes() {
		return rabatRes;
	}

	public void setRabatRes(String rabatRes) {
		this.rabatRes = rabatRes == null ? null : rabatRes.trim();
	}

	public Integer getLungFunc() {
		return lungFunc;
	}

	public void setLungFunc(Integer lungFunc) {
		this.lungFunc = lungFunc;
	}

	public String getLungFuncRes() {
		return lungFuncRes;
	}

	public void setLungFuncRes(String lungFuncRes) {
		this.lungFuncRes = lungFuncRes == null ? null : lungFuncRes.trim();
	}

	public Integer getEcg() {
		return ecg;
	}

	public void setEcg(Integer ecg) {
		this.ecg = ecg;
	}

	public String getEcgRes() {
		return ecgRes;
	}

	public void setEcgRes(String ecgRes) {
		this.ecgRes = ecgRes == null ? null : ecgRes.trim();
	}

	public Integer getUcg() {
		return ucg;
	}

	public void setUcg(Integer ucg) {
		this.ucg = ucg;
	}

	public String getUcgRes() {
		return ucgRes;
	}

	public void setUcgRes(String ucgRes) {
		this.ucgRes = ucgRes == null ? null : ucgRes.trim();
	}

	public Integer getLiverFunc() {
		return liverFunc;
	}

	public void setLiverFunc(Integer liverFunc) {
		this.liverFunc = liverFunc;
	}

	public String getLiverFuncRes() {
		return liverFuncRes;
	}

	public void setLiverFuncRes(String liverFuncRes) {
		this.liverFuncRes = liverFuncRes == null ? null : liverFuncRes.trim();
	}

	public Integer getRenalFunc() {
		return renalFunc;
	}

	public void setRenalFunc(Integer renalFunc) {
		this.renalFunc = renalFunc;
	}

	public String getRenalFuncRes() {
		return renalFuncRes;
	}

	public void setRenalFuncRes(String renalFuncRes) {
		this.renalFuncRes = renalFuncRes == null ? null : renalFuncRes.trim();
	}

	public Integer getCoagulFunc() {
		return coagulFunc;
	}

	public void setCoagulFunc(Integer coagulFunc) {
		this.coagulFunc = coagulFunc;
	}

	public String getCoagulFuncRes() {
		return coagulFuncRes;
	}

	public void setCoagulFuncRes(String coagulFuncRes) {
		this.coagulFuncRes = coagulFuncRes == null ? null : coagulFuncRes
				.trim();
	}

	public Integer getElectrolytic() {
		return electrolytic;
	}

	public void setElectrolytic(Integer electrolytic) {
		this.electrolytic = electrolytic;
	}

	public String getElectrolyticRes() {
		return electrolyticRes;
	}

	public void setElectrolyticRes(String electrolyticRes) {
		this.electrolyticRes = electrolyticRes == null ? null : electrolyticRes
				.trim();
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other == null ? null : other.trim();
	}
}