package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareCodeStudyYear;

/**
 * 学年信息 ShareCodeStudyYearDao
 * 
 * @author creator BZT 2016年4月28日 下午5:06:50
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareCodeStudyYearDao {
    /*
     * 查询学年列表
     */
    List<ShareCodeStudyYear> getStudyYearList(Map<String, Object> param);

    /*
     * 查询学年分页信息
     */
    Page getStudyYearPage(Map<String, Object> param);

    /**
     * 保存学年 ()<br>
     * 
     * @param shareCodeStudyYear
     * @return
     */
    boolean addStudyYear(ShareCodeStudyYear shareCodeStudyYear);

    /**
     * 查询学年 ()<br>
     * 
     * @param yearCode
     * @return
     */
    ShareCodeStudyYear getYear(String yearCode);

    /**
     * 删除学年 ()<br>
     * 
     * @param studyYearCode
     */
    void deleteStudyYear(String studyYearCode);

    /**
     * 
     * (查询所有学年列表)<br>
     * 
     * @return
     */
    List<ShareCodeStudyYear> selectList();
}
