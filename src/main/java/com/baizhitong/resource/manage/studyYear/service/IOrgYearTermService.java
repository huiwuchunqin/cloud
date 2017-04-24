package com.baizhitong.resource.manage.studyYear.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareOrgYearTerm;

public interface IOrgYearTermService {

    /*
     * 查询学年学期
     */
    Page getOrgYearTermList(Map<String, Object> mapList);

    /*
     * 更新或者新增学年学期
     */
    ResultCodeVo updateOrAddYearTerm(ShareOrgYearTerm yearTerm, String startDate, String endDate);

    /**
     * 删除学期 ()<br>
     * 
     * @param gid
     * @return
     */
    ResultCodeVo deleteOrgYearTerm(String gid);

    /**
     * 添加学期 ()<br>
     * 
     * @param gid
     * @param orgCode
     * @return
     */
    ResultCodeVo addOrgTerm(String gid, String orgCode);

    /**
     * 得到当前的机构学期 ()<br>
     * 
     * @return
     */
    ShareOrgYearTerm getCurrentOrgYearTerm();

    /**
     * 查询学年学期列表 ()<br>
     * 
     * @return
     */
    List<Map<String, Object>> getOrgYearTermList(String orgCode, String sectionCode);
}
