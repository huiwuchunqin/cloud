package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareTeachingClass;

/**
 * 教学班级班级dao接口 ShareTeachingClassDao
 * 
 * @author creator BZT 2016年6月7日 上午10:23:15
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareTeachingClassDao {
    /**
     * 保存行政班级 ()<br>
     * 
     * @param shareTeachingClass
     * @return
     */
    boolean saveShareTeachingClass(ShareTeachingClass shareTeachingClass);

    /**
     * 查询课程班级列表 ()<br>
     * 
     * @param param
     * @return
     */
    Page getList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询教学班级 ()<br>
     * 
     * @param gid
     * @return
     */
    ShareTeachingClass getClass(String gid);

    /**
     * 通过编码查班级 ()<br>
     * 
     * @param classCode
     * @return
     */
    ShareTeachingClass getClassByCode(String classCode);

    /**
     * 查询课程班级是否存在 ()<br>
     * 
     * @param className
     * @param subjectCode
     * @param gradeCode
     * @param orgCode
     * @param gid
     * @return
     */
    List<ShareTeachingClass> getClass(String adminClassCode, String subjectCode, String gradeCode, String orgCode,
                    String gid);

    /**
     * 新增教学班级 ()<br>
     * 
     * @param teachingClassList
     * @return
     */
    int saveShareTeachingClassList(List<ShareTeachingClass> teachingClassList);

    /**
     * 
     * (根据相关条件查询行政班级下的教学班级)<br>
     * 
     * @param orgCode 机构编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param studyYearCode 学年编码
     * @param adminClassCode 行政班级编码
     * @return 教学班级信息
     */
    List<ShareTeachingClass> selectListByAdminClassCode(String orgCode, String subjectCode, String gradeCode,
                    String studyYearCode, String adminClassCode);

    /**
     * 
     * (查询教学班级信息)<br>
     * 
     * @param orgCode 机构编码
     * @param teachingClassCode 教学班级编码
     * @return
     */
    ShareTeachingClass selectByTeachingClassCode(String orgCode, String teachingClassCode);

    /**
     * 更新教学班级名称 ()<br>
     * 
     * @param adminClassCode 行政班级编码
     * @param className 教学班级名称
     */
    int updateShareTeacherClass(String adminClassCode, String className);

    /**
     * 删除教学班级 ()<br>
     * 
     * @param teachingClassCode
     * @return
     */
    public int delete(String teachingClassCode);
    /**
     * 根据行政班级编码删除教学班级
     * ()<br>
     * @param adminClassCode
     */
    public void deleteByAdminClassCode(String adminClassCode);

}
