package com.baizhitong.resource.manage.courseReport.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.report.PlatformLessonCoverageChapterDao;
import com.baizhitong.resource.dao.share.ShareTextbookChapterDao;
import com.baizhitong.resource.manage.courseReport.service.CourseCoverageChapterService;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.report.ChapterExportVo;
import com.baizhitong.resource.model.vo.report.NodeVo;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 课程章节详情导出 CourseChapterDetailReportAction TODO
 * 
 * @author creator gaow 2017年1月5日 下午2:52:03
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping(value = "/manage/courseChapterDetailReport")
@Controller
public class CourseChapterDetailReportAction extends BaseAction {
    /**
     * 课程章节覆盖率接口
     */
    @Autowired
    CourseCoverageChapterService                        courseCoverageChapterService;
    private @Autowired PlatformLessonCoverageChapterDao platformLessonCoverageChapterDao;
    private @Autowired ShareTextbookChapterDao          shareTextbookChapterDao;
    /** 教材章节 DAO */
    private @Autowired ShareTextbookChapterDao          textbookChapterDao;

    /**
     * 查询课程章节覆盖率列表 ()<br>
     * 
     * @param chapterCode 章节编码
     * @param chapterPcode 章节父编码
     * @param tbCode 教材编码
     * @return volist
     */
    @RequestMapping(value = "/detailList.html")
    @ResponseBody
    public List<NodeVo> getCourseChapterDetailList(String chapterCode, String chapterPcode, String tbCode) {
        return courseCoverageChapterService.getCoverageList(chapterCode, chapterPcode, tbCode);
    }

    /**
     * 
     * 导出 ()<br>
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportchapter.html")
    @ResponseBody
    public ResultCodeVo exportChapterReport(String tbCode, String chapterCode, HttpServletRequest request,
                    HttpServletResponse response, String fileName) throws Exception {
        // 获取所有章节信息
        List<ShareTextbookChapter> all = this.getList(tbCode);
        List<ChapterExportVo> chapterExportVos = new ArrayList<ChapterExportVo>();
        Map<String, String> map = new LinkedHashMap<String, String>();
        String[] kps = { "一级节点", "二级节点", "三级节点", "四级节点", "五级节点" };
        String[] kps1 = { "node1", "node2", "node3", "node4", "node5" };
        int nodeCount = 0;// 一共有多少节点
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getLevel() > nodeCount) {
                nodeCount = all.get(i).getLevel();
            }
        }
        for (ShareTextbookChapter shareTextbookChapterVo : all) {
            Map<String, Object> childCount = shareTextbookChapterDao.getChildCount(shareTextbookChapterVo.getCode());
            int count = (Integer) childCount.get("count");
            if (count == 0) {
                if (shareTextbookChapterVo.getLevel() == shareTextbookChapterVo.getLevel().intValue()) {
                    ChapterExportVo chapterExportVo = new ChapterExportVo();
                    if (1 == shareTextbookChapterVo.getLevel()) {
                        chapterExportVo.setNode1(shareTextbookChapterVo.getName());
                        chapterExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    if (2 == shareTextbookChapterVo.getLevel()) {
                        chapterExportVo.setNode2(shareTextbookChapterVo.getName());
                        chapterExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    if (3 == shareTextbookChapterVo.getLevel()) {
                        chapterExportVo.setNode3(shareTextbookChapterVo.getName());
                        chapterExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    if (4 == shareTextbookChapterVo.getLevel()) {
                        chapterExportVo.setNode4(shareTextbookChapterVo.getName());
                        chapterExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    if (5 == shareTextbookChapterVo.getLevel()) {
                        chapterExportVo.setNode5(shareTextbookChapterVo.getName());
                        chapterExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    // 根据章节编码查询统计信息
                    Map<String, Object> report = platformLessonCoverageChapterDao
                                    .getReportInfo(shareTextbookChapterVo.getCode());
                    // 写入统计信息
                    chapterExportVo.setCode(shareTextbookChapterVo.getCode());
                    chapterExportVo.setLessonCount((Integer) report.get("lessonNum"));
                    chapterExportVo.setFlipLessonCount((Integer) report.get("flipLessonNum"));
                    chapterExportVo.setAutonomousCount((Integer) report.get("autonomousLessonNum"));
                    this.putParentAttr(chapterExportVo, shareTextbookChapterVo);
                    chapterExportVos.add(chapterExportVo);
                }
            }
        }
        Collections.sort(chapterExportVos, new Comparator<ChapterExportVo>() {
            @Override
            public int compare(ChapterExportVo arg0, ChapterExportVo arg1) {
                int result = arg0.getCode().compareTo(arg1.getCode());
                if (result == 0) {
                    return arg0.getDispOrder().compareTo(arg1.getDispOrder());
                } else {
                    return result;
                }
            }
        });
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (ChapterExportVo chapterExportVo : chapterExportVos) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("node1", chapterExportVo.getNode1());
            data.put("node2", chapterExportVo.getNode2());
            data.put("node3", chapterExportVo.getNode3());
            data.put("node4", chapterExportVo.getNode4());
            data.put("node5", chapterExportVo.getNode5());
            data.put("lessonCount", chapterExportVo.getLessonCount());
            data.put("flipLessonCount", chapterExportVo.getFlipLessonCount());
            data.put("autonomousCount", chapterExportVo.getAutonomousCount());
            list.add(data);
        }
        // 知识点节点
        for (int i = 0; i < nodeCount; i++) {
            map.put(kps[i], kps1[i]);
        }
        map.put("课程数", "lessonCount");
        map.put("翻转课堂模式数", "flipLessonCount");
        map.put("自主创建模式数", "autonomousCount");

        // 知识点节点
        for (int i = 0; i < nodeCount; i++) {
            map.put(kps[i], kps1[i]);
        }
        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        response.setContentType("application/x-msdownload ");

        HSSFWorkbook excel = ExcelUtils.getWb(list, map, null);
        try {
            excel.write(response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return null;
        }
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

    /**
     *
     * (放父节点属性)<br>
     *
     * @param kpExportVo 要导出的知识点信息
     * @param shareKnowledgePoint 当前节点信息
     */
    public void putParentAttr(ChapterExportVo chapterExportVo, ShareTextbookChapter shareTextbookChapterVo) {
        ShareTextbookChapter parent = shareTextbookChapterDao.getByCode(shareTextbookChapterVo.getPcode());
        if (parent == null) {
            return;// 退出递归
        }
        if (parent.getLevel() == 1) {
            chapterExportVo.setNode1(parent.getName());
        }
        if (parent.getLevel() == 2) {
            chapterExportVo.setNode2(parent.getName());
        }
        if (parent.getLevel() == 3) {
            chapterExportVo.setNode3(parent.getName());
        }
        if (parent.getLevel() == 4) {
            chapterExportVo.setNode4(parent.getName());
        }
        putParentAttr(chapterExportVo, parent);
    }
}
