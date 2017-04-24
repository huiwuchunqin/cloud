package com.baizhitong.resource.dao.res.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResMediaSpecialDao;
import com.baizhitong.resource.model.res.ResMediaSpecial;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.ObjectUtils;

/**
 * 
 * 资源_特色资源SqlServer接口实现
 * 
 * @author creator zhangqiang 2016年8月9日 上午11:34:10
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ResMediaSpecialSqlServerDaoImpl extends BaseSqlServerDao<ResMediaSpecial> implements ResMediaSpecialDao {

    /**
     * 
     * (查询特色资源全部信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectSpecialAllInfoPage(Map<String, Object> param) {
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        Integer shareLevel = MapUtils.getInteger(param, "shareLevel");
        String resSpecialTypeL1 = MapUtils.getString(param, "resSpecialTypeL1");
        String resSpecialTypeL2 = MapUtils.getString(param, "resSpecialTypeL2");
        Integer resStatus = MapUtils.getInteger(param, "resStatus");
        String makerOrgName = MapUtils.getString(param, "makerOrgName");
        String makerOrgCode = MapUtils.getString(param, "makerOrgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String resName = MapUtils.getString(param, "resName");
        String makerName = MapUtils.getString(param, "makerName");
        Integer shareCheckStatus = MapUtils.getInteger(param, "shareCheckStatus");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        rms.resName");
        sql.append("        ,rms.shareCheckStatus");
        sql.append("        ,rms.shareLevel");
        sql.append("        ,rms.shareCheckLevel");
        sql.append("        ,type1.name AS resSpecialTypeL1Name");
        sql.append("        ,type2.name AS resSpecialTypeL2Name");
        sql.append("        ,rms.makerName");
        sql.append("        ,rms.makerOrgName");
        sql.append("        ,rms.makeTime");
        sql.append("        ,rms.resStatus ");
        sql.append("        ,rms.id");
        sql.append("        ,rms.resCode");
        sql.append("    FROM");
        sql.append("        res_media_special rms ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type type1 ");
        sql.append("            ON rms.resSpecialTypeL1 = type1.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type type2 ");
        sql.append("            ON rms.resSpecialTypeL2 = type2.code ");
        sql.append("    WHERE");
        sql.append("        rms.flagDelete = 0");
        if (shareLevel != null) {
            sql.append("  AND rms.shareLevel = :shareLevel");
            sqlParam.put("shareLevel", shareLevel);
        }
        if (StringUtils.isNotEmpty(resSpecialTypeL1)) {
            sql.append("  AND rms.resSpecialTypeL1 = :resSpecialTypeL1");
            sqlParam.put("resSpecialTypeL1", resSpecialTypeL1);
        }
        if (StringUtils.isNotEmpty(resSpecialTypeL2)) {
            sql.append("  AND rms.resSpecialTypeL2 = :resSpecialTypeL2");
            sqlParam.put("resSpecialTypeL2", resSpecialTypeL2);
        }
        if (resStatus != null) {
            sql.append("  AND rms.resStatus = :resStatus");
            sqlParam.put("resStatus", resStatus);
        }
        if (StringUtils.isNotEmpty(makerOrgName)) {
            sql.append("  AND rms.makerOrgName like :makerOrgName");
            sqlParam.put("makerOrgName", "%" + makerOrgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(makerOrgCode)) {
            sql.append("  AND rms.makerOrgCode = :makerOrgCode");
            sqlParam.put("makerOrgCode", makerOrgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
            sql.append("  AND rms.makeTime >= :startDate ");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
            sql.append("  AND rms.makeTime <= :endDate ");
            sqlParam.put("endDate", endDate);
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append("  AND rms.resName like :resName");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(makerName)) {
            sql.append("  AND rms.makerName like :makerName");
            sqlParam.put("makerName", "%" + makerName.trim() + "%");
        }
        if (shareCheckStatus != null && 60 == shareCheckStatus.intValue()) {
            sql.append("  AND (rms.shareCheckStatus= :shareCheckStatus or rms.shareCheckStatus is null ) ");
            sqlParam.put("shareCheckStatus", CoreConstants.CHECK_STATUS_UNCOMMIT);
        }
        if (shareCheckStatus != null && 60 != shareCheckStatus.intValue()) {
            sql.append("  AND rms.shareCheckStatus= :shareCheckStatus ");
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        sql.append(" ORDER BY rms.makeTime DESC ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 
     * (删除特色资源)<br>
     * 
     * @param id
     * @param userName
     * @param ipAddress
     * @return
     */
    @Override
    public int delete(String id, String userName, String ipAddress) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        res_media_special ");
        sql.append("    SET");
        sql.append("        flagDelete = :flagDelete ");
        sql.append("        ,modifier = :modifier ");
        sql.append("        ,modifierIP = :modifierIP ");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id = :id ");
        sqlParam.put("id", id);
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_YES);
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ipAddress);
        sqlParam.put("modifyTime", DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (查询特色资源待审核信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectSpecialCheckInfoPage(Map<String, Object> param) {
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        String resSpecialTypeL1 = MapUtils.getString(param, "resSpecialTypeL1");
        String resSpecialTypeL2 = MapUtils.getString(param, "resSpecialTypeL2");
        String makerOrgName = MapUtils.getString(param, "makerOrgName");
        String makerOrgCode = MapUtils.getString(param, "makerOrgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String resName = MapUtils.getString(param, "resName");
        String makerName = MapUtils.getString(param, "makerName");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        rms.resName");
        sql.append("        ,rms.resCode");
        sql.append("        ,rms.shareCheckStatus");
        sql.append("        ,type1.name AS resSpecialTypeL1Name");
        sql.append("        ,type2.name AS resSpecialTypeL2Name");
        sql.append("        ,rms.makerName");
        sql.append("        ,rms.makerOrgName");
        sql.append("        ,rms.shareTime");
        sql.append("        ,rms.shareCheckLevel");
        sql.append("        ,rms.id");
        sql.append("        ,rms.shareLevel");
        sql.append("    FROM");
        sql.append("        res_media_special rms ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type type1 ");
        sql.append("            ON rms.resSpecialTypeL1 = type1.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type type2 ");
        sql.append("            ON rms.resSpecialTypeL2 = type2.code ");
        sql.append("    WHERE");
        sql.append("        rms.flagDelete = 0 and rms.resStatus = :resStatus ");
        sql.append("  and rms.shareCheckStatus = :shareCheckStatus ");
        sqlParam.put("shareCheckStatus", CoreConstants.CHECK_STATUS_CHECKING);// 待审核：10
        sqlParam.put("resStatus", CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        if (StringUtils.isNotEmpty(resSpecialTypeL1)) {
            sql.append("  AND rms.resSpecialTypeL1 = :resSpecialTypeL1");
            sqlParam.put("resSpecialTypeL1", resSpecialTypeL1);
        }
        if (StringUtils.isNotEmpty(resSpecialTypeL2)) {
            sql.append("  AND rms.resSpecialTypeL2 = :resSpecialTypeL2");
            sqlParam.put("resSpecialTypeL2", resSpecialTypeL2);
        }
        if (StringUtils.isNotEmpty(makerOrgName)) {
            sql.append("  AND rms.makerOrgName like :makerOrgName");
            sqlParam.put("makerOrgName", "%" + makerOrgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append("  AND rms.resName like :resName");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(makerName)) {
            sql.append("  AND rms.makerName like :makerName");
            sqlParam.put("makerName", "%" + makerName.trim() + "%");
        }
        // 校内管理员
        if (StringUtils.isNotEmpty(makerOrgCode)) {
            sql.append("  AND rms.makerOrgCode = :makerOrgCode");
            sql.append("  AND rms.shareCheckLevel = :shareCheckLevel");
            sqlParam.put("makerOrgCode", makerOrgCode);
            sqlParam.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY);
        } else {
            // 区域管理员和教研员一样，查看待审核都是查看申请区域共享的
            sql.append("  AND rms.shareCheckLevel = :shareCheckLevel");
            sqlParam.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
            sql.append("  AND rms.shareTime >= :startDate ");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
            sql.append("  AND rms.shareTime <= :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append(" ORDER BY rms.shareTime DESC ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 
     * (特色资源审核)<br>
     * 
     * @param resCode 资源编码
     * @param shareCheckLevel 分享审核中级别
     * @param modifier 更新者姓名
     * @param modifierIP 更新者IP
     * @param shareCheckStatus 分享审核中状态
     * @return
     */
    @Override
    public int updateShareCheckStatus(String resCode, Integer shareCheckLevel, String modifier, String modifierIP,
                    Integer shareCheckStatus) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp timeNow = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sqlParam.put("timeNow", timeNow);
        sql.append("UPDATE");
        sql.append("        res_media_special ");
        sql.append("    SET");
        sql.append("        shareCheckTime = :timeNow");
        sql.append("        ,shareCheckStatus = :shareCheckStatus");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :timeNow ");
        sql.append("        ,sysVersion = sysVersion+1 ");
        if (CoreConstants.CHECK_STATUS_CHECKED.toString().equals(shareCheckStatus.toString())) {
            sql.append("    ,shareLevel = :shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        sql.append("    WHERE");
        sql.append("        resCode = :resCode");
        sqlParam.put("resCode", resCode);
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifierIP", modifierIP);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (查询特色资源已通过审核信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectSpecialCheckedInfoPage(Map<String, Object> param) {
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        String resSpecialTypeL1 = MapUtils.getString(param, "resSpecialTypeL1");
        String resSpecialTypeL2 = MapUtils.getString(param, "resSpecialTypeL2");
        String makerOrgName = MapUtils.getString(param, "makerOrgName");
        String makerOrgCode = MapUtils.getString(param, "makerOrgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String resName = MapUtils.getString(param, "resName");
        String makerName = MapUtils.getString(param, "makerName");
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" SELECT");
        sql.append("        rms.resName");
        sql.append("        ,rms.resCode");
        sql.append("        ,rms.shareCheckStatus");
        sql.append("        ,type1.name AS resSpecialTypeL1Name");
        sql.append("        ,type2.name AS resSpecialTypeL2Name");
        sql.append("        ,rms.makerName");
        sql.append("        ,rms.makerOrgName");
        sql.append("        ,rms.shareCheckTime");
        sql.append("        ,rms.id");
        sql.append("        ,rms.resTypeL1");
        sql.append("        ,rms.shareLevel");
        sql.append("    FROM");
        sql.append("        res_media_special rms ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type type1 ");
        sql.append("            ON rms.resSpecialTypeL1 = type1.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type type2 ");
        sql.append("            ON rms.resSpecialTypeL2 = type2.code ");
        sql.append("    WHERE");
        sql.append("        rms.flagDelete = 0 and rms.resStatus = :resStatus ");
        sql.append("  and rms.shareCheckStatus = :shareCheckStatus ");
        sqlParam.put("shareCheckStatus", CoreConstants.CHECK_STATUS_CHECKED);// 已通过审核：20
        sqlParam.put("resStatus", CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        if (StringUtils.isNotEmpty(resSpecialTypeL1)) {
            sql.append("  AND rms.resSpecialTypeL1 = :resSpecialTypeL1");
            sqlParam.put("resSpecialTypeL1", resSpecialTypeL1);
        }
        if (StringUtils.isNotEmpty(resSpecialTypeL2)) {
            sql.append("  AND rms.resSpecialTypeL2 = :resSpecialTypeL2");
            sqlParam.put("resSpecialTypeL2", resSpecialTypeL2);
        }
        if (StringUtils.isNotEmpty(makerOrgName)) {
            sql.append("  AND rms.makerOrgName like :makerOrgName");
            sqlParam.put("makerOrgName", "%" + makerOrgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append("  AND rms.resName like :resName");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(makerName)) {
            sql.append("  AND rms.makerName like :makerName");
            sqlParam.put("makerName", "%" + makerName.trim() + "%");
        }
        // 校内管理员
        if (StringUtils.isNotEmpty(makerOrgCode)) {
            sql.append("  AND rms.makerOrgCode = :makerOrgCode");
            sqlParam.put("makerOrgCode", makerOrgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
            sql.append("  AND rms.shareCheckTime >= :startDate ");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
            sql.append("  AND rms.shareCheckTime <= :endDate ");
            sqlParam.put("endDate", endDate);
        }
        if (ObjectUtils.isNotNull(shareCheckLevel)) {
            sql.append("  AND rms.shareCheckLevel = :shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        sql.append(" ORDER BY rms.shareCheckTime DESC ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 
     * (查询特色资源已退回信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectSpecialBackedInfoPage(Map<String, Object> param) {
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        String resSpecialTypeL1 = MapUtils.getString(param, "resSpecialTypeL1");
        String resSpecialTypeL2 = MapUtils.getString(param, "resSpecialTypeL2");
        String makerOrgName = MapUtils.getString(param, "makerOrgName");
        String makerOrgCode = MapUtils.getString(param, "makerOrgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String resName = MapUtils.getString(param, "resName");
        String makerName = MapUtils.getString(param, "makerName");
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" SELECT");
        sql.append("        rms.resName");
        sql.append("        ,rms.resCode");
        sql.append("        ,rms.shareCheckStatus");
        sql.append("        ,type1.name AS resSpecialTypeL1Name");
        sql.append("        ,type2.name AS resSpecialTypeL2Name");
        sql.append("        ,rms.makerName");
        sql.append("        ,rms.makerOrgName");
        sql.append("        ,rms.shareCheckTime");
        sql.append("        ,rms.id");
        sql.append("        ,rms.resTypeL1");
        sql.append("        ,rms.shareLevel");
        sql.append("    FROM");
        sql.append("        res_media_special rms ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type type1 ");
        sql.append("            ON rms.resSpecialTypeL1 = type1.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type type2 ");
        sql.append("            ON rms.resSpecialTypeL2 = type2.code ");
        sql.append("    WHERE");
        sql.append("        rms.flagDelete = 0 and rms.resStatus = :resStatus ");
        sql.append("  and rms.shareCheckStatus = :shareCheckStatus ");
        sqlParam.put("shareCheckStatus", CoreConstants.CHECK_STATUS_UNAPPROVE);// 审核不通过
        sqlParam.put("resStatus", CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        if (StringUtils.isNotEmpty(resSpecialTypeL1)) {
            sql.append("  AND rms.resSpecialTypeL1 = :resSpecialTypeL1");
            sqlParam.put("resSpecialTypeL1", resSpecialTypeL1);
        }
        if (StringUtils.isNotEmpty(resSpecialTypeL2)) {
            sql.append("  AND rms.resSpecialTypeL2 = :resSpecialTypeL2");
            sqlParam.put("resSpecialTypeL2", resSpecialTypeL2);
        }
        if (StringUtils.isNotEmpty(makerOrgName)) {
            sql.append("  AND rms.makerOrgName like :makerOrgName");
            sqlParam.put("makerOrgName", "%" + makerOrgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append("  AND rms.resName like :resName");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(makerName)) {
            sql.append("  AND rms.makerName like :makerName");
            sqlParam.put("makerName", "%" + makerName.trim() + "%");
        }
        // 校内管理员
        if (StringUtils.isNotEmpty(makerOrgCode)) {
            sql.append("  AND rms.makerOrgCode = :makerOrgCode");
            sqlParam.put("makerOrgCode", makerOrgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
            sql.append("  AND rms.shareCheckTime >= :startDate ");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
            sql.append("  AND rms.shareCheckTime <= :endDate ");
            sqlParam.put("endDate", endDate);
        }
        if (ObjectUtils.isNotNull(shareCheckLevel)) {
            sql.append("  AND rms.shareCheckLevel = :shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        sql.append(" ORDER BY rms.shareCheckTime DESC ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 
     * (根据主键id获取)<br>
     * 
     * @param id 主键id
     * @return
     */
    @Override
    public Map<String, Object> selectById(Integer id) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        rms.id");
        sql.append("        ,rms.resCode");
        sql.append("        ,rms.resName");
        sql.append("        ,rms.resTypeL1");
        sql.append("        ,rms.resTypeL2");
        sql.append("        ,rms.resSpecialTypeL1");
        sql.append("        ,rms.resSpecialTypeL2");
        sql.append("        ,rms.objectId");
        sql.append("        ,rms.fileKey");
        sql.append("        ,rms.suffix");
        sql.append("        ,rms.resSize");
        sql.append("        ,rms.mediaDurationMS");
        sql.append("        ,rms.thumbnailPath");
        sql.append("        ,rms.lowPath");
        sql.append("        ,rms.highPath");
        sql.append("        ,rms.p2pPath");
        sql.append("        ,rms.coverPath");
        sql.append("        ,rms.trialTimeRate");
        sql.append("        ,rms.resStatus");
        sql.append("        ,rms.convertCompletedTime");
        sql.append("        ,rms.shareLevel");
        sql.append("        ,rms.shareTime");
        sql.append("        ,rms.shareCheckLevel");
        sql.append("        ,rms.shareCheckStatus");
        sql.append("        ,rms.makerOrgCode");
        sql.append("        ,rms.makerCode");
        sql.append("        ,rms.makerName");
        sql.append("        ,rms.makerOrgName");
        sql.append("        ,rms.makeTime");
        sql.append("        ,rms.downloadPoints");
        sql.append("        ,rms.flagAllowDownload");
        sql.append("        ,rms.flagAllowBrowse");
        sql.append("        ,rms.clickCount");
        sql.append("        ,rms.browseCount");
        sql.append("        ,rms.downloadCount");
        sql.append("        ,rms.referCount");
        sql.append("        ,rms.favoriteCount");
        sql.append("        ,rms.goodCount");
        sql.append("        ,rms.badCount");
        sql.append("        ,rms.commentCount");
        sql.append("        ,rms.resDesc");
        sql.append("        ,rms.flagHistoryShow");
        sql.append("        ,rms.lockStatus");
        sql.append("        ,rms.flagDelete");
        sql.append("        ,rms.accessPath");
        sql.append("        ,rms.tags");
        sql.append("        ,rms.shareCheckTime");
        sql.append("        ,rms.sysVersion");
        sql.append("        ,rms.bizVersion ");
        sql.append("        ,rst1.name AS resSpecialTypeL1Name ");
        sql.append("        ,rst2.name AS resSpecialTypeL2Name ");
        sql.append("    FROM");
        sql.append("        res_media_special AS rms ");
        sql.append("    left join share_res_special_type rst1 on rms.resSpecialTypeL1=rst1.code ");
        sql.append("    left join share_res_special_type rst2 on rms.resSpecialTypeL2=rst2.code ");
        sql.append("    WHERE");
        sql.append("        rms.id = :id ");
        // sql.append(" AND rms.flagDelete = :flagDelete ");
        sqlParam.put("id", id);
        // sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 
     * (新增或修改特色资源)<br>
     * 
     * @param entity 实体
     * @return
     */
    @Override
    public boolean add(ResMediaSpecial entity) {
        try {
            return super.saveOne(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 
     * (批量操作特色资源)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    @Override
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        sql.append("UPDATE");
        sql.append("        res_media_special ");
        sql.append("    SET");
        sql.append("        flagDelete = :flagDelete ");
        sql.append("        ,modifier = :modifier ");
        sql.append("        ,modifierIP = :modifierIP ");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id IN (" + ids + ") ");
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
     * (资源首页设置，查询特色资源列表)<br>
     * 
     * @param param
     * @param rows
     * @param page
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectPageByResSetting(Map<String, Object> param, Integer rows, Integer page) {
        String resSpecialTypeL1 = MapUtils.getString(param, "resSpecialTypeL1");
        String resSpecialTypeL2 = MapUtils.getString(param, "resSpecialTypeL2");
        String resName = MapUtils.getString(param, "resName");
        Integer shareLevel = MapUtils.getInteger(param, "shareLevel");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        sqlParam.put("shareCheckStatus", CoreConstants.CHECK_STATUS_CHECKED);
        sqlParam.put("resStatus", CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        sql.append("SELECT");
        sql.append("        t1.name AS resSpecialTypeL1Name");
        sql.append("        ,t2.name AS resSpecialTypeL2Name");
        sql.append("        ,rms.resName");
        sql.append("        ,rms.makerOrgName");
        sql.append("        ,rms.makeTime");
        sql.append("        ,rms.makerName");
        sql.append("        ,rms.resCode");
        sql.append("        ,rms.resTypeL1");
        sql.append("        ,rms.resSpecialTypeL1");
        sql.append("        ,rms.resSpecialTypeL2 ");
        sql.append("        ,rms.shareLevel ");
        sql.append("    FROM");
        sql.append("        res_media_special rms ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type t1 ");
        sql.append("            ON rms.resSpecialTypeL1 = t1.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_special_type t2 ");
        sql.append("            ON rms.resSpecialTypeL2 = t2.code ");
        sql.append("    WHERE");
        sql.append("        rms.flagDelete = :flagDelete ");
        if (ObjectUtils.isNotNull(shareLevel)) {
            sql.append("        AND rms.shareLevel = :shareLevel ");
            sqlParam.put("shareLevel", shareLevel);
        } else {
            sql.append("        AND rms.shareLevel IN (20,30) ");
        }
        sql.append("        AND rms.shareCheckStatus = :shareCheckStatus ");
        sql.append("        AND rms.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(resSpecialTypeL1)) {
            sql.append("        AND rms.resSpecialTypeL1 = :resSpecialTypeL1 ");
            sqlParam.put("resSpecialTypeL1", resSpecialTypeL1);
        }
        if (StringUtils.isNotEmpty(resSpecialTypeL2)) {
            sql.append("        AND rms.resSpecialTypeL2 = :resSpecialTypeL2 ");
            sqlParam.put("resSpecialTypeL2", resSpecialTypeL2);
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append("        AND rms.resName LIKE :resName ");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        sql.append(" ORDER BY rms.makeTime DESC ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

}
