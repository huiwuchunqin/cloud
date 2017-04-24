package com.baizhitong.resource.manage.questionTypeSubject.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.ShareQuestionTypeSubjectDao;
import com.baizhitong.resource.manage.questionTypeSubject.service.IQuestionTypeSubjectService;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubject;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;

@Service
public class QuestionTypeSubjectServiceImpl extends BaseService implements IQuestionTypeSubjectService {
    /**
     * 主键生成
     */
    private @Autowired ISysCodeService             sysCodeService;
    /**
     * 平台dao
     */
    private @Autowired SharePlatformDao            sharePlatformDao;
    /**
     * 题型学科dao
     */
    private @Autowired ShareQuestionTypeSubjectDao shareQuestionTypeSubjectDao;

    /**
     * 查询学科题型分页信息 ()<br>
     * 
     * @param map
     * @param page
     * @param rows
     * @return
     */
    public Page<Map<String,Object>> getPage(Map<String, Object> map, Integer page, Integer rows) {
        return shareQuestionTypeSubjectDao.getPageList(map, page, rows);
    }

    /**
     * 保存学科题型信息 ()<br>
     * 
     * @param shareQuestionTypeSubject
     * @return
     */
    public ResultCodeVo add(ShareQuestionTypeSubject shareQuestionTypeSubject) {
        SharePlatform platform = sharePlatformDao.getByCodeGlobal();
        String code = sysCodeService.getCode(SysCodeConstants.PLATFORM_QUESTIONTYPE_CODE, "subjectCode",
                        shareQuestionTypeSubject.getSubjectCode(), "platformCode", platform.getCodeGlobal(),
                        "questionType", shareQuestionTypeSubject.getQuestionType());
        Integer nextOrder = shareQuestionTypeSubjectDao.getNextOrder(shareQuestionTypeSubject.getSubjectCode());
        shareQuestionTypeSubject.setCode(code);
        shareQuestionTypeSubject.setGid(code);
        shareQuestionTypeSubject.setDispOrder(nextOrder);
        shareQuestionTypeSubject.setSysVer(0);
        shareQuestionTypeSubject.setFlagDelete(CoreConstants.FLAG_DELETE_NO);
        shareQuestionTypeSubject.setModifyIP(getIp());
        shareQuestionTypeSubject.setModifyPgm("questionTypeSubjectService");
        shareQuestionTypeSubject.setModifyTime(new Timestamp(new Date().getTime()));
        boolean success = shareQuestionTypeSubjectDao.save(shareQuestionTypeSubject);
        return success ? ResultCodeVo.rightCode("保存成功！") : ResultCodeVo.errorCode("保存失败！");
    }

    /**
     * 查询机构学科没有选的学科题型列表 ()<br>
     * 
     * @param subjectCode
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getShareQuestionTypeSubejctNotSelect(String subjectCode, String orgCode) {
        return shareQuestionTypeSubjectDao.getNotSelected(orgCode, subjectCode);
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    public ResultCodeVo delete(String code) {
        int count = shareQuestionTypeSubjectDao.delete(code);
        return count > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }

}
