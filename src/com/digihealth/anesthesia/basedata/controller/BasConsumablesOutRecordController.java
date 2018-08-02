package com.digihealth.anesthesia.basedata.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.BasMedicineRegOptFormBean;
import com.digihealth.anesthesia.basedata.formbean.BatAddConsumablesOutRecordFormbean;
import com.digihealth.anesthesia.basedata.formbean.BatAddConsumablesRetreatFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasConsumablesLoseRecord;
import com.digihealth.anesthesia.basedata.po.BasConsumablesOutRecord;
import com.digihealth.anesthesia.basedata.po.BasConsumablesRetreatRecord;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasConsumablesOutRecordController", description = "耗材取药处理类")
public class BasConsumablesOutRecordController extends BaseController
{
	/**
     * 
     * @discription 添加耗材取药记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param basConsumablesInRecord
     * @return
     */
    @RequestMapping(value = "/addConsumablesOutRecord")
    @ResponseBody
    @ApiOperation(value = "添加耗材取药记录", httpMethod = "POST", notes = "添加耗材取药记录")
    public String addConsumablesOutRecord(
        @ApiParam(name = "basConsumablesInRecord", value = "耗材入库对象") @RequestBody BasConsumablesOutRecord basConsumablesOutRecord)
    {
    	logger.info("begin addConsumablesOutRecord");
        ResponseValue resp = new ResponseValue();
        if(null == basConsumablesOutRecord)
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("要保存的记录不能为空。");
        }else
        {
        	basConsumablesOutRecordService.addConsumablesOutRecord(basConsumablesOutRecord,resp);
        }
        logger.info("end addConsumablesOutRecord");
        return resp.getJsonStr();
    }
    
    
	/**
     * 
     * @discription 批量添加耗材取药记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param basConsumablesInRecord
     * @return
     */
    @RequestMapping(value = "/batAddConsumablesOutRecord")
    @ResponseBody
    @ApiOperation(value = "添加耗材取药记录", httpMethod = "POST", notes = "添加耗材取药记录")
    public String batAddConsumablesOutRecord(
        @ApiParam(name = "batAddConsumablesOutRecord", value = "耗材入库对象") @RequestBody BatAddConsumablesOutRecordFormbean basConsumablesOutRecord)
    {
    	logger.info("begin batAddConsumablesOutRecord");
        ResponseValue resp = new ResponseValue();
        if(null == basConsumablesOutRecord)
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("要保存的记录不能为空。");
        }else
        {
        	try {
        		basConsumablesOutRecordService.batAddConsumablesOutRecord(basConsumablesOutRecord,resp);
			} catch (Exception e) {
				return resp.getJsonStr();
			}
        }
        logger.info("end batAddConsumablesOutRecord");
        return resp.getJsonStr();
    }
    
    
    
	/**
     * 
     * @discription 根据条件查询耗材取药记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param systemSearchFormBean
     * @return
     */
    @RequestMapping(value = "/queryConsumablesOutRecordList")
    @ResponseBody
    @ApiOperation(value = "根据条件查询耗材取药记录", httpMethod = "POST", notes = "根据条件查询耗材取药记录")
    public String queryConsumablesOutRecordList(
        @ApiParam(name = "systemSearchFormBean", value = "系统查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryConsumablesOutRecordList");
        ResponseValue resp = new ResponseValue();
        List<BasConsumablesOutRecord> resultList = basConsumablesOutRecordService.queryConsumablesOutRecordList(systemSearchFormBean);
        int total = basConsumablesOutRecordService.queryConsumablesOutRecordListTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryConsumablesOutRecordList");
        return resp.getJsonStr();
    }
    
    
	/**
     * 
     * @discription 添加耗材退药记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param basConsumablesRetreatRecord
     * @return
     */
    @RequestMapping(value = "/addConsumablesRetreatRecord")
    @ResponseBody
    @ApiOperation(value = "添加耗材退药记录", httpMethod = "POST", notes = "添加耗材退药记录")
    public String addConsumablesRetreatRecord(
        @ApiParam(name = "basConsumablesRetreatRecord", value = "耗材退药记录") @RequestBody BasConsumablesRetreatRecord basConsumablesRetreatRecord)
    {
    	logger.info("begin addConsumablesRetreatRecord");
        ResponseValue resp = new ResponseValue();
        if(null == basConsumablesRetreatRecord)
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("要保存的记录不能为空。");
        }else
        {
        	basConsumablesOutRecordService.addConsumablesRetreatRecord(basConsumablesRetreatRecord, resp);
        }
        logger.info("end addConsumablesRetreatRecord");
        return resp.getJsonStr();
    }
    
    
	/**
     * 
     * @discription 添加耗材退药记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param basConsumablesRetreatRecord
     * @return
     */
    @RequestMapping(value = "/batAddConsumablesRetreatRecord")
    @ResponseBody
    @ApiOperation(value = "添加耗材退药记录", httpMethod = "POST", notes = "添加耗材退药记录")
    public String batAddConsumablesRetreatRecord(
        @ApiParam(name = "batAddConsumablesRetreatRecord", value = "耗材退药记录") @RequestBody BatAddConsumablesRetreatFormbean retreatRecord)
    {
    	logger.info("begin batAddConsumablesRetreatRecord");
        ResponseValue resp = new ResponseValue();
        if(null == retreatRecord)
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("要保存的记录不能为空。");
        }else
        {
        	try {
        		basConsumablesOutRecordService.batAddConsumablesRetreatRecord(retreatRecord, resp);
			} catch (Exception e) {
				return resp.getJsonStr();
			}
        }
        logger.info("end batAddConsumablesRetreatRecord");
        return resp.getJsonStr();
    }
    
    
    
    /**
     * 
     * @discription 添加耗材报损记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param basConsumablesLoseRecord
     * @return
     */
    @RequestMapping(value = "/addConsumablesLoseRecord")
    @ResponseBody
    @ApiOperation(value = "添加耗材报损记录", httpMethod = "POST", notes = "添加耗材报损记录")
    public String addConsumablesLoseRecord(
        @ApiParam(name = "basConsumablesLoseRecord", value = "耗材退药记录") @RequestBody BasConsumablesLoseRecord basConsumablesLoseRecord)
    {
    	logger.info("begin addConsumablesLoseRecord");
        ResponseValue resp = new ResponseValue();
        if(null == basConsumablesLoseRecord)
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("要保存的记录不能为空。");
        }else
        {
        	basConsumablesOutRecordService.addConsumablesLoseRecord(basConsumablesLoseRecord, resp);
        }
        logger.info("end addConsumablesLoseRecord");
        return resp.getJsonStr();
    }
    
//	/**
//     * 
//     * @discription 查询手术是否取药信息列表
//     * @author pengqing
//     * @created 2017年6月13日 下午3:01:40
//     * @param systemSearchFormBean
//     * @return
//     */
//    @RequestMapping(value = "/selectRegOptInfoForOutRecord")
//    @ResponseBody
//    @ApiOperation(value = "查询手术是否取药信息列表", httpMethod = "POST", notes = "查询手术是否取药信息列表")
//  	public String selectRegOptInfoForOutRecord(@ApiParam(name = "systemSearchFormBean", value = "系统查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
//    {
//        logger.info("begin selectRegOptInfoForOutRecord");
//        ResponseValue resp = new ResponseValue();
//        List<BasMedicineRegOptFormBean> resultList = basConsumablesOutRecordService.selectRegOptInfoForOutRecordList(systemSearchFormBean);
//        int total = basConsumablesOutRecordService.selectRegOptInfoForOutRecordListTotal(systemSearchFormBean);
//        resp.put("resultList", resultList);
//        resp.put("total", total);
//        logger.info("end selectRegOptInfoForOutRecord");
//        return resp.getJsonStr();
//    }
    
    /**
     * 
     * @discription 删除耗材退药记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param map
     * @return
     */
    @RequestMapping(value = "/delConsumablesRetreatRecord")
    @ResponseBody
    @ApiOperation(value = "删除耗材退药记录", httpMethod = "POST", notes = "添加耗材退药记录")
    public String delConsumablesRetreatRecord(
        @ApiParam(name = "map", value = "耗材退药记录id和取药id必传") @RequestBody Map<String,Object> map)
    {
    	logger.info("begin delConsumablesRetreatRecord");
        ResponseValue resp = new ResponseValue();
        if(null == map || null == map.get("id"))
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("退药记录id不能为空！");
        }else
        {
        	Integer id = Integer.parseInt(map.get("id").toString());
        	basConsumablesOutRecordService.delConsumablesRetreatRecord(id,resp);
        }
        logger.info("end delConsumablesRetreatRecord");
        return resp.getJsonStr();
    }
    
    /**
     * 
     * @discription 删除耗材报损记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param map
     * @return
     */
    @RequestMapping(value = "/delConsumablesLoseRecord")
    @ResponseBody
    @ApiOperation(value = "删除耗材报损记录", httpMethod = "POST", notes = "添加耗材报损记录")
    public String delConsumablesLoseRecord(
        @ApiParam(name = "map", value = "耗材报损记录id") @RequestBody Map<String,Object> map)
    {
    	logger.info("begin delConsumablesLoseRecord");
        ResponseValue resp = new ResponseValue();
        if(null == map || null == map.get("id"))
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("报损记录id不能为空！");
        }else
        {
        	Integer id = Integer.parseInt(map.get("id").toString());
        	basConsumablesOutRecordService.delConsumablesLoseRecord(id,resp);
        }
        logger.info("end delConsumablesLoseRecord");
        return resp.getJsonStr();
    }
    
    
    /**
     * 
     * @discription 逻辑删除耗材取药记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param map
     * @return
     */
    @RequestMapping(value = "/delConsumablesOutRecord")
    @ResponseBody
    @ApiOperation(value = "逻辑删除耗材取药记录", httpMethod = "POST", notes = "逻辑删除耗材取药记录")
    public String delConsumablesOutRecord(
        @ApiParam(name = "map", value = "耗材取药id必传") @RequestBody Map<String,Object> map)
    {
    	logger.info("begin delConsumablesOutRecord");
        ResponseValue resp = new ResponseValue();
        if(null == map || null == map.get("id"))
        {
        	resp.setResultCode("10000001");
            resp.setResultMessage("取药记录id不能为空！");
        }else
        {
        	Integer id = Integer.parseInt(map.get("id").toString());
        	basConsumablesOutRecordService.delConsumablesOutRecord(id,resp);
        }
        logger.info("end delConsumablesOutRecord");
        return resp.getJsonStr();
    }
}
