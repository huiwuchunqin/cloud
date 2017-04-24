package com.baizhitong.resource.manage.textbook.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.Decoder;
import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.subject.service.SubjectService;
import com.baizhitong.resource.manage.textbook.service.TextbookVersionService;
import com.baizhitong.resource.model.share.ShareCodeTextbookVer;
import com.baizhitong.resource.model.vo.share.ShareTextbookVerTreeVo;
import com.baizhitong.resource.model.vo.share.VersionSimpleVo;
import com.baizhitong.utils.StringUtils;

/**
 * 教材版本控制器
 * 
 * @author zhangqiang
 * @date 2015年12月14日 下午4:55:10
 */

@Controller
@RequestMapping("/manage/textbook")
public class TextbookVersionAction extends BaseAction {
    /** 教材版本接口 */
    private @Autowired TextbookVersionService textbookVersionService;
    /** 学科信息接口 */
    private @Autowired SubjectService         subjectService;
    /** 学段信息接口 */
    private @Autowired SectionService         sectionService;

    /**
     * 跳转到教材版本信息页面
     * 
     * @param request Http请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月14日 下午5:00:40
     */
    @RequestMapping("/version.html")
    public String jumpToTextBookVersionPage(HttpServletRequest request, ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            return "/manage/textbook/textbookVersion.html";
        } catch (Exception e) {
            log.error("获取教材版本失败！", e);
            return "/manage/textbook/textbookVersion.html";
        }
    }

    /**
     * 教材版本分页信息
     * 
     * @param textBookName
     * @param subjectCode
     * @param sectionCode
     * @param rows
     * @param page
     * @return
     * @author gaow
     * @date:2015年12月15日 下午6:47:11
     */
    @RequestMapping("/versionPageList.html")
    public @ResponseBody Page getTextBookPage(String name, String sectionCode, String subjectCode, Integer rows,
                    Integer page) {
        if (rows == null)
            rows = 20;
        if (page == null)
            page = 1;
        try {
            return textbookVersionService.queryTextbookVerPageInfo(sectionCode, subjectCode, name, rows, page);
        } catch (Exception e) {
            log.error("获取教材版本信息失败！", e);
            return null;
        }
    }

    /**
     * 跳转到教材版本新增页面 ()<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/textbookVerAdd.html")
    public String toTextbookverAdd(ModelMap model) {
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            return "/manage/textbook/textbookVersionAdd.html";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return "/manage/textbook/textbookVersionAdd.html";
        }
    }

    /**
     * 
     * 保存教材版本
     * 
     * @param addCodes 新增教材版本相关学科
     * @param name
     * @param editGid 修改教材版本id
     * @param delGid 删除教材版本id
     * @param originalName 原来的名称
     * @return
     */
    @RequestMapping(value = "addTextbook.html")
    @ResponseBody
    public ResultCodeVo addTextbookVer(String originalName, @RequestParam(value = "addCodes[]",
                                                                          required = false) String[] addCodes,
                    String name, @RequestParam(value = "editGid[]",
                                               required = false) String[] editGid,
                    @RequestParam(value = "delGid[]",
                                  required = false) String[] delGid) {
        return textbookVersionService.addTextbooxVer(originalName, addCodes, editGid, delGid, name);

    }

    /**
     * 通过名称删除 ()<br>
     * 
     * @param name
     * @return
     */
    @RequestMapping(value = "/delTextbook")
    @ResponseBody
    public ResultCodeVo delTextbook(String name) {
        return textbookVersionService.delTextbook(name);
    }

    /**
     * 通过gid删除 ()<br>
     * 
     * @param delTextbookByGid
     * @return
     */
    @RequestMapping(value = "/delTextbookByGid")
    @ResponseBody
    public ResultCodeVo delTextbookByGid(String gid) {
        return textbookVersionService.delTextbookByGid(gid);
    }

    /**
     * 教材版本树形列表 ()<br>
     * 
     * @param subjectCode
     * @param sectionCode
     * @param name
     * @return
     */
    @RequestMapping(value = "/textbookVerTree.html")
    @ResponseBody
    public List<ShareTextbookVerTreeVo> getShareTextbookVer(String subjectCode, String sectionCode, String name) {
        return textbookVersionService.getTextbookVerTreeVo(subjectCode, sectionCode, name);
    }

    /**
     * 查询教材版本 ()<br>
     * 
     * @param name
     * @return
     */
    @RequestMapping(value = "/getTextbookVerList.html")
    @ResponseBody
    public List<Map<String, Object>> getVerList(String name) {
        try {
            return textbookVersionService.getTextbookVer(URLDecoder.decode(name, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改教材版本 ()<br>
     * 
     * @param verName
     * @return
     */
    @RequestMapping(value = "editTextbookVer.html")
    public String editShareTextbookVer(ModelMap model, String name) {
        try {
            model.put("name", name);
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/manage/textbook/textbookVersionAdd.html";
    }

    /**
     * 根据学科加载教材版本信息
     * 
     * @param request 请求
     * @param subjectCode 学科编码
     * @return
     * @author zhangqiang
     * @date 2015年12月15日 下午4:28:56
     */
    @RequestMapping("/getVersionBySubjectCode.html")
    public @ResponseBody List<ShareCodeTextbookVer> getTextbookVersionListBySubjectID(HttpServletRequest request,
                    String subjectCode) {
        try {
            List<ShareCodeTextbookVer> list = textbookVersionService.getTextbookVersionListBySubjectCode(subjectCode);
            return list;
        } catch (Exception e) {
            log.error("获取教材版本信息失败！", e);
            return null;
        }
    }

    /**
     * 查询某些学科的教材版本
     * 
     * @param subjectCodes
     * @return
     * @author gaow
     * @date:2015年12月17日 下午7:00:05
     */
    @RequestMapping("/getVersionInSubjectCodes.html")
    public @ResponseBody List<VersionSimpleVo> getTextBookVersionInSubjects(String subjectCodes) {
        try {
            if (StringUtils.isEmpty(subjectCodes))
                return null;
            List<String> subjectCodesList = StringUtils.arrToList(subjectCodes.split(","));
            List<ShareCodeTextbookVer> list = textbookVersionService.getTextbookVersionInSubject(subjectCodesList);
            return VersionSimpleVo.getDistinicSimpList(list);
        } catch (Exception e) {
            log.error("查询教材版本出错", e);
            return null;
        }
    }

}
