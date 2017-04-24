package com.baizhitong.resource.manage.shareTeachingClass.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;

public interface IShareTeachingClassStudentService {
    /**
     * 查询课程班级学生 ()<br>
     * 
     * @param param
     * @return
     */
    Page getTeachingClassStudentList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 新增班级学生 ()<br>
     * 
     * @param teachingClassCode
     * @param userCode
     * @return
     */
    ResultCodeVo addClassStudent(String teachingClassCode, String[] userCode, String[] userName);

    /**
     * 删除学生 ()<br>
     * 
     * @param userCode
     * @return
     */
    ResultCodeVo deleteStudent(String userCode, String teachingClassCode);
}
