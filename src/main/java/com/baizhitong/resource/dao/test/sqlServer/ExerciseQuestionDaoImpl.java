package com.baizhitong.resource.dao.test.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.test.ExerciseQuestionDao;
import com.baizhitong.resource.model.test.ExerciseQuestion;

/**
 * 
 * 作业题目DAO接口实现类
 * 
 * @author creator ZhangQiang 2017年4月7日 上午10:32:03
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ExerciseQuestionDaoImpl extends BaseSqlServerDao<ExerciseQuestion> implements ExerciseQuestionDao{

    /**
     * 
     * (根据试卷编码和教师信息查询一份试卷教师自己创建的题目)<br>
     * @param testCode 试卷编码
     * @param makerCode 创建人编码
     * @param makerOrgCode 创建人机构编码
     * @return 题目列表
     */
    @Override
    public List<Map<String, Object>> selectQuestionListByMakerCode(String testCode, String makerCode,
                    String makerOrgCode) {
        StringBuffer sql=new StringBuffer();
        Map<String,Object> sqlParam=new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        eq.questionCode ");
        sql.append("    FROM");
        sql.append("        exercise_question eq ");
        sql.append("    INNER JOIN");
        sql.append("        question q ");
        sql.append("            ON eq.questionCode = q.questionCode ");
        sql.append("    WHERE");
        sql.append("        eq.flagDelete = :flagDelete ");
        sql.append("        AND q.flagDelete = :flagDelete ");
        sql.append("        AND eq.testCode = :testCode ");
        sql.append("        AND q.makerCode = :makerCode ");
        sql.append("        AND q.makerOrgCode = :makerOrgCode ");        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        sqlParam.put("testCode", testCode);
        sqlParam.put("makerCode", makerCode);
        sqlParam.put("makerOrgCode", makerOrgCode);
        return super.findBySql(sql.toString(), sqlParam);
    }
    
}
