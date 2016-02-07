package com.xzcl.util;

import java.io.ObjectInputStream.GetField;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 
 * @author zhaizhenlei
 * @version 创建时间：2016年2月6日  下午7:31:30
 * 校验工具类 
 */
public class CheckUtil {
	private static String teken="xzclwx";
	public static boolean checkSignature(String Signature,String timestamp,String nonce){
		String arr[] = new String[]{teken,timestamp,nonce};
		//排序
		Arrays.sort(arr);
		
		//生成字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		
		//sha1加密
		String temp = getSha1(content.toString());
		// 返回 加密记过和Signature 是否相等 如果相等返回true，否则返回false
		return temp.equals(Signature); 
	}
	/**
	 * 
	 * @param str
	 * @return 
	 * sha1加密 返回一个字符串加密结果
	 */
	public static String getSha1(String str){
		if (null == str || 0 == str.length()){
		       return null;
		   }
		   char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		           'a', 'b', 'c', 'd', 'e', 'f'};
		   try {
		       MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
		       mdTemp.update(str.getBytes("UTF-8"));
		        
		       byte[] md = mdTemp.digest();
		       int j = md.length;
		       char[] buf = new char[j * 2];
		       int k = 0;
		       for (int i = 0; i < j; i++) {
		           byte byte0 = md[i];
		           buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
		           buf[k++] = hexDigits[byte0 & 0xf];
		       }
		       return new String(buf);
		   } catch (Exception e) {
		       return null;
		   }
	}
}
