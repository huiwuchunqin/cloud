package com.baizhitong.resource.dao.share.sqlserver;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareRelGradeSubjectDao;
import com.baizhitong.resource.model.share.ShareRelGradeSubject;

@Service
public class ShareRelGradeSubjectSqlServerDaoImpl extends BaseSqlServerDao<ShareRelGradeSubject>
                implements ShareRelGradeSubjectDao {

    /**
     * 删除年级学科关系 ()<br>
     * 
     * @param gradeCode 年级编码
     */
    public void delGradeSubject(String gradeCode) {
        String sql = "delete from share_rel_grade_subject where gradeCode=?";
        super.update(sql, gradeCode);

    }

    /**
     * 保存年级学科关系 ()<br>
     * 
     * @param relGradeSubjectList
     * @throws Exception
     */
    public void saveGradeSubjectRef(List<ShareRelGradeSubject> relGradeSubjectList) throws Exception {
        super.saveAll(relGradeSubjectList);

    }

}
