package com.digihealth.anesthesia.evt.formbean;

import java.util.List;

public class RegOptOperMedicaleventFormBean {
	private String code;
	private String name;
	private String thickness;
	private String thicknessUnit;
	private float dosage;
	private String dosageUnit;
	private List<SearchOptOperMedicalevent> medicalEventList;
	private String durable; // 是否是持续性 1持续,0不持续,2:TCI
	private String medEventId;
	
	private Float packageDosageAmount;
    private Float amout; //金额
    private String unit; //单位
    private Float quantity; //数量
    private Float priceMinPackage;
    private String medWay; //用药方式，红白处方用该字段
    private String spec; //规格
    private String proportion; //比重
    private Integer showMedWay;  //是否显示用药方式

    public Integer getShowMedWay()
    {
        return showMedWay;
    }

    public void setShowMedWay(Integer showMedWay)
    {
        this.showMedWay = showMedWay;
    }

    public String getProportion()
    {
        return proportion;
    }

    public void setProportion(String proportion)
    {
        this.proportion = proportion;
    }

    public String getMedWay()
    {
        return medWay;
    }

    public void setMedWay(String medWay)
    {
        this.medWay = medWay;
    }

    public String getSpec()
    {
        return spec;
    }

    public void setSpec(String spec)
    {
        this.spec = spec;
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

	public String getMedEventId()
    {
        return medEventId;
    }

    public void setMedEventId(String medEventId)
    {
        this.medEventId = medEventId;
    }

    public String getDosageUnit() {
		return dosageUnit;
	}

	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}

	public float getDosage() {
		return dosage;
	}

	public void setDosage(float dosage) {
		this.dosage = dosage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThickness() {
		return thickness;
	}

	public void setThickness(String thickness) {
		this.thickness = thickness;
	}

	public String getThicknessUnit() {
		return thicknessUnit;
	}

	public void setThicknessUnit(String thicknessUnit) {
		this.thicknessUnit = thicknessUnit;
	}

	public List<SearchOptOperMedicalevent> getMedicalEventList() {
		return medicalEventList;
	}

	public void setMedicalEventList(List<SearchOptOperMedicalevent> medicalEventList) {
		this.medicalEventList = medicalEventList;
	}

	public String getDurable() {
		return durable;
	}

	public void setDurable(String durable) {
		this.durable = durable;
	}

}