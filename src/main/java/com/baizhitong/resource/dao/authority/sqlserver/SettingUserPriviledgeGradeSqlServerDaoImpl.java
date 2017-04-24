package com.baizhitong.resource.dao.authority.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeGradeDao;
import com.baizhitong.resource.model.authority.SettingUserPriviledgeGrade;

/**
 * 
 * 设置-管理员权限-年级DAO实现类
 * 
 * @author creator ZhangQiang 2016年9月26日 下午2:40:25
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class SettingUserPriviledgeGradeSqlServerDaoImpl extends BaseSqlServerDao<SettingUserPriviledgeGrade>
                implements SettingUserPriviledgeGradeDao {

    /**
     * 
     * (新增一条记录)<br>
     * 
     * @param entity
     * @return
     */
    @Override
    public boolean insert(SettingUserPriviledgeGrade entity) {
        try {
            return super.saveOne(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据登录者编码查询年级权限并返回<br>
     * 处理说明:按显示顺序升序排序
     * 
     * @param userCode 登录帐号
     * @return 年级权限列表
     */
    @Override
    public List<Map<String, Object>> selectListByUserCode(String userCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("userCode", userCode);
        sql.append(" SELECT");
        sql.append("    supg.id AS priviledgeGradeId ");
        sql.append("    ,supg.userCode ");
        sql.append("    ,supg.gradeCode ");
        sql.append("    ,supg.dispOrder ");
        sql.append("    ,supg.memo ");
        sql.append("    ,scg.name AS gradeName ");
        sql.append(" FROM setting_user_priviledge_grade AS supg ");
        sql.append(" LEFT JOIN  share_code_grade AS scg ON scg.code = supg.gradeCode ");
        sql.append(" WHERE supg.userCode = :userCode  ");
        sql.append(" ORDER BY supg.dispOrder ASC,supg.id ");
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
        sql.append(" DELETE FROM setting_user_priviledge_grade ");
        sql.append(" WHERE userCode = :userCode ");
        return update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (根据学段编码查询用户可以审核的年级列表)<br>
     * 
     * @param userCode
     * @param sectionCode
     * @return
     */
    @Override
    public List<Map<String, Object>> selectGradeListByUserCode(String userCode, String sectionCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("userCode", userCode);
        sqlParam.put("sectionCode", sectionCode);
        sql.append(" SELECT");
        sql.append("    supg.userCode ");
        sql.append("    ,supg.gradeCode AS code");
        sql.append("    ,scg.name ");
        sql.append(" FROM setting_user_priviledge_grade AS supg ");
        sql.append(" LEFT JOIN  share_code_grade AS scg ON scg.code = supg.gradeCode ");
        sql.append(" WHERE supg.userCode = :userCode  ");
        sql.append(" AND supg.sectionCode = :sectionCode ");
        sql.append(" ORDER BY supg.dispOrder ASC,supg.id ");
        return super.findBySql(sql.toString(), sqlParam);
    }

}
