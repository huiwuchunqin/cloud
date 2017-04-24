package com.baizhitong.resource.dao.authority;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.authority.AuthResource;

/**
 * 菜单资源数据接口
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午5:22:22
 */
public interface AuthResourceDao {

    /**
     * 根据父资源ID获取菜单资源
     * 
     * @param parentId 父资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午5:49:38
     */
    public List<Map<String, Object>> getTreeDataByParentId(String parentId) throws Exception;

    /**
     * 根据资源类型获取资源集合
     * 
     * @param type 资源类型
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午6:50:50
     */
    public List<AuthResource> getPageResourceList(String type) throws Exception;

    /**
     * 根据资源Id获取资源
     * 
     * @param resId 资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午6:58:51
     */
    public AuthResource getResourceByID(Integer resId) throws Exception;

    /**
     * 获取资源信息集合
     * 
     * @param parentId 父节点ID
     * @param resUri 资源uri
     * @param param 资源参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:06:02
     */
    public List<AuthResource> getResourceListBySomeParam(String parentId, String resUri, String param) throws Exception;

    /**
     * 根据资源名称和父节点ID获取资源
     * 
     * @param name 资源名称
     * @param pid 父节点ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:12:58
     */
    public AuthResource getResByNameAndPId(String name, Integer pid) throws Exception;

    /**
     * 添加资源
     * 
     * @param resource 资源实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:17:59
     */
    public boolean addResource(AuthResource resource) throws Exception;

    /**
     * 根据节点ID修改资源节点状态
     * 
     * @param id 节点ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:25:04
     */
    public int modifyStatusByResId(String id) throws Exception;

    /**
     * 根据父节点ID获取资源信息集合
     * 
     * @param id 父节点ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:48:22
     */
    public List<AuthResource> getResourceListByParentId(String id) throws Exception;

    /**
     * 删除资源
     * 
     * @param id 资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:48:56
     */
    public int deleteResource(String id) throws Exception;

    /**
     * 获取所有未删除的资源
     * 
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 上午10:14:15
     */
    public List<AuthResource> getAllNoDeleteResource() throws Exception;

    /**
     * 通过父节点和排序顺序查询资源 ()<br>
     * 
     * @param dispOrder 排序顺序
     * @param parentId 父节点id
     * @return
     */
    public AuthResource getResourceByDispOrder(Integer dispOrder, Integer parentId);

}
