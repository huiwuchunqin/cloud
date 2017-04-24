package com.baizhitong.resource.dao.share;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareOrgYearTerm;

/**
 * 机构学年学期接口 ShareOrgYearTermDao
 * 
 * @author creator BZT 2016年4月28日 下午5:13:20
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareOrgYearTermDao {
    /*
     * 查询机构学年学期
     */
    Page getOrgYearTermList(Map<String, Object> map);

    /*
     * 添加或更新机构学年学期
     */
    boolean updateOrAddYearTerm(ShareOrgYearTerm shareOrgYearTerm);

    /*
     * 更新机构学年学期
     */
    int updateOrgYearTerm(String yearTermCode, String orgCode, Timestamp startDate, Timestamp endDate,
                    int totalWeekNum); 

    /**
     * 删除机构学期 ()<br>
     * 
     * @param yearTermCode
     * @return
     */
    int deleteOrgYearTerm(String yearTermCode, String orgCode, String sectionCode);

    /**
     * 查询机构学期 ()<br>
     * 
     * @param yearTermCode
     * @param orgCode
     * @return
     */
    ShareOrgYearTerm getTerm(String yearTermCode, String orgCode, String sectionCode);

    /**
     * 删除学年学期 ()<br>
     * 
     * @param gid
     * @return
     */
    int deleteYearTerm(String gid);

    /**
     * 查询学年学期列表 ()<br>
     * 
     * @return
     */
    List<Map<String, Object>> getOrgYearTermList(String orgCode, String sectionCode);

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
     * (根据gid查询实体)<br>
     * @param gid
     * @return 
     */
    ShareOrgYearTerm selectByGid(String gid);
    
    /**
     * 
     * (根据学段编码和学年学期编码等条件查询上一个或下一个学年学期)<br>
     * @param yearTermCode 学年学期编码
     * @param type 操作类型（1：查询上一个；2：查询下一个）
     * @param orgCode 机构编码
     * @return 符合条件的学年学期信息
     */
    Map<String, Object> selectLastOrNextYearTermBySectionCode(String yearTermCode, Integer type,
                    String orgCode);
    
}
