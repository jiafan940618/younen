package com.yn.enums;

/**
 * Created by Xiang on 2017/7/28.
 */
public enum DevideEnum {

    FIRST_LEVEL(1, "品牌"),
    SECOND_LEVEL(2, "品牌下的设备"),
    ;

    private Integer code;
    private String message;

    DevideEnum(Integer code, String message) {
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
