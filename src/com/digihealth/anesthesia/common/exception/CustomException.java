package com.digihealth.anesthesia.common.exception;

import java.io.IOException;

/**
 * 自定义异常
     * Title: CustomException.java    
     * Description: 
     * @author chenyong       
     * @created 2017年7月21日 下午5:24:32
 */
public class CustomException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
		super(message);
	}

	public CustomException(String message, IOException e) {
		super(message, e);
	}
}
