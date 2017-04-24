package com.baizhitong.resource.common.constants;

import com.mchange.v2.c3p0.stmt.StatementCache;

/**
 * 通用返回消息常量定义
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public class CodeConstants {
    /************************************************** 查询BEGIN **************************************************/
    /** 查询成功 */
    public static final String  SELECT_SUCCESS        = "1000";
    /** 查询无数据 */
    public static final String  SELECT_NO_DATA        = "1010";
    /** 查询参数异常 */
    public static final String  SELECT_PARAM_ERROR    = "1020";
    /** 查询异常 */
    public static final String  SELECT_ERROR          = "1099";
                                                      /*************************************************** 查询END ***************************************************/

    /************************************************** 保存BEGIN **************************************************/
    /** 保存成功 */
    public static final String  SAVE_SUCCESS          = "2000";
    /** 保存异常 */
    public static final String  SAVE_ERROR            = "2099";
                                                      /*************************************************** 保存END ***************************************************/

    /************************************************** 修改BEGIN **************************************************/
    /** 修改成功 */
    public static final String  UPDATE_SUCCESS        = "3000";
    /** 修改异常 */
    public static final String  UPDATE_ERROR          = "3099";
                                                      /*************************************************** 修改END ***************************************************/

    /************************************************** 删除BEGIN **************************************************/
    /** 删除成功 */
    public static final String  DELETE_SUCCESS        = "4000";
    /** 删除异常 */
    public static final String  DELETE_ERROR          = "4099";
                                                      /*************************************************** 删除END ***************************************************/

    /************************************************** 验证BEGIN **************************************************/
    /** 验证成功 */
    public static final String  CHECK_SUCCESS         = "5000";
    /** 验证异常 */
    public static final String  CHECK_ERROR           = "5099";
                                                      /*************************************************** 验证END ***************************************************/

    /************************************************** 上传BEGIN **************************************************/
    /** 上传成功 */
    public static final String  UPLOAD_SUCCESS        = "6000";
    /** 上传异常 */
    public static final String  UPLOAD_ERROR          = "6099";
                                                      /*************************************************** 上传END ***************************************************/

    /************************************************** 下载BEGIN **************************************************/
    /** 下载成功 */
    public static final String  DOWNLOAD_SUCCESS      = "7000";
    /** 下载异常 */
    public static final String  DOWNLOAD_ERROR        = "7099";
                                                      /*************************************************** 下载END ***************************************************/

    /************************************************** 登录BEGIN **************************************************/
    /** 登录成功 */
    public static final String  LOGIN_SUCCESS         = "9000";
    /** 登录失败 */
    public static final String  LOGIN_FAIL            = "9010";
    /** 登录参数异常 */
    public static final String  LOGIN_PARAM_ERROR     = "9020";
    /** 未登录 */
    public static final String  LOGIN_NOT_LOGGED      = "9030";
    /** 登录无效 */
    public static final String  LOGIN_INVALID         = "0";
    /** 登录锁定 */
    public static final String  LOGIN_LOCK            = "2";
    /** 登录异常 */
    public static final String  LOGIN_ERROR           = "9099";
    /*************************************************** 登录END ***************************************************/
    /** 禁止登录 */
    public static final Integer FLAG_FORBID_LOGIN_YES = 1;
    /** 允许登录 */
    public static final Integer FLAG_FORBID_LOGIN_NO  = 0;
    /** 登录账号存在 */
    public static final String  FLAG_ACCOUNT_EXIST    = "8000";
    /*************************************************** 登录类型 ***************************************************/
    /**账号密码登录*/
    public static final String LOGIN_TYPE_ACCOUNT_PASSWORD="10";
   
    
    /*************************************************** 登录类型 ***************************************************/
    /************************************************** |以上已确认| **************************************************/
}
