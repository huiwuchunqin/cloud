package com.baizhitong.resource.manage.questionType.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareQuestionType;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubject;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubjectOrg;

/**
 * 题型接口 IQuestionTypeService TODO
 * 
 * @author creator BZT 2016年5月11日 下午5:27:20
 * @author updater
 *
 * @version 1.0.0
 */
public interface IQuestionTypeService {

    /**
     * 查询机构学科题型列表 ()<br>
     * 
     * @param orgCode
     * @param subjectCode
     * @return
     */
    List<ShareQuestionTypeSubjectOrg> getOrgSubjectQuestionTypeList(String orgCode, String subjectCode);

    /**
     * 查询题型列表 ()<br>
     * 
     * @return
     */
    List<ShareQuestionType> getQuestionTypeList();

    /**
     * 查询学科题型列表 ()<br>
     * 
     * @return
     */
    List<ShareQuestionTypeSubject> getShareQuestionTypeSubjectList(String subjectCode);

    /**
     * 删除机构学科题型 ()<br>
     * 
     * @param gid
     * @return
     */
    ResultCodeVo deleteOrgQuestionType(String gid);

    /**
     * 新增机构学科题型 ()<br>
     * 
     * @param shareQuestionTypeSubjectOrg
     * @return
     */
    ResultCodeVo addOrgSubjectQuestionType(List<ShareQuestionTypeSubject> questionTypeList, String orgCode);

    /**
     * 删除学科题型 ()<br>
     * 
     * @param orgQuestionTypeList
     * @return
     */
    ResultCodeVo deleteOrgSubjectQuestionType(List<ShareQuestionTypeSubjectOrg> orgQuestionTypeList);

}
