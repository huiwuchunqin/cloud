package com.baizhitong.resource.dao.res.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.res.ResExerciseQuestionsDao;
import com.baizhitong.resource.model.res.ResExerciseQuestions;

/**
 * 资源_练习结构数据MySql接口实现
 * 
 * @author creator shancy 2015/12/07
 * @author updater
 */
@Service("resExerciseQuestionsMySqlDao")
public class ResExerciseQuestionsMySqlDaoImpl extends BaseMySqlDao<ResExerciseQuestions, Integer>
                implements ResExerciseQuestionsDao {
    /**
     * 根据练习ID、练习结构ID获取该练习的练习试题
     * 
     * @param exerciseId 练习ID
     * @param exerciseStructureId 练习结构ID
     * @return 该练习的练习试题
     * @throws Exception 异常
     */
    @Override
    public List<Map<String, Object>> selectExerciseQuestionList(Integer exerciseId, Integer exerciseStructureId)
                    throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT req.id AS exerciseQuestionId, req.exerciseId, req.questionId, req.exerciseStructureID, ");
        sql.append(" rq.questionCode, ");
        sql.append(" rq.typeAtomCode, rqta.typeName AS typeAtomName, ");
        sql.append(" rq.typeCode, rqt.typeName, ");
        sql.append(" rq.preTextId, rqbtp.content AS preTextName, ");
        sql.append(" rq.bodyTextId, rqbtb.content AS bodyTextName, ");
        sql.append(" rq.optionTextId, rqbto.content AS optionTextName, ");
        sql.append(" rq.answerTextId, rqbtaw.content AS answerTextName, ");
        sql.append(" rq.analysisTextId, rqbtay.content AS analysisTextName, ");
        sql.append(" rq.difficulty, rq.correctRate, rq.discrimination, rq.canRandom, rq.groupOrder, rq.orderInGroup, rq.canRandomGroup, ");
        sql.append(" rq.canSeperate, rq.score, rq.estimateAnswerSeconds, rq.usedCount, rq.answerCount, rq.flagAnswerShare, rq.blankNum, ");
        sql.append(" rq.criterionTextId, rq.mediaObjectId, rq.mediaStartTime, rq.mediaEndTime, rq.showableEngine, rq.creator, rq.createTime ");
        sql.append(" FROM res_exercise_questions req ");
        sql.append(" INNER JOIN res_question rq ON rq.id = req.questionId ");
        sql.append(" INNER JOIN res_question_type_atom rqta ON rqta.validStatus = 1 AND rqta.typeCode = rq.typeAtomCode ");
        sql.append(" INNER JOIN res_question_type rqt ON rqt.validStatus = 1 AND rqt.typeCode = rq.typeCode ");
        sql.append(" LEFT JOIN res_question_big_text rqbtp ON rqbtp.id = rq.preTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbtb ON rqbtb.id = rq.bodyTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbto ON rqbto.id = rq.optionTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbtaw ON rqbtaw.id = rq.answerTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbtay ON rqbtay.id = rq.analysisTextId ");
        sql.append(" WHERE rq.groupOrder = 1 AND rq.orderInGroup = 1 AND rq.status = 1 AND rq.flagPublic = 1 AND rq.checkStatus = 20 ");
        // 判断练习ID是否为空
        if (exerciseId != null) {
            sql.append(" AND req.exerciseId = :exerciseId ");
        }
        // 判断练习结构ID是否为空
        if (exerciseStructureId != null) {
            sql.append(" AND req.exerciseStructureId = :exerciseStructureId ");
        }
        sql.append(" ORDER BY req.id ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exerciseId", exerciseId);
        map.put("exerciseStructureId", exerciseStructureId);

        // SQL执行
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 根据练习ID、试题ID获取练习试题
     * 
     * @param exerciseId 练习ID
     * @param questionId 试题ID
     * @return 练习试题
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectExerciseQuestionMap(Integer exerciseId, Integer questionId) throws Exception {
        return null;
    }

    /**
     * 创建练习试题
     * 
     * @param resTestpaper 练习试题对象
     * @return 练习试题ID
     * @throws Exception 异常
     */
    @Override
    public Integer saveExerciseQuestions(ResExerciseQuestions resExerciseQuestions) throws Exception {
        return null;
    }

    /**
     * 更新练习试题
     * 
     * @param resExerciseQuestions 练习试题对象
     * @return 更新行数
     * @throws Exception 异常
     */
    public int updateExerciseStructure(ResExerciseQuestions resExerciseQuestions) throws Exception {
        return 0;
    }

    /**
     * 根据练习ID、试题ID删除试卷试题
     * 
     * @param exerciseId 练习ID
     * @param questionId 试题ID
     * @throws Exception 异常
     */
    @Override
    public void deleteExerciseQuestions(Integer exerciseId, Integer questionId) throws Exception {

    }
}