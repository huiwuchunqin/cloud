package com.baizhitong.resource.dao.share;

import java.util.Map;

import org.apache.activemq.util.StringArrayConverter;
import org.apache.poi.hssf.record.formula.DeletedArea3DPtg;
import org.springframework.ui.context.support.DelegatingThemeSource;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareTeachingClass;
import com.baizhitong.resource.model.share.ShareTeachingClassStudent;

/**
 * 教学班级学生dao接口 ShareTeachingClassStudentDao TODO
 * 
 * @author creator BZT 2016年6月7日 上午10:23:31
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareTeachingClassStudentDao {
    /**
     * 新增行政课程学生 ()<br>
     * 
     * @param shareTeachingClassStudent
     * @return
     */
    int addShareTeachingClassStudent(ShareTeachingClass teachingClass);

    /**
     * 更新程序新增课程班级学生 ()<br>
     * 
     * @param teachingClass
     * @return
     */
    int addShareTeachingClassStudentDataSynchronize(ShareTeachingClass teachingClass);

    /**
     * 查询课程班级学生信息 ()<br>
     * 
     * @param param
     * @return
     */
    Page getTeachingClassStudent(Map<String, Object> param, Integer page, Integer rows);

    int addTeachingClassStudent(ShareTeachingClass teachingClass, String userCode, String userName);

    int deleteStudent(String userCode, String teachingClassCode);

    /**
     * 
     * 删除教学班级学生
     * 
     * @param userCode 学生编码
     */
    void deleteShareTeachingClassStudent(String userCode);

    /**
     * 
     * 把行政班级学生插入教学班级学生表
     * 
     * @param userCode 学生编码
     * @param userName 学生名称
     * @param adminClassCode 行政班级编码
     */
    void insertTeachingClassStudent(String userCode, String userName, String adminClassCode, String ip);

    /**
     * 删除所有学生 ()<br>
     * 
     * @param teachingClassCode
     * @return
     */
    public int deleteAllStudent(String teachingClassCode);
    /**
     * 删除教学班级学生
     * ()<br>
     * @param adminClassCode
     */
    public void deleteByAdminClass(String adminClassCode);
}
