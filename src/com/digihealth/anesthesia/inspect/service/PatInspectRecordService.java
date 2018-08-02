package com.digihealth.anesthesia.inspect.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocPatInspectRecord;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.inspect.formbean.PatInspectRecordFormbean;

@Service
public class PatInspectRecordService extends BaseService{
	
	public List<DocPatInspectRecord> getPatInspectRecordList(SystemSearchFormBean searchFormBean){
		setBeid(searchFormBean);
		if(StringUtils.isBlank(searchFormBean.getSort())){
            searchFormBean.setSort("id");
        }
        if(StringUtils.isBlank(searchFormBean.getOrderBy())){
            searchFormBean.setOrderBy("DESC");
        }
        List<Filter> filters = searchFormBean.getFilters();
        if(filters.size()==0){
            filters = new ArrayList<Filter>();
        }
        return docPatInspectRecordDao.getPatInspectRecordList(searchFormBean,filters);
	}
	
	public int getTotalPatInspectRecordList(SystemSearchFormBean searchFormBean){
		List<Filter> filters = searchFormBean.getFilters();
		if(filters.size()==0){
			filters = new ArrayList<Filter>();
		}
		setBeid(searchFormBean); 
		return docPatInspectRecordDao.getTotalPatInspectRecordList(filters);
	}
	
	
	public List<PatInspectRecordFormbean> getRegInfoListByPatInspect(SystemSearchFormBean searchFormBean){
		setBeid(searchFormBean);
		if(StringUtils.isBlank(searchFormBean.getSort())){
			searchFormBean.setSort("regOptId");
		}
		if(StringUtils.isBlank(searchFormBean.getOrderBy())){
			searchFormBean.setOrderBy("DESC");
		}
		List<Filter> filters = searchFormBean.getFilters();
		if(filters.size()==0){
			filters = new ArrayList<Filter>();
		}
		return docPatInspectRecordDao.getRegInfoListByPatInspect(searchFormBean,filters);
	}
	    
	public int getTotalRegInfoListByPatInspect(SystemSearchFormBean searchFormBean){
		List<Filter> filters = searchFormBean.getFilters();
		if(filters.size()==0){
			filters = new ArrayList<Filter>();
		}
		return docPatInspectRecordDao.getTotalRegInfoListByPatInspect(filters);
	}
}
