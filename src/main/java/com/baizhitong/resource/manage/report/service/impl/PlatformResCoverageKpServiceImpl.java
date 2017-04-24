package com.baizhitong.resource.manage.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.dao.report.PlatformResCoverageKpDao;
import com.baizhitong.resource.dao.share.ShareKnowledgePointDao;
import com.baizhitong.resource.manage.report.service.PlatformResCoverageKpService;
import com.baizhitong.resource.model.report.PlatformResCoverageKp;
import com.baizhitong.resource.model.share.ShareKnowledgePoint;
import com.baizhitong.resource.model.vo.report.NodeVo;
import com.baizhitong.utils.StringUtils;

/**
 * 知识点覆盖率接口 PlatformResCoverageKpServiceImpl TODO
 * 
 * @author creator BZT 2016年7月8日 上午11:11:52
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PlatformResCoverageKpServiceImpl implements PlatformResCoverageKpService {
    private @Autowired PlatformResCoverageKpDao coverageKpDao;
    /** 知识点 DAO */
    private @Autowired ShareKnowledgePointDao   shareKnowledgePointDao;
    private Map<String, ShareKnowledgePoint>    kpMap = new HashMap<String, ShareKnowledgePoint>();

    /**
     * 查询知识点覆盖率 ()<br>
     * 
     * @param code 知识点编码
     * @param kpsCode 知识体系编码
     * @return
     */
    public List<NodeVo> getChapterCoverageList(String code, String kpsCode, Integer baseDate) {
        List<NodeVo> voList = new ArrayList<NodeVo>();
        List<ShareKnowledgePoint> kpList = null;
        try {
            kpList = shareKnowledgePointDao.getKnowledgeList(kpsCode);
            if (kpList != null && kpList.size() > 0) {
                for (ShareKnowledgePoint kpPoint : kpList) {
                    kpMap.put(kpPoint.getCode(), kpPoint);
                }
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (StringUtils.isEmpty(kpsCode)) {
            String pcode = "";
            ShareKnowledgePoint shareKnowledgePoint = kpMap.get(code);
            if (shareKnowledgePoint != null) {
                pcode = shareKnowledgePoint.getPcode();
            }
            NodeVo vo = NodeVo.frommShareKnowledgePointChapter(shareKnowledgePoint);
            // 插入当前节点
            voList.add(vo);
            // 插入父节点
            putParent(voList, pcode);
            // 插入子节点
            addChild(voList, code, kpList, baseDate);
            return voList;
        } else {// 查询所有的节点
            try {
                List<ShareKnowledgePoint> top = new ArrayList<ShareKnowledgePoint>();
                if (kpList != null && kpList.size() > 0) {
                    for (ShareKnowledgePoint konwledgePoint : kpList) {
                        if (StringUtils.isEmpty(konwledgePoint.getPcode())) {
                            top.add(konwledgePoint);
                        }
                    }
                }
                if (top != null && top.size() > 0) {
                    for (ShareKnowledgePoint knowledge : top) {
                        NodeVo vo = NodeVo.frommShareKnowledgePointChapter(knowledge);
                        // 插入当前节点
                        voList.add(vo);
                        // 插入子节点
                        addChild(voList, knowledge.getCode(), kpList, baseDate);
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
        ShareKnowledgePoint knowledge = kpMap.get(pcode);
        if (knowledge == null) {
            return voList;
        }
        NodeVo vo = NodeVo.frommShareKnowledgePointChapter(knowledge);
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
    public void addChild(List<NodeVo> voList, String code, List<ShareKnowledgePoint> all, Integer baseDate) {
        List<ShareKnowledgePoint> knowledgeList = new ArrayList<ShareKnowledgePoint>();
        if (all != null && all.size() > 0) {
            knowledgeList = getChildren(all, code);
        } else {
            knowledgeList = shareKnowledgePointDao.getchildKnowledgeList(code);
        }
        List<NodeVo> nodeVoList = NodeVo.fromShareKnowledgePointList(knowledgeList);
        if (nodeVoList != null && nodeVoList.size() > 0) {
            voList.addAll(nodeVoList);
        }
        if (nodeVoList == null || nodeVoList.size() <= 0) {
            // 如果没有子节点了,则去查资源章节情况
            PlatformResCoverageKp coverageKp = coverageKpDao.getKpCoverage(code, baseDate);
            if (coverageKp != null) {
                voList.addAll(NodeVo.fromKpCoverage(coverageKp));
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
    public List<ShareKnowledgePoint> getChildren(List<ShareKnowledgePoint> all, String pcode) {
        List<ShareKnowledgePoint> childrenList = new ArrayList<ShareKnowledgePoint>();
        for (ShareKnowledgePoint shareKnowledgePoint : all) {
            if (pcode.equals(shareKnowledgePoint.getPcode())) {
                childrenList.add(shareKnowledgePoint);
            }
        }
        return childrenList;
    }
}
