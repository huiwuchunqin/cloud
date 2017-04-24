package com.baizhitong.resource.dao.res.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResAudioDao;
import com.baizhitong.resource.model.res.ResAudio;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * ResAudioDaoImpl 音频资源dao
 * 
 * @author creator gaow 2017年3月29日 上午10:05:27
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ResAudioDaoImpl extends BaseSqlServerDao<ResAudio> implements ResAudioDao {
    
    /**
     * ()<br>
     * 
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getAudioById(Integer id) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        ra.*");
        sql.append("        ,scg.name AS gradeName ");
        sql.append("        ,scs.name AS sectionName ");
        sql.append("        ,scs2.name AS subjectName ");
        sql.append("        ,org.orgName ");
        sql.append("        ,org.orgCode ");
        sql.append("    FROM");
        sql.append("        res_audio ra ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org org ");
        sql.append("            ON ra.makerOrgCode = org.orgCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_grade rrg ");
        sql.append("            ON ra.id = rrg.resId ");
        sql.append("            AND ra.resTypeL1 = rrg.resTypeL1 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON rrg.gradeCode = scg.code ");
        sql.append("            AND scg.flagDelete = 0 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_section rrs ");
        sql.append("            ON ra.id = rrs.resId ");
        sql.append("            AND ra.resTypeL1 = rrs.resTypeL1 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON rrs.sectionCode = scs.code ");
        sql.append("            AND scs.flagDelete = 0 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_subject rrs2 ");
        sql.append("            ON ra.id = rrs2.resId ");
        sql.append("            AND ra.resTypeL1 = rrs2.resTypeL1 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs2 ");
        sql.append("            ON rrs2.subjectCode = scs2.code ");
        sql.append("            AND scs2.flagDelete = 0 ");
        sql.append("            ");
        sql.append("    WHERE 1=1 ");
        /* sql.append("        ra.flagDelete = 0"); */ // 资源回收站也需要查
        sql.append("       and ra.id=:id  ");
        Map<String, Object> sqlParamMap = new HashMap<String, Object>();
        sqlParamMap.put("id", id);
        List<Map<String, Object>> mapList = super.findBySql(sql.toString(), sqlParamMap);
        if (mapList != null && mapList.size() > 0) {
            return mapList.get(0);
        } else {
            return null;
        }
    }

    /**
     * ()<br>
     * 
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getAllAudioInfo(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        // 查询参数
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
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
        String userName = MapUtils.getString(param, "userName");
        String orgName = MapUtils.getString(param, "orgName");
        String orgCode = MapUtils.getString(param, "orgCode");
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel");
        String orderType = MapUtils.getString(param, "orderType");
        Integer resStatusSuccess = MapUtils.getInteger(param, "resStatusSuccess");
        String sectionCodes = MapUtils.getString(param, "sectionCodes");
        String gradeCodes = MapUtils.getString(param, "gradeCodes");
        String subjectCodes = MapUtils.getString(param, "subjectCodes");
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append("select b.* from ( ");
        sql.append(" select ra.*,so.orgName, scsec.name as sectionName,scs.name as subjectName,scg.name as gradeName,srtl.name as resTypeL2Name, ");
        sql.append(" (select count (*)   from rel_res_tbc  rrt where rrt.resId=ra.id and rrt.resTypeL1=ra.resTypeL1) as tbcCount ,");
        sql.append(" (select count (*)  from rel_res_kp  rrk where rrk.resId=ra.id and rrk.resTypeL1=ra.resTypeL1) as kpCount  ");
        sql.append(" FROM res_audio ra ");
        sql.append(" LEFT JOIN rel_res_section rrsec ON rrsec.resTypeL1 = ra.resTypeL1 AND rrsec.resId = ra.id ");
        sql.append(" LEFT JOIN share_code_section scsec ON scsec.code = rrsec.sectionCode and scsec.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = ra.resTypeL1 AND rrs.resId = ra.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode  and scs.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = ra.resTypeL1 AND rrg.resId = ra.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode and scg.flagDelete=0 ");
        sql.append(" LEFT JOIN share_org so ON so.orgCode = ra.makerOrgCode ");
        sql.append(" LEFT JOIN share_res_type_l2  srtl  ON srtl.code = ra.resTypeL2 and srtl.codeL1=ra.resTypeL1 ");
        sql.append(" WHERE ra.flagDelete=0 and useType=10 ");
        // 查询条件参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
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
        if (StringUtils.isNotEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" and  rrg.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" and rrs.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append(" and rrsec.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and so.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and ra.makerName like :userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (shareCheckStatus != null && shareCheckStatus.intValue() != -1) {
            if (shareCheckStatus == 60) {
                sql.append(" and (ra.shareCheckStatus is null or ra.shareCheckStatus='') ");
            } else {
                sql.append(" and ra.shareCheckStatus=:shareCheckStatus ");
            }
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        if (shareCheckLevel != null) {
            sql.append(" and ra.shareCheckLevel=:shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        if (!StringUtils.isEmpty(resName)) {
            sql.append(" and ra.resName like '%" + resName.trim() + "%'");
        }
        if (!StringUtils.isEmpty(uploadTimeStart)) {
            sql.append(" and ra.makeTime  >=:uploadTimeStart ");
            sqlParam.put("uploadTimeStart", uploadTimeStart);
        }
        if (!StringUtils.isEmpty(uploadTimeEnd)) {
            sql.append(" and ra.makeTime <=:uploadTimeEnd ");
            sqlParam.put("uploadTimeEnd", uploadTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(checkTimeStart)) {
            sql.append(" and ra.shareCheckTime >=:checkTimeStart ");
            sqlParam.put("checkTimeStart", checkTimeStart);
        }
        if (!StringUtils.isEmpty(checkTimeEnd)) {
            sql.append(" and ra.shareCheckTime<=:checkTimeEnd ");
            sqlParam.put("checkTimeEnd", checkTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(shareTimeStart)) {
            sql.append(" and ra.shareTime >=:shareTimeStart ");
            sqlParam.put("shareTimeStart", shareTimeStart);
        }
        if (!StringUtils.isEmpty(shareTimeEnd)) {
            sql.append(" and ra.shareTime<=:shareTimeEnd ");
            sqlParam.put("shareTimeEnd", shareTimeEnd + " 23:59:59");
        }
        if (resStatus != null && resStatus != -1) {
            sql.append(" and ra.resStatus =:resStatus ");
            sqlParam.put("resStatus", resStatus);
        }
        if (resStatusSuccess != null) {
            sql.append(" and ra.resStatus = :resStatusSuccess ");
            sqlParam.put("resStatusSuccess", resStatusSuccess);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and ra.makerOrgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (shareLevel != null && shareLevel != -1) {
            sql.append(" and ra.shareLevel =:shareLevel ");
            sqlParam.put("shareLevel", shareLevel);
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

        return super.queryDistinctPage(sql.toString(), sqlParam, pageNo, pageSize);
    }

    /**
     * 查询音频资源 ()<br>
     * 
     * @param resCode
     * @return
     */
    public ResAudio getAudio(String resCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resCode", resCode);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 查询音频资源 ()<br>
     * 
     * @param resCode
     * @return
     */
    public ResAudio select(Integer resId) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", resId);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 更新音频资源 ()<br>
     * 
     * @param audio
     */
    public void updateAudio(ResAudio audio) {
        try {
            super.saveOne(audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除音频 ()<br>
     * 
     * @param ids
     *        视频ids
     * @return
     */
    public int deleteAudio(String ids, String modifier, String modifierIP) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        res_audio ");
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
}
