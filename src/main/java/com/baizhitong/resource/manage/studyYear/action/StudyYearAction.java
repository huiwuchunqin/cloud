package com.baizhitong.resource.manage.studyYear.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.studyYear.service.IOrgYearTermService;
import com.baizhitong.resource.manage.studyYear.service.IPlatfromYearTermService;
import com.baizhitong.resource.manage.studyYear.service.IStudyYearService;
import com.baizhitong.resource.manage.studyYear.service.IStudyYearTermService;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.share.ShareCodeStudyYear;
import com.baizhitong.resource.model.share.ShareCodeYearTerm;
import com.baizhitong.resource.model.share.ShareOrgYearTerm;
import com.baizhitong.resource.model.share.SharePlatformYearTerm;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;

@Controller
@RequestMapping("/manage/studyYear")
public class StudyYearAction extends BaseAction {
    /**
     * 学年接口
     */
    private @Autowired IStudyYearService        studyYearService;
    /**
     * 学年学期接口
     */
    private @Autowired IStudyYearTermService    studyYearTermService;
    /**
     * 机构学年学期接口
     */
    private @Autowired IOrgYearTermService      orgYearTermService;
    /**
     * 平台学年学期接口
     */
    private @Autowired IPlatfromYearTermService platformYearTermService; 
    /**
     * 学段接口
     */
    private @Autowired SectionService           sectionService;
    /**
     * 机构接口
     */
    private @Autowired ICompanyService          companyService;

    /**
     * 添加学年 ()<br>
     * 
     * @param studyYear
     * @return
     */
    @RequestMapping("/addStudyYear")
    public ResultCodeVo addStudyYear(ShareCodeStudyYear studyYear) {
        return null;
    }

    /**
     * 
     * 查询学年列表
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/studyYearPageList")
    @ResponseBody
    public Page getStudyYearPageList(Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("page", page);
        param.put("rows", rows);
        return studyYearService.getStudyYearPage(param);
    }

    /**
     * 
     * 查询学年学期列表
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/studyYearTermPageList")
    @ResponseBody
    public Page getStudyYearTermPageList(Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("page", page);
        param.put("rows", rows);
        return studyYearTermService.getTermPageList(param);
    }

    /**
     * 查询学期列表 ()<br>
     * 
     * @return
     */
    @RequestMapping("/studyYearTermList")
    @ResponseBody
    public List<ShareCodeYearTerm> getTermList() {
        Map<String, Object> map = new HashMap<String, Object>();
        return studyYearTermService.getTermList(map);

    }

    /**
     * 添加学期 ()<br>
     * 
     * @param shareCodeYearTerm
     * @return
     */
    @RequestMapping("/addStudyYearTerm")
    @ResponseBody
    public ResultCodeVo addStudyYearTerm(ShareCodeYearTerm shareCodeYearTerm) {
        return studyYearTermService.addTerm(shareCodeYearTerm);
    }

    /**
     * 删除学期 ()<br>
     * 
     * @param gid
     * @return
     */
    @RequestMapping("/deleteStudyYearTerm")
    @ResponseBody
    public ResultCodeVo deleteStudyYearTerm(String gid) {
        return studyYearTermService.deleteCodeYearTerm(gid);
    }

    /**
     * 删除机构学年学期 ()<br> 
     * 
     * @param gid
     * @return
     */
    @RequestMapping("/deleteOrgYearTerm")
    @ResponseBody
    public ResultCodeVo Org(String gid) {
        return orgYearTermService.deleteOrgYearTerm(gid);
    }

    /**
     * 跳转到机构学年学期页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toOrgYearTerm")
    public String toOrgYearTerm(HttpServletRequest request, ModelMap model) {
        try {
            CompanyInfoVo company =  getCompanyInfo();
            List<ShareCodeSectionVo> sectionList = companyService.getCompanySection(company.getOrgCode());
            String sectionCodeStr = "";
            if (sectionList != null && sectionList.size() > 0) {
                for (int i = 0; i < sectionList.size(); i++) {
                    sectionCodeStr = sectionCodeStr + sectionList.get(i).getCode() + ",";
                }
            }
            sectionCodeStr = sectionCodeStr.substring(0, sectionCodeStr.length() - 1);
            List<ShareCodeYearTerm> termList = studyYearTermService.getTermList(null);
            List<Map<String, Object>> platTermList = platformYearTermService.selectYearTerm(sectionCodeStr);
            model.put("termList", JSON.toJSONString(termList));
            model.put("platTermList", JSON.toJSONString(platTermList));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "/manage/studyYearTerm/orgYearTerm.html";
    }

    /**
     * 
     * 查询学年学期列表
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/orgYearTermPageList")
    @ResponseBody
    public Page getOrgYearTermPageList(HttpServletRequest request, Integer page, Integer rows) {
        UserInfoVo userInfoVo =getUserInfoVo();
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("page", page);
        param.put("rows", rows);
        param.put("orgCode", userInfoVo.getOrgCode());
        return orgYearTermService.getOrgYearTermList(param);
    }

    /**
     * 添加机构学年学期 ()<br>
     * 
     * @param sharePlatformYearTerm
     * @return
     */
    @RequestMapping("/updateOrgYearTerm")
    @ResponseBody
    public ResultCodeVo addOrgYearTerm(ShareOrgYearTerm shareOrgYearTerm, String startDateStr, String endDateStr) {
        return orgYearTermService.updateOrAddYearTerm(shareOrgYearTerm, startDateStr, endDateStr);
    }

    /**
     * 添加机构学年学期 ()<br>
     * 
     * @param shareOrgYearTerm
     * @return
     */
    @RequestMapping("/selectIntoOrgTerm")
    @ResponseBody
    public ResultCodeVo addOrgYearTerm(String gid) {
        return orgYearTermService.addOrgTerm(gid, null);
    }

    /**
     * 跳转到平台学年学期页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPlatfromYearTerm")
    public String toPlatformYearTerm(ModelMap model) {
        try {
            List<ShareCodeYearTerm> termList = studyYearTermService.getTermList(null);
            List<ShareCodeSection> sectionList = sectionService.selectSectionList();
            model.put("termList", JSON.toJSONString(termList));
            model.put("sectionList", JSON.toJSONString(sectionList));
            return "/manage/studyYearTerm/platformYearTerm.html";
        } catch (Exception e) {
            return "/manage/studyYearTerm/platformYearTerm.html";
        }
    }

    /**
     * 
     * 查询平台学年学期列表
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/platformYearTermPageList")
    @ResponseBody
    public Page getPlatformYearTermPageList(Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("page", page);
        param.put("rows", rows);
        return platformYearTermService.getYearTermList(param);
    }

    /**
     * 
     * ()<br>
     * 
     * @param request
     * @param sectionCode
     * @return
     */
    @RequestMapping("/getOrgTermList.html")
    @ResponseBody
    public List<Map<String, Object>> getOrgYearTermList(HttpServletRequest request, String sectionCode) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        return orgYearTermService.getOrgYearTermList(companyInfoVo.getOrgCode(), sectionCode);
    }

    /**
     * 添加平台学年学期 ()<br>
     * 
     * @param sharePlatformYearTerm
     * @return
     */
    @RequestMapping("/addPlatfromYearTerm")
    @ResponseBody
    public ResultCodeVo addPlatYearTerm(SharePlatformYearTerm sharePlatformYearTerm, String startDateStr,
                    String endDateStr) {
        return platformYearTermService.updateOrAddYearTerm(sharePlatformYearTerm, startDateStr, endDateStr);
    }

    /**
     * 删除平台学期 ()<br>
     * 
     * @param gid
     * @return
     */
    @RequestMapping("/deletePlatYearTerm")
    @ResponseBody
    public ResultCodeVo deletePlatYearTerm(String gid) {
        return platformYearTermService.deleteYearTerm(gid);
    }

}
