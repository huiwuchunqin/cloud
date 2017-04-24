package com.baizhitong.resource.manage.res.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;

public class DataFormatter {
    public static List<Map<String, Object>> format(List<Map<String, Object>> list) {
        if (list == null || list.size() <= 0)
            return new ArrayList<Map<String,Object>>();;
        for (Map<String, Object> map : list) {
            getTbcCountStr(map);
            getKpCountStr(map);
            getAllowBrowser(map);
            getAllowDownLoad(map);
            getCheckStatusStr(map);
            getResStatusStr(map);
            getShareLevelStr(map);
            getShareCheckLevelStr(map);
            timefmt(map);
        }
        return list;
    }

    public static void getTbcCountStr(Map<String, Object> map) {
        Integer tbcCount = MapUtils.getInteger(map, "tbcCount");
        if (tbcCount != null && tbcCount > 0) {
            map.put("tbcCountStr", "已标注");
        } else {
            map.put("tbcCountStr", "");
        }
    }
    
    public  static void timefmt(Map<String,Object> map){
        String shareTime=MapUtils.getString(map, "shareTime");
        String shareCheckTime=MapUtils.getString(map, "shareCheckTime");
        String makeTime=MapUtils.getString(map, "makeTime");
        if(StringUtils.isNotEmpty(shareTime)) {
            map.put("shareTime", TimeUtils.formatDate(shareTime, "yyyy-MM-dd HH:mm"));           
        }
        if(StringUtils.isNotEmpty(shareCheckTime)) {
            map.put("shareCheckTime", TimeUtils.formatDate(shareCheckTime, "yyyy-MM-dd HH:mm"));           
        }
        if(StringUtils.isNotEmpty(makeTime)) {
            map.put("makeTime", TimeUtils.formatDate(makeTime, "yyyy-MM-dd HH:mm"));           
        }
    }

    public static void getKpCountStr(Map<String, Object> map) {
        Integer kpCount = MapUtils.getInteger(map, "kpCount");
        if (kpCount != null && kpCount > 0) {
            map.put("kpCountStr", "已标注");
        } else {
            map.put("kpCountStr", "");
            ;
        }
    }

    /**
     * 是否允许浏览
     * 
     * @return
     * @author gaow
     * @date:2015年12月19日 下午7:50:13
     */
    public static void getAllowBrowser(Map<String, Object> map) {
        Integer flagAllowBrowse = MapUtils.getInteger(map, "flagAllowBrowse");
        if ((CoreConstants.RESOURCE_ALLOW_BROWSER_NO).equals(flagAllowBrowse)) {
            map.put("allowBrowser", "否");
        } else if ((CoreConstants.RESOURCE_ALLOW_BROWSER_YES).equals(flagAllowBrowse)) {
            map.put("allowBrowser", "是");
        } else {
            map.put("allowBrowser", "");
        }
    }

    /**
     * 是否语序下载
     * 
     * @return
     * @author gaow
     * @date:2015年12月19日 下午7:50:05
     */
    public static void getAllowDownLoad(Map<String, Object> map) {
        Integer flagAllowDownload = MapUtils.getInteger(map, "flagAllowDownload");
        if ((CoreConstants.RESOURCE_ALLOW_DOWNLOAD_NO).equals(flagAllowDownload)) {
            map.put("allowDownload", "否");
        } else if ((CoreConstants.RESOURCE_ALLOW_DOWNLOAD_YES).equals(flagAllowDownload)) {
            map.put("allowDownload", "是");
        } else {
            map.put("allowDownload", "");
        }
    }

    /**
     * 审核状态
     * 
     * @return
     * @author gaow
     * @date:2015年12月16日 下午2:46:23
     */
    public static void getCheckStatusStr(Map<String, Object> map) {
        Integer shareCheckStatus = MapUtils.getInteger(map, "shareCheckStatus");
        if (shareCheckStatus == null) {
            map.put("shareCheckStatusStr", "未提交");
        } else if ((CoreConstants.CHECK_STATUS_CHECKING).equals(shareCheckStatus)) {
            map.put("shareCheckStatusStr", "待审核");
        } else if ((CoreConstants.CHECK_STATUS_CHECKED).equals(shareCheckStatus)) {
            map.put("shareCheckStatusStr", "已通过");
        } else if ((CoreConstants.CHECK_STATUS_UNAPPROVE).equals(shareCheckStatus)) {
            map.put("shareCheckStatusStr", "已退回");
        } else if ((CoreConstants.CHECK_STATUS_UNCOMMIT).equals(shareCheckStatus)) {
            map.put("shareCheckStatusStr", "未提交");
        } else {
            map.put("shareCheckStatusStr", "");
        }
    }

    /**
     * 分享级别
     * 
     * @return
     * @author gaow
     * @date:2015年12月16日 下午2:43:53
     */
    public static void getShareLevelStr(Map<String, Object> map) {
        Integer shareLevel = MapUtils.getInteger(map, "shareLevel");
        if ((CoreConstants.RESOURCE_SHARE_LEVEL_CITY).equals(shareLevel)) {
            map.put("shareLevelStr", "地方");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY).equals(shareLevel)) {
            map.put("shareLevelStr", "校内共享");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_OPEN).equals(shareLevel)) {
            map.put("shareLevelStr", "公开");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE).equals(shareLevel)) {
            map.put("shareLevelStr", "个人私有");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_PROVINCE).equals(shareLevel)) {
            map.put("shareLevelStr", "省级");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_TOWN).equals(shareLevel)) {
            map.put("shareLevelStr", "区域共享");
        } else {
            map.put("shareLevelStr", "");
        }
    }

    /**
     * 分享审核级别
     * 
     * @return
     * @author gaow
     * @date:2015年12月16日 下午2:43:53
     */
    public static void getShareCheckLevelStr(Map<String, Object> map) {
        Integer shareCheckLevel = MapUtils.getInteger(map, "shareCheckLevel");
        if ((CoreConstants.RESOURCE_SHARE_LEVEL_CITY).equals(shareCheckLevel)) {
            map.put("shareCheckLevelStr", "地方");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY).equals(shareCheckLevel)) {
            map.put("shareCheckLevelStr", "校内共享");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_OPEN).equals(shareCheckLevel)) {
            map.put("shareCheckLevelStr", "公开");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE).equals(shareCheckLevel)) {
            map.put("shareCheckLevelStr", "个人私有");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_PROVINCE).equals(shareCheckLevel)) {
            map.put("shareCheckLevelStr", "省级");
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_TOWN).equals(shareCheckLevel)) {
            map.put("shareCheckLevelStr", "区域共享");
        } else {
            map.put("shareCheckLevelStr", "");
        }
    }

    /**
     * 资源状态
     * 
     * @return
     * @author gaow
     * @date:2015年12月16日 下午2:48:37
     */
    public static void getResStatusStr(Map<String, Object> map) {
        Integer resStatus = MapUtils.getInteger(map, "resStatus");
        if (resStatus == null)
            map.put("resStatusStr", "转码中");
        if ((CoreConstants.RESOURCE_STATE_CONVERTING).equals(resStatus)) {
            map.put("resStatusStr", "转码中");
        } else if ((CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS).equals(resStatus)) {
            map.put("resStatusStr", "转码成功");
        } else if ((CoreConstants.RESOURCE_STATE_CONVERT_FAIL).equals(resStatus)) {
            map.put("resStatusStr", "转码失败");
        } else {
            map.put("resStatusStr", "转码中");
        }
    }
}