package com.digihealth.anesthesia.interfacedata.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface RxrmService
{
    @WebMethod
    public @WebResult(name = "response")
    String cancleRegOpt(@WebParam(name = "request")String request);//取消手术
    
    @WebMethod
    public @WebResult(name = "response")
    String getHisOperateNotice(@WebParam(name = "request")String request);//获取手术通知
}
