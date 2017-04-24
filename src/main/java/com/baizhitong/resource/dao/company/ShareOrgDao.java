package com.baizhitong.resource.dao.company;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.company.ShareOrg;

/**
 * 机构dao接口
 * 
 * @author creator BZT 2016年1月22日 上午11:08:24
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareOrgDao {
    /**
     * 查询机构信息 ()<br>
     * 
     * @param orgName 机构名称
     * @param sectionCode 学段编码
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    Page getOgrPageInfo(String orgName, String sectionCode, Integer pageNo, Integer pageSize);

    /**
     * 查询机构信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    ShareOrg getOrg(String orgCode);

    /**
     * 查询机构信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    Map<String, Object> getOrgInfo(String orgCode);

    /**
     * 查询机构学段 ()<br>
     * 
     * @param orgCode
     * @return
     */
    List<Map<String, Object>> getOrgSection(String orgCode);

    List<Map<String, Object>> getOrgList();

    /**
     * 查询机构信息 ()<br>
     * 
     * @param sectionCode 学段编码
     * @return
     */
    List<Map<String, Object>> getOrgList(String sectionCode);

    /**
     * 
     * 根据机构名称查询机构
     * 
     * @param orgName
     * @return
     */
    List<ShareOrg> getOrgByName(String orgName);

    /**
     * 保存机构信息 ()<br>
     * 
     * @param org
     * @return
     */
    boolean saveOrUpdate(ShareOrg org);

    /**
     * 根据guid查询机构 ()<br>
     * 
     * @param cdGuid
     * @return
     */
    ShareOrg getByGuid(String cdGuid);

    /**
     * 根据域名查询机构 ()<br>
     * 
     * @param domainID
     * @return
     */
    ShareOrg getOrgByDomain(Integer domainID);

    /**
     * 更新机构信息 ()<br>
     * 
     * @param org
     * @param ip
     * @param modifyPgm
     * @return
     */
    int updateAgency(ShareOrg org, String ip, String modifyPgm);

    /**
     * 查询代理商信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public Map<String, Object> getAgencyInfo(String orgCode);

    /**
     * 查询代理商信息
     * 
     * @param param 查询条件
     * @param page 页码
     * @param rows 记录数
     * 
     *        ()<br>
     * @return page
     */
    Page getAgencyPage(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询代理商学校 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getAgencySchool(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 禁用或者启用机构 ()<br>
     * 
     * @param state 1启用 0禁用
     * @param orgCode 机构编码
     * @return 操作结果
     */
    int updateState(Integer state, String orgCode);

    /**
     * 查询邮箱一样的机构 ()<br>
     * 
     * @param mail
     * @param orgCode
     * @return
     */
    long getSameMailOrg(String mail, String orgCode);

    /**
     * 查询手机号一样的机构 ()<br>
     * 
     * @param phone
     * @param orgCode
     * @return
     */
    long getSamePhoneOrg(String phone, String orgCode);

    /**
     * 
     * (根据当前日期批量更新机构的学年学期信息)<br>
     * 
     * @param currentDate 当前日期
     * @return 更新记录数
     */
    int updateOrgYearTermInfoBatch(Timestamp currentDate);

    /**
     * 查询相同域名的机构数量 ()<br>
     * 
     * @param domainID 域名id
     * @return
     */
    public long getSameDomainCount(Integer domainID);

    /**
     * 更新机构域名 ()<br>
     * 
     * @param orgCode
     * @param domainID
     * @return
     */
    public int updateOrgDomain(String orgCode, Integer domainID);

    /**
     * 
     * (查询没有学段的机构列表)<br>
     * 
     * @return 没有学段的机构列表
     */
    List<Map<String, Object>> selectNoSectionOrgList();

}
