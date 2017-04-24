package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.share.ShareQuestionTypeSubjectOrg;

/**
 * 学科机构题型 ShareQuestionTypeSubjectOrgDao TODO
 * 
 * @author creator BZT 2016年5月11日 下午5:01:21
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareQuestionTypeSubjectOrgDao {
    /**
     * 查询机构学科题型 ()<br>
     * 
     * @param subjectCode
     * @param orgCode
     * @return
     */
    List<ShareQuestionTypeSubjectOrg> getBySubject(String subjectCode, String orgCode);

    /**
     * 查询机构学科题型 ()<br>
     * 
     * @param subjectCode
     * @param orgCode
     * @param questionType
     * @param name
     * @return
     */
    ShareQuestionTypeSubjectOrg getOrgQuestionType(String subjectCode, String orgCode, String questionType,
                    String name);

    /**
     * 新增机构学科题型 ()<br>
     * 
     * @param shareQuestionTypeSubjectOrg
     * @return
     */
    int add(List<ShareQuestionTypeSubjectOrg> shareQuestionTypeSubjectOrgList);

    /**
     * 新增 ()<br>
     * 
     * @param shareQuestionTypeSubjectOrg
     * @return
     */
    boolean saveOrUpdate(ShareQuestionTypeSubjectOrg shareQuestionTypeSubjectOrg);

    /**
     * 删除机构学科题型 ()<br>
     * 
     * @param gid
     * @return
     */
    int delete(String gid);

}
