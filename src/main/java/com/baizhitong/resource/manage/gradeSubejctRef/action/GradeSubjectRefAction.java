package com.baizhitong.resource.manage.gradeSubejctRef.action;

import java.util.List;

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
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.gradeSubejctRef.service.GradeSubjectRefService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.sectionGradeRef.service.SectionGradeRefService;
import com.baizhitong.resource.model.vo.share.SectionSubjectVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;

/**
 * 年级学科关系控制类
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:47:00
 */
@Controller
@RequestMapping(value = "/manage/gradeSubjectRef")
public class GradeSubjectRefAction extends BaseAction {
    private @Autowired GradeSubjectRefService gradeSubjectRefService; // 年级学科关系接口
    private @Autowired SectionService         sectionService;         // 学段业务接口
    private @Autowired SectionGradeRefService sectionGradeRefService; // 学段和年级关系业务接口
    private @Autowired GradeService           gradeService;           // 年级接口

    /**
     * 跳转到年级学科设置页面
     * 
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月15日 下午1:57:17
     */
    @RequestMapping(value = "/toGradeSubjectRef.html")
    public String toGradeSubjectRef(ModelMap map) {

        try {
            map.put("sectionList", JSON.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("年级信息查询异常", e);
            return "/manage/gradeSubjectRef/gradeSubjectRef.html";
        }
        return "/manage/gradeSubjectRef/gradeSubjectRef.html";
    }

    /**
     * 查询年级学科信息
     * 
     * @param gradeCode 年级编码
     * @return
     * @author gaow
     * @date:2015年12月15日 下午5:38:08
     */
    @RequestMapping(value = "/gradeSubject.html")
    public @ResponseBody List<ShareCodeSubjectVo> getGradeSubject(String gradeCode) {
        try {
            return gradeSubjectRefService.getGradeSubjectList(gradeCode);
        } catch (Exception e) {
            log.error("年级学科信息查询异常", e);
            return null;
        }
    }

    /**
     * 查询学段年级信息
     * 
     * @param gradeCode 年级编码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月15日 下午5:38:08
     */
    @RequestMapping(value = "/gradeSubejctPage.html")
    public @ResponseBody Page<ShareCodeSubjectVo> getGradeSubjectPage(String gradeCode, Integer rows, Integer page) {
        if (rows == null)
            rows = 20;
        if (page == null)
            page = 1;
        try {
            return gradeSubjectRefService.getGradeSubjectPage(gradeCode, page, rows);
        } catch (Exception e) {
            log.error("年级学科信息查询异常", e);
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
    @RequestMapping(value = "/gradeSubjectRefSet.html")
    public @ResponseBody List<SectionSubjectVo> SectionSubjectRefSet(String gradeCode, String sectionCode) {
        try {
            return gradeSubjectRefService.gradeSubjectRefSetList(gradeCode, sectionCode);
        } catch (Exception e) {
            log.error("年级学科信息查询异常", e);
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
    @RequestMapping(value = "/gradeSubjectRefSave.html")
    public @ResponseBody ResultCodeVo saveGradeSubjectRef(String gradeCode, @RequestParam(value = "subjectCodeArray[]",
                                                                                          required = false) String[] subjectCodeArray) {
        try {
            gradeSubjectRefService.saveGradeSubjectList(gradeCode, subjectCodeArray);
            return ResultCodeVo.rightCode("年级学科关系保存成功");
        } catch (Exception e) {
            log.error("学段学科关系保存异常", e);
            return ResultCodeVo.errorCode("年级学科关系保存异常");
        }
    }

}
