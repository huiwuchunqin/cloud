package com.baizhitong.resource.dao.authority.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.authority.AuthResourceDao;
import com.baizhitong.resource.model.authority.AuthResource;

/**
 * 菜单资源数据接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午5:24:35
 */
@Service("authResourceSqlServerDao")
public class AuthResourceSqlServerDaoImpl extends BaseSqlServerDao<AuthResource> implements AuthResourceDao {

    /**
     * 获取所有菜单资源
     */
    @Override
    public List<Map<String, Object>> getTreeDataByParentId(String parentId) throws Exception {
        List<Map<String, Object>> dataList = this.getResourceDataByParentId(parentId);
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> map = dataList.get(i);
                Integer status = MapUtils.getInteger(map, "status");
                if (status != null && status == 1) {
                    map.put("children", getTreeDataByParentId(MapUtils.getString(map, "id")));
                } else {
                    continue;
                }
            }

        }
        return dataList;
    }

    /**
     * 根据父节点获取子资源
     * 
     * @param parentId 父节点ID
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 下午5:51:29
     */
    public List<Map<String, Object>> getResourceDataByParentId(String parentId) {
        String sql = " select * from auth_resource where flagDelete=0 and pid=? order by dispOrder";
        List<Map<String, Object>> parentResList = super.findBySql(sql, new Object[] { parentId });
        return parentResList;
    }

    /**
     * 根据资源类型获取资源集合
     */
    @Override
    public List<AuthResource> getPageResourceList(String type) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("type", type);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        qr.addAscOrder("pid");
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }

    /**
     * 根据资源ID获取资源
     */
    @Override
    public AuthResource getResourceByID(Integer resId) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", resId);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 获取资源信息集合
     */
    @Override
    public List<AuthResource> getResourceListBySomeParam(String parentId, String resUri, String param)
                    throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("pid", parentId);
        qr.andEqual("uri", resUri);
        qr.andEqual("param", param);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }

    /**
     * 根据资源名称和父节点ID获取资源
     */
    @Override
    public AuthResource getResByNameAndPId(String name, Integer pid) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        if (pid != null) {
            qr.andEqual("pid", pid);
        }
        if (!StringUtils.isEmpty(name)) {
            qr.andEqual("name", name);
        }
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 添加资源
     */
    @Override
    public boolean addResource(AuthResource resource) throws Exception {
        return super.saveOne(resource);
    }

    /**
     * 根据节点ID修改资源节点状态
     */
    @Override
    public int modifyStatusByResId(String id) throws Exception {
        String sql = "update auth_resource set status=1 where id=:id";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return super.update(sql, param);
    }

    /**
     * 根据父节点ID查询子资源集合
     */
    @Override
    public List<AuthResource> getResourceListByParentId(String id) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("pid", id);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }

    /**
     * 删除资源
     */
    @Override
    public int deleteResource(String id) throws Exception {
        String sql = "update auth_resource set flagDelete=1 where id=:id";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return super.update(sql, param);
    }

    /**
     * 获取所有未删除的资源
     */
    @Override
    public List<AuthResource> getAllNoDeleteResource() throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }

    /**
     * 通过父节点和排序顺序查询资源 ()<br>
     * 
     * @param dispOrder 排序顺序
     * @param parentId 父节点id
     * @return
     */
    public AuthResource getResourceByDispOrder(Integer dispOrder, Integer parentId) {
        QueryRule qr = QueryRule.getInstance();
        if (dispOrder != null) {
            qr.andEqual("dispOrder", dispOrder);
        }
        if (parentId != null) {
            qr.andEqual("pid", parentId);
        } else {
            qr.andIsNull("pid");
        }
        return super.findUnique(qr);
    }
}
