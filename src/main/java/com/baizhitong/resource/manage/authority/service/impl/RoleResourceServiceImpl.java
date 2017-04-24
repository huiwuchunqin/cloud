package com.baizhitong.resource.manage.authority.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baizhitong.resource.dao.authority.AuthResourceDao;
import com.baizhitong.resource.dao.authority.AuthRoleResRefDao;
import com.baizhitong.resource.manage.authority.service.RoleResourceService;
import com.baizhitong.resource.model.authority.AuthResource;
import com.baizhitong.resource.model.authority.AuthRoleResRef;
import com.baizhitong.resource.model.vo.authority.TreeNodeAttr;
import com.baizhitong.resource.model.vo.authority.TreeNodeVo;

/**
 * 角色资源接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月18日 上午9:34:47
 */
@Service("roleResourceService")
public class RoleResourceServiceImpl implements RoleResourceService {
    /** 资源Dao */
    private @Autowired AuthResourceDao   authResourceDao;
    /** 角色资源关联信息Dao */
    private @Autowired AuthRoleResRefDao authRoleResRefDao;

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
     * 获取菜单下的资源信息列表
     */
    @Override
    public List<TreeNodeVo> getResourceListByRoleID(String roleId) throws Exception {
        List<AuthResource> allMenuInfoList = authResourceDao.getAllNoDeleteResource();// 获取所有未删除的资源
        List<AuthRoleResRef> selectedResList = this.getResourceInfoListByRoleId(roleId);// 获取该角色已经拥有的资源列表信息
        List<Integer> parentIdList = new ArrayList<Integer>();// List 用于存放资源的父节点ID值
        if (allMenuInfoList != null && allMenuInfoList.size() > 0) {
            for (int i = 0; i < allMenuInfoList.size(); i++) {
                Integer thisParentId = allMenuInfoList.get(i).getPid();
                if (!parentIdList.contains(thisParentId)) {
                    parentIdList.add(thisParentId);
                }
            }
        }
        List<TreeNodeVo> treeNodeVoList = new ArrayList<TreeNodeVo>();
        List<TreeNodeVo> topParentNodeList = new ArrayList<TreeNodeVo>();
        List<TreeNodeVo> handledList = new ArrayList<TreeNodeVo>();
        if (allMenuInfoList != null && allMenuInfoList.size() > 0) {
            for (AuthResource resource : allMenuInfoList) {
                TreeNodeVo treeNodeVo = new TreeNodeVo();
                TreeNodeAttr attr = new TreeNodeAttr();
                if ((!parentIdList.contains(resource.getId())) && !resource.getPid().equals("0")) {
                    if (selectedResList != null && selectedResList.size() > 0) {
                        for (int i = 0; i < selectedResList.size(); i++) {
                            AuthRoleResRef selectedRes = selectedResList.get(i);
                            if (resource.getId().intValue() == selectedRes.getResId().intValue()) {
                                treeNodeVo.setChecked(true);
                                break;
                            }
                        }
                    }
                }
                String id = resource.getId().toString();
                String name = resource.getName();
                String parentId = resource.getPid().toString();
                attr.setPid(parentId);
                treeNodeVo.setId(id);
                treeNodeVo.setText(name);
                treeNodeVo.setAttributes(attr);
                treeNodeVoList.add(treeNodeVo);
                if (treeNodeVo.getAttributes().getPid().equals("0")) {
                    topParentNodeList.add(treeNodeVo);
                }
            }
        }
        if (topParentNodeList != null && topParentNodeList.size() > 0) {
            for (TreeNodeVo treeNodeVoVo : topParentNodeList) {
                handledList.add(handleResource(treeNodeVoList, treeNodeVoVo));
            }
        }
        return handledList;
    }

    /**
     * 根据角色ID查询分配给该角色的资源ID
     */
    @Override
    public List<AuthRoleResRef> getResourceInfoListByRoleId(String roleId) throws Exception {
        return authRoleResRefDao.getResIDListByRoleId(roleId);
    }

    /**
     * 添加一个角色资源关联信息
     */
    @Override
    public boolean addAuthRoleResRef(AuthRoleResRef authRoleResRef) throws Exception {
        return authRoleResRefDao.addAuthRoleResRef(authRoleResRef);
    }

    /**
     * 删除一个角色资源关联信息
     */
    @Override
    public int delAuthRoleResRef(AuthRoleResRef authRoleResRef) throws Exception {
        return authRoleResRefDao.delAuthRoleResRef(authRoleResRef);
    }

    /**
     * 获取当前登录用户的菜单资源
     */
    @Override
    public List<TreeNodeVo> getUserMenuInfo(String roleCode) throws Exception {
        List<Map<String, Object>> roleResource = authRoleResRefDao.getRoleResourceList(roleCode);
        List<TreeNodeVo> treeNodeList = new ArrayList<TreeNodeVo>();
        List<TreeNodeVo> topParentNodeList = new ArrayList<TreeNodeVo>();
        List<TreeNodeVo> handledList = new ArrayList<TreeNodeVo>();
        if (roleResource != null && roleResource.size() > 0) {
            for (Map<String, Object> map : (List<Map<String, Object>>) roleResource) {
                TreeNodeVo treeNode = new TreeNodeVo();
                String id = MapUtils.getString(map, "id");
                String name = MapUtils.getString(map, "name");
                String uri = MapUtils.getString(map, "uri");
                String parentId = MapUtils.getString(map, "pid");
                String param = MapUtils.getString(map, "param");
                TreeNodeAttr attr = new TreeNodeAttr();
                attr.setPid(parentId);
                if (!StringUtils.isEmpty(param)) {
                    String resUri = uri + "?" + param;
                    attr.setUrl(resUri);
                } else {
                    attr.setUrl(uri);
                }
                treeNode.setId(id);
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

}
