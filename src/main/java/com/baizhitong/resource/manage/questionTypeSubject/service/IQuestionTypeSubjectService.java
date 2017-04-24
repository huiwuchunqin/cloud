package com.baizhitong.resource.manage.questionTypeSubject.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubject;

public interface IQuestionTypeSubjectService {
    /**
     * 查询 ()<br>
     * 
     * @param map
     * @param page
     * @param rows
     * @return
     */
    public Page<Map<String,Object>> getPage(Map<String, Object> map, Integer page, Integer rows);

    /**
     * 新增学科题型 ()<br>
     * 
     * @param shareQuestionTypeSubject
     * @return
     */
    public ResultCodeVo add(ShareQuestionTypeSubject shareQuestionTypeSubject);

    /**
     * 查询机构学科没有选的题型列表 ()<br>
     * 
     * @param subjectCode
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getShareQuestionTypeSubejctNotSelect(String subjectCode, String orgCode);

    /**
     * 删除 ()<br>
     * 
     * @param code
     * @return
     */
    public ResultCodeVo delete(String code);
}
