package com.digihealth.anesthesia.basedata.utils;

import java.math.BigDecimal;

public class Arith {
	
    public static float multiply(double value1,double value2){
    	
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        BigDecimal result  = b1.multiply(b2);
        return result.setScale(2, result.ROUND_HALF_UP).floatValue();
    }
    
	public static float add(float value1,float value2){  
	    BigDecimal b1 = new BigDecimal(Float.toString(value1));  
	    BigDecimal b2 = new BigDecimal(Float.toString(value2));  
	    return b1.add(b2).floatValue();  
	}
}
