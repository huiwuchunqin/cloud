package com.baizhitong.resource.dao.basic;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.basic.ShareAdminClass;

/**
 * 行政班级dao接口 ShareAdminClassDao
 * 
 * @author creator gaow 2016年1月11日 下午6:49:51
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareAdminClassDao {

    /**
     * 查询行政班级列表 @param companyCode 机构编码 @param className 班级名称 @param gradeCode 年级编码 @param pageNo
     * 第几页 @param pageSize 每页记录数 @return
     * 
     * @exception
     */
    Page<Map<String, Object>> getClassList(String companyCode, String className, String gradeCode, String sectionCode,
                    String teacherName, Integer pageNo, Integer pageSize);

    /**
     * 新增或更新行政班级 ()<br>
     * 
     * @param adminClass
     * @return
     */
    boolean addOrUpdate(ShareAdminClass adminClass);

    /**
     * 查询行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    ShareAdminClass getAdminClass(String gid);

    /**
     * 查询行政班级 ()<br>
     * 
     * @param adminClassCode 行政班级编码
     * @return
     */
    ShareAdminClass getAdminClassByAdminClassCode(String adminClassCode);

    /**
     * 删除行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    int deleteAdminClass(String gid);

    /**
     * 查询行政班级名称 ()<br>
     * 
     * @return
     */
    List<ShareAdminClass> getAdminClassList(String orgCode);

    /**
     * 查询未毕业行政班级信息 ()<br>
     * 
     * @return
     */
    List<ShareAdminClass> getAdminClassNotGraduatedList(String orgCode);

    /**
     * 查询机构学年班级最大班级编号 ()<br>
     * 
     * @param orgCode
     * @param entryYear
     * @return
     */
    String getMax(String orgCode, Integer entryYear);

    /**
     * 查询行政班级列表 ()<br>
     * 
     * @param orgCode 机构编码
     * @param gradeCode 年级编码
     * @return
     */
    List<ShareAdminClass> getAdminClassList(String orgCode, String gradeCode);

    /**
     * 查询同名的行政班级 ()<br>
     * 
     * @param className
     * @param orgCode
     * @param gid
     * @return
     */
    List<ShareAdminClass> getSameName(Integer entryYear, String className, String orgCode, String gid);

    /**
     * 保存)<br>
     * 
     * @param adminClassList 行政班级列表
     */
    int addShareAdminClass(List<ShareAdminClass> adminClassList);

    /**
     * 
     * (更新班级状态)<br>
     * 
     * @param classCode 行政班级编码
     * @return 操作影响行数
     */
    int updateStatus(String classCode);

    /**
     * 
     * (更新年级信息)<br>
     * 
     * @param classCode 行政班级编码
     * @return 操作影响条数
     */
    int updateGradeInfo(String gradeCode, String className, String classCode, Timestamp modifyTime);

    /**
     * 根据guid更新行政班级 ()<br>
     * 
     * @param gradeCode 年级
     * @param className 班级名称
     * @param entryYear 入学年份
     * @param headTeacherCode 班主任编码
     * @param guid
     * @param ip
     */
    public int updateAdminClassByGUid(String headTeacherCode, String gradeCode, String className, Integer entryYear,
                    String guid, String ip);

    /**
     * 
     * (根据相关条件查询行政班级信息)<br>
     * 
     * @param orgCode 机构编码
     * @param gradeCode 年级编码
     * @param classCode 行政班级编码
     * @return
     */
    ShareAdminClass selectByClassCode(String orgCode, String gradeCode, String classCode);

    public ShareAdminClass getByClassCode(String adminClassCode);
    /**
     * 查询有任课信息却没有教学班级的行政班级<br>
     * 
     * @param orgCode 机构编码
     * @return
     */
    public List<Map<String, Object>> getClassHavingNoTClass(String orgCode);

}
