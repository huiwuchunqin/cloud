package com.baizhitong.resource.dao.company;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.company.ShareOrgGradeSubject;

/**
 * 
 * 机构年级学科业务接口
 * 
 * @author creator BZT 2016年1月21日 下午2:20:15
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareOrgGradeSubjectDao {
    /**
     * 
     * 查询机构学科年级
     * 
     * @param subjectCode 学科编码
     * @param orgCode 机构编码
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOrgSubjectGrade(String subjectCode, String orgCode) throws Exception;

    /**
     * 保存机构年级学科 ()<br>
     * 
     * @param gradeSubjectList
     * @throws Exception
     */
    public void saveOrgSubjectGrade(List<ShareOrgGradeSubject> gradeSubjectList) throws Exception;

    /**
     * 删除机构学科所对应的年级 ()<br>
     * 
     * @param orgCode 机构编码
     * @param subjectCode 学科编码
     * @throws Exception
     */
    public void deleteOrgSubjectGrade(String orgCode, String subjectCode) throws Exception;
}
