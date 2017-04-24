package com.baizhitong.resource.manage.companyGradeSubject.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.companyGradeSubject.service.ICompanyGradeSubjectService;

/**
 * 
 * 机构学科年级控制类
 * 
 * @author creator gaow 2016年1月21日 下午6:02:39
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/companyGradeSubject")
public class CompanyGradeSubjectAction extends BaseAction {
    @Autowired
    ICompanyGradeSubjectService companyGradeSubjectService;

    /**
     * 保存学科年级 ()<br>
     * 
     * @param gradeCodes 年级编码
     * @param subjectCode 学科编码
     * @return
     */
    @RequestMapping("/saveGradeSubject.html")
    @ResponseBody
    public ResultCodeVo saveSubjectGrade(@RequestParam(value = "gradeCodes[]",
                                                       required = false) String[] gradeCodes,
                    String subjectCode) {
        return companyGradeSubjectService.saveSubjectGrade(gradeCodes, subjectCode);
    }

}
