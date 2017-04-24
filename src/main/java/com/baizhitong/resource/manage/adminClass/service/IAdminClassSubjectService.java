package com.baizhitong.resource.manage.adminClass.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;

public interface IAdminClassSubjectService {
    /**
     * 保存行政班级学科 ()<br>
     * 
     * @param adminClassSubject
     * @return
     */
    ResultCodeVo saveAdminClassSubject(ShareAdminClassSubject adminClassSubject);

    /**
     * 查询行政班级学科列表 ()<br>
     * 
     * @param param 过滤条件
     * @param pageNo 页码
     * @param pageSize 记录数
     * @return
     */
    Page getAdminClassSubject(Map<String, Object> param, Integer pageNo, Integer pageSize);

    /**
     * 删除行政班级学科 ()<br>
     * 
     * @param gid
     * @return
     */
    ResultCodeVo delete(String gid);

    /**
     * 行政班级学科 ()<br>
     * 
     * @param orgCode
     * @return
     */
    List<ShareAdminClassSubject> getAdminClassSubject(String orgCode, String admminClassCode);
}
