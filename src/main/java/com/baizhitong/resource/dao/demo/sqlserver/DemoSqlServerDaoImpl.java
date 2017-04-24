package com.baizhitong.resource.dao.demo.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.demo.DemoDao;
import com.baizhitong.resource.model.demo.Demo;
import com.baizhitong.resource.model.vo.temp.StudentTempVo;
import com.baizhitong.resource.model.vo.temp.TeacherTempVo;
import com.baizhitong.resource.model.vo.temp.adminClassSubjectTempVo;
import com.baizhitong.resource.model.vo.temp.adminClassVo;

/**
 * 测试数据接口实现
 * 
 * @author creator cuidc 2015/12/01
 * @author updater
 */
@Service("demoSqlServerDao")
public class DemoSqlServerDaoImpl extends BaseMySqlDao<Demo, Integer> implements DemoDao {

    /**
     * 获取测试集合
     * 
     * @return 测试集合
     */
    @Override
    public List<Demo> select() {
        /*
         * Map<String, Object> m = new HashMap<String, Object>(); Page page =
         * super.queryDistinctPage("SELECT * FROM demo", super.op.rowMapper, m, 2, 2);
         * 
         * QueryRule queryRule = QueryRule.getInstance(); queryRule.andEqual("id", 3); Demo demo =
         * super.findUnique(queryRule);
         * 
         * super.deleteByPK(12);
         * 
         * demo.setUpdateTime(new Date()); try { super.update(demo); } catch
         * (IllegalArgumentException e) { e.printStackTrace(); } catch (IllegalAccessException e) {
         * e.printStackTrace(); }
         * 
         * Demo demo3 = new Demo(); demo3.setCode("0007"); demo3.setName("第七");
         * demo3.setUpdateTime(new Date()); try { Integer id = super.saveAndReturnId(demo3); int dd
         * = 0; } catch (IllegalArgumentException e) { e.printStackTrace(); } catch
         * (IllegalAccessException e) { e.printStackTrace(); }
         */

        List<Demo> list = super.getAll();
        return list;
    }

    @Override
    public void insertStudent(StudentTempVo student) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        student_temp");
        sql.append("        (    njmc,    bjdm,    xsdm,    xsxm,    xb,    zp,    tysfzh,    guid,    orgCode ,bjguid,csrq,dzyx,njdm,bjmc,datastatus,xxid  )   ");
        sql.append("    VALUES");
        sql.append("        (    :njmc,    :bjdm,    :xsdm,    :xsxm,    :xb,    :zp,    :tysfzh,    :guid,    :orgCode ,:bjguid ,:csrq,:dzyx,:njdm,:bjmc,:datastatus,:xxid    );");
        sqlParam.put("njmc", student.getNjmc());
        sqlParam.put("bjdm", student.getBjdm());
        sqlParam.put("xsdm", student.getXsdm());
        sqlParam.put("xsxm", student.getXsxm());
        sqlParam.put("xb", student.getXb());
        sqlParam.put("zp", student.getZp());
        sqlParam.put("tysfzh", student.getTysfzh());
        sqlParam.put("orgCode", student.getOrgCode());
        sqlParam.put("guid", student.getGuid());
        sqlParam.put("bjguid", student.getBjguid());
        sqlParam.put("csrq", student.getCsrq());
        sqlParam.put("dzyx", student.getDzyx());
        sqlParam.put("njdm", student.getNjdm());
        sqlParam.put("bjmc", student.getNjmc());
        sqlParam.put("datastatus", student.getDatastatus());
        sqlParam.put("xxid", student.getXxid());
        super.update(sql.toString(), sqlParam);
    }

    @Override
    public void insertTeacher(TeacherTempVo teacher) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        teacher_temp");
        sql.append("        (    lsid,    xm,    xbm,    xddm,    xkdm,    gh,    yddh,    dzyx,    zp,    yzh,    orgCode,datastatus,ssdw,ssdwdm  )   ");
        sql.append("    VALUES");
        sql.append("        (    :lsid,    :xm,    :xbm,    :xddm,    :xkdm,    :gh,    :yddh,    :dzyx,    :zp,    :yzh,    :orgCode,:datastatus,:ssdw,:ssdwdm    );");
        sqlParam.put("lsid", teacher.getLsid());
        sqlParam.put("xm", teacher.getXm());
        sqlParam.put("xbm", teacher.getXbm());
        sqlParam.put("xddm", teacher.getXddm());
        sqlParam.put("xkdm", teacher.getXkdm());
        sqlParam.put("gh", teacher.getGh());
        sqlParam.put("yddh", teacher.getYddh());
        sqlParam.put("dzyx", teacher.getDzyx());
        sqlParam.put("zp", teacher.getZp());
        sqlParam.put("yzh", teacher.getYzh());
        sqlParam.put("ssdw", teacher.getSsdw());
        sqlParam.put("ssdwdm", teacher.getSsdwdm());
        sqlParam.put("orgCode", teacher.getOrgCode());
        sqlParam.put("datastatus", teacher.getDataStatus());
        super.update(sql.toString(), sqlParam);

    }

    @Override
    public void insertAdminClass(adminClassVo adminClass) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        admin_class_temp");
        sql.append("        (guid, rxnf, bjmc, nj, orgCode,bjdm,jssj,bzrid)  ");
        sql.append("    VALUES");
        sql.append("       (:guid, :rxnf, :bjmc, :nj, :orgCode,:bjdm,:jssj,:bzrid)");
        sqlParam.put("guid", adminClass.getGuid());
        sqlParam.put("rxnf", adminClass.getRxnf());
        sqlParam.put("bjmc", adminClass.getBjmc());
        sqlParam.put("nj", adminClass.getNj());
        sqlParam.put("bjdm", adminClass.getBjdm());
        sqlParam.put("jssj", adminClass.getJssj());
        sqlParam.put("bzrid", adminClass.getBzrid());
        sqlParam.put("orgCode", adminClass.getOrgCode());
        super.update(sql.toString(), sqlParam);
    }

    @Override
    public void insertTeacherSubjectClass(adminClassSubjectTempVo adminClassSubject) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        admin_class_subject_temp");
        sql.append("        (lsid,teacherName,    bjguid,    classBeginTime,    classEndTime,    xkbm,orgCode   )   ");
        sql.append("    VALUES");
        sql.append("        (");
        sql.append("   :lsid,     ");
        sql.append("   :teacherName,     ");
        sql.append("   :bjguid,     ");
        sql.append("     :classBeginTime,   ");
        sql.append("  :classEndTime,     ");
        sql.append("  :xkbm ,     ");
        sql.append("  :orgCode )    ");
        sqlParam.put("lsid", adminClassSubject.getLsid());
        sqlParam.put("teacherName", adminClassSubject.getTeacherName());
        sqlParam.put("bjguid", adminClassSubject.getBjguid());
        sqlParam.put("classBeginTime", adminClassSubject.getSkksrq());
        sqlParam.put("classEndTime", adminClassSubject.getSkjsrq());
        sqlParam.put("xkbm", adminClassSubject.getXkbm());
        sqlParam.put("orgCode", adminClassSubject.getOrgCode());
        super.update(sql.toString(), sqlParam);
    }

    /**
     * 删除临时数据 ()<br>
     * 
     * @param orgCode
     */
    public void deleteAll(String orgCode) {
        String sql = "delete from admin_class_subject_temp where orgCode=:orgCode";
        String sql2 = "delete from admin_class_temp where orgCode=:orgCode";
        String sql3 = "delete from teacher_temp where orgCode=:orgCode";
        String sql4 = "delete from student_temp where orgCode=:orgCode";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", orgCode);
        super.update(sql4, sqlParam);
        super.update(sql2, sqlParam);
        super.update(sql, sqlParam);
        super.update(sql3, sqlParam);

    }

    /**
     * 更新机构更新批次表 ()<br>
     * 
     * @param orgGuid 机构唯一码
     * @param bacth 当前更新批次
     * @return
     */
    public int updateOrgUpdateBatch(String orgGuid, Integer batch) {
        String sql = "update org_update_batch set updateVer=:updateVer where orgGuid=:orgGuid";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgGuid", orgGuid);
        sqlParam.put("updateVer", batch);
        return super.update(sql, sqlParam);
    }

    /**
     * 查询机构当前更新批次 ()<br>
     * 
     * @param orgGuid 机构唯一码
     * @return
     */
    public Map<String, Object> getOrgUpdateBatch(String orgGuid) {
        String sql = "select updateVer from  org_update_batch where orgGuid=:orgGuid";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgGuid", orgGuid);
        return super.findUniqueBySql(sql, sqlParam);
    }

    /**
     * 给更新批次插入默认值 ()<br>
     * 
     * @param orgGuid 机构唯一码
     * @param batch 当前更新批次
     */
    public void insertInite(String orgGuid, Integer batch) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        org_update_batch");
        sql.append("        (orgGuid, updateVer)  ");
        sql.append("    VALUES");
        sql.append("        (:orgGuid, :updateVer);");
        sqlParam.put("orgGuid", orgGuid);
        sqlParam.put("updateVer", batch - 1);
        super.update(sql.toString(), sqlParam);
    }

    public void insertTest() {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        maindb.dbo.insertdemo");
        sql.append("        (    a,    b,    c,    d,    e,    f,    g   )   ");
        sql.append("    VALUES");
        sql.append("        (     1,     2,     3,     4,     5,     6,     7    );");
        Map<String, Object> map = new HashMap<String, Object>();

        super.update(sql.toString(), map);

    }
}