package com.digihealth.anesthesia.sysMng.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.sysMng.po.BasTaskSchedule;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
@Controller
@RequestMapping(value = "/sys")
@Api(value = "BasTaskScheduleController", description = "定时任务处理类")
public class BasTaskScheduleController extends BaseController
{
    @RequestMapping(value = "/searchAllTaskSchedule")
    @ResponseBody
    @ApiOperation(value = "查询所有定时任务", httpMethod = "POST", notes = "查询所有定时任务")
    public String searchAllTaskSchedule()
    {
        logger.info("--------------start searchAllTaskSchedule---------------------------");
        ResponseValue resp = new ResponseValue();
        List<BasTaskSchedule> taskSchedules = basTaskScheduleService.searchAllTaskSchedule();
        resp.put("resultList", taskSchedules);
        logger.info("--------------end searchAllTaskSchedule---------------------------");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/updateTaskSchedule")
    @ResponseBody
    @ApiOperation(value = "更新定时任务", httpMethod = "POST", notes = "更新定时任务")
    public String updateTaskSchedule(@ApiParam(name = "taskSchedule", value = "定时任务对象") @RequestBody BasTaskSchedule taskSchedule)
    {
        logger.info("--------------start updateTaskSchedule---------------------------");
        ResponseValue resp = new ResponseValue();
        basTaskScheduleService.updateTaskSchedule(taskSchedule, resp);
        logger.info("--------------end updateTaskSchedule---------------------------");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/deleteTaskSchedule")
    @ResponseBody
    @ApiOperation(value = "删除定时任务", httpMethod = "POST", notes = "删除定时任务")
    public String deleteTaskSchedule(@ApiParam(name = "map", value = "删除对象") @RequestBody Map map)
    {
        logger.info("--------------start deleteTaskSchedule---------------------------");
        ResponseValue resp = new ResponseValue();
        basTaskScheduleService.deleteTaskSchedule((String)map.get("id"));
        logger.info("--------------end deleteTaskSchedule---------------------------");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/searchTaskScheduleById")
    @ResponseBody
    @ApiOperation(value = "根据id查找定时任务", httpMethod = "POST", notes = "根据id查找定时任务")
    public String searchTaskScheduleById(@ApiParam(name = "map", value = "查找对象") @RequestBody Map map)
    {
        logger.info("--------------start searchTaskScheduleById---------------------------");
        ResponseValue resp = new ResponseValue();
        BasTaskSchedule taskSchedule = basTaskScheduleService.searchTaskScheduleById((String)map.get("id"));
        resp.put("taskSchedule", taskSchedule);
        logger.info("--------------end searchTaskScheduleById---------------------------");
        return resp.getJsonStr();
    }
}
