package com.yn.enums;

/**
 * Created by Xiang on 2017/7/28.
 */
public enum StationStatusEnum {

    // [电站状态]{0:未绑定电表,1:正在发电}
    NOT_BINDING_AMMETER(0, "未绑定电表"),
    ELECTRICITY_GENERATING(1, "正在发电"),
    ;

    private Integer code;
    private String message;

    StationStatusEnum(Integer code, String message) {
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
