package com.baizhitong.resource.manage.shareTeachingClass.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareTeachingClass;

public interface IShareTeachingClassService {
    /**
     * 查询课程班级列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page list(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 新增或修改课程班级列表 ()<br>
     * 
     * @param shareTeachingClass
     * @return
     */
    ResultCodeVo addOrUpdate(ShareTeachingClass shareTeachingClass, String teacherCode, String teacherName,
                    String termCode, String teachingClassSubjectGid);

    ResultCodeVo importDefault();
}
