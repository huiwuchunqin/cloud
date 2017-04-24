package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.res.ResPersonalFolder;

/**
 * 个人文件夹接口
 * 
 * @author lusm 2015/12/11 2015/12/08
 *
 */

public interface ResPersonalFolderDao {
    /**
     * 保存文件夹并返回文件夹id
     * 
     * @param resPersonalFolder
     * @return 文件夹id
     */
    public Integer saveResPersonalFolder(ResPersonalFolder resPersonalFolder);

    /**
     * 根据父节点，用户code和机构code获取用户的文件夹列表
     * 
     * @param userCode 用户code
     * @param orgCode 机构code
     * @param parentId 父节点id
     * @return 返回文件夹列表
     */
    public List<ResPersonalFolder> getResPersonalFolder(String userCode, String orgCode, Integer parentId);

    /**
     * 根据文件夹名和父文件夹id获取存在个数
     * 
     * @param folderName 文件夹名
     * @param parentId 父文件夹id
     * @return 个数
     */
    public int getResPersonalFolderCount(String folderName, Integer parentId, String userCode);

    /**
     * 删除文件夹 返回是否成功
     * 
     * @param folderId 文件夹id
     * @return 是否成功
     */
    public void deletePersonalFolder(Integer folderId);

}
