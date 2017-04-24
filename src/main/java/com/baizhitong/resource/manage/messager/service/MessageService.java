package com.baizhitong.resource.manage.messager.service;

import com.baizhitong.core.mvc.commons.message.BztMessage;

/**
 * 
 * IMessageService 消息 Service 接口
 * 
 * @author creator gaow 2016年3月3日 下午6:43:53
 * @author updater
 *
 * @version 1.0.0
 */
public interface MessageService {
    /**
     * 接收到消息后，触发<br>
     * 
     * @param message
     */
    public void onMessage(BztMessage message);

    /**
     * 判断当前消息是否需要接收<br>
     * 
     * @param clazz
     * @return
     */
    public boolean isSupported(String messageType);

    /**
     * 发送消息 ()<br>
     * 
     * @param shareCheckStatus
     * @param resCode
     * @param resTypeL1
     * @return
     */
    public boolean sendMessage(Integer shareCheckStatus, String resCode, Integer resTypeL1, String userCode,
                    String applierCode, String applierOrgCode);

}
