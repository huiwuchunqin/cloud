package com.baizhitong.resource.dao.report;

import java.util.Map;

import com.baizhitong.resource.model.report.PlatformResCoverageChapter;

public interface PlatformResCoverageChapterDao {
    PlatformResCoverageChapter getChapterCoverage(String chapterCode, Integer baseDate);

    /**
     * 
     * (根据章节编码获取统计信息)<br>
     * 
     * @param code 章节编码
     * @param baseDate 基准日
     * @return 统计信息
     */
    Map<String, Object> getReportInfo(String code, String baseDate);
}
