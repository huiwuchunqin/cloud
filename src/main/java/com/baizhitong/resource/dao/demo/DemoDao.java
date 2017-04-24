package com.baizhitong.resource.dao.demo;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.DeletedArea3DPtg;

import com.baizhitong.resource.model.demo.Demo;
import com.baizhitong.resource.model.vo.temp.StudentTempVo;
import com.baizhitong.resource.model.vo.temp.TeacherTempVo;
import com.baizhitong.resource.model.vo.temp.adminClassSubjectTempVo;
import com.baizhitong.resource.model.vo.temp.adminClassVo;

/**
 * 测试数据接口
 * 
 * @author creator cuidc 2015/12/01
 * @author updater
 */
public interface DemoDao {
    /**
     * 获取测试集合
     * 
     * @return 测试集合
     */
    public List<Demo> select();

    /**
     * 插入学生临时数据 ()<br>
     * 
     * @param student
     */
    public void insertStudent(StudentTempVo student);

    /**
     * 插入老师临时数据 ()<br>
     * 
     * @param teacher
     */
    public void insertTeacher(TeacherTempVo teacher);

    /**
     * 插入行政班级临时数据 ()<br>
     * 
     * @param adminClass
     */
    public void insertAdminClass(adminClassVo adminClass);

    /**
     * 插入教学班级学科临时数据 ()<br>
     * 
     * @param adminClassSubject
     */
    public void insertTeacherSubjectClass(adminClassSubjectTempVo adminClassSubject);

    /**
     * 更新机构更新批次表 ()<br>
     * 
     * @param orgGuid 机构唯一码
     * @param batch 当前更新批次
     * @return
     */
    public int updateOrgUpdateBatch(String orgGuid, Integer batch);

    /**
     * 查询机构当前更新批次 ()<br>
     * 
     * @param orgGuid 机构唯一码
     * @return
     */
    public Map<String, Object> getOrgUpdateBatch(String orgGuid);

    /**
     * 给更新批次插入默认值 ()<br>
     * 
     * @param orgGuid 机构唯一码
     * @param batch 当前更新批次
     */
    public void insertInite(String orgGuid, Integer batch);

    /**
     * 删除临时数据 ()<br>
     * 
     * @param orgCode
     */
    public void deleteAll(String orgCode);

    public void insertTest();
}