package com.baizhitong.resource.manage.authority.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.manage.authority.service.RoleService;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.vo.authority.AuthRoleVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.JsonUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 角色管理控制器
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午2:14:05
 */
@Controller
@RequestMapping(value = "/manage/role")
public class RoleAction extends BaseAction {

    /** 角色信息接口 */
    private @Autowired RoleService roleService;

    /**
     * 跳转到角色列表信息页面
     * 
     * @param request
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午2:18:31
     */
    @RequestMapping(value = "roleInfo.html")
    public String getRoleInfoPage(HttpServletRequest request, ModelMap map) {
        return "/manage/authority/role_info.html";
    }

    /**
     * 分页查询角色信息
     * 
     * @param request 请求
     * @param page 页码
     * @param rows 页面大小
     * @param name 角色名称
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午2:53:37
     */
    @RequestMapping(value = "/search.html")
    public @ResponseBody Page<AuthRole> searchRoleInfoList(HttpServletRequest request, Integer page, Integer rows,
                    String name) {
        Page<AuthRole> pageInfo = null;
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
            param.put("name", name);
            pageInfo = roleService.getRolePageInfo(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 跳转到角色新增页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午3:20:38
     */
    @RequestMapping(value = "/roleAdd.html")
    public String toRoleAddPage(HttpServletRequest request, ModelMap map) {
        map.put("isModify", false);
        return "/manage/authority/role_edit.html";
    }

    /**
     * 跳转到修改角色信息页面
     * 
     * @param request 请求
     * @param id 角色ID
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午3:23:41
     */
    @RequestMapping(value = "/roleEdit.html")
    public String toEditRolePage(HttpServletRequest request, Integer id, ModelMap map) {
        try {
            AuthRole role = new AuthRole();
            role = roleService.getRoleById(id);
            map.put("role", JsonUtils.ObjectToJson(role));
            map.put("isModify", true);
        } catch (Exception ex) {
            log.error("获取角色信息异常 ", ex);
        }
        return "/manage/authority/role_edit.html";
    }

    /**
     * 添加或修改角色信息
     * 
     * @param request 请求
     * @param role 角色实体
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午3:41:21
     */
    @RequestMapping(value = "/addRole.html")
    public @ResponseBody ResultCodeVo roleListAdd(HttpServletRequest request, AuthRole role, ModelMap map) {
        UserInfoVo user =getUserInfoVo();
        if (role == null) {
            return ResultCodeVo.errorCode("角色信息不能为空");
        }
        if (StringUtils.isEmpty(role.getName())) {
            return ResultCodeVo.errorCode("角色名称不能为空！");
        }
        try {

            boolean flag = false;
            ResultCodeVo vo = new ResultCodeVo();
            AuthRole roleEntity = new AuthRole();
            Integer roleID = role.getId();
            roleEntity = roleService.getRoleByName(role.getName());
            if (roleID == null) {
                if (roleEntity == null) {
                    role.setSysRole(0);
                    role.setFlagDelete(0);
                    role.setCreator(user.getLoginAccount());
                    role.setCreateTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
                    role.setCreatorIP(getIp());
                    role.setModifierIP(getIp());
                    role.setModifier(user.getLoginAccount());
                    role.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
                    flag = roleService.addRole(role);
                } else {
                    return ResultCodeVo.errorCode("该角色已经存在");
                }

            } else {

                if (roleEntity != null && roleID.intValue() != roleEntity.getId()) {
                    return ResultCodeVo.errorCode("该角色已经存在");
                } else {
                    AuthRole oldRole = roleService.getRoleById(role.getId());
                    role.setCreateTime(oldRole.getCreateTime());
                    role.setCreator(oldRole.getCreator());
                    role.setCreatorIP(oldRole.getCreatorIP());
                    role.setModifierIP(getIp());
                    role.setModifier(user.getLoginAccount());
                    role.setFlagDelete(oldRole.getFlagDelete());
                    role.setSysRole(oldRole.getSysRole());
                    role.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
                    flag = roleService.addRole(role);
                }
            }

            if (flag) {
                vo.setSuccess(true);
                if (roleID != null) {
                    vo.setMsg("修改成功");
                } else {
                    vo.setMsg("添加成功");
                }
            } else {
                vo.setSuccess(false);
                if (roleID != null) {
                    vo.setMsg("修改失败");
                } else {
                    vo.setMsg("保存失败");
                }
            }
            return vo;
        } catch (Exception ex) {
            log.error("角色信息保存失败！", ex);
            return ResultCodeVo.errorCode("保存失败");
        }
    }

    /**
     * 删除角色
     * 
     * @param id 角色ID
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午4:29:04
     */
    @RequestMapping(value = "/delete.html")
    public @ResponseBody ResultCodeVo deleteRole(String id) {
        ResultCodeVo vo = new ResultCodeVo();
        try {
            int delSum = roleService.deleteRole(id);
            if (delSum == 1) {
                vo.setSuccess(true);
                vo.setMsg("删除成功！");
            } else if (delSum == 2) {
                vo.setSuccess(false);
                vo.setMsg("删除失败，该角色下存在用户无法删除！");
            }
        } catch (Exception ex) {
            log.error("删除角色失败!", ex);
            vo.setSuccess(false);
            vo.setMsg("删除失败！");
        }
        return vo;
    }

    /**
     * 获取所有未删除的角色列表信息
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 下午1:06:30
     */
    @RequestMapping(value = "/getRoleList.html",
                    method = RequestMethod.POST)
    public @ResponseBody List<AuthRoleVo> ListRole(HttpServletRequest request, ModelMap map) {
        List<AuthRoleVo> roleList = new ArrayList<AuthRoleVo>();
        try {
            roleList = roleService.getRoleInfoList();
            AuthRoleVo role = new AuthRoleVo();
            role.setName("请选择角色");
            role.setId(0);
            roleList.add(0, role);
        } catch (Exception ex) {
            log.error("获取角色列表信息失败!", ex);
            return new ArrayList<AuthRoleVo>();
        }
        return roleList;
    }

}
