package com.baizhitong.resource.manage.authority.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.manage.authority.service.MenuService;
import com.baizhitong.resource.manage.authority.service.RoleResourceService;
import com.baizhitong.resource.model.authority.AuthResource;
import com.baizhitong.resource.model.vo.authority.TreeNodeVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.JsonUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 菜单资源控制器
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午5:13:27
 */
@Controller
@RequestMapping(value = "/manage/menu")
public class MenuAction extends BaseAction {

    /** 菜单信息接口 */
    private @Autowired MenuService         menuService;
    /** 角色资源信息接口 */
    private @Autowired RoleResourceService roleResourceService;

    /**
     * 跳转到菜单资源信息页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午5:16:18
     */
    @RequestMapping(value = "menuInfo.html")
    public String getMenuTreeInfoPage(HttpServletRequest request, ModelMap map) {
        return "/manage/authority/menu_info.html";
    }

    /**
     * 查询所有菜单信息，以树状形式展现
     * 
     * @param request 请求
     * @param parentId 顶级父节点ID
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午5:34:22
     */
    @RequestMapping(value = "searchAll.html")
    public @ResponseBody List<Map<String, Object>> getMenuTreeData(HttpServletRequest request, String parentId,
                    ModelMap map) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        try {
            dataList = menuService.getTreeDataByParentId(parentId);
        } catch (Exception ex) {
            log.error("未查询到数据 ", ex);
        }
        return dataList;
    }

    /**
     * 跳转到菜单资源新增页面
     * 
     * @param request 请求
     * @param map
     * @param pid 父节点ID
     * @return
     */
    @RequestMapping(value = "/menuAdd.html")
    public String toMenuAddPage(HttpServletRequest request, ModelMap map, String pid) {
        map.put("pid", pid);
        map.put("isModify", false);
        return "/manage/authority/menu_edit.html";
    }

    /**
     * 根据资源类型获取菜单资源列表
     * 
     * @param type 资源类型
     * @param map
     * @return
     */
    @RequestMapping(value = "/listPageRes.html",
                    method = RequestMethod.POST)
    public @ResponseBody List<AuthResource> listParentMenuRes(String type, ModelMap map) {
        try {
            List<AuthResource> list = menuService.listPageMenuResource(type);
            AuthResource resource = new AuthResource();
            resource.setId(0);
            resource.setName("请选择");
            list.add(0, resource);
            return list;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 添加和修改菜单资源
     * 
     * @param request 请求
     * @param resource 资源信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/addMenu.html")
    public @ResponseBody ResultCodeVo addResource(HttpServletRequest request, AuthResource resource, ModelMap map) {
        UserInfoVo user =getUserInfoVo();
        if (StringUtils.isEmpty(resource.getName())) {
            return ResultCodeVo.errorCode("资源名称不能为空！");
        }
        try {
            boolean flag = false;
            ResultCodeVo vo = new ResultCodeVo();
            AuthResource resourceEntity = new AuthResource();
            Integer resourceID = resource.getId();
            if (resource != null) {
                resourceEntity = menuService.getAuthResourceByNameAndPId(resource.getName(), resource.getPid());
                if (resourceID == null) {
                    if (resourceEntity == null) {
                        AuthResource sameOrderResource = menuService.getResourceByDispOrder(resource.getDispOrder(),
                                        resource.getPid());
                        if (sameOrderResource != null) {
                            return ResultCodeVo.errorCode("已存在相同排序顺序的资源");
                        }
                        resource.setFlagDelete(0);
                        resource.setNameFull(resource.getName());
                        resource.setCreator(user.getLoginAccount());
                        resource.setCreateTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
                        resource.setCreatorIP(getIp());
                        resource.setModifierIP(getIp());
                        resource.setModifier(user.getLoginAccount());
                        resource.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
                        if (resource.getPid() == null) {
                            resource.setPid(0);
                            resource.setStatus(1);
                        } else {
                            resource.setStatus(0);
                            menuService.modifyStatusByResId(resource.getPid().toString());
                        }
                        if (resource.getPid() != null && resource.getUri() != null) {
                            String parentId = resource.getPid().toString();
                            String resUri = resource.getUri();
                            String param = resource.getParam();
                            List<AuthResource> resList = menuService.getMenuResListByPIdAndUri(parentId, resUri, param);
                            if (resList != null && resList.size() > 0 && !StringUtils.isEmpty(resUri)
                                            && !StringUtils.isEmpty(param)) {
                                return ResultCodeVo.errorCode("上层资源相同的资源的URI不可以重复！");
                            } else {
                                flag = menuService.addMenu(resource);
                            }
                        } else {
                            flag = menuService.addMenu(resource);
                        }
                    } else {
                        return ResultCodeVo.errorCode("该资源已经存在");
                    }
                } else {
                    if (resourceEntity != null && resourceID.intValue() != resourceEntity.getId()) {
                        return ResultCodeVo.errorCode("该资源已经存在");
                    } else {
                        AuthResource sameOrderResource = menuService.getResourceByDispOrder(resource.getDispOrder(),
                                        resource.getPid());
                        if (sameOrderResource != null
                                        && sameOrderResource.getId().intValue() != resource.getId().intValue()) {
                            return ResultCodeVo.errorCode("已存在相同排序顺序的资源");
                        }
                        AuthResource oldResource = menuService.getMenuInfoById(resource.getId());
                        resource.setNameFull(oldResource.getNameFull());
                        resource.setCreateTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
                        resource.setCreator(oldResource.getCreator());
                        resource.setCreatorIP(oldResource.getCreatorIP());
                        resource.setModifierIP(getIp());
                        resource.setModifier(user.getLoginAccount());
                        resource.setFlagDelete(oldResource.getFlagDelete());
                        resource.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
                        resource.setStatus(oldResource.getStatus());
                        flag = menuService.addMenu(resource);
                    }
                }
            }
            if (flag) {
                vo.setSuccess(true);
                if (resourceID != null) {
                    vo.setMsg("修改成功");
                } else {
                    vo.setMsg("保存成功");
                }
            } else {
                vo.setSuccess(false);
                if (resourceID != null) {
                    vo.setMsg("修改失败");
                } else {
                    vo.setMsg("保存失败");
                }
            }
            return vo;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResultCodeVo.errorCode("保存失败");
        }
    }

    /**
     * 跳转到菜单资源修改页面
     * 
     * @param request
     * @param id 资源ID
     * @param map
     * @return
     */
    @RequestMapping(value = "/menuEdit.html")
    public String toMenuEditPage(HttpServletRequest request, String id, ModelMap map) {
        try {
            AuthResource resource = menuService.getMenuInfoById(Integer.parseInt(id));
            map.put("resource", JsonUtils.ObjectToJson(resource));
            map.put("isModify", true);
        } catch (Exception ex) {
            log.error("获取资源信息失败", ex);
        }
        return "/manage/authority/menu_edit.html";
    }

    /**
     * 删除资源
     * 
     * @param request 请求
     * @param id 资源ID
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午7:37:08
     */
    @RequestMapping(value = "/delete.html")
    public @ResponseBody ResultCodeVo deleteMenu(HttpServletRequest request, String id) {
        ResultCodeVo vo = new ResultCodeVo();
        try {
            int delSum = menuService.deleteMenu(id);
            if (delSum == 1) {
                vo.setSuccess(true);
                vo.setMsg("删除成功！");

            } else if (delSum == 2) {
                vo.setSuccess(false);
                vo.setMsg("删除失败，该资源下有子资源无法删除！");
            } else if (delSum == 3) {
                vo.setSuccess(false);
                vo.setMsg("删除失败，该资源已经被分配给角色无法删除！");
            }
        } catch (Exception ex) {
            log.error("删除菜单资源信息失败!", ex);
            vo.setSuccess(false);
            vo.setMsg("删除失败！");
        }
        return vo;
    }

    /**
     * 获取管理员登录下的菜单信息
     * 
     * @param request 请求
     * @return
     * @author zhangqiang
     * @date 2015年12月19日 上午11:15:42
     */
    @RequestMapping(value = "/getAdminMenu.html")
    public @ResponseBody List<TreeNodeVo> getAdminMenu(HttpServletRequest request) {
        try {
            List<TreeNodeVo> menuList = roleResourceService.getUserMenuInfo(CoreConstants.USER_ROLE_SUPER_ADMIN);
            if (menuList != null && menuList.size() > 0) {
                return menuList;
            } else {
                List<TreeNodeVo> adminMenuInfo = menuService.getAdminMenuInfo();
                return adminMenuInfo;
            }
        } catch (Exception ex) {
            log.error("获取管理员菜单失败！", ex);
            return new ArrayList<TreeNodeVo>();
        }

    }
}
