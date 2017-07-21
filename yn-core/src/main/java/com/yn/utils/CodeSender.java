package com.yn.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

/**
 * Created by TT on 2017/2/21.
 */
public class CodeSender {
    public static String sendCode(String phone) {
        String url = "http://sapi.253.com/msg/";// 应用地址
        String account = "Lkkj888";// 账号
        String pswd = "Lkkj888888";// 密码
//        String mobile = "13265925915";// 手机号码，多个号码使用","分割
        String code =  getCode();
        String msg = "您好，您的验证码是"+code;// 短信内容
        boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
        String extno = null;// 扩展码

        try {
            String returnString = CodeSender.batchSend(url, account, pswd, phone, msg, needstatus, extno);
            System.out.println(returnString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
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

    public static String batchSend(String url, String account, String pswd, String mobile, String msg,
                                   boolean needstatus, String extno) throws Exception {
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        GetMethod method = new GetMethod();
        try {
            URI base = new URI(url, false);
            method.setURI(new URI(base, "HttpBatchSendSM", false));
            method.setQueryString(new NameValuePair[] {
                    new NameValuePair("account", account),
                    new NameValuePair("pswd", pswd),
                    new NameValuePair("mobile", mobile),
                    new NameValuePair("needstatus", String.valueOf(needstatus)),
                    new NameValuePair("msg", msg),
                    new NameValuePair("extno", extno),
            });
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }
    }
}
