package com.baizhitong.resource.manage.company.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;

/**
 * 机构接口 ICompanyService TODO
 * 
 * @author creator BZT 2016年1月22日 上午11:32:50
 * @author updater
 *
 * @version 1.0.0
 */
public interface ICompanyService {
    /**
     * 查询机构分页信息 ()<br>
     * 
     * @param orgName 机构名称
     * @param sectionCode 学段编码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    Page getOrgPageInfo(String orgName, String sectionCode, Integer pageSize, Integer pageNo);

    /**
     * 查询机构信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public CompanyInfoVo getCompanyInfo(String orgCode);

    /**
     * 查询机构学段 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareCodeSectionVo> getCompanySection(String orgCode);

    /**
     * 查询机构年级 ()<br>
     * 
     * @param orgCode
     * @return
     * @throws Exception
     */
    public List<ShareCodeGradeVo> getCompanyGrade(String orgCode) throws Exception;

    /**
     * 
     * 根据机构名称查询机构
     * 
     * @param orgName 机构名称
     * @param isLike 1 模糊查询 0全文匹配
     * @return
     * @throws Exception
     */
    public List<ShareOrg> getCompanyByName(String orgName, Integer isLike) throws Exception;

    /**
     * 保存机构信息 ()<br>
     * 
     * @param org 机构信息
     * @param sectionCodes 学段编码
     * @param password 密码
     * @param role 角色
     * @return
     */
    public ResultCodeVo saveCompany(ShareOrg org, String sectionCodes, String password, String loginAccount,
                    String role);

    /**
     * 更新机构信息 ()<br>
     * 
     * @param orgCode
     * @param orgName
     * @param mail
     * @param orgNameShort
     * @param phone
     * @param sectionCode
     * @param logoUrl
     * @param introduction
     * @return
     */
    public ResultCodeVo updateCompany(String orgCode, String orgName, String mail, String orgNameShort, String phone,
                    String sectionCode, String logoUrl, String introduction, String topName);

    /**
     * 根据学段查询机构 ()<br>
     * 
     * @param sectionCode
     * @return
     */
    List<Map<String, Object>> getSectionCompany(String sectionCode);

    /**
     * 查询所有机构 ()<br>
     * 
     * @return
     */
    List<CompanyInfoVo> getAllCompany();

    /**
     * 
     * (自动更新机构表的学年学期信息)<br>
     */
    void autoUpdateOrgYearTerm();
}
