package com.baizhitong.resource.manage.report.service;

import java.util.List;

import com.baizhitong.resource.model.vo.report.NodeVo;

public interface PlatformResCoverageKpService {
    /**
     * 查询章节覆盖率 ()<br>
     * 
     * @param code
     * @return
     */
    public List<NodeVo> getChapterCoverageList(String code, String kpsCode, Integer baseDate);
}
