package com.baizhitong.resource.manage.agency.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.company.ShareOrg;

/**
 * 代理商接口 IAgencyService TODO
 * 
 * @author creator gaow 2017年2月21日 下午2:52:10
 * @author updater
 *
 * @version 1.0.0
 */
public interface IAgencyService {
    /**
     * 更新代理商信息 ()<br>
     * 
     * @param org 代理商信息
     * @return 操作结果
     */
    ResultCodeVo updateAgency(ShareOrg org);

    /**
     * 新增学校 ()<br>
     * 
     * @param org 机构信息
     * @return 操作结果
     */
    ResultCodeVo addAgencySchool(HttpServletRequest request, ShareOrg org, String password, String loginAccount);

    /**
     * 新增代理商信息 ()<br>
     * 
     * @param org 代理商信息
     * @return 操作结果
     */
    ResultCodeVo addAgency(HttpServletRequest request, ShareOrg org, String password, String loginAccount);

    /**
     * 查询代理商信息 ()<br>
     * 
     * @param param 查询参数
     * @param page 页码
     * @param rows 每页记录数
     * @return page页
     */
    Page list(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询学校 ()<br>
     * 
     * @param param 查询参数
     * @param page 页码
     * @param rows 记录数
     * @return page
     */
    Page listSchool(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 禁用或者启用机构 ()<br>
     * 
     * @param state 1启用 0禁用
     * @param orgCode 机构编码
     * @return 操作结果
     */
    ResultCodeVo updateState(Integer state, String orgCode);

}
