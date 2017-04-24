package com.baizhitong.resource.dao.lesson.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.lesson.LessonHomeSetDao;
import com.baizhitong.resource.model.lesson.LessonSettingsHome;
import com.baizhitong.utils.StringUtils;

@Component
public class LessonHomeSetDaoImpl extends BaseSqlServerDao<LessonSettingsHome> implements LessonHomeSetDao {
    
    /**
     * 查询首页课程 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    @Override
    @SuppressWarnings({"unchecked"}) 
    public Page<Map<String, Object>> selectPage(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String lessonName = MapUtils.getString(param, "lessonName");
        sql.append("SELECT");
        sql.append("        a.lessonId");
        sql.append("        ,a.lessonCode");
        sql.append("        ,a.orgCode");
        sql.append("        ,a.sectionCode");
        sql.append("        ,a.gradeCode");
        sql.append("        ,a.subjectCode");
        sql.append("        ,a.lessonName");
        sql.append("        ,a.dispOrder");
        sql.append("        ,a.thumbnailPath");
        sql.append("        ,a.coverPath");
        sql.append("        ,a.flagAvailable");
        sql.append("        ,a.settingTime");
        sql.append("        ,a.settingUserName");
        sql.append("        ,b.orgName");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,scSubject.name AS subjectName ");
        sql.append("    FROM");
        sql.append("        lesson_settings_home a ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org b ");
        sql.append("            ON a.orgCode = b.orgCode ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = a.sectionCode ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = a.gradeCode ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scSubject ");
        sql.append("            ON scSubject.code = a.subjectCode ");
        sql.append("    WHERE");
        sql.append("        1 = 1");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and a.sectionCode=:sectionCode");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and a.gradeCode=:gradeCode");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and a.subjectCode=:subjectCode");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(lessonName)) {
            sql.append(" and a.lessonName like '%" + lessonName.trim() + "%' ");
        }
        sql.append(" order by a.settingTime desc ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 更新使用状态 ()<br>
     * 
     * @param id
     * @param avaliable
     * @return
     */
    @Override
    public int update(Integer id, Integer avaliable) {
        String sql = "update lesson_settings_home  set flagAvailable=? where lessonId=?";
        return super.update(sql, avaliable, id);
    }

    /**
     * 
     * 设置为课程首页
     * 
     * @param lessonName
     *        课程名称
     * @param lessonCode
     *        课程编码
     * @param lessonId
     *        课程id
     * @param orgCode
     *        机构编码
     * @param sectionCode
     *        学段编码
     * @param gradeCode
     *        年级编码
     * @param subjectCode
     *        学科编码
     * @param coverPath
     *        封面地址
     * @param thumbnailPath
     *        缩略图地址
     * @param userName
     *        用户姓名
     */
    @Override
    public int setCourseHome(String lessonName, String lessonCode, Integer lessonId, String orgCode,
                    String sectionCode, String gradeCode, String subjectCode, String coverPath, String thumbnailPath,
                    String userName, boolean available) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        lesson_settings_home");
        sql.append("        ( lessonId, lessonCode,lessonName, orgCode, sectionCode, gradeCode, subjectCode, dispOrder, thumbnailPath, coverPath, flagAvailable, settingTime, settingUserName ) ");
        sql.append("    VALUES");
        sql.append("        ( :lessonId, :lessonCode,:lessonName, :orgCode, :sectionCode, :gradeCode, :subjectCode, (select max(dispOrder)+1 from lesson_settings_home) , :thumbnailPath, :coverPath, :flagAvailable,GETDATE(), :settingUserName ) "); 
        param.put("lessonId", lessonId);
        param.put("lessonCode", lessonCode);
        param.put("orgCode", orgCode);
        param.put("sectionCode", sectionCode);
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("thumbnailPath", thumbnailPath);
        param.put("lessonName", lessonName);
        param.put("coverPath", coverPath);
        param.put("flagAvailable", available);
        param.put("settingUserName", userName);
        return super.update(sql.toString(), param);
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    @Override
    public int delete(Integer id) {
        String sql = "delete from lesson_settings_home where lessonId=?";
        return super.update(sql, id);
    }

    /**
     * 查询首页课程数 ()<br>
     * 
     * @return
     */
    @Override
    public long selectAvailableCount() {
        Map<String,Object> sqlParam=new HashMap<String, Object>();
        String sql = "select count(lessonId) from lesson_settings_home where flagAvailable=:flagAvailable";
        sqlParam.put("flagAvailable", CoreConstants.FLAG_AVAILABLE_YES);
        return super.queryCount(sql, sqlParam); 
    }
    
}
