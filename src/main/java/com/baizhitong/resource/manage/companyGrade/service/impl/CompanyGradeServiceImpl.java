package com.baizhitong.resource.manage.companyGrade.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.companyGrade.service.ICompanyGradeService;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;

@Service
public class CompanyGradeServiceImpl extends BaseService implements ICompanyGradeService {
    @Autowired
    ICompanyService companyService;
    @Autowired
    GradeService    gradeService;
    @Autowired
    SectionService  sectionService;

    /**
     * 查询机构年级列表 ()<br>
     * 
     * @return
     */
    public List<ShareCodeGradeVo> getGradeList() {
        List<ShareCodeGradeVo> allList;
        try {
           
            CompanyInfoVo company =  getCompanyInfo();
            List<ShareCodeSectionVo> sectionList = companyService.getCompanySection(company.getOrgCode());
            allList = new ArrayList<ShareCodeGradeVo>();
            if (sectionList != null && sectionList.size() > 0) {
                for (ShareCodeSectionVo section : sectionList) {
                    List<ShareCodeGradeVo> allGradeList = sectionService.getSectionGrade(section.getCode());
                    allList.addAll(allGradeList);
                }
            }
            return allList;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
}
