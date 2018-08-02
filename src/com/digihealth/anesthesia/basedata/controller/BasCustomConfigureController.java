package com.digihealth.anesthesia.basedata.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.digihealth.anesthesia.basedata.po.BasCustomConfigure;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * Title: BasCustomConfigureController.java Description: 麻醉记录单自定义配置表
 * 
 * @author liukui
 * @created 2017-12-08 下午1:56:40
 */
@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasCustomConfigureController", description = "麻醉记录单自定义配置表")
public class BasCustomConfigureController extends BaseController {

	@RequestMapping(value = "/searchBasCustomConfigureList")
	@ResponseBody
    @ApiOperation(value = "查询麻醉记录单自定义配置列表", httpMethod = "POST", notes = "查询麻醉记录单自定义配置列表")
	public String searchBasCustomConfigureList(@RequestBody BasCustomConfigure baseQuery) {
		logger.info("----------------------begin searchBasCustomConfigureList----------------------");
        ResponseValue resp = new ResponseValue();
        List<BasCustomConfigure> resultList = basCustomConfigureService.searchBasCustomConfigureList(baseQuery);
        resp.put("resultList", resultList);
        resp.put("total", resultList.size());
		logger.info("----------------------end searchBasCustomConfigureList----------------------");
		return resp.getJsonStr();
	}
	
    
    @RequestMapping(value = "/saveBasCustomConfigure")
    @ResponseBody
    @ApiOperation(value = "保存麻醉记录单自定义配置", httpMethod = "POST", notes = "保存麻醉记录单自定义配置")
    public String saveBasCustomConfigure(@ApiParam(name = "configure", value = "自定义配置对象") @RequestBody BasCustomConfigure configure)
    {
        logger.info("begin saveBasCustomConfigure");
        ResponseValue resp = new ResponseValue();
        basCustomConfigureService.saveBasCustomConfigure(configure);
        logger.info("end saveBasCustomConfigure");
        return resp.getJsonStr();
    }
    
}
