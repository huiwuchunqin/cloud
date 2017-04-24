package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ResPersonalFolderDao;
import com.baizhitong.resource.model.res.ResPersonalFolder;
import com.baizhitong.utils.StringUtils;

/**
 * 个人文件夹接口
 * 
 * @author lusm 2015/12/11
 *
 */
@Service("resPersonalFolderDao")
public class ResPersonalFolderDaoImpl extends BaseSqlServerDao<ResPersonalFolder> implements ResPersonalFolderDao {
    /**
     * 保存文件夹并返回文件夹id
     * 
     * @param resPersonalFolder
     * @return 文件夹id
     */
    @Override
    public Integer saveResPersonalFolder(ResPersonalFolder resPersonalFolder) {
        Integer id = null;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(resPersonalFolder), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 根据父节点，用户code和机构code获取用户的文件夹列表
     * 
     * @param userCode 用户code
     * @param orgCode 机构code
     * @param parentId 父节点id
     * @return 返回文件夹列表
     */
    @Override
    public List<ResPersonalFolder> getResPersonalFolder(String userCode, String orgCode, Integer parentId) {
        QueryRule queryRule = QueryRule.getInstance();
        if (!StringUtils.isEmpty(userCode)) {
            queryRule.andEqual("userCode", userCode);
        }
        if (!StringUtils.isEmpty(orgCode)) {
            queryRule.andEqual("orgCode", orgCode);
        }
        if (null != parentId) {
            queryRule.andEqual("parentId", parentId);
        }
        return super.find(queryRule);
    }

    /**
     * 根据文件夹名和父文件夹id获取存在个数
     * 
     * @param folderName 文件夹名
     * @param parentId 父文件夹id
     * @return 个数
     */
    @Override
    public int getResPersonalFolderCount(String folderName, Integer parentId, String userCode) {
        QueryRule queryRule = QueryRule.getInstance();
        if (!StringUtils.isEmpty(folderName)) {
            queryRule.andEqual("folderName", folderName);
        }
        if (!StringUtils.isEmpty(userCode)) {
            queryRule.andEqual("userCode", userCode);
        }
        if (null != parentId) {
            queryRule.andEqual("parentId", parentId);
        }
        return (int) super.getCount(queryRule);
    }

    /**
     * 删除文件夹 返回是否成功
     * 
     * @param folderId 文件夹id
     * @return 是否成功
     */
    @Override
    public void deletePersonalFolder(Integer folderId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", folderId);
        super.deleteByPK(map);
    }

}
