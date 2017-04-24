package com.baizhitong.resource.manage.report.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.report.PlatformResCoverageKpsDao;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.dao.share.ShareKnowledgePointDao;
import com.baizhitong.resource.manage.report.service.PlatformResCoverageKpsService;
import com.baizhitong.resource.manage.report.service.ReportOrgResDailyService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.share.ShareKnowledgePoint;
import com.baizhitong.resource.model.vo.report.KpExportVo;

@RequestMapping("/manage/coverageKps")
@Controller
public class PlatformResCoverageKpsAction extends BaseAction {
    private @Autowired SectionService                sectionService;
    private @Autowired PlatformResCoverageKpsService coverageKpsService;
    @Autowired
    ReportOrgResDailyService                         reportOrgResDailyService;
    /** 知识点 DAO */
    private @Autowired ShareKnowledgePointDao        shareKnowledgePointDao;
    /** 知识点覆盖率dao */
    private @Autowired PlatformResCoverageKpsDao     platformResCoverageKpsDao;
    private @Autowired ShareCodeSectionDao           shareCodeSectionDao;
    private @Autowired ShareCodeSubjectDao           shareCodeSubjectDao;

    /**
     * 跳转到教材资源覆盖率 ()<br>
     * 
     * @param sectionCode
     * @param subjectCode
     * @return
     */
    @RequestMapping("/toCoverageKps.html")
    public String toKp(ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/manage/report/reportResCoverageKps.html";

    }

    /**
     * 查询分页信息 ()<br>
     * 
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param kpsCode
     * @return
     */
    @RequestMapping("/page.html")
    @ResponseBody
    public Page pageList(Integer page, Integer rows, String termCode, String sectionCode, String subjectCode,
                    String gradeCode, String kpsCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("kpsCode", kpsCode);
        param.put("termCode", termCode);
        return coverageKpsService.getPage(param, page, rows);
    }

    /**
     * 查询分页信息 ()<br>
     * 
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param kpsCode
     * @return
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public List<Map<String, Object>> getList(String termCode, String sectionCode, String subjectCode, String gradeCode,
                    String kpsCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("kpsCode", kpsCode);
        param.put("termCode", termCode);
        return coverageKpsService.getList(param);
    }

    /**
     * 根据知识体系编码查询 ()<br>
     * 
     * @param kpsCode
     * @return
     */
    @RequestMapping("/getByKpsCode.html")
    @ResponseBody
    public Map<String, Object> getByKpsCode(String kpsCode) {
        return coverageKpsService.getByKpsCode(kpsCode);
    }

    /**
     * 
     * 导出 ()<br>
     * 
     * @return
     */
    @RequestMapping("/export.html")
    @ResponseBody
    public ResultCodeVo exportReport(String termCode, String sectionCode, String subjectCode, String gradeCode,
                    String kpsCode, HttpServletRequest request, HttpServletResponse response, String fileName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("kpsCode", kpsCode);
        param.put("termCode", termCode);
        List<Map<String, Object>> list = coverageKpsService.getList(param);
        for (Map<String, Object> info : list) {
            BigDecimal resVideo = new BigDecimal(info.get("resVideoCoverage").toString());
            BigDecimal temp = new BigDecimal(Float.toString(100.00f));
            String resVideoCoverage = resVideo.multiply(temp).setScale(2, BigDecimal.ROUND_DOWN) + "" + "%";
            info.put("resVideoCoverage", resVideoCoverage);
            BigDecimal resDoc = new BigDecimal(info.get("resDocCoverage").toString());
            String resDocCoverage = resDoc.multiply(temp).setScale(2, BigDecimal.ROUND_DOWN) + "" + "%";
            info.put("resDocCoverage", resDocCoverage);
            // BigDecimal resTest = new
            // BigDecimal(info.get("resTestCoverage").toString());
            // String resTestCoverage =
            // resTest.multiply(temp).setScale(2,BigDecimal.ROUND_DOWN)+""+"%";
            // info.put("resTestCoverage", resTestCoverage);
            BigDecimal resQuestion = new BigDecimal(info.get("resQuestionCoverage").toString());
            String resQuestionCoverage = resQuestion.multiply(temp).setScale(2, BigDecimal.ROUND_DOWN) + "" + "%";
            info.put("resQuestionCoverage", resQuestionCoverage);
        }
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("学段", "sectionName");
        map.put("学科", "subjectName");
        map.put("知识体系", "kpsName");
        map.put("知识点节点数", "kpNum");
        map.put("视频资源数", "resVideoNum");
        map.put("文档资源数", "resDocNum");
        // map.put("测验资源数", "resTestNum");
        map.put("题目资源数", "resQuestionNum");
        map.put("视频覆盖率", "resVideoCoverage");
        map.put("文档覆盖率", "resDocCoverage");
        // map.put("测验覆盖率", "resTestCoverage");
        map.put("题目覆盖率", "resQuestionCoverage");
        List<String> countColumn = new ArrayList<String>();
        countColumn.add("kpNum");
        countColumn.add("resVideoNum");
        countColumn.add("resDocNum");
        countColumn.add("resQuestionNum");
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
     * 导出 ()<br>
     * 
     * @return
     */
    @RequestMapping("/exportKps.html")
    @ResponseBody
    public ResultCodeVo exportKpsReport(String sectionCode, String subjectCode, String kpsCode, String baseDate,String fileName,
                    HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("kpsCode", kpsCode);
        // param.put("baseDate", baseDate);
        // 获取所有知识点信息
        List<ShareKnowledgePoint> all = shareKnowledgePointDao.getKnowledgeList(kpsCode);
        // List<Map<String, Object>> list = coverageKpsService.getList(param);
        Map<String, String> map = new LinkedHashMap<String, String>();
        String[] kps = { "一级节点", "二级节点", "三级节点", "四级节点", "五级节点" };
        String[] kps1 = { "node1", "node2", "node3", "node4", "node5" };
        // 学段编码获取段名称
        String sectionName = shareCodeSectionDao.getSection(sectionCode).getName();
        // 根据学科代码获取学科名称
        String subjectName = shareCodeSubjectDao.getSubject(subjectCode).getName();
        List<KpExportVo> kpExportVos = new ArrayList<KpExportVo>();
        int nodeCount = 0;// 一共有多少节点
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getLevel() > nodeCount) {
                nodeCount = all.get(i).getLevel();
            }
        }
        for (ShareKnowledgePoint shareKnowledgePoint : all) {
            Map<String, Object> childCount = shareKnowledgePointDao.getChildCount(shareKnowledgePoint.getCode());
            int count = (Integer) childCount.get("count");
            if (count == 0) {
                if (shareKnowledgePoint.getLevel() == shareKnowledgePoint.getLevel().intValue()) {
                    KpExportVo kpExportVo = new KpExportVo();
                    if (1 == shareKnowledgePoint.getLevel()) {
                        kpExportVo.setNode1(shareKnowledgePoint.getName());
                    }
                    if (2 == shareKnowledgePoint.getLevel()) {
                        kpExportVo.setNode2(shareKnowledgePoint.getName());
                    }
                    if (3 == shareKnowledgePoint.getLevel()) {
                        kpExportVo.setNode3(shareKnowledgePoint.getName());
                    }
                    if (4 == shareKnowledgePoint.getLevel()) {
                        kpExportVo.setNode4(shareKnowledgePoint.getName());
                    }
                    if (5 == shareKnowledgePoint.getLevel()) {
                        kpExportVo.setNode5(shareKnowledgePoint.getName());
                    }
                    // 根据知识点编码查询统计信息
                    Map<String, Object> report = platformResCoverageKpsDao.getReportInfo(shareKnowledgePoint.getCode(),
                                    baseDate);
                    kpExportVo.setSectionName(sectionName);
                    kpExportVo.setSubjectName(subjectName);
                    // 写入统计信息
                    kpExportVo.setDocCount((Integer) report.get("docCount"));
                    kpExportVo.setMediaCount((Integer) report.get("mediaCount"));
                    kpExportVo.setTotalCount((Integer) report.get("totalCount"));
                    kpExportVo.setQuestionCount((Integer) report.get("questionCount"));
                    this.putParentAttr(kpExportVo, shareKnowledgePoint);
                    kpExportVos.add(kpExportVo);
                }
            }
        }
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
            data.put("resVideoNum", kpExportVo.getMediaCount());
            data.put("resDocNum", kpExportVo.getDocCount());
            data.put("resQuestionNum", kpExportVo.getQuestionCount());
            data.put("resTotalNum", kpExportVo.getTotalCount());
            list.add(data);
        }

        map.put("学段", "sectionName");
        map.put("学科", "subjectName");
        // 知识点节点
        for (int i = 0; i < nodeCount; i++) {
            map.put(kps[i], kps1[i]);
        }
        map.put("视频数", "resVideoNum");
        map.put("文档数", "resDocNum");
        map.put("题目数", "resQuestionNum");
        map.put("总数", "resTotalNum");
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
     * (放父节点属性)<br>
     *
     * @param kpExportVo 要导出的知识点信息
     * @param shareKnowledgePoint 当前节点信息
     */
    public void putParentAttr(KpExportVo kpExportVo, ShareKnowledgePoint shareKnowledgePoint) {
        ShareKnowledgePoint parent = shareKnowledgePointDao.getByCode(shareKnowledgePoint.getPcode());
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
