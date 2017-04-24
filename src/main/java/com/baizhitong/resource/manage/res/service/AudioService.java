package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.vo.res.ResVo;

public interface AudioService {

    /**
     * 查询音频资源
     * ()<br>
     * @param id
     * @return
     */
    ResVo getById(Integer id);

    /**
      * 查询音频信息
      * ()<br>
      * @param param
      * @param page
      * @param rows
      */
    Page getPage(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 更新音频资源
     * ()<br>
     * @param resId
     * @param resDesc
     * @param resName
     * @param resTypeL2
     * @param userCode
     * @param userName
     * @param ip
     * @param kpCodes
     * @param tbcCodes
     * @param tbvCodes
     * @param gradeCodes
     * @param subejctCodes
     * @param sectionCodes
     * @param uploader
     * @param makerOrgCode
     * @param makerOrgName
     * @param makerCode
     * @param flagAllowDownload
     * @param coverPath
     * @param tags
     * @return
     */
    ResultCodeVo update(Integer resId, String resDesc, String resName, String resTypeL2, String userCode,
                    String userName, String ip, String kpCodes, String tbcCodes, String tbvCodes, String gradeCodes,
                    String subejctCodes, String sectionCodes, String uploader, String makerOrgCode, String makerOrgName,
                    String makerCode, Integer flagAllowDownload, String coverPath, String tags);

}