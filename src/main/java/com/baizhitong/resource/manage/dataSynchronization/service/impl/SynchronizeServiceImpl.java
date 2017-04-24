package com.baizhitong.resource.manage.dataSynchronization.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.weaver.ast.Var;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.baizhitong.encrypt.MD5;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.ApiUtils;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.dao.demo.DemoDao;
import com.baizhitong.resource.dao.share.ShareAdminClassStudentDao;
import com.baizhitong.resource.dao.share.ShareAdminClassSubjectDao;
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.dao.share.ShareOrgSectionDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassStudentDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassSubjectDao;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.dao.share.ShareUserStudentDao;
import com.baizhitong.resource.dao.share.ShareUserTeacherDao;
import com.baizhitong.resource.manage.dataSynchronization.service.ISynchronizeService;
import com.baizhitong.resource.manage.login.service.LoginService;
import com.baizhitong.resource.manage.shareTeachingClass.service.IShareTeachingClassSubjectService;
import com.baizhitong.resource.manage.student.service.IStudentService;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.resource.model.company.ShareOrgSection;
import com.baizhitong.resource.model.share.ShareAdminClassStudent;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;
import com.baizhitong.resource.model.share.ShareCodeGrade;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.ShareTeachingClass;
import com.baizhitong.resource.model.share.ShareTeachingClassSubject;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserStudent;
import com.baizhitong.resource.model.share.ShareUserTeacher;
import com.baizhitong.resource.model.vo.login.LoginUserVo;
import com.baizhitong.resource.model.vo.share.ShareTeachingClassSubjectVo;
import com.baizhitong.resource.model.vo.share.ShareUserLoginVo;
import com.baizhitong.resource.model.vo.share.ShareUserStudentVo;
import com.baizhitong.resource.model.vo.share.ShareUserTeacherVo;
import com.baizhitong.resource.model.vo.temp.StudentTempVo;
import com.baizhitong.resource.model.vo.temp.adminClassVo;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 同步接口 SynchronizeServiceImpl TODO
 * 
 * @author creator BZT 2016年8月30日 下午2:59:24
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class SynchronizeServiceImpl extends BaseService implements ISynchronizeService {
    @Autowired
    ShareUserLoginDao                       shareUserLoginDao;                                                   // 登录用户数据接口
    @Autowired
    LoginService                            loginServie;
    @Autowired
    ITeacherService                         teacherService;
    @Autowired
    IStudentService                         studentService;
    @Autowired
    ShareUserTeacherDao                     shareUserTeacherDao;                                                 // 老师用户数据接口
    @Autowired
    ShareUserStudentDao                     shareUserStudentDao;                                                 // 学生用户数据接口
    @Autowired
    ShareOrgDao                             shareOrgDao;                                                         // 机构接口
    @Autowired
    ShareCodeGradeDao                       gradeDao;                                                            // 年级接口
    @Autowired
    ShareCodeSubjectDao                     subjectDao;                                                          // 学科接口
    @Autowired
    ShareAdminClassDao                      adminClassDao;                                                       // 行政班级接口
    @Autowired
    ISysCodeService                         sysCodeService;                                                      // 主键生成接口
    @Autowired
    ShareOrgSectionDao                      orgSectionDao;                                                       // 机构学段接口
    @Autowired
    SharePlatformDao                        sharePlatformDao;                                                    // 平台接口
    @Autowired
    ShareAdminClassSubjectDao               shareAdminClassSubjectDao;                                           // 行政班级学科接口
    @Autowired
    ShareTeachingClassDao                   shareTeachingClassDao;                                               // 教学班级接口
    // 教学班级学科接口
    @Autowired
    ShareTeachingClassSubjectDao            shareTeachingClassSubjectDao;
    // 教学班级学科接口
    @Autowired
    IShareTeachingClassSubjectService       shareTeachingClassSubjectService;

    @Autowired
    ShareAdminClassStudentDao               shareAdminClassStudentDao;                                           // 行政班级学科接口
    @Autowired
    ShareTeachingClassStudentDao            shareTeachingClassStudentDao;                                        // 课程班级学生接口
    @Autowired
    DemoDao                                 demoDao;

    private String                          ip;                                                                  // 操作用户ip
    private SharePlatform                   platform;                                                            // 平台
    private String                          orgCode;
    private String                          orgName;
    /*----------Map集合-------------*/
    private Map<String, ShareUserStudentVo> studentMapConstant;
    private Map<String, ShareUserTeacherVo> teacherMapConstant;
    private Map<String, LoginUserVo>        loginMapConstant;
    private Map<String, ShareAdminClass>    adminClassMapConstant;
    private Set<String>                     teachingClassCodeSet;
    /*----------本地数据list集合-------------*/
    private List<ShareUserStudentVo>        localStudentList;
    private List<ShareUserTeacherVo>        localTeacherList;
    private List<ShareOrgSection>           localOrgSections;
    private List<ShareCodeGrade>            localGradeList;
    private List<ShareCodeSubject>          localSubjectList;
    private List<ShareAdminClass>           localAdminClassList;
    private List<LoginUserVo>               localLoginList;
    private List<ShareAdminClassSubject>    localAdminClassSubjectList = new ArrayList<ShareAdminClassSubject>();

    /*----------待保存数据集合-------------*/
    // 新增集合
    private List<ShareUserStudent>          optStudentAddList;
    private List<ShareUserLogin>            optLoginAddList;
    // 更新集合
    private List<ShareUserStudentVo>        optStudentEditList;
    private List<ShareUserTeacher>          optTeacherEditList;
    private List<ShareUserLoginVo>          optLoginEditList;
    private List<ShareAdminClassStudent>    optAdminClassStudent;

    /**
     * 判断数据是否为空 ()<br>
     * 
     * @param list
     * @return
     */
    public boolean isListNotEmpty(List<?> list) {
        if (list == null || list.size() < 0)
            return false;
        return true;
    }

    /**
     * 数据同步 ()<br>
     * 
     * @return
     */
    public synchronized ResultCodeVo dataSynchronize(String orgCode, String orgName, String org_cdguid, Integer batch) {
        try {
            // 判断机构需不需要更新
            Map<String, Object> orgUpdateBatch = demoDao.getOrgUpdateBatch(org_cdguid);
            if (orgUpdateBatch == null) {
                demoDao.insertInite(org_cdguid, batch);// 初始化更新批次
            } else {
                Integer currentUpdateVer = MapUtils.getInteger(orgUpdateBatch, "updateVer");
                if (currentUpdateVer >= batch) {
                    log.info("该机构当前批次已经更新过 机构id：" + org_cdguid + "机构当前批次" + currentUpdateVer);
                    return ResultCodeVo.errorCode("该机构当前批次已经更新过 机构id：" + org_cdguid + "机构当前批次" + currentUpdateVer);
                }
            }
            localStudentList = new ArrayList<ShareUserStudentVo>();
            localTeacherList = new ArrayList<ShareUserTeacherVo>();
            localOrgSections = new ArrayList<ShareOrgSection>();
            localGradeList = new ArrayList<ShareCodeGrade>();
            localSubjectList = new ArrayList<ShareCodeSubject>();
            localAdminClassList = new ArrayList<ShareAdminClass>();
            localAdminClassSubjectList = new ArrayList<ShareAdminClassSubject>();
            localLoginList = new ArrayList<LoginUserVo>();

            studentMapConstant = new HashMap<String, ShareUserStudentVo>();// 把数据库的学生数据存在内存中
                                                                           // 避免频繁访问数据库
            teacherMapConstant = new HashMap<String, ShareUserTeacherVo>();// 把数据库的老师数据存在内存中
            loginMapConstant = new HashMap<String, LoginUserVo>();// 把数据库的登录信息数据存在内存中
            adminClassMapConstant = new HashMap<String, ShareAdminClass>();// 把数据库的行政班级数据存在内存中
            teachingClassCodeSet = new HashSet<String>();
            optAdminClassStudent = new ArrayList<ShareAdminClassStudent>();
            optLoginAddList = new ArrayList<ShareUserLogin>();
            optStudentAddList = new ArrayList<ShareUserStudent>();
            optStudentEditList = new ArrayList<ShareUserStudentVo>();
            optTeacherEditList = new ArrayList<ShareUserTeacher>();
            optLoginEditList = new ArrayList<ShareUserLoginVo>();
           
            this.ip = getIp();
            this.orgCode = orgCode;
            this.orgName = orgName;
            String sectionCode = "";
            this.localStudentList = studentService.getSimpleStudentList("");// 本地学生

            /********** 学生Map ***********/
            if (isListNotEmpty(localStudentList)) {
                for (ShareUserStudentVo student : localStudentList) {
                    if (StringUtils.isNotEmpty(student.getCd_guid())) {
                        studentMapConstant.put(student.getCd_guid(), student);
                    }
                }
            }
            log.info("------------------------------学生的数量----------------------" + studentMapConstant.size());
            this.localTeacherList = teacherService.getSimpleTeacherList("");// 本地老师
            /********** 老师Map ***********/
            if (isListNotEmpty(localTeacherList)) {
                for (ShareUserTeacherVo teacher : localTeacherList) {
                    if (StringUtils.isNotEmpty(teacher.getCd_guid())) {
                        teacherMapConstant.put(teacher.getCd_guid(), teacher);
                    }
                }
            }
            this.localOrgSections = orgSectionDao.getOrgSection(orgCode); // 机构学段
            // 所有年级
            List<ShareCodeGrade> gradeList = gradeDao.getAllGradeList();
            // 所有学科
            List<ShareCodeSubject> subjectList = subjectDao.getSubjectList(); // 所有学科;

            this.platform = sharePlatformDao.getByCodeGlobal(); // 平台

            if (platform == null) {
                log.error("****************平台信息没有数据****************");
                return ResultCodeVo.errorCode("平台信息没有数据");
            }

            this.localLoginList = loginServie.getLoginList(""); // 登录信息列表

            /********** 登录表Map ***********/
            if (isListNotEmpty(localLoginList)) {
                for (LoginUserVo login : localLoginList) {
                    if (StringUtils.isNotEmpty(login.getCd_guid())) {
                        loginMapConstant.put(login.getCd_guid(), login);
                    }
                }
            }

            this.localAdminClassSubjectList = shareAdminClassSubjectDao.getShareAdminClassList(orgCode);// 行政班级学科列表
            // 机构学段
            if (localOrgSections != null && localOrgSections.size() >= 1) {
                sectionCode = localOrgSections.get(0).getSectionCode();
            }
            // 获取机构学段学科
            if (subjectList != null && subjectList.size() > 0) {
                for (ShareCodeSubject subject : subjectList) {
                    if (subject.getCode().indexOf(sectionCode) == 0) {// 如果当前学段没有该学科就新增一条信息
                        this.localSubjectList.add(subject);
                    }
                }
            }
            // 筛选机构年级
            if (gradeList != null && gradeList.size() > 0) {
                for (ShareCodeGrade grade : gradeList) {
                    if (grade.getCode().indexOf(sectionCode) == 0) {
                        this.localGradeList.add(grade);
                    }
                }
            }
            this.localAdminClassList = adminClassDao.getAdminClassList(orgCode);// 行政班级列表
            adminClassMapConstant = new HashMap<String, ShareAdminClass>();
            /********** 行政班级Map ***********/
            if (isListNotEmpty(localAdminClassList)) {
                for (ShareAdminClass adminClass : localAdminClassList) {
                    if (StringUtils.isNotEmpty(adminClass.getCd_guid())) {
                        adminClassMapConstant.put(adminClass.getCd_guid(), adminClass);
                    }
                }
            }
            // 老师更新
            teacherSychronize(orgCode, org_cdguid);

            // 行政班级数据以及学生同步
            adminClassSychronize(org_cdguid, orgCode, sectionCode);
            /*************** 批处理 ****************/
            /*
             * ExeTeacherBatchSql teacherBatchSql=new ExeTeacherBatchSql(); ExeStudentBatchSql
             * studentBatchSql=new ExeStudentBatchSql(); ExeLoginBatchSql loginBatchSql=new
             * ExeLoginBatchSql(); Thread teaThread=new Thread(teacherBatchSql); Thread stuhread=new
             * Thread(studentBatchSql); Thread loginThread=new Thread(loginBatchSql);
             * teaThread.start(); stuhread.start(); loginThread.start(); teaThread.join();
             * stuhread.join(); loginThread.join();
             */

            // 刷新老师数据

            this.localTeacherList = teacherService.getSimpleTeacherList(orgCode);// 本地老师
            teacherMapConstant = new HashMap<String, ShareUserTeacherVo>();
            /********** 老师Map ***********/
            if (isListNotEmpty(localTeacherList)) {
                for (ShareUserTeacherVo teacher : localTeacherList) {
                    if (StringUtils.isNotEmpty(teacher.getCd_guid())) {
                        teacherMapConstant.put(teacher.getCd_guid(), teacher);
                    }
                }
            }

            // 行政班级学科同步
            adminClassSubejctSychronize(org_cdguid);

            // 更新老师的年级学科
            shareUserTeacherDao.updateSubjectGrade(orgCode, BeanHelper.getStudyYearCode());

            // 更新机构批次
            demoDao.updateOrgUpdateBatch(org_cdguid, batch);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultCodeVo.errorCode("失败", e);
        } finally {

            // 把数据清空防止内存占用过大
            localStudentList = null;
            localTeacherList = null;
            localOrgSections = null;
            localGradeList = null;
            localSubjectList = null;
            localAdminClassList = null;
            localAdminClassSubjectList = null;
            localLoginList = null;
            studentMapConstant = null;
            teacherMapConstant = null;
            loginMapConstant = null;
            adminClassMapConstant = null;
            teachingClassCodeSet = null;
            optAdminClassStudent = null;
            optLoginAddList = null;
            optLoginEditList = null;
            optStudentAddList = null;
            optStudentEditList = null;
            optTeacherEditList = null;
            System.gc();
        }
        return ResultCodeVo.rightCode("成功");
    }

    /**
     * 行政班级数据同步 ()<br>
     * 
     * @param org_cdguid
     * @param orgCode
     * @param sectionCode
     * @return
     */
    public ResultCodeVo adminClassSychronize(String org_cdguid, String orgCode, String sectionCode) {

        String adminClassListUrl = "/datahub/School/GetClassListBySchoolID/1.0.0?schoolID=" + org_cdguid;
        List<Map<String, Object>> adminClassList = ApiUtils.getResponseList(adminClassListUrl);
        if (adminClassList == null || adminClassList.size() < 0)
            return ResultCodeVo.rightCode("没有查询到园区行政班级数据");

        /*********** 毕业班级升级 *************/

        graduateUpdate(localAdminClassList, adminClassList);

        /*********** 毕业班级升级 *************/

        for (Map adminClassMap : adminClassList) {
            String guid = MapUtils.getString(adminClassMap, "bjid");
            Integer rxnf = MapUtils.getInteger(adminClassMap, "rxnf");
            String bjmc = MapUtils.getString(adminClassMap, "bjmc");
            String nj = MapUtils.getString(adminClassMap, "nj");
            String bzrid = MapUtils.getString(adminClassMap, "bzrid");
            ShareCodeGrade grade = getGradeByName(localGradeList, nj);
            String gradeCode = grade != null ? grade.getCode() : "mis";
            if (StringUtils.isEmpty(guid)) {
                log.error("!!!!!!!!!!!!!!!!!!!!!!!" + bjmc + "没有班级唯一码" + "****************************");
                continue;
            }
            ShareUserTeacherVo mainTeacher = getExitTeacher(bzrid);
            String headTeacherCode = mainTeacher == null ? "" : mainTeacher.getTeacherCode();
            if (rxnf == null) {
                rxnf = 9999;
            } else {
                rxnf = Integer.parseInt(rxnf.toString().substring(0, 4));
            }

            int count = adminClassDao.updateAdminClassByGUid(headTeacherCode, gradeCode, bjmc, rxnf, guid, ip);

            String adminClassCode = "";
            if (count <= 0) {
                ShareAdminClass adminClass = new ShareAdminClass();
                adminClassCode = sysCodeService.getNextAdminClassCode(orgCode, sectionCode, rxnf);
                adminClass.setOrgCode(orgCode);
                adminClass.setGid(UUID.randomUUID().toString());
                adminClass.setClassStatus(CoreConstants.CLASS_STATUS__NOT_GRADUATE);
                adminClass.setModifyPgm("SynchronizeServiceImplAdd");
                adminClass.setModifyIP(ip);
                adminClass.setModifyTime(new Timestamp(new Date().getTime()));
                adminClass.setSysVer(0);
                adminClass.setClassCode(adminClassCode);
                adminClass.setClassName(bjmc);
                adminClass.setEntryYear(rxnf);
                adminClass.setGradeCode(gradeCode);
                adminClass.setCd_guid(guid);
                adminClass.setHeadTeacherCode(headTeacherCode);
                adminClassMapConstant.put(guid, adminClass);
                // 保存班级
                adminClassDao.addOrUpdate(adminClass);
            } else {
                ShareAdminClass localsAdminClass = getAdminClass(guid);
                if (localsAdminClass == null) {
                    log.info("error!!!!!!!!!!!!!!!!!!!!!!!!!!!班级居然没找到" + guid);
                }
                adminClassCode = localsAdminClass.getClassCode();
                if (StringUtils.isEmpty(adminClassCode)) {
                    log.info("error!!!!!!!!!!!!!!!!!!!!!!!!!!!班级编码为空" + guid);
                }
            }

            // 学生数据同步
            studentSychronize(guid, adminClassCode);
        }
        // 刷新行政班级
        this.localAdminClassList = adminClassDao.getAdminClassList(orgCode);// 行政班级列表
        adminClassMapConstant = new HashMap<String, ShareAdminClass>();
        /********** 行政班级Map ***********/
        if (isListNotEmpty(localAdminClassList)) {
            for (ShareAdminClass adminClass : localAdminClassList) {
                if (StringUtils.isNotEmpty(adminClass.getCd_guid())) {
                    adminClassMapConstant.put(adminClass.getCd_guid(), adminClass);
                }
            }
        }

        /*********************** 开始保存数据 **************************************/
        ExecutorService executor = Executors.newCachedThreadPool();
        ArrayList<StudentUpdateHelper> callers = new ArrayList<StudentUpdateHelper>();
        // 批量处理
        int batchCount = 76, currentCount = 0;
        boolean continueOpt = true;
        log.info("-------------------------------开始批量新增线程----------------------！");
        while (continueOpt) {
            List<ShareUserStudent> helperStudentList = new ArrayList<ShareUserStudent>();
            List<ShareUserLogin> helperLoginList = new ArrayList<ShareUserLogin>();
            List<ShareAdminClassStudent> helperAdminClassStudentList = new ArrayList<ShareAdminClassStudent>();
            helperStudentList.addAll(optStudentAddList);
            helperLoginList.addAll(optLoginAddList);
            helperAdminClassStudentList.addAll(optAdminClassStudent);
            int end = currentCount + batchCount;
            List<ShareUserStudent> tempstudentList = null;
            List<ShareUserLogin> temploginList = null;
            List<ShareAdminClassStudent> tempadminClassStudentList = null;
            if (isListNotEmpty(helperStudentList) && currentCount < helperStudentList.size()) {
                int finalEnd = (end < helperStudentList.size()) ? end : helperStudentList.size();

                tempstudentList = helperStudentList.subList(currentCount, finalEnd);
            }
            if (isListNotEmpty(helperLoginList) && currentCount < helperLoginList.size()) {
                int finalEnd = (end < helperLoginList.size()) ? end : helperLoginList.size();
                temploginList = helperLoginList.subList(currentCount, finalEnd);
            }
            if (isListNotEmpty(helperAdminClassStudentList) && currentCount < helperAdminClassStudentList.size()) {
                int finalEnd = (end < helperAdminClassStudentList.size()) ? end : helperAdminClassStudentList.size();
                tempadminClassStudentList = helperAdminClassStudentList.subList(currentCount, finalEnd);
            }
            currentCount = end;
            StudentUpdateHelper helperThread = new StudentUpdateHelper(tempstudentList, temploginList,
                            tempadminClassStudentList);
            callers.add(helperThread);
            // 没有数据的时候跳出线程
            if (!isListNotEmpty(tempstudentList) && !isListNotEmpty(temploginList)
                            && !isListNotEmpty(tempadminClassStudentList)) {
                continueOpt = false;
            }
        }
        try {
            executor.invokeAll(callers);
            executor.shutdown();
            Thread.sleep(200);
            return ResultCodeVo.rightCode("行政班级和学生全部更新完毕");
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage(), e1);
            return ResultCodeVo.rightCode("更新中断" + e1.getMessage());
        }

        /*
         * while(true){ if (executor.isTerminated()) { return
         * ResultCodeVo.rightCode("行政班级和学生全部更新完毕"); } try { Thread.sleep(200); } catch
         * (InterruptedException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
         */
    }

    /**
     * 行政班级学生数据同步 ()<br>
     * 
     * @param bjdm
     * @param adminClassCode
     * @return
     */
    public ResultCodeVo studentSychronize(String bjdm, String adminClassCode) {
        // 园区学生数据
        String studentListUrl = "/datahub/student/getStudentList/1.0.0" + "?XXDM=&NJDM=&BJDM=&XSXM=&UPDATEDATA=&BJID="
                        + bjdm + "&PageIndex=1&PageSize=" + Integer.MAX_VALUE;
        List<Map<String, Object>> studentList = ApiUtils.getResponseList(studentListUrl);
        if (studentList == null || studentList.size() <= 0) {
            return ResultCodeVo.rightCode("没有查询到学生数据");
        }

        /************************ 业务处理 **************************************/
        for (Map<String, Object> studentMap : studentList) {
            String stuid = MapUtils.getString(studentMap, "id");
            /*
             * ShareUserStudent student = getExitStudent(localStudentList, stuid);
             */
            String sipGradeName = MapUtils.getString(studentMap, "njmc"); // 年级名称
            String sipBjdm = MapUtils.getString(studentMap, "bjdm"); // 班级代码
            String sipStudentNumber = MapUtils.getString(studentMap, "xsdm");// 学号
            String sipUserName = MapUtils.getString(studentMap, "xsxm"); // 学生姓名
            Integer sipxb = MapUtils.getInteger(studentMap, "xb"); // 性别
            String sipAvatarsImg = MapUtils.getString(studentMap, "zp"); // 照片
            String sipSfzh = MapUtils.getString(studentMap, "tysfzh"); // 身份证号码
            String dzyx = MapUtils.getString(studentMap, "dzyx"); // 电子邮箱
            Integer datastatus = MapUtils.getInteger(studentMap, "datastatus"); // 学生数据状态
            if (datastatus != null && datastatus != 0)
                continue;
            Integer xbm = sipxb;
            ShareCodeGrade grade = getGradeByName(localGradeList, sipGradeName);
            String gradeCode = grade != null ? grade.getCode() : "mis";
            if (StringUtils.isEmpty(stuid)) {
                log.error("!!!!!!!!!!!!!!!!!!!" + sipUserName + "没有学生唯一码" + "****************************");
                continue;
            }

            /****** 登录信息 ********/
            ShareUserLoginVo userLogin = new ShareUserLoginVo();
            userLogin.setLoginAccount(sipSfzh);
            userLogin.setOrgCode(this.orgCode);
            userLogin.setOrgName(this.orgName);
            userLogin.setGender(xbm);
            userLogin.setUserName(sipUserName);
            userLogin.setModifyIP(ip);
            userLogin.setModifyPgm("SynchronizeServiceImplEdit");
            userLogin.setModifyTime(new Timestamp(new Date().getTime()));
            userLogin.setUserRole(CoreConstants.USER_ROLE_STUDENT);
            userLogin.setStatus(1);
            userLogin.setMail(dzyx);
            userLogin.setAvatarsImg(sipAvatarsImg);
            userLogin.setCd_guid(stuid);
            LoginUserVo loginExit = getTheLoginUser(stuid);
            if (loginExit != null) {
                log.info("-********************************要更新的登录表信息是***********************-" + loginExit.getCd_guid()
                                + "姓名" + loginExit.getCd_guid() + "当前时间" + new Date());
                shareUserLoginDao.updateStudentLoginByCdguid(userLogin);
                // optLoginEditList.add(userLogin);
            } else {
                ShareUserLogin login = new ShareUserLogin();
                /****** 登录信息 ********/
                // 新增
                String userCode = sysCodeService.getCode("userCode");
                login.setLoginAccount(sipSfzh);
                login.setLoginPwd(MD5.calcMD5(CoreConstants.DEFAULT_STUDENT_PWD));
                login.setOrgCode(this.orgCode);
                login.setOrgName(this.orgName);
                login.setGid(UUID.randomUUID().toString());
                login.setUserName(sipUserName);
                login.setMail(dzyx);
                login.setModifyIP(ip);
                login.setGender(xbm);
                login.setModifyPgm("SynchronizeServiceImplAdd");
                login.setModifyTime(new Timestamp(new Date().getTime()));
                login.setUserRole(CoreConstants.USER_ROLE_STUDENT);
                login.setStatus(1);
                login.setUserCode(userCode);
                login.setAvatarsImg(sipAvatarsImg);
                login.setCd_guid(stuid);
                loginExit = LoginUserVo.EntityToVo(login);
                loginMapConstant.put(stuid, loginExit);
                optLoginAddList.add(login);
                // shareUserLoginDao.insertStudentShareUserLogin(login);
            }
            /****** 学生信息 ********/
            ShareUserStudentVo studentVo = new ShareUserStudentVo();
            studentVo.setOrgCode(this.orgCode);
            studentVo.setGradeCode(gradeCode);
            studentVo.setAdminClassCode(adminClassCode);
            studentVo.setStudentNumber(sipStudentNumber);
            studentVo.setUserName(sipUserName);
            studentVo.setGender(xbm);
            studentVo.setAvatarsImg(sipAvatarsImg);
            studentVo.setTermCode(BeanHelper.getTermCode());
            studentVo.setCd_guid(stuid);
            studentVo.setModifyPgm("SynchronizeServiceImplEdit");
            studentVo.setStudentCode(loginExit.getUserCode());
            ShareUserStudentVo studentExit = getExitStudent(stuid);
            if (studentExit != null) {
                shareUserStudentDao.updateStudentInfoByGuid(studentVo);
                // optStudentEditList.add(studentVo);
            } else {
                /****** 学生信息 ********/
                ShareUserStudent student = new ShareUserStudent();
                student.setAdminClassCode(adminClassCode);
                student.setGradeCode(gradeCode);
                student.setStudentNumber(sipStudentNumber);
                student.setUserName(sipUserName);
                student.setGender(xbm);
                student.setGid(UUID.randomUUID().toString());
                student.setModifyIP("127.0.0.1");
                student.setModifyPgm("SynchronizeServiceImplAdd");
                student.setModifyTime(new Timestamp(new Date().getTime()));
                student.setOrgCode(this.orgCode);
                student.setStatus(1);
                student.setStudentCode(loginExit.getUserCode());
                student.setSysVer(0);
                student.setTermCode(BeanHelper.getTermCode());
                student.setCd_guid(stuid);
                optStudentAddList.add(student);
                // shareUserStudentDao.addStudent(student);
                studentExit = ShareUserStudentVo.EntityToVo(student);
                studentMapConstant.put(stuid, studentExit);
            }
            // 更新行政班级学生
            // 修改班级
            log.info("更新行政班级学生 guid=" + stuid + "行政班级编码=" + adminClassCode + "学生编码" + loginExit.getUserCode());
            int count = shareAdminClassStudentDao.updateShareAdminClassStudentByStuGuid(stuid, sipUserName,
                            loginExit.getUserCode(), adminClassCode, gradeCode, "SynchronizeServiceImplEdit");
            if (count <= 0) {
                /** 添加行政班级学生关系 */
                ShareAdminClassStudent adminClassStudent = new ShareAdminClassStudent();
                adminClassStudent.setOrgCode(orgCode);
                adminClassStudent.setGradeCode(gradeCode);
                adminClassStudent.setAdminClassCode(adminClassCode);
                adminClassStudent.setStudentName(sipUserName);
                adminClassStudent.setRoleInGroup(0);
                adminClassStudent.setDispOrder(null);
                adminClassStudent.setMemo("");
                adminClassStudent.setModifyPgm("SynchronizeServiceImplAdd");
                adminClassStudent.setModifyTime(new Timestamp(new Date().getTime()));
                adminClassStudent.setModifyIP(ip);
                adminClassStudent.setSysVer(1);
                adminClassStudent.setCd_guid(studentExit.getCd_guid());
                adminClassStudent.setStudentCode(loginExit.getUserCode());
                adminClassStudent.setGid(UUID.randomUUID().toString());
                optAdminClassStudent.add(adminClassStudent);
                // shareAdminClassStudentDao.saveShareAdminClassStudent(adminClassStudent);
            }
        }
        log.info(bjdm + "班级学生更新完毕");
        return null;
    }

    /**
     * 老师数据同步 ()<br>
     * 
     * @param orgCode
     * @param xxguid
     * @return
     */
    public ResultCodeVo teacherSychronize(String orgCode, String xxguid) {

        // 园区数据
        String teacherListUrl = "/datahub/teacher/getTeacherList/1.0.0?UpdateData=&XM=&SFZH=&SSDW=" + xxguid
                        + "&XXDM=&PageSize=" + Integer.MAX_VALUE + "&PageIndex=1";
        List<Map<String, Object>> teacherList = ApiUtils.getResponseList(teacherListUrl);
        if (teacherList == null || teacherList.size() <= 0)
            return ResultCodeVo.rightCode("没有查询到老师数据");
        List<ShareUserTeacher> optTeacherAddList = new ArrayList<ShareUserTeacher>();
        List<ShareUserLogin> optLoginAddList = new ArrayList<ShareUserLogin>();

        /************************ 业务处理 **************************************/
        for (Map<String, Object> teacherMap : teacherList) {
            String lsid = MapUtils.getString(teacherMap, "lsid");
            String xm = MapUtils.getString(teacherMap, "xm"); // 姓名
            Integer xbm = MapUtils.getInteger(teacherMap, "xbm"); // 性别
            String xddm = MapUtils.getString(teacherMap, "xddm"); // 学段代码
            String xkdm = MapUtils.getString(teacherMap, "xkdm"); // 学科代码
            String gh = MapUtils.getString(teacherMap, "gh"); // 工号
            String yddh = MapUtils.getString(teacherMap, "yddh"); // 移动电话
            String dzyx = MapUtils.getString(teacherMap, "dzyx"); // 电子邮箱
            String zp = MapUtils.getString(teacherMap, "zp"); // 照片
            String yzh = MapUtils.getString(teacherMap, "yzh"); // 域账好
            Integer datastatus = MapUtils.getInteger(teacherMap, "datastatus"); // 老师数据状态
            if (datastatus != 0)
                continue;
            ShareCodeSubject subject = getSubject(localSubjectList, xkdm);
            String subjectCode = "";
            if (subject == null) {
                subjectCode = "---";
                log.error("学科没找到啊------------" + xkdm);
            } else {
                subjectCode = subject.getCode();
            }
            if (StringUtils.isEmpty(lsid)) {
                log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + xm + "没有教师唯一码" + "****************************");
                continue;
            }

            /****** 更细登录信息 ********/
            LoginUserVo loginExit = getTheLoginUser(lsid);
            if (loginExit != null) {
                ShareUserLoginVo userLogin = new ShareUserLoginVo();
                userLogin.setLoginAccount(yzh);
                userLogin.setOrgCode(orgCode);
                userLogin.setOrgName(this.orgName);
                userLogin.setGender(xbm);
                userLogin.setUserName(xm);
                userLogin.setMobilePhone(yddh);
                userLogin.setModifyIP(ip);
                userLogin.setModifyPgm("SynchronizeServiceImplEdit");
                userLogin.setModifyTime(new Timestamp(new Date().getTime()));
                userLogin.setUserRole(CoreConstants.USER_ROLE_TEACHER);
                userLogin.setStatus(1);
                userLogin.setAvatarsImg(zp);
                userLogin.setMail(dzyx);
                userLogin.setCd_guid(lsid);
                // optLoginEditList.add(userLogin);
                shareUserLoginDao.updateTeacherLoginByCdguid(userLogin);

            } else {
                /****** 新增登录信息 ********/
                ShareUserLogin login = new ShareUserLogin();
                String teacherCode = sysCodeService.getCode("userCode");
                login.setLoginAccount(yzh);
                login.setLoginPwd(MD5.calcMD5(CoreConstants.DEFAULT_TEACHER_PWD));
                login.setOrgCode(orgCode);
                login.setOrgName(this.orgName);
                login.setUserCode(yddh);
                login.setGid(UUID.randomUUID().toString());
                login.setUserName(xm);
                login.setUserCode(teacherCode);
                login.setMail(dzyx);
                login.setModifyIP(ip);
                login.setModifyPgm("SynchronizeServiceImplAdd");
                login.setModifyTime(new Timestamp(new Date().getTime()));
                login.setUserRole(CoreConstants.USER_ROLE_TEACHER);
                login.setStatus(1);
                login.setAvatarsImg(zp);
                login.setCd_guid(lsid);
                loginExit = LoginUserVo.EntityToVo(login);
                loginMapConstant.put(lsid, loginExit);
                optLoginAddList.add(login);
                // shareUserLoginDao.insertTeacherShareUserLogin(login);
            }
            // 修改
            /****** 更细老师信息 ********/
            ShareUserTeacherVo teacherExit = getExitTeacher(lsid);
            if (teacherExit != null) {
                ShareUserTeacher teacher = new ShareUserTeacher();
                teacher.setUserName(xm);
                teacher.setGender(xbm);
                teacher.setModifyIP(ip);
                teacher.setModifyPgm("SynchronizeServiceImplEdit");
                teacher.setModifyTime(new Timestamp(new Date().getTime()));
                teacher.setOrgCode(orgCode);
                teacher.setStatus(1);
                teacher.setSysVer(0);
                teacher.setTeacherCode(loginExit.getUserCode());
                teacher.setGid(UUID.randomUUID().toString());
                teacher.setSubjectCode(subjectCode);
                teacher.setSectionCode(xddm);
                teacher.setWorkNo(gh);
                teacher.setTermCode(BeanHelper.getTermCode());
                teacher.setCd_guid(lsid);
                // optTeacherEditList.add(teacher);
                shareUserTeacherDao.updateTeacherByGuid(teacher);
            } else {
                ShareUserTeacher teacher = new ShareUserTeacher();
                /****** 新增老师信息 ********/
                teacher.setUserName(xm);
                teacher.setGender(xbm);
                teacher.setGid(UUID.randomUUID().toString());
                teacher.setModifyIP(ip);
                teacher.setModifyPgm("SynchronizeServiceImplAdd");
                teacher.setModifyTime(new Timestamp(new Date().getTime()));
                teacher.setOrgCode(orgCode);
                teacher.setStatus(1);
                teacher.setSysVer(0);
                teacher.setGid(UUID.randomUUID().toString());
                teacher.setSubjectCode(subjectCode);
                teacher.setSectionCode(xddm);
                teacher.setWorkNo(gh);
                teacher.setCd_guid(lsid);
                teacher.setTeacherCode(loginExit.getUserCode());
                teacher.setTermCode(BeanHelper.getTermCode());
                teacherExit = ShareUserTeacherVo.EntityToVo(teacher);
                teacherMapConstant.put(lsid, teacherExit);
                optTeacherAddList.add(teacher);
                // shareUserTeacherDao.saveOrgUpdate(teacher);
            }
            if (teacherExit == null) {
                log.info("----------------------------------------" + lsid);
            }
        }

        if (isListNotEmpty(optTeacherAddList)) {
            boolean continueOpt = true;
            int start = 0;
            int end = 50;
            while (continueOpt) {
                if (end >= optTeacherAddList.size()) {
                    continueOpt = false;
                    end = optTeacherAddList.size();
                }
                shareUserTeacherDao.saveTeacherList(optTeacherAddList.subList(start, end));
                start = end;
                end = end + 50;

            }
        }

        if (isListNotEmpty(optLoginAddList)) {
            boolean continueOpt = true;
            int start = 0;
            int end = 50;
            while (continueOpt) {
                if (end > optLoginAddList.size()) {
                    continueOpt = false;
                    end = optLoginAddList.size();
                }
                shareUserLoginDao.addLonginUserList(optLoginAddList.subList(start, end));
                start = end;
                end = end + 50;
            }
        }
        return ResultCodeVo.rightCode("成功");

    }

    /**
     * 行政班级学科数据同步 ()<br>
     * 行政班级学科 同班级同学科的不同老师修改 并插入历史数据表 ，教学班级不存在情况才会新增不做修改
     * 
     * @param xxguid
     * @return
     */
    public ResultCodeVo adminClassSubejctSychronize(String xxguid) {
        List<ShareAdminClassSubject> adminClassSubjectList = new ArrayList<ShareAdminClassSubject>();
        List<ShareTeachingClassSubject> teachingClassSubjectList = new ArrayList<ShareTeachingClassSubject>();
        List<ShareTeachingClass> teachingClassList = new ArrayList<ShareTeachingClass>();
        // 机构所有行政班级学科
        // 新增
        String rkListUrl = "/datahub/teacher/getTeacherRkList/1.0.0?SchoolGUID=" + xxguid + "&TeacherGUID=";
        List<Map<String, Object>> teacherRkList = ApiUtils.getResponseList(rkListUrl);
        if (isListNotEmpty(teacherRkList)) {
            for (Map<String, Object> map : teacherRkList) {
                String bjguid = MapUtils.getString(map, "bjguid");// 班级唯一码
                String lsid = MapUtils.getString(map, "lsguid");// 老师唯一码
                String adminClassXkbm = MapUtils.getString(map, "xkbm"); // 学科编码
                ShareAdminClass adminClass = getAdminClass(bjguid);// 行政班级
                ShareUserTeacherVo teacherExit = getExitTeacher(lsid);

                if (teacherExit == null) {
                    log.info("！！！！！！！！！！！！！！行政班级学科没有找到老师：" + lsid + "班级id" + bjguid + "学科" + adminClassXkbm);
                    continue;
                }
                if (adminClass == null) {
                    log.info("！！！！！！！！！！！！！！行政班级学科没有找到班级" + bjguid + "学科" + adminClassXkbm);
                    continue;
                }
                /*--------准备数据-----------*/
                String adminClassCode = adminClass.getClassCode(); // 行政班级编码
                String adminClassName = adminClass.getClassName(); // 行政班级名称
                String classBeginTime = MapUtils.getString(map, "skksrq"); // 学科班级开始时间
                String classEndTime = MapUtils.getString(map, "skjsrq"); // 学科班级结束时间
                String gradeCode = adminClass.getGradeCode();
                if (StringUtils.isEmpty(gradeCode)) {
                    gradeCode = "mis";
                }

                ShareCodeSubject adminClasssubject = getSubject(localSubjectList, adminClassXkbm);// 行政班级学科

                String subjectCode = adminClasssubject != null ? adminClasssubject.getCode() : "mis";// 课程班级编码
                if ("mis".equals(subjectCode)) {
                    log.info("！！！！！！！！！！！！行政班级学科没有找到学科" + adminClassXkbm + ",班级id" + bjguid + "老师：" + lsid);
                    continue;
                }
                String teachingClassCode = adminClassCode + subjectCode + gradeCode; // 课程班级编码
                Timestamp beginTime = null;
                Timestamp endTime = null;

                if (StringUtils.isNotEmpty(classBeginTime)) {
                    beginTime = new Timestamp(DateUtils.getDateTime(classBeginTime, "yyyy/MM/dd").getTime());
                }
                if (StringUtils.isNotEmpty(classEndTime)) {
                    endTime = new Timestamp(DateUtils.getDateTime(classEndTime, "yyyy/MM/dd").getTime());
                }
                /*-----------------课程班级-----------------*/
                ShareTeachingClass teachingClass = new ShareTeachingClass();
                teachingClass.setAdminClassCode(adminClassCode);
                teachingClass.setTeachingClassName(adminClassName);
                teachingClass.setStartTime(beginTime);
                teachingClass.setEndTime(endTime);
                teachingClass.setGradeCode(gradeCode);
                teachingClass.setStudyYearCode(BeanHelper.getStudyYearCode());
                teachingClass.setMemo("");
                teachingClass.setOrgCode(orgCode);
                teachingClass.setSubjectCode(subjectCode);
                teachingClass.setTeachingClassCode(teachingClassCode);
                teachingClass.setGid(UUID.randomUUID().toString());
                teachingClass.setModifyIP("127.0.0.1");
                teachingClass.setModifyPgm("SynchronizeServiceImpl");
                teachingClass.setModifyTime(new Timestamp(new Date().getTime()));
                teachingClass.setOrgCode(orgCode);
                teachingClass.setSysVer(1);

                /*-----------------课程班级学科表-----------------*/
                ShareTeachingClassSubject classSubject = new ShareTeachingClassSubject();
                classSubject.setTeacherName(teacherExit.getUserName());
                classSubject.setTeacherRole("0");
                classSubject.setTeacherCode(teacherExit.getTeacherCode());
                classSubject.setGid(UUID.randomUUID().toString());
                classSubject.setOrgCode(orgCode);
                classSubject.setSubjectCode(subjectCode);
                classSubject.setGradeCode(gradeCode);
                classSubject.setTeachingClassCode(teachingClassCode);
                classSubject.setBeginTime(beginTime);
                classSubject.setEndTime(endTime);
                classSubject.setStudyYearCode(BeanHelper.getStudyYearCode());
                classSubject.setModifyIP("127.0.0.1");
                classSubject.setTermCode(BeanHelper.getTermCode());
                classSubject.setModifyPgm("SynchronizeServiceImplAdd");
                classSubject.setModifyTime(new Timestamp(new Date().getTime()));
                classSubject.setSysVer(1);

                /*-----------------行政班级学科表-----------------*/
                ShareAdminClassSubject adminClassSubject = new ShareAdminClassSubject();
                adminClassSubject.setAdminClassCode(adminClassCode);
                adminClassSubject.setGid(UUID.randomUUID().toString());
                adminClassSubject.setGradeCode(gradeCode);
                adminClassSubject.setMemo("");
                adminClassSubject.setModifyIP("127.0.0.1");
                adminClassSubject.setModifyPgm("SynchronizeServiceImplAdd");
                adminClassSubject.setModifyTime(new Timestamp(new Date().getTime()));
                adminClassSubject.setOrgCode(orgCode);
                adminClassSubject.setSubjectCode(subjectCode);// 学科编码
                adminClassSubject.setSysVer(1);
                adminClassSubject.setTeacherCode(teacherExit.getTeacherCode());
                adminClassSubject.setTeacherName(teacherExit.getUserName());
                adminClassSubject.setTeacherRole(0);
                if (StringUtils.isNotEmpty(classBeginTime)) {
                    adminClassSubject.setBeginTime(beginTime);
                }
                if (StringUtils.isNotEmpty(classEndTime)) {
                    adminClassSubject.setEndTime(endTime);
                }
                // 教学班级是否存在
                ShareTeachingClass exitTeachingClass = shareTeachingClassDao.getClassByCode(teachingClassCode);
                if (exitTeachingClass == null) {
                    // set表防止园区的数据有重复的
                    if (!teachingClassCodeSet.contains(teachingClass.getTeachingClassCode())) {
                        teachingClassList.add(teachingClass);
                    }
                    teachingClassCodeSet.add(teachingClass.getTeachingClassCode());
                }

                /*--------如果班级学科存在则不新增-----------*/
                ShareAdminClassSubject adminClassSubjectExit = getAdminClassSubject(adminClassCode, gradeCode,
                                subjectCode);
                if (adminClassSubjectExit == null) {
                    localAdminClassSubjectList.add(adminClassSubject);
                    adminClassSubjectList.add(adminClassSubject);
                } else {
                    int count = shareAdminClassSubjectDao.updateAdminClassSubject(adminClassCode, subjectCode,
                                    gradeCode, teacherExit.getTeacherCode(), teacherExit.getUserName(),
                                    "SynchronizeServiceImplEdit");
                    if (count > 0) { // 行政班级学科的老师发生了改变把修改前的班级学科插入历史表
                        shareAdminClassSubjectDao.insertAdminClassSubjectToHistory(adminClassSubjectExit, ip);
                    }
                }

                // 更新课程班级学科

                List<ShareTeachingClassSubjectVo> tSubjectLists = shareTeachingClassSubjectService
                                .getShareTeachingClassSubjectList(teachingClassCode, subjectCode, gradeCode,
                                                teacherExit.getTeacherCode(), gradeCode, BeanHelper.getStudyYearCode(),
                                                "");
                /*
                 * int count = shareTeachingClassSubjectDao.updateClassTeacher(teachingClassCode,
                 * subjectCode, gradeCode, teacherExit.getTeacherCode(), teacherExit.getUserName(),
                 * "SynchronizeServiceImplEdit"); if (count <= 0) { // 一个班级只能一个老师教 if
                 * (!teachingClassSubjectSet.contains(classSubject.getTeachingClassCode())) {
                 * teachingClassSubjectList.add(classSubject); }
                 * teachingClassSubjectSet.add(classSubject.getTeachingClassCode()); }
                 */
                // 班级学科不存在的情况下才新增
                if (tSubjectLists == null || tSubjectLists.size() <= 0) {
                    teachingClassSubjectList.add(classSubject);
                }

            }
            /**************** 课程班级学生 ******************/
            if (teachingClassList != null && teachingClassList.size() > 0) {
                for (ShareTeachingClass teachingClass : teachingClassList) {
                    shareTeachingClassStudentDao.addShareTeachingClassStudentDataSynchronize(teachingClass);

                }
            }
            /**************** 行政班级学科 ******************/

            if (isListNotEmpty(adminClassSubjectList)) {
                boolean continueOpt = true;
                int start = 0;
                int end = 100;
                while (continueOpt) {
                    if (end > adminClassSubjectList.size()) {
                        continueOpt = false;
                        end = adminClassSubjectList.size();
                    }
                    shareAdminClassSubjectDao.saveShareAdminClassSubject(adminClassSubjectList.subList(start, end));
                    start = end;
                    end = end + 100;
                }
            }
            /*
             * while (adminClassSubjectList.size() > 0) { List<ShareAdminClassSubject>
             * adminClassTempList = new ArrayList<ShareAdminClassSubject>(); int stepCount =
             * adminClassSubjectList.size() >= 100 ? 100 : adminClassSubjectList.size(); for (int i
             * = 0; i < stepCount; i++) { adminClassTempList.add(adminClassSubjectList.get(i)); }
             * shareAdminClassSubjectDao.saveShareAdminClassSubject(adminClassTempList);
             * adminClassSubjectList.removeAll(adminClassTempList); }
             */

            /**************** 课程班级 ******************/
            if (isListNotEmpty(teachingClassList)) {
                boolean continueOpt = true;
                int start = 0;
                int end = 100;
                while (continueOpt) {
                    if (end > teachingClassList.size()) {
                        continueOpt = false;
                        end = teachingClassList.size();
                    }
                    shareTeachingClassDao.saveShareTeachingClassList(teachingClassList.subList(start, end));
                    start = end;
                    end = end + 100;
                }
            }
            /*
             * while (teachingClassList.size() > 0) { List<ShareTeachingClass> teachingClasstempList
             * = new ArrayList<ShareTeachingClass>(); int stepCount = teachingClassList.size() >=
             * 100 ? 100 : teachingClassList.size(); for (int i = 0; i < stepCount; i++) {
             * teachingClasstempList.add(teachingClassList.get(i)); }
             * shareTeachingClassDao.saveShareTeachingClassList(teachingClasstempList);
             * teachingClassList.removeAll(teachingClasstempList); }
             */

            /**************** 课程班级学科 ******************/
            if (isListNotEmpty(teachingClassSubjectList)) {
                boolean continueOpt = true;
                int start = 0;
                int end = 100;
                while (continueOpt) {
                    if (end > teachingClassSubjectList.size()) {
                        continueOpt = false;
                        end = teachingClassSubjectList.size();
                    }
                    shareTeachingClassSubjectDao.saveTeacherClassSubject(teachingClassSubjectList.subList(start, end));
                    start = end;
                    end = end + 100;
                }
            }
            /*
             * while (teachingClassSubjectList.size() > 0) { List<ShareTeachingClassSubject>
             * tempList = new ArrayList<ShareTeachingClassSubject>(); int stepCount =
             * teachingClassSubjectList.size() >= 100 ? 100 : teachingClassSubjectList.size(); for
             * (int i = 0; i < stepCount; i++) { tempList.add(teachingClassSubjectList.get(i)); }
             * shareTeachingClassSubjectDao.saveTeacherClassSubject(tempList);
             * teachingClassSubjectList.removeAll(tempList); }
             */
        }
        log.info("--------------------教师班级学科保存完毕----------------------");
        return ResultCodeVo.rightCode("老师学科弄好");
    }

    /**
     * 查询老师是否存在 ()<br>
     * 
     * @param teacherList
     * @param cd_guid
     * @return
     */
    public ShareUserTeacherVo getExitTeacher(String cd_guid) {
        if (StringUtils.isEmpty(cd_guid))
            return null;
        if (teacherMapConstant == null)
            return null;
        return teacherMapConstant.get(cd_guid);
    }

    /**
     * 用户登录信息 ()<br>
     * 
     * @param loginList
     * @param userCode
     * @return
     */
    public LoginUserVo getTheLoginUser(String cd_guid) {
        if (StringUtils.isEmpty(cd_guid))
            return null;
        if (loginMapConstant == null) {
            return null;
        }
        return loginMapConstant.get(cd_guid);
    }

    /**
     * 查询学生是否存在 ()<br>
     * 
     * @param studentList
     * @param cd_guid
     * @return
     */
    public ShareUserStudentVo getExitStudent(String cd_guid) {
        if (StringUtils.isEmpty(cd_guid))
            return null;
        if (studentMapConstant == null)
            return null;
        return studentMapConstant.get(cd_guid);
    }

    /**
     * 查询行政班级学科 ()<br>
     * 
     * @param adminClassSubjectList
     * @param adminClassCode 行政班级编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @return
     */
    public ShareAdminClassSubject getAdminClassSubject(String adminClassCode, String gradeCode, String subjectCode) {
        if (localAdminClassSubjectList == null || localAdminClassSubjectList.size() <= 0)
            return null;
        for (ShareAdminClassSubject shareAdminClassSubject : localAdminClassSubjectList) {
            if (shareAdminClassSubject.getAdminClassCode().equals(adminClassCode)
                            && shareAdminClassSubject.getGradeCode().equals(gradeCode)
                            && shareAdminClassSubject.getSubjectCode().equals(subjectCode)) {
                return shareAdminClassSubject;
            }
        }
        return null;
    }

    /**
     * 查询机构 ()<br>
     * 
     * @param orgList
     * @param cd_guid
     * @return
     */
    public ShareOrg getExitOrg(List<ShareOrg> orgList, String cd_guid) {
        if (StringUtils.isEmpty(cd_guid))
            return null;
        if (orgList == null || orgList.size() < 0)
            return null;
        for (ShareOrg org : orgList) {
            if (cd_guid.equals(org.getCd_guid())) {
                return org;
            }
        }
        return null;
    }

    /**
     * 通过ioCode查询年级 ()<br>
     * 
     * @param gradeList
     * @param ioCode
     * @return
     */
    public ShareCodeGrade getGrade(List<ShareCodeGrade> gradeList, String ioCode) {
        if (StringUtils.isEmpty(ioCode))
            return null;
        if (gradeList == null && gradeList.size() < 0)
            return null;
        for (ShareCodeGrade grade : gradeList) {
            if (ioCode.equals(grade.getIoCode())) {
                return grade;
            }
        }
        return null;
    }

    /**
     * 通过年级名称查询年级 ()<br>
     * 
     * @param gradeList
     * @param gradeName
     * @return
     */
    public ShareCodeGrade getGradeByName(List<ShareCodeGrade> gradeList, String gradeName) {

        if (StringUtils.isEmpty(gradeName))
            return null;
        if (gradeName.equals("七年级")) {
            gradeName = "初一";
        }
        if (gradeName.equals("八年级")) {
            gradeName = "初二";
        }
        if (gradeName.equals("九年级")) {
            gradeName = "初三";
        }
        if (gradeList == null && gradeList.size() < 0)
            return null;
        for (ShareCodeGrade grade : gradeList) {
            if (gradeName.equals(grade.getName())) {
                return grade;
            }
        }
        return null;
    }

    /**
     * 查询学科 ()<br>
     * 
     * @param subjectList
     * @param ioCode
     * @return
     */
    public ShareCodeSubject getSubject(List<ShareCodeSubject> subjectList, String ioCode) {
        if (StringUtils.isEmpty(ioCode))
            return null;
        if (subjectList == null && subjectList.size() < 0)
            return null;
        for (ShareCodeSubject subject : subjectList) {
            if (ioCode.equals(subject.getIoCode())) {
                return subject;
            }
        }
        return null;
    }

    /**
     * 行政班级 ()<br>
     * 
     * @param adminClassList
     * @param cd_guid
     * @return
     */
    public ShareAdminClass getAdminClass(String cd_guid) {
        if (StringUtils.isEmpty(cd_guid))
            return null;
        if (adminClassMapConstant == null)
            return null;
        return adminClassMapConstant.get(cd_guid);
    }

    @Test
    public void getGradeList() {
        /*
         * List<Integer> aList=new ArrayList<Integer>(); aList.add(1); aList.add(2); aList.add(3);
         * List<Integer> newlIST=aList.subList(0, 2);
         */
        /********** 学生Map ***********/

        // 新增
        String list[] = new String[4];
        String funny = "/datahub/baseCode/getBaseCodeList/1.0.0?DMLX=CourseType&FromDate=&ToDate=";
        String rkListUrl = "/datahub/teacher/getTeacherRkList/1.0.0?SchoolGUID=E5826CB6E73348EAA6DB341E7555087E&TeacherGUID=";
        String teacherurl = "/datahub/teacher/getTeacherList/1.0.0?XM&SFZH&SSDW&XXDM=&UpdateData&PageSize="
                        + Integer.MAX_VALUE + "&PageIndex=1";
        String studenturl = "/datahub/student/getStudentList/1.0.0?UPDATEDATA=&XXDM=&NJDM=&BJDM=&BJID=&XSXM=&PageSize="
                        + Integer.MAX_VALUE + "&PageIndex=1";
        String adminClassListUrl = "/datahub/School/GetClassListBySchoolID/1.0.0?schoolID=A4627C2ACCAE4FE39EC0E9DBD3B0BF15";
        /*
         * String studentListUrl = "/datahub/student/getStudentList/1.0.0" +
         * "?XXDM=&NJDM=&BJDM=&XSXM=&UPDATEDATA=&BJID=B73BA9FC232F4AFA86EC721E86E15CC0&PageIndex=1&PageSize="
         * + Integer.MAX_VALUE;
         */
        String studentListUrl = "/datahub/student/getStudentList/1.0.0"
                        + "?XXDM=&NJDM=&BJDM=&XSXM=&UPDATEDATA=&BJID=&PageIndex=1&PageSize=" + 1000;
        String teacherListUrl = "/datahub/teacher/getTeacherList/1.0.0?UpdateData=&XM=&SFZH=&SSDW=E5826CB6E73348EAA6DB341E7555087E"
                        + "&XXDM=&PageSize=" + Integer.MAX_VALUE + "&PageIndex=1";
        String subjectUrl = "/datahub/baseCode/getBaseCodeList/1.0.0?DMLX=XKBM";
        list[0] = funny;
        list[1] = funny;
        for (int i = 1; i < 100; i++) {
            String url = list[1];
            if (i % 2 == 0) {
                url = list[0];
            }
            List<Map<String, Object>> teacherList = ApiUtils.getResponseList(url);
            if (isListNotEmpty(teacherList)) {
                if (i % 2 == 0) {
                    log.info("Jiaoshi大小" + teacherList.size());
                } else {
                    log.info("banji大小" + teacherList.size());
                }

            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            log.info("----------------------" + i + "---------------------------");
            if (teacherList == null) {
                Integer ss = 111;
            }
        }

    }

    /**
     * 毕业升级 ()<br>
     * 
     * @param adminClassList
     * @param sipAdminClassList
     */
    public void graduateUpdate(List<ShareAdminClass> adminClassList, List<Map<String, Object>> sipAdminClassList) {
        if (adminClassList == null || adminClassList.size() <= 0)
            return;
        String[] gids = new String[sipAdminClassList.size()];
        int i = 0;
        if (sipAdminClassList == null || sipAdminClassList.size() <= 0) {
            gids[1] = "none";
        } else {
            for (Map<String, Object> sipClass : sipAdminClassList) {
                gids[i++] = MapUtils.getString(sipClass, "bjid");
            }
        }
        for (ShareAdminClass adminClass : adminClassList) {
            if (StringUtils.isNotEmpty(adminClass.getCd_guid())) {
                int index = ArrayUtils.indexOf(gids, adminClass.getCd_guid());
                if (index < 0) {
                    adminClass.setClassStatus(CoreConstants.CLASS_STATUS__NOT_GRADUATE);
                    adminClass.setModifyPgm("SynchronizeServiceImplEdit");
                    adminClass.setModifyTime(new Timestamp(new Date().getTime()));
                    adminClassDao.addOrUpdate(adminClass);
                }
            }
        }
    }

    /**
     * 学生数据更新线程帮助类 studentUpdateThreadHelper TODO
     * 
     * @author creator BZT 2016年9月19日 下午3:57:30
     * @author updater
     *
     * @version 1.0.0
     */
    public class StudentUpdateHelper implements Callable<String> {
        List<ShareUserStudent>       studentList           = new ArrayList<ShareUserStudent>();
        List<ShareUserLogin>         loginList             = new ArrayList<ShareUserLogin>();
        List<ShareAdminClassStudent> adminClassStudentList = new ArrayList<ShareAdminClassStudent>();

        public StudentUpdateHelper(List<ShareUserStudent> studentList,
                                   List<ShareUserLogin> loginList,
                                   List<ShareAdminClassStudent> adminClassStudentList) {
            this.loginList = loginList;
            this.studentList = studentList;
            this.adminClassStudentList = adminClassStudentList;
        }

        @Override
        public String call() throws Exception {
            if (isListNotEmpty(studentList)) {
                shareUserStudentDao.addStudentList(studentList);
            }
            if (isListNotEmpty(loginList)) {
                shareUserLoginDao.addLonginUserList(loginList);
            }
            if (isListNotEmpty(adminClassStudentList)) {
                shareAdminClassStudentDao.saveList(adminClassStudentList);
                /*
                 * for (ShareAdminClassStudent student : adminClassStudentList) { log.info("cd_GUid"
                 * + student.getCd_guid() + "~~~~~~~~~~~~~~~~~~~~~~~~当前学生编码" +
                 * student.getStudentCode() + "当前班级编码" + student.getAdminClassCode());
                 * shareAdminClassStudentDao.saveOne(student); }
                 */
            }
            return "保存完毕";
        }
    }

    public ResultCodeVo tempInsert(String orgCode, String orgGuid) {
        demoDao.deleteAll(orgCode);
        String adminClassListUrl = "/datahub/School/GetClassListBySchoolID/1.0.0?schoolID=" + orgGuid;
        List<ShareAdminClass> adminClasses = new ArrayList<ShareAdminClass>();
        log.info(adminClassListUrl);
        List<Map<String, Object>> adminClassList = ApiUtils.getResponseList(adminClassListUrl);
        if (adminClassList != null && adminClassList.size() > 0) {
            for (Map adminClass : adminClassList) {
                String guid = MapUtils.getString(adminClass, "bjid");
                Integer rxnf = MapUtils.getInteger(adminClass, "rxnf");
                String bjmc = MapUtils.getString(adminClass, "bjmc");
                String nj = MapUtils.getString(adminClass, "nj");
                String jssj = MapUtils.getString(adminClass, "jssj");
                String bzrid = MapUtils.getString(adminClass, "bzrid");
                String bjdm = MapUtils.getString(adminClass, "bjdm");
                adminClassVo classVo = new adminClassVo();
                classVo.setBjmc(bjmc);
                classVo.setGuid(guid);
                classVo.setNj(nj);
                classVo.setOrgCode(orgCode);
                classVo.setRxnf(rxnf);
                classVo.setJssj(jssj);
                classVo.setBzrid(bzrid);
                classVo.setBjdm(bjdm);
                demoDao.insertAdminClass(classVo);
                // 园区学生数据
                String studentListUrl = "/datahub/student/getStudentList/1.0.0"
                                + "?XXDM=&NJDM=&BJDM=&XSXM=&UPDATEDATA=&BJID=" + guid + "&PageIndex=1&PageSize="
                                + Integer.MAX_VALUE;
                List<Map<String, Object>> studentList = ApiUtils.getResponseList(studentListUrl);
                if (isListNotEmpty(studentList)) {
                    for (Map student : studentList) {
                        String stuid = MapUtils.getString(student, "id");
                        String sipSfzh = MapUtils.getString(student, "tysfzh"); // 照片
                        String csrq = MapUtils.getString(student, "csrq"); // 出生日期
                        String dzyx = MapUtils.getString(student, "csrq"); // 电子邮箱
                        String njdm = MapUtils.getString(student, "njdm"); // 年级代码
                        String bjmc2 = MapUtils.getString(student, "bjmc"); // 班级名称
                        String datastatus = MapUtils.getString(student, "datastatus"); // 数据状态
                        String sipGradeName = MapUtils.getString(student, "njmc"); // 年级名称
                        String sipBjdm = MapUtils.getString(student, "bjdm"); // 班级代码
                        String sipStudentNumber = MapUtils.getString(student, "xsdm");// 学号
                        String sipUserName = MapUtils.getString(student, "xsxm"); // 学生姓名
                        String sipxb = MapUtils.getString(student, "xb"); // 性别
                        String sipAvatarsImg = MapUtils.getString(student, "zp"); // 照片
                        String xxid = MapUtils.getString(student, "xxid"); // 学校id
                        StudentTempVo vo = new StudentTempVo();
                        vo.setCsrq(csrq);
                        vo.setDzyx(dzyx);
                        vo.setNjdm(njdm);
                        vo.setBjmc(bjmc2);
                        vo.setDatastatus(datastatus);
                        vo.setBjdm(sipBjdm);
                        vo.setBjguid(guid);
                        vo.setGuid(stuid);
                        vo.setNjmc(sipGradeName);
                        vo.setOrgCode(orgCode);
                        vo.setTysfzh(sipSfzh);
                        vo.setXb(sipxb);
                        vo.setXsdm(sipStudentNumber);
                        vo.setXsxm(sipUserName);
                        vo.setZp(sipAvatarsImg);
                        vo.setXxid(xxid);
                        demoDao.insertStudent(vo);
                    }
                }
            }
        }
        return ResultCodeVo.rightCode("成功");
        /*
         * String teacherListUrl =
         * "/datahub/teacher/getTeacherList/1.0.0?UpdateData=&XM=&SFZH=&SSDW=" + orgGuid +
         * "&XXDM=&PageSize=" + Integer.MAX_VALUE + "&PageIndex=1"; List<Map<String, Object>>
         * teacherList = ApiUtils.getResponseList(teacherListUrl); if (teacherList == null ||
         * teacherList.size() <= 0) return ResultCodeVo.rightCode(""); for (Map teacher :
         * teacherList) { String lsid = MapUtils.getString(teacher, "lsid"); String xm =
         * MapUtils.getString(teacher, "xm"); // 姓名 Integer xbm = MapUtils.getInteger(teacher,
         * "xbm"); // 性别 String xddm = MapUtils.getString(teacher, "xddm"); // 学段代码 String xkdm =
         * MapUtils.getString(teacher, "xkdm"); // 学科代码 String gh = MapUtils.getString(teacher,
         * "gh"); // 工号 String yddh = MapUtils.getString(teacher, "yddh"); // 移动电话 String dzyx =
         * MapUtils.getString(teacher, "dzyx"); // 电子邮箱 String zp = MapUtils.getString(teacher,
         * "zp"); // 照片 String yzh = MapUtils.getString(teacher, "yzh"); // 域账好 String ssdw =
         * MapUtils.getString(teacher, "ssdw"); // 所属单位 String ssdwdm = MapUtils.getString(teacher,
         * "ssdwdm"); // 所属单位代码 String datastatus = MapUtils.getString(teacher, "datastatus"); //
         * 数据状态 TeacherTempVo teacherTempVo = new TeacherTempVo(); teacherTempVo.setDzyx(dzyx);
         * teacherTempVo.setGh(gh); teacherTempVo.setLsid(lsid); teacherTempVo.setOrgCode(orgCode);
         * teacherTempVo.setXbm(xbm); teacherTempVo.setXddm(xddm); teacherTempVo.setXkdm(xkdm);
         * teacherTempVo.setXm(xm); teacherTempVo.setSsdw(ssdw); teacherTempVo.setSsdwdm(ssdwdm);
         * teacherTempVo.setYddh(yddh); teacherTempVo.setYzh(yzh);
         * teacherTempVo.setDataStatus(datastatus); teacherTempVo.setZp(zp);
         * demoDao.insertTeacher(teacherTempVo); } // 新增 String rkListUrl =
         * "/datahub/teacher/getTeacherRkList/1.0.0?SchoolGUID=" + orgGuid + "&TeacherGUID=";
         * List<Map<String, Object>> teacherRkList = ApiUtils.getResponseList(rkListUrl); if
         * (isListNotEmpty(teacherRkList)) { for (Map teacherRk : teacherRkList) { String bjguid =
         * MapUtils.getString(teacherRk, "bjguid");// 班级唯一码 String classBeginTime =
         * MapUtils.getString(teacherRk, "skksrq"); // 学科班级开始时间 String classEndTime =
         * MapUtils.getString(teacherRk, "skjsrq"); // 学科班级结束时间 String adminClassXkbm =
         * MapUtils.getString(teacherRk, "xkbm"); // 学科编码 String lsid =
         * MapUtils.getString(teacherRk, "lsguid"); // 老师编码 adminClassSubjectTempVo
         * classSubjectTempVo = new adminClassSubjectTempVo(); classSubjectTempVo.setBjguid(bjguid);
         * classSubjectTempVo.setLsid(lsid); classSubjectTempVo.setOrgCode(orgCode);
         * classSubjectTempVo.setSkjsrq(classBeginTime); classSubjectTempVo.setSkksrq(classEndTime);
         * classSubjectTempVo.setXkbm(adminClassXkbm);
         * demoDao.insertTeacherSubjectClass(classSubjectTempVo); } } return
         * ResultCodeVo.rightCode("成功");
         */
    }

    public void updateTest(DataSource dataSource) {
        try {
            Connection conn = null;
            conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();

            for (int i = 1; i <= 10000; i++) {
                String sql = "update insertdemo set a='ss' where id=" + i;
                stmt.addBatch(sql); // 添加一次预定义参数
            }
            stmt.executeBatch();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Resource(name = "dataSource_mooc")
    DataSource dataSource;

    public class ExeTeacherBatchSql implements Runnable {
        @Override
        public void run() {
            log.info("-------------------------------开始老师更新批处理----------------------！");
            try {
                Connection conn = null;
                conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                // 老师信息
                if (isListNotEmpty(optTeacherEditList)) {
                    for (ShareUserTeacher teacher : optTeacherEditList) {
                        StringBuffer teacherSql = new StringBuffer();
                        teacherSql.append("UPDATE");
                        teacherSql.append("        share_user_teacher  ");
                        teacherSql.append("    SET");
                        teacherSql.append("        orgCode ='" + teacher.getOrgCode() + "'");
                        teacherSql.append("        ,userName ='" + teacher.getUserName() + "'");
                        teacherSql.append("        ,teacherCode ='" + teacher.getTeacherCode() + "'");
                        teacherSql.append("        ,jobTitleCode ='" + teacher.getJobTitleCode() + "'");
                        teacherSql.append("        ,positionCode ='" + teacher.getPositionCode() + "'");
                        teacherSql.append("        ,status =1");
                        teacherSql.append("        ,gender ='" + teacher.getGender() + "'");
                        teacherSql.append("        ,sectionCode ='" + teacher.getSectionCode() + "'");
                        teacherSql.append("        ,subjectCode ='" + teacher.getSubjectCode() + "'");
                        teacherSql.append("        ,termCode ='" + teacher.getTermCode() + "'");
                        teacherSql.append("        ,workNo ='" + teacher.getWorkNo() + "'");
                        teacherSql.append("        ,modifyPgm ='" + teacher.getModifyPgm() + "'");
                        teacherSql.append("        ,modifyTime ='" + teacher.getModifyTime() + "'");
                        teacherSql.append("        ,modifyIP ='" + teacher.getModifyIP() + "'");
                        teacherSql.append("        ");
                        teacherSql.append("    WHERE");
                        teacherSql.append("        cd_guid ='" + teacher.getCd_guid() + "'");
                        stmt.addBatch(teacherSql.toString()); // 添加一次预定义参数
                    }
                }
                // 执行批量执行
                stmt.executeBatch();
                stmt.close();
                conn.close();
                long end = System.currentTimeMillis();
                long last = (end - begin) / (1000);
                log.info("-------------------------------老师更新处理结束----------------------！持续" + last + "秒");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        long begin = System.currentTimeMillis();

    }

    public class ExeStudentBatchSql implements Runnable {
        public void run() {
            long begin = System.currentTimeMillis();
            log.info("-------------------------------开始学生更新批处理----------------------！");
            try {
                Connection conn = null;
                conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                // 学生信息
                if (isListNotEmpty(optStudentEditList)) {
                    for (ShareUserStudentVo studentVo : optStudentEditList) {
                        StringBuffer studentSql = new StringBuffer();
                        // sql语句
                        StringBuffer sql = new StringBuffer();
                        studentSql.append("UPDATE");
                        studentSql.append("        share_user_student ");
                        studentSql.append("    SET");
                        studentSql.append("        gradeCode ='" + studentVo.getGradeCode() + "'");
                        studentSql.append("        ,orgCode ='" + studentVo.getOrgCode() + "'");
                        studentSql.append("        ,adminClassCode ='" + studentVo.getAdminClassCode() + "'");
                        studentSql.append("        ,studentNumber ='" + studentVo.getStudentNumber() + "'");
                        studentSql.append("        ,userName ='" + studentVo.getUserName() + "'");
                        studentSql.append("        ,studentCode ='" + studentVo.getStudentCode() + "'");
                        studentSql.append("        ,gender ='" + studentVo.getGender() + "'");
                        studentSql.append("        ,avatarsImg ='" + studentVo.getAvatarsImg() + "'");
                        studentSql.append("        ,termCode ='" + studentVo.getTermCode() + "'");
                        studentSql.append("        ,modifyPgm ='" + studentVo.getModifyPgm() + "'");
                        studentSql.append("        ,modifyTime ='" + new Timestamp(new Date().getTime()) + "'");
                        studentSql.append("    WHERE");
                        studentSql.append("        cd_guid ='" + studentVo.getCd_guid() + "'");
                        stmt.addBatch(studentSql.toString()); // 添加一次预定义参数
                    }
                }
                // 执行批量执行
                stmt.executeBatch();
                stmt.close();
                conn.close();
                long end = System.currentTimeMillis();
                long last = (end - begin) / (1000);
                log.info("-------------------------------学生更新批处理结束----------------------！持续" + last + "秒");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public class ExeLoginBatchSql implements Runnable {
        public void run() {
            long begin = System.currentTimeMillis();
            log.info("-------------------------------开始登录信息批处理----------------------！");
            try {
                Connection conn = null;
                conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                // 登录信息
                if (isListNotEmpty(optLoginEditList)) {
                    for (ShareUserLoginVo login : optLoginEditList) {
                        StringBuffer sql = new StringBuffer();
                        sql.append("update");
                        sql.append("        share_user_login  ");
                        sql.append("    SET");
                        sql.append("        loginAccount= '" + login.getLoginAccount() + "'");
                        sql.append("        ,orgCode='" + login.getOrgCode() + "'");
                        sql.append("        ,orgName='" + login.getOrgName() + "'");
                        sql.append("        ,gender='" + login.getGender() + "'");
                        sql.append("        ,userName='" + login.getUserName() + "'");
                        sql.append("        ,modifyIP='" + login.getModifyIP() + "'");
                        sql.append("        ,modifyPgm='" + login.getModifyPgm() + "'");
                        sql.append("        ,modifyTime='" + login.getModifyTime() + "'");
                        sql.append("        ,userRole='" + login.getUserRole() + "'");
                        sql.append("        ,avatarsImg='" + login.getAvatarsImg() + "'");
                        sql.append("        ,mail='" + login.getMail() + "'");
                        sql.append("    WHERE");
                        sql.append("        cd_guid='" + login.getCd_guid() + "'");
                        stmt.addBatch(sql.toString()); // 添加一次预定义参数
                    }
                }
                // 执行批量执行
                stmt.executeBatch();
                log.info("-------------------------------登录信息批处理结束----------------------！");
                stmt.close();
                conn.close();
                long end = System.currentTimeMillis();
                long last = (end - begin) / (1000);
                log.info("-------------------------------登录信息批处理结束----------------------！持续" + last + "秒");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
