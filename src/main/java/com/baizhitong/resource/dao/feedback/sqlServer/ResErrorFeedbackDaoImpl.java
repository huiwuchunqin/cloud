package com.baizhitong.resource.dao.feedback.sqlServer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.feedback.ResErrorFeedbackDao;
import com.baizhitong.resource.model.feedback.ResErrorFeedback;
import com.baizhitong.utils.DateUtils;

/**
 * 
 * ResErrorFeedbackDaoImpl 资源纠错Dao实现
 * 
 * @author creator zhanglzh 2016年9月29日 上午9:22:18
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ResErrorFeedbackDaoImpl extends BaseSqlServerDao<ResErrorFeedback> implements ResErrorFeedbackDao {
    /**
     * 
     * (获取资源纠错信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    @Override
    public Page getResErrorFeedbackList(Integer page, Integer rows, Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String resTypeL1 = MapUtils.getString(param, "resTypeL1");
        String resName = MapUtils.getString(param, "resName");
        String disposeStatus = MapUtils.getString(param, "disposeStatus");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");

        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        sql.append("SELECT");
        sql.append("            ref.id");
        sql.append("            ,ref.orgCode");
        sql.append("            ,ref.orgName");
        sql.append("            ,ref.userRole");
        sql.append("            ,ref.userCode");
        sql.append("            ,ref.userName");
        sql.append("            ,ref.deviceType");
        sql.append("            ,ref.browserInfo");
        sql.append("            ,ref.appVerInfo");
        sql.append("            ,ref.deviceBrand");
        sql.append("            ,ref.deviceOsVer");
        sql.append("            ,ref.deviceOther");
        sql.append("            ,ref.geoInfo");
        sql.append("            ,ref.resTypeL1");
        sql.append("            ,ref.resTypeL2");
        sql.append("            ,ref.resCode");
        sql.append("            ,ref.resName");
        sql.append("            ,ref.feedbackTime");
        sql.append("            ,ref.errorType");
        sql.append("            ,ref.errorDesc");
        sql.append("            ,ref.disposeStatus");
        sql.append("            ,ref.disposeDesc");
        sql.append("            ,ref.memo");
        sql.append("            ,ref.creator");
        sql.append("            ,ref.createTime ");
        sql.append("            ,srt.name AS resTypeL1Name ");
        sql.append("        FROM");
        sql.append("            res_error_feedback AS ref ");
        sql.append("                LEFT JOIN share_res_type_l1 srt ON ref.resTypeL1 = srt.code ");
        sql.append("    WHERE");
        sql.append("        ref.flagDelete = :flagDelete "); 
        sql.append("    AND ref.createTime <= :endDate ");
        if (StringUtils.isNotEmpty(startDate)) { 
            sql.append("        AND ref.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(disposeStatus)) {
            sql.append("        AND ref.disposeStatus = :disposeStatus ");
            sqlParam.put("disposeStatus", disposeStatus);
        }
        if (StringUtils.isNotEmpty(resTypeL1)) {
            sqlParam.put("resTypeL1", resTypeL1);
            sql.append("        AND ref.resTypeL1 = :resTypeL1 ");
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append("  AND ref.resName like :resName ");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        sql.append("        ORDER BY  ref.feedbackTime DESC");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }

    /**
     * 
     * (获取资源纠错详细信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    @Override
    public Page getResErrorFeedbackDailyList(Integer page, Integer rows, Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String resName = MapUtils.getString(param, "resName");
        String resCode = MapUtils.getString(param, "resCode");

        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sql.append("SELECT");
        sql.append("        ref.id");
        sql.append("        ,ref.orgCode");
        sql.append("        ,ref.orgName");
        sql.append("        ,ref.userRole");
        sql.append("        ,ref.userCode");
        sql.append("        ,ref.userName");
        sql.append("        ,ref.deviceType");
        sql.append("        ,ref.browserInfo");
        sql.append("        ,ref.appVerInfo");
        sql.append("        ,ref.deviceBrand");
        sql.append("        ,ref.deviceOsVer");
        sql.append("        ,ref.deviceOther");
        sql.append("        ,ref.geoInfo");
        sql.append("        ,ref.resTypeL1");
        sql.append("        ,ref.resTypeL2");
        sql.append("        ,ref.resCode");
        sql.append("        ,ref.resName");
        sql.append("        ,ref.feedbackTime");
        sql.append("        ,ref.errorType");
        sql.append("        ,ref.errorDesc");
        sql.append("        ,ref.moduleDesc");
        sql.append("        ,ref.memo");
        sql.append("        ,ref.creator");
        sql.append("        ,ref.createTime ");
        sql.append("    FROM");
        sql.append("     res_error_feedback AS ref ");
        sql.append("    WHERE");
        sql.append("        ref.flagDelete = 0 ");
        sql.append("    AND    ref.createTime <= :endDate ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND ref.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(resCode)) {
            sqlParam.put("resCode", resCode);
            sql.append("        AND ref.resCode = :resCode ");
        }
        if (StringUtils.isNotEmpty(resName)) {
            sqlParam.put("resName", resName);
            sql.append("        AND ref.resName = :resName ");
        }
        sql.append("        ORDER BY feedbackTime DESC");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 
     * (删除资源纠错信息)<br>
     * 
     * @param ids
     * @param operateType
     * @return 操作影响数
     */
    @Override
    public int updateFlag(String ids, Integer operateType, String userName, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        sql.append("UPDATE");
        sql.append("        res_error_feedback ");
        sql.append("    SET");
        sql.append("        flagDelete = :flagDelete");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id ");
        sql.append("    IN (" + ids + ") ");
        // 删除
        if (1 == operateType.intValue()) {
            sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_PACED);
        } else if (2 == operateType.intValue()) {
            // 恢复
            sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        }
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (修改资源纠错信息处理状态)<br>
     * 
     * @param ids
     * @param operateType
     * @return 操作影响数
     */
    @Override
    public int updateDisposeStatus(String ids, Integer operateType, String userName, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        sql.append("UPDATE");
        sql.append("        res_error_feedback ");
        sql.append("    SET");
        sql.append("        disposeStatus = :disposeStatus");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id ");
        sql.append("    IN (" + ids + ") ");
        sqlParam.put("disposeStatus", operateType.intValue());

        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (修改资源纠错信息处理描述)<br>
     * 
     * @param ids
     * @param disposeDesc
     * @return 操作影响数
     */
    @Override
    public int updateDisposeDesc(String ids, String disposeDesc, String userName, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        sql.append("UPDATE");
        sql.append("        res_error_feedback ");
        sql.append("    SET");
        sql.append("        disposeDesc = :disposeDesc");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id ");
        sql.append("    IN (" + ids + ") ");
        sqlParam.put("disposeDesc", disposeDesc);

        return super.update(sql.toString(), sqlParam);
    }

    @Override
    public Map<String, Object> getResErrorFeedbackById(Integer page, Integer rows, Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String id = MapUtils.getString(param, "id");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("id", id);
        sql.append("SELECT");
        sql.append("            ref.errorDesc");
        sql.append("            ,ref.disposeDesc");
        sql.append("        ,ref.disposeStatus");
        sql.append("        FROM");
        sql.append("            res_error_feedback AS ref ");
        sql.append("    WHERE");
        sql.append("        ref.id = :id ");
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }
}
