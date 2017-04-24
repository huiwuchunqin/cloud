package com.baizhitong.resource.manage.authority.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baizhitong.resource.dao.authority.AuthResourceDao;
import com.baizhitong.resource.dao.authority.AuthRoleResRefDao;
import com.baizhitong.resource.manage.authority.service.MenuService;
import com.baizhitong.resource.model.authority.AuthResource;
import com.baizhitong.resource.model.authority.AuthRoleResRef;
import com.baizhitong.resource.model.vo.authority.TreeNodeAttr;
import com.baizhitong.resource.model.vo.authority.TreeNodeVo;

/**
 * 菜单资源接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午5:19:55
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    /** 菜单资源Dao */
    private @Autowired AuthResourceDao   authResourceDao;
    /** 角色资源关联信息Dao */
    private @Autowired AuthRoleResRefDao AuthRoleResRefDao;

    /**
     * 获取所有菜单资源
     */
    @Override
    public List<Map<String, Object>> getTreeDataByParentId(String parentId) throws Exception {
        return authResourceDao.getTreeDataByParentId(parentId);
    }

    /**
     * 根据资源类型获取资源信息集合
     */
    @Override
    public List<AuthResource> listPageMenuResource(String type) throws Exception {
        return authResourceDao.getPageResourceList(type);
    }

    /**
     * 根据资源ID获取资源
     */
    @Override
    public AuthResource getMenuInfoById(Integer resId) throws Exception {
        return authResourceDao.getResourceByID(resId);
    }

    /**
     * 获取资源集合
     */
    @Override
    public List<AuthResource> getMenuResListByPIdAndUri(String parentId, String resUri, String param) throws Exception {
        return authResourceDao.getResourceListBySomeParam(parentId, resUri, param);
    }

    /**
     * 根据资源名称和父节点ID获取资源
     */
    @Override
    public AuthResource getAuthResourceByNameAndPId(String name, Integer pid) throws Exception {
        return authResourceDao.getResByNameAndPId(name, pid);
    }

    /**
     * 添加资源
     */
    @Override
    public boolean addMenu(AuthResource resource) throws Exception {
        return authResourceDao.addResource(resource);
    }

    /**
     * 根据节点ID修改资源节点状态
     */
    @Override
    public int modifyStatusByResId(String id) throws Exception {
        return authResourceDao.modifyStatusByResId(id);
    }

    /**
     * 删除资源
     * 
     * @param id 资源ID
     * @return 0:删除出错 1:删除成功 2:该资源下存在子资源,无法删除 3:该资源已经被分配给角色,无法删除
     */
    @Override
    public int deleteMenu(String id) throws Exception {
        List<AuthResource> menuList = authResourceDao.getResourceListByParentId(id);
        List<AuthRoleResRef> list = AuthRoleResRefDao.getRoleResListInfoByResID(Integer.parseInt(id));
        try {
            if (menuList == null || menuList.size() == 0) {// 该资源下没有子资源
                if (list == null || list.size() == 0) {// 该资源下没有角色信息
                    int delSum = authResourceDao.deleteResource(id);
                    if (delSum == 1) {
                        return 1;
                    } else {
                        return 0;
                    }

                } else {
                    return 3;
                }
            } else {
                return 2;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取管理员登录状态下的菜单资源
     */
    @Override
    public List<TreeNodeVo> getAdminMenuInfo() throws Exception {
        List<AuthResource> allResource = authResourceDao.getAllNoDeleteResource();
        List<TreeNodeVo> treeNodeList = new ArrayList<TreeNodeVo>();
        List<TreeNodeVo> topParentNodeList = new ArrayList<TreeNodeVo>();
        List<TreeNodeVo> handledList = new ArrayList<TreeNodeVo>();
        if (allResource != null && allResource.size() > 0) {
            for (AuthResource resource : allResource) {
                TreeNodeVo treeNode = new TreeNodeVo();
                String id = resource.getId().toString();
                String name = resource.getName();
                String uri = resource.getUri();
                String param = resource.getParam();
                String parentId = resource.getPid().toString();
                TreeNodeAttr attr = new TreeNodeAttr();
                attr.setPid(parentId);
                if (!StringUtils.isEmpty(param)) {
                    String resUri = uri + "?" + param;
                    attr.setUrl(resUri);
                } else {
                    attr.setUrl(uri);
                }
                treeNode.setId(id);
                treeNode.setState("");
                treeNode.setText(name);
                treeNode.setAttributes(attr);
                treeNodeList.add(treeNode);
                if (treeNode.getAttributes().getPid().equals("0")) {
                    topParentNodeList.add(treeNode);
                }
            }
        }
        if (topParentNodeList != null && topParentNodeList.size() > 0) {
            for (TreeNodeVo treeNodeVo : topParentNodeList) {
                handledList.add(handleResource(treeNodeList, treeNodeVo));
            }
        }
        for (TreeNodeVo node : treeNodeList) {
            if (node.getChildren().size() > 0) {
                node.setState("closed");
            }
        }
        return handledList;
    }

    /**
     * 处理资源列表
     */
    public TreeNodeVo handleResource(List<TreeNodeVo> TreeNodeList, TreeNodeVo parentNode) throws Exception {
        List<TreeNodeVo> children = new ArrayList<TreeNodeVo>();
        for (TreeNodeVo treeNodeVo : TreeNodeList) {
            if (treeNodeVo.getAttributes().getPid().equals(parentNode.getId())) {
                children.add(handleResource(TreeNodeList, treeNodeVo));
            }
        }
        parentNode.setChildren(children);
        return parentNode;
    }

    /**
     * 通过排序顺序，父节点查询资源 ()<br>
     * 
     * @param dispOrder 排序顺序
     * @param parentId 父节点id
     * @return
     */
    public AuthResource getResourceByDispOrder(Integer dispOrder, Integer parentId) {
        return authResourceDao.getResourceByDispOrder(dispOrder, parentId);
    }
}
