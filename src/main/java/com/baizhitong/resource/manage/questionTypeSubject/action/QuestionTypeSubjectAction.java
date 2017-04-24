package com.baizhitong.resource.manage.questionTypeSubject.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.questionType.service.IQuestionTypeService;
import com.baizhitong.resource.manage.questionTypeSubject.service.IQuestionTypeSubjectService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubject;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

@RequestMapping("/manage/questionTypeSubject")
@Controller
public class QuestionTypeSubjectAction extends BaseAction{
    
    private @Autowired IQuestionTypeSubjectService questionTypeSubjectService;
    private @Autowired SectionService              sectionService;
    private @Autowired IQuestionTypeService        questionTypeService;

    /**
     * 
     * (跳转到学科题型管理页面)<br>
     * @param model 数据模型
     * @return
     */
    @RequestMapping("/toQuestionType.html")
    public String toQuestionTypeSubject(ModelMap model) {
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("跳转到学科题型管理页面失败！", e);
            e.printStackTrace();
        }
        return "/manage/company/questionTypeSubject.html"; 
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete.html")
    @ResponseBody
    public ResultCodeVo delete(String code) {
        return questionTypeSubjectService.delete(code);
    }

    /**
     * 查询学科题型列表 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public Page<Map<String,Object>> getQuestionTypeSubject(String sectionCode, String subjectCode, Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("subjectCode", subjectCode);
        return questionTypeSubjectService.getPage(map, page, rows); 

    }

    /**
     * 跳转到学科题型新增 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toQuestionTypeAdd.html")
    public String toAdd(ModelMap model) {
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            model.put("questionTypeList", JSONArray.toJSONString(questionTypeService.getQuestionTypeList()));
            return "/manage/company/questionTypeSubjectAdd.html";
        } catch (Exception e) {
            e.printStackTrace();
            return "/manage/company/questionTypeSubjectAdd.html";
        }
    }

    /**
     * 保存学科题型 ()<br>
     * 
     * @param shareQuestionTypeSubject
     * @return
     */
    @RequestMapping("/addQuestionTypeSubject.html")
    @ResponseBody
    public ResultCodeVo addQuestionTypeSubject(ShareQuestionTypeSubject shareQuestionTypeSubject) {
        return questionTypeSubjectService.add(shareQuestionTypeSubject);
    }

    /**
     * 查询没选择的学科题型 ()<br>
     * 
     * @param subejctCode
     * @return
     */
    @RequestMapping("/getNotSelect.html")
    @ResponseBody
    public List<Map<String, Object>> getNotSelectQuestionTypeList(HttpServletRequest request, String subjectCode) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        return questionTypeSubjectService.getShareQuestionTypeSubejctNotSelect(subjectCode, companyInfoVo.getOrgCode());
    }
    
}
