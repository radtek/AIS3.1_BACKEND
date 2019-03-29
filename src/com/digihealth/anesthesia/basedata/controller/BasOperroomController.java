package com.digihealth.anesthesia.basedata.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.OperRoomFormBean;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasOperroom;
import com.digihealth.anesthesia.basedata.utils.CustomConfigureUtil;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * Title: AdminUserController.java Description: 手术室Controller
 * 
 * @author liukui
 * @created 2015-9-15 下午1:56:40
 */
@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasOperroomController", description = "手术室信息处理类")
public class BasOperroomController extends BaseController
{
    
    @RequestMapping(value = "/getOperroomList")
    @ResponseBody
    @ApiOperation(value = "查询手术室信息列表", httpMethod = "POST", notes = "查询手术室信息列表")
    public String getOperroomList(@ApiParam(name = "baseQuery", value = "系统查询参数") @RequestBody BaseInfoQuery baseQuery)
    {
        ResponseValue resp = new ResponseValue();
        logger.info("begin getOperroomList");
        List<OperRoomFormBean> resultList = basOperroomService.findList(baseQuery);
        resp.put("resultList", resultList);
        // 获取台次码表
        List<SysCodeFormbean> pcss = basSysCodeService.searchSysCodeByGroupId("pcs_type", baseQuery.getBeid());
        resp.put("pcss", pcss);
        logger.info("end getOperroomList");
        return resp.getJsonStr();
    }
    
    /**
     * 分页查询手术室
     * 
     * @param systemSearchFormBean
     * @return
     */
    @RequestMapping(value = "/queryRoomList")
    @ResponseBody
    @ApiOperation(value = "分页查询手术室", httpMethod = "POST", notes = "分页查询手术室")
    public String queryRoomList(@ApiParam(name = "systemSearchFormBean", value = "系统查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryRoomList");
        ResponseValue resp = new ResponseValue();
        List<BasOperroom> resultList = basOperroomService.queryRoomList(systemSearchFormBean);
        int total = basOperroomService.queryRoomListTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryRoomList");
        return resp.getJsonStr();
    }
    
    /**
     * 根据id获取手术
     * 
     * @param operroom
     * @return
     */
    @RequestMapping(value = "/queryRoomListById")
    @ResponseBody
    @ApiOperation(value = "根据id获取手术", httpMethod = "POST", notes = "根据id获取手术")
    public String queryRoomListById(@ApiParam(name = "operroom", value = "手术室信息对象") @RequestBody BasOperroom operroom)
    {
        logger.info("begin queryRoomListById");
        ResponseValue resp = new ResponseValue();
        BasOperroom obj = basOperroomService.selectEntityByPrimaryKey(operroom.getOperRoomId());
        resp.put("basOperroom", obj);
        logger.info("end queryRoomListById");
        return resp.getJsonStr();
    }
    
    /**
     * 保存手术室配置
     * 
     * @param operroom
     * @return
     */
    @RequestMapping(value = "/saveOperroom")
    @ResponseBody
    @ApiOperation(value = "保存手术室配置", httpMethod = "POST", notes = "保存手术室配置")
    public String saveOperroom(@ApiParam(name = "operroom", value = "手术室信息对象") @RequestBody BasOperroom operroom)
    {
        logger.info("begin saveOperroom");
        ResponseValue resp = new ResponseValue();
        if(CustomConfigureUtil.isSync()){
        	String roomId = basOperroomService.getRoomIdByIp(operroom.getRemotehost(),getBeid());
        	 if(StringUtils.isBlank(operroom.getOperRoomId())){
        		 if(!"0".equals(roomId)){
             		resp.setResultCode("100000001");
             		resp.setResultMessage("新建手术室配置时,IP地址已经存在其他手术室，请核对!!");
             		return resp.getJsonStr();
             	}
        	 }else{
        		 if(!operroom.getOperRoomId().equals(roomId) && !"0".equals(roomId)){
              		resp.setResultCode("100000001");
              		resp.setResultMessage("保存手术室配置时,IP地址已经存在其他手术室，请核对!!");
              		return resp.getJsonStr();
              	} 
        	 }
        }
        basOperroomService.saveOperroom(operroom);
        logger.info("end saveOperroom");
        return resp.getJsonStr();
    }
    
    /**
     * 保存手术室配置
     * 
     * @param operroom
     * @return
     */
    @RequestMapping(value = "/saveOperroomQNZZY")
    @ResponseBody
    @ApiOperation(value = "保存手术室配置", httpMethod = "POST", notes = "保存手术室配置")
    public String saveOperroomQNZZY(@ApiParam(name = "operroom", value = "手术室信息对象") @RequestBody BasOperroom operroom)
    {
        logger.info("begin saveOperroom");
        ResponseValue resp = new ResponseValue();
        if(CustomConfigureUtil.isSync()){
            String roomId = basOperroomService.getRoomIdByIp(operroom.getRemotehost(),getBeid());
             if(StringUtils.isBlank(operroom.getOperRoomId())){
                 if(!"0".equals(roomId)){
                    resp.setResultCode("100000001");
                    resp.setResultMessage("新建手术室配置时,IP地址已经存在其他手术室，请核对!!");
                    return resp.getJsonStr();
                }
             }else{
                 if(!operroom.getOperRoomId().equals(roomId) && !"0".equals(roomId)){
                    resp.setResultCode("100000001");
                    resp.setResultMessage("保存手术室配置时,IP地址已经存在其他手术室，请核对!!");
                    return resp.getJsonStr();
                } 
             }
        }
        basOperroomService.saveOperroomQNZZY(operroom);
        logger.info("end saveOperroom");
        return resp.getJsonStr();
    }
    
    /**
     * 修改卫生护士
     * 
     * @param operroom
     * @return
     */
    @RequestMapping(value = "/updateHealthnurse")
    @ResponseBody
    @ApiOperation(value = "修改卫生护士", httpMethod = "POST", notes = "修改卫生护士")
    public String updateHealthnurse(@ApiParam(name = "operroom", value = "手术室信息对象") @RequestBody BasOperroom operroom)
    {
        logger.info("begin updateHealthnurse");
        ResponseValue resp = new ResponseValue();
        basOperroomService.updateHealthnurse(operroom.getOperRoomId(), operroom.getHealthNurse(),operroom.getBeid());
        logger.info("end updateHealthnurse");
        return resp.getJsonStr();
    }
}
