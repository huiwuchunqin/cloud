package com.baizhitong.resource.manage.student.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserStudent;
import com.baizhitong.resource.model.vo.share.ShareUserStudentVo;
import com.baizhitong.resource.model.vo.share.ShareUserTeacherVo;

public interface IStudentService {
    /**
     * 查询学生分页信息 ()<br>
     * 
     * @param studentNumber 学号
     * @param sectionCode 学段编码
     * @param teachingClassCode 教学班级编码
     * @param gradeCode 年级编码
     * @param userName 学生姓名
     * @param orgName 机构名称
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    public Page getStudentPageInfo(boolean hasAdminClass, String studentNumber, String loginAccount, Integer type,
                    Integer adminClassStudentChoose, String teachingClassCode, String adminClassCode,
                    String sectionCode, String gradeCode, String userName, String orgName, String orgCode,
                    Integer pageSize, Integer pageNo);

    /**
     * 新增或更新学生信息 ()<br>
     * 
     * @param student
     * @return
     */
    public ResultCodeVo addOrUpdateStudent(ShareUserStudent student, ShareUserLogin login);

    /**
     * 导入学生 ()<br>
     * 
     * @param sheet
     * @return
     */
    public ResultCodeVo importStudent(Sheet sheet);

    /**
     * 删除学生 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ResultCodeVo deleteStudent(String userCode);

    /**
     * 查询学生列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareUserStudentVo> getSimpleStudentList(String orgCode);

}
