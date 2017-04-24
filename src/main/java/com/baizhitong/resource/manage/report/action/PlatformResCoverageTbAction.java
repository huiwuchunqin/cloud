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
import com.baizhitong.resource.manage.report.service.PlatformResCoverageTbService;
import com.baizhitong.resource.manage.report.service.ReportOrgResDailyService;
import com.baizhitong.resource.manage.section.service.SectionService;

@Controller
@RequestMapping("/manage/coverageTb")
public class PlatformResCoverageTbAction extends BaseAction {
    private @Autowired SectionService               sectionService;
    private @Autowired PlatformResCoverageTbService coverageTbService;
    @Autowired
    ReportOrgResDailyService                        reportOrgResDailyService;

    /**
     * 跳转到章节资源覆盖率 ()<br>
     * 
     * @param map
     * @return 页面
     */
    @RequestMapping("/toCoverageTb.html")
    public String toTb(ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/manage/report/reportResCoverageTb.html";
    }

    /**
     * 查询分页信息 ()<br>
     * 
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param verCode
     * @return
     */
    @RequestMapping("/page.html")
    @ResponseBody
    public Page pageList(Integer page, Integer rows, String termCode, String sectionCode, String subjectCode,
                    String gradeCode, String verCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("verCode", verCode);
        param.put("termCode", termCode);
        return coverageTbService.getPage(param, page, rows);
    }

    /**
     * 查询分页信息 ()<br>
     * 
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param verCode
     * @return
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public List<Map<String, Object>> pageList(String termCode, String sectionCode, String subjectCode, String gradeCode,
                    String verCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("verCode", verCode);
        param.put("termCode", termCode);
        return coverageTbService.getList(param);
    }

    /**
     * 根据教材章节查询最新 ()<br>
     * 
     * @param tbCode
     * @return
     */
    @RequestMapping("/getByTbCode.html")
    @ResponseBody
    public Map<String, Object> getByTbCode(String tbCode) {
        return coverageTbService.getByTbCode(tbCode);
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
                    String verCode, HttpServletRequest request, HttpServletResponse response, String fileName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("verCode", verCode);
        param.put("termCode", termCode);
        List<Map<String, Object>> list = coverageTbService.getList(param);
        for (Map<String, Object> info : list) {
            BigDecimal resVideo = new BigDecimal(info.get("resVideoCoverage").toString());
            BigDecimal temp = new BigDecimal(Float.toString(100.00f));
            String resVideoCoverage = resVideo.multiply(temp).setScale(2, BigDecimal.ROUND_DOWN) + "" + "%";
            info.put("resVideoCoverage", resVideoCoverage);
            BigDecimal resDoc = new BigDecimal(info.get("resDocCoverage").toString());
            String resDocCoverage = resDoc.multiply(temp).setScale(2, BigDecimal.ROUND_DOWN) + "" + "%";
            info.put("resDocCoverage", resDocCoverage);
            BigDecimal resTest = new BigDecimal(info.get("resTestCoverage").toString());
            String resTestCoverage = resTest.multiply(temp).setScale(2, BigDecimal.ROUND_DOWN) + "" + "%";
            info.put("resTestCoverage", resTestCoverage);
            BigDecimal resQuestion = new BigDecimal(info.get("resQuestionCoverage").toString());
            String resQuestionCoverage = resQuestion.multiply(temp).setScale(2, BigDecimal.ROUND_DOWN) + "" + "%";
            info.put("resQuestionCoverage", resQuestionCoverage);
        }
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("学段", "sectionName");
        map.put("学科", "subjectName");
        map.put("年级", "gradeName");
        map.put("教材名称", "tbName");
        map.put("版本", "verName");
        map.put("章节节点数", "chapterNum");
        map.put("视频资源数", "resVideoNum");
        map.put("文档资源数", "resDocNum");
        map.put("测验资源数", "resTestNum");
        map.put("题目资源数", "resQuestionNum");
        map.put("视频覆盖率", "resVideoCoverage");
        map.put("文档覆盖率", "resDocCoverage");
        map.put("测验覆盖率", "resTestCoverage");
        map.put("题目覆盖率", "resQuestionCoverage");
        List<String> countColumn = new ArrayList<String>();
        countColumn.add("chapterNum");
        countColumn.add("resVideoNum");
        countColumn.add("resDocNum");
        countColumn.add("resTestNum");
        countColumn.add("resQuestionNum");
        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        response.setContentType("application/x-msdownload ");
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
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
}
