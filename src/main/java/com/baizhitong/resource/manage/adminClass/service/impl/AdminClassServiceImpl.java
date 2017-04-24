package com.baizhitong.resource.manage.adminClass.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.PrimaryKeyHelper;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.dao.share.ShareAdminClassStudentDao;
import com.baizhitong.resource.dao.share.ShareAdminClassSubjectDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassStudentDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassSubjectDao;
import com.baizhitong.resource.dao.share.ShareUserStudentDao;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassService;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.ShareTeachingClass;
import com.baizhitong.resource.model.share.ShareTeachingClassSubject;
import com.baizhitong.resource.model.share.ShareUserTeacher;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 行政班级接口实现类 AdminClassServiceImpl
 * 
 * @author creator gaow 2016年1月11日 下午7:03:57
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class AdminClassServiceImpl extends BaseService implements IAdminClassService {

    // 行政班级dao
    @Autowired
    private ShareAdminClassDao           shareAdminClassDao;
    @Autowired
    private ITeacherService              teacherService;
    @Autowired
    private ShareAdminClassStudentDao    adminClassStudentDao;
    @Autowired
    private ShareUserStudentDao          shareUserStudentDao;
    @Autowired
    private ShareAdminClassSubjectDao    adminClassSubjectDao;
    @Autowired
    private SharePlatformDao             sharePlatformDao;
    @Autowired
    private ShareTeachingClassDao        shareTeachingClassDao;
    @Autowired
    private ShareTeachingClassSubjectDao shareTeachingClassSubjectDao;
    @Autowired
    private ShareTeachingClassStudentDao shareTeachingClassStudentDao;

    /**
     * 查询行政班级列表 @param className 班级名称 @param gradeCode 年级编码 @param pageNo 页码 @param pageSize
     * 每页记录数 @return
     * 
     * @exception
     */
    public Page<Map<String, Object>> getAdminClassList(String className, String gradeCode, String sectionCode,
                    String teacherName, Integer pageNo, Integer pageSize) {
       
        CompanyInfoVo company =  getCompanyInfo();
        return shareAdminClassDao.getClassList(company.getOrgCode(), className, gradeCode, sectionCode, teacherName,
                        pageNo, pageSize);
    }

    /**
     * 新增或者更新行政班级 ()<br>
     * 
     * @param adminClass
     */

    public ResultCodeVo addOrUpdateAdminClass(ShareAdminClass adminClass) {
       
        CompanyInfoVo companyInfoVo =  getCompanyInfo();

        // 同名验证
        List<ShareAdminClass> sameNameList = shareAdminClassDao.getSameName(adminClass.getEntryYear(),
                        adminClass.getClassName(), companyInfoVo.getOrgCode(), adminClass.getGid());
        if (sameNameList != null && sameNameList.size() > 0) {
            return ResultCodeVo.errorCode("存在同名的班级");
        }
        adminClass.setOrgCode(companyInfoVo.getOrgCode());
        adminClass.setClassStatus(CoreConstants.CLASS_STATUS__NOT_GRADUATE);
        adminClass.setModifyPgm("AdminClassService");
        adminClass.setModifyIP(getIp());
        adminClass.setModifyTime(new Timestamp(new Date().getTime()));
        adminClass.setSysVer(0);
        // 新增情况
        if (StringUtils.isEmpty(adminClass.getGid())) {
            // 根据所选的老师来确定是哪个学段的
            ShareUserTeacher teacher = teacherService.getTeacher(adminClass.getHeadTeacherCode());
            adminClass.setGid(UUID.randomUUID().toString());
            // 获取行政班级编码
            String maxCode = shareAdminClassDao.getMax(companyInfoVo.getOrgCode(), adminClass.getEntryYear());
            String adminClassCode = PrimaryKeyHelper.getAdminClassCode(companyInfoVo.getOrgCode(),
                            teacher.getSectionCode(), adminClass.getEntryYear(), Integer.parseInt(maxCode));
            adminClass.setClassCode(adminClassCode);
            boolean success = shareAdminClassDao.addOrUpdate(adminClass);
            if (success) {
                return ResultCodeVo.rightCode("保存成功", adminClassCode);
            } else {
                return ResultCodeVo.errorCode("保存失败");
            }
        } else {
            ShareAdminClass oldAdminClass = shareAdminClassDao.getAdminClass(adminClass.getGid());
            // 更新教学班级名称
            shareTeachingClassDao.updateShareTeacherClass(oldAdminClass.getClassCode(), adminClass.getClassName());
            return shareAdminClassDao.addOrUpdate(adminClass) ? ResultCodeVo.rightCode("保存成功")
                            : ResultCodeVo.errorCode("保存失败");
        }
    }

    /***
     * 查询行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    public ShareAdminClass getAdminClass(String gid) {
        return shareAdminClassDao.getAdminClass(gid);
    }

    /**
     * 删除行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo deleteAdminClass(String gid) {
        ShareAdminClass adminClass = getAdminClass(gid);
        int count = shareAdminClassDao.deleteAdminClass(gid);
        adminClassSubjectDao.deleteByAdminClassCode(adminClass.getClassCode());
        adminClassStudentDao.deleteAdminClassStudent(adminClass.getClassCode());
        shareUserStudentDao.deleteStudentAdminClass(adminClass.getClassCode());
        shareTeachingClassStudentDao.deleteByAdminClass(adminClass.getClassCode());
        shareTeachingClassSubjectDao.delete(adminClass.getClassCode(), null, null);
        shareTeachingClassDao.deleteByAdminClassCode(adminClass.getClassCode());
        return count > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }

    /**
     * 查询机构行政班级 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareAdminClass> getList(String orgCode) {
        return shareAdminClassDao.getAdminClassList(orgCode);
    }

    /**
     * 查询行政班级列表 ()<br>
     * 
     * @param orgCode 机构编码
     * @param gradeCode 年级编码
     * @return
     */
    public List<ShareAdminClass> getAdminClassList(String orgCode, String gradeCode) {
        return shareAdminClassDao.getAdminClassList(orgCode, gradeCode);
    }

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
    @Override
    public List<Map<String, Object>> queryAdminClassTeachInfo(String orgCode, String adminClassCode, Integer page,
                    Integer rows, String gradeCode) {
        return adminClassSubjectDao.selectPageByAdminClassCode(orgCode, adminClassCode, page, rows, gradeCode);
    }

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
    @Override
    public ResultCodeVo updateTeacherInfo(String orgCode, String subjectCode, String gradeCode, String teacherCode,
                    String teacherName, String adminClassCode, String beginTimeStr, String endTimeStr,
                    String oldTeacherCode) throws Exception {
        // 参数校验
        if (StringUtils.isEmpty(teacherCode)) {
            return ResultCodeVo.errorCode("请选择任教老师！");
        }
        if (StringUtils.isEmpty(beginTimeStr)) {
            return ResultCodeVo.errorCode("任教开始时间不能为空！");
        }
        if (StringUtils.isEmpty(endTimeStr)) {
            return ResultCodeVo.errorCode("任教结束时间不能为空！");
        }
       
        String ipAddress = getIp();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        Timestamp beginTime = new Timestamp(DateUtils.getDateTime(beginTimeStr, "yyyy-MM-dd").getTime());
        Timestamp endTime = new Timestamp(DateUtils.getDateTime(endTimeStr, "yyyy-MM-dd").getTime());
        List<ShareAdminClassSubject> list = adminClassSubjectDao.select(orgCode, adminClassCode, subjectCode, gradeCode,
                        oldTeacherCode);
        // 判断新选择的老师在当前行政班级学科信息中是否已存在
        List<ShareAdminClassSubject> oldList = adminClassSubjectDao.select(orgCode, adminClassCode, subjectCode,
                        gradeCode, teacherCode);
        // 如果新选择的教师编码等于旧的教师编码，说明老师信息没改，只改了时间
        if (teacherCode.equals(oldTeacherCode)) {
            int updateNum = adminClassSubjectDao.updateTeacherInfo(orgCode, subjectCode, gradeCode, teacherCode,
                            teacherName, adminClassCode, ipAddress, beginTime, endTime, oldTeacherCode);
            // 如果更新行政班级学科信息成功，需要将更新前的信息插入历史记录表
            if (0 < updateNum) {
                adminClassSubjectDao.insertAdminClassSubjectToHistory(list.get(0), ipAddress);
            }
        } else {
            if (oldList != null && oldList.size() > 0) {
                return ResultCodeVo.errorCode("当前选择的老师在对应的班级中已存在，请勿重复设置！");
            } else {
                int updateNum = adminClassSubjectDao.updateTeacherInfo(orgCode, subjectCode, gradeCode, teacherCode,
                                teacherName, adminClassCode, ipAddress, beginTime, endTime, oldTeacherCode);
                // 如果更新行政班级学科信息成功，需要将更新前的信息插入历史记录表
                if (0 < updateNum) {
                    adminClassSubjectDao.insertAdminClassSubjectToHistory(list.get(0), ipAddress);
                }
            }
        }
        // 当前学年编码
        String currentStudyYearCode = BeanHelper.getStudyYearCode();
        // 当前学期编码
        String currentTermCode = BeanHelper.getTermCode();
        // 根据行政班级查询教学班级
        List<ShareTeachingClass> teachingClassList = shareTeachingClassDao.selectListByAdminClassCode(orgCode,
                        subjectCode, gradeCode, currentStudyYearCode, adminClassCode);
        // 循环拼接教学班级信息
        String teachingClassCodes = "";
        if (teachingClassList != null && teachingClassList.size() > 0) {
            for (ShareTeachingClass teachingClass : teachingClassList) {
                teachingClassCodes = teachingClassCodes + teachingClass.getTeachingClassCode() + ",";
            }
        }
        // 去掉尾端逗号
        if (StringUtils.isNotEmpty(teachingClassCodes)) {
            teachingClassCodes = teachingClassCodes.substring(0, teachingClassCodes.length() - 1);
        }
        // 更新教学班级学科表
        int updateResult = shareTeachingClassSubjectDao.updateTeacherInfo(orgCode, subjectCode, gradeCode,
                        teachingClassCodes, teacherCode, teacherName, currentStudyYearCode, currentTermCode, ipAddress,
                        beginTime, endTime, oldTeacherCode);
        // 根据更新结果做不同处理
        if (updateResult <= 0) {
            generateTeachingClassDefaultInfo(orgCode, subjectCode, gradeCode, teacherCode, teacherName, adminClassCode,
                            ipAddress, currentTime, currentStudyYearCode, currentTermCode, beginTime, endTime);
        }
        return ResultCodeVo.rightCode("修改成功！");
    }

    /**
     * 
     * (生成教学班级默认信息)<br>
     * 处理流程：1，教学班级新增一条记录，新增前需要判断，如果已经存在，无需重复新增 2，教学班级学科新增一条记录 3，生成教学班级学生信息
     * 
     * @param orgCode
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @param teacherName
     * @param adminClassCode
     * @param ipAddress
     * @param currentTime
     * @param currentStudyYearCode
     * @param currentTermCode
     */
    private void generateTeachingClassDefaultInfo(String orgCode, String subjectCode, String gradeCode,
                    String teacherCode, String teacherName, String adminClassCode, String ipAddress,
                    Timestamp currentTime, String currentStudyYearCode, String currentTermCode, Timestamp beginTime,
                    Timestamp endTime) {
        // 拼接生成教学班级编码：教学班级编码=行政班级编码+学科编码+年级编码
        String teachingClassCode = adminClassCode + subjectCode + gradeCode;
        // 查询教学班级是否已经存在，如果存在无需重复新增
        ShareTeachingClass teachingClass = shareTeachingClassDao.selectByTeachingClassCode(orgCode, teachingClassCode);
        if (null == teachingClass) {
            ShareAdminClass adminClass = shareAdminClassDao.selectByClassCode(orgCode, gradeCode, adminClassCode);
            // 教学班级新增一条记录
            ShareTeachingClass shareTeachingClass = new ShareTeachingClass();
            shareTeachingClass.setGid(UUID.randomUUID().toString());
            shareTeachingClass.setOrgCode(orgCode);
            shareTeachingClass.setSubjectCode(subjectCode);
            shareTeachingClass.setGradeCode(gradeCode);
            shareTeachingClass.setStudyYearCode(currentStudyYearCode);
            shareTeachingClass.setAdminClassCode(adminClassCode);
            shareTeachingClass.setTeachingClassCode(teachingClassCode);
            if (adminClass != null) {
                shareTeachingClass.setTeachingClassName(adminClass.getClassName());
            }
            shareTeachingClass.setModifyIP(ipAddress);
            shareTeachingClass.setModifyPgm("updateTeacherInfo");
            shareTeachingClass.setModifyTime(currentTime);
            shareTeachingClass.setSysVer(0);
            shareTeachingClass.setStartTime(beginTime);
            shareTeachingClass.setEndTime(endTime);
            shareTeachingClassDao.saveShareTeachingClass(shareTeachingClass);
            // 新增教学班级学生
            shareTeachingClassStudentDao.addShareTeachingClassStudent(shareTeachingClass);
        }
        // 教学班级学科新增一条记录
        ShareTeachingClassSubject shareTeachingClassSubject = new ShareTeachingClassSubject();
        shareTeachingClassSubject.setGid(UUID.randomUUID().toString());
        shareTeachingClassSubject.setOrgCode(orgCode);
        shareTeachingClassSubject.setSubjectCode(subjectCode);
        shareTeachingClassSubject.setGradeCode(gradeCode);
        shareTeachingClassSubject.setTeachingClassCode(teachingClassCode);
        shareTeachingClassSubject.setTeacherCode(teacherCode);
        shareTeachingClassSubject.setTeacherName(teacherName);
        shareTeachingClassSubject.setTeacherRole("0");// 默认值
        shareTeachingClassSubject.setStudyYearCode(currentStudyYearCode);
        shareTeachingClassSubject.setTermCode(currentTermCode);
        shareTeachingClassSubject.setModifyIP(ipAddress);
        shareTeachingClassSubject.setModifyPgm("updateTeacherInfo");
        shareTeachingClassSubject.setModifyTime(currentTime);
        shareTeachingClassSubject.setSysVer(0);
        shareTeachingClassSubject.setBeginTime(beginTime);
        shareTeachingClassSubject.setEndTime(endTime);
        shareTeachingClassSubjectDao.add(shareTeachingClassSubject);
    }

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
    @Override
    public ResultCodeVo addAdminClassSubject(String orgCode, String subjectCode, String gradeCode, String teacherCode,
                    String teacherName, String adminClassCode, String beginTimeStr, String endTimeStr)
                                    throws Exception {
        // 参数校验
        if (StringUtils.isEmpty(teacherCode)) {
            return ResultCodeVo.errorCode("请选择老师！");
        }
        /*
         * if(StringUtils.isEmpty(beginTimeStr)){ return ResultCodeVo.errorCode("开始时间不能为空！"); }
         * if(StringUtils.isEmpty(endTimeStr)){ return ResultCodeVo.errorCode("结束时间不能为空！"); }
         */
        if (StringUtils.isEmpty(subjectCode)) {
            return ResultCodeVo.errorCode("请选择学科！");
        }
       
        String ipAddress = getIp();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        /*
         * Timestamp beginTime=new Timestamp(DateUtils.getDateTime(beginTimeStr,
         * "yyyy-MM-dd").getTime()); Timestamp endTime=new
         * Timestamp(DateUtils.getDateTime(endTimeStr, "yyyy-MM-dd").getTime());
         */
        // 获取当前学年信息
        SharePlatform platformInfo = sharePlatformDao.getByCodeGlobal();
        // 当前学年编码
        String currentStudyYearCode = BeanHelper.getStudyYearCode();
        // 当前学期编码
        String currentTermCode = BeanHelper.getTermCode();
        // 拼接生成教学班级编码
        String teachingClassCode = adminClassCode + subjectCode + gradeCode;
        // 查询是否已存在相同的行政班级学科记录
        List<ShareAdminClassSubject> list = adminClassSubjectDao.select(orgCode, adminClassCode, subjectCode, gradeCode,
                        teacherCode);
        if (list.size() == 0) {
            ShareAdminClassSubject adminClassSubject = new ShareAdminClassSubject();
            adminClassSubject.setGid(UUID.randomUUID().toString());
            adminClassSubject.setOrgCode(orgCode);
            adminClassSubject.setSubjectCode(subjectCode);
            adminClassSubject.setGradeCode(gradeCode);
            adminClassSubject.setAdminClassCode(adminClassCode);
            adminClassSubject.setTeacherCode(teacherCode);
            adminClassSubject.setTeacherName(teacherName);
            adminClassSubject.setTeacherRole(0);
            /*
             * adminClassSubject.setBeginTime(beginTime); adminClassSubject.setEndTime(endTime);
             */
            adminClassSubject.setModifyPgm("addAdminClassSubject");
            adminClassSubject.setModifyTime(currentTime);
            adminClassSubject.setModifyIP(ipAddress);
            adminClassSubject.setSysVer(0);
            // 行政班级学科新增一条记录
            adminClassSubjectDao.save(adminClassSubject);
            // 查询对应的教学班级信息是否已存在
            ShareTeachingClass teachingClass = shareTeachingClassDao.selectByTeachingClassCode(orgCode,
                            teachingClassCode);
            if (teachingClass != null) {
                // 如果查询到记录，那么只要新增教学班级学科信息
                ShareTeachingClassSubject shareTeachingClassSubject = new ShareTeachingClassSubject();
                shareTeachingClassSubject.setGid(UUID.randomUUID().toString());
                shareTeachingClassSubject.setOrgCode(orgCode);
                shareTeachingClassSubject.setSubjectCode(subjectCode);
                shareTeachingClassSubject.setGradeCode(gradeCode);
                shareTeachingClassSubject.setTeachingClassCode(teachingClassCode);
                shareTeachingClassSubject.setTeacherCode(teacherCode);
                shareTeachingClassSubject.setTeacherName(teacherName);
                shareTeachingClassSubject.setTeacherRole("0");// 默认值
                shareTeachingClassSubject.setStudyYearCode(currentStudyYearCode);
                shareTeachingClassSubject.setTermCode(currentTermCode);
                shareTeachingClassSubject.setModifyIP(ipAddress);
                shareTeachingClassSubject.setModifyPgm("addAdminClassSubject");
                shareTeachingClassSubject.setModifyTime(currentTime);
                shareTeachingClassSubject.setSysVer(0);
                /*
                 * shareTeachingClassSubject.setBeginTime(beginTime);
                 * shareTeachingClassSubject.setEndTime(endTime);
                 */
                shareTeachingClassSubjectDao.add(shareTeachingClassSubject);
            } else {
                // 如果没有查询到教学班级信息
                ShareAdminClass adminClass = shareAdminClassDao.selectByClassCode(orgCode, gradeCode, adminClassCode);
                // 教学班级新增一条记录
                ShareTeachingClass shareTeachingClass = new ShareTeachingClass();
                shareTeachingClass.setGid(UUID.randomUUID().toString());
                shareTeachingClass.setOrgCode(orgCode);
                shareTeachingClass.setSubjectCode(subjectCode);
                shareTeachingClass.setGradeCode(gradeCode);
                shareTeachingClass.setStudyYearCode(currentStudyYearCode);
                shareTeachingClass.setAdminClassCode(adminClassCode);
                shareTeachingClass.setTeachingClassCode(teachingClassCode);
                if (adminClass != null) {
                    shareTeachingClass.setTeachingClassName(adminClass.getClassName());
                }
                shareTeachingClass.setModifyIP(ipAddress);
                shareTeachingClass.setModifyPgm("addAdminClassSubject");
                shareTeachingClass.setModifyTime(currentTime);
                shareTeachingClass.setSysVer(0);
                /*
                 * shareTeachingClass.setStartTime(beginTime);
                 * shareTeachingClass.setEndTime(endTime);
                 */
                shareTeachingClassDao.saveShareTeachingClass(shareTeachingClass);
                // 教学班级学科新增一条记录
                ShareTeachingClassSubject shareTeachingClassSubject = new ShareTeachingClassSubject();
                shareTeachingClassSubject.setGid(UUID.randomUUID().toString());
                shareTeachingClassSubject.setOrgCode(orgCode);
                shareTeachingClassSubject.setSubjectCode(subjectCode);
                shareTeachingClassSubject.setGradeCode(gradeCode);
                shareTeachingClassSubject.setTeachingClassCode(teachingClassCode);
                shareTeachingClassSubject.setTeacherCode(teacherCode);
                shareTeachingClassSubject.setTeacherName(teacherName);
                shareTeachingClassSubject.setTeacherRole("0");// 默认值
                shareTeachingClassSubject.setStudyYearCode(currentStudyYearCode);
                shareTeachingClassSubject.setTermCode(currentTermCode);
                shareTeachingClassSubject.setModifyIP(ipAddress);
                shareTeachingClassSubject.setModifyPgm("addAdminClassSubject");
                shareTeachingClassSubject.setModifyTime(currentTime);
                shareTeachingClassSubject.setSysVer(0);
                /*
                 * shareTeachingClassSubject.setBeginTime(beginTime);
                 * shareTeachingClassSubject.setEndTime(endTime);
                 */
                shareTeachingClassSubjectDao.add(shareTeachingClassSubject);
                // 新增教学班级学生
                shareTeachingClassStudentDao.addShareTeachingClassStudent(shareTeachingClass);
            }
            return ResultCodeVo.rightCode("添加成功！");
        } else {
            return ResultCodeVo.errorCode("该老师对应学科的任教信息已存在！");
        }
    }
}
