package com.baizhitong.resource.dao.lesson.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.lesson.LessonDao;
import com.baizhitong.resource.model.lesson.Lesson;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.ObjectUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 课时DAO实现
 * 
 * @author creator ZhangQiang 2016年8月23日 下午4:38:48
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class LessonDaoImpl extends BaseSqlServerDao<Lesson> implements LessonDao {

    /**
     * 
     * (查询课时审核全部信息)<br>
     * 
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectPageAll(Map<String, Object> param, String sectionCodes, String subjectCodes,
                    String gradeCodes) {
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        Integer shareCheckStatus = MapUtils.getInteger(param, "shareCheckStatus");
        String orgCode = MapUtils.getString(param, "orgCode");
        String orgName = MapUtils.getString(param, "orgName");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String lessonName = MapUtils.getString(param, "lessonName");
        String teacherName = MapUtils.getString(param, "teacherName");
        Integer shareLevel=MapUtils.getInteger(param,"shareLevel");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        l.lessonName");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,scs2.name AS subjectName");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,l.teacherName");
        sql.append("        ,l.orgName");
        sql.append("        ,l.shareCheckStatus");
        sql.append("        ,l.createTime");
        sql.append("        ,l.lessonCode ");
        sql.append("        ,l.id ");
        sql.append("        ,l.shareLevel ");
        sql.append("        ,l.shareCheckLevel");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON l.sectionCode = scs.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs2 ");
        sql.append("            ON l.subjectCode = scs2.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON l.gradeCode = scg.code");
        sql.append("    WHERE l.flagDelete = 0");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
            sql.append(" AND l.createTime>= :startDate");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
            sql.append(" AND l.createTime<= :endDate");
            sqlParam.put("endDate", endDate);
        }
        if (shareCheckStatus != null && 60 == shareCheckStatus.intValue()) {
            sql.append("  AND (l.shareCheckStatus= :shareCheckStatus or l.shareCheckStatus is null ) ");
            sqlParam.put("shareCheckStatus", CoreConstants.CHECK_STATUS_UNCOMMIT);
        }
        if (shareCheckStatus != null && 60 != shareCheckStatus.intValue()) {
            sql.append("  AND l.shareCheckStatus= :shareCheckStatus ");
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("  AND l.orgCode= :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("  AND l.orgName like :orgName");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append("  AND l.sectionCode= :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append("  AND l.subjectCode= :subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if(shareLevel!=null){
            sql.append("  AND l.shareLevel= :shareLevel ");
            sqlParam.put("shareLevel", shareLevel); 
        }
        if (StringUtils.isNotEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append("  AND l.gradeCode= :gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        // 教研员登录
        if (StringUtils.isNotEmpty(sectionCodes)) {
            if (sectionCodes.contains(",")) {
                sectionCodes = "'" + sectionCodes.replace(",", "','") + "'";
            } else {
                sectionCodes = "'" + sectionCodes + "'";
            }
            sql.append(" AND l.sectionCode IN (" + sectionCodes + ") ");
        }
        if (StringUtils.isNotEmpty(subjectCodes)) {
            if (subjectCodes.contains(",")) {
                subjectCodes = "'" + subjectCodes.replace(",", "','") + "'";
            } else {
                subjectCodes = "'" + subjectCodes + "'";
            }
            sql.append(" AND l.subjectCode IN (" + subjectCodes + ") ");
        }
        if (StringUtils.isNotEmpty(gradeCodes)) {
            if (gradeCodes.contains(",")) {
                gradeCodes = "'" + gradeCodes.replace(",", "','") + "'";
            } else {
                gradeCodes = "'" + gradeCodes + "'";
            }
            sql.append(" AND l.gradeCode IN (" + gradeCodes + ") ");
        }
        if (StringUtils.isNotEmpty(lessonName)) {
            sql.append("  AND l.lessonName like :lessonName");
            sqlParam.put("lessonName", "%" + lessonName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("  AND l.teacherName like :teacherName");
            sqlParam.put("teacherName", "%" + teacherName.trim() + "%");
        }
        sql.append(" ORDER BY l.createTime DESC ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 
     * (根据审核状态，分页查询课时审核信息)<br>
     * 
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectPageCheck(Map<String, Object> param, String sectionCodes,
                    String subjectCodes, String gradeCodes) {
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        Integer shareCheckStatus = MapUtils.getInteger(param, "shareCheckStatus");
        String orgCode = MapUtils.getString(param, "orgCode");
        String orgName = MapUtils.getString(param, "orgName");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String lessonName = MapUtils.getString(param, "lessonName");
        String teacherName = MapUtils.getString(param, "teacherName");
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        l.lessonName");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,scs2.name AS subjectName");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,l.teacherName");
        sql.append("        ,l.orgName");
        sql.append("        ,l.shareCheckStatus");
        sql.append("        ,l.shareTime");
        sql.append("        ,l.shareCheckTime");
        sql.append("        ,l.shareCheckLevel");
        sql.append("        ,l.lessonCode");
        sql.append("        ,l.id");
        sql.append("        ,l.shareLevel ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON l.sectionCode = scs.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs2 ");
        sql.append("            ON l.subjectCode = scs2.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON l.gradeCode = scg.code");
        sql.append("    WHERE l.flagDelete = 0");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
            if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
                sql.append(" AND l.shareTime>= :startDate");
            } else {
                sql.append(" AND l.shareCheckTime>= :startDate");
            }
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
            if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
                sql.append(" AND l.shareTime<= :endDate"); 
            } else {
                sql.append(" AND l.shareCheckTime<= :endDate");
            }
            sqlParam.put("endDate", endDate);
        }
        if (ObjectUtils.isNotNull(shareCheckStatus)) {
            sql.append("  AND l.shareCheckStatus= :shareCheckStatus ");
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("  AND l.orgCode= :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("  AND l.orgName like :orgName");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append("  AND l.sectionCode= :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append("  AND l.subjectCode= :subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append("  AND l.gradeCode= :gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        // 教研员登录
        if (StringUtils.isNotEmpty(sectionCodes)) {
            if (sectionCodes.contains(",")) {
                sectionCodes = "'" + sectionCodes.replace(",", "','") + "'";
            } else {
                sectionCodes = "'" + sectionCodes + "'";
            }
            sql.append(" AND l.sectionCode IN (" + sectionCodes + ") ");
        }
        if (StringUtils.isNotEmpty(subjectCodes)) {
            if (subjectCodes.contains(",")) {
                subjectCodes = "'" + subjectCodes.replace(",", "','") + "'";
            } else {
                subjectCodes = "'" + subjectCodes + "'";
            }
            sql.append(" AND l.subjectCode IN (" + subjectCodes + ") ");
        }
        if (StringUtils.isNotEmpty(gradeCodes)) {
            if (gradeCodes.contains(",")) {
                gradeCodes = "'" + gradeCodes.replace(",", "','") + "'";
            } else {
                gradeCodes = "'" + gradeCodes + "'";
            }
            sql.append(" AND l.gradeCode IN (" + gradeCodes + ") ");
        }
        if (StringUtils.isNotEmpty(lessonName)) {
            sql.append("  AND l.lessonName like :lessonName");
            sqlParam.put("lessonName", "%" + lessonName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("  AND l.teacherName like :teacherName");
            sqlParam.put("teacherName", "%" + teacherName.trim() + "%");
        }
        if (ObjectUtils.isNotNull(shareCheckLevel)) {
            sql.append(" AND l.shareCheckLevel = :shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        // 查询待审核数据的情况
        if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
            sql.append(" ORDER BY l.shareTime DESC ");
        } else {
            sql.append(" ORDER BY l.shareCheckTime DESC ");
        }
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 
     * (课时审核)<br>
     * 
     * @param lessonCode 资源编码
     * @param shareCheckLevel 共享审核级别
     * @param modifier 更新者姓名
     * @param modifierIP 更新者IP
     * @param shareCheckStatus 共享审核状态
     * @return
     */
    @Override
    public int updateShareCheckStatus(String lessonCode, Integer shareCheckLevel, String modifier, String modifierIP,
                    Integer shareCheckStatus) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp timeNow = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sqlParam.put("timeNow", timeNow);
        sql.append("UPDATE");
        sql.append("        lesson ");
        sql.append("    SET");
        sql.append("        shareCheckTime = :timeNow");
        sql.append("        ,shareCheckStatus = :shareCheckStatus");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :timeNow ");
        sql.append("        ,sysVer = sysVer+1 ");
        if (CoreConstants.CHECK_STATUS_CHECKED.toString().equals(shareCheckStatus.toString())) {
            sql.append("    ,shareLevel = :shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        sql.append("    WHERE");
        sql.append("        lessonCode = :lessonCode");
        sqlParam.put("lessonCode", lessonCode);
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifierIP", modifierIP);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (批量操作课时资源)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    @Override
    public int updateFlagDeleteBatch(String ids, Integer operateType) {
        String sql = "update lesson set flagDelete = :flagDelete where id in (" + ids + ") ";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // 删除
        if (1 == operateType.intValue()) {
            sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_PACED);
        } else if (2 == operateType.intValue()) {
            // 恢复
            sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        }
        return super.update(sql, sqlParam);
    }

    /**
     * 
     * (根据课时编码查询课时信息)<br>
     * 
     * @param lessonCode 课时编码
     * @return 课时信息
     */
    @Override
    public Lesson selectByLessonCode(String lessonCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("lessonCode", lessonCode);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 
     * (查询可以设置在首页显示的课程信息)<br>
     * @param param 查询参数
     * @param page 当前页码
     * @param rows 每页大小
     * @return 课程信息列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectLessonHomeSettingPage(Map<String, Object> param, Integer page,
                    Integer rows) {
        // 查询参数
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String lessonName = MapUtils.getString(param, "lessonName");
        String teacherName = MapUtils.getString(param, "teacherName");
        String orgName = MapUtils.getString(param, "orgName");
        Integer shareLevel = MapUtils.getInteger(param, "shareLevel");
        // SQL语句
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        sql.append("SELECT");
        sql.append("        l.shareLevel ");
        sql.append("        ,l.lessonName "); 
        sql.append("        ,l.orgName "); 
        sql.append("        ,l.teacherName ");
        sql.append("        ,l.createTime "); 
        sql.append("        ,l.lessonCode "); 
        sql.append("        ,l.id ");
        sql.append("        ,l.orgCode ");
        sql.append("        ,l.sectionCode ");
        sql.append("        ,l.subjectCode ");
        sql.append("        ,l.gradeCode ");
        sql.append("        ,scs.name AS sectionName ");
        sql.append("        ,scs2.name AS subjectName ");
        sql.append("        ,scg.name AS gradeName ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = l.sectionCode ");
        sql.append("            AND scs.flagDelete = :flagDelete ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs2 ");
        sql.append("            ON scs2.code = l.subjectCode ");
        sql.append("            AND scs2.flagDelete = :flagDelete ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = l.gradeCode ");
        sql.append("            AND scg.flagDelete = :flagDelete ");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = :flagDelete ");
        sql.append("        AND l.lessonCode NOT ");
        sql.append("    IN (");
        sql.append("        SELECT");
        sql.append("            lessonCode FROM");
        sql.append("                lesson_settings_home ");
        sql.append("        ) ");
        
        if (ObjectUtils.isNotNull(shareLevel)) {
            sql.append("        AND l.shareLevel = :shareLevel ");
            sqlParam.put("shareLevel", shareLevel);
        } else {
            sql.append("        AND l.shareLevel IN (20,30) ");
        }
        sql.append("        AND l.shareCheckStatus = :shareCheckStatus ");
        sqlParam.put("shareCheckStatus", CoreConstants.CHECK_STATUS_CHECKED);
        
        if (StringUtils.isNotEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" and  l.gradeCode = :gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" and l.subjectCode = :subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append(" and l.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and l.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName + "%");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append(" and l.teacherName like :teacherName ");
            sqlParam.put("teacherName", "%" + teacherName + "%");
        }
        
        if (StringUtils.isNotEmpty(lessonName)) {
            sql.append(" and l.lessonName like :lessonName ");
            sqlParam.put("lessonName", "%" + lessonName + "%");
        }
        sql.append(" order by l.createTime desc ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

}
