package com.digihealth.anesthesia.basedata.controller;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.digihealth.anesthesia.basedata.formbean.ApplyMonitorFormBean;
import com.digihealth.anesthesia.basedata.formbean.BasDeviceMonitorConfigControlBean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasDeviceMonitorConfig;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfig;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * Title: BasDeviceMonitorConfigController.java Description:监测点设置Controller
 * 
 * @author liukui
 * @created 2015-9-15 下午1:56:40
 */
@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasDeviceMonitorConfigController", description = "床旁设备配置处理类")
public class BasDeviceMonitorConfigController extends BaseController
{
    @RequestMapping(value = "/getDeviceMonitorConfigListByBeidDeviceId")
    @ResponseBody
    @ApiOperation(value = "床旁设备对应采集项配置", httpMethod = "POST", notes = "床旁设备对应采集项配置")
    public String getDeviceMonitorConfigListByBeidDeviceId(@ApiParam(name = "BasDeviceMonitorConfig", value = "床旁设备对应采集项") @RequestBody BasDeviceMonitorConfig basDeviceMonitorConfig)
    {
        logger.info("begin getDeviceMonitorConfigListByBeidDeviceId");
        ResponseValue resp = new ResponseValue();
        List<BasDeviceMonitorConfigControlBean> resultList = basDeviceMonitorConfigService.getControlRoomDeviceMonitorConfigByBeid(basDeviceMonitorConfig.getBeid(),basDeviceMonitorConfig.getDeviceId(),basDeviceMonitorConfig.getRoomId());
        resp.put("resultList", resultList);
        logger.info("end getDeviceMonitorConfigListByBeidDeviceId");
        return resp.getJsonStr();
    }
    
    /**
     * 运营中心新增局点时，配置局点对应的设备信息
     * 
     * @param BasDeviceConfigOperateFormBean
     * @return
     */
    @RequestMapping(value = "/saveBasDeviceMonitorConfig")
    @ResponseBody
    @ApiOperation(value = "配置局点对应的设备信息", httpMethod = "POST", notes = "配置局点对应的设备信息")
    public String saveBasDeviceMonitorConfig(@ApiParam(name = "BasDeviceMonitorConfig", value = "配置局点对应的设备信息") @RequestBody BasDeviceMonitorConfig basDeviceMonitorConfig)
    {
        logger.info("begin saveBasDeviceMonitorConfig");
        ResponseValue resp = new ResponseValue();
        basDeviceMonitorConfigService.updateDeviceMonitorConfig(basDeviceMonitorConfig);
        logger.info("end saveBasDeviceMonitorConfig");
        return resp.getJsonStr();
    }
    
    /**
     * 运营中心新增局点时，配置局点对应的额设备信息
     * 
     * @param BasDeviceConfigOperateFormBean
     * @return
     */
    @RequestMapping(value = "/saveBasMonitorConfig")
    @ResponseBody
    @ApiOperation(value = "配置采集指标信息", httpMethod = "POST", notes = "配置采集指标信息")
    public String saveBasMonitorConfig(@ApiParam(name = "BasMonitorConfig", value = "配置采集指标信息") @RequestBody BasMonitorConfig basMonitorConfig)
    {
        logger.info("begin saveBasMonitorConfig");
        ResponseValue resp = new ResponseValue();
        basMonitorConfigService.batUpdateMonitorConfig(basMonitorConfig);
        logger.info("end saveBasMonitorConfig");
        return resp.getJsonStr();
    }
    
    /**
     * 运营中心新增采集指标
     * 
     * @param BasDeviceConfigOperateFormBean
     * @return
     */
    @RequestMapping(value = "/insertBasMonitorConfig")
    @ResponseBody
    @ApiOperation(value = "新增采集指标", httpMethod = "POST", notes = "新增采集指标")
    public String insertBasMonitorConfig(@ApiParam(name = "BasMonitorConfig", value = "新增采集指标") @RequestBody BasMonitorConfig basMonitorConfig)
    {
        logger.info("begin insertBasMonitorConfig");
        ResponseValue resp = new ResponseValue();
        basMonitorConfigService.insertBasMonitorConfig(basMonitorConfig);
        logger.info("end insertBasMonitorConfig");
        return resp.getJsonStr();
    }
    
    
    
    /**
     * 运营中心查询所有基础采集指标
     * 
     * @param 
     * @return
     */
    @RequestMapping(value = "/queryBasMonitorConfigList")
    @ResponseBody
    @ApiOperation(value = "查询所有基础采集指标", httpMethod = "POST", notes = "查询所有基础采集指标")
    public String queryBasMonitorConfigList(@ApiParam(name = "SystemSearchFormBean", value = "新增采集指标") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryBasMonitorConfigList");
        ResponseValue resp = new ResponseValue();
        resp.put("resultList", basMonitorConfigService.queryBasMonitorConfigList(systemSearchFormBean));
        resp.put("total", basMonitorConfigService.queryBasMonitorConfigListTotal(systemSearchFormBean));
        logger.info("end queryBasMonitorConfigList");
        return resp.getJsonStr();
    }
    
    
    /**
     * 运营中心设备对应采集指标绑定
     * 
     * @param BasDeviceConfigOperateFormBean
     * @return
     */
    @RequestMapping(value = "/bindDeviceMonitorConfig")
    @ResponseBody
    @ApiOperation(value = "配置局点对应的设备信息", httpMethod = "POST", notes = "配置局点对应的设备信息")
    public String bindDeviceMonitorConfig(@ApiParam(name = "BasDeviceMonitorConfig", value = "配置局点对应的设备信息") @RequestBody Map map)
    {
        logger.info("begin bindDeviceMonitorConfig");
        ResponseValue resp = new ResponseValue();
        basDeviceMonitorConfigService.bindDeviceMonitorConfig(map);
        logger.info("end bindDeviceMonitorConfig");
        return resp.getJsonStr();
    }
    
    /**
     * 运营中心添加采集指标，绑定设备时，查询出所有已使用该设备且未绑定采集指标的局点列表
     * 
     * @param 
     * @return
     */
    @RequestMapping(value = "/queryBusEntityListByDeviceIdAndEventId")
    @ResponseBody
    @ApiOperation(value = "查询所有局点列表", httpMethod = "POST", notes = "查询所有局点列表")
    public String queryBusEntityListByDeviceIdAndEventId(@ApiParam(name = "BasDeviceMonitorConfig", value = "查询所有局点列表") @RequestBody BasDeviceMonitorConfig record)
    {
        logger.info("begin queryBusEntityListByDeviceIdAndEventId");
        ResponseValue resp = new ResponseValue();
        resp.put("resultList", basDeviceMonitorConfigService.queryBusEntityListByDeviceIdAndEventId(record));
        logger.info("end queryBusEntityListByDeviceIdAndEventId");
        return resp.getJsonStr();
    }
    
    
    /**
     * 运营中心新增采集指标应用到局点、手术室等
     * 
     * @param BasDeviceConfigOperateFormBean
     * @return
     */
    @RequestMapping(value = "/applyMonitorByBeid")
    @ResponseBody
    @ApiOperation(value = "新增采集指标应用到局点、手术室", httpMethod = "POST", notes = "新增采集指标应用到局点、手术室")
    public String applyMonitorByBeid(@ApiParam(name = "BasDeviceMonitorConfig", value = "新增采集指标应用到局点、手术室") @RequestBody ApplyMonitorFormBean applyMonitorFormBean)
    {
        logger.info("begin applyMonitorByBeid");
        ResponseValue resp = new ResponseValue();
        basDeviceMonitorConfigService.applyMonitorByBeid(applyMonitorFormBean);
        logger.info("end applyMonitorByBeid");
        return resp.getJsonStr();
    }
    
    
    
}
