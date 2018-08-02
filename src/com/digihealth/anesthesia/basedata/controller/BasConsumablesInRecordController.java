package com.digihealth.anesthesia.basedata.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasConsumablesInRecord;
import com.digihealth.anesthesia.basedata.po.BasConsumablesInRecord;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasConsumablesInRecordController", description = "耗材入库处理类")
public class BasConsumablesInRecordController extends BaseController
{

	/**
     * 
     * @discription 根据条件查询耗材入库记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param systemSearchFormBean
     * @return
     */
    @RequestMapping(value = "/queryConsumablesInRecordList")
    @ResponseBody
    @ApiOperation(value = "根据条件查询耗材入库记录", httpMethod = "POST", notes = "根据条件查询耗材入库记录")
    public String queryConsumablesInRecordList(
        @ApiParam(name = "systemSearchFormBean", value = "系统查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryConsumablesInRecordList");
        ResponseValue resp = new ResponseValue();
        List<BasConsumablesInRecord> resultList = basConsumablesInRecordService.queryConsumablesInRecordList(systemSearchFormBean);
        int total = basConsumablesInRecordService.queryConsumablesInRecordListTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryConsumablesInRecordList");
        return resp.getJsonStr();
    }
    
	/**
     * 
     * @discription 存储耗材入库记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param basConsumablesInRecord
     * @return
     */
    @RequestMapping(value = "/saveConsumablesInRecord")
    @ResponseBody
    @ApiOperation(value = "存储耗材入库记录", httpMethod = "POST", notes = "没有传了id是新增，传了是修改")
    public String saveConsumablesInRecord(
        @ApiParam(name = "basConsumablesInRecord", value = "耗材入库对象") @RequestBody BasConsumablesInRecord basConsumablesInRecord)
    {
    	logger.info("begin saveConsumablesInRecord");
        ResponseValue resp = new ResponseValue();
        basConsumablesInRecordService.saveConsumablesInRecord(basConsumablesInRecord,resp);
        logger.info("end saveConsumablesInRecord");
        return resp.getJsonStr();
    }
    
	/**
     * 
     * @discription 审核耗材入库记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param basConsumablesInRecord
     * @return
     */
    @RequestMapping(value = "/checkConsumablesInRecord")
    @ResponseBody
    @ApiOperation(value = "审核耗材入库记录", httpMethod = "POST", notes = "没有传了id是新增，传了是修改")
    public String checkConsumablesInRecord(
        @ApiParam(name = "basConsumablesInRecord", value = "耗材入库对象") @RequestBody BasConsumablesInRecord basConsumablesInRecord)
    {
    	logger.info("begin checkConsumablesInRecord");
        ResponseValue resp = new ResponseValue();
        if(null == basConsumablesInRecord || null == basConsumablesInRecord.getId()
        		|| StringUtils.isBlank(basConsumablesInRecord.getCheckName()))
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("核对记录和核对者不能为空");
        }else
        {
        	Integer id = basConsumablesInRecord.getId();
        	BasConsumablesInRecord selectBasConsumablesInRecord = basConsumablesInRecordService.selectById(id);
        	//状态必须是未审核的才可以，不然会导致重复审核
        	if(selectBasConsumablesInRecord.getStatus() == 0)
        	{
        		//改变状态
            	basConsumablesInRecordService.checkConsumablesInRecord(basConsumablesInRecord);
            	//存入耗材库
            	basConsumablesStorageService.saveToConsumablesStorage(selectBasConsumablesInRecord);
        	}else
        	{
        		resp.setResultCode("10000002");
                resp.setResultMessage("这条记录已经核对入库了，不能重复操作");
        	}
        }
        logger.info("end checkConsumablesInRecord");
        return resp.getJsonStr();
    }
    
    /**
     * 
     * @discription 删除耗材入库记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param systemSearchFormBean
     * @return
     */
    @RequestMapping(value = "/delConsumablesInRecord")
    @ResponseBody
    @ApiOperation(value = "删除耗材入库记录", httpMethod = "POST", notes = "没有传了id是新增，传了是修改")
    public String delConsumablesInRecord(
        @ApiParam(name = "map", value = "要删除的耗材入库记录id必传") @RequestBody Map<String,Object> map)
    {
    	logger.info("begin delConsumablesInRecord");
        ResponseValue resp = new ResponseValue();
        if(null == map || null == map.get("id"))
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("要删除的耗材入库记录id必传");
        }else
        {
        	Integer id = Integer.parseInt(map.get("id").toString());
        	basConsumablesInRecordService.delConsumablesInRecord(id);
        }
        
        logger.info("end delConsumablesInRecord");
        return resp.getJsonStr();
    }
    
}
