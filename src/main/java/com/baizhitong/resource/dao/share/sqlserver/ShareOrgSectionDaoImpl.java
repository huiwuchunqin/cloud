package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareOrgSectionDao;
import com.baizhitong.resource.model.company.ShareOrgSection;

@Component
public class ShareOrgSectionDaoImpl extends BaseSqlServerDao<ShareOrgSection> implements ShareOrgSectionDao {

    /**
     * 新增机构学段 ()<br>
     * 
     * @param section
     * @return
     */
    public boolean addOrgSection(ShareOrgSection section) {
        try {
            return super.saveOne(section);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询机构学段 ()<br>
     * 
     * @param sectionName
     * @return
     */
    public List<Map<String, Object>> getOrgSection(String sectionName, String orgCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("select");
        sql.append("        sos.* ");
        sql.append("    FROM");
        sql.append("        share_org_section sos ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            on sos.sectionCode=scs.code ");
        sql.append("    WHERE");
        sql.append("        scs.name=:name ");
        sql.append("        and sos.orgCode=:orgCode");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", sectionName);
        param.put("orgCode", orgCode);
        return super.findBySql(sql.toString(), param);

    }

    /**
     * 查询机构学段 ()<br>
     * 
     * @param orgCode 机构编码
     * @return
     */
    public List<ShareOrgSection> getOrgSection(String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        return super.find(qr);
    }

    /**
     * 更新机构学段信息 ()<br>
     * 
     * @param orgCode 机构编码
     * @param sectionCode 学段编码
     * @return
     */
    public int updateOrgSection(String orgCode, String sectionCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_org_section  ");
        sql.append("    SET");
        sql.append("        sectionCode =:sectionCode  ");
        sql.append("    WHERE");
        sql.append("        orgCode =:orgCode");
        param.put("orgCode", orgCode);
        param.put("sectionCode", sectionCode);
        return super.update(sql.toString(), param);

    }

}
