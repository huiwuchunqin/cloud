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

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.manage.orgReport.service.OrgResAggregateService;

/**
 * 机构资源汇总 OrgResGather TODO
 * 
 * @author creator gaow 2016年11月22日 上午10:37:27
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/orgResAggreagte")
public class OrgResAggregateAction extends BaseAction {
    /**
     * 机构资源汇总
     */
    @Autowired
    private OrgResAggregateService orgResAggregateService;

    /**
     * 跳转机构资源汇总 ()<br>
     * 
     * @param model 数据模型
     * @param request 请求
     * @return 页面
     */
    @RequestMapping("/toOrgResResAggregate.html")
    public String toResAggregate(ModelMap model, HttpServletRequest request) {
        return "/manage/report/orgReport/orgResAggregate.html";
    }

    /**
     * 查询机构资源汇总 ()<br>
     * 
     * @param request request请求
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return list
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public List<Map<String, Object>> resAggregateList(HttpServletRequest request, Integer startDate, Integer endDate) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("orgCode", getOrgCode() );
        return orgResAggregateService.getOrgResAggregate(sqlParam);
    }

    /**
     * 
     * 机构资源汇总数据导出
     * 
     * @param request request请求
     * @param response response相应
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 操作结果
     */
    @RequestMapping("/export.html")
    @ResponseBody
    public ResultCodeVo exportAggregate(HttpServletRequest request, HttpServletResponse response, Integer startDate,
                    Integer endDate, String fileName) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("orgCode", getOrgCode() );
        List<Map<String, Object>> list = orgResAggregateService.getOrgResAggregate(sqlParam);
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("一级分类", "level1Name");
        map.put("二级分类", "level2Name");
        map.put("个人私有", "privateNum");
        map.put("校内共享", "orgNum");
        map.put("区域共享", "areaNum");
        map.put("数量", "totalNum");
        List<String> countColumn = new ArrayList<String>();
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
        } catch (UnsupportedEncodingException e) {
            return ResultCodeVo.errorCode("文件名编码转换错误");
        }
        response.setContentType("application/x-msdownload ");
        HSSFWorkbook excel = ExcelUtils.getWb(list, map, null, countColumn);
        try {
            excel.write(response.getOutputStream());
        } catch (IOException e) {
            return ResultCodeVo.errorCode("excel数据输出异常");
        }
        return null;

    }
}
