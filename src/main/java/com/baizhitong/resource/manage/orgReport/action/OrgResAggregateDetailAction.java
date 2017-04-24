package com.baizhitong.resource.manage.orgReport.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.manage.companyGrade.service.ICompanyGradeService;
import com.baizhitong.resource.manage.companySubject.service.ICompanySubjectSerivce;
import com.baizhitong.resource.manage.report.service.ReportOrgResDailyService;
import com.baizhitong.resource.manage.res.service.ResTypeService;

/**
 * 机构资源汇总详情控制类 OrgResAggregateDetailAction TODO
 * 
 * @author creator gaow 2016年11月22日 下午2:57:51
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/orgResAggreateDetail")
public class OrgResAggregateDetailAction extends BaseAction {
    /**
     * 资源详情接口
     */
    @Autowired
    private ReportOrgResDailyService reportOrgResDailyService;
    /**
     * 资源类型接口
     */
    @Autowired
    private ResTypeService           resTypeService;
    /**
     * 机构学科接口
     */
    @Autowired
    private ICompanySubjectSerivce   companySubjectService;
    /**
     * 机构年级接口
     */
    @Autowired
    private ICompanyGradeService     companyGradeService;

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
        model.put("orgCode", getOrgCode());
        model.put("resTypeL1List", JSON.toJSONString(resTypeService.getResTypeL1()));
        model.put("subjectList", JSON.toJSONString(companySubjectService.getCompanySubjectList("")));
        model.put("gradeList", JSON.toJSONString(companyGradeService.getGradeList()));
        return "/manage/report/orgReport/orgResAggregateDetail.html";
    }

    /**
     * 
     * 查询机构资源汇总详情资源列表 ()<br>
     * 
     * @param resTypeL1 资源一级分类
     * @param resTypeL2 二级分类
     * @param shareLevel 分享级别
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param textbookVerCode 教材版本编码
     * @param termCode 学期编码
     * @param chapterCode 教材章节编码
     * @param kpCode 知识点编码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param rows 记录数
     * @return 分页列表
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public Page list(HttpServletRequest request, Integer resTypeL1, String resTypeL2, String shareLevel,
                    String subjectCode, String gradeCode, String textbookVerCode, String termCode, String chapterCode,
                    String kpCode, String startDate, String endDate, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resTypeL1", resTypeL1);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("textbookVerCode", textbookVerCode);
        param.put("termCode", termCode);
        param.put("chapterCode", chapterCode);
        param.put("kpCode", kpCode);
        param.put("orgCode", getOrgCode() );
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return reportOrgResDailyService.getResPageList(param, page, rows);
    }

    /**
     * 机构资源汇总详情导出 ()<br>
     * ()<br>
     * 
     * @param resTypeL1 一级分类
     * @param resTypeL2 二级分类
     * @param shareLevel 分享级别
     * 
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param textbookVerCode 教材版本
     * @param termCode 学期
     * @param chapterCode 教材章节
     * @param kpCode 知识点
     * 
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param request 请求
     * @param response 相应
     * @return 操作结果
     */
    @RequestMapping("/exportDetail.html")
    @ResponseBody
    public ResultCodeVo exportDetail(Integer resTypeL1, String resTypeL2, String shareLevel, String subjectCode,
                    String gradeCode, String textbookVerCode, String termCode, String chapterCode, String kpCode,
                    String startDate, String endDate, HttpServletRequest request, String fileName,
                    HttpServletResponse response) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resTypeL1", resTypeL1);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("textbookVerCode", textbookVerCode);
        param.put("termCode", termCode);
        param.put("chapterCode", chapterCode);
        param.put("kpCode", kpCode);
        param.put("orgCode", getOrgCode());
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

}
