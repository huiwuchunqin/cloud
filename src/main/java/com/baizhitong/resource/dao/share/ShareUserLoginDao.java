package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.vo.share.ShareUserLoginVo;

/**
 * 查询用户信息 ShareUserLoginDao
 * 
 * @author creator BZT 2016年1月22日 下午2:38:14
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareUserLoginDao {
    /**
     * 查询用户信息 ()<br>
     * 
     * @param loginAccount 登录名
     * @param loginPwd 登录密码
     * @param orgCode 机构编码
     * @return
     */
    public ShareUserLogin getUser(String loginAccount, String loginPwd, String orgCode);

    /**
     * 查询用户信息 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ShareUserLogin getUser(String userCode);

    /**
     * 新增登录用户 ()<br>
     * 
     * @param user
     */
    public boolean addLoginUser(ShareUserLogin user);

    /**
     * 批量插入登录信息 ()<br>
     * 
     * @param shareUserLoginList
     * @return
     */
    public int addLonginUserList(List<ShareUserLogin> shareUserLoginList);

    /**
     * 查询用户登录信息 ()<br>
     * 
     * @param roleId 用户角色
     * @param userName 用户姓名
     * @param loginAccount 登录账户
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    public Page<Map<String, Object>> getLoginList(String roleId, String userName, String loginAccount, String orgName,
                    String orgCode, Integer pageSize, Integer pageNo);

    /**
     * 查询区域管理员 ()<br>
     * 
     * @param userName 登录名称
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Page<Map<String, Object>> getAreaLoginList(String userName, Integer pageNo, Integer pageSize);

    /**
     * 查询学校管理员 ()<br>
     * 
     * @param userName 用户姓名
     * @param orgName 机构名称
     * @param orgCode 机构编码
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Page<Map<String, Object>> getSchoolLoginList(String userName, String orgName, String orgCode, Integer pageNo,
                    Integer pageSize);

    /**
     * 查询登录用户
     * 
     * @param loginAccount
     * @param orgCode
     * @return
     */
    public List<ShareUserLogin> getLoginUser(String loginAccount, String orgCode);

    /**
     * 查询账号信息 ()<br>
     * 
     * @param domainID 域id
     * @param loginAccount 登录账号
     * @param loginPwd 登录密码
     * @return
     */
    public List<Map<String, Object>> getAgencyUser(Integer domainID, String loginAccount, String loginPwd);

    /**
     * 删除登录用户 ()<br>
     * 
     * @param gid
     */
    public void deleteLoginUser(String gid);

    /**
     * 删除登录用户 ()<br>
     * 
     * @param userCode
     * @return
     */
    public int deleteLoginUserByUserCode(String userCode);

    /**
     * 修改登录用户信息 ()<br>
     * 
     * @param loginUser
     */
    public void updateLoginUser(ShareUserLogin loginUser) throws Exception;

    /**
     * 更新用户状态 ()<br>
     * 
     * @param userCode
     * @param status
     * @return
     */
    public void updateState(String userCode, Integer status) throws Exception;

    /**
     * 修改登录账号机构名称 ()<br>
     * 
     * @param orgCode
     * @param orgName
     * @return
     */
    public int updateSchoolName(String orgCode, String orgName);

    /**
     * 查询登录账户相同的账号 ()<br>
     * 
     * @param orgCode
     * @param loginAccount
     * @return
     */
    public List<Map<String, Object>> getSameAccount(String orgCode, String loginAccount);

    /**
     * 查询机构所有的登录名 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getOrgAccountName(String orgCode);

    /**
     * 查询机构所有的登录名 ()<br>
     * 
     * @param domainID 域名id
     * @return
     */
    public List<Map<String, Object>> getSameDomainAccountName(Integer domainID);

    /**
     * 撤销管理员 ()<br>
     * 
     * @param userCode
     * @param type
     */
    public int recalAdmin(String userCode, String type);

    /**
     * 通过guid查询登录信息 ()<br>
     * 
     * @param guid
     * @return
     */
    public ShareUserLogin getByGuid(String guid);

    /**
     * 查询机构登录用户信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareUserLogin> getOrgLoginList(String orgCode);

    /**
     * 保存或新增登录信息 ()<br>
     * 
     * @param loginList
     * @return
     */
    public int saveLoginList(List<ShareUserLogin> loginList);

    /**
     * 通过guid更新登录用户信息 ()<br>
     * 
     * @param userLoginVo
     * @return
     */
    public int updateTeacherLoginByCdguid(ShareUserLoginVo userLoginVo);

    /**
     * 通过guid更新登录用户信息 ()<br>
     * 
     * @param userLoginVo
     * @return
     */
    public int updateStudentLoginByCdguid(ShareUserLoginVo userLoginVo);

    /**
     * 关联学生表插入登录表 ()<br>
     * 
     * @param shareUserLogin
     */
    public void insertStudentShareUserLogin(ShareUserLogin shareUserLogin);

    /**
     * 关联教师表插入登录表 ()<br>
     * 
     * @param shareUserLogin
     */
    public void insertTeacherShareUserLogin(ShareUserLoginVo shareUserLogin);

    /**
     * 
     * (查询教研员列表信息)<br>
     * 
     * @param userName 姓名
     * @param page 当前页码
     * @param rows 每页大小
     * @return
     */
    public Page<Map<String, Object>> selectEduStaffList(String userName, Integer page, Integer rows);

    /**
     * 查询简单的登录列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getSimpleLoginList(String orgCode);

    /**
     * 
     * (根据邮箱查询登录信息列表)<br>
     * 
     * @param mail
     * @param userCode
     * @return
     */
    public List<ShareUserLogin> selectListByMail(String mail, String userCode);

    /**
     * 
     * (根据手机号查询登录信息列表)<br>
     * 
     * @param mobilePhone
     * @param userCode
     * @return
     */
    public List<ShareUserLogin> selectListByMobilePhone(String mobilePhone, String userCode);

    /**
     * 更新密码 ()<br>
     * 
     * @param newPwd 新的密码
     * @param userCodes 改密码的用户
     */
    public void updatePwd(String newPwd, String userCodes);

    /**
     * 根据代理商的机构编码 查询代理商 和他下一级代理商的用户信息 ()<br>
     * 
     * @param orgCode 代理商机构编码
     * @return page
     */
    public Page getAgencyAdminInfo(String orgCode, Integer page, Integer rows, Map<String, Object> param);

    /**
     * 验证代理商模式下账户是否存在 ()<br>
     * 
     * @param account
     * @param domainID
     * @return
     */
    public long checkAgencyAccountExit(String account, Integer domainID);

    /**
     * 验证账号在整个平台是否存在 ()<br>
     * 
     * @param account
     * @return
     */
    public long checkAccountExit(String account);

    public List<String> getMobileList();

    public List<String> getEmailList();
}
