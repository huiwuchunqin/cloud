package com.baizhitong.resource.manage.report.action;

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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.report.PlatformResCoverageChapterDao;
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.dao.share.ShareTextbookChapterDao;
import com.baizhitong.resource.manage.report.service.PlatformResCoverageChapterService;
import com.baizhitong.resource.manage.report.service.PlatformResCoverageTbService;
import com.baizhitong.resource.manage.report.service.ReportOrgResDailyService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.textbook.service.TextbookChapterService;
import com.baizhitong.resource.model.share.ShareCodeGrade;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.report.KpExportVo;
import com.baizhitong.resource.model.vo.report.NodeVo;
import com.baizhitong.utils.StringUtils;

@RequestMapping("/manage/coverageChapter")
@Controller
public class PlatfromResCoverageChapterAction extends BaseAction {
    /** 教材章节信息接口 */
    private @Autowired TextbookChapterService            textbookChapterService;
    private @Autowired SectionService                    sectionService;
    /** 章节资源覆盖接口 */
    private @Autowired PlatformResCoverageChapterService coverageChapterService;
    /** 教材资源覆盖接口 */
    private @Autowired PlatformResCoverageTbService      coverageTbService;
    private @Autowired ShareCodeSectionDao               shareCodeSectionDao;
    private @Autowired ShareCodeSubjectDao               shareCodeSubjectDao;
    private @Autowired ShareCodeGradeDao                 shareCodeGradeDao;
    private @Autowired ShareTextbookChapterDao           shareTextbookChapterDao;
    private @Autowired PlatformResCoverageChapterDao     PlatformResCoverageChapterDao;
    @Autowired
    ReportOrgResDailyService                             reportOrgResDailyService;

    /**
     * 跳转到章节资源覆盖率查看页面 ()<br>
     * 
     * @param tbvCode
     * @return
     */
    @RequestMapping("/toCoverageChapter.html")
    public String toCoverageChapter(ModelMap map, Integer id, String chapterName, Integer baseDate) {
        try {
            map.put("tbCoverage", JSON.toJSONString(coverageTbService.getById(id)));
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            map.put("baseDate", baseDate);
            /*
             * map.put("chapterList",
             * JSON.toJSONString(textbookChapterService.getChapterTopNodeTreeList(tbCode,chapterName
             * )));
             */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/manage/report/reportResCoverageChapter.html";
    }

    /**
     * 查询章节覆盖率 ()<br>
     * 
     * @param code
     * @return
     */
    @RequestMapping("/getChapterCoverageList.html")
    @ResponseBody
    public List<NodeVo> getChapterCoverageList(String code, String tbCode, Integer baseDate) {
        return coverageChapterService.getChapterCoverageList(code, tbCode, baseDate);
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
    public ResultCodeVo exportKpsReport(String sectionCode, String subjectCode, String gradeCode, String tbCode,
                    String baseDate, HttpServletRequest request, HttpServletResponse response, String fileName)
                                    throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("baseDate", baseDate);
        // 获取所有章节信息
        List<ShareTextbookChapter> all = textbookChapterService.getList(tbCode);
        // List<Map<String, Object>> list = coverageKpsService.getList(param);
        Map<String, String> map = new LinkedHashMap<String, String>();
        String[] kps = { "一级节点", "二级节点", "三级节点", "四级节点", "五级节点" };
        String[] kps1 = { "node1", "node2", "node3", "node4", "node5" };
        // 学段编码获取段名称
        String sectionName = shareCodeSectionDao.getSection(sectionCode).getName();
        // 根据学科代码获取学科名称
        String subjectName = shareCodeSubjectDao.getSubject(subjectCode).getName();
        // 根据年级代码获取年级名称
        String gradeName = "";
        ShareCodeGrade grade = shareCodeGradeDao.getGrade(gradeCode);
        if (grade != null) {
            gradeName = grade.getName();
        }
        List<KpExportVo> kpExportVos = new ArrayList<KpExportVo>();
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
                    KpExportVo kpExportVo = new KpExportVo();
                    if (1 == shareTextbookChapterVo.getLevel()) {
                        kpExportVo.setNode1(shareTextbookChapterVo.getName());
                        kpExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    if (2 == shareTextbookChapterVo.getLevel()) {
                        kpExportVo.setNode2(shareTextbookChapterVo.getName());
                        kpExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    if (3 == shareTextbookChapterVo.getLevel()) {
                        kpExportVo.setNode3(shareTextbookChapterVo.getName());
                        kpExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    if (4 == shareTextbookChapterVo.getLevel()) {
                        kpExportVo.setNode4(shareTextbookChapterVo.getName());
                        kpExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    if (5 == shareTextbookChapterVo.getLevel()) {
                        kpExportVo.setNode5(shareTextbookChapterVo.getName());
                        kpExportVo.setDispOrder(shareTextbookChapterVo.getDispOrder());
                    }
                    // 根据章节编码查询统计信息
                    Map<String, Object> report = PlatformResCoverageChapterDao
                                    .getReportInfo(shareTextbookChapterVo.getCode(), baseDate);
                    kpExportVo.setCode(shareTextbookChapterVo.getCode());
                    kpExportVo.setSectionName(sectionName);
                    kpExportVo.setSubjectName(subjectName);
                    kpExportVo.setGradeName(gradeName);
                    // 写入统计信息
                    kpExportVo.setDocCount((Integer) report.get("docCount"));
                    kpExportVo.setMediaCount((Integer) report.get("mediaCount"));
                    kpExportVo.setTotalCount((Integer) report.get("totalCount"));
                    kpExportVo.setQuestionCount((Integer) report.get("questionCount"));
                    kpExportVo.setTestCount((Integer) report.get("testCount"));
                    this.putParentAttr(kpExportVo, shareTextbookChapterVo);
                    kpExportVos.add(kpExportVo);
                }
            }
        }
        Collections.sort(kpExportVos, new Comparator<KpExportVo>() {
            @Override
            public int compare(KpExportVo arg0, KpExportVo arg1) {
                int result = arg0.getCode().compareTo(arg1.getCode());
                if (result == 0) {
                    return arg0.getDispOrder().compareTo(arg1.getDispOrder());
                } else {
                    return result;
                }
            }
        });
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (KpExportVo kpExportVo : kpExportVos) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("node1", kpExportVo.getNode1());
            data.put("node2", kpExportVo.getNode2());
            data.put("node3", kpExportVo.getNode3());
            data.put("node4", kpExportVo.getNode4());
            data.put("node5", kpExportVo.getNode5());
            data.put("sectionName", kpExportVo.getSectionName());
            data.put("subjectName", kpExportVo.getSubjectName());
            data.put("gradeName", kpExportVo.getGradeName());
            data.put("resVideoNum", kpExportVo.getMediaCount());
            data.put("resDocNum", kpExportVo.getDocCount());
            data.put("resTestNum", kpExportVo.getTestCount());
            data.put("resQuestionNum", kpExportVo.getQuestionCount());
            data.put("resTotalNum", kpExportVo.getTotalCount());
            list.add(data);
        }

        map.put("学段", "sectionName");
        map.put("学科", "subjectName");
        map.put("年级", "gradeName");
        // 知识点节点
        for (int i = 0; i < nodeCount; i++) {
            map.put(kps[i], kps1[i]);
        }
        map.put("视频数", "resVideoNum");
        map.put("文档数", "resDocNum");
        map.put("测验数", "resTestNum");
        map.put("题目数", "resQuestionNum");
        map.put("总数", "resTotalNum");
        List<String> countColumn = new ArrayList<String>();
        countColumn.add("resVideoNum");
        countColumn.add("resDocNum");
        countColumn.add("resTestNum");
        countColumn.add("resQuestionNum");
        countColumn.add("resTotalNum");
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

        HSSFWorkbook excel = ExcelUtils.getWb(list, map, null, countColumn);
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
     * (放父节点属性)<br>
     *
     * @param kpExportVo 要导出的知识点信息
     * @param shareKnowledgePoint 当前节点信息
     */
    public void putParentAttr(KpExportVo kpExportVo, ShareTextbookChapter shareTextbookChapterVo) {
        ShareTextbookChapter parent = shareTextbookChapterDao.getByCode(shareTextbookChapterVo.getPcode());
        if (parent == null) {
            return;// 退出递归
        }
        if (parent.getLevel() == 1) {
            kpExportVo.setNode1(parent.getName());
        }
        if (parent.getLevel() == 2) {
            kpExportVo.setNode2(parent.getName());
        }
        if (parent.getLevel() == 3) {
            kpExportVo.setNode3(parent.getName());
        }
        if (parent.getLevel() == 4) {
            kpExportVo.setNode4(parent.getName());
        }
        putParentAttr(kpExportVo, parent);
    }
}
