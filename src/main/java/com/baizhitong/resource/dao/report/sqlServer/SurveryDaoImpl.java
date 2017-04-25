package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.dao.report.SurveryDao;
import com.baizhitong.utils.StringUtils;

/**
 * 概况dao SurveryDaoImpl TODO
 * 
 * @author creator gaow 2017年4月25日 上午11:19:51
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class SurveryDaoImpl  implements SurveryDao {
   @Autowired 
   NamedParameterJdbcTemplate JdbcTemplate;

    /**
     * ()<br>
     * 
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getSurvery(Map param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String resTypeL1 = MapUtils.getString(param, "resTypeL1");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (*) as count");
        sql.append("        ,makeTime ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            CONVERT (VARCHAR(100)");
        sql.append("            ,makeTime");
        sql.append("            ,23) AS makeTime");
        sql.append("            ,resTypeL1 ");
        sql.append("        FROM");
        sql.append("            res_doc ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            CONVERT (VARCHAR(100)");
        sql.append("            ,makeTime");
        sql.append("            ,23) AS makeTime");
        sql.append("            ,resTypeL1 ");
        sql.append("        FROM");
        sql.append("            res_media ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            CONVERT (VARCHAR(100)");
        sql.append("            ,makeTime");
        sql.append("            ,23) AS makeTime");
        sql.append("            ,resTypeL1 ");
        sql.append("        FROM");
        sql.append("            exercise ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            CONVERT (VARCHAR(100)");
        sql.append("            ,makeTime");
        sql.append("            ,23) AS makeTime");
        sql.append("            ,50 ");
        sql.append("        FROM");
        sql.append("            question ");
        sql.append("    ) res ");
        sql.append("WHERE");
        sql.append("    1 = 1 ");
        if (StringUtils.isNotEmpty(resTypeL1)) {
            sql.append(" AND resTypeL1 =:resTypeL1");
            sqlParam.put("resTypeL1", resTypeL1);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND makeTime >=:startDate");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND makeTime <=:endDate");
            sqlParam.put("endDate", endDate);
        }
        sql.append("    ");
        sql.append("GROUP BY");
        sql.append("    makeTime ");
        sql.append("    ");
        sql.append("ORDER BY");
        sql.append("    makeTime");
        return JdbcTemplate.queryForList(sql.toString(), sqlParam);
    }

}
