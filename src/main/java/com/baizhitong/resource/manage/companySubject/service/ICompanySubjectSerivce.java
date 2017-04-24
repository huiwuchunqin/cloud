package com.baizhitong.resource.manage.companySubject.service;

import java.util.List;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;

public interface ICompanySubjectSerivce {
    /**
     * 保存机构学科 ()<br>
     * 
     * @param subjectCode 学科编码
     * @return
     */
    ResultCodeVo saveCompanySubject(String[] subjectCodes, String orgCode);

    /**
     * 查询机构学科 ()<br>
     * 
     * @param orgCode 机构编码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    Page getCompanySubject(String orgCode, String sectionCode, Integer pageSize, Integer pageNo);

    /**
     * 删除机构学科 ()<br>
     * 
     * @param subjectCodes
     * @return
     */
    ResultCodeVo delCompanySubject(String[] subjectCodes);

    /**
     * 查询机构学科 ()<br>
     * 
     * @return
     */
    List<ShareCodeSubjectVo> getCompanySubjectList(String sectionCode);

    /**
     * 查询机构没有选择的学科 ()<br>
     * 
     * @return
     */
    List<ShareCodeSubjectVo> getCompanyNotSelectSubject();

}
