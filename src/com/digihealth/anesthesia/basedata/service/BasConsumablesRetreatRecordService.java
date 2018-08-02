package com.digihealth.anesthesia.basedata.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.digihealth.anesthesia.basedata.formbean.CommonRetreatRecordFormBean;
import com.digihealth.anesthesia.basedata.formbean.OperationRetreatRecordFormBean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;

@Service
public class BasConsumablesRetreatRecordService extends BaseService
{
	//查询耗材普通退货信息列表
	public List<CommonRetreatRecordFormBean> queryCommonRetreatRecordList(SystemSearchFormBean systemSearchFormBean)
	{
		if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
			systemSearchFormBean.setSort("id");
		}
		if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
			systemSearchFormBean.setOrderBy("ASC");
		}
		if(StringUtils.isEmpty(systemSearchFormBean.getBeid())){
			systemSearchFormBean.setBeid(getBeid());
		}
		List<Filter> filters = systemSearchFormBean.getFilters();
		if(null == filters)
		{
			filters = new ArrayList<Filter>();
		}
		return basConsumablesRetreatRecordDao.queryCommonRetreatRecordList(filters, systemSearchFormBean);
	}
	
	//查询耗材普通退货信息数量
	public int queryCommonRetreatRecordListTotal(SystemSearchFormBean systemSearchFormBean)
	{
		List<Filter> filters = systemSearchFormBean.getFilters();
		return basConsumablesRetreatRecordDao.queryCommonRetreatRecordListTotal(filters);
	}
	
	//查询耗材手术退货信息列表
	public List<OperationRetreatRecordFormBean> queryOperationRetreatRecordList(SystemSearchFormBean systemSearchFormBean)
	{
		if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
			systemSearchFormBean.setSort("id");
		}
		if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
			systemSearchFormBean.setOrderBy("ASC");
		}
		
		List<Filter> filters = systemSearchFormBean.getFilters();
		if(null == filters)
		{
			filters = new ArrayList<Filter>();
		}
		Filter filter = new Filter();
		filter.setField("beid");
		filter.setValue(getBeid());
		filters.add(filter);
		
		return basConsumablesRetreatRecordDao.queryOperationRetreatRecordList(filters, systemSearchFormBean);
	}
	
	//查询耗材手术退货信息数量
	public int queryOperationRetreatRecordListTotal(SystemSearchFormBean systemSearchFormBean)
	{
		List<Filter> filters = systemSearchFormBean.getFilters();
		return basConsumablesRetreatRecordDao.queryOperationRetreatRecordListTotal(filters);
	}
}
