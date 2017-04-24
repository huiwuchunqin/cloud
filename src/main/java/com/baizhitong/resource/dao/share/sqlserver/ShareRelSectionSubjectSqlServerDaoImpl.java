package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareRelSectionSubjectDao;
import com.baizhitong.resource.model.share.ShareRelSectionSubject;
import com.baizhitong.utils.StringUtils;

@Component
public class ShareRelSectionSubjectSqlServerDaoImpl extends BaseSqlServerDao<ShareRelSectionSubject>
                implements ShareRelSectionSubjectDao {
    /**
     * 删除学段学科关系
     * 
     * @param sectionCode
     * @author gaow
     * @date:2015年12月15日 下午2:08:31
     */
    public void delSectionSubject(String sectionCode) {
        String sql = "delete from share_rel_section_subject where sectionCode=?";
        super.update(sql, sectionCode);
    }

    /**
     * 保存学段学科关系
     * 
     * @param relSectionSubjectList
     * @author gaow
     * @date:2015年12月15日 下午2:10:42
     */
    public void saveSectionSubjectRef(List<ShareRelSectionSubject> relSectionSubjectList) throws Exception {
        super.saveAll(relSectionSubjectList);
    }

    /**
     * 保存学段学科关系 ()<br>
     * 
     * @param relSectionSubject
     * @throws Exception
     */
    public void saveSectionSubjectRef(ShareRelSectionSubject relSectionSubject) throws Exception {
        super.saveOne(relSectionSubject);

    }

    @Override
    public List<Map<String, Object>> selectListBySectionCode(String sectionCode) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" SELECT   ");
        sql.append("    srss.sectionCode AS sectionCode ");// 学段编码
        sql.append("    ,scs.code AS code ");// 学科编码
        sql.append("    ,scs.name AS name ");// 学科名称
        sql.append(" FROM ");
        sql.append("    share_rel_section_subject srss ");// 学段与学科关联表
        sql.append("    ,share_code_subject scs ");// 学科表
        sql.append(" WHERE srss.subjectCode = scs.code ");
        sql.append(" AND scs.flagDelete = :flagDelete");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" AND srss.sectionCode= :sectionCode ");// 学段编码条件赋值
            sqlParam.put("sectionCode", sectionCode);// 学科编码
        }
        // 查询逻辑未删除的数据
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        sql.append(" ORDER BY scs.dispOrder ASC ");// 按照显示排序升序排序
        return super.findBySql(sql.toString(), sqlParam);
    }

}
