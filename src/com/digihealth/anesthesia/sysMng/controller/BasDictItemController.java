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
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.sysMng.po.BasDictItem;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/sys")
@Api(value="字典值处理",description="专门处理一些系统配置，页面下拉值等")
public class BasDictItemController extends BaseController 
{
	
	@RequestMapping(value = "/getDictItemsByGroupId")
    @ResponseBody
    @ApiOperation(value="查询字典组下面的字典列表（分页显示）",httpMethod="POST",notes="查询字典组下面的字典列表（分页显示）")
    public String getDictItemsByGroupId(@ApiParam(name="systemSearchFormBean", value ="系统查询对象,groupId必传参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
	{
		ResponseValue resp = new ResponseValue();
		List<BasDictItem> dictList = basDictItemService.getBasDictItemsByGroupId(systemSearchFormBean);
		Integer total = basDictItemService.getBasDictItemsNumByGroupId(systemSearchFormBean);
		resp.put("resultList", dictList);
		resp.put("total",total);
		return resp.getJsonStr();
	}
	
    @RequestMapping(value = "/getDictItemList")
    @ResponseBody
    @ApiOperation(value="查询字典值",httpMethod="POST",notes="通过groupId或者codeValue查询字典值")
    public String getDictItemList(@ApiParam(name="map", value ="查询对象,groupId不能为空。") @RequestBody Map<String,Object> map)
    {
       ResponseValue resp = new ResponseValue();
       String groupId = null;
       String codeValue = null;
       String beid = null;
       if(null != map.get("groupId"))
       {
    	   groupId = map.get("groupId").toString();
       }
       if(null != map.get("codeValue"))
       {
    	   codeValue = map.get("codeValue").toString();
       }
       if(null != map.get("beid"))
       {
    	   beid = map.get("beid").toString();
       }
       resp.put("dictItemList",  basDictItemService.getListByGroupId(groupId, codeValue, beid));
       return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/addDictItem")
    @ResponseBody
    @ApiOperation(value="新增字典值",httpMethod="POST",notes="新增字典值")
    public String addDictItem(@ApiParam(name="dictItem", value ="字典对象") @RequestBody BasDictItem dictItem)
	{
		ResponseValue resp = new ResponseValue();
		if(null == dictItem || null == dictItem.getGroupId() || null == dictItem.getCodeName())
		{
			resp.setResultCode("20000011");
	    	resp.setResultMessage(Global.getRetMsg("20000011"));
		}else
		{
			BasDictItem oldDictItem = basDictItemService.getListByGroupIdandCodeName(dictItem.getGroupId(), dictItem.getCodeName(), dictItem.getBeid());
			//判断是否重复了
			if(null != oldDictItem)
			{
				resp.setResultCode("20000014");
		    	resp.setResultMessage(Global.getRetMsg("20000014"));
			}else
			{
				basDictItemService.addBasDictItem(dictItem);
			}
		}
		return resp.getJsonStr();
	}
    
    @RequestMapping(value = "/upDictItem")
    @ResponseBody
    @ApiOperation(value="修改字典值",httpMethod="POST",notes="修改字典值")
    public String upDictItem(@ApiParam(name="dictItem", value ="字典对象") @RequestBody BasDictItem dictItem)
	{
		ResponseValue resp = new ResponseValue();
		if(null == dictItem || null == dictItem.getGroupId() || null == dictItem.getCodeName())
		{
			resp.setResultCode("20000011");
	    	resp.setResultMessage(Global.getRetMsg("20000011"));
		}else
		{
			//2018-04-02中央大屏修改字典值时请求参数中没有beid，因此这里需要获取当前局点的beid
			BasDictItem oldDictItem = basDictItemService.getListByGroupIdandCodeName(dictItem.getGroupId(), dictItem.getCodeName(), StringUtils.isBlank(dictItem.getBeid())?getBeid():dictItem.getBeid());
			dictItem.setId(null==dictItem.getId()?oldDictItem.getId():dictItem.getId());
			
			//判断是否重复了
			if(null != oldDictItem)
			{
				//3个唯一索引存在，且为自己，可以修改
				if(oldDictItem.getId().intValue() == dictItem.getId().intValue())
				{
					basDictItemService.upBasDictItem(dictItem);
				}else
				{
					resp.setResultCode("20000014");
			    	resp.setResultMessage(Global.getRetMsg("20000014"));
				}
			}else
			{
				basDictItemService.upBasDictItem(dictItem);
			}
		}
		return resp.getJsonStr();
	}
    
    @RequestMapping(value = "/deleteDictItem")
    @ResponseBody
    @ApiOperation(value="删除字典",httpMethod="POST",notes="删除字典")
    public String deleteDictItem(@ApiParam(name="map", value ="参数对象,id 不能为空。") @RequestBody Map<String,Object> map)
	{
		ResponseValue resp = new ResponseValue();
		if(null == map || null == map.get("id"))
		{
			resp.setResultCode("20000013");
	    	resp.setResultMessage(Global.getRetMsg("20000013"));
		}else
		{
			Integer id = Integer.parseInt(map.get("id").toString());
			basDictItemService.deleteBasDictItem(id);
		}
		return resp.getJsonStr();
	}
    
}
