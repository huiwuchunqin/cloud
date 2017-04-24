package com.baizhitong.resource.common.utils.Excel;

import com.baizhitong.utils.StringUtils;

public class MessageHandel {
    /**
     * 处理字符串
     * ()<br>
     * @param msg       消息
     * @param prefix    前缀
     * @param suffix    后缀
     * @return
     */
    public static  String messageHandler(String msg,String prefix,String suffix){
        if(StringUtils.isEmpty(msg)){
            return "";
        }
        StringBuffer buffer=new StringBuffer();
        buffer.append(prefix+":<br/>")
              .append(msg.substring(0,msg.length()-1))
              .append(suffix+"<br/>");
        return buffer.toString();
    }
}
