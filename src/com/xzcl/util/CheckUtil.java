package com.xzcl.util;

import java.io.ObjectInputStream.GetField;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 
 * @author zhaizhenlei
 * @version ����ʱ�䣺2016��2��6��  ����7:31:30
 * У�鹤���� 
 */
public class CheckUtil {
	private static String teken="xzclwx";
	public static boolean checkSignature(String Signature,String timestamp,String nonce){
		String arr[] = new String[]{teken,timestamp,nonce};
		//����
		Arrays.sort(arr);
		
		//�����ַ���
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		
		//sha1����
		String temp = getSha1(content.toString());
		// ���� ���ܼǹ���Signature �Ƿ���� �����ȷ���true�����򷵻�false
		return temp.equals(Signature); 
	}
	/**
	 * 
	 * @param str
	 * @return 
	 * sha1���� ����һ���ַ������ܽ��
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
