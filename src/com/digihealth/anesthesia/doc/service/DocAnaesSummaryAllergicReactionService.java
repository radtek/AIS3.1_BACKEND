/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author liukui       
 * @created 2015-10-10 下午5:32:33    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAllergicReaction;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;

/**
 * Title: NerveBlockService.java Description: 神经阻滞
 * 
 * @author liukui
 * @created 2015-10-10 下午5:32:33
 */
@Service
public class DocAnaesSummaryAllergicReactionService extends BaseService {
	
	 public List<DocAnaesSummaryAllergicReaction> searchAllergicReactionList(SearchFormBean searchBean){
		return docAnaesSummaryAllergicReactionDao.searchAllergicReactionList(searchBean);
	}

}
