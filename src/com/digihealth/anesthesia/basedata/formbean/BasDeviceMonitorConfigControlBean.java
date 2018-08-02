package com.digihealth.anesthesia.basedata.formbean;

import java.io.Serializable;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "设备与监测项配置参数对象")
public class BasDeviceMonitorConfigControlBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "设备id")
	private String deviceId;

	@ApiModelProperty(value = "eventId")
	private String eventId;

	@ApiModelProperty(value = "M:必须勾选，O:勾选，null代表没有勾选")
	private String optional;

	@ApiModelProperty(value = "手术间id")
	private String roomId;

	@ApiModelProperty(value = "eventName")
	private String eventName;

	@ApiModelProperty(value = "eventDesc")
	private String eventDesc;	

    @ApiModelProperty(value="基线id")
    private String beid;

    @ApiModelProperty(value="偏差")
    private Integer precision;

    @ApiModelProperty(value="频率")
    private Integer frequency;


    @ApiModelProperty(value="最大值")
    private Float max;

    @ApiModelProperty(value="最小值")
    private Float min;

    @ApiModelProperty(value="修正")
    private Integer amendFlag;


    @ApiModelProperty(value="颜色")
    private String color;


    @ApiModelProperty(value="图标")
    private String iconId;


    @ApiModelProperty(value="必须显示;1-必须展示，null非必需展示")
    private String mustShow;

    @ApiModelProperty(value="0:描点；1：数字，-1实时")
    private Integer position;

    @ApiModelProperty(value="单位")
    private String units;
    
    @ApiModelProperty(value="参数ID")
    private String paraId;
    
    @ApiModelProperty(value="参数名称")
    private String paraName;
	
    @ApiModelProperty(value="图标宽高")
    private String widthAndHeight;

    
	public String getWidthAndHeight() {
		return widthAndHeight;
	}

	public void setWidthAndHeight(String widthAndHeight) {
		this.widthAndHeight = widthAndHeight;
	}
    
    
	public String getParaId() {
		return paraId;
	}

	public void setParaId(String paraId) {
		this.paraId = paraId;
	}

	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public String getOptional() {
		return optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
	}

	public String getBeid() {
		return beid;
	}

	public void setBeid(String beid) {
		this.beid = beid;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
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

	public Integer getAmendFlag() {
		return amendFlag;
	}

	public void setAmendFlag(Integer amendFlag) {
		this.amendFlag = amendFlag;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIconId() {
		return iconId;
	}

	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	public String getMustShow() {
		return mustShow;
	}

	public void setMustShow(String mustShow) {
		this.mustShow = mustShow;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
	
	
}