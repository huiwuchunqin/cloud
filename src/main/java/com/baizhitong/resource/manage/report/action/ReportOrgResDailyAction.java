package com.baizhitong.resource.manage.report.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.manage.report.service.OrgResTypeMakeDailyService;
import com.baizhitong.resource.manage.report.service.ReportOrgResDailyService;
import com.baizhitong.resource.manage.res.service.ResTypeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.company.ShareOrg;

/**
 * 资源汇总 ReportOrgResDailyAction
 * 
 * @author creator BZT 2016年7月6日 下午6:04:09
 * @author updatero
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/reportOrgResDaily")
public class ReportOrgResDailyAction extends BaseAction {
    /**
     * 资源详情接口
     */
    @Autowired
    private ReportOrgResDailyService   reportOrgResDailyService;
    /**
     * 学段接口
     */
    @Autowired
    private SectionService             sectionService;
    /**
     * 资源汇总统计接口
     */
    @Autowired
    private OrgResTypeMakeDailyService orgResTypeMakeDailyService;
    /**
     * 机构dao
     */
    @Autowired
    private ShareOrgDao                shareOrgDao;
    /**
     * 资源类型接口
     */
    @Autowired
    private ResTypeService             resTypeService;

    /**
     * 跳转资源汇总 ()<br>
     * 
     * @param model 数据模型
     * @param request 请求
     * @return 页面
     */
    @RequestMapping("/toAllOrgResReport.html")
    public String toAllOrgResMakeDaily(ModelMap model, HttpServletRequest request) {
        return "/manage/report/allOrgResMakeDaily.html";
    }

    /**
     * 跳转资源汇总资源详情 ()<br>
     * 
     * @param model 数据模型
     * @param resTypeL1 一级分类
     * @param resTypeL2 二级分类
     * @param request 请求
     * @return 页面
     */
    @RequestMapping("/toResReportDetial.html")
    public String toAllOrgResMakeDaily(HttpServletRequest request, ModelMap model, Integer resTypeL1,
                    String resTypeL2) {
        model.put("resTypeL1", resTypeL1);
        model.put("resTypeL2", resTypeL2);
        model.put("resTypeL1List", JSON.toJSONString(resTypeService.getResTypeL1()));
        try {
            model.put("sectionList", JSON.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/manage/report/allOrgResMakeDailyDetail.html";
    }

    /**
     * 跳转到学校资源统计 ()<br>
     * 
     * @param model 数据模型
     * @return 页面
     */
    @RequestMapping("/toReportResOrg.html")
    public String toReportResOrgDaily(ModelMap model) {
        try {
            model.put("sectionList", JSON.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/manage/report/orgResMakeDaily.html";
    }

    /**
     * 
     * 转到学校统计详情页面 ()<br>
     * 
     * @param model 数据模型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param orgCode 机构编码
     * @param sectionCode 学段编码
     * @return 页面
     */
    @RequestMapping("/toReportTypeResOrg.html")
    public String toReportTypeResOrgDaily(ModelMap model, String startDate, String endDate, String orgCode,
                    String sectionCode) {
        try {
            model.put("orgList", JSON.toJSONString(shareOrgDao.getOrgList()));
            model.put("orgCode", orgCode);
            model.put("sectionCode", sectionCode);
            model.put("startDate", startDate);
            model.put("endDate", endDate);
            return "/manage/report/orgResTypeMakeDaily.html";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "/manage/report/orgResTypeMakeDaily.html";
        }
    }

    /**
     * 查询学校统计数据 ()<br>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param sectionCode 学段
     * @param orgName 机构名称
     * @param page 页码
     * @param rows 记录数
     * @return 数据分页列表
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public Page getOrgResDaily(Integer startDate, Integer endDate, String sectionCode, String orgName, Integer page,
                    Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        return reportOrgResDailyService.getReportOrgResDaily(page, rows, param);
    }

    /**
     * 学校资源统计详情<br>
     * 
     * @param orgCode 机构编码
     * @param startDate 开始日期
     * @param sectionCode 学段编码
     * @param endDate 结束日期
     * @return 数据列表
     */
    @RequestMapping("/typeList.html")
    @ResponseBody
    public List<Map<String, Object>> getOrgResTypeMakeDaily(String orgCode, String sectionCode, Integer startDate,
                    Integer endDate) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("orgCode", orgCode);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return orgResTypeMakeDailyService.getResTypeMakeDaily(param);
    }

    /**
     * 得到资源汇总 ()<br>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 数据列表
     */
    @RequestMapping("/listAllOrgRes.html")
    @ResponseBody
    public List<Map<String, Object>> getAllOrgResTypeMakeDaily(Integer startDate, Integer endDate) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return orgResTypeMakeDailyService.getAllCompanyResTypeMakeDaily(param);
    }

    /**
     * 
     * 查询资源汇总详情资源列表 ()<br>
     * 
     * @param resTypeL1 资源一级分类
     * @param resTypeL2 二级分类
     * @param shareLevel 分享级别
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param textbookVerCode 教材版本编码
     * @param termCode 学期编码
     * @param chapterCode 教材章节编码
     * @param kpCode 知识点编码
     * @param orgCode 机构编码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param rows 记录数
     * @return 分页列表
     */
    @RequestMapping("/resDetail.html")
    @ResponseBody
    public Page getResList(Integer resTypeL1, String resTypeL2, String shareLevel, String sectionCode,
                    String subjectCode, String gradeCode, String textbookVerCode, String termCode, String chapterCode,
                    String kpCode, String orgCode, String startDate, String endDate, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resTypeL1", resTypeL1);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("textbookVerCode", textbookVerCode);
        param.put("termCode", termCode);
        param.put("chapterCode", chapterCode);
        param.put("kpCode", kpCode);
        param.put("orgCode", orgCode);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return reportOrgResDailyService.getResPageList(param, page, rows);
    }

    /**
     * 查询资源汇总详情头部累加数据 ()<br>
     * 
     * @param resTypeL1 一级分类
     * @param resTypeL2 二级分类
     * @param shareLevel 分享级别
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param textbookVerCode 教材版本
     * @param termCode 学期
     * @param chapterCode 教材章节
     * @param kpCode 知识点
     * @param orgCode 机构编码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 操作结果
     */
    @RequestMapping("/resDetailSum.html")
    @ResponseBody
    public List<Map<String, Object>> getResSumList(Integer resTypeL1, String resTypeL2, String shareLevel,
                    String sectionCode, String subjectCode, String gradeCode, String textbookVerCode, String termCode,
                    String chapterCode, String kpCode, String orgCode, String startDate, String endDate) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resTypeL1", resTypeL1);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("textbookVerCode", textbookVerCode);
        param.put("termCode", termCode);
        param.put("chapterCode", chapterCode);
        param.put("kpCode", kpCode);
        param.put("orgCode", orgCode);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return reportOrgResDailyService.getSum(param);
    }

    /**
     * 资源汇总详情导出 ()<br>
     * ()<br>
     * 
     * @param resTypeL1 一级分类
     * @param resTypeL2 二级分类
     * @param shareLevel 分享级别
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param textbookVerCode 教材版本
     * @param termCode 学期
     * @param chapterCode 教材章节
     * @param kpCode 知识点
     * @param orgCode 机构
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param request 请求
     * @param response 相应
     * @return 操作结果
     */
    @RequestMapping("/exportDetail.html")
    @ResponseBody
    public ResultCodeVo exportDetail(Integer resTypeL1, String resTypeL2, String shareLevel, String sectionCode,
                    String subjectCode, String gradeCode, String textbookVerCode, String termCode, String chapterCode,
                    String kpCode, String orgCode, String startDate, String endDate, HttpServletRequest request,
                    HttpServletResponse response, String fileName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resTypeL1", resTypeL1);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("textbookVerCode", textbookVerCode);
        param.put("termCode", termCode);
        param.put("chapterCode", chapterCode);
        param.put("kpCode", kpCode);
        param.put("orgCode", orgCode);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        Page page = reportOrgResDailyService.getResPageList(param, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "resName");
        map.put("二级分类", "resTypeL2Name");
        map.put("共享级别", "shareLevelName");
        map.put("学段", "sectionName");
        map.put("学科", "subjectName");
        map.put("年级", "gradeName");
        map.put("上传时间", "makeTime");
        map.put("作者", "makerName");
        map.put("学校", "makerOrgName");
        map.put("查看数", "browseCount");
        map.put("下载数", "downloadCount");
        map.put("引用数", "referCount");
        map.put("收藏数", "favoriteCount");
        map.put("点赞数", "goodCount");
        map.put("评论数", "commentCount");
        map.put("访问地址", "accessPath");
        List<String> countColumn = new ArrayList<String>();
        countColumn.add("browseCount");
        countColumn.add("downloadCount");
        countColumn.add("referCount");
        countColumn.add("favoriteCount");
        countColumn.add("goodCount");
        countColumn.add("commentCount");
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
     * 资源汇总导出 ()<br>
     * ()<br>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param request 请求
     * @param response 相应
     * @param fileName 文件名
     * @return 操作结果
     */
    @RequestMapping("/exportAggregate.html")
    @ResponseBody
    public ResultCodeVo exportAggregate(Integer startDate, Integer endDate, HttpServletRequest request,
                    HttpServletResponse response, String fileName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        List<Map<String, Object>> list = orgResTypeMakeDailyService.getAllCompanyResTypeMakeDaily(param);
        Map<String, String> columMap = new LinkedHashMap<String, String>();
        List<String> countColumn = new ArrayList<String>();
        columMap.put("一级分类", "level1Name");
        columMap.put("二级分类", "level2Name");
        columMap.put("个人私有", "privateNum");
        columMap.put("校内共享", "orgNum");
        columMap.put("区域共享", "areaNum");
        columMap.put("数量", "totalNum");

        countColumn.add("privateNum");
        countColumn.add("orgNum");
        countColumn.add("areaNum");
        countColumn.add("totalNum");

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
        HSSFWorkbook excel = ExcelUtils.getWb(list, columMap, null, countColumn);
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
     * 学校统计详情导出 ()<br>
     * ()<br>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param sectionCode 学段
     * @param orgCode 机构
     * @param request 请求
     * @param response 相应
     * @return 操作结果
     */
    @RequestMapping("/exportType.html")
    @ResponseBody
    public ResultCodeVo exportTypeReport(Integer startDate, Integer endDate, String sectionCode, String orgCode,
                    HttpServletRequest request, HttpServletResponse response, String fileName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgCode", orgCode);
        ShareOrg shareOrg = shareOrgDao.getOrg(orgCode);
        fileName = fileName + "-" + shareOrg.getOrgName() + ".xls";
        List<Map<String, Object>> list = orgResTypeMakeDailyService.getResTypeMakeDaily(param);
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("一级分类", "level1Name");
        map.put("二级分类", "level2Name");
        map.put("数量", "totalNum");
        map.put("个人私有", "privateNum");
        map.put("校内共享", "orgNum");
        map.put("区域共享", "areaNum");
        List<String> countColumn = new ArrayList<String>();
        countColumn.add("totalNum");
        countColumn.add("privateNum");
        countColumn.add("orgNum");
        countColumn.add("areaNum");
        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        HSSFWorkbook excel = ExcelUtils.getWb(list, map, null, countColumn);
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
            response.setContentType("application/x-msdownload ");
            excel.write(response.getOutputStream());
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            return null;
        }

    }

    /**
     * 学校统计导出 ()<br>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param sectionCode 学段
     * @param orgName 机构名称
     * @param request 请求
     * @param response 相应
     * @return 操作结果
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/export.html")
    @ResponseBody
    public ResultCodeVo exportReport(Integer startDate, Integer endDate, String sectionCode, String orgName,
                    HttpServletRequest request, HttpServletResponse response, String fileName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        if (StringUtils.isNotEmpty(orgName)) {
            orgName = URLDecoder.decode(orgName);
            param.put("orgName", orgName);

        }
        Page page = reportOrgResDailyService.getReportOrgResDaily(1, Integer.MAX_VALUE, param);
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("机构名称", "orgName");
        map.put("学段", "sectionName");
        map.put("视频", "mediaTotal");
        map.put("文档", "docTotal");
        map.put("测验", "exerciseTotal");
        map.put("题目", "questionTotal");
        map.put("资源总数", "resTotal");
        List<String> countColumn = new ArrayList<String>();
        countColumn.add("resTotal");
        countColumn.add("mediaTotal");
        countColumn.add("docTotal");
        countColumn.add("exerciseTotal");
        countColumn.add("questionTotal");
        List<Map<String, Object>> list = page.getRows();
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
        HSSFWorkbook excel = ExcelUtils.getWb(page.getRows(), map, null, countColumn);

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
