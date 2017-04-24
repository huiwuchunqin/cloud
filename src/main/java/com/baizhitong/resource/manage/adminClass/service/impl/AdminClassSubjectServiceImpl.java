package com.baizhitong.resource.manage.adminClass.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.dao.share.ShareAdminClassSubjectDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassStudentDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassSubjectDao;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassSubjectService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.ShareTeachingClass;
import com.baizhitong.resource.model.share.ShareTeachingClassSubject;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.ObjectUtils;

@Service
public class AdminClassSubjectServiceImpl extends BaseService implements IAdminClassSubjectService {
    @Autowired
    private ShareAdminClassSubjectDao    adminClassSubjectDao;
    @Autowired
    private ShareTeachingClassDao        shareTeachingClassDao;
    @Autowired
    private ShareTeachingClassSubjectDao shareTeachingClassSubjectDao;
    @Autowired
    private ShareTeachingClassStudentDao shareTeachingClassStudentDao;
    @Autowired
    private ShareAdminClassDao           shareAdminClassDao;

    /**
     * 保存行政班级学科 ()<br>
     * 
     * @param adminClassSubject
     * @return
     */
    public ResultCodeVo saveAdminClassSubject(ShareAdminClassSubject adminClassSubject) {
       
        String ip = getIp();
        CompanyInfoVo company =  getCompanyInfo();
        String orgCode = company.getOrgCode();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        // 当前学年编码
        String currentStudyYearCode = BeanHelper.getStudyYearCode();
        // 当前学期编码
        String currentTermCode = BeanHelper.getTermCode();
        // 新增
        if (StringUtils.isEmpty(adminClassSubject.getGid())) {
            adminClassSubject.setGid(UUID.randomUUID().toString());
            adminClassSubject.setModifyIP(ip);
            adminClassSubject.setModifyPgm("adminClassSubjectService");
            adminClassSubject.setModifyTime(new Timestamp(new Date().getTime()));
            adminClassSubject.setOrgCode(company.getOrgCode());
            adminClassSubject.setTeacherRole(0);
            adminClassSubject.setSysVer(0);
            List<ShareAdminClassSubject> adminClassSubjectsList = adminClassSubjectDao.getAdminClassSubjectList(
                            adminClassSubject.getTeacherCode(), adminClassSubject.getAdminClassCode(),
                            adminClassSubject.getSubjectCode(), adminClassSubject.getGradeCode());
            if (adminClassSubjectsList != null && adminClassSubjectsList.size() > 0) {
                return ResultCodeVo.errorCode("已经存在！");
            } else {
                boolean saveFlag = adminClassSubjectDao.save(adminClassSubject);
                // 新增教学班级、教学班级学生、教学班级学科
                // 拼接生成教学班级编码 教学班级编码=行政班级编码+学科编码+年级编码
                String teachingClassCode = adminClassSubject.getAdminClassCode() + adminClassSubject.getSubjectCode()
                                + adminClassSubject.getGradeCode();
                // 查询对应的教学班级信息是否已存在
                ShareTeachingClass teachingClass = shareTeachingClassDao.selectByTeachingClassCode(orgCode,
                                teachingClassCode);
                if (ObjectUtils.isNotNull(teachingClass)) {
                    // 如果查询到记录，那么只要新增教学班级学科信息
                    ShareTeachingClassSubject shareTeachingClassSubject = new ShareTeachingClassSubject();
                    shareTeachingClassSubject.setGid(UUID.randomUUID().toString());
                    shareTeachingClassSubject.setOrgCode(orgCode);
                    shareTeachingClassSubject.setSubjectCode(adminClassSubject.getSubjectCode());
                    shareTeachingClassSubject.setGradeCode(adminClassSubject.getGradeCode());
                    shareTeachingClassSubject.setTeachingClassCode(teachingClassCode);
                    shareTeachingClassSubject.setTeacherCode(adminClassSubject.getTeacherCode());
                    shareTeachingClassSubject.setTeacherName(adminClassSubject.getTeacherName());
                    shareTeachingClassSubject.setTeacherRole("0");// 默认值
                    shareTeachingClassSubject.setStudyYearCode(currentStudyYearCode);
                    shareTeachingClassSubject.setTermCode(currentTermCode);
                    shareTeachingClassSubject.setModifyIP(ip);
                    shareTeachingClassSubject.setModifyPgm("adminClassSubjectService");
                    shareTeachingClassSubject.setModifyTime(currentTime);
                    shareTeachingClassSubject.setSysVer(0);
                    shareTeachingClassSubject.setBeginTime(adminClassSubject.getBeginTime());
                    shareTeachingClassSubject.setEndTime(adminClassSubject.getEndTime());
                    shareTeachingClassSubjectDao.add(shareTeachingClassSubject);
                } else {
                    // 如果没有查询到教学班级信息
                    ShareAdminClass adminClass = shareAdminClassDao.selectByClassCode(orgCode,
                                    adminClassSubject.getGradeCode(), adminClassSubject.getAdminClassCode());
                    // 教学班级新增一条记录
                    ShareTeachingClass shareTeachingClass = new ShareTeachingClass();
                    shareTeachingClass.setGid(UUID.randomUUID().toString());
                    shareTeachingClass.setOrgCode(orgCode);
                    shareTeachingClass.setSubjectCode(adminClassSubject.getSubjectCode());
                    shareTeachingClass.setGradeCode(adminClassSubject.getGradeCode());
                    shareTeachingClass.setStudyYearCode(currentStudyYearCode);
                    shareTeachingClass.setAdminClassCode(adminClassSubject.getAdminClassCode());
                    shareTeachingClass.setTeachingClassCode(teachingClassCode);
                    if (ObjectUtils.isNotNull(adminClass)) {
                        shareTeachingClass.setTeachingClassName(adminClass.getClassName());
                    }
                    shareTeachingClass.setModifyIP(ip);
                    shareTeachingClass.setModifyPgm("adminClassSubjectService");
                    shareTeachingClass.setModifyTime(currentTime);
                    shareTeachingClass.setSysVer(0);
                    shareTeachingClass.setStartTime(adminClassSubject.getBeginTime());
                    shareTeachingClass.setEndTime(adminClassSubject.getEndTime());
                    shareTeachingClassDao.saveShareTeachingClass(shareTeachingClass);
                    // 教学班级学科新增一条记录
                    ShareTeachingClassSubject shareTeachingClassSubject = new ShareTeachingClassSubject();
                    shareTeachingClassSubject.setGid(UUID.randomUUID().toString());
                    shareTeachingClassSubject.setOrgCode(orgCode);
                    shareTeachingClassSubject.setSubjectCode(adminClassSubject.getSubjectCode());
                    shareTeachingClassSubject.setGradeCode(adminClassSubject.getGradeCode());
                    shareTeachingClassSubject.setTeachingClassCode(teachingClassCode);
                    shareTeachingClassSubject.setTeacherCode(adminClassSubject.getTeacherCode());
                    shareTeachingClassSubject.setTeacherName(adminClassSubject.getTeacherName());
                    shareTeachingClassSubject.setTeacherRole("0");// 默认值
                    shareTeachingClassSubject.setStudyYearCode(currentStudyYearCode);
                    shareTeachingClassSubject.setTermCode(currentTermCode);
                    shareTeachingClassSubject.setModifyIP(ip);
                    shareTeachingClassSubject.setModifyPgm("adminClassSubjectService");
                    shareTeachingClassSubject.setModifyTime(currentTime);
                    shareTeachingClassSubject.setSysVer(0);
                    shareTeachingClassSubject.setBeginTime(adminClassSubject.getBeginTime());
                    shareTeachingClassSubject.setEndTime(adminClassSubject.getEndTime());
                    shareTeachingClassSubjectDao.add(shareTeachingClassSubject);
                    // 新增教学班级学生
                    shareTeachingClassStudentDao.addShareTeachingClassStudent(shareTeachingClass);
                }
                if (saveFlag) {
                    return ResultCodeVo.rightCode("保存成功！");
                } else {
                    return ResultCodeVo.errorCode("保存失败！");
                }
            }
        } else {
            ShareAdminClassSubject old = adminClassSubjectDao.getByGid(adminClassSubject.getGid());
            old.setBeginTime(adminClassSubject.getBeginTime());
            old.setEndTime(adminClassSubject.getEndTime());
            adminClassSubject.setModifyIP(ip);
            adminClassSubject.setModifyPgm("adminClassSubjectService");
            adminClassSubject.setModifyTime(new Timestamp(new Date().getTime()));
            return adminClassSubjectDao.save(adminClassSubject) ? ResultCodeVo.rightCode("保存成功！")
                            : ResultCodeVo.errorCode("保存失败！");
        }

    }

    /**
     * 查询行政班级学科 ()<br>
     * 
     * @param param
     * @return
     */
    public Page getAdminClassSubject(Map<String, Object> param, Integer pageNo, Integer pageSize) {

        return adminClassSubjectDao.getAdminClassSubjectList(param, pageNo, pageSize);
    }

    /**
     * 删除行政班级学科 ()<br>
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo delete(String gid) {
        try {
            ShareAdminClassSubject classSubject = adminClassSubjectDao.getByGid(gid);

            // 教学班级编码
            String teachingClassCode = classSubject.getAdminClassCode() + classSubject.getSubjectCode()
                            + classSubject.getGradeCode();
            shareTeachingClassSubjectDao.delete(classSubject.getAdminClassCode(), classSubject.getSubjectCode(),
                            classSubject.getTeacherCode());
            adminClassSubjectDao.deleteAdminClassSubject(gid);

           /* long teachingSubjectCount = shareTeachingClassSubjectDao.getClassSubjectCount(teachingClassCode);*/

            // 没有任教信息的情况下删除班级和班级学生
            /*if (teachingSubjectCount <= 0) {*/
                shareTeachingClassDao.delete(teachingClassCode);
                shareTeachingClassStudentDao.deleteAllStudent(teachingClassCode);
            /*}*/
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return ResultCodeVo.rightCode("删除失败");
        }
        return ResultCodeVo.rightCode("删除成功");

    }

    /**
     * 行政班级学科 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareAdminClassSubject> getAdminClassSubject(String orgCode, String adminClassCode) {
        return adminClassSubjectDao.getList(orgCode, adminClassCode);
    }
}
