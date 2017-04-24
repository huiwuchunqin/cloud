package com.baizhitong.resource.dao.share;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareUserStudent;
import com.baizhitong.resource.model.vo.share.ShareUserStudentVo;

/**
 * 学生信息数据接口
 * 
 * @author zhangqiang
 * @date 2015年12月16日 下午5:25:11
 */
public interface ShareUserStudentDao {

    /**
     * 根据学生代码获取学生信息
     * 
     * @param studentCode 学生代码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月16日 下午5:26:27
     */
    public Map<String, Object> getStudentByStudentCode(String studentCode) throws Exception;

    public ShareUserStudent getStudentByCode(String studentCode);

    /**
     * 查询学生分页信息 ()<br>
     * 
     * @param studentNumber 学号
     * @param teachingClassCode 学段编码
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param userName 学生姓名
     * @param orgName 机构名称
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    public Page getStudentPageInfo(boolean noAdminClass, String studentNumber, String loginAccount, Integer type,
                    Integer adminClassStudentChoose, String teachingClasCode, String adminClassCode, String sectionCode,
                    String gradeCode, String userName, String orgName, String orgCode, String adminClassStudentList,
                    Integer pageSize, Integer pageNo);

    /**
     * 保存学生信息 ()<br>
     * 
     * @param student
     * @return
     */
    public boolean addStudent(ShareUserStudent student);

    /**
     * 更新 ()<br>
     * 
     * @param student
     * @return
     */
    public int update(ShareUserStudent student);

    /**
     * 更新学生行政班级 ()<br>
     * 
     * @param adminClassCode
     */
    public int deleteStudentAdminClass(String adminClassCode);

    /**
     * 查询学生信息 ()<br>
     * 
     * @param gid
     * @return
     */
    public ShareUserStudent getStudent(String gid);

    /**
     * 删除学生 ()<br>
     * 
     * @param userCode
     * @return
     */
    public int delete(String userCode);

    /**
     * 通过guid查询学生 ()<br>
     * 
     * @param guid
     * @return
     */
    public ShareUserStudent getStudentByGuid(String guid);

    /**
     * 查询机构学生信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareUserStudent> orgStudentList(String orgCode);

    /**
     * 新增或更新学生信息 ()<br>
     * 
     * @param studentList
     * @return
     */
    public int addStudentList(List<ShareUserStudent> studentList);

    /**
     * 
     * (更新年级信息)<br>
     * 
     * @param gradeCode 年级编码
     * @param classCode 班级编码
     * @return 操作影响条数
     */
    public int updateGradeInfo(String gradeCode, String classCode);

    /**
     * 通过guid更新学生信息 ()<br>
     * 
     * @param studentVo
     * @return
     */
    public int updateStudentInfoByGuid(ShareUserStudentVo studentVo);

    /**
     * 查询简单的学生列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getSimpleStudentList(String orgCode);

    /**
     * 
     * (根据相关条件查询学生信息列表)<br>
     * 
     * @param orgCode
     * @param studentNumber
     * @param studentCode
     * @return
     */
    public List<Map<String, Object>> selectListByStudentNumber(String orgCode, String studentNumber,
                    String studentCode);

    /**
     * 
     * 通过机构学号是否存在
     * 
     * @param studentNumber 学号
     * @param orgCode 机构
     * @return
     */
    public Integer existStudentStudentNo(String studentNumber, String orgCode);

    /**
     * 
     * 判断学生硬件号是否存在
     * 
     * @param stuHardNo 硬件号
     * @param orgCode 机构
     * @return
     */
    public Integer existStudentHardNo(String stuHardNo, String orgCode);

    /**
     * 更新学生信息 ()<br>
     * 
     * @param studentName 学生姓名
     * @param studentCode 学生编码
     * @param studentNumber 学生学号
     * @param gradeCode 年级编码
     * @param termCode 学期编码
     * @param adminClassCode 行政班级编码
     * @param enterDate 入学日期
     * @param gender 性别
     * @return 更新条数
     */
    public int updateStudent(String studentName, String studentCode, String studentNumber, String gradeCode,
                    String termCode, String adminClassCode, Timestamp enterDate, Integer gender, String modifyIp,
                    Timestamp modifyTime, String modifyPgm);
}
