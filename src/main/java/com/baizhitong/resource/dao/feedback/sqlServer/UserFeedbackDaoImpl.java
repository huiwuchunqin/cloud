package com.baizhitong.resource.dao.feedback.sqlServer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.feedback.UserFeedbackDao;
import com.baizhitong.resource.model.feedback.UserFeedback;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * UserFeedbackDaoImpl 用户反馈Dao实现
 * 
 * @author creator zhanglzh 2016年9月29日 上午9:21:06
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class UserFeedbackDaoImpl extends BaseSqlServerDao<UserFeedback> implements UserFeedbackDao {
    final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 
     * (获取用户反馈信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> getUserFeedbackList(Integer page, Integer rows, Map<String, Object> param) {
        StringBuilder sql = new StringBuilder();
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String moduleName = MapUtils.getString(param, "moduleName");
        String disposeStatus = MapUtils.getString(param, "disposeStatus");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        sql.append("SELECT");
        sql.append("        id");
        sql.append("        ,orgCode");
        sql.append("        ,userRole");
        sql.append("        ,userCode");
        sql.append("        ,userName");
        sql.append("        ,deviceType");
        sql.append("        ,browserInfo");
        sql.append("        ,appVerInfo");
        sql.append("        ,deviceBrand");
        sql.append("        ,deviceOsVer");
        sql.append("        ,deviceOther");
        sql.append("        ,geoInfo");
        sql.append("        ,content");
        sql.append("        ,actionTime");
        sql.append("        ,moduleName");
        sql.append("        ,moduleDesc");
        sql.append("        ,disposeStatus");
        sql.append("        ,disposeDesc");
        sql.append("        ,memo");
        sql.append("        ,modifier");
        sql.append("        ,modifyTime");
        sql.append("        ,modifierIP");
        sql.append("        ,orgName ");
        sql.append("    FROM");
        sql.append("        user_feedback ");
        sql.append("    WHERE");
        sql.append("     actionTime<= :endDate");
        if (StringUtils.isNotEmpty(moduleName)) {
            sql.append("        AND moduleName like :moduleName ");
            sqlParam.put("moduleName", "%"+moduleName.trim()+"%");
        }
        if (StringUtils.isNotEmpty(disposeStatus)) {
            sql.append("        AND disposeStatus = :disposeStatus "); 
        }
        sql.append("        AND flagDelete = :flagDelete ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("    AND actionTime >= :startDate ");
        }
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("disposeStatus", disposeStatus);
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        sql.append("        ORDER BY  actionTime DESC");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public int updateFlag(String ids, Integer operateType, String userName, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        sql.append("UPDATE");
        sql.append("        user_feedback ");
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
     * (修改用户反馈信息处理状态)<br>
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
        sql.append("        user_feedback ");
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
     * (修改用户反馈信息处理描述)<br>
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
        sql.append("        user_feedback ");
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
    public Map<String, Object> getUserFeedbackById(Integer page, Integer rows, Map<String, Object> param) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String id = MapUtils.getString(param, "id");
        sql.append("SELECT");
        sql.append("        content");
        sql.append("        ,moduleName");
        sql.append("        ,moduleDesc");
        sql.append("        ,disposeDesc");
        sql.append("        ,disposeStatus");
        sql.append("    FROM");
        sql.append("        user_feedback ");
        sql.append("    WHERE");
        sql.append("     id= :id");
        sql.append("        ORDER BY  actionTime DESC");
        sqlParam.put("id", id);
        log.info(sql.toString());
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }
}
