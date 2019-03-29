package com.digihealth.anesthesia.tmp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SearchDoctempFormBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.tmp.po.TmpToxicOutstockTemp;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * Title: TmpToxicOutstockTempController.java Description: 模板操作
 * 
 * @author liukui 
 * @created 2018-10-29
 */
@Controller
@RequestMapping(value = "/basedata")
@Api(value = "TmpToxicOutstockTempController", description = "毒麻药模版处理类")
public class TmpToxicOutstockTempController extends BaseController {
	/**
	 * 查询模板信息
	 */
	@RequestMapping(value = "/queryToxicStockTempList")
	@ResponseBody
	@ApiOperation(value = "查询模板信息", httpMethod = "POST", notes = "查询模板信息")
	public String queryToxicStockTempList(@ApiParam(name = "searchDoctempFormBean", value = "查询对象") @RequestBody SearchDoctempFormBean searchDoctempFormBean) {
		logger.info("begin queryAnaesDoctempList");
		List<TmpToxicOutstockTemp> resultList = tmpToxicOutstockTempService.queryToxicStocktempByForbean(searchDoctempFormBean);
		int total = tmpToxicOutstockTempService.queryToxicStocktempTotalByForbean(searchDoctempFormBean);
		ResponseValue resp = new ResponseValue();
		resp.put("resultList", resultList);
		resp.put("total", total);

		logger.info("end queryAnaesDoctempList");
		return resp.getJsonStr();
	}


     /**
     * 修改模板信息
     */
    @RequestMapping(value = "/saveToxicStockInfoTemp")
    @ResponseBody
    @ApiOperation(value = "更新模板信息", httpMethod = "POST", notes = "更新模板信息")
    public String saveToxicStockInfoTemp(@ApiParam(name = "tmpToxicOutstockTemp", value = "更新参数") @RequestBody TmpToxicOutstockTemp  tmpToxicOutstockTemp) {
        logger.info("begin upAnaesDoctemp");
        ResponseValue resp = new ResponseValue();
        tmpToxicOutstockTempService.HandletoxicTemp(tmpToxicOutstockTemp);
        logger.info("end upAnaesDoctemp");
        return resp.getJsonStr();
    }
    
    
    /**
     * 删除模板信息
     */
    @RequestMapping(value = "/delToxicStockInfoTemp")
    @ResponseBody
    @ApiOperation(value = "删除模板信息", httpMethod = "POST", notes = "删除模板信息")
    public String delToxicStockInfoTemp(@ApiParam(name = "tmpToxicOutstockTemp", value = "删除参数") @RequestBody TmpToxicOutstockTemp  tmpToxicOutstockTemp) {
        logger.info("begin delToxicStockInfoTemp");
        ResponseValue resp = new ResponseValue();
        if(StringUtils.isBlank(tmpToxicOutstockTemp.getId()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", "需要删除模板的ID不能传空!");
            return JsonType.jsonType(resp);
        }
        tmpToxicOutstockTempService.delToxicStockInfo(tmpToxicOutstockTemp.getId(),tmpToxicOutstockTempService.getUserName());
        logger.info("end delToxicStockInfoTemp");
        return JsonType.jsonType(resp);
    }
}
