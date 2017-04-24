package com.baizhitong.resource.manage.teacher.action;

import java.util.List;
import java.util.concurrent.Callable;

import com.baizhitong.resource.dao.share.ShareAdminClassSubjectDao;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.dao.share.ShareUserTeacherDao;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserTeacher;

/**
 * 老师导入帮助类 TeacherImportHelper TODO
 * 
 * @author creator BZT 2016年6月1日 下午4:46:09
 * @author updater
 *
 * @version 1.0.0
 */
public class TeacherImportHelper implements Callable<String> {
    // 登录dao
    private ShareUserLoginDao            userLoginDao;
    // 老师dao
    private ShareUserTeacherDao          teacherDao;
    // 行政班级学科dao
    private ShareAdminClassSubjectDao    adminClassSubjectDao;
    // 老师列表
    private List<ShareUserTeacher>       teacherList;
    // 登录账户列表
    private List<ShareUserLogin>         accountList;
    // 行政班级学科列表
    private List<ShareAdminClassSubject> adminClassSubjectList;

    public TeacherImportHelper(ShareUserLoginDao userLoginDao,
                               ShareUserTeacherDao teacherDao,
                               ShareAdminClassSubjectDao adminClassSubjectDao,
                               List<ShareUserTeacher> teacherList,
                               List<ShareUserLogin> accountList,
                               List<ShareAdminClassSubject> adminClassSubjectList) {
        this.userLoginDao = userLoginDao;
        this.teacherDao = teacherDao;
        this.teacherList = teacherList;
        this.accountList = accountList;
        this.adminClassSubjectDao = adminClassSubjectDao;
        this.adminClassSubjectList = adminClassSubjectList;
    }

    public String call() {
        // 行政班级学科新增
        if (adminClassSubjectList != null && adminClassSubjectList.size() > 0) {
            for (ShareAdminClassSubject adminClassSubject : adminClassSubjectList) {
                adminClassSubjectDao.save(adminClassSubject);
            }
        }
        // 老师新增
        if (teacherList != null && teacherList.size() > 0) {
            for (ShareUserTeacher teacher : teacherList) {
                teacherDao.saveOrgUpdate(teacher);
            }
        }
        // 登录账户新增
        if (accountList != null && accountList.size() > 0) {
            for (ShareUserLogin login : accountList) {
                userLoginDao.addLoginUser(login);
            }
        }
        return "执行完毕";

    }

}
