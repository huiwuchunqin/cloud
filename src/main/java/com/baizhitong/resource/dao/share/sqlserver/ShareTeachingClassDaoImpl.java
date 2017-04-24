package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassDao;
import com.baizhitong.resource.model.share.ShareTeachingClass;
import com.baizhitong.utils.StringUtils;

@Component
public class ShareTeachingClassDaoImpl extends BaseSqlServerDao<ShareTeachingClass> implements ShareTeachingClassDao {

    @Autowired
    private SharePlatformDao sharePlatformDao;

    /**
     * 保存课程班级 ()<br>
     * 
     * @param shareTeachingClass
     * @return
     */
    @Override
    public boolean saveShareTeachingClass(ShareTeachingClass shareTeachingClass) {
        try {
            return super.saveOne(shareTeachingClass);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 查询课程班级 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Page getList(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String teachingClassName = MapUtils.getString(param, "teachingClassName");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String teacherName = MapUtils.getString(param, "teacherName");
        String orgCode = MapUtils.getString(param, "orgCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String studyYearCode = BeanHelper.getStudyYearCode();
        sql.append("SELECT");
        sql.append("        stc.gid");
        sql.append("        ,stc.orgCode");
        sql.append("        ,stc.adminClassCode");
        sql.append("        ,stc.teachingClassCode");
        sql.append("        ,stc.teachingClassName");
        sql.append("        ,stc.memo");
        sql.append("        ,stc.modifyPgm");
        sql.append("        ,stc.modifyTime");
        sql.append("        ,stc.modifyIP");
        sql.append("        ,stc.sysVer");
        sql.append("        ,stc.gradeCode");
        sql.append("        ,stc.classHardId");
        sql.append("        ,stc.subjectCode");
        sql.append("        ,stc.studyYearCode");
        sql.append("        ,stc.startTime");
        sql.append("        ,stc.endTime");
        sql.append("        ,scg.name as gradeName");
        sql.append("        ,scs.name as subjectName");
        sql.append("        ,clSubject.teacherName ");
        sql.append("        ,scyt.yearTermName ");
        sql.append("        ,clSubject.teacherCode ");
        sql.append("        ,clSubject.gid as teachingClassSubjectGid ");
        sql.append("        ,(select");
        sql.append("            count(1) ");
        sql.append("        FROM");
        sql.append("            share_teaching_class_student AS stcs ");
        sql.append("        WHERE");
        sql.append("            stcs.teachingClassCode=stc.teachingClassCode ) as studentCount  ");
        sql.append("    FROM");
        sql.append("        share_teaching_class AS stc   ");
        sql.append("   inner JOIN   ");
        sql.append(" share_teaching_class_subject clSubject ");
        sql.append(" on clSubject.teachingClassCode=stc.teachingClassCode and  clSubject.orgCode=stc.orgCode ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            on stc.gradeCode= scg.code  ");
        sql.append(" inner join share_admin_class sac ");
        sql.append(" on sac.classCode=stc.adminClassCode ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org_year_term soyt ");
        sql.append("            on soyt.orgCode=stc.orgCode and soyt.studyYearCode= stc.studyYearCode ");
        sql.append("     and soyt.termCode=clSubject.termCode       ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_year_term scyt ");
        sql.append("            on scyt.yearTermCode= soyt.yearTermCode  ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            on stc.subjectCode=scs.code  ");
        sql.append("    inner JOIN");
        sql.append("        share_user_teacher sut ");
        sql.append("            on sut.teacherCode=clSubject.teacherCode and sut.status=1  ");
        sql.append("    WHERE");
        sql.append("        1=1 ");
        if (StringUtils.isNotEmpty(studyYearCode)) {
            sql.append(" and stc.studyYearCode = :studyYearCode ");
            sqlParam.put("studyYearCode", studyYearCode);
        }
        if (StringUtils.isNotEmpty(teachingClassName)) {
            sql.append(" and stc.teachingClassName like :teachingClassName");
            sqlParam.put("teachingClassName", "%" + teachingClassName + "%");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and stc.subjectCode =:subjectCode");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and stc.gradeCode =:gradeCode");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and stc.subjectCode like :sectionCodeSta");
            sql.append(" and stc.gradeCode like :sectionCodeSta");
            sqlParam.put("sectionCodeSta", sectionCode + "%");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append(" and clSubject.teacherName like :teacherName");
            sqlParam.put("teacherName", "%" + teacherName + "%");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and stc.orgCode =:orgCode");
            sqlParam.put("orgCode", orgCode);
        }
        sql.append("  order by stc.teachingClassName ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 查询教学班级 ()<br>
     * 
     * @param gid
     * @return
     */
    public ShareTeachingClass getClass(String gid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("gid", gid);
        return super.findUnique(qr);
    }

    /**
     * 通过编码查班级 ()<br>
     * 
     * @param classCode
     * @return
     */
    public ShareTeachingClass getClassByCode(String classCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("teachingClassCode", classCode);
        return super.findUnique(qr);
    }

    /**
     * 查询课程班级列表 ()<br>
     * 
     * @param className
     * @param subjectCode
     * @param gradeCode
     * @param orgCode
     * @param gid
     * @return
     */
    public List<ShareTeachingClass> getClass(String adminClassCode, String subjectCode, String gradeCode,
                    String orgCode, String gid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("subjectCode", subjectCode);
        qr.andEqual("adminClassCode", adminClassCode);
        qr.andEqual("gradeCode", gradeCode);
        if (StringUtils.isNotEmpty(gid)) {
            qr.andNotEqual("gid", gid);
        }
        return super.find(qr);
    }

    /**
     * 新增教学班级 ()<br>
     * 
     * @param teachingClassList
     * @return
     */
    public int saveShareTeachingClassList(List<ShareTeachingClass> teachingClassList) {
        try {
            return super.saveAll(teachingClassList);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 
     * (根据相关条件查询行政班级下的教学班级)<br>
     * 
     * @param orgCode 机构编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param studyYearCode 学年编码
     * @param adminClassCode 行政班级编码
     * @return 教学班级信息
     */
    @Override
    public List<ShareTeachingClass> selectListByAdminClassCode(String orgCode, String subjectCode, String gradeCode,
                    String studyYearCode, String adminClassCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        qr.andEqual("subjectCode", subjectCode);
        qr.andEqual("gradeCode", gradeCode);
        qr.andEqual("studyYearCode", studyYearCode);
        qr.andEqual("adminClassCode", adminClassCode);
        return super.find(qr);
    }

    /**
     * 
     * (查询教学班级信息)<br>
     * 
     * @param orgCode 机构编码
     * @param teachingClassCode 教学班级编码
     * @return
     */
    @Override
    public ShareTeachingClass selectByTeachingClassCode(String orgCode, String teachingClassCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        qr.andEqual("teachingClassCode", teachingClassCode);
        return super.findUnique(qr);
    }

    /**
     * 更新教学班级名称 ()<br>
     * 
     * @param adminClassCode 行政班级编码
     * @param className 教学班级名称
     * @return 更新记录数
     */
    public int updateShareTeacherClass(String adminClassCode, String className) {
        String sql = "update share_teaching_class set teachingClassName=:className where adminClassCode=:adminClassCode";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("className", className);
        param.put("adminClassCode", adminClassCode);
        return super.update(sql, param);
    }

    /**
     * 删除教学班级 ()<br>
     * 
     * @param teachingClassCode
     * @return
     */
    public int delete(String teachingClassCode) {
        String sql = "delete from share_teaching_class where teachingClassCode=? ";
        return super.update(sql, teachingClassCode);
    }
    /**
     * 根据行政班级编码删除教学班级
     * ()<br>
     * @param adminClassCode
     */
    public void deleteByAdminClassCode(String adminClassCode){
        String sql="delete from share_teaching_class where  adminClassCode=?";
        this.update(sql, adminClassCode);
    }
}
