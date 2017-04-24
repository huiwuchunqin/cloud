package com.baizhitong.resource.manage.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.metadata.IIOInvalidTreeException;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement.Else;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.dao.report.ReportOrgResDailyDao;
import com.baizhitong.resource.manage.report.service.ReportOrgResDailyService;

@Service
public class ReportOrgResDailyServiceImpl extends BaseService implements ReportOrgResDailyService {
    private @Autowired ReportOrgResDailyDao reportOrgResDailyDao;

    /**
     * 查询机构资源每日日报 ()<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page getReportOrgResDaily(Integer page, Integer rows, Map<String, Object> param) {
        return reportOrgResDailyDao.getReportOrgResDaily(page, rows, param);
    }

    /**
     * 查询资源分页列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getResPageList(Map<String, Object> param, Integer page, Integer rows) {
        Integer resTypeL1 = MapUtils.getInteger(param, "resTypeL1");
        Page pageInfo = null;
        if (resTypeL1.equals(CoreConstants.RES_TYPE_DOC)) {
            pageInfo = reportOrgResDailyDao.getDocList(param, page, rows);
        } else if (resTypeL1.equals(CoreConstants.RES_TYPE_MEDIA)) {
            pageInfo = reportOrgResDailyDao.getMediaList(param, page, rows);
        } else if (resTypeL1.equals(CoreConstants.RES_TYPE_EXERCISE)) {
            pageInfo = reportOrgResDailyDao.getExerciseList(param, page, rows);
        } else if (resTypeL1.equals(CoreConstants.RES_TYPE_QUESTION)) {
            pageInfo = reportOrgResDailyDao.getQuestionList(param, page, rows);
        }
        if (pageInfo != null) {
            List<Map<String, Object>> mapList = new ArrayList();
            mapList = pageInfo.getRows();
            for (Map map : mapList) {
                String resCode = MapUtils.getString(map, "resCode");
                if (resTypeL1.equals(CoreConstants.RES_TYPE_DOC)) {
                    map.put("accessPath",
                                    SystemConfig.docPreviewUrl.replaceAll("(resCode[^&]*)", "resCode=" + resCode));
                } else if (resTypeL1.equals(CoreConstants.RES_TYPE_MEDIA)) {
                    map.put("accessPath",
                                    SystemConfig.mediaPreviewUrl.replaceAll("(resCode[^&]*)", "resCode=" + resCode));
                } else if (resTypeL1.equals(CoreConstants.RES_TYPE_EXERCISE)) {
                    Integer shareLevel = MapUtils.getInteger(map, "shareLevel");
                    map.put("accessPath",
                                    SystemConfig.exercisePreviewUrl.replaceAll("(resCode[^&]*)", "resCode=" + resCode)
                                                    .replaceAll("(shareLevel[^&]*)", "shareLevel=" + shareLevel));
                } else if (resTypeL1.equals(CoreConstants.RES_TYPE_QUESTION)) {
                    map.put("accessPath",
                                    SystemConfig.questionPreviewUrl.replaceAll("(resCode[^&]*)", "resCode=" + resCode));
                }
            }
        }
        return pageInfo;
    }

    /**
     * 查询资源统计累加数据 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getSum(Map<String, Object> param) {
        Integer resTypeL1 = MapUtils.getInteger(param, "resTypeL1");
        if (resTypeL1.equals(CoreConstants.RES_TYPE_DOC)) {
            return reportOrgResDailyDao.getDocSum(param);
        } else if (resTypeL1.equals(CoreConstants.RES_TYPE_MEDIA)) {
            return reportOrgResDailyDao.getMediaSum(param);
        } else if (resTypeL1.equals(CoreConstants.RES_TYPE_EXERCISE)) {
            return reportOrgResDailyDao.getExerciseSum(param);
        } else if (resTypeL1.equals(CoreConstants.RES_TYPE_QUESTION)) {
            return reportOrgResDailyDao.getQuestionSum(param);
        }
        return null;
    }
}
