package com.yn.enums;

/**
 * Created by Xiang on 2017/7/31.
 */
public enum UserSexEnum {

    // [性别]{0:未知,1:男,2:女}
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女"),
    ;

    private Integer code;
    private String message;

    UserSexEnum(Integer code, String message) {
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
