package com.baizhitong.resource.manage.adminClass.service;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;

public interface IAdminClassStudentService {
    /**
     * 删除行政班级学生 ()<br>
     * 
     * @param userCode
     * @param adminClassCode
     */
    public ResultCodeVo deleteAdminClassStudent(String userCode, String adminClassCode);

    /**
     * 批量删除行政班级学生 ()<br>
     * 
     * @param userCodes
     * @param adminClassCode
     */
    public ResultCodeVo deleteAdminClassStudents(String adminClassCode, String[] userCodes);

    /**
     * 新增班级学生 ()<br>
     * 
     * @param adminClassCode
     * @param userCode
     * @return
     */
    ResultCodeVo addClassStudent(String adminClassCode, String[] userCode, String[] userName);
}
