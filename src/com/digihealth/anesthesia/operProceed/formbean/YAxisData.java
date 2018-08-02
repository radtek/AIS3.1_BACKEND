package com.digihealth.anesthesia.operProceed.formbean;

public class YAxisData implements Comparable<YAxisData>{
	private String name;
	private String type;
	private Float max;
	private Float min;
	private Integer order ;  //该字段用于排序

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getMax() {
		return max;
	}

	public void setMax(Float max) {
		this.max = max;
	}

	public Float getMin() {
		return min;
	}

	public void setMin(Float min) {
		this.min = min;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public int compareTo(YAxisData o) {
		return this.getOrder().compareTo(o.getOrder());
	}
	
	

}
