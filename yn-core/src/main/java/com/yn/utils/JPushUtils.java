package com.yn.utils;

import org.apache.log4j.Logger;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtils {
	private static Logger logger = Logger.getLogger(JPushUtils.class);
	
	/**
	 * 普通appKey
	 */
	public final static String MASTER_SECRET = "560ae484164a7a0addf40ca0";
	
	/**
	 * 普通masterSecret
	 */
	public final static String APP_KEY = "986e0883f82f7b28814c78a2";
	
	/**
	 * 对所有平台推送
	 */
	 public static PushPayload buildPushAll(String alert) {
	        return PushPayload.alertAll(alert);
	    }
	 
	 /**
	   * 对所有平台目标别名为alias的进行推送
	   */
	  public static PushPayload buildPushObjectByAlias(String alias,String alert) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias(alias))
	                .setNotification(Notification.alert(alert))
	                .build();
	    }
	  
		//、静默推送
	    public static PushPayload buildPushObject_memberId_alias_silenceAlert(String alias, String alert) {
	            return PushPayload
	                    .newBuilder()
	                    .setPlatform(Platform.android_ios())
	                    .setAudience(Audience.alias(alias))
	                    .setNotification(
	                            Notification
	                                    .newBuilder()
	                                    .setAlert(alert)
	                                    .addPlatformNotification(
	                                            IosNotification.newBuilder().setContentAvailable(true)
	                                             .build())
	                                    .build())
	                    
	                    .setOptions(Options.newBuilder().setApnsProduction(true).build())        
	                    .build();
	        }
	    
	    public static PushPayload buildPushObject_memberId_alias_silenceAlert(String memberId, String title,int id,int classfyID,int type) {
	        return PushPayload
	                .newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.alias(memberId))
	                .setNotification(
	                        Notification
	                                .newBuilder()
	                                .setAlert("")
	                                .addPlatformNotification(
	                                        IosNotification.newBuilder().setContentAvailable(true).addExtra("classfyID",classfyID)
	                                                .build())
	                                .build())
	                .setMessage(
	                        Message
	                             .newBuilder()
	                             .setMsgContent("")
	                             .addExtra("classfyID",classfyID).build()
	                        )
	                .setOptions(Options.newBuilder().setApnsProduction(true).build())        
	                .build();
	    }
	  
	  /**
	   * 对android平台目标设备为tag的进行推送
	   */
	  public static PushPayload buildPushObjectAndroidByTag(String tag,String alert,String title) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android())
	                .setAudience(Audience.tag(tag))
	                .setNotification(Notification.android(alert, title, null))
	                .build();
	    }
	  
	  /**
	   *平台是 iOS，推送目标是 "tag1", "tag_all" 的交集，推送内容同时包括通知与消息 - 通知信息是 ALERT，
	   *角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容是 MSG_CONTENT。通知是 APNs 
	   *推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
	   */
	  public static PushPayload buildPushObjectIos(String alert,String happy,String from,String JPush,String MSG_CONTENT) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.ios())
	                .setAudience(Audience.tag_and("tag1", "tag_all"))
	                .setNotification(Notification.newBuilder()
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                .setAlert(alert)
	                                .setBadge(5)
	                                .setSound(happy)
	                                .addExtra(from, JPush)
	                                .build())
	                        .build())
	                 .setMessage(Message.content(MSG_CONTENT))
	                 .setOptions(Options.newBuilder()
	                         .setApnsProduction(true)
	                         .build())
	                 .build();
	    }

	  /**
	   * 构建推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的并集）交（"alias1" 与 "alias2" 的并集），
	   * 推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush。
	   */
	    public static PushPayload buildPushObjectIosAndAndorid(String MSG_CONTENT) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.newBuilder()
	                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
	                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
	                        .build())
	                .setMessage(Message.newBuilder()
	                        .setMsgContent(MSG_CONTENT)
	                        .addExtra("from", "JPush")
	                        .build())
	                .build();
	    }

	  /**
	   *  构建推送对象：推送内容包含SMS信息
	   */    
   
	    public static void sendWithSMS(String content,String alias) {
			        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
			        try {
			            SMS sms = SMS.content(content, 10);
		        PushResult result = jpushClient.sendAndroidMessageWithAlias(content, "test sms", sms, alias);
		        logger.info("Got result - " + result);
		    } catch (APIConnectionException e) {
		    	logger.error("Connection error. Should retry later. ", e);
		    } catch (APIRequestException e) {
		    	logger.error("Error response from JPush server. Should review and fix it. ", e);
		    	logger.info("HTTP Status: " + e.getStatus());
		    	logger.info("Error Code: " + e.getErrorCode());
		    	logger.info("Error Message: " + e.getErrorMessage());
				        }
				    }
	    

}
