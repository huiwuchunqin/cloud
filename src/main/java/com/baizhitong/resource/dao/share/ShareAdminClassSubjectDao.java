package com.baizhitong.resource.dao.share;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.DeletedArea3DPtg;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;

public interface ShareAdminClassSubjectDao {
    /**
     * 保存班级学科 ()<br>
     * 
     * @param classSubject
     * @return
     */
    boolean save(ShareAdminClassSubject classSubject);

    /**
     * 查询行政班级学科 ()<br>
     * 
     * @param gid
     */
    ShareAdminClassSubject getByGid(String gid);

    /**
     * 查询班级学科老师信息 ()<br>
     * 
     * @param teacherCode
     * @param adminClassCode
     * @param subjectCode
     * @return
     */
    List<ShareAdminClassSubject> getAdminClassSubjectList(String teacherCode, String adminClassCode, String subjectCode,
                    String gradeCode);

    /**
     * 查询行政班级学科 ()<br>
     * 
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page getAdminClassSubjectList(Map<String, Object> param, Integer pageNo, Integer pageSize);

    /**
     * 删除行政班级学科 ()<br>
     * 
     * @param gid
     * @return
     */
    public int deleteAdminClassSubject(String gid);

    /**
     * 删除行政班级学科 ()<br>
     * 
     * @param adminClassCode
     * @return
     */
    public int deleteByAdminClassCode(String adminClassCode);

    /**
     * 查询所有的行政班级学科 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareAdminClassSubject> getList(String orgCode, String adminClassCode);

    /**
     * 查询所有行政班级学科 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareAdminClassSubject> getShareAdminClassList(String orgCode);

    /**
     * 保存行政班级学科 ()<br>
     * 
     * @param adminClassSubjct
     * @return
     */
    int saveShareAdminClassSubject(List<ShareAdminClassSubject> adminClassSubjct);

    /**
     * 
     * (保存原先的行政班级信息)<br>
     * 
     * @param classCode 行政班级编码
     * @return 操作影响条数
     */
    int insert(String classCode);

    /**
     * 把要修改的行政班级学科插入历史表 ()<br>
     * 
     * @param adminClassSubject
     */
    public void insertAdminClassSubjectToHistory(ShareAdminClassSubject adminClassSubject, String ip);

    /**
     * 
     * (更新行政班级学科信息)<br>
     * 
     * @param startDate 学期开始时间
     * @param endDate 学期结束时间
     * @param gradeCode 年级编码
     * @param classCode 班级编码
     * @param modifyTime 修改时间
     * @return 操作影响条数
     */
    int updateGradeInfo(Timestamp startDate, Timestamp endDate, String gradeCode, String classCode,
                    Timestamp modifyTime);

    /**
     * 更新行政班级学科表 ()<br>
     * 
     * @param adminClassCode 班级编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param teacherCode 老师编码
     * @param teacherName 老师名称
     * @param modifyPgm 修改程序
     * @return
     */
    int updateAdminClassSubject(String adminClassCode, String subjectCode, String gradeCode, String teacherCode,
                    String teacherName, String modifyPgm);

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
    List<Map<String, Object>> selectPageByAdminClassCode(String orgCode, String adminClassCode, Integer page,
                    Integer rows, String gradeCode);

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
    int updateTeacherInfo(String orgCode, String subjectCode, String gradeCode, String teacherCode, String teacherName,
                    String adminClassCode, String ipAddress, Timestamp beginTime, Timestamp endTime,
                    String oldTeacherCode);

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
    List<ShareAdminClassSubject> select(String orgCode, String adminClassCode, String subjectCode, String gradeCode,
                    String teacherCode);

    /**
     * 删除老师行政班级学科 ()<br>
     * 
     * @param teacherCode
     */
    void deleteTeacherSubject(String teacherCode);

}
