package com.baizhitong.resource.dao.report;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.report.PlatformLessonCoverageChapter;

/**
 * 课程章节覆盖率 PlatformLessonCoverageChapterDao TODO
 * 
 * @author creator gaow 2017年1月5日 上午11:26:45
 * @author updater
 *
 * @version 1.0.0
 */
public interface PlatformLessonCoverageChapterDao {
    /**
     * 查询课程教材章节覆盖率 ()<br>
     * 
     * @param chapterPCode 章节父编码
     * @param chapterCode 章节编码
     * @param tbCode 教材编码
     * @return list
     */
    List<PlatformLessonCoverageChapter> getList(String chapterPCode, String chapterCode, String tbCode);

    Map<String, Object> getReportInfo(String code);
}
