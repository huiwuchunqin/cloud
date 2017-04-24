package com.baizhitong.resource.dao.basic.sqlServer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.utils.RowMapperUtils;

@Component
public class ShareAdminClassDaoImpl extends BaseSqlServerDao<ShareAdminClass> implements ShareAdminClassDao {

    /**
     * 查询行政班级列表
     * 
     * @param className 班级名称 @param gradeCode 年级编码 @param pageNo 第几页 @param pageSize 每页记录数 @return
     * 
     * @exception
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getClassList(String companyCode, String className, String gradeCode,
                    String sectionCode, String teacherName, Integer pageNo, Integer pageSize) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql语句
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(
                        "sac.gid,  sac.orgCode,  sac.gradeCode,  sac.classCode,  sac.className,  sac.entryYear,  sac.classStatus,  sac.modifyPgm,  sac.modifyTime,  sac.modifyIP,  sac.sysVer,  sac.cd_guid,  sac.classHardId ");

        sqlBuffer.append(" ,scg.name AS gradeName,sut.userName as teacherName, ");
        sqlBuffer.append(
                        " (select count(0) from share_user_student sut where sut.adminClassCode=sac.classCode and sut.status=1 ) as studentCount ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" share_admin_class sac ");
        sqlBuffer.append(" LEFT JOIN share_code_grade scg ON sac.gradeCode = scg.code ");
        sqlBuffer.append(
                        " LEFT JOIN share_user_teacher sut ON sac.headTeacherCode = sut.teacherCode  and sut.status=1 ");
        sqlBuffer.append(" WHERE sac.classStatus = :classStatus ");
        if (StringUtils.isNotEmpty(className)) {
            sqlBuffer.append(" AND sac.className like:className ");
            sqlParam.put("className", "%" + className + "%");
        }
        if (StringUtils.isNotEmpty(companyCode)) {
            sqlBuffer.append(" AND sac.orgCode=:orgCode ");
            sqlParam.put("orgCode", companyCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sqlBuffer.append(" AND sac.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sqlBuffer.append(" and sut.userName like '%" + teacherName.trim() + "%'");
        }
        sqlParam.put("classStatus", CoreConstants.CLASS_STATUS__NOT_GRADUATE);
        if (StringUtils.isNotBlank(sectionCode)) {
            sqlBuffer.append(" AND sac.gradeCode like :sectionCodeStar");
            sqlParam.put("sectionCodeStar", sectionCode + "%");
        }
        sqlBuffer.append("order by sac.gradeCode,sac.className ");
        return super.queryDistinctPage(sqlBuffer.toString(), new RowMapperUtils(), sqlParam, pageNo, pageSize);
    }

    /**
     * 新增或更新行政班级 ()<br>
     * 
     * @param adminClass
     * @return
     */
    public boolean addOrUpdate(ShareAdminClass adminClass) {
        try {
            return super.saveOne(adminClass);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    /**
     * 查询行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    public ShareAdminClass getAdminClass(String gid) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(gid)) {
            qr.andEqual("gid", gid);
            return super.findUnique(qr);
        } else {
            return null;
        }

    }

    /**
     * 删除行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    public int deleteAdminClass(String gid) {
        String sql = "delete from share_admin_class where gid=?";
        return super.update(sql, gid);
    }

    /**
     * 查询行政班级名称 ()<br>
     * 
     * @return
     */
    public List<ShareAdminClass> getAdminClassList(String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        qr.andEqual("classStatus", CoreConstants.CLASS_STATUS__NOT_GRADUATE);
        qr.addAscOrder("gradeCode");
        qr.addAscOrder("className");
        return super.find(qr);
    }

    /**
     * 查询机构学年班级最大班级编号 ()<br>
     * 
     * @param orgCode
     * @param entryYear
     * @return
     */
    public String getMax(String orgCode, Integer entryYear) {
        QueryRule qr = QueryRule.getInstance();
        qr.andLike("orgCode", "%" + orgCode + "%");
        if (entryYear != null) {
            qr.andEqual("entryYear", entryYear);
        }
        qr.andLike("classCode", "BJ%");
        qr.addDescOrder("classCode");
        List<ShareAdminClass> adminClassList = super.find(qr);
        List<String> list = new ArrayList<String>();
        if (adminClassList != null && adminClassList.size() > 0) {
            for (ShareAdminClass adminClass : adminClassList) {
                String adminClassCode = adminClass.getClassCode();
                list.add(adminClassCode.substring(adminClassCode.length() - 2));
            }
            Collections.sort(list, new Comparator<String>() {
                public int compare(String code1, String code2) {
                    return (Integer.parseInt(code1) > Integer.parseInt(code2)) ? -1 : 1;
                }
            });

            return list.get(0);
        } else {
            return "01";
        }
    }

    @Test
    public void compare() {
        int i = (Integer.parseInt("3") > Integer.parseInt("02")) ? 1 : -1;
    }

    /**
     * 根据班级名称查询行政班级 ()<br>
     * 
     * @param className
     * @param orgCode
     * @param gid
     * @return
     */
    public List<ShareAdminClass> getSameName(Integer entryYear, String className, String orgCode, String gid) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(gid)) {
            qr.andNotEqual("gid", gid);
        }
        qr.andEqual("entryYear", entryYear);
        qr.andEqual("className", className);
        qr.andEqual("orgCode", orgCode);
        return super.find(qr);
    }

    /**
     * 查询行政班级列表 ()<br>
     * 
     * @param orgCode 机构编码
     * @param gradeCode 年级编码
     * @return
     */
    public List<ShareAdminClass> getAdminClassList(String orgCode, String gradeCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(gradeCode)) {
            qr.andEqual("gradeCode", gradeCode);
        }
        qr.andEqual("classStatus", CoreConstants.CLASS_STATUS__NOT_GRADUATE);
        qr.andEqual("orgCode", orgCode);
        qr.addAscOrder("gradeCode");
        qr.addAscOrder("className");
        return super.find(qr);
    }

    /**
     * 
     * 查询行政班级
     * 
     * @param adminClassCode 行政班级编码
     * @return
     */
    public ShareAdminClass getAdminClassByAdminClassCode(String adminClassCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("classCode", adminClassCode);
        return super.findUnique(qr);
    }

    /**
     * 保存或更新 ()<br>
     * 
     * @param adminClassList 行政班级列表
     */
    public int addShareAdminClass(List<ShareAdminClass> adminClassList) {
        try {
            return super.saveAll(adminClassList);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<ShareAdminClass> getAdminClassNotGraduatedList(String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("classStatus", 10);
        qr.andEqual("orgCode", orgCode);
        return super.find(qr);
    }

    @Override
    public int updateStatus(String classCode) {
        String sql = "update share_admin_class set classStatus = 20 where classCode=?";
        return super.update(sql, classCode);
    }

    @Override
    public int updateGradeInfo(String gradeCode, String className, String classCode, Timestamp modifyTime) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql语句
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        share_admin_class ");
        sql.append("    SET");
        sql.append("        gradeCode = :gradeCode");
        sql.append("        ,className = :className ");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        classCode = :classCode");
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("className", className);
        sqlParam.put("classCode", classCode);
        sqlParam.put("modifyTime", modifyTime);

        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 根据guid更新行政班级 ()<br>
     * 
     * @param gradeCode 年级编码
     * @param className 班级名称
     * @param guid 班级唯一码
     * @param entryYear 入学年份
     * @param ip ip地址
     */
    public int updateAdminClassByGUid(String headTeacherCode, String gradeCode, String className, Integer entryYear,
                    String guid, String ip) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql语句
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        share_admin_class ");
        sql.append("    SET");
        sql.append("        gradeCode = :gradeCode");
        sql.append("        ,className = :className ");
        sql.append("        ,entryYear = :entryYear ");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("        ,headTeacherCode = :headTeacherCode ");
        sql.append("        ,modifyIp = :modifyIP ");
        sql.append("        ,modifyPgm = :modifyPgm ");
        sql.append("    WHERE");
        sql.append("        cd_guid = :cd_guid");
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("headTeacherCode", headTeacherCode);
        sqlParam.put("className", className);
        sqlParam.put("entryYear", entryYear);
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("modifyIP", ip);
        sqlParam.put("modifyPgm", "SynchronizeServiceImplEdit");
        sqlParam.put("cd_guid", guid);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (根据相关条件查询行政班级信息)<br>
     * 
     * @param orgCode 机构编码
     * @param gradeCode 年级编码
     * @param classCode 行政班级编码
     * @return
     */
    @Override
    public ShareAdminClass selectByClassCode(String orgCode, String gradeCode, String classCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        qr.andEqual("gradeCode", gradeCode);
        qr.andEqual("classCode", classCode);
        qr.andEqual("classStatus", CoreConstants.CLASS_STATUS__NOT_GRADUATE);
        return super.findUnique(qr);
    }

    public ShareAdminClass getByClassCode(String adminClassCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("classCode", adminClassCode);
        return super.findUnique(qr);
    }

    /**
     * 查询有任课信息却没有教学班级的行政班级<br>
     * 
     * @param orgCode 机构编码
     * @return
     */
    public List<Map<String, Object>> getClassHavingNoTClass(String orgCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        c.* ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            a.gradeCode");
        sql.append("            ,a.className");
        sql.append("            ,a.entryYear");
        sql.append("            ,a.classCode");
        sql.append("            ,a.gid");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (1) ");
        sql.append("            FROM");
        sql.append("                share_teaching_class c ");
        sql.append("            WHERE");
        sql.append("                c.adminClassCode = a.classCode ");
        sql.append("                AND c.gradeCode = a.gradeCode ) AS tClassCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (1) ");
        sql.append("            FROM");
        sql.append("                share_admin_class_subject b ");
        sql.append("            WHERE");
        sql.append("                b.adminClassCode = a.classCode ");
        sql.append("                AND b.gradeCode = a.gradeCode ) AS classSubjectCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (1) ");
        sql.append("            FROM");
        sql.append("                share_user_student ");
        sql.append("            WHERE");
        sql.append("                adminClassCode = a.classCode ) AS studentCount ");
        sql.append("        FROM");
        sql.append("            share_admin_class a ");
        sql.append("        WHERE");
        sql.append("            a.orgCode =:orgCode ");
        sql.append("        ) c ");
        sql.append("    WHERE");
        sql.append("        c.tClassCount = 0 ");
        sql.append("        AND classSubjectCount > 0");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgCode", orgCode);
        return super.findBySql(sql.toString(), param);
    }

}
