package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareUserTeacher;
import com.baizhitong.resource.model.vo.share.ShareUserTeacherVo;

/**
 * 教师信息数据接口
 * 
 * @author zhangqiang
 * @date 2015年12月16日 下午3:36:06
 */
public interface ShareUserTeacherDao {

    /**
     * 根据教师代码获取教师基本信息
     * 
     * @param teacherCode 教师代码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月16日 下午3:37:37
     */
    public Map<String, Object> getTeacherInfoByTeaCode(String teacherCode) throws Exception;

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
     * @param orgCode
     * @return
     */
    public List<ShareUserTeacher> getTeacherList(String orgCode);

    /**
     * 得到老师信息 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ShareUserTeacher getTeacher(String userCode);

    /**
     * 保存老师 ()<br>
     * 
     * @param teacher
     * @return
     */
    public boolean saveOrgUpdate(ShareUserTeacher teacher);

    /**
     * 删除老师 ()<br>
     * 
     * @param userCode
     * @return
     */
    public int deleteTeacher(String userCode);

    /**
     * 通过guid查询老师 ()<br>
     * 
     * @param guid
     * @return
     */
    public ShareUserTeacher getTeacherByGuid(String guid);

    /**
     * 新增或更新 ()<br>
     * 
     * @param teacherList
     * @return
     */
    public int saveTeacherList(List<ShareUserTeacher> teacherList);

    /**
     * 通过guid更新老师信息 ()<br>
     * 
     * @param teacher
     * @return
     */
    public int updateTeacherByGuid(ShareUserTeacher teacher);

    /**
     * 查询简单的老师列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getSimpleTeacherList(String orgCode);

    /**
     * 更新所有老师的年级学科 ()<br>
     * 
     * @param orgCode
     */
    public void updateSubjectGrade(String orgCode, String studyYearCode);
}
