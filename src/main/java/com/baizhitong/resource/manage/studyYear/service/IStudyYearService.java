package com.baizhitong.resource.manage.studyYear.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareCodeStudyYear;

/**
 * 学年学期接口 IStudyYearService TODO
 * 
 * @author creator BZT 2016年4月28日 下午5:31:13
 * @author updater
 *
 * @version 1.0.0
 */
public interface IStudyYearService {
    /*
     * 查询学年列表
     */
    List<ShareCodeStudyYear> getStudyYearList(Map<String, Object> param);

    /*
     * 查询学年分页信息
     */
    Page getStudyYearPage(Map<String, Object> param);
}
