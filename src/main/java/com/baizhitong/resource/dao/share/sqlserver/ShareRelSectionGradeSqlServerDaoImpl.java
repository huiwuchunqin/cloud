package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareRelSectionGradeDao;
import com.baizhitong.resource.model.share.ShareRelSectionGrade;

@Service(value = "shareRelSectionGradeSqlServerDao")
public class ShareRelSectionGradeSqlServerDaoImpl extends BaseSqlServerDao<ShareRelSectionGrade>
                implements ShareRelSectionGradeDao {
    /**
     * 删除学段年级关系
     * 
     * @param sectionCode 学段编码
     * @author gaow
     * @date:2015年12月15日 下午2:08:31
     */
    public void delSectionGrade(String sectionCode) {
        String sql = "delete from share_rel_section_grade where sectionCode=?";
        super.update(sql, sectionCode);
    }

    /**
     * 保存学段年级关系
     * 
     * @param relSectionGradeList
     * @author gaow
     * @date:2015年12月15日 下午2:10:42
     */
    public void saveSectionGradeRef(List<ShareRelSectionGrade> relSectionGradeList) throws Exception {
        super.saveAll(relSectionGradeList);
    }

    @Override
    public List<Map<String, Object>> selectGradeListBySectionCode(String sectionCode) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" SELECT ");
        sql.append("    sssg.sectionCode AS sectionCode ");// 学段编码
        sql.append("    ,scg.code AS code ");// 年级编码
        sql.append("    ,scg.name AS name ");// 年级名称
        sql.append(" FROM ");
        sql.append("    share_rel_section_grade sssg ");// 学段与年级关联表
        sql.append("    ,share_code_grade scg ");// 年级表
        sql.append(" WHERE sssg.gradeCode = scg.code ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" AND sssg.sectionCode= :sectionCode ");// 学段编码条件限定
            sqlParam.put("sectionCode", sectionCode);
        }
        // 按照显示排序升序排序
        sql.append(" ORDER BY scg.dispOrder ASC ");
        return super.findBySql(sql.toString(), sqlParam);
    }
}
