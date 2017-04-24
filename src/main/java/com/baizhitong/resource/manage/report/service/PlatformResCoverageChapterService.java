package com.baizhitong.resource.manage.report.service;

import java.util.List;

import com.baizhitong.resource.model.vo.report.NodeVo;

public interface PlatformResCoverageChapterService {
    public List<NodeVo> getChapterCoverageList(String code, String tbCode, Integer baseDate);
}
