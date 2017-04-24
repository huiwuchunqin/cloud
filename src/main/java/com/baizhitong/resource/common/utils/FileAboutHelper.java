package com.baizhitong.resource.common.utils;

import com.baizhitong.utils.UrlUtils;
import org.springframework.ui.ModelMap;

/**
 * 跟文件上传、下载等有关的帮助类
 * 
 * @author zhangll
 *
 */
public class FileAboutHelper {
    private static final String TYPE_ONE                      = "1";

    private static final String TYPE_MUTI                     = "2";

    private static final String REMOTE_URL_UPLOAD             = "uploadDisk.shtml";             // http://192.168.1.162:8088/

    private static final String REMOTE_URL_DOWNLOAD           = "down.html";                    // http://192.168.1.162:8088/

    private static final String REMOTE_URL_DELETE             = "delete.html";                  // http://192.168.1.162:8088/

    private static final String REMOTE_URL_CHECKEXIST         = "/common/transcoding/exit.html";// http://192.168.1.162:8088/

    /** 分片上传url */
    private static final String REMOTE_URL_CHUNK_UPLOAD       = "/chunkedDiskUpload.shtml";

    /** 分片上传查询上传信息 */
    private static final String REMOTE_URL_CHUNK_UPLOAD_QUERY = "/chunkedInfoQuery.shtml";

    private static final String URL_PATH_SPLIT                = "/";

    private static final String URL_QUESTION_MARK             = "?";

    private static final String URL_AND                       = "&";

    private static final String URL_EQUAL                     = "=";

    private static final String FILENAME                      = "filename";

    private static final String ENC                           = "enc";

    private static final String KEY                           = "key";

    private static final String TYPE                          = "type";

    private static final String REMAIN                        = "remain";

    private static final String UPLOAD                        = "uploadUrl";

    private static final String DOWNLOAD                      = "downloadUrl";

    private static final String CHECKEXIST                    = "checkUrl";

    private static final String DELETE                        = "deleteUrl";

    private static final Long   SPACE                         = 1024 * 1024 * 1024l;

    public static final String  RESOURCE_TYPE_VIDEO           = "video";

    public static final String  RESOURCE_TYPE_DOCUMENT        = "document";

    public static final String  FILE_EXIST_FALSE              = "false";

    public static final String  FILE_EXIST_TRUE               = "true";

    /**
     * 得到下载的url包括http和参数
     * 
     * @param objectId
     * @return
     */
    public static String getRemoteDownloadUrl(String objectId, String url) {
        String enc = UrlHelper.getDownKey(objectId);
        return url + URL_PATH_SPLIT + REMOTE_URL_DOWNLOAD + URL_QUESTION_MARK + ENC + URL_EQUAL + enc;
    }

    /**
     * 得到下载的url包括http和参数
     * 
     * @param objectId
     * @param config
     * @param name
     * @param suffix
     * @return
     */
    public static String getRemoteDownloadUrl(String objectId, String url, String name, String suffix) {
        String enc = UrlHelper.getDownKey(objectId);
        if (name.lastIndexOf(".") == name.length() - 1) {
            name = name.substring(0, name.lastIndexOf("."));
        }
        if (suffix.charAt(0) != '.') {
            suffix = '.' + suffix;
        }
        if (name.indexOf(suffix) == -1) {
            name += suffix;
        }
        String fileName = UrlUtils.encodeURIComponent(UrlUtils.encodeURIComponent(name));
        return url + URL_PATH_SPLIT + REMOTE_URL_DOWNLOAD + URL_QUESTION_MARK + ENC + URL_EQUAL + enc + URL_AND
                        + FILENAME + URL_EQUAL + fileName;
    }

    /**
     * 将enc，userCode，type等信息加载到ModelMap的attribute中
     * 
     * @param userCode 登录者ID
     * @param model ModelMap
     * @param isMuti 是否多文件
     */
    public static void addFileParams2Model(String userCode, ModelMap model, boolean isMuti) {
        String type = isMuti ? TYPE_MUTI : TYPE_ONE;
        String enc = UrlHelper.getMD5String(userCode, type, SPACE);
        model.addAttribute(REMAIN, SPACE);
        model.addAttribute(ENC, enc);
        model.addAttribute(KEY, userCode);
        model.addAttribute(TYPE, type);
        model.addAttribute("chunk_upload_url", REMOTE_URL_CHUNK_UPLOAD);
        model.addAttribute("chunk_query_url", REMOTE_URL_CHUNK_UPLOAD_QUERY);
        model.addAttribute("chunk_query_url", REMOTE_URL_CHUNK_UPLOAD_QUERY);
    }

    /**
     * 获取资源类型
     * 
     * @param ext 后缀
     */
    public static String getResourceType(String ext) {
        if ("f4v,wmv,mkv,mov,avi,flv,rm,mp4,.f4v,.wmv,.mkv,.mov,.avi,.flv,.rm,.mp4".indexOf(ext) > -1) {
            return RESOURCE_TYPE_VIDEO;
        } else if ("ppt,pdf,doc,xls,docx,pptx,xlsx,.ppt,.pdf,.doc,.xls,.docx,.pptx,.xlsx".indexOf(ext) > -1) {
            return RESOURCE_TYPE_DOCUMENT;
        }
        return "";
    }
}
