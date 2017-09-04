package com.yn.service;

import com.yn.dao.NoticeDao;
import com.yn.enums.DeleteEnum;
import com.yn.enums.NoticeEnum;
import com.yn.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {


    @Autowired
    private NoticeDao noticeDao;


    /**
     * 批量新增
     *
     * @param type
     * @param typeId
     * @param userIds
     */
    public void insertBatch(Integer type, Long typeId, List<Long> userIds) {
        for (Long userId : userIds) {
            Notice notice = new Notice();
            notice.setType(type);
            notice.setTypeId(typeId);
            notice.setUserId(userId);
            noticeDao.save(notice);
        }
    }


    /**
     * 查找是否未读
     *
     * @param type
     * @param typeId
     * @param userId
     * @return
     */
    public Boolean findIsNew(Integer type, Long typeId, Long userId) {


        Notice notice = new Notice();
        notice.setType(type);
        notice.setTypeId(typeId);
        notice.setUserId(userId);
        notice.setIsRead(NoticeEnum.UN_READ.getCode());
        notice.setDel(DeleteEnum.NOT_DEL.getCode());


        Notice result = noticeDao.findOne(Example.of(notice));
        if (result != null) {
            return true;
        }


        return false;
    }


}
