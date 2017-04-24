package com.baizhitong.resource.manage.questionType.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareQuestionTypeDao;
import com.baizhitong.resource.dao.share.ShareQuestionTypeSubjectDao;
import com.baizhitong.resource.dao.share.ShareQuestionTypeSubjectOrgDao;
import com.baizhitong.resource.manage.questionType.service.IQuestionTypeService;
import com.baizhitong.resource.model.share.ShareQuestionType;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubject;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubjectOrg;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.StringUtils;

/**
 * 题型接口实现 QuestionTypeServiceImpl TODO
 * 
 * @author creator BZT 2016年5月11日 下午5:27:39
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class QuestionTypeServiceImpl extends BaseService implements IQuestionTypeService {
    private @Autowired ShareQuestionTypeDao           shareQuestionTypeDao;          // 系统学科题型
    private @Autowired ShareQuestionTypeSubjectOrgDao shareQuestionTypeSubjectOrgDao;// 机构学科题型
    private @Autowired ShareQuestionTypeSubjectDao    shareQuestionTypeSubjectDao;   // 学科题型
    private @Autowired ISysCodeService                SysCodeService;                // 编码生成接口

    /**
     * 新增机构学科题型 ()<br>
     * 
     * @param shareQuestionTypeSubjectOrg
     * @return
     */
    public ResultCodeVo addOrgSubjectQuestionType(List<ShareQuestionTypeSubject> questionTypeList, String orgCode) {
       
        if (questionTypeList != null && questionTypeList.size() > 0) {
            for (ShareQuestionTypeSubject questionTypeSubejct : questionTypeList) {
                ShareQuestionTypeSubjectOrg shareQuestionTypeSubjectOrg = new ShareQuestionTypeSubjectOrg();
                shareQuestionTypeSubjectOrg.setGid(UUID.randomUUID().toString());
                shareQuestionTypeSubjectOrg.setCode(questionTypeSubejct.getCode());
                shareQuestionTypeSubjectOrg.setQuestionTypeSubjectOrg(questionTypeSubejct.getCode());
                shareQuestionTypeSubjectOrg.setFlagSysEvaluate(questionTypeSubejct.getFlagSysEvaluate());
                shareQuestionTypeSubjectOrg.setQuestionType(questionTypeSubejct.getQuestionType());
                shareQuestionTypeSubjectOrg.setName(questionTypeSubejct.getName());
                shareQuestionTypeSubjectOrg.setOrgCode(orgCode);
                shareQuestionTypeSubjectOrg.setQuestionTypeSubjectOrgName(questionTypeSubejct.getName());
                shareQuestionTypeSubjectOrg.setModifyIP(getIp());
                shareQuestionTypeSubjectOrg.setDispOrder(questionTypeSubejct.getDispOrder());
                shareQuestionTypeSubjectOrg.setSubjectCode(questionTypeSubejct.getSubjectCode());
                shareQuestionTypeSubjectOrg.setModifyPgm("QuestionTypeService");
                shareQuestionTypeSubjectOrg.setModifyTime(new Timestamp(new Date().getTime()));
                shareQuestionTypeSubjectOrg.setSysVer(1);
                shareQuestionTypeSubjectOrgDao.saveOrUpdate(shareQuestionTypeSubjectOrg);
            }
        }
        return ResultCodeVo.rightCode("保存成功");
    }

    /**
     * 删除学科题型 ()<br>
     * 
     * @param orgQuestionTypeList
     * @return
     */
    public ResultCodeVo deleteOrgSubjectQuestionType(List<ShareQuestionTypeSubjectOrg> orgQuestionTypeList) {
        if (orgQuestionTypeList != null && orgQuestionTypeList.size() > 0) {
            for (ShareQuestionTypeSubjectOrg questionTypeSubejctOrg : orgQuestionTypeList) {
                shareQuestionTypeSubjectOrgDao.delete(questionTypeSubejctOrg.getGid());
            }
        }
        return ResultCodeVo.rightCode("删除成功");
    }

    /**
     * 查询机构学科题型列表 ()<br>
     * 
     * @param orgCode
     * @param subjectCode
     * @return
     */
    public List<ShareQuestionTypeSubjectOrg> getOrgSubjectQuestionTypeList(String orgCode, String subjectCode) {
        return shareQuestionTypeSubjectOrgDao.getBySubject(subjectCode, orgCode);
    }

    /**
     * 
     * 查询题型列表
     * 
     * @return
     */
    public List<ShareQuestionType> getQuestionTypeList() {
        return shareQuestionTypeDao.getAll(); 
    }

    /**
     * 删除 ()<br>
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo deleteOrgQuestionType(String gid) {
        // TODO Auto-generated method stub
        int count = shareQuestionTypeSubjectOrgDao.delete(gid);
        return count > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }

    /**
     * 查询学科题型列表 ()<br>
     * 
     * @return
     */

    public List<ShareQuestionTypeSubject> getShareQuestionTypeSubjectList(String subjectCode) {
        return shareQuestionTypeSubjectDao.getAll(subjectCode);
    }

    /**
     * 插入默认的题型 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    public ResultCodeVo addDefaultQuestionType(String subjectCode, String orgCode) {
       
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        List<ShareQuestionTypeSubject> questionTypeList = getShareQuestionTypeSubjectList(subjectCode);
        if (questionTypeList == null || questionTypeList.size() <= 0)
            return ResultCodeVo.errorCode("系统表里没有数据");
        if (StringUtils.isEmpty(orgCode))
            orgCode = companyInfoVo.getOrgCode();
        for (ShareQuestionTypeSubject questionType : questionTypeList) {
            ShareQuestionTypeSubjectOrg orgQuestionType = shareQuestionTypeSubjectOrgDao.getOrgQuestionType(subjectCode,
                            orgCode, questionType.getQuestionType(), questionType.getName());
            if (orgQuestionType == null) {
                ShareQuestionTypeSubjectOrg shareQuestionTypeSubjectOrg = new ShareQuestionTypeSubjectOrg();
                shareQuestionTypeSubjectOrg.setGid(UUID.randomUUID().toString());
                shareQuestionTypeSubjectOrg.setCode(questionType.getCode());
                shareQuestionTypeSubjectOrg.setQuestionTypeSubjectOrg(questionType.getCode());
                shareQuestionTypeSubjectOrg.setFlagSysEvaluate(questionType.getFlagSysEvaluate());
                shareQuestionTypeSubjectOrg.setQuestionType(questionType.getQuestionType());
                shareQuestionTypeSubjectOrg.setName(questionType.getName());
                shareQuestionTypeSubjectOrg.setOrgCode(orgCode);
                shareQuestionTypeSubjectOrg.setQuestionTypeSubjectOrgName(questionType.getName());
                shareQuestionTypeSubjectOrg.setModifyIP(getIp());
                shareQuestionTypeSubjectOrg.setDispOrder(questionType.getDispOrder());
                shareQuestionTypeSubjectOrg.setSubjectCode(subjectCode);
                shareQuestionTypeSubjectOrg.setModifyPgm("QuestionTypeService");
                shareQuestionTypeSubjectOrg.setModifyTime(new Timestamp(new Date().getTime()));
                shareQuestionTypeSubjectOrg.setSysVer(1);
                shareQuestionTypeSubjectOrgDao.saveOrUpdate(shareQuestionTypeSubjectOrg);
            }
        }
        return ResultCodeVo.rightCode("导入成功");
    }
}
