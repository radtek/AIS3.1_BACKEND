package com.digihealth.anesthesia.doc.controller;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.PostOperRegardFormBean;
import com.digihealth.anesthesia.doc.po.DocPostOperRegard;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;

@Controller
@RequestMapping("/document")
public class DocPostOperRegardController extends BaseController{
	
	@RequestMapping("/searchPostOperRegard")
	@ResponseBody
    public String searchPostOperRegard(@RequestBody JSONObject jsonObject)
    {
        logger.info("------------------start searchPostOperRegard------------------------");
        ResponseValue res = new ResponseValue();
        String regOptId = jsonObject.get("regOptId").toString();
        if (StringUtils.isNotBlank(regOptId))
        {
            PostOperRegardFormBean bean = docPostOperRegardService.searchPostOperRegard(regOptId);
            SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
            res.put("regOptItem", searchRegOptByIdFormBean);
            res.put("bean", bean);
            res.setResultCode("1");
            res.setResultMessage("查询成功！");
        }
        else
        {
            res.setResultCode("70000000");
            res.setResultMessage(Global.getRetMsg(res.getResultCode()));
        }
        logger.info("------------------end searchPostOperRegard------------------------");
        return res.getJsonStr();
    }
	
	
	@RequestMapping("/updatePostOperRegard")
	@ResponseBody
    public String updatePostOperRegard(@RequestBody DocPostOperRegard postOperRegard)
    {
        logger.info("------------------start updatePostOperRegard------------------------");
        ResponseValue res = new ResponseValue();
        if (null != postOperRegard)
        {
            docPostOperRegardService.updatePostOperRegard(postOperRegard);
            res.setResultCode("1");
            res.setResultMessage("修改成功！");
        }
        else
        {
            res.setResultCode("70000000");
            res.setResultMessage(Global.getRetMsg(res.getResultCode()));
        }
        logger.info("------------------end updatePostOperRegard------------------------");
        return res.getJsonStr();
    }
	
}
