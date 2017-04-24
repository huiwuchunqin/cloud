package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.vo.res.ResVo;

/**
 * flash接口 FlashService TODO
 * 
 * @author creator gaow 2016年12月20日 上午10:33:35
 * @author updater
 *
 * @version 1.0.0
 */
public interface FlashService {
    /**
     * 查询flash ()<br>
     * 
     * @param param 查询参数
     * @param page 页码
     * @param rows 行数
     * @return page
     */
    Page selectFlash(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询flash ()<br>
     * 
     * @param resCode 资源编码
     * @return flash
     */
    ResVo getFlash(String resCode);

    /**
     * 
     * ()<br>
     * 
     * @param resId 资源id
     * @param resDesc 资源描述
     * @param resName 资源名称
     * @param userCode 用户编码
     * @param userName 用户名称
     * @param ip ip地址
     * @param kpCodes 知识点
     * @param tbcCodes 章节
     * @param gradeCodes 年级
     * @param subejctCodes 学科
     * @param sectionCodes 学段
     * @param uploader 上传人
     * @param makerOrgCode 制作机构
     * @param makerOrgName 制作机构名称
     * @param makerCode 制作人
     * @param flagAllowDownload 允许下载
     * @param coverPath 封面地址
     * @param tags 标签
     * @return
     * @throws Exception
     */
    ResultCodeVo flashInfoUpdate(Integer resId, String resDesc, String resName, String userCode, String userName,
                    String ip, String kpCodes, String tbcCodes, String gradeCodes, String subejctCodes,
                    String sectionCodes, String uploader, String makerOrgCode, String makerOrgName, String makerCode,
                    Integer flagAllowDownload, String coverPath, String tags) throws Exception;
}
