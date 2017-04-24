package com.baizhitong.resource.dao.lessonreport;

import java.util.Map;

import com.baizhitong.common.Page;

public interface PlatformLessonCoverageTbDao {
    /**
     * 查询课程资源覆盖率 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getPage(Map<String, Object> param, Integer page, Integer rows);
}
