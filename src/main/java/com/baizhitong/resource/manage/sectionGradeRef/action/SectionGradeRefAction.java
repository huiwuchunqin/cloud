package com.baizhitong.resource.manage.sectionGradeRef.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.sectionGradeRef.service.SectionGradeRefService;
import com.baizhitong.resource.model.vo.share.SectionGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;

/**
 * 学段年级关系控制类
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:47:00
 */
@Controller
@RequestMapping(value = "/manage/sectionGradeRef")
public class SectionGradeRefAction extends BaseAction {
    private @Autowired SectionService         sectionService;         // 学段业务接口
    private @Autowired SectionGradeRefService sectionGradeRefService; // 学段和年级关系业务接口

    /**
     * 跳转到学段年级设置页面
     * 
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月15日 下午1:57:17
     */
    @RequestMapping(value = "/toSectionGradeRef.html")
    public String toSectionGradeRef(ModelMap map) {

        try {
            map.put("sectionList", JSON.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("学段信息查询异常", e);
            return "/manage/sectionGradeRef/sectionGradeRef.html";
        }
        return "/manage/sectionGradeRef/sectionGradeRef.html";
    }

    /**
     * 查询学段年级信息
     * 
     * @param sectionCode 学段编码
     * @return
     * @author gaow
     * @date:2015年12月15日 下午5:38:08
     */
    @RequestMapping(value = "/sectionGrade.html")
    public @ResponseBody List<ShareCodeGradeVo> getSectionGrade(String sectionCode) {
        try {
            return sectionService.getSectionGrade(sectionCode);
        } catch (Exception e) {
            log.error("学段年级信息查询异常", e);
            return null;
        }
    }

    /**
     * 查询学段年级信息
     * 
     * @param sectionCode 学段编码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月15日 下午5:38:08
     */
    @RequestMapping(value = "/sectionGradePage.html")
    public @ResponseBody Page<ShareCodeGradeVo> getSectionGradePage(String sectionCode, Integer rows, Integer page) {
        if (rows == null)
            rows = 20;
        if (page == null)
            page = 1;
        try {
            return sectionService.getSectionGradePage(sectionCode, rows, page);
        } catch (Exception e) {
            log.error("学段年级信息查询异常", e);
            return null;
        }
    }

    /**
     * 查询学段年级配置信息
     * 
     * @param sectionCode
     * @return
     * @author gaow
     * @date:2015年12月15日 下午1:57:02
     */
    @RequestMapping(value = "/sectionGradeRefSet.html")
    public @ResponseBody List<SectionGradeVo> SectionGradeRefSet(String sectionCode) {
        try {
            return sectionGradeRefService.sectionGradeRefSetList(sectionCode);
        } catch (Exception e) {
            log.error("学段年级信息查询异常", e);
            return null;
        }
    }

    /**
     * 学段年级关系保存
     * 
     * @param sectionCode
     * @param gradeCodeArray
     * @return
     * @author gaow
     * @date:2015年12月15日 下午2:36:36
     */
    @RequestMapping(value = "/sectionGradeRefSave.html")
    public @ResponseBody ResultCodeVo saveSectionGradeRef(String sectionCode, @RequestParam(value = "gradeCodeArray[]",
                                                                                            required = false) String[] gradeCodeArray) {
        try {
            sectionGradeRefService.saveSectionGradeList(sectionCode, gradeCodeArray);
            return ResultCodeVo.rightCode("学段年级关系保存成功");
        } catch (Exception e) {
            log.error("学段学科关系保存异常", e);
            return ResultCodeVo.errorCode("学段学科关系保存异常");
        }
    }

    /**
     * 
     * (根据学段查询学段年级关系列表)<br>
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/list.html")
    @ResponseBody
    public List<Map<String, Object>> getGradeRelList(HttpServletRequest request) {
        try {
            return sectionGradeRefService.queryListBySectionCode(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
