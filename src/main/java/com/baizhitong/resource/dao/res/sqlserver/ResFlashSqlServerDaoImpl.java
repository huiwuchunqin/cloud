package com.baizhitong.resource.dao.res.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResFlashDao;
import com.baizhitong.resource.model.res.ResFlash;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * flash播放dao ResFlashSqlServerDaoImpl
 * 
 * @author creator gaow 2016年12月20日 上午9:59:18
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ResFlashSqlServerDaoImpl extends BaseSqlServerDao<ResFlash> implements ResFlashDao {
    /**
     * 查询flash ()<br>
     * 
     * @param resCode
     * @return
     */
    public ResFlash selectFlash(String resCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resCode", resCode);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 查询flash ()<br>
     * 
     * @param resId
     * @return
     */
    public ResFlash selectFlash(Integer resId) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", resId);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 查询flash ()<br>
     * 
     * @param param 查询条件
     * @param page 页码
     * @param rows 每页记录数
     * @return page
     */
    public Page selectFlash(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        Integer shareCheckStatus = MapUtils.getInteger(param, "shareCheckStatus");
        String uploadTimeStart = MapUtils.getString(param, "uploadTimeStart");
        String uploadTimeEnd = MapUtils.getString(param, "uploadTimeEnd");
        String checkTimeStart = MapUtils.getString(param, "checkTimeStart");
        String checkTimeEnd = MapUtils.getString(param, "checkTimeEnd");
        String shareTimeStart = MapUtils.getString(param, "shareTimeStart");
        String shareTimeEnd = MapUtils.getString(param, "shareTimeEnd");
        String resName = MapUtils.getString(param, "resName");
        Integer resStatus = MapUtils.getInteger(param, "resStatus");
        Integer shareLevel = MapUtils.getInteger(param, "shareLevel");
        Integer kpTagStatus = MapUtils.getInteger(param, "kpTagStatus");
        Integer tbcTagStatus = MapUtils.getInteger(param, "tbcTagStatus");
        String makerName = MapUtils.getString(param, "makerName");
        String orgName = MapUtils.getString(param, "orgName");
        String orgCode = MapUtils.getString(param, "orgCode");
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel");
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String sectionCodes = MapUtils.getString(param, "sectionCodes");
        String gradeCodes = MapUtils.getString(param, "gradeCodes");
        String subjectCodes = MapUtils.getString(param, "subjectCodes");
        String orderType = MapUtils.getString(param, "orderType");
        sql.append("select b.* from ( ");
        sql.append("SELECT");
        sql.append("        so.orgName");
        sql.append("        ,rf.id");
        sql.append("        ,rf.resCode");
        sql.append("        ,rf.resName");
        sql.append("        ,rf.resTypeL1");
        sql.append("        ,rf.resTypeL2");
        sql.append("        ,srt2.name AS resTypeL2Name");
        sql.append("        ,rf.objectId");
        sql.append("        ,rf.fileKey");
        sql.append("        ,rf.suffix");
        sql.append("        ,rf.resSize");
        sql.append("        ,rf.coverPath");
        sql.append("        ,rf.mediaDurationMS");
        sql.append("        ,rf.resStatus");
        sql.append("        ,rf.shareLevel");
        sql.append("        ,rf.shareTime");
        sql.append("        ,rf.shareCheckLevel");
        sql.append("        ,rf.shareCheckStatus");
        sql.append("        ,rf.shareCheckTime");
        sql.append("        ,rf.makerOrgCode");
        sql.append("        ,rf.makerCode");
        sql.append("        ,rf.makerName");
        sql.append("        ,rf.makerOrgName");
        sql.append("        ,rf.makeTime");
        sql.append("        ,rf.downloadPoints");
        sql.append("        ,rf.flagAllowDownload");
        sql.append("        ,rf.flagAllowBrowse");
        sql.append("        ,rf.clickCount");
        sql.append("        ,rf.browseCount");
        sql.append("        ,rf.downloadCount");
        sql.append("        ,rf.referCount");
        sql.append("        ,rf.favoriteCount");
        sql.append("        ,rf.goodCount");
        sql.append("        ,rf.badCount");
        sql.append("        ,rf.commentCount");
        sql.append("        ,rf.accepptableIndex");
        sql.append("        ,rf.resDesc");
        sql.append("        ,rf.flagHistoryShow");
        sql.append("        ,rf.flagDelete");
        sql.append("        ,rf.creator");
        sql.append("        ,rf.createTime");
        sql.append("        ,rf.creatorIP");
        sql.append("        ,rf.modifier");
        sql.append("        ,rf.modifyTime");
        sql.append("        ,rf.modifierIP");
        sql.append("        ,rf.sysVersion");
        sql.append("        ,rf.bizVersion");
        sql.append("        ,rf.tags");
        sql.append("        ,rf.tempID");
        sql.append("        ,rf.accessPath");
        sql.append("        ,scse.name AS sectionName");
        sql.append("        ,scs.name AS subjectName");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,sctv.name AS textbookVerName");
        sql.append("        ,(   SELECT");
        sql.append("            COUNT (*)   ");
        sql.append("        FROM");
        sql.append("            rel_res_tbc rrt   ");
        sql.append("        WHERE");
        sql.append("            rrt.resId = rf.id   ");
        sql.append("            AND rrt.resTypeL1 = rf.resTypeL1  ) AS tbcCount");
        sql.append("        ,(   SELECT");
        sql.append("            COUNT (*)   ");
        sql.append("        FROM");
        sql.append("            rel_res_kp rrk   ");
        sql.append("        WHERE");
        sql.append("            rrk.resId = rf.id   ");
        sql.append("            AND rrk.resTypeL1 = rf.resTypeL1  ) AS kpCount ");
        sql.append("    FROM");
        sql.append("        res_flash rf ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_section rrse ");
        sql.append("            ON rrse.resTypeL1 = rf.resTypeL1 ");
        sql.append("            AND rrse.resId = rf.id ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scse ");
        sql.append("            ON scse.code = rrse.sectionCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_subject rrs ");
        sql.append("            ON rrs.resTypeL1 = rf.resTypeL1 ");
        sql.append("            AND rrs.resId = rf.id ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            ON scs.code = rrs.subjectCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_grade rrg ");
        sql.append("            ON rrg.resTypeL1 = rf.resTypeL1 ");
        sql.append("            AND rrg.resId = rf.id ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = rrg.gradeCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_tbv rrt ");
        sql.append("            ON rrt.resTypeL1 = rf.resTypeL1 ");
        sql.append("            AND rrt.resId = rf.id ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_textbook_ver sctv ");
        sql.append("            ON sctv.code = rrt.tbvCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org so ");
        sql.append("            ON so.orgCode = rf.makerOrgCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l2 srt2 ");
        sql.append("            ON srt2.code = rf.resTypeL2 ");
        sql.append("            AND srt2.codeL1 = rf.resTypeL1 ");
        sql.append("    WHERE");
        sql.append("        rf.flagDelete=0");
        // 教研员登录
        if (StringUtils.isNotEmpty(sectionCodes)) {
            if (sectionCodes.contains(",")) {
                sectionCodes = "'" + sectionCodes.replace(",", "','") + "'";
            } else {
                sectionCodes = "'" + sectionCodes + "'";
            }
            sql.append(" AND rrsec.sectionCode IN (" + sectionCodes + ") ");
        }
        if (StringUtils.isNotEmpty(subjectCodes)) {
            if (subjectCodes.contains(",")) {
                subjectCodes = "'" + subjectCodes.replace(",", "','") + "'";
            } else {
                subjectCodes = "'" + subjectCodes + "'";
            }
            sql.append(" AND rrs.subjectCode IN (" + subjectCodes + ") ");
        }
        if (StringUtils.isNotEmpty(gradeCodes)) {
            if (gradeCodes.contains(",")) {
                gradeCodes = "'" + gradeCodes.replace(",", "','") + "'";
            } else {
                gradeCodes = "'" + gradeCodes + "'";
            }
            sql.append(" AND rrg.gradeCode IN (" + gradeCodes + ") ");
        }
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (shareCheckLevel != null) {
            sql.append(" and rf.shareCheckLevel =:shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        if (shareLevel != null && shareLevel != -1) {
            sql.append(" and rf.shareLevel =:shareLevel ");
            sqlParam.put("shareLevel", shareLevel);
        }
        if (shareCheckStatus != null && shareCheckStatus.intValue() != -1) {
            if (shareCheckStatus == 60) {
                sql.append(" and rf.shareCheckStatus is null ");
            } else {
                sql.append(" and rf.shareCheckStatus=:shareCheckStatus ");
            }
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        if (!StringUtils.isEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append(" and rrse.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" and  rrg.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" and rrs.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }

        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and so.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (!StringUtils.isEmpty(resName)) {
            sql.append(" and rf.resName like '%" + resName.trim() + "%'");
        }
        if (StringUtils.isNotEmpty(makerName)) {
            sql.append(" and rf.makerName like :makerName ");
            sqlParam.put("makerName", "%" + makerName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(resTypeL2) && !"-1".equals(resTypeL2)) {
            sql.append(" and rf.resTypeL2 = :resTypeL2 ");
            sqlParam.put("resTypeL2", resTypeL2);
        }
        if (!StringUtils.isEmpty(orgCode)) {
            sql.append(" and rf.makerOrgCode =:orgCode");
            sqlParam.put("orgCode", orgCode);
        }
        if (!StringUtils.isEmpty(uploadTimeStart)) {
            sql.append(" and rf.makeTime >=:uploadTimeStart ");
            sqlParam.put("uploadTimeStart", uploadTimeStart);
        }
        if (!StringUtils.isEmpty(uploadTimeEnd)) {
            sql.append(" and rf.makeTime<=:uploadTimeEnd ");
            sqlParam.put("uploadTimeEnd", uploadTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(shareTimeStart)) {
            sql.append(" and rf.shareTime >=:shareTimeStart ");
            sqlParam.put("shareTimeStart", shareTimeStart);
        }
        if (!StringUtils.isEmpty(shareTimeEnd)) {
            sql.append(" and rf.shareTime<=:shareTimeEnd ");
            sqlParam.put("shareTimeEnd", shareTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(checkTimeStart)) {
            sql.append(" and rf.shareCheckTime >=:checkTimeStart ");
            sqlParam.put("checkTimeStart", checkTimeStart);
        }
        if (!StringUtils.isEmpty(checkTimeEnd)) {
            sql.append(" and rf.shareCheckTime<=:checkTimeEnd ");
            sqlParam.put("checkTimeEnd", checkTimeEnd + " 23:59:59");
        }
        if (resStatus != null && resStatus != -1) {
            sql.append(" and rf.resStatus =:resStatus ");
            sqlParam.put("resStatus", resStatus);
        }

        sql.append(" )b where 1=1  ");
        if (kpTagStatus != null) {
            if (kpTagStatus == 1) {
                sql.append(" and b.kpCount >0 ");
            } else if (kpTagStatus == 2) {
                sql.append(" and b.kpCount =0 ");
            }
        }
        if (tbcTagStatus != null) {
            if (tbcTagStatus == 1) {
                sql.append(" and b.tbcCount >0 ");
            } else if (tbcTagStatus == 2) {
                sql.append(" and b.tbcCount =0 ");
            }
        }
        if (StringUtils.isNotEmpty(orderType)) {
            sql.append("  order by b.makeTime desc");
        } else if (shareCheckStatus == 10) {
            sql.append("  order by b.shareTime desc");
        } else {
            sql.append("  order by b.shareCheckTime desc ");
        }
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }

    /**
     * 查询flash ()<br>
     * 
     * @param id 主键
     * @return map
     */
    public Map<String, Object> getFlash(String resCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        so.orgName");
        sql.append("        ,so.orgCode");
        sql.append("        ,rf.id");
        sql.append("        ,rf.resCode");
        sql.append("        ,rf.resName");
        sql.append("        ,rf.resTypeL1");
        sql.append("        ,rf.resTypeL2");
        sql.append("        ,rf.objectId");
        sql.append("        ,rf.fileKey");
        sql.append("        ,rf.suffix");
        sql.append("        ,rf.resSize");
        sql.append("        ,rf.coverPath");
        sql.append("        ,rf.mediaDurationMS");
        sql.append("        ,rf.resStatus");
        sql.append("        ,rf.shareLevel");
        sql.append("        ,rf.shareTime");
        sql.append("        ,rf.shareCheckLevel");
        sql.append("        ,rf.shareCheckStatus");
        sql.append("        ,rf.shareCheckTime");
        sql.append("        ,rf.makerOrgCode");
        sql.append("        ,rf.makerCode");
        sql.append("        ,rf.makerName");
        sql.append("        ,rf.makerOrgName");
        sql.append("        ,rf.makeTime");
        sql.append("        ,rf.downloadPoints");
        sql.append("        ,rf.flagAllowDownload");
        sql.append("        ,rf.flagAllowBrowse");
        sql.append("        ,rf.clickCount");
        sql.append("        ,rf.browseCount");
        sql.append("        ,rf.downloadCount");
        sql.append("        ,rf.referCount");
        sql.append("        ,rf.favoriteCount");
        sql.append("        ,rf.goodCount");
        sql.append("        ,rf.badCount");
        sql.append("        ,rf.commentCount");
        sql.append("        ,rf.accepptableIndex");
        sql.append("        ,rf.resDesc");
        sql.append("        ,rf.flagHistoryShow");
        sql.append("        ,rf.flagDelete");
        sql.append("        ,rf.creator");
        sql.append("        ,rf.createTime");
        sql.append("        ,rf.creatorIP");
        sql.append("        ,rf.modifier");
        sql.append("        ,rf.modifyTime");
        sql.append("        ,rf.modifierIP");
        sql.append("        ,rf.sysVersion");
        sql.append("        ,rf.bizVersion");
        sql.append("        ,rf.tags");
        sql.append("        ,rf.tempID");
        sql.append("        ,rf.accessPath");
        sql.append("        ,scse.name AS sectionName");
        sql.append("        ,scs.name AS subjectName");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,sctv.name AS textbookVerName");
        sql.append("    FROM");
        sql.append("        res_flash rf ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_section rrse ");
        sql.append("            ON rrse.resTypeL1 = rf.resTypeL1 ");
        sql.append("            AND rrse.resId = rf.id ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scse ");
        sql.append("            ON scse.code = rrse.sectionCode ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_subject rrs ");
        sql.append("            ON rrs.resTypeL1 = rf.resTypeL1 ");
        sql.append("            AND rrs.resId = rf.id ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            ON scs.code = rrs.subjectCode ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_grade rrg ");
        sql.append("            ON rrg.resTypeL1 = rf.resTypeL1 ");
        sql.append("            AND rrg.resId = rf.id ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = rrg.gradeCode ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_tbv rrt ");
        sql.append("            ON rrt.resTypeL1 = rf.resTypeL1 ");
        sql.append("            AND rrt.resId = rf.id ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_textbook_ver sctv ");
        sql.append("            ON sctv.code = rrt.tbvCode ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org so ");
        sql.append("            ON so.orgCode = rf.makerOrgCode ");
        sql.append("    WHERE");
        sql.append("        rf.flagDelete = :flagDelete ");
        sql.append("        AND rf.resCode =:resCode");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resCode", resCode);
        param.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUniqueBySql(sql.toString(), param);
    }

    /**
     * 保存flash ()<br>
     * 
     * @param flash
     */
    public void save(ResFlash flash) {
        try {
            super.saveOne(flash);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    /**
     * 删除flash ()<br>
     * 
     * @param ids
     * @return
     */
    public int deleteFlash(String ids, String modifier, String modifierIP) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        res_flash ");
        sql.append("    SET");
        sql.append("        flagDelete = :flagDelete");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id ");
        sql.append("    IN (" + ids + ") ");
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_YES);
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifierIP", modifierIP);
        sqlParam.put("modifyTime", currentTime);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (批量操作flash)<br>
     * 
     * @param ids flashId集合
     * @param operateType 操作类型
     * @param userName 用户姓名
     * @param ip IP地址
     * @return
     */
    @Override
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", currentTime);
        sql.append("UPDATE");
        sql.append("        res_flash ");
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

}
