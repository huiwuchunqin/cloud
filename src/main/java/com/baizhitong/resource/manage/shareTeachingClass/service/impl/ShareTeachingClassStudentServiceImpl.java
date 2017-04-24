package com.baizhitong.resource.manage.shareTeachingClass.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.share.ShareTeachingClassDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassStudentDao;
import com.baizhitong.resource.manage.shareTeachingClass.service.IShareTeachingClassStudentService;
import com.baizhitong.resource.model.share.ShareTeachingClass;

@Service
public class ShareTeachingClassStudentServiceImpl extends BaseService implements IShareTeachingClassStudentService {
    @Autowired
    ShareTeachingClassStudentDao classStudentDao;
    @Autowired
    ShareTeachingClassDao        teachingClassDao;

    /**
     * 查询课程班级学生 ()<br>
     * 
     * @param param
     * @return
     */
    public Page getTeachingClassStudentList(Map<String, Object> param, Integer page, Integer rows) {
        return classStudentDao.getTeachingClassStudent(param, page, rows);
    }

    /**
     * 新增班级学生 ()<br>
     * 
     * @param teachingClassCode
     * @param userCode
     * @return
     */
    public ResultCodeVo addClassStudent(String teachingClassCode, String[] userCode, String[] userName) {
        ShareTeachingClass teachingClass = teachingClassDao.getClassByCode(teachingClassCode);
        if (teachingClass == null)
            return ResultCodeVo.errorCode("没找到班级");
        if (userCode != null && userCode.length > 0) {
            for (int i = 0; i < userCode.length; i++) {
                classStudentDao.addTeachingClassStudent(teachingClass, userCode[i], userName[i]);
            }
        }
        return ResultCodeVo.rightCode("保存成功");
    }

    /**
     * 删除学生 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ResultCodeVo deleteStudent(String userCode, String teachingClassCode) {
        return classStudentDao.deleteStudent(userCode, teachingClassCode) > 0 ? ResultCodeVo.rightCode("删除成功")
                        : ResultCodeVo.errorCode("删除失败");
    }

}
