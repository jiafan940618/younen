package com.yn.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yn.utils.JPushUtils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

@Service
public class JPushService {
	
	private static Logger logger = Logger.getLogger(JPushService.class);
	
	/**
	 * appKey
	 */
	public final static String MASTER_SECRET = "560ae484164a7a0addf40ca0";
	
	/**
	 * masterSecret
	 */
	public final static String APP_KEY = "986e0883f82f7b28814c78a2";
	
	/**
	 * 对所有平台
	 */
	public void JPushAll(String alert) {
		
		   JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

		    // For push, all you need do is to build PushPayload object.
		    PushPayload payload = JPushUtils.buildPushAll(alert);

		    try {
		        PushResult result = jpushClient.sendPush(payload);
		        logger.info("Got result - " + result);

		    } catch (APIConnectionException e) {
		        // Connection error, should retry later
		    	logger.error("Connection error, should retry later", e);

		    } catch (APIRequestException e) {
		        // Should review the error, and fix the request
		    	logger.error("Should review the error, and fix the request", e);
		    	logger.info("HTTP Status: " + e.getStatus());
		    	logger.info("Error Code: " + e.getErrorCode());
		    	logger.info("Error Message: " + e.getErrorMessage());
		    }
		
	}
	 /**
	   * 对所有平台目标别名为alias的进行推送
	   */
	public void JPushByAlias(String alert,String alias) {
		
		   JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

		    // For push, all you need do is to build PushPayload object.
		    PushPayload payload = JPushUtils.buildPushObjectByAlias( alias, alert);

		    try {
		        PushResult result = jpushClient.sendPush(payload);
		        logger.info("Got result - " + result);

		    } catch (APIConnectionException e) {
		        // Connection error, should retry later
		    	logger.error("Connection error, should retry later", e);

		    } catch (APIRequestException e) {
		        // Should review the error, and fix the request
		    	logger.error("Should review the error, and fix the request", e);
		    	logger.info("HTTP Status: " + e.getStatus());
		    	logger.info("Error Code: " + e.getErrorCode());
		    	logger.info("Error Message: " + e.getErrorMessage());
		    }
		
	}
	/**
	   * 对所有平台目标别名为alias的进行静默推送
	   */
	
	public void JPushAndroidByQuite(String alert,String alias) {
		
		   JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

		    // For push, all you need do is to build PushPayload object.
		    PushPayload payload = JPushUtils.buildPushObject_memberId_alias_silenceAlert( alias, alert);

		    try {
		        PushResult result = jpushClient.sendPush(payload);
		        logger.info("Got result - " + result);

		    } catch (APIConnectionException e) {
		        // Connection error, should retry later
		    	logger.error("Connection error, should retry later", e);

		    } catch (APIRequestException e) {
		        // Should review the error, and fix the request
		    	logger.error("Should review the error, and fix the request", e);
		    	logger.info("HTTP Status: " + e.getStatus());
		    	logger.info("Error Code: " + e.getErrorCode());
		    	logger.info("Error Message: " + e.getErrorMessage());
		    }
		
	}
	public void JPushIos(String alert) {
		
		   JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

		    // For push, all you need do is to build PushPayload object.
		    PushPayload payload = JPushUtils.buildPushAll(alert);

		    try {
		        PushResult result = jpushClient.sendPush(payload);
		        logger.info("Got result - " + result);

		    } catch (APIConnectionException e) {
		        // Connection error, should retry later
		    	logger.error("Connection error, should retry later", e);

		    } catch (APIRequestException e) {
		        // Should review the error, and fix the request
		    	logger.error("Should review the error, and fix the request", e);
		    	logger.info("HTTP Status: " + e.getStatus());
		    	logger.info("Error Code: " + e.getErrorCode());
		    	logger.info("Error Message: " + e.getErrorMessage());
		    }
		
	}

}
