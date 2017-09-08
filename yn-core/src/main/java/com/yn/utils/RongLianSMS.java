package com.yn.utils;

import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.cloopen.rest.sdk.CCPRestSDK;

public class RongLianSMS {

	public static CCPRestSDK restAPI;

	static {
		restAPI = new CCPRestSDK();
		restAPI.init("sandboxapp.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount("8aaf07085600cabd01560716f4190726", "c21f8feb7e8e4a39aeccb2b6ed24251f");// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId("8aaf07085600cabd01560716f482072c");// 初始化应用ID
	}

	/**
	 *  请求验证码
	 * @param phone
	 * @return
	 */
	public static String sendCode(String phone) {
		String code = getCode();
		HashMap<String, Object> result = restAPI.sendTemplateSMS(phone, "103488", new String[] { code, "5分钟" });
		System.out.println("SDKTestSendTemplateSMS result=" + result);
		if("000000".equals(result.get("statusCode"))){
			return code;
		} else {
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			return null;
		}
	}
	
	/**
	 * 短信通知客户
	 * @param phone
	 * @return
	 */
	public static int sendMsg4User(String phone, String userName, String orderCode, String msgContent) {
		HashMap<String, Object> result = restAPI.sendTemplateSMS(phone, "103873", new String[] {userName,orderCode,msgContent});
		System.out.println("SDKTestSendTemplateSMS result=" + result);
		if("000000".equals(result.get("statusCode"))){
			return 1;
		} else {
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			return 0;
		}
	}
	
	/**
	 * 短信通知服务商
	 */
	public static int sendMsg4Server(String phone, String msgContent) {
		HashMap<String, Object> result = restAPI.sendTemplateSMS(phone, "104298", new String[] {msgContent});
		System.out.println("SDKTestSendTemplateSMS result=" + result);
		if("000000".equals(result.get("statusCode"))){
			return 1;
		} else {
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			return 0;
		}
	}
	
	/**
	 * 服务商提现-短信通知
	 */
	public static int sendMsg4ServerTiXian(String phone, String msg1, String msg2) {
		HashMap<String, Object> result = restAPI.sendTemplateSMS(phone, "119120", new String[] {msg1, msg2});
		System.out.println("SDKTestSendTemplateSMS result=" + result);
		if("000000".equals(result.get("statusCode"))){
			return 1;
		} else {
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			return 0;
		}
	}
	
	/**
	 * 获取随机验证码
	 */
	public static String getCode() {
		Random random = new Random();
		String s = String.valueOf(random.nextInt());
		String code = s.substring(s.length() - 7, s.length() - 1);
		if (code.length() != 6)
			code = getCode();
		return code;
	}
	
	public static void sendWarn(String phone, String storeName, String time) {
		HashMap<String, Object> result = restAPI.sendTemplateSMS(phone, "88803", new String[] { storeName, time });
		System.out.println("SDKTestSendTemplateSMS result=" + result);
		/*if("000000".equals(result.get("statusCode"))){
		} else {
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}*/
	}
	
	/**
	 * 验证短信时效性
	 * @param sessionName
	 * @param httpSession
	 * @return
	 */
	public static ResultData checkMsgTimeOut(String sessionName, HttpSession httpSession) {
		ResultData resultData = new ResultData<>();
		// 如果短信验证码超过了5分钟
		Long codeTime = (Long)httpSession.getAttribute(sessionName);
		if (codeTime==null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		
		Long spaceTime = System.currentTimeMillis() - codeTime;
		if(spaceTime > 300000){
			httpSession.setAttribute(sessionName,null);
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("该验证码已失效，请重新获取");
			return resultData;
		}
		return resultData;
	}

}
