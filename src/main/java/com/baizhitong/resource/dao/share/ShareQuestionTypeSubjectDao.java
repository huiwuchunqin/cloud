package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubject;

public interface ShareQuestionTypeSubjectDao {
    /**
     * 查询所有的学科题型 ()<br>
     * 
     * @return
     */
    public List<ShareQuestionTypeSubject> getAll(String subjectCode);

    /**
     * 查询学科题型分页列表 ()<br>
     * 
     * @return
     */
    public Page<Map<String,Object>> getPageList(Map<String, Object> param, Integer page, Integer rows); 

    /**
     * 保存学科题型 ()<br>
     * 
     * @param shareQuestionSubject
     * @return
     */
    public boolean save(ShareQuestionTypeSubject shareQuestionSubject);

    /**
     * 查询没有选的机构学科题型 ()<br>
     * 
     * @param orgCode
     * @param subjectCode
     * @return
     */
    public List<Map<String, Object>> getNotSelected(String orgCode, String subjectCode);

    /**
     * 查询最大排序 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    public Integer getNextOrder(String subjectCode);

    /**
     * 删除 ()<br>
     * 
     * @param code
     * @return
     */
    public int delete(String code);
}
