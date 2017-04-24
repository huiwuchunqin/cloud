package com.baizhitong.resource.common.config;

import com.baizhitong.utils.PropertieUtils;

public class SystemConfig {

    private static SystemConfig config;

    private SystemConfig() {
    }

    static {
        // 单例
        config = null == config ? new SystemConfig() : config;
        // 加载基础配置
        loadFromProp();
    }

    public static SystemConfig getInstance() {
        return config;
    }

    /**
     * 系统共通文件HOST
     */
    public static String  bztSyscommHost;

    public static String  ftpSyscommHost;
    public static String  resHost;
    public static String  testHost;
    public static String  ilpHost;
    public static String  plsServerUrl;
    public static String  casLoginOutUrl;
    public static boolean casEnable;
    public static String  casHost;
    public static String  exercisePreviewUrl;
    public static String  questionPreviewUrl;
    public static String  lessonPreviewUrl;
    public static String  mediaPreviewUrl;
    public static String  docPreviewUrl;
    public static boolean agentEnable;
    /**
     * 全局系统的编码
     * 
     * @author zhangll
     * @date 2015年8月20日 下午1:10:33
     */
    private static String platformCdGlobal;

    /****************************************
     * 转码服务地址与回调标示 Start
     *******************************************************/

    /** 视频访问地址 */
    private static String videoHost;
    /** 文档访问地址 */
    private static String docHost;
    /** 图片访问地址 */
    private static String imgHost;
    /** 视频转码地址 */
    private static String videoConvertHost;
    /** 数据仓库地址 */
    private static String datadepotHost;
    /** 文档转换地址 */
    private static String convertHost;
    /** 转码回调domain(对应转码DB中获取回调地址唯一标示) */
    private static String convertCallBackDomain;
    /** 转码接口 */
    private static String convertAsyncDomain;
    /** 前台访问地址 */
    private static String webUrl;
    /** 密码 */
    private static String pwd;
    /****************************************
     * 转码服务地址与回调标示 End
     *******************************************************/

    /**
     * 初始化系统，加载相关的配置信息及系统常量
     */
    public static void loadFromProp() {

        PropertieUtils setting = new PropertieUtils("settings.properties");
        platformCdGlobal = setting.getProperty("bzt.syscomm.platformCdGlobal");
        ftpSyscommHost = setting.getProperty("ftphost");
        convertAsyncDomain = setting.getProperty("convertAsyncDomain");
        convertCallBackDomain = setting.getProperty("convertCallBackDomain");
        videoHost = setting.getProperty("videoHost");
        docHost = setting.getProperty("docHost");
        imgHost = setting.getProperty("imgHost");
        videoConvertHost = setting.getProperty("videoConvertHost");
        datadepotHost = setting.getProperty("datadepotHost");
        convertHost = setting.getProperty("videoConvertHost");
        resHost = setting.getProperty("resHost");
        testHost = setting.getProperty("testHost");
        ilpHost = setting.getProperty("ilpHost");
        plsServerUrl = setting.getProperty("plsServerUrl");
        casLoginOutUrl = setting.getProperty("casLogoOutHtml");
        casEnable = setting.getBoolean("casEnable");
        casHost = setting.getProperty("casHost");
        questionPreviewUrl = setting.getProperty("questionPreviewUrl");
        exercisePreviewUrl = setting.getProperty("exercisePreviewUrl");
        lessonPreviewUrl = setting.getProperty("lessonPreviewUrl");
        docPreviewUrl = setting.getProperty("docPreviewUrl");
        mediaPreviewUrl = setting.getProperty("mediaPreviewUrl");
        webUrl = setting.getProperty("webUrl");
        pwd = setting.getProperty("pwd");
        agentEnable = setting.getBoolean("agentEnable");
        ;
        setting.close();
    }

    public static String getBztSyscommHost() {
        return bztSyscommHost;
    }

    public static String getFtpSyscommHost() {
        return ftpSyscommHost;
    }

    public static SystemConfig getConfig() {
        return config;
    }

    public static void setConfig(SystemConfig config) {
        SystemConfig.config = config;
    }

    public static String getResHost() {
        return resHost;
    }

    public static void setResHost(String resHost) {
        SystemConfig.resHost = resHost;
    }

    public static String getTestHost() {
        return testHost;
    }

    public static void setTestHost(String testHost) {
        SystemConfig.testHost = testHost;
    }

    public static String getIlpHost() {
        return ilpHost;
    }

    public static void setIlpHost(String ilpHost) {
        SystemConfig.ilpHost = ilpHost;
    }

    public static String getPlsServerUrl() {
        return plsServerUrl;
    }

    public static void setPlsServerUrl(String plsServerUrl) {
        SystemConfig.plsServerUrl = plsServerUrl;
    }

    public static String getCasLoginOutUrl() {
        return casLoginOutUrl;
    }

    public static void setCasLoginOutUrl(String casLoginOutUrl) {
        SystemConfig.casLoginOutUrl = casLoginOutUrl;
    }

    public static boolean isCasEnable() {
        return casEnable;
    }

    public static void setCasEnable(boolean casEnable) {
        SystemConfig.casEnable = casEnable;
    }

    public static String getCasHost() {
        return casHost;
    }

    public static void setCasHost(String casHost) {
        SystemConfig.casHost = casHost;
    }

    public static boolean isAgentEnable() {
        return agentEnable;
    }

    public static void setAgentEnable(boolean agentEnable) {
        SystemConfig.agentEnable = agentEnable;
    }

    public static String getExercisePreviewUrl() {
        return exercisePreviewUrl;
    }

    public static void setExercisePreviewUrl(String exercisePreviewUrl) {
        SystemConfig.exercisePreviewUrl = exercisePreviewUrl;
    }

    public static String getQuestionPreviewUrl() {
        return questionPreviewUrl;
    }

    public static void setQuestionPreviewUrl(String questionPreviewUrl) {
        SystemConfig.questionPreviewUrl = questionPreviewUrl;
    }

    public static void setBztSyscommHost(String bztSyscommHost) {
        SystemConfig.bztSyscommHost = bztSyscommHost;
    }

    public static void setFtpSyscommHost(String ftpSyscommHost) {
        SystemConfig.ftpSyscommHost = ftpSyscommHost;
    }

    public static void setPlatformCdGlobal(String platformCdGlobal) {
        SystemConfig.platformCdGlobal = platformCdGlobal;
    }

    public static String getLessonPreviewUrl() {
        return lessonPreviewUrl;
    }

    public static void setLessonPreviewUrl(String lessonPreviewUrl) {
        SystemConfig.lessonPreviewUrl = lessonPreviewUrl;
    }

    public static String getWebUrl() {
        return webUrl;
    }

    public static void setWebUrl(String webUrl) {
        SystemConfig.webUrl = webUrl;
    }

    public static void setVideoHost(String videoHost) {
        SystemConfig.videoHost = videoHost;
    }

    public static void setDocHost(String docHost) {
        SystemConfig.docHost = docHost;
    }

    public static void setImgHost(String imgHost) {
        SystemConfig.imgHost = imgHost;
    }

    public static void setVideoConvertHost(String videoConvertHost) {
        SystemConfig.videoConvertHost = videoConvertHost;
    }

    public static void setDatadepotHost(String datadepotHost) {
        SystemConfig.datadepotHost = datadepotHost;
    }

    public static void setConvertHost(String convertHost) {
        SystemConfig.convertHost = convertHost;
    }

    public static void setConvertCallBackDomain(String convertCallBackDomain) {
        SystemConfig.convertCallBackDomain = convertCallBackDomain;
    }

    public static void setConvertAsyncDomain(String convertAsyncDomain) {
        SystemConfig.convertAsyncDomain = convertAsyncDomain;
    }

    public static String getPlatformCdGlobal() {
        return platformCdGlobal;
    }

    public static String getVideoHost() {
        return videoHost;
    }

    public static String getDocHost() {
        return docHost;
    }

    public static String getVideoConvertHost() {
        return videoConvertHost;
    }

    public static String getDatadepotHost() {
        return datadepotHost;
    }

    public static String getConvertHost() {
        return convertHost;
    }

    public static String getConvertCallBackDomain() {
        return convertCallBackDomain;
    }

    public static String getConvertAsyncDomain() {
        return convertAsyncDomain;
    }

    public static String getImgHost() {
        return imgHost;
    }

    public static String getMediaPreviewUrl() {
        return mediaPreviewUrl;
    }

    public static void setMediaPreviewUrl(String mediaPreviewUrl) {
        SystemConfig.mediaPreviewUrl = mediaPreviewUrl;
    }

    public static String getDocPreviewUrl() {
        return docPreviewUrl;
    }

    public static void setDocPreviewUrl(String docPreviewUrl) {
        SystemConfig.docPreviewUrl = docPreviewUrl;
    }

    public static String getPwd() {
        return pwd;
    }

    public static void setPwd(String pwd) {
        SystemConfig.pwd = pwd;
    }

}
