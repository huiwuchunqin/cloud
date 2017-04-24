package com.baizhitong.resource.manage.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.dao.report.PlatformResCoverageChapterDao;
import com.baizhitong.resource.dao.share.ShareTextbookChapterDao;
import com.baizhitong.resource.manage.report.service.PlatformResCoverageChapterService;
import com.baizhitong.resource.model.report.PlatformResCoverageChapter;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.report.NodeVo;
import com.baizhitong.utils.StringUtils;

/**
 * 教材章节覆盖率接口 PlatformResCoverageChapterServiceImpl TODO
 * 
 * @author creator BZT 2016年7月8日 上午11:10:53
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PlatformResCoverageChapterServiceImpl implements PlatformResCoverageChapterService {
    /** 教材章节 DAO */
    private @Autowired ShareTextbookChapterDao       textbookChapterDao;
    private @Autowired PlatformResCoverageChapterDao platformResCoverageChapterDao;
    private Map<String, ShareTextbookChapter>        chapterMap = new HashMap<String, ShareTextbookChapter>();

    /**
     * 查询章节覆盖率 ()<br>
     * 
     * @param code
     * @return
     */
    public List<NodeVo> getChapterCoverageList(String code, String tbCode, Integer baseDate) {
        List<NodeVo> voList = new ArrayList<NodeVo>();

        List<ShareTextbookChapter> chapterList = null;
        try {
            chapterList = textbookChapterDao.getChapterList(tbCode);
            if (chapterList != null && chapterList.size() > 0) {
                for (ShareTextbookChapter chapter : chapterList) {
                    chapterMap.put(chapter.getCode(), chapter);
                }
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (StringUtils.isEmpty(tbCode)) {
            String pcode = "";
            ShareTextbookChapter shareTextbook = chapterMap.get(code);
            if (shareTextbook != null) {
                pcode = shareTextbook.getPcode();
            }
            NodeVo vo = NodeVo.fromShareTextbookChapter(shareTextbook);
            // 插入当前节点
            voList.add(vo);
            // 插入父节点
            putParent(voList, pcode);
            // 插入子节点
            addChild(voList, code, chapterList, baseDate);
            return voList;
        } else {// 查询所有的节点
            try {
                List<ShareTextbookChapter> top = textbookChapterDao.getTopChapterList(tbCode, "");
                if (top != null && top.size() > 0) {
                    for (ShareTextbookChapter chapter : top) {
                        NodeVo vo = NodeVo.fromShareTextbookChapter(chapter);
                        // 插入当前节点
                        voList.add(vo);
                        // 插入子节点
                        addChild(voList, chapter.getCode(), chapterList, baseDate);
                    }
                }
                return voList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }

    // 塞父节点
    public List<NodeVo> putParent(List<NodeVo> voList, String pcode) {
        ShareTextbookChapter shareTextbook = chapterMap.get(pcode);
        if (shareTextbook == null) {
            return voList;
        }
        NodeVo vo = NodeVo.fromShareTextbookChapter(shareTextbook);
        voList.add(vo);
        if (StringUtils.isNotEmpty(vo.getPcode())) {
            return putParent(voList, vo.getPcode());
        } else {
            return voList;
        }

    }

    /**
     * 塞子节点 ()<br>
     * 
     * @param voList 列表
     * @param code 父节点
     * @param all 所有的章节，如果传入此参数则从里面找孩子不再查询数据库
     */
    public void addChild(List<NodeVo> voList, String code, List<ShareTextbookChapter> all, Integer baseDate) {
        List<ShareTextbookChapter> chapterList = new ArrayList<ShareTextbookChapter>();
        if (all != null && all.size() > 0) {
            chapterList = getChildren(all, code);
        } else {
            chapterList = textbookChapterDao.getListInfo(code);
        }
        List<NodeVo> nodeVoList = NodeVo.fromShareTextbookChapterList(chapterList);
        if (nodeVoList != null && nodeVoList.size() > 0) {
            voList.addAll(nodeVoList);
        }
        if (nodeVoList == null || nodeVoList.size() <= 0) {
            // 如果没有子节点了,则去查资源章节情况
            PlatformResCoverageChapter coverageChapter = platformResCoverageChapterDao.getChapterCoverage(code,
                            baseDate);
            if (coverageChapter != null) {
                voList.addAll(NodeVo.fromChapterCoverage(coverageChapter));
            }
        } else {
            for (NodeVo vo : nodeVoList) {
                addChild(voList, vo.getCode(), all, baseDate);
            }
        }
    }

    /**
     * 找孩子 ()<br>
     * 
     * @param all 所有的列表
     * @param pcode 父节点
     * @return
     */
    public List<ShareTextbookChapter> getChildren(List<ShareTextbookChapter> all, String pcode) {
        List<ShareTextbookChapter> children = new ArrayList<ShareTextbookChapter>();
        for (ShareTextbookChapter chapter : all) {
            if (pcode.equals(chapter.getPcode())) {
                children.add(chapter);
            }
        }
        return children;
    }

}
