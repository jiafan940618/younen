package com.yn.model;

import com.yn.domain.IDomain;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;


/**
 * 通知
 */
@Entity
@Data
public class Notice extends IDomain implements Serializable {

    @Column(columnDefinition = "int(2) comment '[通知类型]{1:新增用户,2:新增服务商,3:新增订单,4:新增电站,5:新增电表,6:反馈信息}'")
    private Integer type;

    @Column(columnDefinition = "int(11) comment '[类型的id]'")
    private Long typeId;

    @Column(columnDefinition = "int(11) comment '[用户id]'")
    private Long userId;

    @Column(insertable = false, columnDefinition = "int(2) default 0 comment '[是否已读]{0:未读,1:已读}'")
    private Integer isRead;
}
