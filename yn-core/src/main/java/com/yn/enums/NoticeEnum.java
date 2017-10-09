package com.yn.enums;

/**
 * Created by Xiang on 2017/7/25.
 */
public enum NoticeEnum {


    // [通知类型]{1:新增用户,2:新增服务商,3:新增订单,4:新增电站,5:新增电表,6:反馈信息}
    NEW_USER(1, "新增用户"),
    NEW_SERVER(2, "新增服务商"),
    NEW_ORDER(3, "新增订单"),
    NEW_STATION(4, "新增电站"),
    NEW_AMMETER(5, "新增电表"),
    NEW_FEEDBACK(6, "反馈信息"),


    // [是否已读]{0:未读,1:已读}
    UN_READ(0, "未读"),
    READ(1, "已读"),;

    private Integer code;
    private String message;

    NoticeEnum(Integer code, String message) {
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
