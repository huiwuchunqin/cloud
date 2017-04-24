package com.baizhitong.resource.common.constants;

/**
 * 核心常量定义
 * 
 * @author creator cuidc 2015/10/20
 * @author updater
 */
public class CoreConstants {

    /**************************************** 分页BEGIN ****************************************/
    /** 每页条数 */
    public static final Integer DEFAULT_PAGE_SIZE                  = 10;

    /**************************************** Cookie BEGIN ****************************************/
    /** cookie最大值 */
    public static final Integer COOKIE_MAXAGE                      = 60 * 60 * 24;

    /**************************************** 资源一级分类BEGIN ****************************************/
    /** 10：视频 */
    public static final Integer RES_TYPE_MEDIA                     = 10;
    /** 11：特色资源视频 */
    public static final Integer RES_TYPE_SPECIAL_MEDIA             = 11; 
    /** 12：音频资源 */
    public static final Integer RES_TYPE_AUDIO                     = 12;
    /** 15：互动资源 */
    public static final Integer RES_TYPE_FLASH                     = 15;
    /** 20：文档 */
    public static final Integer RES_TYPE_DOC                       = 20;
    /** 30：练习 */
    public static final Integer RES_TYPE_EXERCISE                  = 30;
    /** 50：题库 */
    public static final Integer RES_TYPE_QUESTION                  = 50;
    /** 60：课时 */
    public static final Integer RES_TYPE_COURSE                    = 60;
                                                                   
    /**************************************** 资源操作类型BEGIN ****************************************/
    /** 赞 */
    public static final Integer RES_OPERATE_TYPE_GOOD              = 10;
    /** 踩 */
    public static final Integer RES_OPERATE_TYPE_STEP              = 20;
    /** 收藏 */
    public static final Integer RES_OPERATE_TYPE_FAVORITE          = 30;
    /** 观看 */
    public static final Integer RES_OPERATE_TYPE_BROWSE            = 40;
    /** 引用 */
    public static final Integer RES_OPERATE_TYPE_REFERENCE         = 50;
    /** 下载 */
    public static final Integer RES_OPERATE_TYPE_DOWNLOAD          = 60;

    /**************************************** 资源操作类型END ****************************************/

    /**************************************** 组卷方式BEGIN ****************************************/
    /** 自动组卷 */
    public static final String  TESTPAPER_BUILD_MODE_AUTO          = "auto";
    /** 手动组卷 */
    public static final String  TESTPAPER_BUILD_MODE_MANUAL        = "manual";

    /**************************************** 文件夹图标BEGIN ****************************************/
    /** 视频 */
    public static final String  FOLDER_ICON_MEDIA                  = "folder_media.png";
    /** 文档 */
    public static final String  FOLDER_ICON_DOC                    = "folder_doc.png";
    /** 试卷 */
    public static final String  FOLDER_ICON_TESTPAPER              = "folder_testpaper.png";
    /** 试题 */
    public static final String  FOLDER_ICON_QUESTION               = "folder_question.png";
    /** 其他 */
    public static final String  FOLDER_ICON_OTHER                  = "folder_other.png";
    /**************************************** 文件夹图标END ****************************************/

    /**************************************** 文件夹状态BEGIN ****************************************/ 
    /** 继承父目录权限 */
    public static final Integer FOLDER_STATUS_EXTENDED_FROM_PARENT = 00;
    /** 无权限 */
    public static final Integer FOLDER_STATUS_NO_PERMISSION        = 10;
    /** 只读 */
    public static final Integer FOLDER_STATUS_READONLY             = 20;
    /** 可读可写 */
    public static final Integer FOLDER_STATUS_WRITE                = 30;
    /**************************************** 文件夹状态END ****************************************/

    /** 用户登录验证 */
    public static final String  SESSION_USER_LOGIN_CAPTCHA_KEY     = "USER_LOGIN_CAPTCHA";

    /**************************************** SESSION验证key ****************************************/
    /** 登录账号无效 */
    public static final Integer LOGIN_INVALID                      = 0;
    /** 登录账号有效 */
    public static final Integer LOGIN_EFFECTIVE                    = 1;
    /** 登录账号锁定 */
    public static final Integer LOGIN_LOCK                         = 2;

    /**************************************** 资源审核状态 BEGIN ****************************************/
    /** 10：审核中 */
    public static final Integer CHECK_STATUS_CHECKING              = 10;
    /** 20：审核通过 */
    public static final Integer CHECK_STATUS_CHECKED               = 20;
    /** 5：审核不通过 */
    public static final Integer CHECK_STATUS_UNAPPROVE             = 05; 
    /** 0：未提交 */
    public static final Integer CHECK_STATUS_UNCOMMIT              = 0;

    /**************************************** 资源状态 BEGIN ****************************************/ 
    /** 资源转码中 */
    public static final Integer RESOURCE_STATE_CONVERTING          = 10;
    /** 转码成功 */
    public static final Integer RESOURCE_STATE_CONVERT_SUCCESS     = 20;
    /** 转码失败 */
    public static final Integer RESOURCE_STATE_CONVERT_FAIL        = 30;

    /*** 文件转换异步地址 */
    public static final String  CONVERT_SRC_ASYNCHRONOUS           = "/convert/mangodb";
    /*** 文件转换同步地址 */
    public static final String  CONVERT_SRC_SYNCHRONOUS            = "/convert/synmangodb";
    /*** 视频音频转换进度查询地址 */
    public static final String  CONVERT_SRC_SELECTPROCESS          = "/convert/converProcess";
    /*** 检查文件是否存在于mangoDB中 */
    public static final String  CONVERT_SRC_MANGODBEXISTS          = "/convert/mangoExists";
    /*** 文本转音频地址 */
    public static final String  TEXT_TO_SPEECH_URL                 = "/tts/text2Speech?text=";

    /** 转为互动ppt */
    public static final Integer DYNAMICPPT_STATUS_TRUE             = 1;

    /** 转码服务器提供-转码状态-成功 */
    public static final String  TRANSCODING_STATUS_SUCCESS         = "1";

    /**************************************** 资源状态 END ****************************************/

    /**************************************** 共享级别 BEGIN ****************************************/
    /** 私有 */
    public static final Integer RESOURCE_SHARE_LEVEL_PRIVATE       = 10;
    /** 机构 */
    public static final Integer RESOURCE_SHARE_LEVEL_COMPANY       = 20;
    /** 县级 */
    public static final Integer RESOURCE_SHARE_LEVEL_TOWN          = 30;
    /** 地方 */
    public static final Integer RESOURCE_SHARE_LEVEL_CITY          = 40;
    /** 省级 */
    public static final Integer RESOURCE_SHARE_LEVEL_PROVINCE      = 50;
    /** 公开 */
    public static final Integer RESOURCE_SHARE_LEVEL_OPEN          = 60;

    /**************************************** 是否允许浏览 BEGIN ****************************************/
    /** 允许浏览 */
    public static final Integer RESOURCE_ALLOW_BROWSER_NO          = 0;
    /** 不允许 */
    public static final Integer RESOURCE_ALLOW_BROWSER_YES         = 1;

    /**************************************** 是否允许下载 BEGIN ****************************************/
    /** 允许浏览 */
    public static final Integer RESOURCE_ALLOW_DOWNLOAD_NO         = 0;
    /** 不允许 */
    public static final Integer RESOURCE_ALLOW_DOWNLOAD_YES        = 1;

    /**************************************** 文件夹属性 BEGIN ***************************************/
    /** 是系统文件夹 */
    public static final Integer FLAG_SYSTEM_FOLDER_TRUE            = 1;
    /** 不是系统文件夹 */
    public static final Integer FLAG_SYSTEM_FOLDER_FALSE           = 0;

    /**************************************** 资源下载属性 BEGIN ***************************************/
    /** 允许下载 */
    public static final Integer FLAG_DOWNLOAD_ALLOW_TRUE           = 1;
    /** 不允许下载 */
    public static final Integer FLAG_DOWNLOAD_ALLOW_FALSE          = 0;

    /*********************** 权限角色编码 START **************************************/
    /** 区域管理员 */
    public static final String  USER_ROLE_AREA_ADMIN_CODE          = "10";
    /** 学校管理员 */
    public static final String  USER_ROLE_SCHOOL_ADMIN_CODE        = "20";
    /** 教研员 */
    public static final String  USER_ROLE_EDU_STAFF_CODE           = "30";
    /** 超级管理员 */
    public static final String  USER_ROLE_SUPER_ADMIN              = "40";
    /** 代理商 */
    public static final String  USER_ROLE_AGENCY                   = "50";
    /** 代理商学校 */
    public static final String  USER_ROLE_AGENCY_SCHOOL            = "51";
    /*********************** 权限角色编码 END **************************************/                                                              

    /*********************** 登录表角色 START **************************************/
    /** 管理员 */
    public static final Integer LOGIN_USER_ROLE_ADMIN              = 30;

    /*********************** 角色编码 END **************************************/

    /*********************** 区域机构 START **************************************/
    public static final String  AREA_ORG_CODE                      = "00000000000000";

    /************************** 班级毕业状态 *************************/
    /** 已毕业 */
    public static final Integer CLASS_STATUS_GRADUATE              = 20;
    /** 未毕业 */
    public static final Integer CLASS_STATUS__NOT_GRADUATE         = 10;

    /************************** 用户角色 *************************/
    /** 老师 */
    public static final Integer USER_ROLE_TEACHER                  = 10;
    /** 学生 */
    public static final Integer USER_ROLE_STUDENT                  = 20;

    /************************** 默认密码 *************************/
    /** 学生默认密码 */
    public static final String  DEFAULT_STUDENT_PWD                = "111111";
    /** 老师默认密码 */
    public static final String  DEFAULT_TEACHER_PWD                = "111111";
    /** 默认密码 */
    public static final String  DEFAULT_PWD                        = "111111";

    /** 用户头像地址 */
    public static final String  FTP_USER                           = "/user/head_portrait";
    /** 用户头像地址 */
    public static final String  MEDIA_COVER                        = "/img_upload";
    /** 机构 */
    public static final String  FTP_COMPANY                        = "/company/banner";
    /** 礼品图片 */
    public static final String  FTP_GIFT                           = "/gift/banner";
    /** 资源首页 封面图片 */
    public static final String  FTP_RES_HOME_COVER                 = "/res_home/cover";
    /** 资源首页 缩略图 */
    public static final String  FTP_RES_HOME_THUMBNAIL             = "/res_home/thumbnail";
    /** 平台LOGO */
    public static final String  FTP_PLATFORM_LOGO                  = "/platform/logo";

    /******************** 积分等级设定方式 *********************************/
    /** 继承设定方式 */
    public static final Integer SETTING_TYPE_EXTEND                = 0;
    /** 不继承 */
    public static final Integer SETTING_TYPE_NOT_EXTEND            = 10;

    /******************** 商品级别 *********************************/
    /** 机构 */
    public static final Integer GOODS_LEVEL_COMPANY                = 20;
    /** 区域 */
    public static final Integer GOODS_LEVEL_AREA                   = 30;
    /** 域登录 */
    public static final String  LOGIN_TYPE_GLOBAL                  = "10";
    /** 后台自己登录 */
    public static final String  LOGIN_TYPE_BACK                    = "20";

    /******************** 资源首页设置是否使用 *********************************/
    /** 0：不使用 */
    public static final int     FLAG_AVAILABLE_NO                  = 0;
    /** 1：使用 */
    public static final int     FLAG_AVAILABLE_YES                 = 1;

    /******************** 资源首页设置显示类别 *********************************/
    /** 0：首页横幅 */
    public static final String  SET_TYPE_HEAD                      = "0";
    /** 1：最新 */
    public static final String  SET_TYPE_NEW                       = "1";
    /** 2：最热 */
    public static final String  SET_TYPE_HOT                       = "2";

    /******************** 是否删除（逻辑删除） *********************************/
    /** 0：正常 */
    public static final int     FLAG_DELETE_NO                     = 0;
    /** 1：已删除 */
    public static final int     FLAG_DELETE_YES                    = 1;
    /** 9：彻底删除 */
    public static final int     FLAG_DELETE_PACED                  = 9;

    /************************** 登录用户状态 *****************************/
    /** 0：无效 */
    public static final int     USER_STATUS_INVALID                = 0;
    /** 1：有效 */
    public static final int     USER_STATUS_EFFECTIVE              = 1;
    /** 2：锁定 */
    public static final int     USER_STATUS_LOCK                   = 2;

    /************************** 积分动作编码 *****************************/
    /** 课程审核通过 */
    public static final Integer ACTION_LESSON_CHECKED              = 12000040; 

    /************************** 邮箱认证标识 *****************************/
    /** 0：未验证或验证中 */
    public static final int     FLAG_MAIL_VALIDATE_NO              = 0;
    /** 1：已通过验证 */
    public static final int     FLAG_MAIL_VALIDATE_YES             = 1;

    /************************** 手机认证标识 *****************************/
    /** 0：未验证或验证中 */
    public static final int     FLAG_PHONE_VALIDATE_NO             = 0;
    /** 1：已通过验证 */
    public static final int     FLAG_PHONE_VALIDATE_YES            = 1;

    /** 设备类型PC */
    public static final int     DEVICE_TYPE_PC                     = 10;
    /** 设备类型android_pad */
    public static final int     DEVICE_TYPE_ANDROID_PAD            = 20;
    /** 设备类型ipad */
    public static final int     DEVICE_TYPE_IPAD                   = 21;
    /** 设备类型android */
    public static final int     DEVICE_TYPE_ANDROID                = 30;
    /** 设备类型iphone */
    public static final int     DEVICE_TYPE_IPHONE                 = 31;

    /** 机构有效 */
    public static final int     FLAG_COMPANY_VALIDAYE_YES          = 1;
    /** 机构无效 */
    public static final int     FLAG_COMPANY_VALIDAYE_NO           = 0;

    /** 是代理商 */
    public static final int     FLAG_AGENCY_TRUE                   = 1;
    /** 不是代理商 */
    public static final int     FLAG_AGENCY_FALSE                  = 0;

    /** 五级代理商 */
    public static final int     AGENCY_LEVEL_FIVE                  = 5;

    /** 是备用账号 */
    public static final int     STANDBY_ACCOUNT_YES                = 1;
    /** 不是备用账号 */
    public static final int     STANDBY_ACCOUNT_NO                 = 0;
    /************************** 学段信息 *****************************/
    /** 1：学前 */
    public static final String  SECTION_PRESCHOOL                  = "1";
    /** 2：小学 */
    public static final String  SECTION_PRIMARY                    = "2";
    /** 3：初中 */
    public static final String  SECTION_MIDDLE                     = "3";
    /** 4：高中 */
    public static final String  SECTION_HIGH                       = "4";

    /************************** 机构学年学期表是否更新 *****************************/
    /** 0：未更新 */
    public static final int     FLAG_UPD_BY_ORG_NO                 = 0;
    /** 1：已更新 */
    public static final int     FLAG_UPD_BY_ORG_YES                = 1;

}
