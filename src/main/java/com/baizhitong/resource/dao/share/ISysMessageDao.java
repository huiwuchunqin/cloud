package com.baizhitong.resource.dao.share;

import com.baizhitong.resource.model.share.SysMessage;

public interface ISysMessageDao {

    /**
     * 查询
     * ()<br>
     * @param messageType
     * @param messageId
     * @return
     */
    SysMessage getMessage(String messageType, String messageId);

    /**
     * 新增
     * ()<br>
     * @param sysMessage
     */
    void insert(SysMessage sysMessage);

}