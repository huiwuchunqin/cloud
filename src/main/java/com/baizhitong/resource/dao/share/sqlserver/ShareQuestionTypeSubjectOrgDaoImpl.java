package com.baizhitong.resource.dao.share.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareQuestionTypeSubjectOrgDao;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubjectOrg;

@Repository
public class ShareQuestionTypeSubjectOrgDaoImpl extends BaseSqlServerDao<ShareQuestionTypeSubjectOrg>
                implements ShareQuestionTypeSubjectOrgDao {
    /**
     * 查询机构学科题型 ()<br>
     * 
     * @param subjectCode
     * @param orgCode
     * @return
     */
    public List<ShareQuestionTypeSubjectOrg> getBySubject(String subjectCode, String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        qr.andEqual("subjectCode", subjectCode);
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }

    /**
     * 新增机构学科题型 ()<br>
     * 
     * @param shareQuestionTypeSubjectOrg
     * @return
     */
    public int add(List<ShareQuestionTypeSubjectOrg> shareQuestionTypeSubjectOrgList) {

        try {
            return super.saveAll(shareQuestionTypeSubjectOrgList);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            return 0;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            return 0;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            return 0;
        }

    }

    /**
     * 新增或者更新 ()<br>
     * 
     * @param shareQuestionTypeSubjectOrg
     * @return
     */
    public boolean saveOrUpdate(ShareQuestionTypeSubjectOrg shareQuestionTypeSubjectOrg) {
        try {
            return super.saveOne(shareQuestionTypeSubjectOrg);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            return false;
        }
    }

    /**
     * 查询机构学科题型 ()<br>
     * 
     * @param subjectCode
     * @param orgCode
     * @param questionType
     * @param name
     * @return
     */
    public ShareQuestionTypeSubjectOrg getOrgQuestionType(String subjectCode, String orgCode, String questionType,
                    String name) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("subjectCode", subjectCode);
        qr.andEqual("orgCode", orgCode);
        qr.andEqual("questionType", questionType);
        qr.andEqual("name", name);
        return super.findUnique(qr);

    }

    /**
     * 删除机构学科 ()<br>
     * 
     * @param gid
     * @return
     */
    public int delete(String gid) {
        String sql = "delete from  share_question_type_subject_org where gid=?";
        return super.update(sql, gid);
    }
}
