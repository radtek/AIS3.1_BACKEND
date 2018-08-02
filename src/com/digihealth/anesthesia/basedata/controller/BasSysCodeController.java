package com.digihealth.anesthesia.basedata.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value="BasSysCodeController",description="码表信息处理类")
public class BasSysCodeController extends BaseController {

	@RequestMapping(value = "/searchSysCodeByGroupId")
	@ResponseBody
	@ApiOperation(value = "查询字典值", httpMethod = "POST", notes = "通过groupId或者beid查询字典值")
	public String searchSysCodeByGroupId(@ApiParam(name = "map", value = "查询对象,groupId不能为空。" )@RequestBody Map map){
		logger.info("begin searchSysCodeByGroupId");
		ResponseValue resp = new ResponseValue();
		String groupId = map.get("groupId").toString() == null ? "" : map.get("groupId").toString();
        String state = map.get("state") == null ? "" : map.get("state").toString();
        List<SysCodeFormbean> syscodeList = null;
        List<SysCodeFormbean> newSyscodeList = new ArrayList<SysCodeFormbean>();
        if("anaesEventType".equals(groupId) && "04".equals(state)){
            syscodeList = basSysCodeService.searchSysCodeByGroupId(groupId, getBeid());
            if(null != syscodeList && syscodeList.size()>0){
                for (SysCodeFormbean sysCodeFormbean : syscodeList) {
                    String codeValue = sysCodeFormbean.getCodeValue();
                    if(codeValue.equals("9")){//移除出室事件
                        //syscodeList.remove(sysCodeFormbean);
                        continue;
                    }else{
                        newSyscodeList.add(sysCodeFormbean);
                    }
                }
            }
            resp.put("resultList", newSyscodeList);
        }else{
            syscodeList = basSysCodeService.searchSysCodeByGroupId(groupId, getBeid());
            resp.put("resultList", syscodeList);
        }
		logger.info("end searchSysCodeByGroupId");
		return resp.getJsonStr();
	}
}
