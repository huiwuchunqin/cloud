package com.baizhitong.resource.manage.courseReport.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.report.NodeVo;

public interface CourseCoverageChapterService {
    /**
     * *课程章节覆盖率列表 ()<br>
     * 
     * @param chapterCode 章节编码
     * @param chapterPCode 章节父节点编码
     * @param tbCode 教材编码
     * @return
     */
    List<NodeVo> getCoverageList(String chapterCode, String chapterPCode, String tbCode);

    /**
     * 
     * 根据教材查询教材章节
     * 
     * @param tbCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapter> getList(String tbCode) throws Exception;

}
