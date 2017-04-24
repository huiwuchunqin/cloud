package com.baizhitong.resource.manage.student.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.aspectj.bridge.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.encrypt.MD5;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.common.utils.GradeEntryYearMap;
import com.baizhitong.resource.common.utils.PrimaryKeyHelper;
import com.baizhitong.resource.common.utils.RegexUtil;
import com.baizhitong.resource.common.utils.Excel.ExcelRepeatCheck;
import com.baizhitong.resource.common.utils.Excel.ExcelRepeatCheck.CheckType;
import com.baizhitong.resource.common.utils.Excel.MessageHandel;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.dao.share.ShareAdminClassStudentDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassStudentDao;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.dao.share.ShareUserStudentDao;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.login.service.LoginService;
import com.baizhitong.resource.manage.student.action.StudentImportHelper;
import com.baizhitong.resource.manage.student.service.IStudentService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareAdminClassStudent;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserStudent;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;
import com.baizhitong.resource.model.vo.share.ShareUserStudentVo;
import com.baizhitong.utils.RegexUtils;
import com.baizhitong.utils.StringUtils;

@Service
public class StudentServiceImpl extends BaseService implements IStudentService {

    private @Autowired ShareUserStudentDao          studentDao;

    private @Autowired ShareUserLoginDao            userLoginDao;

    private @Autowired ShareAdminClassDao           adminClassDao;

    private @Autowired ShareAdminClassStudentDao    adminClassStudentDao;

    /***** 机构接口 *********/
    private @Autowired ICompanyService              companyService;

    /******* 年级接口 **********/
    private @Autowired GradeService                 gradeService;

    private @Autowired ShareTeachingClassStudentDao classStudentDao;

    private @Autowired ShareTeachingClassStudentDao teachingClassStudentDao;

    private @Autowired LoginService                 loginService;

    /**
     * 查询学生分页信息 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param userName 学生姓名
     * @param orgName 机构名称
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    public Page getStudentPageInfo(boolean noAdminClass, String studentNumber, String loginAccount, Integer type,
                    Integer adminClassStudentChoose, String teachingClassCode, String adminClassCode,
                    String sectionCode, String gradeCode, String userName, String orgName, String orgCode,
                    Integer pageSize, Integer pageNo) {
        List<Map<String, Object>> studentList = adminClassStudentDao.getAdminClassStudent(adminClassCode);
        String adminClassStuList = "";
        if (studentList != null && studentList.size() > 0) {
            for (Map student : studentList) {
                adminClassStuList = adminClassStuList + "'" + MapUtils.getString(student, "studentCode") + "'" + ",";
            }
            adminClassStuList = adminClassStuList.substring(0, adminClassStuList.length() - 1);
        }
        Page page = studentDao.getStudentPageInfo(noAdminClass, studentNumber, loginAccount, type,
                        adminClassStudentChoose, teachingClassCode, adminClassCode, sectionCode, gradeCode, userName,
                        orgName, orgCode, adminClassStuList, pageSize, pageNo);

        List<Map<String, Object>> mapList = page.getRows();
        if (mapList == null || mapList.size() <= 0) {
            return page;
        }
        List<ShareUserStudentVo> voList = ShareUserStudentVo.mapListToVoList(mapList);
        page.setRows(voList);
        return page;
    }

    /**
     * 新增或更新学生信息 ()<br>
     * 
     * @param student
     * @return
     */
    public ResultCodeVo addOrUpdateStudent(ShareUserStudent student, ShareUserLogin login) {
       
        CompanyInfoVo companyInfoVo =  getCompanyInfo();

        // 新增学生信息
        if (StringUtils.isEmpty(student.getGid())) {
            // 验证登录账号是否存在
            long exist = loginService.accountExitCheck(login.getLoginAccount());
            if (exist > 0) {
                return ResultCodeVo.errorCode("账号已经存在");
            }
            // 校验学号是否重复
            List<Map<String, Object>> studentNumberList = studentDao
                            .selectListByStudentNumber(companyInfoVo.getOrgCode(), student.getStudentNumber(), "");
            if (studentNumberList != null && studentNumberList.size() > 0) {
                return ResultCodeVo.errorCode("该学号已存在，请重新输入！");
            }
            // 校验手机号是否存在或者已被他人绑定
            if (StringUtils.isNotEmpty(login.getMobilePhone())) {
                List<ShareUserLogin> phoneList = userLoginDao.selectListByMobilePhone(login.getMobilePhone(), "");
                if (phoneList != null && phoneList.size() > 0) {
                    return ResultCodeVo.errorCode("该手机号已被他人使用，请重新输入！");
                } else {
                    login.setFlagPhoneValidate(CoreConstants.FLAG_PHONE_VALIDATE_NO);
                }
            }
            // 校验邮箱是否存在或者已被他人绑定
            if (StringUtils.isNotEmpty(login.getMail())) {
                List<ShareUserLogin> mailList = userLoginDao.selectListByMail(login.getMail(), "");
                if (mailList != null && mailList.size() > 0) {
                    return ResultCodeVo.errorCode("该邮箱已被他人使用，请重新输入！");
                } else {
                    login.setFlagMailValidate(CoreConstants.FLAG_MAIL_VALIDATE_NO);
                }
            }
            // 学生信息
            String userCode = PrimaryKeyHelper.getUserCode();
            student.setGid(UUID.randomUUID().toString());
            student.setStudentTypeCode("--");
            student.setStudentCode(userCode);
            student.setSysVer(0);
            student.setStatus(CoreConstants.USER_STATUS_EFFECTIVE);
            student.setOrgCode(companyInfoVo.getOrgCode());
            // 登录账号新增
            login.setUserCode(userCode);
            login.setLoginPwd(MD5.calcMD5(login.getLoginPwd()));
            login.setOrgCode(companyInfoVo.getOrgCode());
            login.setOrgName(companyInfoVo.getOrgName());
            login.setGid(UUID.randomUUID().toString());
            login.setUserName(student.getUserName());
            login.setModifyIP(getIp());
            login.setModifyPgm("studentService");
            login.setModifyTime(new Timestamp(new Date().getTime()));
            login.setUserRole(CoreConstants.USER_ROLE_STUDENT);
            login.setStatus(CoreConstants.USER_STATUS_EFFECTIVE);
            // 行政班级学生关系表新增
            ShareAdminClassStudent adminClassStudent = new ShareAdminClassStudent();
            adminClassStudent.setGid(UUID.randomUUID().toString());
            adminClassStudent.setOrgCode(companyInfoVo.getOrgCode());
            adminClassStudent.setGradeCode(student.getGradeCode());
            adminClassStudent.setAdminClassCode(student.getAdminClassCode());
            adminClassStudent.setAdminGroupCode("");
            adminClassStudent.setStudentCode(student.getStudentCode());
            adminClassStudent.setStudentName(student.getUserName());
            adminClassStudent.setRoleInGroup(0);
            adminClassStudent.setDispOrder(null);
            adminClassStudent.setMemo("");
            adminClassStudent.setModifyPgm("studentService");
            adminClassStudent.setModifyTime(new Timestamp(new Date().getTime()));
            adminClassStudent.setModifyIP(getIp());
            adminClassStudent.setSysVer(1);
            adminClassStudentDao.saveOne(adminClassStudent);

            // 把学生插入行政班级对应的教学班级中去
            teachingClassStudentDao.insertTeachingClassStudent(userCode, student.getUserName(),
                            student.getAdminClassCode(), getIp(getRequest()));
            try {
                userLoginDao.addLoginUser(login);
                // 学生信息
                student.setModifyIP(getIp());
                student.setModifyPgm("studentService");
                student.setModifyTime(new Timestamp(new Date().getTime()));
                return studentDao.addStudent(student) ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
            } catch (Exception e) {
                e.printStackTrace();
                return ResultCodeVo.errorCode("登录账号保存失败");
            }

        } else {
            // 登录账号修改
            ShareUserLogin old = userLoginDao.getUser(student.getStudentCode());
            ShareUserStudent oldStudent = studentDao.getStudentByCode(student.getStudentCode());
            // 校验学号是否重复
            List<Map<String, Object>> studentNumberList = studentDao.selectListByStudentNumber(
                            companyInfoVo.getOrgCode(), student.getStudentNumber(), student.getStudentCode());
            if (studentNumberList != null && studentNumberList.size() > 0) {
                return ResultCodeVo.errorCode("该学号已存在，请重新输入！");
            }
            // 校验手机号是否存在或者已被他人绑定
            if (StringUtils.isNotEmpty(login.getMobilePhone())) {
                List<ShareUserLogin> phoneList = userLoginDao.selectListByMobilePhone(login.getMobilePhone(),
                                student.getStudentCode());
                if (phoneList != null && phoneList.size() > 0) {
                    return ResultCodeVo.errorCode("该手机号已被他人使用，请重新输入！");
                } else {
                    old.setMobilePhone(login.getMobilePhone());
                    old.setFlagPhoneValidate(CoreConstants.FLAG_PHONE_VALIDATE_NO);
                }
            } else {
                // 取消手机号的绑定
                old.setMobilePhone(login.getMobilePhone());
                old.setFlagPhoneValidate(CoreConstants.FLAG_PHONE_VALIDATE_NO);
            }
            // 校验邮箱是否存在或者已被他人绑定
            if (StringUtils.isNotEmpty(login.getMail())) {
                List<ShareUserLogin> mailList = userLoginDao.selectListByMail(login.getMail(),
                                student.getStudentCode());
                if (mailList != null && mailList.size() > 0) {
                    return ResultCodeVo.errorCode("该邮箱已被他人使用，请重新输入！");
                } else {
                    old.setMail(login.getMail());
                    old.setFlagMailValidate(CoreConstants.FLAG_MAIL_VALIDATE_NO);
                }
            } else {
                // 取消邮箱的绑定
                old.setMail(login.getMail());
                old.setFlagMailValidate(CoreConstants.FLAG_MAIL_VALIDATE_NO);
            }
            old.setModifyIP(getIp());
            old.setModifyPgm("studentService");
            old.setUserName(login.getUserName());
            old.setGender(login.getGender());
            old.setModifyTime(new Timestamp(new Date().getTime()));
            if (!student.getAdminClassCode().equals(oldStudent.getAdminClassCode())) {
                /** 删除班级学生关系 */
                adminClassStudentDao.deleteShareAdminClassStudent(student.getStudentCode());
                /** 添加行政班级学生关系 */
                ShareAdminClassStudent adminClassStudent = new ShareAdminClassStudent();
                adminClassStudent.setGid(UUID.randomUUID().toString());
                adminClassStudent.setOrgCode(companyInfoVo.getOrgCode());
                adminClassStudent.setGradeCode(student.getGradeCode());
                adminClassStudent.setAdminClassCode(student.getAdminClassCode());
                adminClassStudent.setAdminGroupCode("");
                adminClassStudent.setStudentCode(student.getStudentCode());
                adminClassStudent.setStudentName(student.getUserName());
                adminClassStudent.setRoleInGroup(0);
                adminClassStudent.setDispOrder(null);
                adminClassStudent.setMemo("");
                adminClassStudent.setModifyPgm("studentService");
                adminClassStudent.setModifyTime(new Timestamp(new Date().getTime()));
                adminClassStudent.setModifyIP(getIp());
                adminClassStudent.setSysVer(1);
                adminClassStudentDao.saveOne(adminClassStudent);
                /** 删除教学班级学生 */
                teachingClassStudentDao.deleteShareTeachingClassStudent(student.getStudentCode());
                /** 把学生插入行政班级对应的教学班级中去 */
                teachingClassStudentDao.insertTeachingClassStudent(student.getStudentCode(), student.getUserName(),
                                student.getAdminClassCode(), getIp(getRequest()));
            }
            try {
                userLoginDao.addLoginUser(old);
                // 学生信息
                student.setModifyIP(getIp());
                student.setModifyPgm("studentService");
                student.setModifyTime(new Timestamp(new Date().getTime()));
                studentDao.updateStudent(student.getUserName(), student.getStudentCode(), student.getStudentNumber(),
                                student.getGradeCode(), student.getTermCode(), student.getAdminClassCode(),
                                student.getEnterSchoolDate(), student.getGender(), getIp(),
                                new Timestamp(new Date().getTime()), "studentService");
                return studentDao.addStudent(student) ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ResultCodeVo.errorCode("登录账号保存失败");
            }
        }

    }

    /**
     * 导入学生 ()<br>
     * 
     * @param sheet
     * @return
     */
    public ResultCodeVo importStudent(Sheet sheet) {
        try {
           
            CompanyInfoVo company =  getCompanyInfo();
            String orgCode = company.getOrgCode();
            String orgName = company.getOrgName();
            Integer domainID = company.getDomainID();
            String studyYearCode=getStudyYearCode();
            // 机构所有的行政班级
            List<ShareAdminClass> adminClassList = adminClassDao.getAdminClassList(company.getOrgCode());
            // 机构所有的账号信息
            List<Map<String, Object>> globalAccountList = null;
            if (SystemConfig.agentEnable) {
                globalAccountList = userLoginDao.getSameDomainAccountName(domainID);
            } else {
                globalAccountList = userLoginDao.getOrgAccountName(company.getOrgCode());
            }
            // 机构学段
            List<ShareCodeSectionVo> orgSectionList = companyService.getCompanySection(company.getOrgCode());
            // 机构年级
            List<ShareCodeGradeVo> orgGradeList = new ArrayList<ShareCodeGradeVo>();

            // 学生列表
            List<ShareUserStudent> studentList = new ArrayList<ShareUserStudent>();
            // 登录账户列表
            List<ShareUserLogin> accountList = new ArrayList<ShareUserLogin>();

            // 行政班级学生关系
            List<ShareAdminClassStudent> adminClassStudentList = new ArrayList<ShareAdminClassStudent>();

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

            int studentNameIndex = -1, studentNumberIndex = -1, genderIndex = -1, mobileIndex = -1, mailIndex = -1,
                            loginAccountIndex = -1, passwordIndex = -1, adminClassIndex = -1, 
                            sectionIndex = -1, gradeIndex = -1,studentHardNoIndex=-1;

            HashSet<Integer> set = new HashSet<Integer>();
            Row firstRow = sheet.getRow(0);
            for (int i = firstRow.getFirstCellNum(); i <= firstRow.getLastCellNum(); i++) {
                if (firstRow.getCell(i) == null) {
                    continue;
                }
                String columnName = firstRow.getCell(i).toString();
                if (columnName.contains("姓名")) {
                    studentNameIndex = i;
                    set.add(studentNameIndex);
                }
                if (columnName.contains("年级")) {
                    gradeIndex = i;
                    set.add(gradeIndex);
                }
                if (columnName.contains("学号")) {
                    studentNumberIndex = i;
                    set.add(studentNumberIndex);
                }
                if (columnName.contains("学段")) {
                    sectionIndex = i;
                    set.add(sectionIndex);
                }
                if ("性别".equals(columnName)) {
                    genderIndex = i;
                    set.add(genderIndex);
                }
               
                if ("手机号".equals(columnName)) {
                    mobileIndex = i;
                    set.add(mobileIndex);
                }
                if ("邮箱".equals(columnName)) {
                    mailIndex = i;
                    set.add(mailIndex);
                }
                if (columnName.contains("账号")) {
                    loginAccountIndex = i;
                    set.add(loginAccountIndex);
                }
                if (columnName.contains("密码")) {
                    passwordIndex = i;
                    set.add(passwordIndex);
                }
                if (columnName.contains("班级") && columnName.startsWith("班级")) {
                    adminClassIndex = i;
                    set.add(adminClassIndex);
                }
                if(columnName.contains("硬件号")){
                    studentHardNoIndex=i;
                    set.add(studentHardNoIndex);
                }
            }
            if (set.size() < 11) {
                return ResultCodeVo.errorCode("模板不正确请联系管理员");
            }
            
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (ExcelUtils.checkRowNull(row)) {
                    continue;
                }

                // 学生信息
                String userCode = PrimaryKeyHelper.getUserCode();
                ShareUserStudent student = new ShareUserStudent();
                ShareUserLogin login = new ShareUserLogin();

                /************** 学生信息 ******************/
                // 准备数据
                String studentNumber = ExcelUtils.changeString(row.getCell(studentNumberIndex));
                String userName = ExcelUtils.changeString(row.getCell(studentNameIndex));
                String sectionName = ExcelUtils.changeString(row.getCell(sectionIndex));
                String gradeName = ExcelUtils.changeString(row.getCell(gradeIndex));
                String genderStr = ExcelUtils.changeString(row.getCell(genderIndex));
                Integer gender = null;
                if ("男".equals(genderStr)) {
                    gender = 1;
                } else if ("女".equals(genderStr)) {
                    gender = 2;
                }
                String adminClassName = ExcelUtils.changeString(row.getCell(adminClassIndex));
                String loginAccount = ExcelUtils.changeString(row.getCell(loginAccountIndex));
                String mail = ExcelUtils.changeString(row.getCell(mailIndex));
                String mobile = ExcelUtils.changeString(row.getCell(mobileIndex));
                String password = ExcelUtils.changeString(row.getCell(passwordIndex));
                String studentHardNo= ExcelUtils.changeString(row.getCell(studentHardNoIndex));
                /** -----------------------非空校验--------------------------- */
                if (StringUtils.isEmpty(studentNumber)) {
                    return ResultCodeVo.errorCode("学号不能为空！");
                }
                if (StringUtils.isEmpty(userName)) {
                    return ResultCodeVo.errorCode("学生姓名不能为空！");
                }
                if (StringUtils.isEmpty(sectionName)) {
                    return ResultCodeVo.errorCode("学段不能为空！");
                }
                if (StringUtils.isEmpty(gradeName)) {
                    return ResultCodeVo.errorCode("年级不能为空！");
                }
                if (StringUtils.isEmpty(adminClassName)) {
                    return ResultCodeVo.errorCode("班级名称不能为空！");
                }
                if (StringUtils.isEmpty(loginAccount)) {
                    return ResultCodeVo.errorCode("登录账号不能为空！");
                }
                /** -----------------------账号检验--------------------------- */
                if (!loginAccount.matches(RegexUtil.REG_ACCOUNT)) {
                    return ResultCodeVo.errorCode("请输入正确的账号（0~50位半角英数字）");
                }

                /** -----------------------姓名检验--------------------------- */
                if (!RegexUtil.valideLength(userName, 0, 30)) {
                    return ResultCodeVo.errorCode("姓名的长度不能超过30位");
                }

                /** --------------------邮箱校验--------------------------- */
                if (!RegexUtils.test(mail, RegexUtils.email) && StringUtils.isNotEmpty(mail)) {
                    return ResultCodeVo.errorCode(mail + "邮箱格式不对");
                }
                if (!RegexUtil.valideLength(mail, 0, 50)) {
                    return ResultCodeVo.errorCode(mail + "邮箱长度不能超过50位");
                }

                /** --------------------手机校验--------------------------- */
                if (!RegexUtils.test(mobile, RegexUtils.mobile) && StringUtils.isNotEmpty(mobile)) {
                    return ResultCodeVo.errorCode(mobile + "手机号格式不对");
                }

               

                if (StringUtils.isEmpty(password)) {
                    password = CoreConstants.DEFAULT_STUDENT_PWD;
                } else {
                    if (!password.matches(RegexUtil.REG_PASSWORD)) {
                        return ResultCodeVo.errorCode("密码不能输入中文及空格且长度为 6~20");
                    }
                }

                /** --------------------学号校验--------------------------- */
                if (!studentNumber.matches("^[0-9a-zA-Z]{0,22}$")) {
                    return ResultCodeVo.errorCode("请输入正确学号(0~22位半角英数字)");
                }

                /** --------------------学段-------------------------- */
                ShareCodeSectionVo section = getSameNameSection(orgSectionList, sectionName);
                if (section == null) {
                    return ResultCodeVo.errorCode("学段" + sectionName + "不存在");
                }

                /** --------------------年级-------------------------- */
                ShareCodeGradeVo grade = getSameNameGrade(orgGradeList, gradeName);
                if (grade == null) {
                    return ResultCodeVo.errorCode("年级" + gradeName + "不存在");
                }
                
                /** --------------------入学年份校验--------------------------- */
                String entryYear;
                try {
                    entryYear = GradeEntryYearMap.getEntryYear(grade.getCode(),studyYearCode);
                } catch (Exception e) {
                    return ResultCodeVo.errorCode("年级" + gradeName + "不存在");
                }
                /** --------------------硬件号---------------------------*/
                if(StringUtils.isNotEmpty(studentHardNo)){
                    if(!studentHardNo.matches("^[0-9a-zA-Z]{0,50}$")){
                        return ResultCodeVo.errorCode("请输入正确硬件号(0~50位半角英数字)");  
                    }
                  
                }
                ShareAdminClass theSameClass = getSameNameAdminClass(adminClassList, adminClassName, entryYear);
                if (theSameClass == null) {
                    if (!RegexUtil.valideLength(adminClassName, 0, 20)) {
                        return ResultCodeVo.errorCode("行政班级名称的长度不能超过20位");
                    }
                    ShareAdminClass adminClass = new ShareAdminClass();
                    adminClass.setGid(UUID.randomUUID().toString());
                    // 获取行政班级编码
                    Integer currenMax = Integer.parseInt(
                                        adminClassDao.getMax(company.getOrgCode(), Integer.parseInt(entryYear)));
                    String adminClassCode = PrimaryKeyHelper.getAdminClassCode(company.getOrgCode(), section.getCode(),
                                    Integer.parseInt(entryYear), currenMax);
                    adminClass.setClassCode(adminClassCode);
                    adminClass.setOrgCode(company.getOrgCode());
                    adminClass.setGradeCode(grade.getCode());
                    adminClass.setEntryYear(Integer.parseInt(entryYear));
                    adminClass.setClassName(adminClassName);
                    adminClass.setClassStatus(CoreConstants.CLASS_STATUS__NOT_GRADUATE);
                    adminClass.setModifyPgm("AdminClassService");
                    adminClass.setModifyIP(getIp());
                    adminClass.setModifyTime(new Timestamp(new Date().getTime()));
                    adminClass.setSysVer(0);
                    adminClassList.add(adminClass);
                    adminClassDao.addOrUpdate(adminClass);
                    theSameClass = adminClass;
                }
                student.setAdminClassCode(theSameClass.getClassCode());
                student.setGradeCode(theSameClass.getGradeCode());
                student.setStudentNumber(studentNumber);
                student.setUserName(userName);
                student.setGender(gender);
                student.setGid(UUID.randomUUID().toString());
                student.setModifyIP("127.0.0.1");
                student.setModifyPgm("studentService");
                student.setModifyTime(new Timestamp(new Date().getTime()));
                student.setOrgCode(orgCode);
                student.setStatus(1);
                student.setStudentCode(userCode);
                student.setSysVer(0);
                student.setTermCode("");
                student.setStudentHardNo(studentHardNo);
                studentList.add(student);
                /************* 登录信息 ******************/
                login.setGender(gender);
                login.setGid(UUID.randomUUID().toString());
                login.setLoginAccount(loginAccount);
                login.setLoginPwd(MD5.calcMD5(password));
                login.setMail(mail);
                login.setMobilePhone(mobile);
                login.setOrgCode(orgCode);
                login.setModifyIP("127.0.0.1");
                login.setModifyPgm("studentService");
                login.setModifyTime(new Timestamp(new Date().getTime()));
                login.setOrgName(orgName);
                login.setStatus(1);
                login.setUserCode(userCode);
                login.setUserName(userName);
                login.setUserRole(CoreConstants.USER_ROLE_STUDENT);
                accountList.add(login);
                /***************** 学生行政班级关系 ******************************/
                ShareAdminClassStudent adminClassStudent = new ShareAdminClassStudent();
                adminClassStudent.setGid(UUID.randomUUID().toString());
                adminClassStudent.setOrgCode(orgCode);
                adminClassStudent.setGradeCode(student.getGradeCode());
                adminClassStudent.setAdminClassCode(student.getAdminClassCode());
                adminClassStudent.setAdminGroupCode("");
                adminClassStudent.setStudentCode(student.getStudentCode());
                adminClassStudent.setStudentName(student.getUserName());
                adminClassStudent.setRoleInGroup(0);
                adminClassStudent.setDispOrder(null);
                adminClassStudent.setMemo("");
                adminClassStudent.setModifyPgm("StudentService");
                adminClassStudent.setModifyTime(new Timestamp(new Date().getTime()));
                adminClassStudent.setModifyIP(getIp());
                adminClassStudent.setSysVer(1);
                adminClassStudentList.add(adminClassStudent);
            }

            ExcelRepeatCheck repeatCheck = new ExcelRepeatCheck();
            String phoneRepeatMsg = "";
            String mailRepeatMsg = "";
            String loginAccountRepeatMsg = "";
            String studentNumberRepeatMsg = "";
            String studentHardNoRepeatMsg="";
            // 判断导入列表里面数据是否重复
            for (ShareUserLogin login : accountList) {
                Map<String, Object> sameNameAccount = getSameNameLoginAccount(globalAccountList,
                                login.getLoginAccount());
                if (sameNameAccount != null) {
                    loginAccountRepeatMsg = loginAccountRepeatMsg + login.getLoginAccount() + ",";
                }
                repeatCheck.addData(CheckType.loginAccount, login.getLoginAccount());

                if (StringUtils.isNotEmpty(login.getMail())) {
                    // 校验邮箱是否存在或者已被他人绑定
                    List<ShareUserLogin> mailList = userLoginDao.selectListByMail(login.getMail(), null);
                    if (mailList != null && mailList.size() > 0) {
                        mailRepeatMsg = mailRepeatMsg + login.getMail() + ",";
                    }
                    repeatCheck.addData(CheckType.Email, login.getMail());
                }

                if (StringUtils.isNotEmpty(login.getMobilePhone())) {
                    // 校验手机号是否存在或者已被他人绑定
                    List<ShareUserLogin> phoneList = userLoginDao.selectListByMobilePhone(login.getMobilePhone(), "");
                    if (phoneList != null && phoneList.size() > 0) {
                        phoneRepeatMsg = phoneRepeatMsg + login.getMobilePhone() + ",";
                    }
                    repeatCheck.addData(CheckType.Mobile, login.getMobilePhone());
                }
            }

            for (ShareUserStudent student : studentList) {
                
                //学号
                Integer count = studentDao.existStudentStudentNo(student.getStudentNumber(), orgCode);
                if (count > 0) {
                    studentNumberRepeatMsg = studentNumberRepeatMsg + student.getStudentNumber() + ",";
                }
                
                //硬件号
                repeatCheck.addData(CheckType.studentNumber, student.getStudentNumber());
                if(StringUtils.isNotEmpty(student.getStudentHardNo())){
                    //硬件号
                    Integer hardNoCount=studentDao.existStudentHardNo(student.getStudentHardNo(), orgCode);
                    //校验硬件号是否存在或者已被他人绑定
                   if (hardNoCount > 0) {
                       studentHardNoRepeatMsg=studentHardNoRepeatMsg+student.getStudentHardNo()+",";
                   } 
                   repeatCheck.addData(CheckType.studentHardNumber, student.getStudentHardNo());
                }
            }

            /************ 导入数据中重复的 *************/
            String excelRepeat = repeatCheck.getCheckMsg();
            if (StringUtils.isNotEmpty(excelRepeat)) {
                return ResultCodeVo.errorCode(excelRepeat);
            }

            /************ 输入错误信息 *************/
            StringBuffer errorMsg = new StringBuffer();
            errorMsg.append(MessageHandel.messageHandler(loginAccountRepeatMsg,"账号","已被使用"))
                    .append(MessageHandel.messageHandler(studentNumberRepeatMsg,"学号","已被使用"))
                    .append(MessageHandel.messageHandler(mailRepeatMsg,"邮箱","已被使用"))
                    .append(MessageHandel.messageHandler(phoneRepeatMsg,"手机号","已被使用"))
                    .append(MessageHandel.messageHandler(studentHardNoRepeatMsg,"硬件号","已被使用"));
            
            if (StringUtils.isNotEmpty(errorMsg.toString())) {
                return ResultCodeVo.errorCode(errorMsg.toString());
            }
            int batchCount = 500;
            int n = batchCount;
            ExecutorService service = Executors.newCachedThreadPool();
            List<StudentImportHelper> allTead = new ArrayList<StudentImportHelper>();
            while (n < accountList.size()) {
                // 学生列表
                List<ShareUserStudent> theadStuList = new ArrayList<ShareUserStudent>();
                // 登录账户列表
                List<ShareUserLogin> theadAccountList = new ArrayList<ShareUserLogin>();
                // 学生行政班级关系
                List<ShareAdminClassStudent> shareAdminClassStudentList = new ArrayList<ShareAdminClassStudent>();
                for (int i = n - batchCount; i < n; i++) {
                    theadStuList.add(studentList.get(i));
                    theadAccountList.add(accountList.get(i));
                    shareAdminClassStudentList.add(adminClassStudentList.get(i));
                }
                StudentImportHelper thead = new StudentImportHelper(studentDao, userLoginDao, adminClassStudentDao,
                                theadStuList, theadAccountList, shareAdminClassStudentList);
                allTead.add(thead);
                n += batchCount;
            }
            /************** 数据没导完 *******************/
            // 学生列表
            List<ShareUserStudent> theadStuList = new ArrayList<ShareUserStudent>();
            // 登录账户列表
            List<ShareUserLogin> theadAccountList = new ArrayList<ShareUserLogin>();
            // 学生行政班级关系
            List<ShareAdminClassStudent> shareAdminClassStudentList = new ArrayList<ShareAdminClassStudent>();
            for (int i = n - batchCount; i < accountList.size(); i++) {
                theadStuList.add(studentList.get(i));
                theadAccountList.add(accountList.get(i));
                shareAdminClassStudentList.add(adminClassStudentList.get(i));
            }
            StudentImportHelper thead = new StudentImportHelper(studentDao, userLoginDao, adminClassStudentDao,
                            theadStuList, theadAccountList, shareAdminClassStudentList);
            allTead.add(thead);
            try {
                service.invokeAll(allTead);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                service.shutdown();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return ResultCodeVo.errorCode("导入异常");
        }
        return ResultCodeVo.rightCode("导入成功");
    }

    // 查询名称相同的班级
    private ShareAdminClass getSameNameAdminClass(List<ShareAdminClass> classList, String className, String entryYear) {
        if (classList == null || classList.size() <= 0) {
            return null;
        }
        for (ShareAdminClass adminClass : classList) {
            if (className.equals(adminClass.getClassName()) && entryYear.equals(adminClass.getEntryYear().toString())) {
                return adminClass;
            }
        }
        return null;
    }

    // 查询学段
    private ShareCodeSectionVo getSameNameSection(List<ShareCodeSectionVo> sectionList, String sectionName) {
        if (sectionList == null || sectionList.size() <= 0) {
            return null;
        }
        for (ShareCodeSectionVo section : sectionList) {
            if (sectionName.equals(section.getName())) {
                return section;
            }
        }
        return null;
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

    /**
     * 删除学生 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ResultCodeVo deleteStudent(String userCode) {
        int stuCount = studentDao.delete(userCode);
        int loginCount = userLoginDao.deleteLoginUserByUserCode(userCode);
        classStudentDao.deleteStudent(userCode, "");
        adminClassStudentDao.deleteShareAdminClassStudent(userCode);
        return (stuCount > 0 && loginCount > 0) ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }

    /**
     * 查询学生列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareUserStudentVo> getSimpleStudentList(String orgCode) {
        List<Map<String, Object>> mapList = studentDao.getSimpleStudentList(orgCode);
        List<ShareUserStudentVo> voList = ShareUserStudentVo.mapListToVoList(mapList);
        return voList;
    }
}
