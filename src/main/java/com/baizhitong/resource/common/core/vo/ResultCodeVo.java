package com.baizhitong.resource.common.core.vo;

/**
 * action通用返回json对象
 * 
 * @author creator cuidc 2015/12/01
 * @author updater
 */
public class ResultCodeVo {
    /** 是否成功(true:成功,false:失败) */
    private Boolean success;
    /** 代码 */
    private String  code;
    /** 消息 */
    private String  msg;
    /** 数据 */
    private Object  data;

    /**
     * action通用返回json对象
     */
    public ResultCodeVo() {

    }

    /**
     * action通用返回json对象
     * 
     * @param success 是否成功
     * @param code 代码
     * @param msg 消息
     */
    public ResultCodeVo(Boolean success, String code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回错误对象
     * 
     * @param msg 信息
     * @return
     * @author gaow
     * @date:2015年12月15日 下午2:33:18
     */
    public static ResultCodeVo errorCode(String msg) {
        return new ResultCodeVo(false, "", msg, null);
    }

    /**
     * 返回错误对象
     * 
     * @param msg 信息
     * @return
     * @author gaow
     * @date:2015年12月15日 下午2:33:18
     */
    public static ResultCodeVo errorCode(String msg, String code) {
        return new ResultCodeVo(false, code, msg, null);
    }

    /**
     * 返回错误对象
     * 
     * @param msg 信息
     * @param data 数据
     * @return
     * @author gaow
     * @date:2015年12月15日 下午2:33:18
     */
    public static ResultCodeVo errorCode(String msg, Object date) {
        return new ResultCodeVo(false, "", msg, date);
    }

    /**
     * 返回正确对象
     * 
     * @param msg 信息
     * @param data 数据
     * @return
     * @author gaow
     * @date:2015年12月15日 下午2:33:18
     */
    public static ResultCodeVo rightCode(String msg, Object date) {
        return new ResultCodeVo(true, "", msg, date);
    }

    /**
     * 返回正确对象
     * 
     * @param msg 信息
     * @return
     * @author gaow
     * @date:2015年12月15日 下午2:33:18
     */
    public static ResultCodeVo rightCode(String msg) {
        return new ResultCodeVo(true, "", msg, null);
    }

    /**
     * action通用返回json对象
     * 
     * @param success 是否成功
     * @param code 代码
     * @param msg 消息
     * @param data 数据
     */
    public ResultCodeVo(Boolean success, String code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 获取是否成功
     * 
     * @return 是否成功
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 设置是否成功
     * 
     * @param success 是否成功
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 获取代码
     * 
     * @return 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置代码
     * 
     * @param code 代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取消息
     * 
     * @return 消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置消息
     * 
     * @param msg 消息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取数据
     * 
     * @return 数据
     */
    public Object getData() {
        return data;
    }

    /**
     * 设置数据
     * 
     * @param data 数据
     */
    public void setData(Object data) {
        this.data = data;
    }
}
