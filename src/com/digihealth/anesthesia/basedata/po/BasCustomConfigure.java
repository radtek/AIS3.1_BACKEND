/*
 * BasCustomConfigure.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-12-08 Created
 */
package com.digihealth.anesthesia.basedata.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "麻醉记录单自定义配置基础表")
public class BasCustomConfigure {
    /**
     * 配置表主键
     */
	@ApiModelProperty(value = "配置表主键")
    private String configureId;

    /**
     * 所属模块
     */
	@ApiModelProperty(value = "所属模块")
    private String modularType;

    /**
     * 配置模块名称
     */
	@ApiModelProperty(value = "配置模块名称")
    private String modularName;

    /**
     * 基线id
     */
	@ApiModelProperty(value = "基线id")
    private String beid;

    /**
     * 是否可用：0-不可用,1-可用
     */
	@ApiModelProperty(value = "是否可用")
    private Integer enable;

    /**
     * 配置值
     */
	@ApiModelProperty(value = "配置值")
    private String configureValue;

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId == null ? null : configureId.trim();
    }

    public String getModularType() {
        return modularType;
    }

    public void setModularType(String modularType) {
        this.modularType = modularType == null ? null : modularType.trim();
    }

    public String getModularName() {
        return modularName;
    }

    public void setModularName(String modularName) {
        this.modularName = modularName == null ? null : modularName.trim();
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid == null ? null : beid.trim();
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getConfigureValue() {
        return configureValue;
    }

    public void setConfigureValue(String configureValue) {
        this.configureValue = configureValue == null ? null : configureValue.trim();
    }
}