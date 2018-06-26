package com.sendyago.util.common;

import java.security.MessageDigest;
import java.util.LinkedHashMap;

/**
 * ================================================== 
 * 数据转换工具类
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/06/03 9:20:20$
 * ==================================================
 */
public class Convert {
	

	/**
	 * UUID转换
	 * 
	 */
	public static String uuidConvert() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * MD5加密数据转换
	 * 
	 */
	public final static String md5Convert(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * WebService接口参数转换
	 * 
	 */
	public static LinkedHashMap<String, Object> paramConvert(String param) {
		String[] params = param.split(",");
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < params.length; i++) {
			if (!"".equals(params[i])) {
				map.put("param" + i, params[i]);
			}
		}
		return map;
	}
	
}
