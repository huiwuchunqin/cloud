package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.company.ShareOrgSection;

public interface ShareOrgSectionDao {
    boolean addOrgSection(ShareOrgSection section);

    /**
     * 查询机构学段 ()<br>
     * 
     * @param sectionName 学段名称
     * @param orgCode 机构编码
     * @return
     */
    public List<Map<String, Object>> getOrgSection(String sectionName, String orgCode);

    /**
     * 查询机构学段 ()<br>
     * 
     * @param orgCode 机构编码
     * @return
     */
    public List<ShareOrgSection> getOrgSection(String orgCode);

    /**
     * 更新机构学段信息 ()<br>
     * 
     * @param orgCode 机构编码
     * @param sectionCode 学段编码
     * @return
     */
    public int updateOrgSection(String orgCode, String sectionCode);
}
