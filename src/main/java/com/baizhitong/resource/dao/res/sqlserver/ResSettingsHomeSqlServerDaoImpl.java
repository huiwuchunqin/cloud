package com.baizhitong.resource.dao.res.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResSettingsHomeDao;
import com.baizhitong.resource.model.res.ResSettingsHome;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 资源设置-首页显示SqlServer接口实现
 * 
 * @author creator zhangqiang 2016年7月27日 下午1:44:19
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ResSettingsHomeSqlServerDaoImpl extends BaseSqlServerDao<ResSettingsHome> implements ResSettingsHomeDao {

    /**
     * 
     * (分页查询资源设置显示信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectPage(Map<String, Object> param) {
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        String resName = MapUtils.getString(param, "resName");
        String setType = MapUtils.getString(param, "setType");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        rsh.resName");
        sql.append("        ,rsh.setType");
        sql.append("        ,srtl1.name AS resTypeL1Name");
        sql.append("        ,srtl2.name AS resTypeL2Name");
        sql.append("        ,rsh.id");
        sql.append("        ,rsh.thumbnailPath");
        sql.append("        ,rsh.coverPath");
        sql.append("        ,rsh.dispOrder");
        sql.append("        ,rsh.flagAvailable ");
        sql.append("        ,rsh.sectionCode ");
        sql.append("        ,rsh.subjectCode ");
        sql.append("        ,rsh.resCode ");
        sql.append("        ,scs.name AS sectionName ");
        sql.append("        ,scs2.name AS subjectName ");
        sql.append("        ,t1.name AS resSpecialTypeL1Name ");
        sql.append("        ,t2.name AS resSpecialTypeL2Name ");
        sql.append("        ,rsh.resSpecialTypeL1 ");
        sql.append("        ,rsh.resSpecialTypeL2 ");
        sql.append("    FROM");
        sql.append("        res_settings_home rsh ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l1 srtl1 ");
        sql.append("            ON rsh.resTypeL1 = srtl1.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l2 srtl2 ");
        sql.append("            ON rsh.resTypeL2 = srtl2.code ");
        sql.append("    left join share_code_section scs ");
        sql.append("    on rsh.sectionCode = scs.code ");
        sql.append("    left join share_code_subject scs2 ");
        sql.append("    on rsh.subjectCode = scs2.code ");
        sql.append("    left join share_res_special_type t1 ");
        sql.append("    on t1.code=rsh.resSpecialTypeL1 ");
        sql.append("    left join share_res_special_type t2 ");
        sql.append("    on t2.code=rsh.resSpecialTypeL2 ");
        sql.append("    WHERE 1=1 ");
        if (StringUtils.isNotEmpty(resName)) {
            sql.append("        AND rsh.resName LIKE :resName ");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(setType)) {
            sql.append("        AND rsh.setType = :setType ");
            sqlParam.put("setType", setType);
        }
        sql.append(" ORDER BY rsh.settingTime DESC ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 
     * (新增或修改)<br>
     * 
     * @param entity 实体
     * @return
     */
    @Override
    public boolean add(ResSettingsHome entity) {
        try {
            return super.saveOne(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 
     * (根据显示类别查询当前已有的最大顺序号)<br>
     * 
     * @param setType 显示类别
     * @return
     */
    @Override
    public int selectMaxOrderBySetType(String setType) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" SELECT MAX (dispOrder) AS maxDispOrder FROM res_settings_home WHERE setType= :setType ");
        sqlParam.put("setType", setType);
        // 执行SQL
        Map<String, Object> map = super.findUniqueBySql(sql.toString(), sqlParam);
        int orderNum = 0;
        if (MapUtils.isNotEmpty(map)) {
            orderNum = MapUtils.getInteger(map, "maxDispOrder") != null
                            ? MapUtils.getInteger(map, "maxDispOrder").intValue() : 0;
        }
        return orderNum;
    }

    /**
     * 
     * (删除首页显示资源)<br>
     * 
     * @param id 主键id
     * @return
     */
    @Override
    public int delete(String id) {
        String sql = "delete from res_settings_home where id = :id";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("id", id);
        return super.update(sql, sqlParam);
    }

    /**
     * 
     * (根据资源编码和显示类型查询资源首页设置信息)<br>
     * 
     * @param resCode 资源编码
     * @param setType 显示类别
     * @return
     */
    @Override
    public ResSettingsHome selectByResCodeAndSetType(String resCode, String setType) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resCode", resCode);
        qr.andEqual("setType", setType);
        return super.findUnique(qr);
    }

    /**
     * 
     * (修改资源在首页是否使用)<br>
     * 
     * @param id 主键id
     * @param flagAvailable 是否使用
     * @return
     */
    @Override
    public int updateFlagAvailable(Integer id, Integer flagAvailable) {
        String sql = "update res_settings_home set flagAvailable = :flagAvailable where id = :id";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("flagAvailable", flagAvailable);
        sqlParam.put("id", id);
        return super.update(sql, sqlParam);
    }

    /**
     * 
     * (查询列表，已使用的情况)<br>
     * 
     * @param setType 显示类别
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @return
     */
    @Override
    public List<ResSettingsHome> selectList(String setType, String sectionCode, String subjectCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("setType", setType);
        if (StringUtils.isNotEmpty(sectionCode)) {
            qr.andEqual("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            qr.andEqual("subjectCode", subjectCode);
        }
        qr.andEqual("flagAvailable", CoreConstants.FLAG_AVAILABLE_YES);
        return super.find(qr);
    }

    /**
     * 
     * (查询特色资源列表，已使用的情况)<br>
     * 
     * @param setType 显示类别
     * @param resSpecialTypeL2 特色二级分类
     * @return
     */
    @Override
    public List<ResSettingsHome> selectList(String setType, String resSpecialTypeL2) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("setType", setType);
        if (StringUtils.isNotEmpty(resSpecialTypeL2)) {
            qr.andEqual("resSpecialTypeL2", resSpecialTypeL2);
        }
        qr.andEqual("flagAvailable", CoreConstants.FLAG_AVAILABLE_YES);
        return super.find(qr);
    }

}
