package com.baizhitong.resource.manage.teacher.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.encrypt.MD5;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.common.utils.PrimaryKeyHelper;
import com.baizhitong.resource.common.utils.RegexUtil;
import com.baizhitong.resource.common.utils.Excel.ExcelRepeatCheck;
import com.baizhitong.resource.common.utils.Excel.ExcelRepeatCheck.CheckType;
import com.baizhitong.resource.common.utils.Excel.MessageHandel;
import com.baizhitong.resource.common.vo.ProgressVO;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.dao.share.ShareAdminClassSubjectDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassStudentDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassSubjectDao;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.dao.share.ShareUserTeacherDao;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassSubjectService;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.companySubject.service.ICompanySubjectSerivce;
import com.baizhitong.resource.manage.furtherEducation.action.WebSocket;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.login.service.LoginService;
import com.baizhitong.resource.manage.shareTeachingClass.service.IShareTeachingClassSubjectService;
import com.baizhitong.resource.manage.studyYear.service.IOrgYearTermService;
import com.baizhitong.resource.manage.teacher.action.TeacherImportHelper;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;
import com.baizhitong.resource.model.share.ShareOrgYearTerm;
import com.baizhitong.resource.model.share.ShareTeachingClass;
import com.baizhitong.resource.model.share.ShareTeachingClassSubject;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserTeacher;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;
import com.baizhitong.resource.model.vo.share.ShareTeachingClassSubjectVo;
import com.baizhitong.resource.model.vo.share.ShareUserTeacherVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

import javassist.tools.web.Webserver;

@Service
public class TeacherServiceImpl extends BaseService implements ITeacherService {
    @Autowired
    private ShareUserTeacherDao                  teacherDao;
    /***** 登录dao *********/
    private @Autowired ShareUserLoginDao         userLoginDao;
    /***** 机构接口 *********/
    private @Autowired ICompanyService           companyService;
    /***** 行政班级dao *********/
    private @Autowired ShareAdminClassDao        adminClassDao;
    /******* 机构学科接口 **********/
    private @Autowired ICompanySubjectSerivce    companySubjectService;
    /******* 年级接口 **********/
    private @Autowired GradeService              gradeService;
    /******* 行政班级学科 **********/
    private @Autowired IAdminClassSubjectService adminClassSubjectService;
    /******* 行政班级学科dao **********/
    @Autowired
    private ShareAdminClassSubjectDao            adminClassSubjectDao;
    // 机构学期接口
    private @Autowired IOrgYearTermService       orgYearTermService;

    private @Autowired LoginService              loginService;
    @Autowired
    private ShareTeachingClassStudentDao         teachingClassStudentDao;
    @Autowired
    private ShareTeachingClassSubjectDao         teachingClassSubjectDao;
    @Autowired
    private IShareTeachingClassSubjectService    teachingClassSubjectService;
    @Autowired
    private ShareTeachingClassDao                teachingClassDao;

    /**
     * 查询教师信息 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param orgName 机构名称
     * @param userName 老师名称
     * @param loginAccount 登录账户
     * @param userRole 用户角色
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page getTeacherInfo(String sectionCode, String subjectCode, String orgName, String orgCode, String userName,
                    String loginAccount, Integer userRole, Integer pageSize, Integer pageNo, Integer adminChooseType) {
        Page page = teacherDao.getTeacherInfo(sectionCode, subjectCode, orgName, orgCode, userName, loginAccount,
                        userRole, pageSize, pageNo, adminChooseType);
        List<Map<String, Object>> mapList = page.getRows();
        if (mapList == null || mapList.size() <= 0)
            return page;
        List<ShareUserTeacherVo> voList = ShareUserTeacherVo.mapListToVoList(mapList);
        page.setRows(voList);
        return page;
    }

    /**
     * 查询老师列表 ()<br>
     * 
     * @return
     */
    public List<ShareUserTeacher> getTeacherList(String orgCode) {

        CompanyInfoVo companyInfoVo = getCompanyInfo();
        if (StringUtils.isEmpty(orgCode)) {
            orgCode = companyInfoVo.getOrgCode();
        }
        return teacherDao.getTeacherList(orgCode);
    }

    /**
     * 查询老师列表 ()<br>
     * 
     * @return
     */
    public List<ShareUserTeacher> getTeacherList() {

        CompanyInfoVo companyInfoVo = getCompanyInfo();

        return teacherDao.getTeacherList(companyInfoVo.getOrgCode());
    }

    /**
     * 得到老师信息 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ShareUserTeacher getTeacher(String userCode) {
        return teacherDao.getTeacher(userCode);
    }

    /**
     * 新增或编辑老师信息 ()<br>
     * 
     * @param teacher
     * @param userLogin
     * @return
     */
    public ResultCodeVo saveOrUpdateTeacher(ShareUserTeacher teacher, ShareUserLogin userLogin) {
        /******** 数据准备 **************/

        CompanyInfoVo company = getCompanyInfo();
        String ip = getIp();
        if (StringUtils.isEmpty(teacher.getGid())) { // 新增
            String userCode = PrimaryKeyHelper.getUserCode();

            // 验证登录账号是否存在
            long exist = loginService.accountExitCheck(userLogin.getLoginAccount());
            if (exist > 0) {
                return ResultCodeVo.errorCode("账号已经存在");
            }
            // 校验手机号是否存在或者已被他人绑定
            if (StringUtils.isNotEmpty(userLogin.getMobilePhone())) {
                List<ShareUserLogin> phoneList = userLoginDao.selectListByMobilePhone(userLogin.getMobilePhone(), "");
                if (phoneList != null && phoneList.size() > 0) {
                    return ResultCodeVo.errorCode("该手机号已被他人使用，请重新输入！");
                } else {
                    userLogin.setFlagPhoneValidate(CoreConstants.FLAG_PHONE_VALIDATE_NO);
                }
            }
            // 校验邮箱是否存在或者已被他人绑定
            if (StringUtils.isNotEmpty(userLogin.getMail())) {
                List<ShareUserLogin> mailList = userLoginDao.selectListByMail(userLogin.getMail(), "");
                if (mailList != null && mailList.size() > 0) {
                    return ResultCodeVo.errorCode("该邮箱已被他人使用，请重新输入！");
                } else {
                    userLogin.setFlagMailValidate(CoreConstants.FLAG_MAIL_VALIDATE_NO);
                }
            }
            /********** 老师信息 *************/
            teacher.setModifyIP(ip);
            teacher.setModifyPgm("teacherService");
            teacher.setModifyTime(new Timestamp(new Date().getTime()));
            teacher.setOrgCode(company.getOrgCode());
            teacher.setStatus(CoreConstants.USER_STATUS_EFFECTIVE);
            teacher.setSysVer(1);
            teacher.setTeacherCode(userCode);
            teacher.setGid(UUID.randomUUID().toString());
            teacher.setOrgCode(company.getOrgCode());
            teacher.setCd_guid(userCode);

            /************ 登录信息 ***************/
            userLogin.setGid(UUID.randomUUID().toString());
            userLogin.setModifyIP(ip);
            userLogin.setLoginPwd(MD5.calcMD5(userLogin.getLoginPwd()));
            userLogin.setUserCode(userCode);
            userLogin.setModifyPgm("teacherService");
            userLogin.setModifyTime(new Timestamp(new Date().getTime()));
            userLogin.setOrgCode(company.getOrgCode());
            userLogin.setStatus(CoreConstants.USER_STATUS_EFFECTIVE);
            userLogin.setOrgName(company.getOrgName());
            userLogin.setUserRole(CoreConstants.USER_ROLE_TEACHER);

            /******** 保存 ********/
            boolean teacherSuccess = teacherDao.saveOrgUpdate(teacher);
            boolean loginSuccess = userLoginDao.addLoginUser(userLogin);
            return (teacherSuccess && loginSuccess) ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
        } else {
            // 修改老师信息
            ShareUserTeacher oldTeacher = teacherDao.getTeacher(teacher.getTeacherCode());
            ShareUserLogin oldLogin = userLoginDao.getUser(teacher.getTeacherCode());
            /******* 老师修改 *********/
            oldTeacher.setGender(teacher.getGender());
            oldTeacher.setGradeCode(teacher.getGradeCode());
            oldTeacher.setSubjectCode(teacher.getSubjectCode());
            oldTeacher.setGender(teacher.getGender());
            oldTeacher.setSectionCode(teacher.getSectionCode());
            oldTeacher.setUserName(teacher.getUserName());
            oldTeacher.setWorkFirstDay(teacher.getWorkFirstDay());
            oldTeacher.setWorkNo(teacher.getWorkNo());
            oldTeacher.setModifyIP(ip);
            oldTeacher.setModifyPgm("teacherService");
            oldTeacher.setModifyTime(new Timestamp(new Date().getTime()));

            /******* 登录信息修改 ********/
            // 校验手机号是否存在或者已被他人绑定
            if (StringUtils.isNotEmpty(userLogin.getMobilePhone())) {
                List<ShareUserLogin> phoneList = userLoginDao.selectListByMobilePhone(userLogin.getMobilePhone(),
                                teacher.getTeacherCode());
                if (phoneList != null && phoneList.size() > 0) {
                    return ResultCodeVo.errorCode("该手机号已被他人使用，请重新输入！");
                } else {
                    oldLogin.setMobilePhone(userLogin.getMobilePhone());
                    oldLogin.setFlagPhoneValidate(CoreConstants.FLAG_PHONE_VALIDATE_NO);
                }
            } else {
                oldLogin.setMobilePhone(userLogin.getMobilePhone());
                oldLogin.setFlagPhoneValidate(CoreConstants.FLAG_PHONE_VALIDATE_NO);
            }

            // 校验邮箱是否存在或者已被他人绑定
            if (StringUtils.isNotEmpty(userLogin.getMail())) {
                List<ShareUserLogin> mailList = userLoginDao.selectListByMail(userLogin.getMail(),
                                teacher.getTeacherCode());
                if (mailList != null && mailList.size() > 0) {
                    return ResultCodeVo.errorCode("该邮箱已被他人使用，请重新输入！");
                } else {
                    oldLogin.setMail(userLogin.getMail());
                    oldLogin.setFlagMailValidate(CoreConstants.FLAG_MAIL_VALIDATE_NO);
                }
            } else {
                oldLogin.setMail(userLogin.getMail());
                oldLogin.setFlagMailValidate(CoreConstants.FLAG_MAIL_VALIDATE_NO);
            }
            oldLogin.setUserName(userLogin.getUserName());
            oldLogin.setModifyIP(ip);
            oldLogin.setModifyPgm("teacherService");
            oldLogin.setModifyTime(new Timestamp(new Date().getTime()));

            boolean loginSuccess = userLoginDao.addLoginUser(oldLogin);
            boolean teacherSuccess = teacherDao.saveOrgUpdate(oldTeacher);
            return (teacherSuccess && loginSuccess) ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
        }
    }

    /**
     * 删除老师 ()<br>
     * 
     * @param teacherCode
     * @return
     */
    public ResultCodeVo deleteTeacher(String teacherCode) {
        int count = teacherDao.deleteTeacher(teacherCode);
        int count2 = userLoginDao.deleteLoginUserByUserCode(teacherCode);
        // 删除教学班级学科
        /*
         * teachingClassSubjectDao.delete(null, null, teacherCode);
         * adminClassSubjectDao.deleteTeacherSubject(teacherCode);
         */
        if (count > 0 && count2 > 0) {
            return ResultCodeVo.rightCode("删除成功");
        } else {
            return ResultCodeVo.errorCode("删除失败");
        }
    }

    /**
     * 导入老师 ()<br>
     * 
     * @param sheet
     * @return
     */
    public ResultCodeVo importTeacher(Sheet sheet) {
        try {
            List<String> teacherLoginAccountList = new ArrayList<String>();
            List<ShareUserTeacher> teacherList = new ArrayList<ShareUserTeacher>();
            List<ShareUserLogin> accountList = new ArrayList<ShareUserLogin>();
            List<ShareAdminClassSubject> adminClassSubjectList = new ArrayList<ShareAdminClassSubject>();
            Map<String, String> userCodeMapList = new HashMap<String, String>();

            CompanyInfoVo company = getCompanyInfo();
            String orgCode = company.getOrgCode();
            String orgName = company.getOrgName();
            Integer domainID = company.getDomainID();
            ShareOrgYearTerm orgYearTerm = orgYearTermService.getCurrentOrgYearTerm();
            ProgressVO progress = new ProgressVO();
            // 机构所有的账号信息
            List<Map<String, Object>> orgAcountList = null;
            if (SystemConfig.agentEnable) {
                orgAcountList = userLoginDao.getSameDomainAccountName(domainID);
            } else {
                orgAcountList = userLoginDao.getOrgAccountName(company.getOrgCode());
            }

            // 机构所有的行政班级
            List<ShareAdminClass> orgAdminClassList = adminClassDao.getAdminClassList(company.getOrgCode());
            // 机构学段
            List<ShareCodeSectionVo> orgSectionList = companyService.getCompanySection(company.getOrgCode());
            // 机构学科
            List<ShareCodeSubjectVo> orgSubjectList = companySubjectService.getCompanySubjectList("");
            // 机构行政班级学科
            List<ShareAdminClassSubject> orgAdminClassSubjectList = adminClassSubjectService
                            .getAdminClassSubject(company.getOrgCode(), "");
            // 机构年级
            List<ShareCodeGradeVo> orgGradeList = new ArrayList<ShareCodeGradeVo>();

            if (orgSectionList != null && orgSectionList.size() > 0) {
                try {
                    for (ShareCodeSectionVo section : orgSectionList) {
                        orgGradeList.addAll(gradeService.selectGradeList(section.getCode()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResultCodeVo.errorCode("查询机构年级出错");
                }
            }

            int teacherNameIndex = 1000, adminClassSubjectIndex = 10000, sectionIndex = 10000, genderIndex = 10000,
                            mobileIndex = 10000, mailIndex = 10000, loginAccountIndex = 10000, passwordIndex = 10000,
                            subjectIndex = 10000, gradeIndex = 10000, workNoIndex = 10000, workFirstDayIndex = 10000;
            HashSet<Integer> set = new HashSet<Integer>();
            Row firstRow = sheet.getRow(0);

            for (int i = firstRow.getFirstCellNum(); i <= firstRow.getLastCellNum(); i++) {

                if (firstRow.getCell(i) == null) {
                    continue;
                }
                String coulumName = firstRow.getCell(i).toString();
                if (coulumName.contains("姓名")) {
                    teacherNameIndex = i;
                    set.add(teacherNameIndex);
                }
                if (coulumName.contains("任教信息")) {
                    adminClassSubjectIndex = i;
                    set.add(adminClassSubjectIndex);
                }
                if (coulumName.contains("学段（必输）")) {
                    sectionIndex = i;
                    set.add(sectionIndex);
                }
                if ("性别".equals(coulumName)) {
                    genderIndex = i;
                    set.add(genderIndex);
                }
                if ("手机号".equals(coulumName)) {
                    mobileIndex = i;
                    set.add(mobileIndex);
                }
                if ("邮箱".equals(coulumName)) {
                    mailIndex = i;
                    set.add(mailIndex);
                }
                if (coulumName.contains("账号")) {
                    loginAccountIndex = i;
                    set.add(loginAccountIndex);
                }
                if ("密码".equals(coulumName)) {
                    passwordIndex = i;
                    set.add(passwordIndex);
                }
                if (coulumName.contains("年级（必输）")) {
                    gradeIndex = i;
                    set.add(gradeIndex);
                }
                if (coulumName.contains("学科（必输）")) {
                    subjectIndex = i;
                    set.add(subjectIndex);
                }
                if (coulumName.contains("工号")) {
                    workNoIndex = i;
                    set.add(workNoIndex);
                }
                if (coulumName.contains("入职日期")) {
                    workFirstDayIndex = i;
                    set.add(workFirstDayIndex);
                }
            }
            if (set.size() < 12) {
                return ResultCodeVo.errorCode("模板不正确请联系管理员");
            }
            progress.setText("正在读取excel数据");
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                progress.setValue(i / sheet.getLastRowNum() * 100);
                WebSocket.session.getBasicRemote().sendText(JSON.toJSONString(progress));
                Row row = sheet.getRow(i);
                if (ExcelUtils.checkRowNull(row)) {
                    continue;
                }

                /************** 老师信息 ******************/
                // 准备数据
                String teacherName = ExcelUtils.changeString(row.getCell(teacherNameIndex));
                String loginAccount = ExcelUtils.changeString(row.getCell(loginAccountIndex));
                String genderStr = ExcelUtils.changeString(row.getCell(genderIndex));
                Integer gender = null;
                if ("男".equals(genderStr)) {
                    gender = 1;
                } else if ("女".equals(genderStr)) {
                    gender = 2;
                }
                String sectionName = ExcelUtils.changeString(row.getCell(sectionIndex));
                String subjectName = ExcelUtils.changeString(row.getCell(subjectIndex));
                String gradeName = ExcelUtils.changeString(row.getCell(gradeIndex));
                String mail = ExcelUtils.changeString(row.getCell(mailIndex));
                String mobile = ExcelUtils.changeString(row.getCell(mobileIndex));
                String password = ExcelUtils.changeString(row.getCell(passwordIndex));
                String workNo = ExcelUtils.changeString(row.getCell(workNoIndex));
                String workFirstDayStr = ExcelUtils.changeString(row.getCell(workFirstDayIndex));
                if (!RegexUtil.test(workFirstDayStr, RegexUtil.date) && StringUtils.isNotEmpty(workFirstDayStr)) {
                    return ResultCodeVo.errorCode(workFirstDayStr + "入职日期格式不正确,请检查日期格式");
                }
                String adminClassSubjectStr = ExcelUtils.changeString(row.getCell(adminClassSubjectIndex));

                if (StringUtils.isEmpty(teacherName)) {
                    return ResultCodeVo.errorCode("老师姓名不能为空");
                }
                if (StringUtils.isEmpty(loginAccount)) {
                    return ResultCodeVo.errorCode("登录账号不能为空");
                }
                if (StringUtils.isEmpty(sectionName)) {
                    return ResultCodeVo.errorCode("学段不能为空");
                }
                if (StringUtils.isEmpty(gradeName)) {
                    return ResultCodeVo.errorCode("年级不能为空");
                }
                if (StringUtils.isEmpty(subjectName)) {
                    return ResultCodeVo.errorCode("学科不能为空");
                }
                /** -------------------------------姓名--------------------------- */
                if (!RegexUtil.valideLength(teacherName, 0, 30)) {
                    return ResultCodeVo.errorCode("姓名的长度不能超过30位");
                }

                /** -------------------------------邮箱---------------------------- */

                if (!RegexUtil.test(mail, RegexUtil.email) && StringUtils.isNotEmpty(mail)) {
                    return ResultCodeVo.errorCode(mail + "邮箱格式不对,请确保邮箱格式正确");
                }
                if (!RegexUtil.valideLength(mail, 0, 50)) {
                    return ResultCodeVo.errorCode(mail + "邮箱长度不能超过50位");
                }

                /** -------------------------------手机---------------------------- */

                if (!RegexUtil.test(mobile, RegexUtil.mobile) && StringUtils.isNotEmpty(mobile)) {
                    return ResultCodeVo.errorCode(mobile + "手机号格式不对");
                }

                /** ------------------------------密码---------------------------- */

                if (StringUtils.isEmpty(password)) {
                    password = CoreConstants.DEFAULT_TEACHER_PWD;
                } else {
                    if (!password.matches(RegexUtil.REG_PASSWORD)) {
                        return ResultCodeVo.errorCode("密码不能输入中文及空格且长度为6~20");
                    }
                }

                /** -----------------------------工号--------------------------------------- */
                if (!workNo.matches(RegexUtil.REG_WORKNO)) {
                    return ResultCodeVo.errorCode("请输入正确的工号（0~20位半角英文数字）");
                }

                /** -------------------------------账号---------------------------- */
                if (!loginAccount.matches(RegexUtil.REG_ACCOUNT)) {
                    return ResultCodeVo.errorCode("请输入正确的账号（0~50位半角英文数字）");
                }

                ShareUserTeacher teacher = new ShareUserTeacher();
                ShareUserLogin login = new ShareUserLogin();

                /** -------------------------------学段---------------------------- */

                ShareCodeSectionVo section = getSameNameSection(orgSectionList, sectionName);
                if (section == null) {
                    return ResultCodeVo.errorCode("学段" + sectionName + "不存在");
                }
                if (section != null) {
                    teacher.setSectionCode(section.getCode());
                }

                /** -------------------------------年级---------------------------- */

                ShareCodeGradeVo grade = getSameNameGrade(orgGradeList, gradeName);
                if (grade == null) {
                    return ResultCodeVo.errorCode("年级" + gradeName + "不存在");
                }
                teacher.setGradeCode(grade.getCode());

                /** -------------------------------学科---------------------------- */

                ShareCodeSubjectVo subejct = getSameNameSubejct(orgSubjectList, subjectName);
                if (subejct == null) {
                    return ResultCodeVo.errorCode("学科" + subjectName + "不存在");
                }
                // 老师信息
                String userCode = "";
                // 账号是否已经存在
                boolean exist = false;
                // 登录账号
                Integer existAccountIndex = teacherLoginAccountList.indexOf(loginAccount);

                // 账号存在的情况下
                if (existAccountIndex != null && existAccountIndex >= 0) {
                    exist = true;
                    // 取上一条老师的code
                    userCode = userCodeMapList.get(loginAccount);
                } else {
                    exist = false;
                    teacherLoginAccountList.add(loginAccount);
                }
                // 有可能excel的2行表示的是一个老师就只需要新增班级学科信息
                if (userCode == null || StringUtils.isEmpty(userCode)) {
                    userCode = PrimaryKeyHelper.getUserCode();
                    userCodeMapList.put(loginAccount, userCode);
                }

                if (!exist) {
                    /************* 老师信息 ******************/
                    teacher.setUserName(teacherName);
                    teacher.setGender(gender);
                    teacher.setGid(UUID.randomUUID().toString());
                    teacher.setModifyIP("127.0.0.1");
                    teacher.setModifyPgm("teacherService");
                    teacher.setModifyTime(new Timestamp(new Date().getTime()));
                    teacher.setOrgCode(orgCode);
                    teacher.setTeacherCode(userCode);
                    teacher.setStatus(1);
                    teacher.setSysVer(0);
                    teacher.setGid(UUID.randomUUID().toString());
                    teacher.setSubjectCode(subejct.getCode());
                    teacher.setCd_guid(userCode);
                    teacher.setWorkNo(workNo);
                    if (StringUtils.isNotEmpty(workFirstDayStr)) {
                        teacher.setWorkFirstDay(
                                        new Timestamp(DateUtils.getDateTime(workFirstDayStr, "yyyy-MM-dd").getTime()));
                    }
                    teacherList.add(teacher);
                    /************* 登录信息 ******************/
                    login.setGender(gender);
                    login.setGid(UUID.randomUUID().toString());
                    login.setLoginAccount(loginAccount);
                    login.setLoginPwd(MD5.calcMD5(password));
                    login.setMail(mail);
                    login.setMobilePhone(mobile);
                    login.setOrgCode(orgCode);
                    login.setModifyIP("127.0.0.1");
                    login.setModifyPgm("teacherService");
                    login.setModifyTime(new Timestamp(new Date().getTime()));
                    login.setOrgName(orgName);
                    login.setStatus(1);
                    login.setUserCode(userCode);
                    login.setUserName(teacherName);
                    login.setUserRole(CoreConstants.USER_ROLE_TEACHER);
                    accountList.add(login);
                }

                /************* 行政班级学科 ******************/
                List<String> adminClassSubjectStrList = adminClassSubjectAnalysis(adminClassSubjectStr);
                // 针对{1班，语文，一年级}，{2班，数学，一年级}的格式
                if (adminClassSubjectStrList != null && adminClassSubjectStrList.size() > 0) {
                    for (String str : adminClassSubjectStrList) {

                        str = str.replaceAll("\\{|\\}", "");
                        String classSubjectGrade[] = str.split(",|，");

                        ShareCodeSubjectVo subjectVo = getSameNameSubejct(orgSubjectList, classSubjectGrade[1]);
                        ShareCodeGradeVo gradeVo = getSameNameGrade(orgGradeList, classSubjectGrade[2]);

                        if (subjectVo == null) {
                            return ResultCodeVo.errorCode(classSubjectGrade[1] + "不存在");
                        }
                        if (gradeVo == null) {
                            return ResultCodeVo.errorCode(classSubjectGrade[2] + "不存在");
                        }
                        ShareAdminClass adminClass = getSameNameAdminClass(orgAdminClassList, classSubjectGrade[0],
                                        gradeVo.getCode());
                        if (adminClass == null) {
                            return ResultCodeVo.errorCode(classSubjectGrade[0] + "不存在");
                        }
                        ShareAdminClassSubject adminClassSubject = new ShareAdminClassSubject();
                        adminClassSubject.setAdminClassCode(adminClass.getClassCode());
                        if (orgYearTerm != null) {
                            adminClassSubject.setBeginTime(orgYearTerm.getStartDate());
                            adminClassSubject.setEndTime(orgYearTerm.getEndDate());
                        } else {
                            adminClassSubject.setBeginTime(new Timestamp(new Date().getTime()));
                            adminClassSubject.setEndTime(new Timestamp(new Date().getTime()));
                        }
                        adminClassSubject.setGid(UUID.randomUUID().toString());
                        adminClassSubject.setGradeCode(gradeVo.getCode());
                        adminClassSubject.setMemo("");
                        adminClassSubject.setModifyIP("127.0.0.1");
                        adminClassSubject.setModifyPgm("teacherService");
                        adminClassSubject.setModifyTime(new Timestamp(new Date().getTime()));
                        adminClassSubject.setOrgCode(orgCode);
                        adminClassSubject.setSubjectCode(subjectVo.getCode());
                        adminClassSubject.setSysVer(1);
                        adminClassSubject.setTeacherCode(userCode);
                        adminClassSubject.setTeacherName(teacherName);
                        adminClassSubject.setTeacherRole(0);
                        ShareAdminClassSubject sameAdminClassSubject = getSameAdminClassSubject(
                                        orgAdminClassSubjectList, gradeVo.getCode(), subjectVo.getCode(), userCode,
                                        adminClass.getClassCode());
                        if (sameAdminClassSubject == null) {
                            adminClassSubjectList.add(adminClassSubject);
                            orgAdminClassSubjectList.add(adminClassSubject);
                        }
                    }
                } else {
                    adminClassSubjectStr = adminClassSubjectStr.replaceAll("\\s", "");
                    // 针对1班，2班，3班的格式
                    String classList[] = adminClassSubjectStr.split(",|，");
                    if (classList != null && classList.length > 0 && StringUtils.isNotEmpty(classList[0])) {
                        for (int j = 0; j < classList.length; j++) {
                            ShareAdminClass adminClass = getSameNameAdminClass(orgAdminClassList, classList[j],
                                            grade.getCode());
                            if (adminClass == null) {
                                return ResultCodeVo.errorCode(classList[j] + "不存在");
                            }

                            ShareAdminClassSubject adminClassSubject = new ShareAdminClassSubject();
                            adminClassSubject.setAdminClassCode(adminClass.getClassCode());
                            if (orgYearTerm != null) {
                                adminClassSubject.setBeginTime(orgYearTerm.getStartDate());
                                adminClassSubject.setEndTime(orgYearTerm.getEndDate());
                            } else {
                                adminClassSubject.setBeginTime(new Timestamp(new Date().getTime()));
                                adminClassSubject.setEndTime(new Timestamp(new Date().getTime()));
                            }
                            adminClassSubject.setGid(UUID.randomUUID().toString());
                            adminClassSubject.setGradeCode(grade.getCode());
                            adminClassSubject.setMemo("");
                            adminClassSubject.setModifyIP("127.0.0.1");
                            adminClassSubject.setModifyPgm("teacherService");
                            adminClassSubject.setModifyTime(new Timestamp(new Date().getTime()));
                            adminClassSubject.setOrgCode(orgCode);
                            adminClassSubject.setSubjectCode(subejct.getCode());
                            adminClassSubject.setSysVer(1);
                            adminClassSubject.setTeacherCode(userCode);
                            adminClassSubject.setTeacherName(teacherName);
                            adminClassSubject.setTeacherRole(0);
                            ShareAdminClassSubject sameAdminClassSubject = getSameAdminClassSubject(
                                            orgAdminClassSubjectList, grade.getCode(), subejct.getCode(), userCode,
                                            adminClass.getClassCode());
                            if (sameAdminClassSubject == null) {
                                adminClassSubjectList.add(adminClassSubject);
                                orgAdminClassSubjectList.add(adminClassSubject);
                            }
                        }
                    }
                }
            }
            progress.setText("正在验证数据...");
            WebSocket.session.getBasicRemote().sendText(JSON.toJSONString(progress));
            String mailRepeatMsg = "";
            String phoneRepeatMsg = "";
            String loginAccountRepeatMsg = "";
            ExcelRepeatCheck repeatCheck = new ExcelRepeatCheck();

            for (ShareUserLogin login : accountList) {

                // 登录账号重复验证
                Map<String, Object> sameNameAccount = getSameNameLoginAccount(orgAcountList, login.getLoginAccount());
                if (sameNameAccount != null) {
                    loginAccountRepeatMsg = loginAccountRepeatMsg + login.getLoginAccount();
                }

                // 手机重复验证
                if (StringUtils.isNotEmpty(login.getMobilePhone())) {
                    List<ShareUserLogin> phoneList = userLoginDao.selectListByMobilePhone(login.getMobilePhone(), "");
                    if (phoneList != null && phoneList.size() > 0) {
                        phoneRepeatMsg = phoneRepeatMsg + login.getMobilePhone() + ",";
                    }
                    repeatCheck.addData(CheckType.Mobile, login.getMobilePhone());
                }

                // 邮箱重复验证
                if (StringUtils.isNotEmpty(login.getMail())) {
                    List<ShareUserLogin> mailList = userLoginDao.selectListByMail(login.getMail(), "");
                    if (mailList != null && mailList.size() > 0) {
                        mailRepeatMsg = mailRepeatMsg + login.getMail() + ",";
                    }
                    repeatCheck.addData(CheckType.Email, login.getMail());
                }
            }

            /************ 导入数据中重复的 *************/
            String excelRepeat = repeatCheck.getCheckMsg();
            if (StringUtils.isNotEmpty(excelRepeat)) {
                return ResultCodeVo.errorCode(excelRepeat);
            }

            /************ 输入错误信息 *************/
            StringBuffer errorMsg = new StringBuffer();
            errorMsg.append(MessageHandel.messageHandler(loginAccountRepeatMsg, "账号", "已被使用"))
                            .append(MessageHandel.messageHandler(phoneRepeatMsg, "手机号", "已被使用"))
                            .append(MessageHandel.messageHandler(mailRepeatMsg, "邮箱", "已被使用"));
            if (StringUtils.isNotEmpty(errorMsg.toString())) {
                return ResultCodeVo.errorCode(errorMsg.toString());
            }
            progress.setText("正在保存基础数据...");
            WebSocket.session.getBasicRemote().sendText(JSON.toJSONString(progress));
            int batchCount = 500;
            int n = batchCount;
            ExecutorService service = Executors.newCachedThreadPool();
            List<TeacherImportHelper> allTead = new ArrayList<TeacherImportHelper>();
            while (n < accountList.size()) {
                // 老师列表
                List<ShareUserTeacher> theadTeaList = new ArrayList<ShareUserTeacher>();
                // 登录账户列表
                List<ShareUserLogin> theadAccountList = new ArrayList<ShareUserLogin>();
                // 行政班级学科
                List<ShareAdminClassSubject> theadAdminClassSubjectList = new ArrayList<ShareAdminClassSubject>();

                for (int i = n - batchCount; i < n; i++) {
                    theadTeaList.add(teacherList.get(i));
                    theadAccountList.add(accountList.get(i));
                    theadAdminClassSubjectList.add(adminClassSubjectList.get(i));
                }
                TeacherImportHelper thead = new TeacherImportHelper(userLoginDao, teacherDao, adminClassSubjectDao,
                                theadTeaList, theadAccountList, theadAdminClassSubjectList);
                allTead.add(thead);
                n += batchCount;
            }

            /*********** 数据没导完 **************/
            // 老师列表
            List<ShareUserTeacher> theadTeaList = new ArrayList<ShareUserTeacher>();
            // 登录账户列表
            List<ShareUserLogin> theadAccountList = new ArrayList<ShareUserLogin>();
            // 行政班级学科
            List<ShareAdminClassSubject> theadAdminClassSubjectList = new ArrayList<ShareAdminClassSubject>();

            for (int i = n - batchCount; i < accountList.size(); i++) {
                theadTeaList.add(teacherList.get(i));
                theadAccountList.add(accountList.get(i));
            }
            for (int i = n - batchCount; i < adminClassSubjectList.size(); i++) {
                theadAdminClassSubjectList.add(adminClassSubjectList.get(i));
            }
            TeacherImportHelper thead = new TeacherImportHelper(userLoginDao, teacherDao, adminClassSubjectDao,
                            theadTeaList, theadAccountList, theadAdminClassSubjectList);
            allTead.add(thead);
            try {
                service.invokeAll(allTead);
            } finally {
                service.shutdown();
            }
            progress.setText("开始生成任教信息...");
            // 任教信息
            int process = 1;
            for (ShareAdminClassSubject adminClassSubject : adminClassSubjectList) {
                progress.setValue(process);
                WebSocket.session.getBasicRemote().sendText(JSON.toJSONString(progress));
                ShareAdminClass adminClass = getAdminClass(orgAdminClassList, adminClassSubject.getAdminClassCode());
                String gradeCode = adminClassSubject.getGradeCode();
                String adminClassCode = adminClass.getClassCode();
                // 任教信息年级跟行政班级不匹配跳过
                if (!gradeCode.equals(adminClass.getGradeCode())) {
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
                String teachingClassCode = adminClassCode + subjectCode.trim() + adminClassSubject.getGradeCode();
                ShareTeachingClass teachingClass = new ShareTeachingClass();
                teachingClass.setGid(UUID.randomUUID().toString());
                teachingClass.setTeachingClassCode(teachingClassCode);
                teachingClass.setModifyIP(getIp());
                teachingClass.setAdminClassCode(adminClassCode);
                teachingClass.setModifyPgm("teacherService-import");
                teachingClass.setModifyTime(new Timestamp(new Date().getTime()));
                teachingClass.setStudyYearCode(BeanHelper.getStudyYearCode());
                teachingClass.setOrgCode(orgCode);
                teachingClass.setSysVer(1);
                teachingClass.setTeachingClassName(teachingClassName);
                teachingClass.setGradeCode(adminClassSubject.getGradeCode());
                teachingClass.setSubjectCode(subjectCode);
                teachingClass.setStartTime(beginTime);
                teachingClass.setEndTime(endTime);
                /******* 课程班级学科 *********/
                ShareTeachingClassSubject teachingClassSubject = new ShareTeachingClassSubject();
                teachingClassSubject.setTeacherCode(teacherCode);
                teachingClassSubject.setGid(UUID.randomUUID().toString());
                teachingClassSubject.setOrgCode(orgCode);
                teachingClassSubject.setSubjectCode(subjectCode);
                teachingClassSubject.setGradeCode(adminClassSubject.getGradeCode());
                teachingClassSubject.setTeachingClassCode(teachingClassCode);
                teachingClassSubject.setBeginTime(beginTime);
                teachingClassSubject.setEndTime(endTime);
                teachingClassSubject.setTeacherRole("0");
                teachingClassSubject.setModifyIP(getIp());
                teachingClassSubject.setStudyYearCode(BeanHelper.getStudyYearCode());
                teachingClassSubject.setTermCode(BeanHelper.getTermCode());
                teachingClassSubject.setModifyPgm("shareTeachingClassService-importDefault");
                teachingClassSubject.setModifyTime(new Timestamp(new Date().getTime()));
                teachingClassSubject.setSysVer(1);
                teachingClassSubject.setTeacherName(teacherName);
                // 查询老师有没有教过此班级学科
                List<ShareTeachingClassSubjectVo> classSubjects = teachingClassSubjectService
                                .getShareTeachingClassSubjectList(teachingClassCode, subjectCode,
                                                adminClassSubject.getGradeCode(), teacherCode, orgCode,
                                                BeanHelper.getStudyYearCode(), "");

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
                        continue;
                    } else {
                        teachingClassSubject.setTeachingClassCode(classList.get(0).getTeachingClassCode());
                        teachingClassSubjectDao.add(teachingClassSubject);
                    }
                }

                process++;
            }
            WebSocket.session.close();
            return ResultCodeVo.rightCode("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return ResultCodeVo.errorCode("导入异常");
        }
    }

    // 查询登录名相同账户
    private Map<String, Object> getSameNameLoginAccount(List<Map<String, Object>> accountList, String loginAccount) {
        for (Map<String, Object> map : accountList) {
            if (loginAccount.toLowerCase().equals(MapUtils.getString(map, "loginAccount").toLowerCase())) {
                return map;
            }
        }
        return null;
    }

    // 查询学段
    private ShareCodeSectionVo getSameNameSection(List<ShareCodeSectionVo> sectionList, String sectionName) {
        if (sectionList == null || sectionList.size() <= 0)
            return null;
        for (ShareCodeSectionVo section : sectionList) {
            if (sectionName.equals(section.getName())) {
                return section;
            }
        }
        return null;
    }

    // 查询年级
    private ShareCodeGradeVo getSameNameGrade(List<ShareCodeGradeVo> gradeList, String gradeName) {
        if (gradeList == null || gradeList.size() <= 0)
            return null;
        for (ShareCodeGradeVo grade : gradeList) {
            if (gradeName.equals(grade.getName())) {
                return grade;
            }
        }
        return null;
    }

    // 查询学科
    private ShareCodeSubjectVo getSameNameSubejct(List<ShareCodeSubjectVo> subjectList, String subjectName) {
        if (subjectList == null || subjectList.size() <= 0)
            return null;
        for (ShareCodeSubjectVo subject : subjectList) {
            if (subjectName.equals(subject.getName())) {
                return subject;
            }
        }
        return null;
    }

    public ShareAdminClass getAdminClass(List<ShareAdminClass> classList, String adminClassCode) {
        for (ShareAdminClass adminClass : classList) {
            if (adminClassCode.equals(adminClass.getClassCode())) {
                return adminClass;
            }
        }
        return null;
    }

    // 查询名称相同的班级
    private ShareAdminClass getSameNameAdminClass(List<ShareAdminClass> classList, String className, String gradeCode) {
        if (classList == null || classList.size() <= 0)
            return null;
        for (ShareAdminClass adminClass : classList) {
            if (className.equals(adminClass.getClassName()) && gradeCode.equals(adminClass.getGradeCode())) {
                return adminClass;
            }
        }
        return null;
    }

    private ShareAdminClassSubject getSameAdminClassSubject(List<ShareAdminClassSubject> adminClassSubjectList,
                    String gradeCode, String subjectCode, String teacherCode, String adminClassCode) {
        if (adminClassSubjectList == null || adminClassSubjectList.size() <= 0)
            return null;
        for (ShareAdminClassSubject adminClassSubject : adminClassSubjectList) {
            if (adminClassCode.equals(adminClassSubject.getAdminClassCode())) {
                if (gradeCode.equals(adminClassSubject.getGradeCode())
                                && subjectCode.equals(adminClassSubject.getSubjectCode().trim())
                                && teacherCode.equals(adminClassSubject.getTeacherCode())) {
                    return adminClassSubject;
                }
            }
        }
        return null;
    }

    public static List<String> getRepeatAccount(List<ShareUserLogin> loginList) {
        List<String> result = new ArrayList<String>();
        List<String> repeatList = new ArrayList<String>();
        for (ShareUserLogin loginUser : loginList) {
            if (StringUtils.isNotEmpty(loginUser.getLoginAccount())
                            && result.indexOf(loginUser.getLoginAccount().toLowerCase()) >= 0) {
                repeatList.add(loginUser.getLoginAccount());
            } else {
                result.add(loginUser.getLoginAccount().toLowerCase());
            }
        }
        return repeatList;
    }

    // 行政班级学科分析
    public List<String> adminClassSubjectAnalysis(String adminClassSubjectStr) {
        List<String> adminClassSubject = new ArrayList<String>();
        adminClassSubject = compareResult(adminClassSubjectStr, "([\\{|{][^(\\}|})]*[\\}|}])");
        return adminClassSubject;
    }

    // 正则匹配
    public List<String> compareResult(String srt, String regex) {
        if (StringUtils.isEmpty(srt))
            return null;
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(srt);

        List<String> adminClassSubject = new ArrayList<String>();
        while (match.find()) {
            adminClassSubject.add(match.group(1));
        }
        return adminClassSubject;
    }

    @Test
    public void test() {
        String s = "";
        s = s.replaceAll("\\{|\\}", "");
        String a[] = s.split(",|，");
        List<String> str = adminClassSubjectAnalysis("{1,2,3},{4,5,6}");
        String aString = "sdsd";
        /*
         * List<String> adminClassSubject=compareResult("1,2,3","\\,"); String aString="sdsd";
         */

    }

    /**
     * 查询老师列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareUserTeacherVo> getSimpleTeacherList(String orgCode) {
        List<Map<String, Object>> mapList = teacherDao.getSimpleTeacherList(orgCode);
        List<ShareUserTeacherVo> voList = ShareUserTeacherVo.mapListToVoList(mapList);
        return voList;
    }
}
