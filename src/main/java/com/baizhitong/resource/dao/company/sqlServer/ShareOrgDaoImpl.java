package com.baizhitong.resource.dao.company.sqlServer;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.utils.ObjectUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 机构dao
 * 
 * @author creator gaow 2016年1月22日 上午11:08:05
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ShareOrgDaoImpl extends BaseSqlServerDao<ShareOrg> implements ShareOrgDao {

    /**
     * 查询机构信息 ()<br>
     * 
     * @param orgName 机构名称
     * @param sectionCode 学段编码
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Page getOgrPageInfo(String orgName, String sectionCode, Integer pageNo, Integer pageSize) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append(" org.gid, org.orgCode, org.orgNameShort, org.orgName, org.orgRegCode, org.introduction, org.logoUrl, org.orgType, org.orgFavicon, org.orgGroupNo, org.buildTime, org.flagValid, org.validDateStart, org.validDateEnd, org.remark, org.icp_no, org.schoolTypeCode, org.districtTypeCode, org.orgCodeState, org.orgCodeType, org.defaultAreaCode, org.scaleDesc, org.feature, org.topWorkNo, org.topName, org.phone, org.mail, org.modifyPgm, org.modifyTime, org.modifyIP, org.sysVer, org.cd_guid, org.orgCodeXXDM,exerciseAutoSaveInterval,learningAutoSaveInterval");
        sql.append("        ,sos.sectionCode");
        sql.append("        ,scs.name AS sectionName ");
        sql.append("    FROM");
        sql.append("        share_org AS org ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org_section sos ");
        sql.append("            ON org.orgCode = sos.orgCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = sos.sectionCode ");
        sql.append("            AND scs.flagDelete = 0 ");
        sql.append("    WHERE");
        sql.append("        1 = 1");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" AND org.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append(" AND sos.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append(" order by orgName ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNo, pageSize);

    }

    /**
     * 查询机构信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public ShareOrg getOrg(String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        return super.findUnique(qr);
    }

    /**
     * 查询机构学段 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getOrgSection(String orgCode) {
        StringBuffer buf = new StringBuffer();
        buf.append("SELECT");
        buf.append("        scs.code");
        buf.append("        ,scs.name ");
        buf.append("    FROM");
        buf.append("        share_org_section sos ");
        buf.append("    INNER JOIN");
        buf.append("        share_code_section scs ");
        buf.append("            ON sos.sectionCode = scs.code and scs.flagDelete = 0");
        buf.append(" and sos.orgCode=:orgCode ");
        buf.append(" order by  scs.dispOrder ");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgCode", orgCode);
        return super.findBySql(buf.toString(), map);

    }

    /**
     * 查询机构信息 ()<br>
     * 
     * @return
     */
    public List<Map<String, Object>> getOrgList() {
        String sql = "select * from share_org";
        Map<String, Object> param = new HashMap<String, Object>();
        return super.findBySql(sql, param);
    }

    /**
     * 查询机构详细信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public Map<String, Object> getOrgInfo(String orgCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        org.orgCode");
        sql.append("        ,org.orgNameShort");
        sql.append("        ,org.orgName");
        sql.append("        ,org.orgRegCode");
        sql.append("        ,org.introduction");
        sql.append("        ,org.phone");
        sql.append("        ,org.logoUrl");
        sql.append("        ,org.mail");
        sql.append("        ,org.cd_guid");
        sql.append("        ,org.topName");
        sql.append("        ,sos.sectionCode");
        sql.append("        ,org.modifyTime");
        sql.append("        ,org.domainID");
        sql.append("        ,org.agency");
        sql.append("        ,org.currentStudyYearCode");
        sql.append("        ,org.currentYearTermCode");
        sql.append("        ,org.currentTermCode");
        sql.append("        ,(select domainID from share_org where orgCode=org.agency) as pDomainID ");
        sql.append("        ,sd.domainURL");
        /* sql.append("        ,scs.name AS sectionName "); */
        sql.append("    FROM");
        sql.append("        share_org AS org ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org_section sos ");
        sql.append("            ON org.orgCode = sos.orgCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = sos.sectionCode ");
        sql.append("            AND scs.flagDelete = 0 ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org porg ");
        sql.append("            ON porg.orgCode = org.agency ");
        sql.append(" 	LEFT JOIN share_domain sd ");
        sql.append("    on org.domainID=sd.id and sd.flagDelete=0 ");
        sql.append("    WHERE");
        sql.append("        1 = 1");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("  and org.orgCode=:orgCode ");
        sqlParam.put("orgCode", orgCode);

        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 
     * 根据机构名称查询机构
     * 
     * @param orgName 机构名称
     * @return 机构列表
     */
    public List<ShareOrg> getOrgByName(String orgName) {
        QueryRule qr = QueryRule.getInstance();
        qr.andLike("orgName", orgName.trim());
        return super.find(qr);
    }

    /**
     * 查询机构信息 ()<br>
     * 
     * @param sectionCode 学段编码
     * @return
     */
    public List<Map<String, Object>> getOrgList(String sectionCode) {
        String sql = "select so.orgCode,so.orgName from share_org so ";
        Map<String, Object> param = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql = sql + "inner join share_org_section  sos on so.orgCode=sos.orgCode where sos.sectionCode=:sectionCode";
            param.put("sectionCode", sectionCode);
        }
        List<Map<String, Object>> mapList = super.findBySql(sql, param);
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                String orgCode = MapUtils.getString(map, "orgCode");
                map.put("orgCode", orgCode.trim());
            }
        }
        return mapList;
    }

    /**
     * 保存机构信息 ()<br>
     * 
     * @param org
     * @return
     */
    public boolean saveOrUpdate(ShareOrg org) {
        try {
            return super.saveOne(org);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            return false;
        }
    }

    /**
     * 根据guid查询机构 ()<br>
     * 
     * @param cdGuid
     * @return
     */
    public ShareOrg getByGuid(String cdGuid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("cd_guid", cdGuid);
        return super.findUnique(qr);
    }

    /**
     * 根据域名查询机构 ()<br>
     * 
     * @param domain
     * @return 机构
     */
    public ShareOrg getOrgByDomain(Integer domainID) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("domainID", domainID);
        return super.findUnique(qr);
    }

    /**
     * 查询代理商信息 ()<br>
     * 
     * @param orgCode 机构编码
     * @return 机构信息
     */
    public Map<String, Object> getAgencyInfo(String orgCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        a.orgCode");
        sql.append("        ,a.orgName");
        sql.append("        ,a.orgNameShort");
        sql.append("        ,a.introduction");
        sql.append("        ,a.logoUrl");
        sql.append("        ,a.remark");
        sql.append("        ,a.topName");
        sql.append("        ,a.phone");
        sql.append("        ,a.mail");
        sql.append("        ,a.modifyPgm");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.modifyIP");
        sql.append("        ,a.sectionCode");
        sql.append("        ,a.agency");
        sql.append("        ,a.flagAgency");
        sql.append("        ,a.flagValid");
        sql.append("        ,a.domainID");
        sql.append("        ,a.agencyLevel");
        sql.append("        ,b.orgName AS agencyName ");
        sql.append("        ,sd.domainLogo AS domainLogo ");
        sql.append("    FROM");
        sql.append("        share_org a ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org b ");
        sql.append("            ON b.orgCode = a.agency ");
        sql.append(" left join share_domain sd on sd.id=a.domainID ");
        sql.append(" where a.orgCode=:orgCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", orgCode);
        return super.findUniqueBySql(sql.toString(), sqlParam);

    }

    /**
     * 更新机构信息 ()<br>
     * 
     * @param org
     * @param ip
     * @param modifyPgm
     * @return
     */
    public int updateAgency(ShareOrg org, String ip, String modifyPgm) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_org  ");
        sql.append("    SET");
        sql.append("        orgName =:orgName");
        sql.append("        ,orgNameShort =:orgNameShort");
        sql.append("        ,topName =:topName");
        sql.append("        ,phone =:phone");
        sql.append("        ,logoURL =:logoURL");
        sql.append("        ,mail =:mail");
        sql.append("        ,remark =:remark");
        sql.append("        ,modifyIP =:modifyIP");
        sql.append("        ,modifyPgm =:modifyPgm");
        sql.append("        ,modifyTime = GETDATE()  ");
        sql.append("    WHERE");
        sql.append("        orgCode =:orgCode");

        param.put("orgName", org.getOrgName());
        param.put("orgNameShort", org.getOrgNameShort());
        param.put("topName", org.getTopName());
        param.put("phone", org.getPhone());
        param.put("mail", org.getMail());
        param.put("domainID", org.getDomainID());
        param.put("logoURL", org.getLogoUrl());
        param.put("remark", org.getRemark());
        param.put("modifyIP", ip);
        param.put("modifyPgm", modifyPgm);
        param.put("orgCode", org.getOrgCode());
        return super.update(sql.toString(), param);
    }

    /**
     * 查询代理商信息
     * 
     * @param param 查询条件
     * @param page 页码
     * @param rows 记录数
     * 
     *        ()<br>
     * @return page
     */
    public Page getAgencyPage(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // 参数
        String agency = MapUtils.getString(param, "agency");
        String orgName = MapUtils.getString(param, "orgName");
        Integer agencyLevel = MapUtils.getInteger(param, "agencyLevel");

        sql.append("SELECT");
        sql.append("        a.orgCode");
        sql.append("        ,a.orgNameShort");
        sql.append("        ,a.orgName");
        sql.append("        ,a.introduction");
        sql.append("        ,a.logoUrl");
        sql.append("        ,a.flagValid");
        sql.append("        ,a.remark");
        sql.append("        ,a.topName");
        sql.append("        ,a.phone");
        sql.append("        ,a.mail");
        sql.append("        ,a.modifyPgm");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.modifyIP");
        sql.append("        ,a.sysVer");
        sql.append("        ,a.sectionCode");
        sql.append("        ,a.agency");
        sql.append("        ,a.flagAgency");
        sql.append("        ,a.agencyLevel");
        sql.append("        ,a.domainID");
        sql.append("        ,sd.domainURL");
        sql.append("        ,(   SELECT");
        sql.append("            COUNT (1)   ");
        sql.append("        FROM");
        sql.append("            share_org c   ");
        sql.append("        WHERE");
        sql.append("            c.agency = a.orgCode   ");
        sql.append("            AND c.flagAgency != :flagAgencyTrue  ) AS schoolCount ");
        sql.append("    FROM");
        sql.append("        share_org a ");
        sql.append(" left join  ");
        sql.append("      share_domain sd on sd.id=a.domainID ");
        sql.append("    WHERE");
        sql.append("        a.agency =:agency and a.flagAgency=:flagAgencyTrue ");
        sqlParam.put("agency", agency);
        sqlParam.put("flagAgencyTrue", CoreConstants.FLAG_AGENCY_TRUE);
        if (agencyLevel != null) {
            sql.append(" and agencyLevel=:agencyLevel ");
            sqlParam.put("agencyLevel", agencyLevel);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and orgName like '%" + orgName + "%'");
        }

        sql.append("       order by a.modifyTime desc");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }

    /**
     * 查询代理商学校 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getAgencySchool(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();

        // 查询参数
        String orgName = MapUtils.getString(param, "orgName");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        sql.append("SELECT");
        sql.append("        a.orgCode");
        sql.append("        ,a.orgNameShort");
        sql.append("        ,a.orgName");
        sql.append("        ,a.introduction");
        sql.append("        ,a.logoUrl");
        sql.append("        ,a.buildTime");
        sql.append("        ,a.flagValid");
        sql.append("        ,a.remark");
        sql.append("        ,a.topWorkNo");
        sql.append("        ,a.topName");
        sql.append("        ,a.phone");
        sql.append("        ,a.mail");
        sql.append("        ,a.modifyPgm");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.modifyIP");
        sql.append("        ,a.sectionCode");
        sql.append("        ,a.agency");
        sql.append("        ,a.flagAgency");
        sql.append("        ,a.agencyLevel");
        sql.append("        ,a.domainID");
        sql.append("        ,b.orgName AS agencyName");
        sql.append("        ,c.name AS sectionName");
        sql.append("        ,sd.domainURL ");
        sql.append("        ,(   SELECT");
        sql.append("            COUNT (1)   ");
        sql.append("        FROM");
        sql.append("            share_admin_class   ");
        sql.append("        WHERE");
        sql.append("            orgCode = a.orgCode   ");
        sql.append("            AND classStatus =:notGraduate  ) AS adminClassCount");
        sql.append("        ,(   SELECT");
        sql.append("            COUNT (1)   ");
        sql.append("        FROM");
        sql.append("            share_user_teacher   ");
        sql.append("        WHERE");
        sql.append("            orgCode = a.orgCode   ");
        sql.append("            AND status =:status  ) AS teacherCount");
        sql.append("        ,(   SELECT");
        sql.append("            COUNT (1)   ");
        sql.append("        FROM");
        sql.append("            share_user_student   ");
        sql.append("        WHERE");
        sql.append("            orgCode = a.orgCode   ");
        sql.append("            AND status =:status  ) AS studentCount ");
        sql.append("    FROM");
        sql.append("        share_org a ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org b ");
        sql.append("            ON a.agency = b.orgCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section c ");
        sql.append("            ON c.code = a.sectionCode ");
        sql.append(" left join ");
        sql.append(" share_domain sd on sd.id=a.domainID ");
        sql.append("    where 1=1    and a.flagAgency=:flagNotAgency    ");
        sqlParam.put("flagNotAgency", CoreConstants.FLAG_AGENCY_FALSE);

        sqlParam.put("notGraduate", CoreConstants.CLASS_STATUS__NOT_GRADUATE);
        sqlParam.put("status", CoreConstants.USER_STATUS_EFFECTIVE);
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and b.orgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and a.orgName like '%" + orgName + "%'");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and a.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("    ORDER BY");
        sql.append("        a.modifyTime");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 禁用或者启用机构 ()<br>
     * 
     * @param state 1启用 0禁用
     * @param orgCode 机构编码
     * @return 操作结果
     */
    public int updateState(Integer state, String orgCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_org  ");
        sql.append("    SET");
        sql.append("        flagValid =:flagValid  ");
        sql.append("    WHERE");
        sql.append("        orgCode =:orgCode");
        sqlParam.put("flagValid", state);
        sqlParam.put("orgCode", orgCode);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 查询邮箱一样的机构 ()<br>
     * 
     * @param mail
     * @param orgCode
     * @return
     */
    public long getSameMailOrg(String mail, String orgCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (1) ");
        sql.append("    FROM");
        sql.append("        share_org ");
        sql.append("    WHERE");
        sql.append("        mail =:mail");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and orgCode!=:orgCode");
            param.put("orgCode", orgCode);
        }
        param.put("mail", mail);
        return super.queryCount(sql.toString(), param);
    }

    /**
     * 查询手机号一样的机构 ()<br>
     * 
     * @param phone
     * @param orgCode
     * @return
     */
    public long getSamePhoneOrg(String phone, String orgCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (1) ");
        sql.append("    FROM");
        sql.append("        share_org ");
        sql.append("    WHERE");
        sql.append("        phone =:phone");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and orgCode!=:orgCode");
            param.put("orgCode", orgCode);
        }
        param.put("phone", phone);
        return super.queryCount(sql.toString(), param);
    }

    /**
     * 
     * (根据当前日期批量更新机构的学年学期信息)<br>
     * 
     * @param currentDate 当前日期
     * @return 更新记录数
     */
    @Override
    public int updateOrgYearTermInfoBatch(Timestamp currentDate) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_org  ");
        sql.append("    SET");
        sql.append("        currentStudyYearCode = soyt.studyYearCode");
        sql.append("        ,currentTermCode = soyt.termCode");
        sql.append("        ,currentYearTermCode = soyt.yearTermCode  ");
        sql.append("    FROM");
        sql.append("        (   SELECT");
        sql.append("            A.*   ");
        sql.append("        FROM");
        sql.append("            share_org_year_term A   ");
        sql.append("        INNER JOIN");
        sql.append("            (");
        sql.append("                SELECT");
        sql.append("                    MAX (yearTermCode) AS yearTermCode");
        sql.append("                    ,orgcode   ");
        sql.append("                FROM");
        sql.append("                    share_org_year_term   ");
        sql.append("                WHERE");
        sql.append("                    startDate <= :currentDate   ");
        sql.append("                    AND endDate >= :currentDate   ");
        sql.append("                GROUP BY");
        sql.append("                    orgcode   ");
        sql.append("            ) B ");
        sql.append("                ON A.orgcode = B.orgcode   ");
        sql.append("                AND A.yearTermCode = B.yearTermCode   ");
        sql.append("            ) soyt  ");
        sql.append("    WHERE");
        sql.append("        soyt.orgCode = share_org.orgCode  ");
        sql.append("        AND soyt.startDate <= :currentDate  ");
        sql.append("        AND soyt.endDate >= :currentDate  ");
        sql.append("        AND (");
        sql.append("            currentYearTermCode != soyt.yearTermCode   ");
        sql.append("            OR currentYearTermCode IS NULL  ");
        sql.append("        )");
        sqlParam.put("currentDate", currentDate);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 查询相同域名的机构数量 ()<br>
     * 
     * @param domainID 域名id
     * @return
     */
    public long getSameDomainCount(Integer domainID) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (1) ");
        sql.append("    FROM");
        sql.append("        share_org ");
        sql.append("    WHERE");
        sql.append("        domainID =:domainID");
        param.put("domainID", domainID);
        return super.queryCount(sql.toString(), param);
    }

    /**
     * 更新机构域名 ()<br>
     * 
     * @param orgCode
     * @param domainID
     * @return
     */
    public int updateOrgDomain(String orgCode, Integer domainID) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_org  ");
        sql.append("    SET");
        sql.append("        domainID =:domainID  ");
        sql.append("    WHERE");
        sql.append("        orgCode =:orgCode");
        param.put("orgCode", orgCode);
        param.put("domainID", domainID);
        return super.update(sql.toString(), param);

    }

    /**
     * 
     * (查询没有学段的机构列表)<br>
     * 
     * @return 没有学段的机构列表
     */
    @Override
    public List<Map<String, Object>> selectNoSectionOrgList() {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("select");
        sql.append("        so.orgCode");
        sql.append("        ,so.orgName ");
        sql.append("    FROM");
        sql.append("        share_org so ");
        sql.append("    WHERE");
        sql.append("        so.orgCode NOT ");
        sql.append("    IN(");
        sql.append("        SELECT");
        sql.append("            DISTINCT orgCode FROM");
        sql.append("                share_org_section ");
        sql.append("        )");
        List<Map<String, Object>> mapList = super.findBySql(sql.toString(), sqlParam);
        if (ObjectUtils.isNotNull(mapList) && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                String orgCode = MapUtils.getString(map, "orgCode");
                map.put("orgCode", orgCode.trim());
            }
        }
        return mapList;
    }

}
