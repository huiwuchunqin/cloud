package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareAdminClassStudent;

public interface ShareAdminClassStudentDao {
    /**
     * 新增行政班级学生 ()<br>
     * 
     * @param adminClassStudent
     * @return
     */
    boolean saveOne(ShareAdminClassStudent adminClassStudent);

    /**
     * 删除行政班级学生 ()<br>
     * 
     * @param adminClassCode
     * @return
     */
    int deleteAdminClassStudent(String adminClassCode);

    /**
     * 删除行政班级学生关系 ()<br>
     * 
     * @param userCode 学生编号
     * @return
     */
    int deleteShareAdminClassStudent(String userCode);

    /**
     * 行政班级学生 ()<br>
     * 
     * @param adminClass
     * @param userCode
     * @return
     */
    public int addAdminClassStudent(ShareAdminClass adminClass, String userCode, String userName);

    /**
     * 
     * (更新行政班级学生年级信息)<br>
     * 
     * @param gradeCode 年级编码
     * @param classCode 行政班级编吗
     * @return 操作影响条数
     */
    int updateGradeInfo(String gradeCode, String classCode);

    /**
     * 根据学生guid更新行政班级表 ()<br>
     * 
     * @param stu_guid
     * @param adminClassCode
     * @param gradeCode
     * @param modifyPgm
     * @return
     */
    int updateShareAdminClassStudentByStuGuid(String stu_guid, String userName, String studentCode,
                    String adminClassCode, String gradeCode, String modifyPgm);

    /**
     * 
     * 根据学生guid新增行政班级学生
     * 
     * @param shareAdminClassStudent
     */
    void saveShareAdminClassStudent(ShareAdminClassStudent shareAdminClassStudent);

    /**
     * 批量保存 ()<br>
     * 
     * @param adminClassStudent
     * @return
     */
    int saveList(List<ShareAdminClassStudent> adminClassStudent);

    /**
     * 查询行政班级学生 ()<br>
     * 
     * @param classCode
     * @return
     */
    List<Map<String, Object>> getAdminClassStudent(String classCode);
}
