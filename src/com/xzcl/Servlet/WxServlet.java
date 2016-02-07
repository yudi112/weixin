package com.xzcl.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xzcl.bean.TextMessage;
import com.xzcl.util.CheckUtil;
import com.xzcl.util.MessageUtil;

/**
 * Servlet implementation class wx
 */
public class WxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public WxServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature"); //微信加密签名
		String timestamp = request.getParameter("timestamp"); //时间戳
		String nonce = request.getParameter("nonce");		  //随机数
		String echostr = request.getParameter("echostr");	  //随机字符串
		
		PrintWriter out = response.getWriter();
		
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Map<String,String> map = MessageUtil.xmlToMap(request);
			String ToUserName = map.get("ToUserName");
			String FromUserName = map.get("FromUserName");
			String MsgType = map.get("MsgType");
			String Content = map.get("Content");
			
			String message = null;
			
			
			if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){    			//文本消息回复
				System.out.println(Content);
				if("1".equals(Content)){
					System.out.println("======================");
					message = MessageUtil.textInit(ToUserName, FromUserName, "课程介绍来了~~~~~~~~~~~~~~~~~~~~~~~~~ " );
				}else if("?".equals(Content) || "？".equals(Content)){
					message = MessageUtil.textInit(ToUserName, FromUserName, MessageUtil.menuText());
				}else{
					message = MessageUtil.textInit(ToUserName, FromUserName, "收到你的消息： " + Content);
				}
				
			}else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)){
				String evenType = map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(evenType)){
					message = MessageUtil.textInit(ToUserName, FromUserName, MessageUtil.menuText());
				}
				
			}
			out.print(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}

}
