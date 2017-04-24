package com.baizhitong.resource.common.constants;

/**
 * action通用返回错误消息常量定义
 * 
 * @author creator cuidc 2015/10/20
 * @author updater
 */
public class ResultCodeConstants {
    /************************************************** 查询BEGIN **************************************************/
    /** 查询成功 */
    public static final String SELECT_SUCCESS   = "1000";
    /** 查询无数据 */
    public static final String SELECT_NO_DATA   = "1010";
    /** 查询异常 */
    public static final String SELECT_ERROR     = "1099";
                                                /*************************************************** 查询END ***************************************************/

    /************************************************** 保存BEGIN **************************************************/
    /** 保存成功 */
    public static final String SAVE_SUCCESS     = "2000";
    /** 保存异常 */
    public static final String SAVE_ERROR       = "2099";
                                                /*************************************************** 保存END ***************************************************/

    /************************************************** 修改BEGIN **************************************************/
    /** 修改成功 */
    public static final String UPDATE_SUCCESS   = "3000";
    /** 修改异常 */
    public static final String UPDATE_ERROR     = "3099";
                                                /*************************************************** 修改END ***************************************************/

    /************************************************** 删除BEGIN **************************************************/
    /** 删除成功 */
    public static final String DELETE_SUCCESS   = "4000";
    /** 删除异常 */
    public static final String DELETE_ERROR     = "4099";
                                                /*************************************************** 删除END ***************************************************/

    /************************************************** 验证BEGIN **************************************************/
    /** 验证成功 */
    public static final String CHECK_SUCCESS    = "5000";
    /** 验证异常 */
    public static final String CHECK_ERROR      = "5099";
                                                /*************************************************** 验证END ***************************************************/

    /************************************************** 上传BEGIN **************************************************/
    /** 上传成功 */
    public static final String UPLOAD_SUCCESS   = "6000";
    /** 上传异常 */
    public static final String UPLOAD_ERROR     = "6099";
                                                /*************************************************** 上传END ***************************************************/

    /************************************************** 下载BEGIN **************************************************/
    /** 下载成功 */
    public static final String DOWNLOAD_SUCCESS = "7000";
    /** 下载异常 */
    public static final String DOWNLOAD_ERROR   = "7099";
                                                /*************************************************** 下载END ***************************************************/

    /************************************************** 登录BEGIN **************************************************/
    /** 登录成功 */
    public static final String LOGIN_SUCCESS    = "9000";
    /** 未登录 */
    public static final String LOGIN_NOT_LOGGED = "9010";
    /** 登录异常 */
    public static final String LOGIN_ERROR      = "9099";
    /*************************************************** 登录END ***************************************************/
}