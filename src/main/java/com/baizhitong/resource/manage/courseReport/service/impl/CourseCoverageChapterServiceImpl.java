package com.baizhitong.resource.manage.courseReport.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.dao.report.PlatformLessonCoverageChapterDao;
import com.baizhitong.resource.dao.share.ShareTextbookChapterDao;
import com.baizhitong.resource.manage.courseReport.service.CourseCoverageChapterService;
import com.baizhitong.resource.model.report.PlatformLessonCoverageChapter;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.report.NodeVo;

/**
 * 课程站街覆盖率 CourseCoverageChapterServiceImpl TODO
 * 
 * @author creator gaow 2017年1月5日 下午2:15:43
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class CourseCoverageChapterServiceImpl extends BaseService implements CourseCoverageChapterService {
    /** 教材章节 DAO */
    private @Autowired ShareTextbookChapterDao          textbookChapterDao;
    private @Autowired PlatformLessonCoverageChapterDao platformLessonCoverageChapterDao;
    private Map<String, ShareTextbookChapter>           chapterMap = new HashMap<String, ShareTextbookChapter>();
    private @Autowired ShareTextbookChapterDao          shareTextbookChapterDao;

    /**
     * *课程章节覆盖率列表 ()<br>
     * 
     * @param chapterCode 章节编码
     * @param chapterPCode 章节父节点编码
     * @param tbCode 教材编码
     * @return
     */
    public List<NodeVo> getCoverageList(String chapterCode, String chapterPCode, String tbCode) {
        List<NodeVo> nodeList = new ArrayList<NodeVo>();
        try {
            List<ShareTextbookChapter> chapterList = textbookChapterDao.getChapterList(tbCode);
            Set<String> parentTbCode = new HashSet<String>();
            Set<String> addedCode = new HashSet<String>();
            if (chapterList != null && chapterList.size() > 0) {
                for (ShareTextbookChapter chapter : chapterList) {
                    chapterMap.put(chapter.getCode(), chapter);
                    parentTbCode.add(chapter.getPcode());
                }
            }
            List<PlatformLessonCoverageChapter> lessonCoverageChapterList = platformLessonCoverageChapterDao
                            .getList(chapterPCode, chapterCode, tbCode);
            if (lessonCoverageChapterList != null && lessonCoverageChapterList.size() > 0) {
                for (PlatformLessonCoverageChapter lessonCoverageChapter : lessonCoverageChapterList) {
                    // 直插入最后一层
                    if (!parentTbCode.contains(lessonCoverageChapter.getChapterCode())) {
                        addedCode.add(lessonCoverageChapter.getChapterCode());
                        nodeList.add(NodeVo.fromShareTextbookChapter(
                                        shareTextbookChapterDao.getByCode(lessonCoverageChapter.getChapterCode())));
                        putParent(addedCode, nodeList, lessonCoverageChapter.getChapterPCode());
                        nodeList.addAll(NodeVo.fromLessonCoverageChapter(lessonCoverageChapter));
                    }

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("查询教材章节错误", e);
        }
        return nodeList;
    }

    /**
     * 
     * 根据教材查询教材章节
     * 
     * @param tbCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapter> getList(String tbCode) throws Exception {
        List<ShareTextbookChapter> chapterList = textbookChapterDao.getChapterList(tbCode);
        return chapterList;
    }

    /** 添加父节点 */
    public void putParent(Set<String> addedCode, List<NodeVo> nodeList, String chapterPcode) {
        ShareTextbookChapter parent = chapterMap.get(chapterPcode);
        if (parent != null && !addedCode.contains(parent.getCode())) {
            nodeList.add(NodeVo.fromShareTextbookChapter(parent));
            addedCode.add(parent.getCode());
            putParent(addedCode, nodeList, parent.getPcode());
        }
    }
}
