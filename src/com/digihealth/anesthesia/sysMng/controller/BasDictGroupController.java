package com.digihealth.anesthesia.sysMng.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.sysMng.po.BasDictGroup;
import com.digihealth.anesthesia.sysMng.po.BasDictItem;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/sys")
@Api(value="字典组处理",description="专门处理字典组")
public class BasDictGroupController extends BaseController
{

	@RequestMapping(value = "/getDictItemGroups")
    @ResponseBody
    @ApiOperation(value="查询某局点下的系统字典值组",httpMethod="POST",notes="通过beid查询该局点下所有的字典值")
    public String getDictItemGroups(@ApiParam(name="systemSearchFormBean", value ="系统查询对象") @RequestBody SystemSearchFormBean systemSearchFormBean)
	{
		ResponseValue resp = new ResponseValue();
		List<BasDictGroup> dgfbList = basDictGroupService.getDictItemGroups(systemSearchFormBean);
		Integer total = basDictGroupService.getDictItemGroupsNum(systemSearchFormBean);
		resp.put("resultList", dgfbList);
		resp.put("total",total);
		return resp.getJsonStr();
	}
	
	 @RequestMapping(value = "/addDictItemGroup")
	    @ResponseBody
	    @ApiOperation(value="新增字典组",httpMethod="POST",notes="新增字典组")
	    public String addDictItemGroup(@ApiParam(name="dictItem", value ="字典对象") @RequestBody BasDictGroup dictGroup)
		{
			ResponseValue resp = new ResponseValue();
			if(null == dictGroup || null == dictGroup.getGroupId() || null == dictGroup.getGroupName())
			{
				resp.setResultCode("20000011");
		    	resp.setResultMessage(Global.getRetMsg("20000011"));
			}else
			{
				BasDictGroup oldDictGroup = basDictGroupService.getDictGroupByGroupId(dictGroup.getGroupId(),dictGroup.getBeid());
				if(null != oldDictGroup)
				{
					resp.setResultCode("20000018");
			    	resp.setResultMessage(Global.getRetMsg("20000018"));
				}else
				{
					basDictGroupService.addDictItemGroup(dictGroup);
				}
				
			}
			return resp.getJsonStr();
		}
	    
	    @RequestMapping(value = "/upDictItemGroup")
	    @ResponseBody
	    @ApiOperation(value="修改字典组",httpMethod="POST",notes="修改字典组")
	    public String upDictItemGroup(@ApiParam(name="dictItem", value ="字典对象") @RequestBody BasDictGroup dictGroup)
		{
			ResponseValue resp = new ResponseValue();
			if(null == dictGroup || null == dictGroup.getGroupId() || null == dictGroup.getGroupName())
			{
				resp.setResultCode("20000011");
		    	resp.setResultMessage(Global.getRetMsg("20000011"));
			}else
			{
				BasDictGroup oldDictGroup = basDictGroupService.getDictGroupByGroupId(dictGroup.getGroupId(),dictGroup.getBeid());
				if(null != oldDictGroup)
				{
					if(oldDictGroup.getId().intValue() == dictGroup.getId().intValue())
					{
						basDictGroupService.upDictItemGroup(dictGroup);
					}else
					{
						resp.setResultCode("20000018");
				    	resp.setResultMessage(Global.getRetMsg("20000018"));
					}
				}else
				{
					basDictGroupService.upDictItemGroup(dictGroup);
				}
			}
			return resp.getJsonStr();
		}
	    
	    @RequestMapping(value = "/deleteDictItemGroup")
	    @ResponseBody
	    @ApiOperation(value="删除字典组",httpMethod="POST",notes="删除字典组")
	    public String deleteDictItemGroup(@ApiParam(name="map", value ="参数对象,id不能为空。") @RequestBody Map<String,Object> map)
		{
			ResponseValue resp = new ResponseValue();
			if(null == map || null == map.get("id"))
			{
				resp.setResultCode("20000013");
		    	resp.setResultMessage(Global.getRetMsg("20000013"));
			}else
			{
				Integer id = Integer.parseInt(map.get("id").toString());
				BasDictGroup dictGroup =basDictGroupService.getDictGroupById(id);
				if(null != dictGroup)
				{
					List<BasDictItem> dictItemList = basDictItemService.getListByGroupId(dictGroup.getGroupId(), null, dictGroup.getBeid());
					if(null != dictItemList && dictItemList.size()>0)
					{
						resp.setResultCode("20000017");
				    	resp.setResultMessage(Global.getRetMsg("20000017"));
					}else
					{
						basDictGroupService.deleteDictItemGroup(id);
					}
				}
			}
			return resp.getJsonStr();
		}
}
