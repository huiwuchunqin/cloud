package com.baizhitong.resource.dao.share.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareAdminClassSubjectDao;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;
import com.baizhitong.utils.DateUtils;

@Component
public class ShareAdminClassSubjectDaoImpl extends BaseSqlServerDao<ShareAdminClassSubject>
                implements ShareAdminClassSubjectDao {

    /**
     * 保存班级学科 ()<br>
     * 
     * @param classSubject
     * @return
     */
    public boolean save(ShareAdminClassSubject classSubject) {
        try {
            return super.saveOne(classSubject);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Page getAdminClassSubjectList(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        StringBuffer sql = new StringBuffer();
        String orgCode = MapUtils.getString(param, "orgCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String teacherCode = MapUtils.getString(param, "teacherCode");
        sql.append("SELECT");
        sql.append("        sac.className");
        sql.append("        ,scs.name as subjectName");
        sql.append("        ,scg.name as gradeName");
        sql.append("        ,ascs.teacherName");
        sql.append("        ,ascs.beginTime");
        sql.append("        ,ascs.endTime ");
        sql.append("        ,ascs.gid ");
        sql.append("    FROM");
        sql.append("        share_admin_class_subject ascs ");
        sql.append("        ");
        sql.append("    INNER  JOIN");
        sql.append("        share_admin_class sac ");
        sql.append("            ON ascs.adminClassCode = sac.classCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            ON scs.code = ascs.subjectCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            on scg.code=ascs.gradeCode");
        sql.append("    inner JOIN");
        sql.append("        share_user_teacher sut on sut.teacherCode=ascs.teacherCode ");
        sql.append("          and sut.status=1 ");
        sql.append("           where 1=1 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and ascs.orgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(teacherCode)) {
            sql.append(" and ascs.teacherCode=:teacherCode");
            sqlParam.put("teacherCode", teacherCode);
        }
        sql.append(" order by ascs.teacherName,sac.className ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNo, pageSize);
    }

    /**
     * 查询行政班级学科 ()<br>
     * 
     * @param gid
     * @return
     */
    public ShareAdminClassSubject getByGid(String gid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("gid", gid);
        return super.findUnique(qr);
    }

    /**
     * 删除行政班级学科 ()<br>
     * 
     * @param gid
     * @return
     */
    public int deleteAdminClassSubject(String gid) {
        String sql = "delete from share_admin_class_subject where gid=? ";
        return super.update(sql, gid);
    }

    /**
     * 删除行政班级学科 ()<br>
     * 
     * @param adminClassCode
     * @return
     */
    public int deleteByAdminClassCode(String adminClassCode) {
        String sql = "delete from share_admin_class_subject where adminClassCode=?";
        return super.update(sql, adminClassCode);
    }

    /**
     * 查询班级学科老师信息 ()<br>
     * 
     * @param teacherCode
     * @param adminClassCode
     * @param subjectCode
     * @return
     */
    public List<ShareAdminClassSubject> getAdminClassSubjectList(String teacherCode, String adminClassCode,
                    String subjectCode, String gradeCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(teacherCode)) {
            qr.andEqual("teacherCode", teacherCode);
        }
        qr.andEqual("adminClassCode", adminClassCode);
        qr.andEqual("subjectCode", subjectCode);
        qr.andEqual("gradeCode", gradeCode);
        return super.find(qr);

    }

    /**
     * 查询所有的行政班级学科 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareAdminClassSubject> getList(String orgCode, String adminClassCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        if (StringUtils.isNotEmpty(adminClassCode)) {
            qr.andEqual("adminClassCode", adminClassCode);
        }
        return super.find(qr);
    }

    /**
     * 查询所有行政班级学科 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareAdminClassSubject> getShareAdminClassList(String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        return super.find(qr);
    }

    /**
     * 保存行政班级学科 ()<br>
     * 
     * @param adminClassSubjct
     * @return
     */
    public int saveShareAdminClassSubject(List<ShareAdminClassSubject> adminClassSubject) {
        try {
            return super.saveAll(adminClassSubject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 0;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int insert(String classCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_admin_class_subject_history");
        sql.append("        (originalGid, orgCode, subjectCode, textbookVerCode, gradeCode, adminClassCode, teacherCode, teacherName, teacherRole, beginTime, endTime, memo, modifyPgm, modifyTime, modifyIP) SELECT");
        sql.append("            gid AS originalGid");
        sql.append("            ,orgCode");
        sql.append("            ,subjectCode");
        sql.append("            ,textbookVerCode");
        sql.append("            ,gradeCode");
        sql.append("            ,adminClassCode");
        sql.append("            ,teacherCode");
        sql.append("            ,teacherName");
        sql.append("            ,teacherRole");
        sql.append("            ,beginTime");
        sql.append("            ,endTime");
        sql.append("            ,memo");
        sql.append("            ,modifyPgm");
        sql.append("            ,modifyTime");
        sql.append("            ,modifyIP ");
        sql.append("        FROM");
        sql.append("            share_admin_class_subject ");
        sql.append("        WHERE");
        sql.append("            adminclassCode = :adminclassCode");
        param.put("adminclassCode", classCode);
        return super.update(sql.toString(), param);
    }

    /**
     * 把要修改的行政班级学科插入历史表 ()<br>
     * 
     * @param adminClassSubject
     */
    public void insertAdminClassSubjectToHistory(ShareAdminClassSubject adminClassSubject, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_admin_class_subject_history");
        sql.append("        ( originalGid, orgCode, subjectCode, textbookVerCode, gradeCode, adminClassCode, teacherCode, teacherName, teacherRole, beginTime, endTime, memo, modifyPgm, modifyTime, modifyIP, sysVer)");
        sql.append("    VALUES");
        sql.append("        ( :originalGid, :orgCode, :subjectCode, :textbookVerCode, :gradeCode, :adminClassCode, :teacherCode, :teacherName, :teacherRole, :beginTime, :endTime, '', :modifyPgm, :modifyTime, :modifyIP, :sysVer)");
        sqlParam.put("originalGid", adminClassSubject.getGid());
        sqlParam.put("orgCode", adminClassSubject.getOrgCode());
        sqlParam.put("subjectCode", adminClassSubject.getSubjectCode());
        sqlParam.put("textbookVerCode", adminClassSubject.getTextbookVerCode());
        sqlParam.put("gradeCode", adminClassSubject.getGradeCode());
        sqlParam.put("adminClassCode", adminClassSubject.getAdminClassCode());
        sqlParam.put("teacherCode", adminClassSubject.getTeacherCode());
        sqlParam.put("teacherName", adminClassSubject.getTeacherName());
        sqlParam.put("teacherRole", adminClassSubject.getTeacherRole());
        sqlParam.put("beginTime", adminClassSubject.getBeginTime());
        sqlParam.put("endTime", adminClassSubject.getEndTime());
        sqlParam.put("modifyPgm", "SynchronizeServiceImpl");
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("modifyIP", ip);
        sqlParam.put("sysVer", adminClassSubject.getSysVer());
        super.update(sql.toString(), sqlParam);
    }

    @Override
    public int updateGradeInfo(Timestamp startDate, Timestamp endDate, String gradeCode, String classCode,
                    Timestamp modifyTime) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql语句
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        share_admin_class_subject ");
        sql.append("    SET");
        sql.append("        gradeCode = :gradeCode");
        sql.append("        ,beginTime = :beginTime");
        sql.append("        ,endTime = :endTime");
        sql.append("        ,modifyTime = :modifyTime");
        sql.append("    WHERE");
        sql.append("        adminClassCode = :adminClassCode");
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("adminClassCode", classCode);
        sqlParam.put("beginTime", startDate);
        sqlParam.put("endTime", endDate);
        sqlParam.put("modifyTime", modifyTime);

        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 更新行政班级学科表老师信息 ()<br>
     * 
     * @param adminClassCode 班级编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param teacherCode 老师编码
     * @param teacherName 老师名称
     * @param modifyPgm 修改程序
     * @return
     */
    public int updateAdminClassSubject(String adminClassCode, String subjectCode, String gradeCode, String teacherCode,
                    String teacherName, String modifyPgm) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_admin_class_subject  ");
        sql.append("    SET");
        sql.append("        teacherCode =:teacherCode");
        sql.append("        ,teacherName =:teacherName");
        sql.append("        ,modifyTime =:modifyTime");
        sql.append("        ,modifyPgm =:modifyPgm  ");
        sql.append("    WHERE");
        sql.append("        adminClassCode =:adminClassCode  ");
        sql.append("        AND gradeCode =:gradeCode  ");
        sql.append("        AND subjectCode =:subjectCode  ");
        sql.append("        AND teacherCode !=:teacherCode");
        sqlParam.put("teacherCode", teacherCode);
        sqlParam.put("teacherName", teacherName);
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("modifyPgm", modifyPgm);
        sqlParam.put("adminClassCode", adminClassCode);
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("subjectCode", subjectCode);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (根据行政班级查询行政班级学科信息)<br>
     * 
     * @param orgCode 机构编码
     * @param adminClassCode 行政班级编码
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> selectPageByAdminClassCode(String orgCode, String adminClassCode, Integer page,
                    Integer rows, String gradeCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        sacs.subjectCode");
        sql.append("        ,sacs.gid");
        sql.append("        ,sacs.orgCode");
        sql.append("        ,sacs.adminClassCode");
        sql.append("        ,sacs.teacherCode");
        sql.append("        ,sacs.teacherName");
        sql.append("        ,sacs.teacherRole");
        sql.append("        ,sacs.beginTime");
        sql.append("        ,sacs.endTime");
        sql.append("        ,sacs.modifyTime");
        sql.append("        ,sacs.gradeCode");
        sql.append("        ,scs.name AS subjectName ");
        sql.append("    FROM");
        sql.append("        share_admin_class_subject AS sacs ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject AS scs ");
        sql.append("            ON sacs.subjectCode = scs.code ");
        sql.append("    INNER JOIN");
        sql.append("        share_user_teacher sut ");
        sql.append("            ON sut.teacherCode = sacs.teacherCode and sut.status=1 ");
        sql.append("    WHERE");
        sql.append("        sacs.orgCode = :orgCode ");
        sql.append("        AND sacs.adminClassCode = :adminClassCode ");
        sql.append("        AND sacs.gradeCode = :gradeCode ");
        sql.append("    ORDER BY sacs.beginTime ASC ");
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("adminClassCode", adminClassCode);
        sqlParam.put("gradeCode", gradeCode);
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 
     * (更新教师信息)<br>
     * 
     * @param orgCode
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @param teacherName
     * @param adminClassCode
     * @param ipAddress
     * @return
     */
    @Override
    public int updateTeacherInfo(String orgCode, String subjectCode, String gradeCode, String teacherCode,
                    String teacherName, String adminClassCode, String ipAddress, Timestamp beginTime, Timestamp endTime,
                    String oldTeacherCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        share_admin_class_subject ");
        sql.append("    SET");
        sql.append("        teacherCode = :teacherCode");
        sql.append("        ,teacherName = :teacherName");
        sql.append("        ,modifyIP = :modifyIP");
        sql.append("        ,modifyPgm = 'updateTeacherInfo'");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("        ,sysVer=sysVer+1 ");// 版本号加1
        sql.append("        ,beginTime= :beginTime ");
        sql.append("        ,endTime= :endTime ");
        sql.append("    WHERE");
        sql.append("        orgCode = :orgCode ");
        sql.append("        AND subjectCode = :subjectCode ");
        sql.append("        AND gradeCode = :gradeCode ");
        sql.append("        AND adminClassCode = :adminClassCode ");
        sql.append("        AND teacherCode = :oldTeacherCode ");
        sqlParam.put("teacherCode", teacherCode);
        sqlParam.put("teacherName", teacherName);
        sqlParam.put("modifyIP", ipAddress);
        sqlParam.put("modifyTime", currentTime);
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("adminClassCode", adminClassCode);
        sqlParam.put("beginTime", beginTime);
        sqlParam.put("endTime", endTime);
        sqlParam.put("oldTeacherCode", oldTeacherCode);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (查询行政班级学科信息是否已存在)<br>
     * 
     * @param orgCode
     * @param adminClassCode
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @return
     */
    @Override
    public List<ShareAdminClassSubject> select(String orgCode, String adminClassCode, String subjectCode,
                    String gradeCode, String teacherCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        qr.andEqual("adminClassCode", adminClassCode);
        qr.andEqual("subjectCode", subjectCode);
        qr.andEqual("gradeCode", gradeCode);
        qr.andEqual("teacherCode", teacherCode);
        return super.find(qr);
    }

    /**
     * 删除老师行政班级学科 ()<br>
     * 
     * @param teacherCode
     */
    public void deleteTeacherSubject(String teacherCode) {
        String sql = "delete from share_admin_class_subject where teacherCode=?";
        super.update(sql, teacherCode);
    }
}
