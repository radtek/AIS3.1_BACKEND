package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocLaborAnalgesiaAccede;

@Service
public class DocLaborAnalgesiaAccedeService extends BaseService {

	/**
	 * 
	 * @discription 根据手术ID获取分娩镇痛同意书信息
	 * @author chengwang
	 * @created 2015-10-10 下午5:13:48
	 * @param regOptId
	 * @return
	 */
	public DocLaborAnalgesiaAccede searchLaborAccedeByRegOptId(String regOptId) {
		return docLaborAnalgesiaAccedeDao.searchLaborAccedeByRegOptId(regOptId);
	}

	/**
	 * 
	 * @discription 通过ID查询分娩镇痛同意书
	 * @author chengwang
	 * @created 2015-10-20 下午1:44:32
	 * @param id
	 * @return
	 */
	public DocLaborAnalgesiaAccede searchLaborAccedeById(String id) {
		return docLaborAnalgesiaAccedeDao.searchLaborAccedeById(id);
	}

	/**
	 * 
	 * @discription 保存分娩镇痛同意书
	 * @author chengwang
	 * @created 2015-10-20 下午1:44:18
	 * @param preVisit
	 * @return
	 */
	@Transactional
	public void saveLaborAccede(DocLaborAnalgesiaAccede record) {
		docLaborAnalgesiaAccedeDao.updateByPrimaryKeySelective(record);
	}
}
