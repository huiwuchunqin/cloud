package com.baizhitong.resource.manage.furtherEducation.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.dao.share.ShareAdminClassStudentDao;
import com.baizhitong.resource.dao.share.ShareAdminClassSubjectDao;
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.dao.share.ShareOrgYearTermDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.ShareUserStudentDao;
import com.baizhitong.resource.manage.furtherEducation.action.WebSocket;
import com.baizhitong.resource.manage.furtherEducation.service.IFurtherEducationService;
import com.baizhitong.resource.manage.shareTeachingClass.service.IShareTeachingClassService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareCodeGrade;
import com.baizhitong.resource.model.share.ShareOrgYearTerm;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.vo.basic.AdminClassVo;
import com.baizhitong.utils.BeanUtils;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;

/**
 * 
 * 机构升学服务实现类 FurtherEducationServiceImpl TODO
 * 
 * @author creator zhangqd 2016年9月9日 下午2:42:58
 * @author updater
 *
 * @version 1.0.0
 */

@Service(value = "furtherEducationService")
public class FurtherEducationServiceImpl extends BaseService implements IFurtherEducationService {
    /**
     * 行政班级dao接口
     */
    @Autowired
    private ShareAdminClassDao         shareAdminClassDao;
    /**
     * 当前平台信息dao接口
     */
    @Autowired
    private SharePlatformDao           sharePlatformDao;
    /**
     * 学生信息dao接口
     */
    @Autowired
    private ShareUserStudentDao        shareUserStudentDao;
    /**
     * 行政班级学生dao接口
     */
    @Autowired
    private ShareAdminClassStudentDao  shareAdminClassStudentDao;
    /**
     * 行政班级学科dao接口
     */
    @Autowired
    private ShareAdminClassSubjectDao  shareAdminClassSubjectDao;
    /**
     * 机构学年学期dao接口
     */
    @Autowired
    private ShareOrgYearTermDao        shareOrgYearTermDao;
    /**
     * 课程班级接口
     */
    @Autowired
    private IShareTeachingClassService shareTeachingClassService;
    @Autowired
    private ShareCodeGradeDao          gradeDao;

    /**
     * 查询需要更新的行政班级 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List getAdminClassToUpdate(String orgCode) {
        String studyYearCode = getStudyYearCode();

        String[][] grades = { { "100", "101", "102", "103" }, { "201", "202", "203", "204", "205", "206" },
                        { "301", "302", "303" }, { "401", "402", "403" } };
        // 获取当前机构所有行政班级信息
        List<ShareAdminClass> shareAdminClasses = shareAdminClassDao.getAdminClassNotGraduatedList(orgCode);
        List<ShareAdminClass> toUp = new ArrayList<ShareAdminClass>();
        List<AdminClassVo> toUpVO = new ArrayList<AdminClassVo>();
        if (!shareAdminClasses.isEmpty()) {
            for (ShareAdminClass adminClass : shareAdminClasses) {
                Integer sectionCode = Integer.parseInt(adminClass.getGradeCode().substring(0, 1));
                if (adminClass.getEntryYear() == null) {
                    continue;
                }
                int pastYear = Integer.parseInt(studyYearCode) - adminClass.getEntryYear().intValue();
                if (pastYear < 0) {
                    continue;
                }
                String gradeCodeToUp = grades[sectionCode - 1][pastYear];
                if (StringUtils.isEmpty(gradeCodeToUp) || !gradeCodeToUp.equals(adminClass.getGradeCode())) {
                    toUp.add(adminClass);
                    AdminClassVo vo = new AdminClassVo();
                    try {
                        BeanUtils.copyProperties(vo, adminClass);
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    ShareCodeGrade grade = gradeDao.getGrade(adminClass.getGradeCode());
                    ShareCodeGrade gradeToUp = gradeDao.getGrade(gradeCodeToUp);
                    vo.setGradeName(grade.getName());
                    vo.setGradeToUp(gradeToUp.getName());
                    toUpVO.add(vo);
                }
            }
            return toUpVO;
        }
        return new ArrayList();
    }

    // 更新行政班级

    public ResultCodeVo updateAdminClass(String orgCode) {
        String studyYearCode = getStudyYearCode();

        String[][] grades = { { "100", "101", "102", "103" }, { "201", "202", "203", "204", "205", "206" },
                        { "301", "302", "303" }, { "401", "402", "403" } };
        String[][] gradesName = { { "学前", "小班", "中班", "大班" }, { "一年级", "二年级", "三年级", "四年级", "五年级", "六年级" },
                        { "七年级", "八年级", "九年级", "初一", "初二", "初三" }, { "高一", "高二", "高三" } };
        // 获取当前机构所有行政班级信息
        List<ShareAdminClass> shareAdminClasses = shareAdminClassDao.getAdminClassNotGraduatedList(orgCode);
        if (!shareAdminClasses.isEmpty()) {
            int progress = 1;
            for (ShareAdminClass adminClass : shareAdminClasses) {
                Integer sectionCode = Integer.parseInt(adminClass.getGradeCode().substring(0, 1));
                int pastYear = Integer.parseInt(studyYearCode) - adminClass.getEntryYear().intValue();
                if (pastYear < 0) {
                    continue;
                }
                String gradeCode = grades[sectionCode - 1][pastYear];
                if (!gradeCode.equals(adminClass.getGradeCode())) {
                    // 毕业
                    if (StringUtils.isEmpty(gradeCode)) {
                        // 调用方法修改班级状态
                        shareAdminClassDao.updateStatus(adminClass.getClassCode());
                    } else if (!gradeCode.equals(adminClass.getGradeCode())) {
                        String className = "";
                        if ("初一初二初三".indexOf(adminClass.getClassName()) >= 0) {
                            className = gradesName[sectionCode - 1][pastYear + 2];
                        } else {
                            className = gradesName[sectionCode - 1][pastYear];
                        }

                        // 修改行政班级名称等信息(六年级、初三、高三的改为已毕业)
                        shareAdminClassDao.updateGradeInfo(gradeCode, className.toString(), adminClass.getClassCode(),
                                        new Timestamp(System.currentTimeMillis()));
                        // 修改学生表的年级信息
                        shareUserStudentDao.updateGradeInfo(gradeCode, adminClass.getClassCode());
                        // 修改行政班级学生表的年级信息
                        shareAdminClassStudentDao.updateGradeInfo(gradeCode, adminClass.getClassCode());
                        // 修改行政班级学科表的年级信息、开始时间、结束时间(在修改这个表之前插入历史表)
                        shareAdminClassSubjectDao.updateGradeInfo(null, null, gradeCode, adminClass.getClassCode(),
                                        new Timestamp(System.currentTimeMillis()));
                    }
                }
                try {
                    WebSocket.session.getBasicRemote().sendObject(progress / shareAdminClasses.size() * 100);
                   
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (EncodeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                    progress++;
            }
            try {
                WebSocket.session.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
       
        return ResultCodeVo.rightCode("升级成功");
    }

    /**
     * 查询有任教信息需要更新的行政班级 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List getAdminClassWidthTeaInfo(String orgCode) {
        return shareAdminClassDao.getClassHavingNoTClass(orgCode);
    }

    /**
     * 更新任教信息 ()<br>
     * 
     * @return
     */
    public ResultCodeVo updateTeachingInfo() {
        // 调用接口实现教学班级升学
        shareTeachingClassService.importDefault();

        return ResultCodeVo.rightCode("升级成功");

    }

    @Override
    public ResultCodeVo updateStudyYearTerm(String orgCode) {
        // 获取当前平台信息
        // 获取当前平台信息
        SharePlatform sharePlatform = sharePlatformDao.getByCodeGlobal();
        int currentYear = TimeUtils.getYear();
        int currentMonth = TimeUtils.getMonth();
        int currentStudyYear = Integer.parseInt("1");
        if ((currentMonth >= 9 && currentYear == currentStudyYear)
                        || (currentMonth < 9 && currentYear == currentStudyYear + 1)) {
            // 获取当前机构所有行政班级信息
            List<ShareAdminClass> shareAdminClasses = shareAdminClassDao.getAdminClassNotGraduatedList(orgCode);
            // // 获取当前平台信息
            // SharePlatform sharePlatform = sharePlatformDao.getByCodeGlobal();
            // // 当前学年信息
            // int currentStudyYear = Integer.parseInt(sharePlatform.getCurrentStudyYearCode());
            // 当前学期信息
            // int currentTerm = Integer.parseInt(sharePlatform.getCurrentTermCode());
            // 根据当前学年学期信息得出学期开始时间与结束时间
            ShareOrgYearTerm shareOrgYearTerm = null;
            for (ShareAdminClass shareAdminClass : shareAdminClasses) {
                // 根据入学年份、当前学年计算出当前应该是几年级(gradeCode)
                int grade = ((currentStudyYear - shareAdminClass.getEntryYear().intValue()) + 1);
                String gradeCode = shareAdminClass.getGradeCode().substring(0, 2) + grade + "";
                // 获取出新的班级名称
                String className = null;
                if (gradeCode.equals(shareAdminClass.getGradeCode())) {// 不用升学
                    continue;
                } else {// 需要升学
                    if (shareAdminClass.getGradeCode().substring(0, 1).equals("2")) {// 小学
                        className = this.getNewClassName(grade, shareAdminClass.getClassName(), "2");
                        shareOrgYearTerm = shareOrgYearTermDao.getTerm("1", orgCode, "2");
                        if (shareAdminClass.getGradeCode().substring(2, 3).equals("6")) {// 改为已经毕业
                            // 调用方法修改班级状态
                            shareAdminClassDao.updateStatus(shareAdminClass.getClassCode());
                            continue;
                        }
                    } else if (shareAdminClass.getGradeCode().substring(0, 1).equals("3")) {// 初中
                        className = this.getNewClassName(grade, shareAdminClass.getClassName(), "3");
                        shareOrgYearTerm = shareOrgYearTermDao.getTerm("1", orgCode, "3");
                        if (shareAdminClass.getGradeCode().substring(2, 3).equals("3")) {// 改为已经毕业
                            // 调用方法修改班级状态
                            shareAdminClassDao.updateStatus(shareAdminClass.getClassCode());
                            continue;
                        }
                    } else if (shareAdminClass.getGradeCode().substring(0, 1).equals("4")) {// 高中
                        className = this.getNewClassName(grade, shareAdminClass.getClassName(), "4");
                        shareOrgYearTerm = shareOrgYearTermDao.getTerm("1", orgCode, "4");
                        if (shareAdminClass.getGradeCode().substring(2, 3).equals("3")) {// 改为已经毕业
                            // 调用方法修改班级状态
                            shareAdminClassDao.updateStatus(shareAdminClass.getClassCode());
                            continue;
                        }
                    }
                    // 保存行政班级学科表历史记录
                    shareAdminClassSubjectDao.insert(shareAdminClass.getClassCode());
                }
                // 修改行政班级名称等信息(六年级、初三、高三的改为已毕业)
                shareAdminClassDao.updateGradeInfo(gradeCode, className.toString(), shareAdminClass.getClassCode(),
                                new Timestamp(System.currentTimeMillis()));
                // 修改学生表的年级信息
                shareUserStudentDao.updateGradeInfo(gradeCode, shareAdminClass.getClassCode());
                // 修改行政班级学生表的年级信息
                shareAdminClassStudentDao.updateGradeInfo(gradeCode, shareAdminClass.getClassCode());
                // 修改行政班级学科表的年级信息、开始时间、结束时间(在修改这个表之前插入历史表)
                shareAdminClassSubjectDao.updateGradeInfo(shareOrgYearTerm.getStartDate(),
                                shareOrgYearTerm.getEndDate(), gradeCode, shareAdminClass.getClassCode(),
                                new Timestamp(System.currentTimeMillis()));
            }
            // 调用接口实现教学班级升学
            shareTeachingClassService.importDefault();
        } else {
            return ResultCodeVo.rightCode("请先升级平台学年学期信息后再升级年级!");
        }
        return ResultCodeVo.rightCode("升学成功");
    }

    private String getNewClassName(int grade, String oldClassName, String sectionCode) {
        String newClassName = null;
        StringBuffer className = new StringBuffer(oldClassName);
        String[] grades = { "一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "七年级", "八年级", "九年级", "初一", "初二", "初三", "高一", "高二",
                        "高三" };
        if (sectionCode.equals("3")) {
            newClassName = grades[grade + 5];
            if (oldClassName.contains("年级")) {
                for (int i = 5; i < 9; i++) {
                    if (oldClassName.contains(grades[i])) {
                        className.replace(oldClassName.indexOf(grades[i]),
                                        oldClassName.indexOf(grades[i]) + grades[i].length(), newClassName);
                        break;
                    }
                }
            } else {
                newClassName = grades[grade + 8];
                for (int i = 0; i < 15; i++) {
                    if (oldClassName.contains(grades[i])) {
                        className.replace(oldClassName.indexOf(grades[i]),
                                        oldClassName.indexOf(grades[i]) + grades[i].length(), newClassName);
                        break;
                    }
                }
            }
        } else if (sectionCode.equals("2")) {
            newClassName = grades[grade - 1];
            for (int i = 0; i < 9; i++) {
                if (oldClassName.contains(grades[i])) {
                    className.replace(oldClassName.indexOf(grades[i]),
                                    oldClassName.indexOf(grades[i]) + grades[i].length(), newClassName);
                    break;
                }
            }
        } else {
            newClassName = grades[grade + 11];
            for (int i = 0; i < 15; i++) {
                if (oldClassName.contains(grades[i])) {
                    className.replace(oldClassName.indexOf(grades[i]),
                                    oldClassName.indexOf(grades[i]) + grades[i].length(), newClassName);
                    break;
                }
            }
        }
        return className.toString();
    }

}