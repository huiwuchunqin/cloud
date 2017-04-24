package com.baizhitong.resource.model.vo.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.baizhitong.resource.model.report.PlatformLessonCoverageChapter;
import com.baizhitong.resource.model.report.PlatformResCoverageChapter;
import com.baizhitong.resource.model.report.PlatformResCoverageKp;
import com.baizhitong.resource.model.share.ShareKnowledgePoint;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.utils.DataUtils;

public class NodeVo {
    private String  level;
    private String  name;
    private String  code;
    private String  pcode;
    private Integer notChapter;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Integer getNotChapter() {
        return notChapter;
    }

    public void setNotChapter(Integer notChapter) {
        this.notChapter = notChapter;
    }

    // 把教材章节转换为表格vo
    public static NodeVo fromShareTextbookChapter(ShareTextbookChapter chapter) {
        NodeVo vo = new NodeVo();
        DataUtils.copySimpleObject(chapter, vo);
        vo.setNotChapter(0);
        return vo;
    }

    // 把知识点转换为表格vo
    public static NodeVo frommShareKnowledgePointChapter(ShareKnowledgePoint knowledgePoint) {
        NodeVo vo = new NodeVo();
        DataUtils.copySimpleObject(knowledgePoint, vo);
        vo.setNotChapter(0);
        return vo;
    }

    // 章节列表转表格列表vo
    public static List<NodeVo> fromShareTextbookChapterList(List<ShareTextbookChapter> chapterList) {
        if (chapterList == null || chapterList.size() <= 0)
            return null;
        List<NodeVo> nodeVoList = new ArrayList<NodeVo>();
        for (ShareTextbookChapter chapter : chapterList) {
            nodeVoList.add(fromShareTextbookChapter(chapter));
        }
        return nodeVoList;
    }

    // 知识点列表转表格列表vo
    public static List<NodeVo> fromShareKnowledgePointList(List<ShareKnowledgePoint> knowledgePointList) {
        if (knowledgePointList == null || knowledgePointList.size() <= 0)
            return null;
        List<NodeVo> nodeVoList = new ArrayList<NodeVo>();
        for (ShareKnowledgePoint knowledgePoint : knowledgePointList) {
            nodeVoList.add(frommShareKnowledgePointChapter(knowledgePoint));
        }
        return nodeVoList;
    }

    // 章节 视频 文档 测验 题目 合计
    public static List<NodeVo> fromChapterCoverage(PlatformResCoverageChapter chapter) {
        if (chapter == null)
            return null;
        List<NodeVo> voList = new ArrayList<NodeVo>();
        // 视频节点
        NodeVo videoVo = new NodeVo();
        videoVo.setPcode(chapter.getChapterCode());
        String radomCode = String.valueOf(RandomUtils.nextInt(100000));
        String code = chapter.getChapterCode() + radomCode;
        videoVo.setCode(code);
        videoVo.setLevel("video");
        videoVo.setName(chapter.getResVideoNum().toString());
        videoVo.setNotChapter(1);
        voList.add(videoVo);
        // 文档节点
        NodeVo docVo = new NodeVo();
        docVo.setPcode(code);
        code = code + "01";
        docVo.setCode(code);
        docVo.setLevel("doc");
        docVo.setName(chapter.getResDocNum().toString());
        docVo.setNotChapter(1);
        voList.add(docVo);
        // 测验节点
        NodeVo testVo = new NodeVo();
        testVo.setPcode(code);
        code = code + "01";
        testVo.setCode(code);
        testVo.setLevel("test");
        testVo.setName(chapter.getResTestNum().toString());
        testVo.setNotChapter(1);
        voList.add(testVo);
        // 题目节点
        NodeVo questionVo = new NodeVo();
        questionVo.setPcode(code);
        code = code + "01";
        questionVo.setCode(code);
        questionVo.setLevel("question");
        questionVo.setName(chapter.getResQuestionNum().toString());
        questionVo.setNotChapter(1);
        voList.add(questionVo);
        // 资源总数节点
        NodeVo totalVo = new NodeVo();
        totalVo.setPcode(code);
        code = code + "01";
        totalVo.setCode(code);
        totalVo.setLevel("total");
        totalVo.setName(chapter.getResNum().toString());
        totalVo.setNotChapter(1);
        voList.add(totalVo);
        return voList;
    }

    // 知识点 视频 文档 测验 题目 合计
    public static List<NodeVo> fromKpCoverage(PlatformResCoverageKp platformResCoverageKp) {
        if (platformResCoverageKp == null)
            return null;
        List<NodeVo> voList = new ArrayList<NodeVo>();
        // 视频节点
        NodeVo videoVo = new NodeVo();
        videoVo.setPcode(platformResCoverageKp.getKpCode());
        String radomCode = String.valueOf(RandomUtils.nextInt(100000));
        String code = platformResCoverageKp.getKpCode() + radomCode;
        videoVo.setCode(code);
        videoVo.setLevel("video");
        videoVo.setName(platformResCoverageKp.getResVideoNum().toString());
        videoVo.setNotChapter(1);
        voList.add(videoVo);
        // 文档节点
        NodeVo docVo = new NodeVo();
        docVo.setPcode(code);
        code = code + "01";
        docVo.setCode(code);
        docVo.setLevel("doc");
        docVo.setName(platformResCoverageKp.getResDocNum().toString());
        docVo.setNotChapter(1);
        voList.add(docVo);
        /*
         * // 测验节点 NodeVo testVo = new NodeVo(); testVo.setPcode(code); code = code + "01";
         * testVo.setCode(code); testVo.setLevel("test");
         * testVo.setName(platformResCoverageKp.getResTestNum().toString());
         * testVo.setNotChapter(1); voList.add(testVo);
         */
        // 题目节点
        NodeVo questionVo = new NodeVo();
        questionVo.setPcode(code);
        code = code + "01";
        questionVo.setCode(code);
        questionVo.setLevel("question");
        questionVo.setName(platformResCoverageKp.getResQuestionNum().toString());
        questionVo.setNotChapter(1);
        voList.add(questionVo);
        // 资源总数节点
        NodeVo totalVo = new NodeVo();
        totalVo.setPcode(code);
        code = code + "01";
        totalVo.setCode(code);
        totalVo.setLevel("total");
        totalVo.setName(platformResCoverageKp.getResNum().toString());
        totalVo.setNotChapter(1);
        voList.add(totalVo);
        return voList;
    }

    /**
     * 课程章节覆盖率 ()<br>
     * 
     * @param coverageChapter 课程章节覆盖率
     * @return list
     */
    public static List<NodeVo> fromLessonCoverageChapter(PlatformLessonCoverageChapter coverageChapter) {
        if (coverageChapter == null)
            return null;
        List<NodeVo> voList = new ArrayList<NodeVo>();
        // 课程数
        NodeVo lessonNumVo = new NodeVo();
        lessonNumVo.setPcode(coverageChapter.getChapterCode());
        String radomCode = String.valueOf(RandomUtils.nextInt(100000));
        String code = coverageChapter.getChapterCode() + radomCode;
        lessonNumVo.setCode(code);
        lessonNumVo.setLevel("lessonNum");
        lessonNumVo.setName(coverageChapter.getLessonNum() + "");
        lessonNumVo.setNotChapter(1);
        voList.add(lessonNumVo);
        // 翻转课堂模式
        NodeVo filpLessonVo = new NodeVo();
        filpLessonVo.setPcode(code);
        code = code + "01";
        filpLessonVo.setCode(code);
        filpLessonVo.setLevel("filpLesson");
        filpLessonVo.setName(coverageChapter.getFlipLessonNum() + "");
        filpLessonVo.setNotChapter(1);
        voList.add(filpLessonVo);
        // 自主创建模式
        NodeVo autonomousLessonVo = new NodeVo();
        autonomousLessonVo.setPcode(code);
        code = code + "01";
        autonomousLessonVo.setCode(code);
        autonomousLessonVo.setLevel("autonomousLesson");
        autonomousLessonVo.setName(coverageChapter.getAutonomousLessonNum() + "");
        autonomousLessonVo.setNotChapter(1);
        voList.add(autonomousLessonVo);
        return voList;
    }

}
