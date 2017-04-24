package com.baizhitong.resource.dao.authority.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeSubjectDao;
import com.baizhitong.resource.model.authority.SettingUserPriviledgeSubject;

/**
 * 
 * 设置-管理员权限-学科DAO实现类
 * 
 * @author creator ZhangQiang 2016年9月26日 下午2:51:39
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class SettingUserPriviledgeSubjectSqlServerDaoImpl extends BaseSqlServerDao<SettingUserPriviledgeSubject>
                implements SettingUserPriviledgeSubjectDao {

    /**
     * 
     * (新增一条记录)<br>
     * 
     * @param entity
     * @return
     */
    @Override
    public boolean insert(SettingUserPriviledgeSubject entity) {
        try {
            return super.saveOne(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据登录者编码查询学科权限并返回<br>
     * 处理说明：按显示顺序升序排序
     * 
     * @param userCode 登录者编码
     * @return 学科权限列表
     */
    @Override
    public List<Map<String, Object>> selectListByUserCode(String userCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("userCode", userCode);
        sql.append(" SELECT");
        sql.append("    sups.id AS priviledgeSubjectId");
        sql.append("    ,sups.userCode");
        sql.append("    ,sups.subjectCode");
        sql.append("    ,sups.sectionCode");
        sql.append("    ,se.name AS sectionName");
        sql.append("    ,sups.dispOrder");
        sql.append("    ,sups.memo ");
        sql.append("    ,scs.name AS subjectName ");
        sql.append(" FROM setting_user_priviledge_subject AS sups ");
        sql.append(" LEFT JOIN share_code_section AS se ON se.code = sups.sectionCode ");
        sql.append(" LEFT JOIN  share_code_subject AS scs ON scs.code = sups.subjectCode ");
        sql.append(" WHERE sups.userCode = :userCode  ");
        sql.append(" ORDER BY sups.dispOrder ASC, sups.id ");
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 根据登录帐号更新删除标识
     * 
     * @param userCode 登录帐号
     * @return 操作成功个数
     */
    @Override
    public int deleteByUserCode(String userCode) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();// SQL参数
        StringBuffer sql = new StringBuffer();// SQL语句
        sqlParam.put("userCode", userCode);
        sql.append(" DELETE FROM setting_user_priviledge_subject ");
        sql.append(" WHERE userCode = :userCode ");
        return update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (根据用户编码和学段编码查询学科列表)<br>
     * 
     * @param userCode
     * @param sectionCode
     * @return
     */
    @Override
    public List<Map<String, Object>> selectSubjectListByUserCode(String userCode, String sectionCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("userCode", userCode);
        sqlParam.put("sectionCode", sectionCode);
        sql.append(" SELECT");
        sql.append("    sups.userCode");
        sql.append("    ,sups.subjectCode AS code");
        sql.append("    ,sups.sectionCode ");
        sql.append("    ,sups.dispOrder");
        sql.append("    ,scs.name ");
        sql.append(" FROM setting_user_priviledge_subject AS sups ");
        sql.append(" LEFT JOIN share_code_subject AS scs ON scs.code = sups.subjectCode ");
        sql.append(" WHERE sups.userCode = :userCode  ");
        sql.append(" AND sups.sectionCode = :sectionCode ");
        sql.append(" ORDER BY sups.dispOrder ASC, sups.id ");
        return super.findBySql(sql.toString(), sqlParam);
    }

}
