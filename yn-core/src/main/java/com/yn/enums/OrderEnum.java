package com.yn.enums;

/**
 * Created by Xiang on 2017/7/25.
 */
public enum OrderEnum {

    // [订单状态]{0:申请中,1:施工中,2:并网发电申请中,3:并网发电}
    STATUS_APPLY(0, "申请中"),
    STATUS_BUILD(1, "施工中"),
    STATUS_GRIDCONNECTED_APPLY(2, "并网发电申请中"),
    STATUS_GRIDCONNECTED(3, "并网发电"),;

    private Integer code;
    private String message;

    OrderEnum(Integer code, String message) {
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
