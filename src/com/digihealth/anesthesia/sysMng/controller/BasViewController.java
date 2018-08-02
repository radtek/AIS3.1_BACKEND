package com.digihealth.anesthesia.sysMng.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.sysMng.formbean.SearchViewFormBean;
import com.digihealth.anesthesia.sysMng.po.BasEntity;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/sys")
@Api(value="BasViewController",description="视图处理类")
public class BasViewController extends BaseController
{
    @RequestMapping(value = "/selectAllView")
    @ResponseBody
    @ApiOperation(value="查询出所有视图以及视图中的所有列",httpMethod="POST",notes="查询出所有视图以及视图中的所有列")
    public String selectAllView()
    {
        logger.info("begin selectAllView");
        ResponseValue res = new ResponseValue();
        List<Map<String, Object>> viewList = basViewService.selectAllView();
        res.put("resultList", viewList);
        logger.info("end selectAllView");
        return res.getJsonStr();
    }
    
    @RequestMapping(value = "/selectAllColumnsOfView")
    @ResponseBody
    @ApiOperation(value="查询出所有视图以及视图中的所有列",httpMethod="POST",notes="查询出所有视图以及视图中的所有列")
    public String selectAllColumnsOfView(@ApiParam(name="formBean", value ="查询参数") @RequestBody SearchViewFormBean formBean)
    {
        logger.info("begin selectAllColumnsOfView");
        ResponseValue res = new ResponseValue();
        //List<Map<String,String>> columnList = basViewService.selectAllColumnsOfView(formBean.getViewName());
        List<BasEntity> columnList = basViewService.selectAllColumnsOfView(formBean.getViewName());
        res.put("resultList", columnList);
        logger.info("end selectAllColumnsOfView");
        return res.getJsonStr();
    }
    
    @RequestMapping(value = "/selectByColumns")
    @ResponseBody
    @ApiOperation(value="查询出指定列对应的数据",httpMethod="POST",notes="查询出指定列对应的数据")
    public String selectByColumns(@ApiParam(name="formBean", value ="查询参数") @RequestBody SearchViewFormBean formBean)
    {
        logger.info("begin selectByColumns");
        ResponseValue res = new ResponseValue();
        if (null != formBean && null != formBean.getColumnList() && formBean.getColumnList().size() > 0)
        {
            List<Map<String, Object>> resultList = basViewService.selectByColumns(formBean);
            int total = basViewService.selectByColumnsTotal(formBean);
            res.put("resultList", resultList);
            res.put("total", total);
        }
        logger.info("end selectByColumns");
        return res.getJsonStr();
    }
}
