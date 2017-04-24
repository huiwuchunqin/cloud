package com.baizhitong.resource.manage.companySubject.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.dao.company.ShareOrgGradeSubjectDao;
import com.baizhitong.resource.dao.company.ShareOrgSubjectDao;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.manage.companySubject.service.ICompanySubjectSerivce;
import com.baizhitong.resource.model.company.ShareOrgSubject;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;
import com.baizhitong.utils.DateUtils;

/**
 * 
 * 机构学科业务接口实现
 * 
 * @author creator BZT 2016年1月21日 下午4:05:16
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class CompanySubjectServiceImpl extends BaseService implements ICompanySubjectSerivce {
    /** 机构学科dao */
    @Autowired
    ShareOrgSubjectDao      shareOrgSubjectDao;
    @Autowired
    ShareCodeSectionDao     sectionDao;
    @Autowired
    ShareOrgDao             orgDao;
    @Autowired
    ShareCodeSubjectDao     subjectDao;
    @Autowired
    ShareOrgGradeSubjectDao shareOrgGradeSubjectDao;

    /**
     * 保存机构学科 ()<br>
     * 
     * @param subjectCodes
     * @return
     */
    public ResultCodeVo saveCompanySubject(String[] subjectCodes, String orgCode) {
        if (subjectCodes.length <= 0)
            return ResultCodeVo.errorCode("没有学科信息,保存失败");
        // 用户信息
       
        String ip = getIp();
        CompanyInfoVo companyInfo =  getCompanyInfo();
        if (StringUtils.isEmpty(orgCode)) {
            orgCode = companyInfo.getOrgCode();
        }
        /************** 保存学科 ****************/
        List<ShareOrgSubject> subjects = new ArrayList<ShareOrgSubject>();
        for (String subjectCode : subjectCodes) {
            ShareOrgSubject orgSubject = new ShareOrgSubject();
            orgSubject.setGid(UUID.randomUUID().toString());
            orgSubject.setModifyIP(ip);
            orgSubject.setModifyPgm("companySubjectService");
            orgSubject.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            orgSubject.setOrgCode(orgCode);
            orgSubject.setSubjectCode(subjectCode);
            orgSubject.setSysVer(0);
            subjects.add(orgSubject);
        }

        try {
            shareOrgSubjectDao.saveOrgSubjects(subjects);
            return ResultCodeVo.rightCode("保存成功");
        } catch (Exception e) {
            log.error("保存失败", e);
            return ResultCodeVo.errorCode("保存失败");
        }

    }

    /**
     * 查询机构学科 ()<br>
     * 
     * @param orgCode 机构编码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    public Page getCompanySubject(String orgCode, String sectionCode, Integer pageSize, Integer pageNo) {
        try {
            return shareOrgSubjectDao.getOrgSubject(orgCode, sectionCode, pageSize, pageNo);
        } catch (Exception e) {
            log.error("查询机构学科失败", e);
            return null;
        }
    }

    /**
     * 查询机构学科信息 ()<br>
     * 
     * @return
     */
    public List<ShareCodeSubjectVo> getCompanySubjectList(String sectionCode) {
        try {
           
            CompanyInfoVo companyInfo =  getCompanyInfo();
            if (companyInfo == null)
                return null;
            String orgCode = companyInfo.getOrgCode();
            List<Map<String, Object>> subjectList = shareOrgSubjectDao.getOrgSubjectList(orgCode, sectionCode);
            return new ShareCodeSubjectVo().getMapToVoList(subjectList);
        } catch (Exception e) {
            log.error("查询机构学科信息失败", e);
            return null;
        }

    }

    /**
     * 删除机构学科 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    public ResultCodeVo delCompanySubject(String[] subjectCodes) {
        try {
           
            CompanyInfoVo companyInfo =  getCompanyInfo();
            String orgCode = companyInfo.getOrgCode();
            if (subjectCodes.length <= 0)
                ResultCodeVo.errorCode("没有要删除的学科");
            for (String subjectCode : subjectCodes) {
                shareOrgSubjectDao.delOrgSubjects(orgCode, subjectCode);
                shareOrgGradeSubjectDao.deleteOrgSubjectGrade(orgCode, subjectCode);
            }

            return ResultCodeVo.rightCode("删除成功");
        } catch (Exception e) {
            log.error("删除失败", e);
            return ResultCodeVo.errorCode("删除失败");
        }
    }

    /**
     * 查询机构没有选择的学科列表 ()<br>
     * 
     * @return
     */
    public List<ShareCodeSubjectVo> getCompanyNotSelectSubject() {
       
        CompanyInfoVo companyInfo =  getCompanyInfo();
        List<Map<String, Object>> mapList = orgDao.getOrgSection(companyInfo.getOrgCode());
        List<ShareCodeSectionVo> sectionList = new ShareCodeSectionVo().getMapToVoList(mapList);
        List<ShareCodeSubjectVo> allSubjectVo = new ArrayList<ShareCodeSubjectVo>();
        // 一个机构可能对应多个学段 查询每个学段下的学科
        if (sectionList != null && sectionList.size() > 0) {
            for (ShareCodeSectionVo vo : sectionList) {
                List<Map<String, Object>> list = sectionDao.getSectionSubject(vo.getCode());
                List<ShareCodeSubjectVo> subjectVoList = new ShareCodeSubjectVo().getMapToVoList(list);
                if (list == null || list.size() <= 0)
                    continue;
                allSubjectVo.addAll(subjectVoList);
            }

        }
        List<ShareCodeSubjectVo> companySubjectList = getCompanySubjectList("");
        List<ShareCodeSubjectVo> notSelectedList = new ArrayList<ShareCodeSubjectVo>();
        notSelectedList.addAll(allSubjectVo);
        // 移除已经选过的学科
        if (companySubjectList == null || companySubjectList.size() <= 0)
            return allSubjectVo;
        for (ShareCodeSubjectVo vo : allSubjectVo) {
            for (ShareCodeSubjectVo selectedVo : companySubjectList) {
                if (vo.getCode().equals(selectedVo.getCode())) {
                    notSelectedList.remove(vo);
                }
            }
        }
        return notSelectedList;

    }

}
