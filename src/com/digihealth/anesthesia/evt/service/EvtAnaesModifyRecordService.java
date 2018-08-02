package com.digihealth.anesthesia.evt.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.evt.formbean.SearchConditionFormBean;
import com.digihealth.anesthesia.evt.po.EvtAnaesModifyRecord;

@Service
public class EvtAnaesModifyRecordService extends BaseService {

	
	public List<EvtAnaesModifyRecord> queryEvtAnaesModifyRecordList(SearchConditionFormBean searchBean) {
		
		if(StringUtils.isBlank(searchBean.getSort())){
			searchBean.setSort("modifyDate");
		}
		if(StringUtils.isBlank(searchBean.getOrderBy())){
			searchBean.setOrderBy("desc");
		}
		List<Filter> filters = searchBean.getFilters();
		if(filters.size()==0){
			filters = new ArrayList<Filter>();
		}
		return evtAnaesModifyRecordDao.queryEvtAnaesModifyRecordList(searchBean,filters);
	}

	public Integer queryCountEvtAnaesModifyRecordList(SearchConditionFormBean searchBean) {
		List<Filter> filters = searchBean.getFilters();
		if(filters.size()==0){
			filters = new ArrayList<Filter>();
		}
		return evtAnaesModifyRecordDao.queryCountEvtAnaesModifyRecordList(filters);
	}

}
