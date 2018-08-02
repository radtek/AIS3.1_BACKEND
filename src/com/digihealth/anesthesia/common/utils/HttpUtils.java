package com.digihealth.anesthesia.common.utils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;



public class HttpUtils {
	
	private static Logger logger = Logger.getLogger(HttpUtils.class);
	
	public static void main(String[] args) throws Exception {
        //请求的url
        doHttpPost("{regOptId:'201709251343410000'}", "http://192.168.5.5:10087/operCtl/stopCollectService.action");
    }
	
	/**
     * 发送Http post请求
     * 
     * @param xmlInfo
     *            json转化成的字符串
     * @param URL
     *            请求url
     * @return 返回信息
     */
	public static String doHttpPost(String xmlInfo, String URL) {
        
        logger.info("----------------start doHttpPost----------------xmlInfo="+xmlInfo);
        logger.info("----------------start doHttpPost----------------URL="+URL);
        
        byte[] xmlData = xmlInfo.getBytes();
        InputStream instr = null;
        java.io.ByteArrayOutputStream out = null;
        try {
            URL url = new URL(URL);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("content-Type", "application/json");
            urlCon.setRequestProperty("charset", "utf-8");
            urlCon.setRequestProperty("Content-length",
                    String.valueOf(xmlData.length));
            System.out.println(String.valueOf(xmlData.length));
            DataOutputStream printout = new DataOutputStream(
                    urlCon.getOutputStream());
            printout.write(xmlData);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = org.apache.commons.io.IOUtils.toByteArray(instr);
            String ResponseString = new String(bis, "UTF-8");            
            logger.info("------------end doHttpPost----------------ResponseString="+ResponseString);
            return ResponseString;

        } catch (Exception e) {
        	 logger.info("---------------- doHttpPost----------------Exceptions1="+Exceptions.getStackTraceAsString(e));
            return Exceptions.getStackTraceAsString(e);
        } finally {
            try {
                //out.close();
                instr.close();

            } catch (Exception ex) {
            	 logger.info("---------------- doHttpPost----------------Exceptions2="+Exceptions.getStackTraceAsString(ex));
                return Exceptions.getStackTraceAsString(ex);
            }
        }
       
    }
}
