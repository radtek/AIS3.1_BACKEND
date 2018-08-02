package com.digihealth.anesthesia.doc.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.BatchSaveDocTitleFormBean;
import com.digihealth.anesthesia.doc.po.DocTheme;
import com.digihealth.anesthesia.doc.po.DocTitle;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value = "DocTitleController",description="自定义文书标题处理类")
public class DocTitleController extends BaseController
{

	@RequestMapping(value = "/batchSaveDocTitle")
	@ResponseBody
	@ApiOperation(value = "批量保存自定义文书标题列表")
	public String batchSaveDocTitle(@ApiParam(name = "map", value = "自定义文书标题对象列表") @RequestBody BatchSaveDocTitleFormBean formbean )
	{
		ResponseValue resp = new ResponseValue();
		if(null != formbean && null != formbean.getDocTheme() && null != formbean.getDocTitleList())
		{
			String tableName = "zdy_";
			DocTheme docTheme = formbean.getDocTheme();
			if(null != docTheme)
			{
				//保存主题对象
				String docThemeId = docTheme.getDocThemeId();
				docThemeService.saveDocTheme(docTheme);
				//根据主题ID删除标题列表
				docTitleService.delDocTitleByThemeId(docThemeId);
				//取得主题名字
				String docThemeName = docTheme.getDocThemeName();
				tableName += PingYinUtil.getTouFengPingYin(docThemeName);
				if(StringUtils.isNotBlank(tableName) && tableName.length()>=64)
				{
					tableName = tableName.substring(0, 63);
				}
			}
			
			List<DocTitle> docTitleList = formbean.getDocTitleList();
			//新增标题列表
			docTitleService.saveDocTitle(docTitleList,tableName);
		}else
		{
			resp.setResultCode("20000019");
			resp.setResultMessage("要保存的自定义主题对象和文书标题对象列表不能为空！");
		}
		return resp.getJsonStr();
	}

	
}
