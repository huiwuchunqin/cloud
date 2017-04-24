package com.baizhitong.resource.manage.studyYear.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareCodeYearTerm;

public interface IStudyYearTermService {
    /*
     * 查询学期列表
     */
    List<ShareCodeYearTerm> getTermList(Map<String, Object> map);

    /*
     * 查询学期分页信息 ()<br>
     * 
     * @param map
     * 
     * @return
     */
    Page getTermPageList(Map<String, Object> map);

    /*
     * 新增学期
     */
    ResultCodeVo addTerm(ShareCodeYearTerm shareCodeYearTerm);

    /*
     * 查询学期 ()<br>
     * 
     * @param yearTermCode
     * 
     * @return
     */
    ShareCodeYearTerm getCodeYearTerm(String yearTermCode);

    /*
     * 删除学期
     */
    ResultCodeVo deleteCodeYearTerm(String gid);
}
