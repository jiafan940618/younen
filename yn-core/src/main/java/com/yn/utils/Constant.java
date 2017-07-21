package com.yn.utils;

public class Constant {

    public final static String NO_LOGIN = "未登录";
    public final static String EXIST_THIS_USER = "用户已存在";
    public final static String NO_THIS_USER = "用户不存在";
    public final static String PASSWORD_ERROR = "密码不正确";
    public final static String CODE_ERROR = "验证码不正确";
    public final static String LONG_TIME_OUT = "登录已过期";
    public final static String NO_THIS_ADDRESS = "地址不存在";
    public final static String PARAMS_ERROR = "参数错误";
    public final static String NO_PERMISSION = "权限不足";
    public final static String STORER_NO_LOGIN = "请选择商家";

    public static <T> ResultData<T> noLogin(ResultData<T> resultData) {
        error(resultData, 444, Constant.NO_LOGIN);
        return resultData;
    }

    public static <T> ResultData<T> error(ResultData<T> resultData, int code, String Msg) {
        resultData.setMsg(Msg);
        resultData.setCode(code);
        return resultData;
    }

    public interface wxgzh{
        /***** 微信公众号平台 *****/
        // 公众账号ID,微信分配的公众账号ID（企业号corpid即为此appId）
        String appid = "wx6491dcd39c520bb1";
        // 商户号
        String mch_id = "1443603002";
        // 设备号
        String device_info = "WEB";
        // 商户API密匙(商户平台--账户中心--API安全--设置密匙)
        String key = "wx280dc00391e1921396805302t10012";
        // appsecret
        String appsecret = "923f6c9ae2597dd885c2ae3bf0f7109f";

        /***** 微信开放平台 *****/
        String open_appid = "wxabe09b54bed0aa28";

        String open_key = "";

        String open_appsecret = "ac8c2012c628b9b8a7f5c7289af07d46";

        /**
         * 微信支付接口地址
         */
        // 微信支付统一接口(POST)
        String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        String PROJECT_NAME = "";

        String host= "www.ingdu.cn";
        // url
        String URL = host+"/" + PROJECT_NAME;
        // 微信支付统一接口的回调action
        String NOTIFY_URL = "http://" + wxgzh.URL + "/client/wxpay/payReturn";
    }
}
