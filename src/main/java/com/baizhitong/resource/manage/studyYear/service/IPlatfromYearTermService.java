package com.baizhitong.resource.manage.studyYear.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.SharePlatformYearTerm;

public interface IPlatfromYearTermService {
    /*
     * 查询学年学期
     */
    Page getYearTermList(Map<String, Object> mapList);

    /*
     * 查询学期 ()<br>
     * 
     * @return
     */
    List<Map<String, Object>> selectYearTerm(String sectionCodeStr);

    /*
     * 更新或者新增学年学期
     */
    ResultCodeVo updateOrAddYearTerm(SharePlatformYearTerm yearTerm, String startDate, String endDate);

    /**
     * 删除学期 ()<br>
     * 
     * @param gid
     * @return
     */
    ResultCodeVo deleteYearTerm(String gid);

}
