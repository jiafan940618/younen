package com.yn.enums;

/**
 * Created by Xiang on 2017/7/25.
 */
public enum ResultEnum {

    NO_LOGIN(444, "未登陆"),
    EXIST_THIS_USER(777, "用户已存在"),
    NO_THIS_USER(777, "用户不存在"),
    PASSWORD_ERROR(777, "密码不正确"),
    CODE_ERROR(777, "验证码不正确"),
    LONG_TIME_OUT(777, "登录已过期"),
    NO_THIS_ADDRESS(777, "地址不存在"),
    PARAMS_ERROR(777, "参数错误"),
    NO_PERMISSION(777, "权限不足"),


    NO_CHOOSE_STATION(777, "未选择电站"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
