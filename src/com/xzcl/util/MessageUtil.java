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
 * @version 创建时间：2016年2月6日  下午9:10:27
 * 
 * 微信消息处理工具
 */

public class MessageUtil {
	
	public static String MESSAGE_TEXT = "text"; //文本消息
	public static String MESSAGE_IMAGE = "image"; //图片消息
	public static String MESSAGE_VOICE = "voice"; //语音消息
	public static String MESSAGE_VIDEO = "video"; //视频消息
	public static String MESSAGE_SHORTVIDEO = "shortvideo";  //小视频消息
	public static String MESSAGE_LOCATION = "location";  //地理位置消息
	public static String MESSAGE_LINK = "link";		//链接消息
	public static String MESSAGE_EVENT = "event";		//推送 
	
	
	public static String MESSAGE_SUBSCRIBE = "subscribe";	//订阅
	public static String MESSAGE_UNSUBSCRIBE = "unsubscribe";		//取消订阅
	public static String MESSAGE_CLICK = "CLICK";		//点击菜单拉取消息时的事件推送
	public static String MESSAGE_VIEW = "VIEW";		//点击菜单跳转链接时的事件推送
	
	
	/**
	 * XML 转 Map集合
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader =new SAXReader();
		InputStream input = request.getInputStream(); //获取request中的输入流
		Document doc = reader.read(input);			  //读取输入流
		
		Element root = doc.getRootElement();		 //获取XML的根元素
		
		List<Element> list = root.elements();		//放入集合中
		//遍历  把list中存的XML中的根元素及其内容放入放入map中
		for (Element element : list) {
			map.put(element.getName(), element.getText());
		}
		input.close();  //关闭输入流
		return map;
		
	}
	/**
	 * 将文本对象转化为XML
	 * @param textMessage
	 * @return
	 */
	
	public static String textToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());  //替换 吧第二个参数的内容替换成第一个参数的内容
		return xstream.toXML(textMessage);  // 吧对象转化成XML类型
	}
	
	/**
	 * 文本消息回复封装
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
		sb.append("欢迎你的关注，请按照菜单提示进行操作：\n\n")
			.append("1.课程介绍\n")
			.append("2.哈哈哈哈哈哈\n")
			.append("3.\n")
			.append("4.\n")
			.append("回复？到调出此菜单\n");
		return sb.toString();
	}
}
