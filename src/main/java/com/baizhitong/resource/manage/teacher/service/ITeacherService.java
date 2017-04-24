package com.baizhitong.resource.manage.teacher.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserTeacher;
import com.baizhitong.resource.model.vo.share.ShareUserTeacherVo;

public interface ITeacherService {
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
    public Page getTeacherInfo(String sectionCode, String subjectCode, String orgName, String orgCode, String userName,
                    String loginAccount, Integer userRole, Integer pageSize, Integer pageNo, Integer adminChooseType);

    /**
     * 查询老师列表 ()<br>
     * 
     * @return
     */
    public List<ShareUserTeacher> getTeacherList(String orgCode);

    /**
     * 查询老师列表 ()<br>
     * 
     * @return
     */
    public List<ShareUserTeacher> getTeacherList();

    /**
     * 得到老师信息 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ShareUserTeacher getTeacher(String userCode);

    /**
     * 新增或修改老师信息 ()<br>
     * 
     * @param teacher
     * @param userLogin
     * @return
     */
    public ResultCodeVo saveOrUpdateTeacher(ShareUserTeacher teacher, ShareUserLogin userLogin);

    /**
     * 删除老师信息 ()<br>
     * 
     * @param teacherCode 老师编码
     * @return
     */
    public ResultCodeVo deleteTeacher(String teacherCode);

    /**
     * 导入老师 ()<br>
     * 
     * @param sheet
     * @return
     */
    public ResultCodeVo importTeacher(Sheet sheet);

    /**
     * 查询老师列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareUserTeacherVo> getSimpleTeacherList(String orgCode);
}
