package com.digihealth.anesthesia.interfacedata.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface SybxService {
	@WebMethod
	public @WebResult(name = "response")
	String cancleRegOpt(@WebParam(name = "request") String request);// 取消手术
}
