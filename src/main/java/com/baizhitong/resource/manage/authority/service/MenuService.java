package com.baizhitong.resource.manage.authority.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.authority.AuthResource;
import com.baizhitong.resource.model.vo.authority.TreeNodeVo;

/**
 * 菜单资源接口
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午5:18:54
 */
public interface MenuService {

    /**
     * 获取所有菜单资源
     * 
     * @param parentId 父节点ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午5:38:01
     */
    public List<Map<String, Object>> getTreeDataByParentId(String parentId) throws Exception;

    /**
     * 根据资源类型获取资源信息集合
     * 
     * @param type 资源类型
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午6:47:37
     */
    public List<AuthResource> listPageMenuResource(String type) throws Exception;

    /**
     * 根据资源ID获取资源
     * 
     * @param resId 资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午6:57:05
     */
    public AuthResource getMenuInfoById(Integer resId) throws Exception;

    /**
     * 获取资源信息集合
     * 
     * @param parentId 父节点ID
     * @param resUri 资源uri
     * @param param 资源参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:03:20
     */
    public List<AuthResource> getMenuResListByPIdAndUri(String parentId, String resUri, String param) throws Exception;

    /**
     * 根据资源名称和父节点ID获取资源
     * 
     * @param name 资源名称
     * @param pid 父节点ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:09:58
     */
    public AuthResource getAuthResourceByNameAndPId(String name, Integer pid) throws Exception;

    /**
     * 添加资源
     * 
     * @param resource 资源实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:16:06
     */
    public boolean addMenu(AuthResource resource) throws Exception;

    /**
     * 根据节点ID修改节点状态
     * 
     * @param id 节点ID
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:21:58
     */
    public int modifyStatusByResId(String id) throws Exception;

    /**
     * 删除资源
     * 
     * @param id 资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:40:21
     */
    public int deleteMenu(String id) throws Exception;

    /**
     * 获取管理员登录状态下的菜单信息（即所有未删除的菜单资源）
     * 
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月19日 上午11:20:04
     */
    public List<TreeNodeVo> getAdminMenuInfo() throws Exception;

    /**
     * 通过排序顺序，父节点查询资源 ()<br>
     * 
     * @param dispOrder 排序顺序
     * @param parentId 父节点id
     * @return
     */
    public AuthResource getResourceByDispOrder(Integer dispOrder, Integer parentId);

}
