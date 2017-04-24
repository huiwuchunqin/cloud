package com.baizhitong.resource.manage.shareTeachingClass.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.dao.share.ShareAdminClassSubjectDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassStudentDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassSubjectDao;
import com.baizhitong.resource.dao.share.ShareUserTeacherDao;
import com.baizhitong.resource.manage.furtherEducation.action.WebSocket;
import com.baizhitong.resource.manage.shareTeachingClass.service.IShareTeachingClassService;
import com.baizhitong.resource.manage.shareTeachingClass.service.IShareTeachingClassSubjectService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;
import com.baizhitong.resource.model.share.ShareTeachingClass;
import com.baizhitong.resource.model.share.ShareTeachingClassSubject;
import com.baizhitong.resource.model.share.ShareUserTeacher;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareTeachingClassSubjectVo;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;

@Service
public class ShareTeachingClassServiceImpl extends BaseService implements IShareTeachingClassService {
    /** 课程班级dao */
    @Autowired
    private ShareTeachingClassDao             teachingClassDao;
    @Autowired
    private ShareAdminClassDao                adminClassDao;
    @Autowired
    private ShareUserTeacherDao               teacherDao;
    @Autowired
    private ShareTeachingClassStudentDao      teachingClassStudentDao;
    @Autowired
    private ShareTeachingClassSubjectDao      teachingClassSubjectDao;
    @Autowired
    private IShareTeachingClassSubjectService teachingClassSubjectService;
    @Autowired
    private ShareAdminClassSubjectDao         adminClassSubjectDao;

    /**
     * 查询课程班级 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page list(Map<String, Object> param, Integer page, Integer rows) {
        return teachingClassDao.getList(param, page, rows);
    }

    /**
     * 新增或修改课程班级 ()<br>
     * 
     * @param tchClass
     * @return
     */
    public ResultCodeVo addOrUpdate(ShareTeachingClass tchClass, String teacherCode, String teacherName,
                    String termCode, String teachingClassSubjectGid) {

        String ip = getIp();
        CompanyInfoVo companyInfoVo = getCompanyInfo();
        String orgCode = companyInfoVo.getOrgCode();

        if (StringUtils.isEmpty(tchClass.getGid())) {

            /******** 课程班级 *******/
            String teachingClassCode = "";
            if (StringUtils.isNotEmpty(tchClass.getAdminClassCode())) {

                teachingClassCode = tchClass.getAdminClassCode() + tchClass.getSubjectCode().trim()
                                + tchClass.getGradeCode();
            } else {

                // 选修课编码生成规则
                teachingClassCode = TimeUtils.formatDate(new Date(), "YYYYMMDDHHmmss") + tchClass.getSubjectCode();
            }

            tchClass.setGid(UUID.randomUUID().toString());
            tchClass.setTeachingClassCode(teachingClassCode);
            tchClass.setModifyIP(ip);
            tchClass.setModifyPgm("teachingClassService");
            tchClass.setModifyTime(new Timestamp(new Date().getTime()));
            tchClass.setOrgCode(orgCode);
            tchClass.setSysVer(1);

            /******** 课程班级学科 *******/
            ShareTeachingClassSubject classSubject = new ShareTeachingClassSubject();
            classSubject.setTeacherName(teacherName);
            classSubject.setTeacherRole("0");
            classSubject.setTeacherCode(teacherCode);
            classSubject.setGid(UUID.randomUUID().toString());
            classSubject.setOrgCode(orgCode);
            classSubject.setSubjectCode(tchClass.getSubjectCode());
            classSubject.setGradeCode(tchClass.getGradeCode());
            classSubject.setTeachingClassCode(teachingClassCode);
            classSubject.setBeginTime(tchClass.getStartTime());
            classSubject.setEndTime(tchClass.getEndTime());
            classSubject.setStudyYearCode(tchClass.getStudyYearCode());
            classSubject.setModifyIP(ip);
            classSubject.setTermCode(termCode);
            classSubject.setModifyPgm("shareTeachingClassService");
            classSubject.setModifyTime(new Timestamp(new Date().getTime()));
            classSubject.setSysVer(1);

            // 选修课开班
            if (StringUtils.isEmpty(tchClass.getAdminClassCode())) {
                teachingClassDao.saveShareTeachingClass(tchClass);
                teachingClassSubjectDao.add(classSubject);
            } else {
                List<ShareTeachingClass> classList = teachingClassDao.getClass(tchClass.getAdminClassCode(),
                                tchClass.getSubjectCode(), tchClass.getGradeCode(), orgCode, "");

                // 班级不存在的情况
                if (classList == null || classList.size() <= 0) {

                    // 课程班级
                    teachingClassDao.saveShareTeachingClass(tchClass);

                    // 课程班级学生
                    teachingClassStudentDao.addShareTeachingClassStudent(tchClass);

                    // 课程班级学科
                    teachingClassSubjectDao.add(classSubject);
                } else {

                    // 学科班级已经存在且老师教的
                    List<ShareTeachingClassSubjectVo> classSubjects = new ArrayList<ShareTeachingClassSubjectVo>();
                    classSubjects = teachingClassSubjectService.getShareTeachingClassSubjectList(teachingClassCode,
                                    tchClass.getSubjectCode(), tchClass.getGradeCode(), teacherCode, orgCode,
                                    tchClass.getStudyYearCode(), "");

                    if ((classSubjects != null && classSubjects.size() > 0)) {
                        return ResultCodeVo.errorCode("该老师已经教过这个班这个学科！");
                    } else {

                        // 防止班级编码生成规则不一样用以前的（课程班级编码规则改了好几次）
                        classSubject.setTeachingClassCode(classList.get(0).getTeachingClassCode());

                        // 课程班级学科
                        teachingClassSubjectDao.add(classSubject);
                    }
                }
            }
        } else {
            ShareTeachingClass old = teachingClassDao.getClass(tchClass.getGid());
            old.setModifyIP(ip);
            old.setModifyPgm("teachingClassService");
            old.setModifyTime(new Timestamp(new Date().getTime()));
            old.setTeachingClassName(tchClass.getTeachingClassName());
            old.setEndTime(tchClass.getEndTime());
            old.setStartTime(tchClass.getStartTime());
            teachingClassDao.saveShareTeachingClass(old);
            // 修改教学班级学科的任课老师
            teachingClassSubjectDao.updateClassTeacher(old.getTeachingClassCode(), old.getSubjectCode(),
                            old.getGradeCode(), teacherCode, teacherName, "teachingClassService");

            // 修改行政班级学科任课老师
            adminClassSubjectDao.updateAdminClassSubject(old.getAdminClassCode(), old.getSubjectCode(),
                            old.getGradeCode(), teacherCode, teacherName, "teachingClassService");

        }
        return ResultCodeVo.rightCode("保存成功");
    }
    
    @OnMessage
    public void Test(String message, Session session) throws IOException, InterruptedException {
        System.out.println("Received: " + message);
        session.getBasicRemote().sendText("你是个逗逼");
    }

    /**
     * 导入默认课程班级 学科 学生信息 ()<br>
     * 如果班级已经有老师教的会继续导入新的老师
     * 
     * @return
     */
    
    public  ResultCodeVo importDefault() {

        CompanyInfoVo companyInfoVo = getCompanyInfo();
        String orgCode = companyInfoVo.getOrgCode();
        String ip = getIp();
        List<ShareAdminClass> adminClassList = adminClassDao.getAdminClassList(orgCode);

        if (adminClassList == null || adminClassList.size() <= 0) {
            return ResultCodeVo.errorCode("行政班级没有数据");
        }
        for (ShareAdminClass adminClass : adminClassList) {
            int progress = 1;
            String gradeCode = adminClass.getGradeCode();
            String adminClassCode = adminClass.getClassCode();
            List<ShareAdminClassSubject> classSubjctList = adminClassSubjectDao.getList(orgCode, adminClassCode);
            if (classSubjctList != null && classSubjctList.size() > 0) {
                for (ShareAdminClassSubject adminClassSubject : classSubjctList) {
                    // 学年跟入学年份所对应的年级匹不匹配
                    if (!isGradeMatch(adminClassSubject.getGradeCode(), BeanHelper.getStudyYearCode(),
                                    adminClass.getEntryYear())) {
                        continue;
                    }
                    String subjectCode = adminClassSubject.getSubjectCode();
                    Timestamp beginTime = adminClassSubject.getBeginTime();
                    Timestamp endTime = adminClassSubject.getEndTime();
                    String teacherCode = adminClassSubject.getTeacherCode();
                    String teacherName = adminClassSubject.getTeacherName();
                    String teachingClassName = adminClass.getClassName();

                    // 老师被删除以后不继续导入
                    ShareUserTeacher teacher = teacherDao.getTeacher(teacherCode);
                    if (teacher == null) {
                        continue;
                    }
                    /**** 课程班级 *****/
                    String teachingClassCode = adminClass.getClassCode() + subjectCode.trim() + gradeCode;
                    ShareTeachingClass teachingClass = new ShareTeachingClass();
                    teachingClass.setGid(UUID.randomUUID().toString());
                    teachingClass.setTeachingClassCode(teachingClassCode);
                    teachingClass.setModifyIP(ip);
                    teachingClass.setAdminClassCode(adminClassCode);
                    teachingClass.setModifyPgm("teachingClassService-importDefault");
                    teachingClass.setModifyTime(new Timestamp(new Date().getTime()));
                    teachingClass.setStudyYearCode(BeanHelper.getStudyYearCode());
                    teachingClass.setOrgCode(orgCode);
                    teachingClass.setSysVer(1);
                    teachingClass.setTeachingClassName(teachingClassName);
                    teachingClass.setGradeCode(gradeCode);
                    teachingClass.setSubjectCode(subjectCode);
                    teachingClass.setStartTime(beginTime);
                    teachingClass.setEndTime(endTime);
                    /******* 课程班级学科 *********/
                    ShareTeachingClassSubject teachingClassSubject = new ShareTeachingClassSubject();
                    teachingClassSubject.setTeacherCode(teacherCode);
                    teachingClassSubject.setGid(UUID.randomUUID().toString());
                    teachingClassSubject.setOrgCode(orgCode);
                    teachingClassSubject.setSubjectCode(subjectCode);
                    teachingClassSubject.setGradeCode(gradeCode);
                    teachingClassSubject.setTeachingClassCode(teachingClassCode);
                    teachingClassSubject.setBeginTime(beginTime);
                    teachingClassSubject.setEndTime(endTime);
                    teachingClassSubject.setTeacherRole("0");
                    teachingClassSubject.setModifyIP(ip);
                    teachingClassSubject.setStudyYearCode(BeanHelper.getStudyYearCode());
                    teachingClassSubject.setTermCode(BeanHelper.getTermCode());
                    teachingClassSubject.setModifyPgm("shareTeachingClassService-importDefault");
                    teachingClassSubject.setModifyTime(new Timestamp(new Date().getTime()));
                    teachingClassSubject.setSysVer(1);
                    teachingClassSubject.setTeacherName(teacherName);
                    // 查询老师有没有教过此班级学科
                    List<ShareTeachingClassSubjectVo> classSubjects = teachingClassSubjectService
                                    .getShareTeachingClassSubjectList(teachingClassCode, subjectCode, gradeCode,
                                                    teacherCode, orgCode, BeanHelper.getStudyYearCode(), "");

                    // 一个行政班级年级学科不能重复增加
                    if (StringUtils.isNotEmpty(adminClassCode)) {
                        List<ShareTeachingClass> classList = teachingClassDao.getClass(adminClassCode,
                                        adminClassSubject.getSubjectCode(), gradeCode, orgCode, "");
                        if ((classList == null || classList.size() <= 0)) {
                            // 课程班级
                            teachingClassDao.saveShareTeachingClass(teachingClass);
                            // 课程班级学生
                            teachingClassStudentDao.addShareTeachingClassStudent(teachingClass);
                            // 课程班级学科
                            if (classSubjects == null || classSubjects.size() <= 0) {
                                teachingClassSubjectDao.add(teachingClassSubject);
                            }
                        } else {
                            // 班级老师学科开过班了直接过
                            if ((classSubjects != null && classSubjects.size() > 0)) {
                                /*
                                 * for (ShareTeachingClassSubject teachingClassSubject :
                                 * classSubjects) { teachingClassSubejct.setTeacherCode(
                                 * teacherCode); teachingClassSubjectDao.add( teachingClassSubejct);
                                 * }
                                 */
                                continue;
                            } else {
                                teachingClassSubject.setTeachingClassCode(classList.get(0).getTeachingClassCode());
                                teachingClassSubjectDao.add(teachingClassSubject);
                            }
                        }
                    }
                }
            }
            try {
                WebSocket.session.getBasicRemote().sendObject(progress /adminClassList.size() * 100);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (EncodeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
                progress++;
        }
        return ResultCodeVo.rightCode("保存成功");
    }

    // 年级与学年是否匹配
    public boolean isGradeMatch(String gradeCode, String studyYearCode, Integer entryYear) {
        Integer minus = Integer.parseInt(studyYearCode) - entryYear;// 入学多少年
        String[] grade1 = { "100", "201", "301", "401" }; // 第一年入学
        String[] grade2 = { "101", "202", "302", "402" }; // 第二年入学
        String[] grade3 = { "102", "203", "303", "403" }; // 第三年入学
        String[] grade4 = { "103", "204" }; // 第四年入学
        String[] grade5 = { "205" };// 第五年入学
        String[] grade6 = { "206" };// 第六年入学
        switch (minus) {
            case 0:
                return Arrays.asList(grade1).contains(gradeCode);
            case 1:
                return Arrays.asList(grade2).contains(gradeCode);
            case 2:
                return Arrays.asList(grade3).contains(gradeCode);
            case 3:
                return Arrays.asList(grade4).contains(gradeCode);
            case 4:
                return Arrays.asList(grade5).contains(gradeCode);
            case 5:
                return Arrays.asList(grade6).contains(gradeCode);
            default:
                return false;
        }
    }

    // 平台学年是否正确
    public boolean isCurrenPlatfromYearRight(String studyYearCode) {
        if (StringUtils.isEmpty(studyYearCode)) {
            return false;
        }
        Integer currentYear = TimeUtils.getYear();
        Integer currentMonth = TimeUtils.getMonth();
        Integer platYear = Integer.parseInt(studyYearCode);
        // 当前学年等于当前年份的情况下必须是上学期也就是8月以后 ，当前学年小于当前年份是下学期 就是8月份之前
        if ((currentMonth >= 8 && currentYear.equals(platYear))
                        || (currentMonth <= 8 && (currentYear - platYear == 1))) {
            return true;
        }
        return false;
    }



}
