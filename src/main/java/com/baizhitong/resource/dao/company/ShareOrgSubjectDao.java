package com.baizhitong.resource.dao.company;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.company.ShareOrgSubject;

/**
 * 
 * 机构学科业务接口
 * 
 * @author creator BZT 2016年1月21日 下午2:19:16
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareOrgSubjectDao {
    /**
     * 保存机构学科 ()<br>
     * 
     * @param orgCode 机构编码
     * @param subjectCode 学科
     */
    public void saveOrgSubjects(List<ShareOrgSubject> orgSubject) throws Exception;

    /**
     * 删除机构学科 ()<br>
     * 
     * @param orgCode 机构编码
     * @param orgCode 学科编码
     */
    public void delOrgSubjects(String orgCode, String subjectCode) throws Exception;

    /**
     * 查询机构学科 ()<br>
     * 
     * @param orgCode 机构编码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     */
    public Page getOrgSubject(String orgCode, String sectionCode, Integer pageSize, Integer pageNo) throws Exception;

    /**
     * 查询机构学科
     * 
     * @param pageSize 机构编码
     */
    public List<Map<String, Object>> getOrgSubjectList(String orgCode, String sectionCode) throws Exception;

}
