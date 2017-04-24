package com.baizhitong.resource.manage.companyGradeSubject.service;

import java.util.List;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.vo.company.SubjectGradeVo;

public interface ICompanyGradeSubjectService {
    /**
     * 查询机构学科年级列表 ()<br>
     * 
     * @param subjectCode 学科编码
     * @return
     */
    public List<SubjectGradeVo> getSubjectGradeList(String subjectCode);

    /**
     * 保存机构年级 ()<br>
     * 
     * @param gradeCodes 年级编码
     * @param subjectCode 学科
     * @return
     */
    public ResultCodeVo saveSubjectGrade(String[] gradeCodes, String subjectCode);

}
