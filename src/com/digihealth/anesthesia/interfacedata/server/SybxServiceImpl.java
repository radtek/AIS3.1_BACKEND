package com.digihealth.anesthesia.interfacedata.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.doc.po.DocAnaesSummary;
import com.digihealth.anesthesia.interfacedata.formbean.sybx.HisCancleOptFormBean;
import com.digihealth.anesthesia.interfacedata.formbean.sybx.HisResponse;

@WebService
@Component
public class SybxServiceImpl extends BaseService implements SybxService {
	/**
	 * 取消手术通知单
	 */
	@Override
	@Transactional
	public String cancleRegOpt(String request) {
		logger.info("---------------begin cancleRegOpt---------------");
		String response = "";
		HisResponse resp = new HisResponse();
		try {
			logger.info("-------------------------cancleRegOpt Request----------------------------:" + request);
			JAXBContext context = JAXBContext.newInstance(HisCancleOptFormBean.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			HisCancleOptFormBean hisCancleOptFormBean = (HisCancleOptFormBean) unmarshaller.unmarshal(new StringReader(request));

			if (StringUtils.isBlank(hisCancleOptFormBean.getHid())) {
				resp.setResultCode("0");
				resp.setResultMessage("住院号不能为空");
				return getObjectToXml(resp);
			}

			if (StringUtils.isBlank(hisCancleOptFormBean.getPreengagementnumber())) {
				resp.setResultCode("0");
				resp.setResultMessage("手术申请单号空");
				return getObjectToXml(resp);
			}

			BasRegOpt regOpt = basRegOptDao.selectHisToRegOptSYBX(hisCancleOptFormBean.getPreengagementnumber(), hisCancleOptFormBean.getHid(), getBeid());
			if (regOpt != null) {
				// 默认是取消状态
				String defaultState = "08";
				Controller controller = new Controller();
				controller.setRegOptId(regOpt.getRegOptId());
				controller.setCostsettlementState("0");
				String state = hisCancleOptFormBean.getState();
				if ("CANCEL".equals(state)) { //取消手术通知单
					if (OperationState.SURGERY.equals(regOpt.getState())) {
						resp.setResultCode("0");
						resp.setResultMessage("该患者正在手术中，不能取消!");
					} else if (OperationState.POSTOPERATIVE.equals(regOpt.getState())) {
						resp.setResultCode("0");
						resp.setResultMessage("该患者手术已完成，不能取消!");
					} else {
						controller.setState(defaultState);
						controllerDao.update(controller);
						resp.setResultCode("1");
						resp.setResultMessage("取消手术通知单成功!");
					}
				} else if ("DELETE".equals(state)) { //删除手术通知单
					BasDispatch basDispatch = basDispatchDao.getDispatchOper(regOpt.getRegOptId());
					if (basDispatch != null) {
						if (basDispatch.getIsHold() != null && basDispatch.getIsHold() == 0) {
							resp.setResultCode("0");
							resp.setResultMessage("已经完成排班的手术通知单不能删除!");
						} else {
							docPreVisitDao.deleteByRegOptId(basDispatch.getRegOptId());
							docAccedeDao.deleteByRegOptId(basDispatch.getRegOptId());
							docPostFollowRecordDao.deleteByRegOptId(basDispatch.getRegOptId());
							docInsuredPatAgreeDao.deleteByRegOptId(basDispatch.getRegOptId());
							docPrePostVisitDao.deleteByRegOptId(basDispatch.getRegOptId());
							docTransferConnectRecordDao.deleteByRegOptId(basDispatch.getRegOptId());
							docPlacentaHandleAgreeDao.deleteByRegOptId(basDispatch.getRegOptId());
							docOptRiskEvaluationDao.deleteByRegOptId(basDispatch.getRegOptId());
							docOptNurseDao.deleteByRegOptId(basDispatch.getRegOptId());
							
							DocAnaesSummary docAnaesSummary = docAnaesSummaryDao.getAnaesSummaryByRegOptId(basDispatch.getRegOptId());
							if (docAnaesSummary != null) {
								docAnaesSummaryVenipunctureDao.deleteByAnaSumId(docAnaesSummary.getAnaSumId());
								docAnaesSummaryAllergicReactionDao.deleteByAnaSumId(docAnaesSummary.getAnaSumId());
								docGeneralAnaesDao.deleteByAnaSumId(docAnaesSummary.getAnaSumId());
								docNerveBlockDao.deleteByAnaSumId(docAnaesSummary.getAnaSumId());
								docSpinalCanalPunctureDao.deleteByAnaSumId(docAnaesSummary.getAnaSumId());
								docAnaesSummaryAppendixVisitDao.deleteByAnaSumId(docAnaesSummary.getAnaSumId());
								docAnaesSummaryAppendixGenDao.deleteByAnaSumId(docAnaesSummary.getAnaSumId());
								docAnaesSummaryAppendixCanalDao.deleteByAnaSumId(docAnaesSummary.getAnaSumId());
								docAnaesSummaryDao.deleteByRegOptId(basDispatch.getRegOptId());
							}
							docExitOperSafeCheckDao.deleteByRegOptId(basDispatch.getRegOptId());
							docOperBeforeSafeCheckDao.deleteByRegOptId(basDispatch.getRegOptId());
							docAnaesBeforeSafeCheckDao.deleteByRegOptId(basDispatch.getRegOptId());
							docSafeCheckDao.deleteByRegOptId(basDispatch.getRegOptId());
							docAnaesQualityControlDao.deleteByRegOptId(basDispatch.getRegOptId());
							docAnaesRecordDao.deleteByRegOptId(basDispatch.getRegOptId());
							basDispatchDao.deleteDispatchHold(basDispatch.getRegOptId());
							basRegOptDao.deleteByPrimaryKey(basDispatch.getRegOptId());
							resp.setResultCode("1");
							resp.setResultMessage("删除手术通知单成功!");
						}
					}
				}
			} else {
				resp.setResultCode("0");
				resp.setResultMessage("没有找到申请单号为" + hisCancleOptFormBean.getPreengagementnumber() + "的手术");
			}
			response = getObjectToXml(resp);
			logger.info("-------------------------cancleRegOpt Response----------------------------:"
					+ response);
		} catch (Exception e) {
			logger.info("更改手术状态时出现异常:" + Exceptions.getStackTraceAsString(e));
			throw new RuntimeException(Exceptions.getStackTraceAsString(e));
		}
		logger.info("---------------end cancleRegOpt---------------");
		return response;
	}

	// 创建getObjectToXml方法（将对象转换成XML格式的文件）
	public static <T> String getObjectToXml(T object) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			// 将对象转变为xml Object------XML
			// 指定对应的xml文件
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);// 是否格式化生成的xml串
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);// 是否省略xml头信息

			// 将对象转换为对应的XML文件
			marshaller.marshal(object, byteArrayOutputStream);
		} catch (JAXBException e) {

			e.printStackTrace();
		}
		// 转化为字符串返回
		String xmlContent = new String(byteArrayOutputStream.toByteArray(),
				"UTF-8");
		return xmlContent;
	}

}
