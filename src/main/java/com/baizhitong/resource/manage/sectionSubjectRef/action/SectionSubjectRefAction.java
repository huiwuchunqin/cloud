package com.baizhitong.resource.manage.sectionSubjectRef.action;

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
import com.baizhitong.resource.manage.sectionSubjectRef.service.SectionSubjectRefService;
import com.baizhitong.resource.model.vo.share.SectionSubjectVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;

/**
 * 学段学科关系控制类
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:46:32
 */
@Controller
@RequestMapping(value = "/manage/sectionSubjectRef")
public class SectionSubjectRefAction extends BaseAction {
    private @Autowired SectionService           sectionService;           // 学段业务接口
    private @Autowired SectionSubjectRefService sectionSubjectRefService; // 学段和学科关系业务接口

    /**
     * 跳转到学段学科设置页面
     * 
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月15日 下午1:57:17
     */
    @RequestMapping(value = "/toSectionSubjectRef.html")
    public String toSectionSubjectRef(ModelMap map) {

        try {
            map.addAttribute("sectionList", JSON.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("学段信息查询异常", e);
            return "/manage/sectionSubjectRef/sectionSubjectRef.html";
        }
        return "/manage/sectionSubjectRef/sectionSubjectRef.html";
    }

    /**
     * 查询学段学科信息
     * 
     * @param sectionCode 学段编码
     * @return
     * @author gaow
     * @date:2015年12月15日 下午5:38:08
     */
    @RequestMapping(value = "/sectionSubject.html")
    public @ResponseBody List<ShareCodeSubjectVo> getSectionSubject(String sectionCode) {
        try {
            return sectionService.getSectionSubject(sectionCode);
        } catch (Exception e) {
            log.error("学段年级信息查询异常", e);
            return null;
        }
    }

    /**
     * 查询学段学科信息
     * 
     * @param sectionCode 学段编码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月15日 下午5:38:08
     */
    @RequestMapping(value = "/sectionSubjectPage.html")
    public @ResponseBody Page<ShareCodeSubjectVo> getSectionSubjectPage(String sectionCode, Integer page,
                    Integer rows) {
        if (rows == null)
            rows = 20;
        if (page == null)
            page = 1;
        try {
            return sectionService.getSectionSubjectPage(sectionCode, rows, page);
        } catch (Exception e) {
            log.error("学段年级信息查询异常", e);
            return null;
        }
    }

    /**
     * 查询学段学科配置信息
     * 
     * @param sectionCode
     * @return
     * @author gaow
     * @date:2015年12月15日 下午1:57:02
     */
    @RequestMapping(value = "/sectionSubjectRefSet")
    public @ResponseBody List<SectionSubjectVo> SectionSubjectRefSet(String sectionCode) {
        try {
            return sectionSubjectRefService.sectionSubjectRefSetList(sectionCode);
        } catch (Exception e) {
            log.error("学段学科信息查询异常", e);
            return null;
        }
    }

    /**
     * 学段学科关系保存
     * 
     * @param sectionCode
     * @param subjectCodeArray
     * @return
     * @author gaow
     * @date:2015年12月15日 下午2:36:36
     */
    @RequestMapping(value = "/sectionSubjectRefSave.html")
    public @ResponseBody ResultCodeVo saveSectionSubjectRef(String sectionCode,
                    @RequestParam(value = "subjectCodeArray[]",
                                  required = false) String[] subjectCodeArray) {
        try {
            sectionSubjectRefService.saveSectionSubjectList(sectionCode, subjectCodeArray);
            return ResultCodeVo.rightCode("学段学科关系保存成功");
        } catch (Exception e) {
            log.error("学段学科关系保存异常", e);
            return ResultCodeVo.errorCode("学段学科关系保存异常");
        }
    }

    /**
     * 
     * (根据学段查询学段学科关系列表)<br>
     * 
     * @param sectionCode
     * @return
     */
    @RequestMapping(value = "/list.html")
    @ResponseBody
    public List<Map<String, Object>> getSectionSubjectRelList(HttpServletRequest request) {
        try {
            return sectionSubjectRefService.querySectionSubjectRelList(null);
        } catch (Exception e) {
            log.error("根据学段查询学段学科关系列表失败！", e);
            e.printStackTrace();
            return null;
        }
    }
}
