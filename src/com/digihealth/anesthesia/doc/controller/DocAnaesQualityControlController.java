package com.digihealth.anesthesia.doc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocAnaesQualityControl;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value = "DocAnaesQualityControlController", description = "麻醉质控文书处理类")
public class DocAnaesQualityControlController extends BaseController
{
    @RequestMapping(value = "/selectAnaesQualityControlByRegOptId")
    @ResponseBody
    @ApiOperation(value = "根据手术ID获取麻醉质控文书", httpMethod = "POST", notes = "根据手术ID获取麻醉质控文书")
    public String selectAnaesQualityControlByRegOptId(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map) {
        logger.info("-----------------start selectAnaesQualityControlByRegOptId-----------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId")
                .toString() : "";
        DocAnaesQualityControl anaesQualityControl = docAnaesQualityControlService.selectAnaesQualityControlByRegOptId(regOptId);

        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService
                .searchApplicationById(map.get("regOptId").toString());
        resp.put("anaesQualityControl", anaesQualityControl);
        resp.put("regOptItem",searchRegOptByIdFormBean);

        logger.info("-----------------end selectAnaesQualityControlByRegOptId-----------------");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/updateAnaesQualityControl")
    @ResponseBody
    @ApiOperation(value = "更新麻醉质控文书", httpMethod = "POST", notes = "更新麻醉质控文书")
    public String updateAnaesQualityControl(@ApiParam(name = "anaesQualityControl", value = "更新参数") @RequestBody DocAnaesQualityControl anaesQualityControl)
    {
        logger.info("-----------------start updateAnaesQualityControl-----------------");
        ResponseValue resp = new ResponseValue();
        docAnaesQualityControlService.updateAnaesQualityControl(anaesQualityControl);
        logger.info("-----------------end updateAnaesQualityControl-----------------");
        return resp.getJsonStr();
    }
}
