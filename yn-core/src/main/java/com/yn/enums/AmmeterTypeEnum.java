package com.yn.enums;

/**
 * Created by Xiang on 2017/7/25.
 */
public enum AmmeterTypeEnum {

    // [电表类型]{1:发电,2:用电}
    GENERATED_ELECTRICITY(1, "发电"),
    USE_ELECTRICITY(2, "用电"),
    ;

    private Integer code;
    private String message;

    AmmeterTypeEnum(Integer code, String message) {
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
