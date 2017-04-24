package com.baizhitong.resource.manage.authority.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.authority.service.RoleService;
import com.baizhitong.resource.manage.authority.service.UserRoleService;
import com.baizhitong.resource.manage.login.service.LoginService;
import com.baizhitong.resource.model.authority.AuthUserRoleRef;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.vo.authority.AuthRoleVo;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.StringUtils;

/**
 * 用户角色控制器
 * 
 * @author zhangqiang
 * @date 2015年12月18日 上午11:23:19
 */
@Controller
@RequestMapping(value = "/manage/userRole")
public class UserRoleAction extends BaseAction {
    /** 用户角色接口 */
    private @Autowired UserRoleService  userRoleService;
    /** 角色接口 */
    private @Autowired RoleService      roleService;
    private @Autowired LoginService     loginService;

    /**
     * 跳转到用户角色信息页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 上午11:30:28
     */
    @RequestMapping(value = "info.html")
    public String jumpToUserRoleInfoPage(HttpServletRequest request, ModelMap map) {
        return "/manage/authority/user_role_info.html";
    }

    /**
     * 查询用户角色信息
     * 
     * @param request 请求
     * @param page 页码
     * @param rows 页面大小
     * @param map
     * @param loginAccount 登录账号
     * @param roleId 角色ID
     * @param userName 用户姓名
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 下午1:15:29
     */
    @RequestMapping(value = "/search.html")
    public @ResponseBody Page<Map<String, Object>> searchUserList(HttpServletRequest request, Integer page,
                    Integer rows, ModelMap map, String loginAccount, String roleId, String userName, String orgName) {
        Page<Map<String, Object>> mapPage = null;
        int size = 20;
        int number = 1;
        if (page == null) {
            page = number;
        }
        if (rows == null) {
            rows = size;
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("pageSize", rows);
            param.put("pageNumber", page);
            if (!StringUtils.isEmpty(loginAccount)) {
                param.put("loginAccount", loginAccount);
            }
            if (!StringUtils.isEmpty(roleId)) {
                param.put("roleId", roleId);
            }
            if (!StringUtils.isEmpty(userName)) {
                param.put("userName", userName);
            }
            mapPage = userRoleService.listLoginUsers(roleId, userName, loginAccount, orgName, "", rows, page);
            mapPage = userRoleService.getUserInfoAddRole(mapPage);
        } catch (Exception ex) {
            log.error("未查询到用户数据", ex);
        }
        return mapPage;
    }

    /**
     * 查询用户角色信息
     * 
     * @param request 请求
     * @param page 页码
     * @param rows 页面大小
     * @param map
     * @param loginAccount 登录账号
     * @param roleId 角色ID
     * @param userName 用户姓名
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 下午1:15:29
     */
    @RequestMapping(value = "/companyUserRole.html")
    public @ResponseBody Page<Map<String, Object>> searchUserList(HttpServletRequest request, Integer page,
                    Integer rows, ModelMap map, String loginAccount, String roleId, String userName) {
        Page<Map<String, Object>> mapPage = null;
        int size = 20;
        int number = 1;
        if (page == null) {
            page = number;
        }
        if (rows == null) {
            rows = size;
        }
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        if (companyInfoVo == null)
            return null;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("pageSize", rows);
            param.put("pageNumber", page);
            if (!StringUtils.isEmpty(loginAccount)) {
                param.put("loginAccount", loginAccount);
            }
            if (!StringUtils.isEmpty(roleId)) {
                param.put("roleId", roleId);
            }
            if (!StringUtils.isEmpty(userName)) {
                param.put("userName", userName);
            }
            mapPage = userRoleService.listLoginUsers(roleId, userName, loginAccount, "", companyInfoVo.getOrgCode(),
                            rows, page);
            mapPage = userRoleService.getUserInfoAddRole(mapPage);
        } catch (Exception ex) {
            log.error("未查询到用户数据", ex);
        }
        return mapPage;
    }

    /**
     * 跳转到给用户分配角色的页面
     * 
     * @param request 请求
     * @param userCode 用户代码
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 下午2:33:29
     */
    @RequestMapping(value = "/roleList.html")
    public String jumpToRoleListPage(HttpServletRequest request, String userCode, ModelMap map) {
        try {
            // 得到所有的未删除角色
            List<AuthRoleVo> roleList = roleService.getRoleInfoList();
            ShareUserLogin user = loginService.userLogin(userCode);
            List<AuthUserRoleRef> selectedRoleList = userRoleService.getRoleListByUserCode(userCode);
            if (roleList != null && roleList.size() > 0) {
                for (int j = 0; j < roleList.size(); j++) {
                    if (selectedRoleList != null && selectedRoleList.size() > 0) {
                        for (int i = 0; i < selectedRoleList.size(); i++) {
                            if (roleList.get(j).getId().intValue() == selectedRoleList.get(i).getRoleId().intValue()) {
                                roleList.get(j).setIsCheck(1);
                                break;
                            }
                        }
                    }
                }
            }
            map.put("user", user);
            map.put("roleList", roleList);
        } catch (Exception ex) {
            log.error("获取角色信息失败", ex);
        }
        return "/manage/authority/role_list.html";
    }

    /**
     * 撤销用户已有的角色
     * 
     * @param request 请求
     * @param userCode 用户代码
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 下午4:13:53
     */
    @RequestMapping(value = "delUserRole.html")
    public @ResponseBody ResultCodeVo deleteUserRole(HttpServletRequest request, String userCode) {
        ResultCodeVo vo = new ResultCodeVo();
        try {
            // 获取用户所有的角色
            List<AuthUserRoleRef> list = userRoleService.getRoleListByUserCode(userCode);
            // 撤销用户所有的角色，即删除操作
            int delSum = userRoleService.deleteUserRoleInfoList(list);
            if (delSum > 0) {
                vo.setSuccess(true);
                vo.setMsg("保存成功!");
            } else {
                vo.setSuccess(false);
                vo.setMsg("未做任何修改!");
            }
        } catch (Exception ex) {
            log.error("删除失败", ex);
        }
        return vo;
    }

    /**
     * 分配用户角色
     * 
     * @param request 请求
     * @param roleIds 角色IDs
     * @param userCode 用户代码
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 下午4:25:19
     */
    @RequestMapping(value = "addUserRole.html")
    public @ResponseBody ResultCodeVo manageUserRole(HttpServletRequest request, String roleIds, String userCode) {
        ResultCodeVo vo = new ResultCodeVo();
        String[] rIds = roleIds.split(",");
        try {

            List<AuthUserRoleRef> list = userRoleService.getRoleListByUserCode(userCode);
            if (list != null && list.size() != 0) {
                boolean flag = false;
                int delSum = 0;
                // 新增
                for (int i = 0; i < rIds.length; i++) {
                    for (int j = 0; j < list.size(); j++) {
                        String thisRoleId = list.get(j).getRoleId().toString();// 当前role
                        if (rIds[i].equals(thisRoleId)) {// 遍历到相同 role，不做操作，跳出内层循环
                            break;
                        }
                        if (j == list.size() - 1) {// 遍历结束，同时没有相同的role，新增
                            if (!rIds[i].equals(thisRoleId)) {
                                AuthUserRoleRef authUserRole = new AuthUserRoleRef();
                                authUserRole.setUserCode(userCode);
                                authUserRole.setRoleId(Integer.parseInt(rIds[i]));
                                flag = userRoleService.addUserRoleResRef(authUserRole);
                            }
                        }
                    }
                }
                // 删除
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < rIds.length; j++) {
                        String thisRoleId = list.get(i).getRoleId().toString();// 当前role
                        if (rIds[j].equals(thisRoleId)) {// 遍历到相同 role，不做操作，跳出内层循环
                            break;
                        }
                        if (j == (rIds.length - 1)) {// 遍历结束，同时没有相同的role，删除
                            if (!rIds[j].equals(thisRoleId)) {
                                AuthUserRoleRef authUserRole = new AuthUserRoleRef();
                                authUserRole.setId(list.get(i).getId());
                                delSum = userRoleService.deleteUserRoleResRef(authUserRole);
                            }
                        }
                    }
                }
                vo.setSuccess(true);
                vo.setMsg("保存成功!");
            } else {
                boolean flag = false;
                for (int i = 0; i < rIds.length; i++) {
                    AuthUserRoleRef authUserRole = new AuthUserRoleRef();
                    authUserRole.setUserCode(userCode);
                    authUserRole.setRoleId(Integer.parseInt(rIds[i]));
                    flag = userRoleService.addUserRoleResRef(authUserRole);
                }
                if (flag) {
                    vo.setSuccess(true);
                    vo.setMsg("保存成功!");
                }
            }

        } catch (Exception ex) {
            log.error("保存失败", ex);
        }
        return vo;
    }
}
