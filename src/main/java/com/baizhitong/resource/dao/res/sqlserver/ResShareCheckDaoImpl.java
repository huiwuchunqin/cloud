package com.baizhitong.resource.dao.res.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.dao.res.ResShareCheckDao;
import com.baizhitong.resource.model.res.ResShareCheck;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

@Repository
public class ResShareCheckDaoImpl extends BaseSqlServerDao<ResShareCheck> implements ResShareCheckDao {

    /**
     * 
     * 查询审核列表
     * 
     * @param param
     * @return
     */
    public Page selectPage(Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql参数
        Integer resTypeL1 = MapUtils.getInteger(param, "resTypeL1"); // 资源一级分类
        String resTypeL2 = MapUtils.getString(param, "resTypeL2"); // 资源二级分类
        String resName = MapUtils.getString(param, "resName"); // 资源名称
        String userName = MapUtils.getString(param, "userName"); // 作者
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel"); // 分享审核中级别
        Integer shareCheckStatus = MapUtils.getInteger(param, "shareCheckStatus"); // 分享审核中状态
        Integer pageNo = MapUtils.getInteger(param, "page"); // 页码
        Integer pageSize = MapUtils.getInteger(param, "rows"); // 每页记录数
        String applierOrgCode = MapUtils.getString(param, "applierOrgCode"); // 申请人机构
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String orgName = MapUtils.getString(param, "orgName");
        /*-----------------SQL语句-------------------------*/
        sql.append("SELECT");
        sql.append("        rsc.id");
        sql.append("        ,rsc.resCode");
        sql.append("        ,rsc.resTypeL1");
        sql.append("        ,rsc.resTypeL2");
        sql.append("        ,rsc.resName");
        sql.append("        ,rsc.resDesc");
        sql.append("        ,rsc.resAccessPath");
        sql.append("        ,rsc.subjectCode");
        sql.append("        ,rsc.shareLevel");
        sql.append("        ,rsc.shareCheckLevel");
        sql.append("        ,rsc.shareCheckStatus");
        sql.append("        ,rsc.applierCode");
        sql.append("        ,rsc.applierName");
        sql.append("        ,rsc.applierOrgCode");
        sql.append("        ,rsc.applierOrgName");
        sql.append("        ,rsc.applyTime");
        sql.append("        ,rsc.applyReason");
        sql.append("        ,rsc.checkerCode");
        sql.append("        ,rsc.checkerName");
        sql.append("        ,rsc.checkerOrgCode");
        sql.append("        ,rsc.checkerOrgName");
        sql.append("        ,rsc.checkTime");
        sql.append("        ,rsc.checkComments");
        sql.append("        ,rsc.memo");
        sql.append("        ,rsc.flagDelete");
        sql.append("        ,rsc.modifier");
        sql.append("        ,rsc.modifyTime");
        sql.append("        ,rsc.modifierIP ");
        sql.append("        ,strl1.name as resTypeL1Name ");
        sql.append("        ,strl2.name as resTypeL2Name ");
        sql.append("        ,scs.name as sectionName ");
        sql.append("        ,scs2.name as subjectName ");
        sql.append("        ,scg.name as gradeName ");
        sql.append("    FROM");
        sql.append("        res_share_check AS rsc ");
        sql.append("    LEFT JOIN share_res_type_l1 strl1 on  strl1.code= rsc.resTypeL1");
        sql.append("    LEFT JOIN share_res_type_l2 strl2 on strl2.code= rsc.resTypeL2 and strl2.codeL1=rsc.resTypeL1");
        sql.append("    LEFT JOIN share_code_section scs on scs.code= rsc.sectionCode ");
        sql.append("    LEFT JOIN share_code_subject scs2 on scs2.code= rsc.subjectCode ");
        sql.append("    LEFT JOIN share_code_grade scg on scg.code= rsc.gradeCode ");
        sql.append("    WHERE 1=1   and rsc.flagDelete=0  and resTypeL1=60 ");
        if (resTypeL1 != null) {
            sql.append("    AND  rsc.resTypeL1=:resTypeL1    ");
            sqlParam.put("resTypeL1", resTypeL1);
        }
        if (StringUtils.isNotEmpty(resTypeL2)) {
            sql.append("    AND  rsc.resTypeL2=:resTypeL2    ");
            sqlParam.put("resTypeL2", resTypeL2);
        }
        if (shareCheckLevel != null) {
            sql.append("    AND  rsc.shareCheckLevel=:shareCheckLevel");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        if (StringUtils.isNotEmpty(applierOrgCode)) {
            sql.append("    AND  rsc.applierOrgCode=:applierOrgCode");
            sqlParam.put("applierOrgCode", applierOrgCode);
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append("    AND  rsc.resName LIKE :resName  ");
            sqlParam.put("resName", "%" + resName + "%");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("    AND  rsc.makerName LIKE :userName  ");
            sqlParam.put("userName", "%" + userName + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND  rsc.makerOrgName LIKE :orgName  ");
            sqlParam.put("orgName", "%" + orgName + "%");
        }
        if (shareCheckStatus != null) {
            sql.append("    AND  rsc.shareCheckStatus=:shareCheckStatus ");
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        if (StringUtils.isNotEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append("    AND  rsc.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(gradeCode) && !"-1".equals(sectionCode)) {
            sql.append("    AND  rsc.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(subjectCode) && !"-1".equals(sectionCode)) {
            sql.append("    AND  rsc.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        sql.append("    ORDER BY");
        sql.append("        rsc.applyTime desc");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNo, pageSize);

    }

    /**
     * 
     * 更新资源审核状态
     * 
     * @param id 资源id
     * @param resTypeL1 资源一级分类
     * @param shareCheckStatus 审核状态
     * @param userInfoVo 用户信息
     * @param ip ip地址
     * @return
     */
    public int updateShareCheckStatus(Integer id, Integer resTypeL1, Integer shareCheckStatus, UserInfoVo userInfoVo,
                    String ip, String checkComments) {
        StringBuffer sql = new StringBuffer();
        sql.append(" update res_share_check set shareCheckStatus=:shareCheckStatus ");
        sql.append(" ,checkerCode=:checkerCode  ");
        sql.append(" ,checkerName=:checkerName  ");
        sql.append(" ,checkerOrgCode=:checkerOrgCode  ");
        sql.append(" ,checkerOrgName=:checkerOrgName  ");
        sql.append(" ,checkTime=:checkTime  ");
        sql.append(" ,checkComments=:checkComments  ");
        sql.append(" ,modifier=:modifier  ");
        sql.append(" ,modifierIP=:modifierIP  ");
        sql.append(" ,modifyTime=:modifyTime  ");
        sql.append(" where id=:id  ");
        sql.append(" and  resTypeL1=:resTypeL1  ");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("id", id);
        sqlParam.put("resTypeL1", resTypeL1);
        sqlParam.put("checkComments", checkComments);
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        sqlParam.put("checkerCode", userInfoVo.getUserCode());
        sqlParam.put("checkerName", userInfoVo.getUserName());
        sqlParam.put("checkerOrgCode", userInfoVo.getOrgCode());
        sqlParam.put("checkerOrgName", userInfoVo.getOrgName());
        sqlParam.put("checkTime", new Date());
        sqlParam.put("modifier", userInfoVo.getUserName());
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", new Date());
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * 更新消息审核表
     * 
     * @param resCode 资源编码
     * @param resCheckTime 申请审核时间
     * @param shareCheckStatus 审核状态
     * @param userInfoVo 审核用户信息
     * @param ip ip地址
     * @param checkComments 审核意见
     * @return
     */
    public int updateShareCheckStatusNew(String resCode, Timestamp resCheckTime, String shareCheckStatus,
                    UserInfoVo userInfoVo, String ip, String checkComments) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" update res_share_check set shareCheckStatus=:shareCheckStatus ");
        sql.append(" ,checkerCode=:checkerCode  ");
        sql.append(" ,checkerName=:checkerName  ");
        sql.append(" ,checkerOrgCode=:checkerOrgCode  ");
        sql.append(" ,checkerOrgName=:checkerOrgName  ");
        sql.append(" ,checkTime=:checkTime  ");
        if(StringUtils.isNotEmpty(checkComments)){
            sql.append(" ,checkComments=:checkComments  ");
            sqlParam.put("checkComments", checkComments);
        }
        sql.append(" ,modifier=:modifier  ");
        sql.append(" ,modifierIP=:modifierIP  ");
        sql.append(" ,modifyTime=:modifyTime  ");
        sql.append(" where resCode=:resCode  and shareCheckStatus=10 and flagDelete=0 ");
        /* sql.append(" and  applyTime=:applyTime  "); */
        sqlParam.put("resCode", resCode);
        /* sqlParam.put("applyTime", resCheckTime); */
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        sqlParam.put("checkerCode", userInfoVo.getUserCode());
        sqlParam.put("checkerName", userInfoVo.getUserName());
        sqlParam.put("checkerOrgCode", userInfoVo.getOrgCode());
        sqlParam.put("checkerOrgName", userInfoVo.getOrgName());
        sqlParam.put("checkTime", new Timestamp(new Date().getTime()));
        sqlParam.put("modifier", userInfoVo.getUserName());
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * 保存资源分享审核资源
     * 
     * @param resShareCheck
     * @return
     */
    public boolean save(ResShareCheck resShareCheck) throws Exception {
        return super.saveOne(resShareCheck);
    }

    /**
     * 查询资源 ()<br>
     * 
     * @param resCode
     * @return
     */
    public List<ResShareCheck> getRes(String resCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resCode", resCode);
        qr.andEqual("flagDelete", 0);
        qr.addDescOrder("applyTime");
        return super.find(qr);
    }

    /**
     * 查询资源 ()<br>
     * 
     * @param resCode
     * @return
     */
    public List<ResShareCheck> getResByType(Integer resTypeL1) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resTypeL1", resTypeL1);
        qr.andEqual("flagDelete", 0);
        qr.addDescOrder("applyTime");
        return super.find(qr);
    }

    /**
     * 查询资源 ()<br>
     * 
     * @param resTypeL1
     * @param resCode
     * @return
     */
    public List<ResShareCheck> getResByCodeAndType(Integer resTypeL1, String resCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resTypeL1", resTypeL1);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO); 
        qr.andEqual("resCode", resCode);
        qr.andEqual("shareCheckStatus", CoreConstants.CHECK_STATUS_CHECKING);
        qr.addDescOrder("applyTime");
        return super.find(qr);
    }

    /**
     * 删除资源 ()<br>
     * 
     * @param resCode
     * @return
     */
    public int delete(String resCode) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String sql = "update res_share_check set flagDelete=1 where resCode=:resCode  and shareCheckStatus=10 ";
        sqlParam.put("resCode", resCode);
        return super.update(sql, sqlParam);
    }

    /**
     * 查询资源 ()<br>
     * 
     * @param id
     * @return
     */
    public ResShareCheck getRes(Integer id) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", id);
        qr.andEqual("flagDelete", 0);
        return super.findUnique(qr);
    }

    /**
     * 
     * (特色资源审核)<br>
     * 
     * @param resCode 资源编码
     * @param resTypeL1 资源一级分类
     * @param shareCheckStatus 审核状态
     * @param userInfo 用户信息
     * @param ip ip地址
     * @param checkComments 审核意见
     * @return
     */
    @Override
    public int update(String resCode, Integer resTypeL1, Integer shareCheckStatus, UserInfoVo userInfo, String ip,
                    String checkComments) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp timeNow = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append(" update res_share_check set shareCheckStatus = :shareCheckStatus ");
        sql.append(" ,checkerCode = :checkerCode  ");
        sql.append(" ,checkerName = :checkerName  ");
        sql.append(" ,checkerOrgCode = :checkerOrgCode  ");
        sql.append(" ,checkerOrgName = :checkerOrgName  ");
        sql.append(" ,checkTime = :checkTime  ");
        if (StringUtils.isNotEmpty(checkComments)) {
            sql.append(" ,checkComments = :checkComments  ");
            sqlParam.put("checkComments", checkComments);
        }
        sql.append(" ,modifier = :modifier  ");
        sql.append(" ,modifierIP = :modifierIP  ");
        sql.append(" ,modifyTime = :modifyTime  ");
        sql.append(" where resCode = :resCode  ");
        sql.append(" and resTypeL1 = :resTypeL1  ");
        sql.append(" and shareCheckStatus = :checking ");
        sql.append(" and flagDelete = 0 ");
        sqlParam.put("checking", CoreConstants.CHECK_STATUS_CHECKING);
        sqlParam.put("resCode", resCode);
        sqlParam.put("resTypeL1", resTypeL1);
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        sqlParam.put("checkerCode", userInfo.getUserCode());
        sqlParam.put("checkerName", userInfo.getUserName());
        sqlParam.put("checkerOrgCode", userInfo.getOrgCode());
        sqlParam.put("checkerOrgName", userInfo.getOrgName());
        sqlParam.put("checkTime", timeNow);
        sqlParam.put("modifier", userInfo.getUserName());
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", timeNow);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (查询审核情况详细数据)<br>
     * 
     * @param param 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectByResCodeTypeL1(Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql参数
        Integer resTypeL1 = MapUtils.getInteger(param, "resTypeL1"); // 资源一级分类
        String resCode = MapUtils.getString(param, "resCode");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber"); // 页码
        Integer pageSize = MapUtils.getInteger(param, "pageSize"); // 每页记录数
        sql.append("SELECT");
        sql.append("        resName");
        sql.append("        ,applyTime");
        sql.append("        ,checkTime");
        sql.append("        ,shareCheckStatus");
        sql.append("        ,checkComments ");
        sql.append("        ,resCode");
        sql.append("        ,resTypeL1");
        sql.append("        ,checkerName");
        sql.append("    FROM");
        sql.append("        res_share_check ");
        sql.append("    WHERE flagDelete=0 ");
        if (StringUtils.isNotEmpty(resCode)) {
            sql.append("       AND resCode = :resCode ");
            sqlParam.put("resCode", resCode);
        }
        if (resTypeL1 != null) {
            sql.append("        AND resTypeL1 = :resTypeL1");
            sqlParam.put("resTypeL1", resTypeL1);
        }
        sql.append(" ORDER BY checkTime DESC ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 得到最近的审核信息 ()<br>
     * 
     * @param resCode
     * @param shareCheckStatus
     * @return
     */
    public Map<String, Object> getRecentlyInfo(String resCode, Integer shareCheckStatus) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        TOP 1 checkerName");
        sql.append("        ,checkComments ");
        sql.append("        ,applyTime ");
        sql.append("    FROM");
        sql.append("        res_share_check ");
        sql.append("    WHERE");
        sql.append("        resCode =:resCode ");
        sql.append("        AND shareCheckStatus =:shareCheckStatus");
        sql.append("        order by checkTime desc ");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("resCode", resCode);
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 
     * (根据相关条件查询审核记录)<br>
     * @param resTypeL1 资源一级分类
     * @param resCode 资源编码
     * @param shareCheckLevel 共享审核级别
     * @return
     */
    @Override
    public List<ResShareCheck> selectResCheckList(Integer resTypeL1, String resCode, Integer shareCheckLevel) {
        QueryRule qr=QueryRule.getInstance();
        qr.andEqual("resTypeL1", resTypeL1);
        qr.andEqual("resCode", resCode);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        qr.andLessEqual("shareCheckLevel", shareCheckLevel);
        qr.andEqual("shareCheckStatus", CoreConstants.CHECK_STATUS_CHECKING);
        return super.find(qr);
    }

}
