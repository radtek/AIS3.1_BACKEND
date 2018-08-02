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
import com.digihealth.anesthesia.tmp.po.TmpOtherevent;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Title: TmpOthereventTempController.java Description: 模板操作
 * @created 2018-3-12
 */
@Controller
@RequestMapping(value = "/basedata")
@Api(value = "TmpOthereventController", description = "其他事件模版处理类")
public class TmpOthereventController extends BaseController {
	/**
	 * 查询模板信息
	 */
	@RequestMapping(value = "/queryOthereventTempList")
	@ResponseBody
	@ApiOperation(value = "查询模板信息", httpMethod = "POST", notes = "查询模板信息")
	public String queryOthereventTempList(@ApiParam(name = "searchDoctempFormBean", value = "查询对象") @RequestBody SearchDoctempFormBean searchDoctempFormBean) {
		logger.info("begin queryOthereventTempList");
		List<TmpOtherevent> resultList = tmpOthereventService.queryOthereventTempList(searchDoctempFormBean);
		int total = tmpOthereventService.queryTotalCntOthereventTemp(searchDoctempFormBean);
		ResponseValue resp = new ResponseValue();
		resp.put("resultList", resultList);
		resp.put("total", total);
		logger.info("end queryOthereventTempList");
		return resp.getJsonStr();
	}

	 /**
     * 增加模板信息
     */
    @RequestMapping(value = "/saveOthereventTemp")
    @ResponseBody
    @ApiOperation(value = "增加模板信息", httpMethod = "POST", notes = "增加模板信息")
    public String saveOthereventTemp(@ApiParam(name = "anaesDoctemp", value = "保存参数") @RequestBody TmpOtherevent tmpOtherevent)
    {
        logger.info("begin addOthereventTemp");
        ResponseValue resp = new ResponseValue();
        tmpOthereventService.saveOthereventTemp(tmpOtherevent);
        logger.info("end saveOthereventTemp");
        return resp.getJsonStr();

    }
    
    /**
     * 删除模板信息
     */
    @RequestMapping(value = "/delOthereventTmp")
    @ResponseBody
    @ApiOperation(value = "删除模板信息", httpMethod = "POST", notes = "删除模板信息")
    public String delOthereventTmp(@ApiParam(name = "anaesDoctemp", value = "删除参数") @RequestBody TmpOtherevent tmpOtherevent) {
        logger.info("begin delOthereventTmp");
        ResponseValue resp = new ResponseValue();
        if(StringUtils.isBlank(tmpOtherevent.getId()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", "需要删除模板的ID不能传空!");
            return JsonType.jsonType(resp);
        }
        tmpOthereventService.delOthereventTmp(tmpOtherevent.getId(), tmpOtherevent.getCreateUser());
        logger.info("end delOthereventTmp");
        return JsonType.jsonType(resp);
    }
}
