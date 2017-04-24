package com.baizhitong.resource.common.vo;

/**
 * View Object或者Value Object，用于Action和Service之间的数据通信 <br>
 * 1.主要包括2个重要属性 2.业务编码、业务说明
 * 
 * @author xuaihua
 */
public class BaseVo {

    /**
     * 关联业务码<br>
     * 一般对应于一个字符串常量 代码中通常需要常量类来维护这样的字符串常量！
     */
    protected String bizCode = "SUCCESS_CODE";

    /**
     * 消息
     */
    protected String bizMsg;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bizCode == null) ? 0 : bizCode.hashCode());
        result = prime * result + ((bizMsg == null) ? 0 : bizMsg.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseVo other = (BaseVo) obj;
        if (bizCode == null) {
            if (other.bizCode != null)
                return false;
        } else if (!bizCode.equals(other.bizCode))
            return false;
        if (bizMsg == null) {
            if (other.bizMsg != null)
                return false;
        } else if (!bizMsg.equals(other.bizMsg))
            return false;
        return true;
    }
}
