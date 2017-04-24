package com.baizhitong.resource.manage.adminClass.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.dao.share.ShareAdminClassStudentDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassStudentDao;
import com.baizhitong.resource.dao.share.ShareUserStudentDao;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassStudentService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareUserStudent;

@Service
public class AdminClassStudentServiceImpl extends BaseService implements IAdminClassStudentService {
    private @Autowired ShareAdminClassStudentDao    adminClassStudentDao;
    private @Autowired ShareAdminClassDao           adminClassDao;
    private @Autowired ShareUserStudentDao          studentDao;
    private @Autowired ShareTeachingClassStudentDao teachingClassStudentDao;

    /**
     * 删除行政班级学生 ()<br>
     * 
     * @param userCode
     * @param adminClassCode
     */
    public ResultCodeVo deleteAdminClassStudent(String userCode, String adminClassCode) {
        int count = adminClassStudentDao.deleteShareAdminClassStudent(userCode);
        ShareUserStudent student = studentDao.getStudentByCode(userCode);
        if (student != null) {
            student.setAdminClassCode("");
            studentDao.update(student);
            teachingClassStudentDao.deleteShareTeachingClassStudent(userCode);
        }
        return count > 0 ? ResultCodeVo.rightCode("删除行政班级学生成功") : ResultCodeVo.errorCode("删除行政班级学生失败");
    }

    /**
     * 批量删除行政班级学生 ()<br>
     * 
     * @param userCodes
     * @param adminClassCode
     */
    public ResultCodeVo deleteAdminClassStudents(String adminClassCode, String[] userCodes) {
        try {
            if (userCodes.length > 0) {
                for (String userCode : userCodes) {
                    ShareUserStudent student = studentDao.getStudentByCode(userCode);
                    if (student != null) {
                        student.setAdminClassCode("");
                        studentDao.update(student);
                        teachingClassStudentDao.deleteShareTeachingClassStudent(userCode);
                    }
                    deleteAdminClassStudent(userCode, adminClassCode);
                }
            }
            return ResultCodeVo.rightCode("删除成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return ResultCodeVo.rightCode("删除失败");
        }

    }

    /**
     * 新增班级学生 ()<br>
     * 
     * @param adminClassCode
     * @param userCode
     * @return
     */
    public ResultCodeVo addClassStudent(String adminClassCode, String[] userCode, String[] userName) {
        ShareAdminClass adminClass = adminClassDao.getAdminClassByAdminClassCode(adminClassCode);
        if (adminClass == null)
            return ResultCodeVo.errorCode("没找到班级");
        if (userCode != null && userCode.length > 0) {
            for (int i = 0; i < userCode.length; i++) {
                ShareUserStudent student = studentDao.getStudentByCode(userCode[i]);
                student.setModifyTime(new Timestamp(new Date().getTime()));
                student.setAdminClassCode(adminClassCode);
                studentDao.update(student);
                adminClassStudentDao.deleteShareAdminClassStudent(userCode[i]);
                adminClassStudentDao.addAdminClassStudent(adminClass, userCode[i], userName[i]);
                // 删除原来所在的原来教学班级学生数据
                teachingClassStudentDao.deleteShareTeachingClassStudent(userCode[i]);
                // 把学生插入行政班级对应的教学班级中去
                teachingClassStudentDao.insertTeachingClassStudent(userCode[i], userName[i], adminClassCode,
                                getIp(getRequest()));
            }
        }
        return ResultCodeVo.rightCode("保存成功");
    }

}
