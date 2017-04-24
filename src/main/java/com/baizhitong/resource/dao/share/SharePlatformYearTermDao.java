package com.baizhitong.resource.dao.share;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.SharePlatformYearTerm;

/**
 * 平台学年学期DAO
 * 
 * @author creator BZT 2016年4月28日 下午5:10:29
 * @author updater
 *
 * @version 1.0.0
 */
public interface SharePlatformYearTermDao {
    /*
     * 查询学年学期
     */
    Page getYearTermList(Map<String, Object> mapList);

    /*
     * 更新或者新增学年学期
     */
    int updateOrAddYearTerm(SharePlatformYearTerm yearTerm);

    /*
     * 查询平台学期
     */
    SharePlatformYearTerm getPlatformYearTerm(String yeaarTermCode, String sectionCode);

    /**
     * 根据id查询 ()<br>
     * 
     * @param gid
     * @return
     */
    SharePlatformYearTerm getPlatformYearTermById(String gid);

    /**
     * 查询学期列表 ()<br>
     * 
     * @param sectionCodeStr 学段区间
     * @return
     */
    List<Map<String, Object>> selectPlatformList(String sectionCodeStr);

    /**
     * 删除平台学年学期 ()<br>
     * 
     * @param gid
     * @return
     */
    int deleteYearTerm(String gid);

    /**
     * 
     * (根据学年学期编码和当前时间查询符合条件的记录)<br>
     * 
     * @param yearTermCode 学年学期编码
     * @param currentDate 当前时间
     * @return 平台学年学期列表
     */
    List<SharePlatformYearTerm> selectListByYearTermCode(String yearTermCode, Timestamp currentDate);

    /**
     * 
     * (根据相关条件更新开始时间)<br>
     * 
     * @param yearTermCode 学年学期编码
     * @param sectionCode 学段编码
     * @param currentDate 当前时间
     * @param userName 用户姓名
     * @param modifyIP 更新者IP
     * @return 更新记录数
     */
    int updateStartDateByYearTermCode(String yearTermCode, String sectionCode, Timestamp currentDate, String userName,
                    String modifyIP);

    /**
     * 
     * (根据当前的学年学期编码获取上一个学年学期编码)<br>
     * 
     * @param yearTermCode 当前学年学期编码
     * @return 上一个学年学期编码
     */
    String selectPreviousYearTermCode(String yearTermCode);

    /**
     * 
     * (根据学年学期编码查询平台学年学期信息列表)<br>
     * 
     * @param yearTermCode 学年学期编码
     * @return 平台学年学期信息列表
     */
    List<SharePlatformYearTerm> selectListByYearTermCode(String yearTermCode);
    
    /**
     * 
     * (根据学段编码和学年学期编码等条件查询上一个或下一个学年学期)<br>
     * @param sectionCode 学段编码
     * @param yearTermCode 学年学期编码
     * @param type 操作类型（1：查询上一个；2：查询下一个）
     * @return 符合条件的学年学期信息
     */
    Map<String, Object> selectLastOrNextYearTermBySectionCode(String sectionCode, String yearTermCode, Integer type);
    
}
