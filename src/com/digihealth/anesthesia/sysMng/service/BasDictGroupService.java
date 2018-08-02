package com.digihealth.anesthesia.sysMng.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.sysMng.po.BasDictGroup;

@Service
public class BasDictGroupService extends BaseService
{
	//分页查询查询所有的groupId
	public List<BasDictGroup> getDictItemGroups(
			SystemSearchFormBean systemSearchFormBean)
	{
		if (StringUtils.isEmpty(systemSearchFormBean.getSort()))
		{
			systemSearchFormBean.setSort("id");
		}
		if (StringUtils.isEmpty(systemSearchFormBean.getOrderBy()))
		{
			systemSearchFormBean.setOrderBy("ASC");
		}
		List<Filter> filters = systemSearchFormBean.getFilters();
		if(null == filters)
		{
			filters = new ArrayList<Filter>();
		}

		if (!"administrator".equals(getUserName()))
		{
			Filter filter = new Filter();
			filter.setField("beid");
			filter.setValue(systemSearchFormBean.getBeid());
			filters.add(filter);
		}

		return basDictGroupDao.getDictItemGroups(filters, systemSearchFormBean);
	}

	public Integer getDictItemGroupsNum(
			SystemSearchFormBean systemSearchFormBean)
	{

		List<Filter> filters = systemSearchFormBean.getFilters();
		return basDictGroupDao.getDictItemGroupsNum(filters);
	}
	
	//根据ID查询字典组
	public BasDictGroup getDictGroupById(Integer id)
	{
		return basDictGroupDao.selectByPrimaryKey(id);
	}
	
	//查询这个groupId + beid 是否已经存在了
	public BasDictGroup getDictGroupByGroupId(String groupId,String beid)
	{
		return basDictGroupDao.getDictGroupByGroupId(groupId, beid);
	}
	
	// 添加字典组
	@Transactional
	public int addDictItemGroup(BasDictGroup dictGroup)
	{
		if (null == dictGroup.getBeid() || "".equals(dictGroup.getBeid()))
		{
			dictGroup.setBeid(getBeid());
		}
		return basDictGroupDao.insertSelective(dictGroup);
	}

	// 修改字典组
	@Transactional
	public int upDictItemGroup(BasDictGroup dictGroup)
	{
		return basDictGroupDao.updateByPrimaryKeySelective(dictGroup);
	}

	// 删除字典值
	@Transactional
	public int deleteDictItemGroup(Integer id)
	{
		return basDictGroupDao.deleteByPrimaryKey(id);
	}
}
