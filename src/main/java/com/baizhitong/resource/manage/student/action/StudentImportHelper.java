package com.baizhitong.resource.manage.student.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.baizhitong.resource.dao.share.ShareAdminClassStudentDao;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.dao.share.ShareUserStudentDao;
import com.baizhitong.resource.dao.share.sqlserver.ShareUserStudentSqlServerDaoImpl;
import com.baizhitong.resource.model.share.ShareAdminClassStudent;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserStudent;

@Repository
public class StudentImportHelper implements Callable<String> {
    // 学生列表
    private List<ShareUserStudent>       studentList                = new ArrayList<ShareUserStudent>();
    // 登录账户列表
    private List<ShareUserLogin>         loginList                  = new ArrayList<ShareUserLogin>();
    // 行政班级学生列表
    private List<ShareAdminClassStudent> shareAdminClassStudentList = new ArrayList<ShareAdminClassStudent>();
    // 学生dao
    private ShareUserStudentDao          shareUserDao;
    // 登录dao
    private ShareUserLoginDao            userLoginDao;
    // 行政班级dao
    private ShareAdminClassStudentDao    adminClassStudentDao;
    private boolean                      stop                       = false;

    public String call() {
        // 学生新增
        if (studentList != null && studentList.size() > 0) {
            for (ShareUserStudent student : studentList) {
                shareUserDao.addStudent(student);
            }
        }

        // 登录信息新增
        if (loginList != null && loginList.size() > 0) {
            for (ShareUserLogin login : loginList) {
                try {
                    userLoginDao.addLoginUser(login);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        // 行政班级学生新增
        if (shareAdminClassStudentList != null && shareAdminClassStudentList.size() > 0) {
            for (ShareAdminClassStudent adminClassStudent : shareAdminClassStudentList) {
                adminClassStudentDao.saveOne(adminClassStudent);
            }
        }

        stop = true;
        return "执行完了";
    }

    public boolean isStop() {
        return stop;
    }

    public StudentImportHelper(ShareUserStudentDao shareUserDao,
                               ShareUserLoginDao userLoginDao,
                               ShareAdminClassStudentDao adminClassStudentDao,
                               List<ShareUserStudent> studentList,
                               List<ShareUserLogin> loginList,
                               List<ShareAdminClassStudent> adminStudentList) {
        super();
        this.studentList = studentList;
        this.loginList = loginList;
        this.shareUserDao = shareUserDao;
        this.userLoginDao = userLoginDao;
        this.adminClassStudentDao = adminClassStudentDao;
        this.shareAdminClassStudentList = adminStudentList;
    }

    public StudentImportHelper() {
        super();
    }

    public List<ShareUserStudent> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<ShareUserStudent> studentList) {
        this.studentList = studentList;
    }

    public List<ShareUserLogin> getLoginList() {
        return loginList;
    }

    public void setLoginList(List<ShareUserLogin> loginList) {
        this.loginList = loginList;
    }

}
