package com.baizhitong.resource.manage.login.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.vo.authority.UserAuthSettingVo;
import com.baizhitong.resource.model.vo.login.LoginUserVo;
import com.baizhitong.resource.model.vo.share.ShareUserLoginVo;

/**
 * 登录模块接口
 * 
 * @author creator Cuidc 2015/12/15
 * @author updater
 */
public interface LoginService {
    /**
     * 验证登录
     * 
     * @param loginAccount 登录账号
     * @param password 登录密码
     * @param domain 域名
     * @return 是否成功
     * @throws Exception 异常
     */
    public ResultCodeVo checkLogin(String loginAccount, String password, String domain) throws Exception;

    /**
     * 全局登录 ()<br>
     */
    public ResultCodeVo globalLogin(UserInfoVo userInfoVo);

    /**
     * 查询用户信息 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ShareUserLogin userLogin(String userCode);

    /**
     * 查询区域管理员 ()<br>
     * 
     * @param userName 用户姓名
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Page getAreaAdminList(String userName, Integer pageNo, Integer pageSize);

    /**
     * 
     * ()<br>
     * 
     * @param userName 用户姓名
     * @param orgName 机构名称
     * @param orgCode 机构编码
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Page getSchoolAdminList(String userName, String orgName, String orgCode, Integer pageNo, Integer pageSize);

    /**
     * 新增用户 ()<br>
     * 
     * @param vo
     * @paramuserRoleCode 10区域管理员角色 20 学校管理员角色
     * @return
     */
    public ResultCodeVo addLoginUser(ShareUserLoginVo vo, String userRoleCode);

    /**
     * 删除登录用户 ()<br>
     * 
     * @param gids
     * @return
     */
    public ResultCodeVo deleteLoginUser(String[] gids);

    /**
     * 修改登录用户信息 ()<br>
     * 
     * @param vo
     * @return
     */
    public ResultCodeVo updateLoginUser(String userName, String phone, String mail, String userCode);

    /**
     * 更新用户状态 ()<br>
     * 
     * @param userCode
     * @param status
     * @return
     */
    public ResultCodeVo updateState(String userCode, Integer status);

    /**
     * 修改密码 ()<br>
     * 
     * @param password
     * @param oldPwd
     * @param userCode
     * @return
     */
    public ResultCodeVo updatePwd(String password, String oldPwd, String userCode);

    /**
     * 新增管理员账号 ()<br>
     * 
     * @param userCodes
     * @param adminAddType
     * @return
     */
    public ResultCodeVo addAdmin(String[] userCodes, String adminAddType);

    /**
     * 撤销管理员 ()<br>
     * 
     * @param userCode
     * @param type
     * @return
     */
    public ResultCodeVo reCallAdmin(String userCode, String type);

    /**
     * 
     * (分页查询教研员列表信息)<br>
     * 
     * @param userName 姓名
     * @param page 当前页码
     * @param rows 每页大小
     * @return
     * @throws Exception
     */
    public Page<Map<String, Object>> queryEduStaffList(String userName, Integer page, Integer rows) throws Exception;

    /**
     * 
     * (查询登录用户已设置的审核权限信息)<br>
     * 
     * @param userCode
     * @param loginAccount
     * @return
     * @throws Exception
     */
    public UserAuthSettingVo querySettingInfo(String userCode, String loginAccount) throws Exception;

    /**
     * 
     * (教研员审核权限保存)<br>
     * 
     * @param loginAccount
     * @param priviledgeSectionCodes
     * @param priviledgeGradeCodes
     * @param priviledgeSubjectCodes
     * @param userCode
     * @return
     * @throws Exception
     */
    public ResultCodeVo saveEduStaffAuthSet(String loginAccount, String priviledgeSectionCodes,
                    String priviledgeGradeCodes, String priviledgeSubjectCodes, String userCode) throws Exception;

    /**
     * 查询登录信息 ()<br>
     * 
     * @Param orgCode 机构编码
     * @return
     */
    public List<LoginUserVo> getLoginList(String orgCode);

    /**
     * 根据代理商的机构编码 查询代理商 和他下一级代理商的用户信息 ()<br>
     * 
     * @param orgCode 代理商机构编码
     * @return page
     */
    public Page getAgencyUserInfo(String orgCode, Integer page, Integer rows, Map<String, Object> param);

    /**
     * 验证账号是否存在 ()<br>
     * 
     * @param account
     * @return
     */
    public long accountExitCheck(String account);
}
