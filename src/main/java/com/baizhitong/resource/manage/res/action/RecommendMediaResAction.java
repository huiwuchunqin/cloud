package com.baizhitong.resource.manage.res.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.res.service.RecommendMediaResService;
import com.baizhitong.resource.manage.section.service.SectionService;

/**
 * 音视频推荐资源控制器
 * 
 * @author zhangqiang
 * @date 2015年12月22日 下午1:21:42
 */
@Controller
@RequestMapping(value = "/manage/recommendMediaRes")
public class RecommendMediaResAction extends BaseAction {
    /** 学段信息接口 */
    private @Autowired SectionService           sectionService;
    /** 文档推荐资源接口 */
    private @Autowired RecommendMediaResService recommendMediaResService;

    /**
     * 跳转到音视频的推荐资源页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月21日 上午10:24:42
     */
    @RequestMapping(value = "/mediaInfo.html")
    public String jumpToMediaRecommendPage(HttpServletRequest request, ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("跳转到音视频推荐资源页面失败！", e);
        }
        return "/manage/recommend/media_recommend_info.html";
    }

    /**
     * 查询已被推荐的音视频资源信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param recommendStartTime 推荐开始时间
     * @param recommendEndTime 推荐结束时间
     * @param resName 资源名称
     * @param rows 页面大小
     * @param page 页码
     * @return
     * @author zhangqiang
     * @date 2015年12月21日 下午2:13:21
     */
    @RequestMapping(value = "/search.html")
    public @ResponseBody Page<Map<String, Object>> getRecommendMediaInfo(String sectionCode, String gradeCode,
                    String subjectCode, String recommendStartTime, String recommendEndTime, String resName,
                    Integer rows, Integer page) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 20;
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("recommendStartTime", recommendStartTime);
        param.put("recommendEndTime", recommendEndTime);
        param.put("resName", resName);
        param.put("pageSize", rows);
        param.put("pageNumber", page);
        try {
            return recommendMediaResService.getRecommendMediaInfo(param);
        } catch (Exception e) {
            log.error("音视频资源信息查询失败！", e);
            return null;
        }
    }

    /**
     * 取消音视频资源推荐
     * 
     * @param resId 资源ID
     * @return
     * @author zhangqiang
     * @date 2015年12月21日 下午4:44:56
     */
    @RequestMapping(value = "/delRecommendMediaRes.html")
    public @ResponseBody ResultCodeVo deleteRecommendMediaRes(String resId) {
        ResultCodeVo vo = new ResultCodeVo();
        try {
            int delSum = recommendMediaResService.deleteRecommendMediaRes(resId);
            if (delSum == 1) {
                vo.setSuccess(true);
                vo.setMsg("取消推荐成功！");
            } else {
                vo.setSuccess(false);
                vo.setMsg("取消推荐失败！");
            }
        } catch (Exception ex) {
            log.error("取消音视频资源推荐失败！", ex);
            vo.setSuccess(false);
            vo.setMsg("取消推荐失败！");
        }
        return vo;
    }

    /**
     * 批量取消音视频资源推荐
     * 
     * @param resIds 资源IDs
     * @return
     * @author zhangqiang
     * @date 2015年12月21日 下午5:23:46
     */
    @RequestMapping(value = "/delBatchRecommendMediaRes.html")
    public @ResponseBody ResultCodeVo deleteBatchRecommendMediaRes(@RequestParam(value = "resIds[]") String resIds[]) {
        try {
            if (resIds == null || resIds.length == 0) {
                return ResultCodeVo.errorCode("请先选择您要取消推荐的音视频资源！");
            } else {
                for (String resId : resIds) {
                    recommendMediaResService.deleteRecommendMediaRes(resId);
                }
                return ResultCodeVo.rightCode("操作成功！");
            }
        } catch (Exception ex) {
            log.error("批量取消音视频资源推荐失败！", ex);
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 跳转到设置音视频推荐资源页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月21日 下午5:49:56
     */
    @RequestMapping(value = "/chooseMediaRes.html")
    public String jumpToSetMediaRecommendResPage(HttpServletRequest request, ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("跳转到设置音视频推荐资源页面失败！", e);
        }
        return "/manage/recommend/media_recommend_set.html";
    }

    /**
     * 查询未推荐的音视频资源信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param uploadStartTime 上传开始时间
     * @param uploadEndTime 上传结束时间
     * @param resName 资源名称
     * @param rows 页面大小
     * @param page 页码
     * @return
     * @author zhangqiang
     * @date 2015年12月21日 下午6:40:55
     */
    @RequestMapping(value = "/searchSetInfo.html")
    public @ResponseBody Page<Map<String, Object>> searchCanRecommendInfo(String sectionCode, String gradeCode,
                    String subjectCode, String uploadStartTime, String uploadEndTime, String resName, Integer rows,
                    Integer page) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 20;
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("uploadEndTime", uploadEndTime);
        param.put("uploadStartTime", uploadStartTime);
        param.put("resName", resName);
        param.put("pageSize", rows);
        param.put("pageNumber", page);
        try {
            return recommendMediaResService.getCanRecommendInfo(param);
        } catch (Exception e) {
            log.error("查询未推荐的音视频资源信息失败！", e);
            return null;
        }
    }

    /**
     * 推荐音视频资源
     * 
     * @param mediaResId 音视频资源ID
     * @return
     * @author zhangqiang
     * @date 2015年12月22日 下午 14:56:28
     */
    @RequestMapping(value = "/recommendMediaRes.html")
    public @ResponseBody ResultCodeVo recommendMediaRes(String mediaResId) {
        try {
            return recommendMediaResService.recommendMediaRes(mediaResId);
        } catch (Exception ex) {
            log.error("推荐音视频资源失败！", ex);
            return ResultCodeVo.errorCode("推荐失败！");
        }

    }
}
