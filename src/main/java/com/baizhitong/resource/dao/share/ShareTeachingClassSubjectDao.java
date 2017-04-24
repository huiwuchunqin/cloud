package com.baizhitong.resource.dao.share;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.share.ShareTeachingClassSubject;

/**
 * 教学班级学科dao接口 ShareTeachingClassSubjectDao
 * 
 * @author creator BZT 2016年6月7日 上午10:24:01
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareTeachingClassSubjectDao {
    /**
     * 新增行政班级学科 ()<br>
     * 
     * @param shareTeachingClassSubject
     * @return
     */
    boolean add(ShareTeachingClassSubject shareTeachingClassSubject);

    /**
     * 查询课程班级学科 ()<br>
     * 
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @param orgCode
     * @param studyYearCode
     * @param termCode
     * @return
     */
    List<Map<String, Object>> getTeachingClassSubject(String teachingClassCode, String subjectCode, String gradeCode,
                    String teacherCode, String orgCode, String studyYearCode, String termCode);

    /**
     * 
     * (查询老师执教的年级和学科)<br>
     * 
     * @param teacherCode
     * @param orgCode
     * @author zhangqiang
     * @return
     */
    List<Map<String, Object>> selectGradeSubjectByTeacher(String teacherCode, String orgCode);

    /**
     * 保存课程班级学科 ()<br>
     * 
     * @param teachingClassList
     * @return
     */
    int saveTeacherClassSubject(List<ShareTeachingClassSubject> teachingClassList);

    /**
     * 更新教学班级学科的老师 ()<br>
     * 
     * @param classCode 班级编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param teacherCode 老师编码
     * @param teacherName 老师姓名
     * @param modifyPgm 修改程序
     * @return
     */
    public int updateClassTeacher(String classCode, String subjectCode, String gradeCode, String teacherCode,
                    String teacherName, String modifyPgm);

    /**
     * 通过gid查询课程班级学科 ()<br>
     * 
     * @param gid 主键
     * @return
     */
    public ShareTeachingClassSubject getTeachingClassSubjectByGid(String gid);

    /**
     * 
     * (更新教师信息)<br>
     * 
     * @param orgCode
     * @param subjectCode
     * @param gradeCode
     * @param teachingClassCodes
     * @param teacherCode
     * @param teacherName
     * @param studyYearCode
     * @param termCode
     * @param ipAddress
     * @return
     */
    public int updateTeacherInfo(String orgCode, String subjectCode, String gradeCode, String teachingClassCodes,
                    String teacherCode, String teacherName, String studyYearCode, String termCode, String ipAddress,
                    Timestamp beginTime, Timestamp endTime, String oldTeacherCode);

    /**
     * 删除任课信息 ()<br>
     * 
     * @param adminClassCode 行政班级编码
     * @param subjectCode 学科编码
     * @param teacherCode 老师编码
     * @return
     */
    public int delete(String adminClassCode, String subjectCode, String teacherCode);

    /**
     * 查询教学班级学科信息 ()<br>
     * 
     * @param teachingClassCode
     * @return
     */
    public long getClassSubjectCount(String teachingClassCode);

}
