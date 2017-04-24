package com.baizhitong.resource.manage.adminClass.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.basic.ShareAdminClass;

/**
 * 行政班级接口 IAdminClassService
 * 
 * @author creator gaow 2016年1月11日 下午7:03:39
 * @author updater
 *
 * @version 1.0.0
 */
public interface IAdminClassService {
    /**
     * 查询行政班级列表
     * 
     * @param className 行政班级名称 @param gradeCode 年级编码 @param pageNo 页码 @param pageSize 每页记录数 @param
     * teacherName 老师姓名 @return
     * 
     * @exception
     */
    public Page<Map<String, Object>> getAdminClassList(String className, String gradeCode, String sectionCode,
                    String teacherName, Integer pageNo, Integer pageSize);

    /**
     * 查询行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    public ShareAdminClass getAdminClass(String gid);

    /**
     * 新增或者更新行政班级 ()<br>
     * 
     * @param adminClass
     */
    public ResultCodeVo addOrUpdateAdminClass(ShareAdminClass adminClass);

    /**
     * 删除行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo deleteAdminClass(String gid);

    /**
     * 查询机构行政班级 ()<br>
     * 
     * @param orgCode
     * @return
     */
    List<ShareAdminClass> getList(String orgCode);

    /**
     * 查询行政班级 ()<br>
     * 
     * @param orgCode 机构编码
     * @param gradeCode 年级编码
     * @return
     */
    List<ShareAdminClass> getAdminClassList(String orgCode, String gradeCode);

    /**
     * 
     * (分页查询行政班级任教信息)<br>
     * 
     * @param orgCode 机构编码
     * @param adminClassCode 行政班级编码
     * @param page
     * @param rows
     * @return
     */
    List<Map<String, Object>> queryAdminClassTeachInfo(String orgCode, String adminClassCode, Integer page,
                    Integer rows, String gradeCode);

    /**
     * 
     * (修改教师信息)<br>
     * 
     * @param orgCode
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @param teacherName
     * @param adminClassCode
     * @return
     * @throws Exception
     */
    ResultCodeVo updateTeacherInfo(String orgCode, String subjectCode, String gradeCode, String teacherCode,
                    String teacherName, String adminClassCode, String beginTimeStr, String endTimeStr,
                    String oldTeacherCode) throws Exception;

    /**
     * 
     * (新增行政班级学科信息)<br>
     * 
     * @param orgCode
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @param teacherName
     * @param adminClassCode
     * @param beginTimeStr
     * @param endTimeStr
     * @return
     * @throws Exception
     */
    ResultCodeVo addAdminClassSubject(String orgCode, String subjectCode, String gradeCode, String teacherCode,
                    String teacherName, String adminClassCode, String beginTimeStr, String endTimeStr) throws Exception;

}
