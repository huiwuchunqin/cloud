package com.baizhitong.resource.manage.questionType.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.questionType.service.impl.QuestionTypeServiceImpl;
import com.baizhitong.resource.model.share.ShareQuestionType;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubject;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubjectOrg;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

@Controller
@RequestMapping(value = "/manage/questionType")
public class QuestionTypeAction extends BaseAction {
    /**
     * 题型
     */
    private @Autowired QuestionTypeServiceImpl questionTypeServiceImpl;

    /**
     * 跳转到学科题型页面 ()<br>
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "toQuestionType.html")
    public String toQuestionType(ModelMap map, String subjectCode, String subjectName) {
        map.put("subjectCode", subjectCode);
        map.put("subjectName", subjectName);
        return "/manage/company/companyQuestionTypeSubejct.html";
    }

    /**
     * 跳转到题型设置页面 ()<br>
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "toQuestionTypeSet.html")
    public String toQuestionTypeSet(ModelMap map, String subjectCode, String subjectName) {
        map.put("subjectCode", subjectCode);
        map.put("subjectName", subjectName);
        return "/manage/company/companyQuestionTypeSubjectSet.html";
    }

    /**
     * 查询机构学科列表 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    @RequestMapping(value = "getOrgQuestionTypeList.html")
    @ResponseBody
    public List<ShareQuestionTypeSubjectOrg> getOrgQuestionTypeList(HttpServletRequest request, String subjectCode) {
        CompanyInfoVo company =  getCompanyInfo();
        return questionTypeServiceImpl.getOrgSubjectQuestionTypeList(company.getOrgCode(), subjectCode);
    }

    /**
     * 查询学科题型列表 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "questionTypeSubjectList.html")
    @ResponseBody
    public List<ShareQuestionTypeSubject> getQuestionTypeSubejctList(String subjectCode) {
        return questionTypeServiceImpl.getShareQuestionTypeSubjectList(subjectCode);
    }

    /**
     * 查询题型列表 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "questionTypeList.html")
    @ResponseBody
    public List<ShareQuestionType> getQuestionTypeList() {
        return questionTypeServiceImpl.getQuestionTypeList();
    }

    /**
     * 删除机构学科题型 ()<br>
     * 
     * @param gid
     * @return
     */
    @RequestMapping(value = "deleteOrgQuestionType.html")
    @ResponseBody
    public ResultCodeVo deleteQuestionTypeSubject(String questionTypeJSON) {
        return questionTypeServiceImpl.deleteOrgSubjectQuestionType(
                        JSON.parseArray(questionTypeJSON, ShareQuestionTypeSubjectOrg.class));
    }

    /**
     * 保存机构学科题型 ()<br>
     * 
     * @param json
     * @return
     */
    @RequestMapping(value = "saveOrgQuestionType.html")
    @ResponseBody
    public ResultCodeVo saveQuestionTypeSubject(HttpServletRequest request, String questionTypeJSON) {
        List<ShareQuestionTypeSubject> orgQuestionTypeSubjectList = JSON.parseArray(questionTypeJSON,
                        ShareQuestionTypeSubject.class);
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        return questionTypeServiceImpl.addOrgSubjectQuestionType(orgQuestionTypeSubjectList,
                        companyInfoVo.getOrgCode());
    }

    /**
     * 倒入默认的题型 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    @RequestMapping(value = "importDefault.html")
    @ResponseBody
    public ResultCodeVo addDefaultQuestionType(String subjectCode) {
        return questionTypeServiceImpl.addDefaultQuestionType(subjectCode, null);
    }

}
