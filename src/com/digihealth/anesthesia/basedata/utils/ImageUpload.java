package com.digihealth.anesthesia.basedata.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

public class ImageUpload {

    //base64字符串转化成图片  
    public static boolean GenerateImage(String imgStr,String filePath)  
    {   
    	//对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
        	imgStr = imgStr.substring(imgStr.indexOf(",")+1);
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            //String imgFilePath = "d://222.jpg";//新生成的图片  
            OutputStream out = new FileOutputStream(filePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)   
        {  
            return false;  
        }  
    } 
    
    /**
     * 根据传入二进制文件保存图片
     * @param imgByte
     * @param imgPath
     * @param imgName
     * @return
     */
	public static int saveToImgByByte(byte[] imgByte, String imgPath,
			String imgName) {
		
		int stateInt = 1;
		if (imgByte != null && imgByte.length > 0) {
			try {

				// 将字符串转换成二进制，用于显示图片
				// 将上面生成的图片格式字符串 imgStr，还原成图片显示

				InputStream in = new ByteArrayInputStream(imgByte);

				File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
				FileOutputStream fos = new FileOutputStream(file);

				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = in.read(b)) != -1) {
					fos.write(b, 0, nRead);
				}
				fos.flush();
				fos.close();
				in.close();

			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
			} finally {
			}
		}
		return stateInt;
	}
}
