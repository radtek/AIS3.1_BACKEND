package com.digihealth.anesthesia.doc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.DocMenuFormBean;
import com.digihealth.anesthesia.doc.formbean.DocRoleFormBean;
import com.digihealth.anesthesia.doc.formbean.DocThemeFormBean;
import com.digihealth.anesthesia.doc.formbean.DocZdyFormBean;
import com.digihealth.anesthesia.doc.po.DocTheme;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.sysMng.po.BasRole;
import com.jfinal.plugin.activerecord.Record;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value = "DocThemeController",description="自定义文书主题处理类")
public class DocThemeController extends BaseController
{
	
	@RequestMapping(value = "/findDocMenuPartIdByBeid")
	@ResponseBody
	@ApiOperation(value = "查询自定义文书可插入的目录和可赋给的角色")
	public String findDocMenuPartIdByBeid()
	{
		ResponseValue resp = new ResponseValue();
		List<DocMenuFormBean> catalogList = docThemeService.findDocMenuPartIdByBeid();
		List<DocRoleFormBean> roleList = new ArrayList<DocRoleFormBean>();
		List<BasRole> roles = basRoleService.getAllRoleByDelFlag();
		if(null != roles && roles.size()>0)
		{
			for(BasRole role : roles)
			{
				DocRoleFormBean roleBean = new DocRoleFormBean();
				roleBean.setRoleId(role.getId());
				roleBean.setRoleName(role.getName());
				roleList.add(roleBean);
			}
		}
		
		resp.put("catalogList", catalogList);
		resp.put("roleList", roleList);
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/saveDocTheme")
	@ResponseBody
	@ApiOperation(value = "保存自定义文书")
	public String saveDocTheme(@ApiParam(name = "docTheme", value = "自定义文书主题对象") @RequestBody DocTheme docTheme)
	{
		ResponseValue resp = new ResponseValue();
		if(null != docTheme)
		{
			docThemeService.saveDocTheme(docTheme);
		}else
		{
			resp.setResultCode("20000019");
			resp.setResultMessage("要保存的自定义文书对象不能为空！");
		}
		
		return resp.getJsonStr();
	}

	@RequestMapping(value = "/delDocTheme")
	@ResponseBody
	@ApiOperation(value = "标记删除自定义文书")
	public String delDocTheme(@ApiParam(name = "docThemeId", value = "自定义文书主题id") @RequestBody Map<String,String> map)
	{
		
		ResponseValue resp = new ResponseValue();
		if(null == map || StringUtils.isBlank(map.get("docThemeId")))
		{
			resp.setResultCode("20000019");
			resp.setResultMessage("要删除的主题id不能为空！");
		}else
		{
			String docThemeId = map.get("docThemeId");
			docThemeService.delDocTheme(docThemeId);
		}
		
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/searchDocTheme")
	@ResponseBody
	@ApiOperation(value = "分页查询自定义文书主题")
	public String searchDocTheme(@ApiParam(name = "searchBean", value = "系统分页查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
	{
		ResponseValue resp = new ResponseValue();
		if(null == systemSearchFormBean)
		{
			resp.setResultCode("20000020");
			resp.setResultMessage("系统分页参数不为空");
		}else
		{
			List<DocThemeFormBean> docThemeList = docThemeService.seachDocTheme(systemSearchFormBean);
			Integer total = docThemeService.seachDocThemeTotal(systemSearchFormBean);
			resp.put("docThemeList", docThemeList);
			resp.put("total", total);
		}
		
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/seachDocThemeById")
	@ResponseBody
	@ApiOperation(value = "通过主题ID查询自定义文书主题对象详情")
	public String seachDocTheme(@ApiParam(name = "docThemeId", value = "自定义文书主题id") @RequestBody Map<String,String> map)
	{
		ResponseValue resp = new ResponseValue();
		if(null == map || StringUtils.isBlank(map.get("docThemeId")))
		{
			resp.setResultCode("20000019");
			resp.setResultMessage("查询的主题id不能为空！");
		}else
		{
			String docThemeId = map.get("docThemeId");
			DocThemeFormBean docThemeFormBean = docThemeService.seachDocThemeById(docThemeId);
			resp.put("docTheme", docThemeFormBean);
		}
		
		return resp.getJsonStr();
	}
	
	
	@RequestMapping(value = "/docThemeExaminationPassed")
	@ResponseBody
	@ApiOperation(value = "自定义文书审核通过")
	public String docThemeExaminationPassed(@ApiParam(name = "docThemeId", value = "自定义文书主题id") @RequestBody Map<String,String> map)
	{
		
		ResponseValue resp = new ResponseValue();
		if(null == map || StringUtils.isBlank(map.get("docThemeId")))
		{
			resp.setResultCode("20000019");
			resp.setResultMessage("要审核的主题id不能为空！");
		}else
		{
			String docThemeId = map.get("docThemeId");
			docThemeService.docThemeExaminationPassed(docThemeId);
		}
		
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/selectDocThemeByMenuId")
	@ResponseBody
	@ApiOperation(value = "通过菜单id查自定义文书")
	public String selectDocThemeByMenuId(@ApiParam(name = "menuId,regOptId", value = "自定义菜单id,患者id") @RequestBody Map<String,String> map)
	{
		
		ResponseValue resp = new ResponseValue();
		if(null == map || StringUtils.isBlank(map.get("menuId")) || StringUtils.isBlank(map.get("regOptId")))
		{
			resp.setResultCode("20000019");
			resp.setResultMessage("自定义菜单id和患者id不能为空！");
		}else
		{
			String menuId = map.get("menuId");
			String regOptId = map.get("regOptId");
			DocThemeFormBean formBean = docThemeService.selectDocThemeByMenuId(menuId,regOptId);
			resp.put("docTheme", formBean);
			SearchRegOptByIdFormBean searchRegOptByIdFormBean =
		            basRegOptService.searchApplicationById(map.get("regOptId").toString());
			resp.put("regOptItem", searchRegOptByIdFormBean);
			
		}
		
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/saveZdyTable")
	@ResponseBody
	@ApiOperation(value = "保存自定义文书")
	public String saveZdyTable(@ApiParam(name = "DocZdyFormBean", value = "自定义文书保存对象") @RequestBody DocZdyFormBean formbean)
	{
		
		ResponseValue resp = new ResponseValue();
		if(null == formbean)
		{
			resp.setResultCode("20000019");
			resp.setResultMessage("自定义文书保存对象不能为空！");
		}else
		{
			String tableName = formbean.getTableName();
			Map<String, Object> columns = formbean.getColumns();
			
			Record record = new  Record();
			record.setColumns(columns);
			docThemeService.saveZdyTable(tableName,record);
		}
		
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/recoveryDocTheme")
	@ResponseBody
	@ApiOperation(value = "恢复自定义文书状态")
	public String recoveryDocTheme(@ApiParam(name = "docThemeId", value = "自定义文书主题id") @RequestBody Map<String,String> map)
	{
		
		ResponseValue resp = new ResponseValue();
		if(null == map || StringUtils.isBlank(map.get("docThemeId")))
		{
			resp.setResultCode("20000019");
			resp.setResultMessage("要删除的主题id不能为空！");
		}else
		{
			String docThemeId = map.get("docThemeId");
			docThemeService.recoveryDocTheme(docThemeId);
		}
		
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/delDocThemeAll")
	@ResponseBody
	@ApiOperation(value = "彻底删除自定义文书")
	public String delDocThemeAll(@ApiParam(name = "docThemeId", value = "自定义文书主题id") @RequestBody Map<String,String> map)
	{
		
		ResponseValue resp = new ResponseValue();
		if(null == map || StringUtils.isBlank(map.get("docThemeId")))
		{
			resp.setResultCode("20000019");
			resp.setResultMessage("要删除的主题id不能为空！");
		}else
		{
			String docThemeId = map.get("docThemeId");
			docThemeService.delDocThemeAll(docThemeId);
		}
		
		return resp.getJsonStr();
	}
	
}
