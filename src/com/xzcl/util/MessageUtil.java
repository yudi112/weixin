package com.xzcl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.xzcl.bean.TextMessage;

/**
 * 
 * @author zhaichenlei
 * @version ����ʱ�䣺2016��2��6��  ����9:10:27
 * 
 * ΢����Ϣ������
 */

public class MessageUtil {
	
	public static String MESSAGE_TEXT = "text"; //�ı���Ϣ
	public static String MESSAGE_IMAGE = "image"; //ͼƬ��Ϣ
	public static String MESSAGE_VOICE = "voice"; //������Ϣ
	public static String MESSAGE_VIDEO = "video"; //��Ƶ��Ϣ
	public static String MESSAGE_SHORTVIDEO = "shortvideo";  //С��Ƶ��Ϣ
	public static String MESSAGE_LOCATION = "location";  //����λ����Ϣ
	public static String MESSAGE_LINK = "link";		//������Ϣ
	public static String MESSAGE_EVENT = "event";		//���� 
	
	
	public static String MESSAGE_SUBSCRIBE = "subscribe";	//����
	public static String MESSAGE_UNSUBSCRIBE = "unsubscribe";		//ȡ������
	public static String MESSAGE_CLICK = "CLICK";		//����˵���ȡ��Ϣʱ���¼�����
	public static String MESSAGE_VIEW = "VIEW";		//����˵���ת����ʱ���¼�����
	
	
	/**
	 * XML ת Map����
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader =new SAXReader();
		InputStream input = request.getInputStream(); //��ȡrequest�е�������
		Document doc = reader.read(input);			  //��ȡ������
		
		Element root = doc.getRootElement();		 //��ȡXML�ĸ�Ԫ��
		
		List<Element> list = root.elements();		//���뼯����
		//����  ��list�д��XML�еĸ�Ԫ�ؼ������ݷ������map��
		for (Element element : list) {
			map.put(element.getName(), element.getText());
		}
		input.close();  //�ر�������
		return map;
		
	}
	/**
	 * ���ı�����ת��ΪXML
	 * @param textMessage
	 * @return
	 */
	
	public static String textToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());  //�滻 �ɵڶ��������������滻�ɵ�һ������������
		return xstream.toXML(textMessage);  // �ɶ���ת����XML����
	}
	
	/**
	 * �ı���Ϣ�ظ���װ
	 * @param ToUserName
	 * @param FromUserName
	 * @param content  
	 * @return
	 */
	public static String textInit(String ToUserName,String FromUserName ,String content){
		TextMessage text =new TextMessage();
		text.setFromUserName(ToUserName);
		text.setToUserName(FromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(String.valueOf(new Date().getTime()));
		text.setContent(content);
		return  MessageUtil.textToXml(text);
	}
	
	public static String menuText(){
		StringBuffer sb =new StringBuffer();
		sb.append("��ӭ��Ĺ�ע���밴�ղ˵���ʾ���в�����\n\n")
			.append("1.�γ̽���\n")
			.append("2.������������\n")
			.append("3.\n")
			.append("4.\n")
			.append("�ظ����������˲˵�\n");
		return sb.toString();
	}
}
