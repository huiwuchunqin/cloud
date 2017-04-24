package com.baizhitong.resource.dao.test.sqlServer;

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
import com.baizhitong.resource.dao.test.TestDao;
import com.baizhitong.resource.model.test.Test;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

@Component
public class TestDaoImpl extends BaseSqlServerDao<Test> implements TestDao {

    /**
     * 查询试卷 ()<br>
     * 
     * @param testCode
     * @return
     */
    public Test findByTestCode(String testCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("testCode", testCode);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 更新试卷审核状态 <br>
     * 
     * @param testCode 试卷编码
     * @param shareCheckStatus 审核状态
     * @param modifierIP 更新者IP
     * @param modifier 更新者姓名
     * @return 更新记录数
     */
    public int updateCheckStatus(String testCode, String shareCheckStatus, String modifierIP, String modifier) {
        Timestamp currentTime=DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        StringBuffer sql=new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        test ");
        sql.append("    SET");
        sql.append("        shareCheckStatus = :shareCheckStatus");
        //审核通过
        if (CoreConstants.CHECK_STATUS_CHECKED.toString().equals(shareCheckStatus)) {
            sql.append("        ,shareLevel = shareCheckLevel");
        }
        sql.append("        ,shareCheckTime = :shareCheckTime");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifyTime = :modifyTime");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,sysVersion = sysVersion + 1 ");
        sql.append("    WHERE");
        sql.append("        testCode = :testCode ");        //SQL参数
        sqlParam.put("testCode", testCode);
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        sqlParam.put("shareCheckTime", currentTime);
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifyTime", currentTime);
        sqlParam.put("modifierIP", modifierIP);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 查询试卷信息 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getTestPage(Map<String, Object> param, Integer page, Integer rows) {
        // 查询条件
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        Integer shareCheckStatus = MapUtils.getInteger(param, "shareCheckStatus");
        String uploadTimeStart = MapUtils.getString(param, "uploadTimeStart");
        String uploadTimeEnd = MapUtils.getString(param, "uploadTimeEnd");
        String checkTimeStart = MapUtils.getString(param, "checkTimeStart");
        String checkTimeEnd = MapUtils.getString(param, "checkTimeEnd");
        String resName = MapUtils.getString(param, "resName");
        Integer shareLevel = MapUtils.getInteger(param, "shareLevel");
        Integer tbcTagStatus = MapUtils.getInteger(param, "tbcTagStatus");
        String shareTimeStart = MapUtils.getString(param, "shareTimeStart");
        String shareTimeEnd = MapUtils.getString(param, "shareTimeEnd");
        String userName = MapUtils.getString(param, "userName");
        String orgName = MapUtils.getString(param, "orgName");
        String orgCode = MapUtils.getString(param, "orgCode");
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String orderType = MapUtils.getString(param, "orderType");
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel");
        String sectionCodes = MapUtils.getString(param, "sectionCodes");
        String gradeCodes = MapUtils.getString(param, "gradeCodes");
        String subjectCodes = MapUtils.getString(param, "subjectCodes");
        // sql语句
        StringBuffer sql = new StringBuffer();
        sql.append("select b.* from ( ");
        sql.append("SELECT");
        sql.append("		a.id			");
        sql.append("        ,a.testCode");
        sql.append("        ,a.testName");
        sql.append("        ,a.testCode as resCode");
        sql.append("        ,a.sectionCode");
        sql.append("        ,a.subjectCode");
        sql.append("        ,a.gradeCode");
        sql.append("        ,a.termCode");
        sql.append("        ,a.shareCheckTime");
        sql.append("        ,a.resTypeL1");
        sql.append("        ,a.resTypeL2");
        sql.append("        ,a.resDesc");
        sql.append("        ,a.makerType");
        sql.append("        ,a.makerOrgCode");
        sql.append("        ,a.makerOrgName");
        sql.append("        ,a.makerCode");
        sql.append("        ,a.makerName");
        sql.append("        ,a.makeTime");
        sql.append("        ,a.orgCode");
        sql.append("        ,a.userCode");
        sql.append("        ,a.userName");
        sql.append("        ,a.difficultyCode");
        sql.append("        ,a.shareLevel");
        sql.append("        ,a.shareCheckLevel");
        sql.append("        ,a.shareCheckStatus");
        sql.append("        ,a.downloadPoints");
        sql.append("        ,a.browseCount");
        sql.append("        ,a.downloadCount");
        sql.append("        ,a.referCount");
        sql.append("        ,a.favoriteCount");
        sql.append("        ,a.goodCount");
        sql.append("        ,a.badCount");
        sql.append("        ,a.commentCount");
        sql.append("        ,a.answerCount");
        sql.append("        ,a.memo");
        sql.append("        ,a.flagDelete");
        sql.append("        ,a.clickCount");
        sql.append("        ,a.studyYearCode");
        sql.append("        ,a.shareTime");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,scsec.name AS sectionName");
        sql.append("        ,scs.name AS subjectName");
        sql.append("        ,srtl.name AS resTypeL2Name  ");
        sql.append(",(");
        sql.append("        select");
        sql.append("            count(*) ");
        sql.append("        FROM");
        sql.append("            rel_exercise_chapter rec ");
        sql.append("        WHERE");
        sql.append("            rec.testCode=a.testCode");
        sql.append("    ) as tbcCount");
        sql.append("    FROM");
        sql.append("        test a  ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_exercise_section res ");
        sql.append("            ON res.testCode = a.testCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scsec ");
        sql.append("            ON scsec.code = res.sectionCode  ");
        sql.append("            AND scsec.flagDelete = 0  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            ON scs.code = a.subjectCode  ");
        sql.append("            AND scs.flagDelete = 0  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_exercise_grade reg ");
        sql.append("            ON reg.testCode = a.testCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = reg.gradeCode  ");
        sql.append("            AND scg.flagDelete = 0  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org so ");
        sql.append("            ON so.orgCode = a.makerOrgCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l2 srtl ");
        sql.append("            ON srtl.code = a.resTypeL2  ");
        sql.append("            AND srtl.codeL1 = a.resTypeL1  ");
        sql.append(" where a.flagDelete=0  and a.scenarioType in(10,20,40) ");
        // 查询条件参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // 教研员登录
        if (StringUtils.isNotEmpty(sectionCodes)) {
            if (sectionCodes.contains(",")) {
                sectionCodes = "'" + sectionCodes.replace(",", "','") + "'";
            } else {
                sectionCodes = "'" + sectionCodes + "'";
            }
            sql.append(" AND res.sectionCode IN (" + sectionCodes + ") ");
        }
        if (StringUtils.isNotEmpty(subjectCodes)) {
            if (subjectCodes.contains(",")) {
                subjectCodes = "'" + subjectCodes.replace(",", "','") + "'";
            } else {
                subjectCodes = "'" + subjectCodes + "'";
            }
            sql.append(" AND a.subjectCode IN (" + subjectCodes + ") ");
        }
        if (StringUtils.isNotEmpty(gradeCodes)) {
            if (gradeCodes.contains(",")) {
                gradeCodes = "'" + gradeCodes.replace(",", "','") + "'";
            } else {
                gradeCodes = "'" + gradeCodes + "'";
            }
            sql.append(" AND reg.gradeCode IN (" + gradeCodes + ") ");
        }
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" and  reg.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" and a.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (!StringUtils.isEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append(" and res.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and so.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and a.makerName like :userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append(" and   a.testName like :resName ");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(resTypeL2) && !"-1".equals(resTypeL2)) {
            sql.append(" and a.resTypeL2 = :resTypeL2 ");
            sqlParam.put("resTypeL2", resTypeL2);
        }
        if (shareCheckStatus != null && shareCheckStatus.intValue() != -1) {
            if (shareCheckStatus == 60) {
                sql.append(" and a.shareCheckStatus is null ");
            } else {
                sql.append(" and a.shareCheckStatus=:shareCheckStatus ");
            }
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        if (shareCheckLevel != null) {
            sql.append(" and a.shareCheckLevel =:shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        if (!StringUtils.isEmpty(orgCode)) {
            sql.append(" and a.makerOrgCode =:orgCode");
            sqlParam.put("orgCode", orgCode);
        }
        if (!StringUtils.isEmpty(uploadTimeStart)) {
            sql.append(" and a.makeTime >=:uploadTimeStart ");
            sqlParam.put("uploadTimeStart", uploadTimeStart);
        }
        if (!StringUtils.isEmpty(uploadTimeEnd)) {
            sql.append(" and a.makeTime<=:uploadTimeEnd ");
            sqlParam.put("uploadTimeEnd", uploadTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(shareTimeStart)) {
            sql.append(" and a.shareTime >=:shareTimeStart ");
            sqlParam.put("shareTimeStart", shareTimeStart);
        }
        if (!StringUtils.isEmpty(shareTimeEnd)) {
            sql.append(" and a.shareTime<=:shareTimeEnd ");
            sqlParam.put("shareTimeEnd", shareTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(checkTimeStart)) {
            sql.append(" and a.shareCheckTime >=:checkTimeStart ");
            sqlParam.put("checkTimeStart", checkTimeStart);
        }
        if (!StringUtils.isEmpty(checkTimeEnd)) {
            sql.append(" and a.shareCheckTime<=:checkTimeEnd ");
            sqlParam.put("checkTimeEnd", checkTimeEnd + " 23:59:59");
        }
        if (shareLevel != null && shareLevel != -1) {
            sql.append(" and a.shareLevel =:shareLevel ");
            sqlParam.put("shareLevel", shareLevel);
        }
        sql.append(" )b where 1=1  ");
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
     * 删除试卷 ()<br>
     * 
     * @param ids
     * @return
     */
    public int deleteTest(String ids, String modifier, String modifierIP) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        exercise ");
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
     * (批量操作测验)<br>
     * 
     * @param ids
     * @param operateType
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
        sql.append("        exercise ");
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
