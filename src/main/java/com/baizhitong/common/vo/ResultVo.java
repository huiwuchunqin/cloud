package com.baizhitong.common.vo;

import java.awt.print.Pageable;

import com.baizhitong.resource.common.vo.BaseVo;

public class ResultVo extends BaseVo {
    /** 是否成功(true:成功,false:失败) */
    private Boolean  success;
    /** 数据 */
    private Object   data;
    /**
     * 分页属性（分页时使用）
     */
    private Pageable page;

    /**
     * Action通用返回JSON对象
     */
    public ResultVo() {

    }

    /**
     * Action通用返回json对象
     * 
     * @param success 是否成功
     * @param bizCode 代码
     * @param msg 消息
     */
    public ResultVo(Boolean success, String bizCode, String bizMsg) {
        this.success = success;
        this.bizCode = bizCode;
        this.bizMsg = bizMsg;
    }

    /**
     * Action通用返回JSON对象
     * 
     * @param success 是否成功
     * @param code 代码
     * @param msg 消息
     * @param data 数据
     */
    public ResultVo(Boolean success, String code, String bizMsg, Object data) {
        this.success = success;
        this.bizMsg = bizMsg;
        this.bizMsg = bizMsg;
        this.data = data;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizMsg() {
        return bizMsg;
    }

    public void setBizMsg(String bizMsg) {
        this.bizMsg = bizMsg;
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

    /**
     * 
     * (获取分页)<br>
     * 
     * @return 分页
     */
    public Pageable getPage() {
        return page;
    }

    /**
     * 
     * (设置分页)<br>
     * 
     * @param page
     */
    public void setPage(Pageable page) {
        this.page = page;
    }

    /**
     * (无参数生成错误码)<br>
     * 
     * @return ResultVo
     */
    public static ResultVo errorCode() {
        return newResultCode(false, null, null, null);
    }

    /**
     * bizMsg生成错误码
     * 
     * @param bizCode
     * @param data
     * @return
     */
    public static ResultVo errorCode(String bizCode, Object data) {
        return newResultCode(false, bizCode, null, data);
    }

    /**
     * (bizMsg生成错误码)<br>
     * 
     * @param bizCode 错误编码
     * @return ResultVo
     */
    public static ResultVo errorCode(String bizCode) {
        return newResultCode(false, bizCode, null, null);
    }

    /**
     * (data返回数据)<br>
     * 
     * @return ResultVo
     */
    public static ResultVo successCode(Object data) {
        return newResultCode(true, null, null, data);
    }

    /**
     * (无参数生成错误码)<br>
     * 
     * @return ResultVo
     */
    public static ResultVo successCode() {
        return newResultCode(true, null, null, null);
    }

    /**
     * 
     * ()<br>
     * 
     * @param success 是否成功
     * @param code 消息业务码
     * @param msg 消息字符串
     * @param data 绑定的返回数据
     * @return ResultVo
     */
    public static ResultVo newResultCode(boolean success, String bizCode, String bizMsg, Object data) {
        ResultVo resultCode = new ResultVo();
        resultCode.success = success;
        resultCode.bizCode = bizCode;
        resultCode.bizMsg = bizMsg;
        resultCode.data = data;
        return resultCode;
    }
    //
    // /**
    // *
    // * @param vo
    // * @param successBizCode
    // * @return
    // */
    // public static <T extends BaseVo> ResultVo newResultCode(T vo, String successBizCode) {
    // ResultVo resultCode = new ResultVo();
    // resultCode.success = vo.bizCode.equals(successBizCode);
    // resultCode.bizCode = vo.bizCode;
    // resultCode.bizMsg = vo.bizMsg;
    // resultCode.data = vo;
    // return resultCode;
    // }

    /**
     * 
     * (分页使用，开发人员调用page中resultVo方法，无需调用此方法)<br>
     * 
     * @param success 是否成功
     * @param code 消息业务码
     * @param msg 消息字符串
     * @param data 绑定的返回数据
     * @return ResultVo
     */
    public static ResultVo newResultCode(boolean success, String bizCode, String bizMsg, Pageable page, Object data) {
        ResultVo resultCode = new ResultVo();
        resultCode.success = success;
        resultCode.bizCode = bizCode;
        resultCode.bizMsg = bizMsg;
        resultCode.page = page;
        resultCode.data = data;
        return resultCode;
    }
}
