package com.baizhitong.resource.manage.authority.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.authority.service.RoleResourceService;
import com.baizhitong.resource.manage.authority.service.RoleService;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.authority.AuthRoleResRef;
import com.baizhitong.resource.model.vo.authority.TreeNodeVo;

/**
 * 角色资源控制器
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午8:12:15
 */
@Controller
@RequestMapping(value = "/manage/roleResource")
public class RoleResourceAction extends BaseAction {

    /** 角色信息接口 */
    private @Autowired RoleService         roleService;
    /** 角色资源信息接口 */
    private @Autowired RoleResourceService roleResourceService;

    /**
     * 跳转到角色资源信息页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 上午9:38:28
     */
    @RequestMapping(value = "info.html")
    public String jumpToRoleResourcePage(HttpServletRequest request, ModelMap map) {
        return "/manage/authority/role_menu_info.html";
    }

    /**
     * 跳转到为角色分配资源页面
     * 
     * @param request
     * @param map
     * @param id 角色ID
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 上午9:47:48
     */
    @RequestMapping(value = "resourceList.html")
    public String showResourceList(HttpServletRequest request, ModelMap map, String id) {
        try {
            AuthRole role = roleService.getRoleById(Integer.parseInt(id));
            map.put("role", role);
        } catch (Exception ex) {
            log.error("获取角色信息失败!", ex);
        }
        return "/manage/authority/menu_list.html";
    }

    /**
     * 加载资源信息，分配给角色
     * 
     * @param request
     * @param roleId 角色ID
     * @return
     */
    @RequestMapping(value = "/getResource.html")
    public @ResponseBody List<TreeNodeVo> getResourceListByCompanyCode(HttpServletRequest request, String roleId) {
        try {
            List<TreeNodeVo> resourceList = roleResourceService.getResourceListByRoleID(roleId);
            return resourceList;
        } catch (Exception ex) {
            log.error("获取菜单资源失败!", ex);
            return new ArrayList<TreeNodeVo>();
        }
    }

    /**
     * 从资源列表中批量选择资源分配给对应的角色
     * 
     * @param request
     * @param resourceCode 资源Code IDs
     * @param roleId 角色ID
     * @return
     */
    @RequestMapping(value = "/addRoleResource.html")
    public @ResponseBody ResultCodeVo addRoleResource(HttpServletRequest request, String resourceCode, String roleId) {
        ResultCodeVo vo = new ResultCodeVo();
        String resIds[] = resourceCode.split(",");
        try {
            List<AuthRoleResRef> selectedList = roleResourceService.getResourceInfoListByRoleId(roleId);
            if (selectedList != null && selectedList.size() != 0) {
                // 原先该角色已分配过资源
                boolean flag = false;
                int delSum = 0;
                // 新增
                for (int i = 0; i < resIds.length; i++) {
                    for (int j = 0; j < selectedList.size(); j++) {
                        String thisResId = selectedList.get(j).getResId().toString();// 当前resource

                        if (resIds[i].equals(thisResId)) {// 遍历到相同 resource,不做操作,跳出内层循环
                            break;
                        }
                        if (j == selectedList.size() - 1) {// 遍历结束,同时没有相同的resource,新增
                            if (!resIds[i].equals(thisResId)) {
                                AuthRoleResRef authRoleResRef = new AuthRoleResRef();
                                authRoleResRef.setRoleId(Integer.parseInt(roleId));
                                authRoleResRef.setResId(Integer.parseInt(resIds[i]));
                                flag = roleResourceService.addAuthRoleResRef(authRoleResRef);
                            }
                        }
                    }
                }
                // 删除
                for (int i = 0; i < selectedList.size(); i++) {
                    for (int j = 0; j < resIds.length; j++) {
                        String thisResId = selectedList.get(i).getResId().toString();// 当前role

                        if (resIds[j].equals(thisResId)) {// 遍历到相同 resource,不做操作,跳出内层循环
                            break;
                        }
                        if (j == (resIds.length - 1)) {// 遍历结束,同时没有相同的resource,删除
                            if (!resIds[j].equals(thisResId)) {
                                AuthRoleResRef authRoleResRef = new AuthRoleResRef();
                                authRoleResRef.setId(selectedList.get(i).getId());
                                delSum = roleResourceService.delAuthRoleResRef(authRoleResRef);
                            }
                        }
                    }
                }
                vo.setSuccess(true);
                vo.setMsg("保存成功!");
            } else {
                // 原先该角色没有分配过资源
                boolean flag = false;
                for (int i = 0; i < resIds.length; i++) {
                    AuthRoleResRef authRoleResRef = new AuthRoleResRef();
                    authRoleResRef.setRoleId(Integer.parseInt(roleId));
                    authRoleResRef.setResId(Integer.parseInt(resIds[i]));
                    flag = roleResourceService.addAuthRoleResRef(authRoleResRef);
                }
                if (flag) {
                    vo.setSuccess(true);
                    vo.setMsg("保存成功!");
                }
            }
        } catch (Exception ex) {
            log.error("保存失败!", ex);
        }
        return vo;
    }

    /**
     * 获取登录用户的菜单资源
     * 
     * @param roleCode 角色编码
     * @return
     * @author zhangqiang
     * @date 2015年12月19日 上午11:40:55
     */
    @RequestMapping(value = "/getUserMenu.html")
    public @ResponseBody List<TreeNodeVo> getUserRoleMenu(String roleCode) {
        try {
            List<TreeNodeVo> menuList = roleResourceService.getUserMenuInfo(roleCode);
            return menuList;
        } catch (Exception ex) {
            log.error("获取当前登录用户的菜单资源失败！", ex);
            return new ArrayList<TreeNodeVo>();
        }

    }
}
