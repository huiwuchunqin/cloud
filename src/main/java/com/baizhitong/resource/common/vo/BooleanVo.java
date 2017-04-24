package com.baizhitong.resource.common.vo;

public class BooleanVo extends BaseVo {
    /**
     * 代表当前真实的返回值
     */
    private boolean bool;

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    /**
     * 
     * 无参返回正确<br> @return
     * 
     * @exception
     */
    public static BooleanVo true0() {
        return new0(true, null);
    }

    /**
     * 
     * 无参返回错误<br> @return
     * 
     * @exception
     */
    public static BooleanVo false0() {
        return new0(false, null);
    }

    /***
     * 正确返回，参数为提示内容 @param msg @return
     * 
     * @exception
     */
    public static BooleanVo true0(String msg) {
        return new0(true, msg);
    }

    /***
     * 错误返回，参数为错误提示 @param msg @return
     * 
     * @exception
     */
    public static BooleanVo false0(String msg) {
        return new0(false, msg);
    }

    /***
     * 根据result 返回不同的 BooleanVo @param result @return
     * 
     * @exception
     */
    public static BooleanVo new0(boolean result) {
        BooleanVo booleanVo = new BooleanVo();
        booleanVo.bool = result;
        return booleanVo;
    }

    /***
     * 根据result 返回不同的 BooleanVo msg 提示内容 @param result @return
     * 
     * @exception
     */
    public static BooleanVo new0(boolean result, String msg) {
        BooleanVo booleanVo = new BooleanVo();
        booleanVo.bool = result;
        booleanVo.bizMsg = msg;
        return booleanVo;
    }

    /**
     * 返回为对应的resultcode
     * 
     * @param booleanVo
     * @return
     */
    public ResultVo resultVo() {
        return ResultVo.newResultCode(bool, bizCode, bizMsg, null);
    }

    /**
     * 将booleanVo返回为对应的resultcode
     * 
     * @param booleanVo
     * @return
     */
    public static ResultVo resultVo(BooleanVo booleanVo) {
        return ResultVo.newResultCode(booleanVo.bool, booleanVo.bizCode, booleanVo.bizMsg, null);
    }

}
