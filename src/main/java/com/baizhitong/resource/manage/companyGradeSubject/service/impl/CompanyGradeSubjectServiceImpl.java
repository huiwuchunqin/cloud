package com.baizhitong.resource.manage.companyGradeSubject.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.company.ShareOrgGradeSubjectDao;
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.manage.companyGradeSubject.service.ICompanyGradeSubjectService;
import com.baizhitong.resource.model.company.ShareOrgGradeSubject;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.company.SubjectGradeVo;
import com.baizhitong.utils.DateUtils;

/**
 * 
 * 机构年级学科接口实现
 * 
 * @author creator gaow 2016年1月21日 下午5:35:34
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class CompanyGradeSubjectServiceImpl extends BaseService implements ICompanyGradeSubjectService {
    /** 机构年级学科dao */
    @Autowired
    ShareOrgGradeSubjectDao orgGradeSubjectDao;
    /** 年级dao */
    @Autowired
    ShareCodeGradeDao       shareCodeGradeDao;
    /** 学段dao */
    @Autowired
    ShareCodeSectionDao     sectionDao;
    /** 年级dao */
    @Autowired
    ShareCodeGradeDao       gradeDao;
    /** 学科dao */
    @Autowired
    ShareCodeSubjectDao     subjectDao;

    @Override
    public List<SubjectGradeVo> getSubjectGradeList(String subjectCode) {
        try {
           
            CompanyInfoVo companyInfo =  getCompanyInfo();
            List<Map<String, Object>> selectedList = orgGradeSubjectDao.getOrgSubjectGrade(subjectCode,
                            companyInfo.getOrgCode());
            List<SubjectGradeVo> selectListVo = SubjectGradeVo.mapListToVoList(selectedList);
            List<Map<String, Object>> allList = sectionDao.getSectionGrade(subjectCode.substring(0, 1));
            List<SubjectGradeVo> allListVo = SubjectGradeVo.mapListToVoList(allList);
            // 区域学科对应的年级
            List<Map<String, Object>> subjectGradeList = gradeDao.getSubjectGrade(subjectCode);
            List<SubjectGradeVo> subjectGradeVoList = SubjectGradeVo.mapListToVoList(subjectGradeList);
            return Process(allListVo, selectListVo, subjectGradeVoList);
        } catch (Exception e) {
            log.error("查询学科年级失败", e);
            return null;
        }
    }

    /**
     * 给选中的年级打钩 ()<br>
     * 
     * @param allListVo
     * @param selectListVo
     * @return
     */
    public List<SubjectGradeVo> Process(List<SubjectGradeVo> allListVo, List<SubjectGradeVo> selectListVo,
                    List<SubjectGradeVo> subjectGradeVoList) {
        if (allListVo == null || allListVo.size() <= 0)
            return null;
        for (SubjectGradeVo vo : allListVo) {
            /*
             * if (selectListVo == null || selectListVo.size() <= 0){ //如果机构没有设置学科年级关系 则用区域的关系
             * if(subjectGradeVoList!=null&&subjectGradeVoList.size()> 0){ for(SubjectGradeVo
             * selectedVo : subjectGradeVoList){
             * if(vo.getGradeCode().equals(selectedVo.getGradeCode())){ vo.setSelected(1);
             * vo.setEditable(1);
             * 
             * } } } continue; }
             */
            if (selectListVo != null && selectListVo.size() > 0) {
                for (SubjectGradeVo selectedVo : selectListVo) {
                    if (vo.getGradeCode().equals(selectedVo.getGradeCode())) {
                        vo.setSelected(1);
                    }
                }
            }
            if (subjectGradeVoList != null && subjectGradeVoList.size() > 0) {
                for (SubjectGradeVo selectedVo : subjectGradeVoList) {
                    if (vo.getGradeCode().equals(selectedVo.getGradeCode())) {
                        vo.setEditable(1);
                    }
                }
                ;
            }
        }
        return allListVo;
    }

    /**
     * 保存机构学科年级关系 ()<br>
     * 
     * @param gradeCodes 年级编码
     * @param subjectCode 学科编码
     * @return
     */
    public ResultCodeVo saveSubjectGrade(String[] gradeCodes, String subjectCode) {
        // 用户信息
       
        String ip = getIp();
        UserInfoVo userInfoVo =getUserInfoVo();
        CompanyInfoVo companyInfo =  getCompanyInfo();
        String orgCode = companyInfo.getOrgCode();
        /****************** 保存学科年级关系 *****************/
        try {
            orgGradeSubjectDao.deleteOrgSubjectGrade(orgCode, subjectCode);
        } catch (Exception e) {
            log.error("删除机构学科年级失败", e);
            return ResultCodeVo.errorCode("删除机构学科年级失败");
        }
        if (gradeCodes == null || gradeCodes.length <= 0)
            return ResultCodeVo.rightCode("保存成功");
        List<ShareOrgGradeSubject> gradeSubjectList = new ArrayList<ShareOrgGradeSubject>();
        for (String gradeCode : gradeCodes) {
            ShareOrgGradeSubject subject = new ShareOrgGradeSubject();
            ShareCodeSubject areaSubject = subjectDao.getSubject(subjectCode);
            if (areaSubject != null) {
                subject.setDispOrder(areaSubject.getDispOrder());
            }
            subject.setGid(UUID.randomUUID().toString());
            subject.setGradeCode(gradeCode);
            subject.setModifyIP(ip);
            subject.setModifyPgm("companyGradeSubjectService");
            subject.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            subject.setOrgCode(orgCode);
            subject.setSubjectCode(subjectCode);
            subject.setSysVer(0);
            gradeSubjectList.add(subject);
        }
        try {
            orgGradeSubjectDao.saveOrgSubjectGrade(gradeSubjectList);
            return ResultCodeVo.rightCode("保存机构学年级关系成功");
        } catch (Exception e) {
            log.error("保存机构学科年级关系失败", e);
            return ResultCodeVo.errorCode("保存机构学科年级关系失败");
        }

    }

}
