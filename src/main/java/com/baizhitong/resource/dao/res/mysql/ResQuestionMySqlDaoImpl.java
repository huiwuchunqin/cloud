package com.baizhitong.resource.dao.res.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.res.ResQuestionDao;
import com.baizhitong.resource.model.res.ResQuestion;
import com.baizhitong.utils.StringUtils;

/**
 * 资源_试题数据MySql接口实现
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
@Service("resQuestionMySqlDao")
public class ResQuestionMySqlDaoImpl extends BaseMySqlDao<ResQuestion, Integer> implements ResQuestionDao {

    /**
     * 获取试题分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param kpCode 知识点编码
     * @param questionName 试题名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 试题分页集合
     * @throws Exception 异常
     */
    @Override
    public Page<Map<String, Object>> selectQuestionPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String kpCode, String questionName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rq.id AS questionId, rq.questionCode, ");
        sql.append(" rq.typeAtomCode, rqta.typeName AS typeAtomName, ");
        sql.append(" rq.typeCode, rqt3.typeName, ");
        sql.append(" rq.preTextId, rqbtp.content AS preTextName, ");
        sql.append(" rq.bodyTextId, rqbtb.content AS bodyTextName, ");
        sql.append(" rq.optionTextId, rqbto.content AS optionTextName, ");
        sql.append(" rq.answerTextId, rqbtaw.content AS answerTextName, ");
        sql.append(" rq.analysisTextId, rqbtay.content AS analysisTextName, ");
        sql.append(" rq.difficulty, rq.correctRate, rq.discrimination, rq.canRandom, rq.groupOrder, rq.orderInGroup, rq.canRandomGroup, ");
        sql.append(" rq.canSeperate, rq.score, rq.estimateAnswerSeconds, rq.usedCount, rq.answerCount, rq.flagAnswerShare, rq.blankNum, ");
        sql.append(" rq.criterionTextId, rq.mediaObjectId, rq.mediaStartTime, rq.mediaEndTime, rq.showableEngine, rq.creator, rq.createTime ");
        sql.append(" FROM res_question rq ");
        sql.append(" INNER JOIN res_question_type_atom rqta ON rqta.validStatus = 1 AND rqta.typeCode = rq.typeAtomCode ");
        sql.append(" INNER JOIN res_question_type rqt3 ON rqt3.validStatus = 1 AND rqt3.typeCode = rq.typeCode ");
        sql.append(" LEFT JOIN rel_question_subject rqs ON rqs.resId = rq.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rqs.subjectCode ");
        sql.append(" LEFT JOIN rel_question_grade rqg ON rqg.resId = rq.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rqg.gradeCode ");
        sql.append(" LEFT JOIN rel_question_tbv rqt ON rqt.resId = rq.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rqt.tbvCode ");
        sql.append(" LEFT JOIN rel_question_kp rqk ON rqk.resId = rq.id ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rqk.kpCode ");
        sql.append(" LEFT JOIN rel_question_tbc rqt2 ON rqt2.resId = rq.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rqt2.tbcCode ");
        sql.append(" LEFT JOIN res_question_big_text rqbtp ON rqbtp.id = rq.preTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbtb ON rqbtb.id = rq.bodyTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbto ON rqbto.id = rq.optionTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbtaw ON rqbtaw.id = rq.answerTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbtay ON rqbtay.id = rq.analysisTextId ");
        sql.append(" WHERE rq.groupOrder = 1 AND rq.orderInGroup = 1 AND rq.status = 1 AND rq.flagPublic = 1 AND rq.checkStatus = 20 ");
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode)) {
            sql.append(" AND rqg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND rqs.subjectCode = :subjectCode ");
        }
        // 判断版本编码是否为空
        if (!StringUtils.isEmpty(versionCode)) {
            sql.append(" AND rqt.tbvCode = :versionCode ");
        }
        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND rq.resTypeL2 = :resTypeL2 ");
        }
        // 判断试题名称是否为空
        if (!StringUtils.isEmpty(questionName)) {
            sql.append(" AND rq.questionName LIKE '%" + questionName + "%' ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            sql.append(" AND rqk.kpCode LIKE '" + kpCode + "%' ");
        }

        // 排序方式(null:默认,10:最热,20:最新)
        if (!StringUtils.isEmpty(sort) && "10".equals(sort)) {
            sql.append(" ORDER BY rq.usedCount DESC, rq.id ");
        } else if (!StringUtils.isEmpty(sort) && "20".equals(sort)) {
            sql.append(" ORDER BY rq.createTime DESC, rq.id ");
        } else {
            sql.append(" ORDER BY rq.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", versionCode);
        map.put("kpCode", kpCode);
        map.put("docName", questionName);
        map.put("resTypeL2", resTypeL2);
        map.put("sort", sort);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, pageNo, pageSize);
    }

    /**
     * 获取试题信息
     * 
     * @param questionId 试题ID
     * @return 信息
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectQuestionInfo(Integer questionId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rq.id AS questionId, rq.questionCode, ");
        sql.append(" rq.typeAtomCode, rqta.typeName AS typeAtomName, ");
        sql.append(" rq.typeCode, rqt3.typeName, ");
        sql.append(" rq.difficulty, rq.correctRate, rq.discrimination, rq.canRandom, rq.groupOrder, rq.orderInGroup, rq.canRandomGroup, ");
        sql.append(" rq.canSeperate, rq.score, rq.estimateAnswerSeconds, rq.usedCount, rq.answerCount, rq.flagAnswerShare, rq.blankNum, ");
        sql.append(" rq.criterionTextId, rq.mediaObjectId, rq.mediaStartTime, rq.mediaEndTime, rq.showableEngine, rq.creator, rq.createTime, ");
        sql.append("   ");

        sql.append(" FROM res_question rq ");
        sql.append(" INNER JOIN res_question_type_atom rqta ON rqta.validStatus = 1 AND rqta.typeCode = rq.typeAtomCode ");
        sql.append(" INNER JOIN res_question_type rqt3 ON rqt3.validStatus = 1 AND rqt3.typeCode = rq.typeCode ");
        sql.append(" LEFT JOIN rel_question_subject rqs ON rqs.resId = rq.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rqs.subjectCode ");
        sql.append(" LEFT JOIN rel_question_grade rqg ON rqg.resId = rq.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rqg.gradeCode ");
        sql.append(" LEFT JOIN rel_question_tbv rqt ON rqt.resId = rq.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rqt.tbvCode ");
        sql.append(" LEFT JOIN rel_question_kp rqk ON rqk.resId = rq.id ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rqk.kpCode ");
        sql.append(" LEFT JOIN rel_question_tbc rqt2 ON rqt2.resId = rq.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rqt2.tbcCode ");
        sql.append(" WHERE rq.groupOrder = 1 AND rq.orderInGroup = 1 AND rq.status = 1 AND rq.flagPublic = 1 AND rq.checkStatus = 20 ");
        sql.append(" WHERE rq.id = :questionId ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exerciseId", questionId);

        // SQL执行
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 根据题目编码获取组合题包含的所有子题目
     * 
     * @param questionCode 题目编码
     * @return 组合题包含的所有子题目
     */
    @Override
    public List<Map<String, Object>> selectQuestionChildList(String questionCode) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rq.questionCode, ");
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
        sql.append(" FROM res_question rq ");
        sql.append(" INNER JOIN res_question_type_atom rqta ON rqta.validStatus = 1 AND rqta.typeCode = rq.typeAtomCode ");
        sql.append(" INNER JOIN res_question_type rqt ON rqt.validStatus = 1 AND rqt.typeCode = rq.typeCode ");
        sql.append(" LEFT JOIN res_question_big_text rqbtp ON rqbtp.id = rq.preTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbtb ON rqbtb.id = rq.bodyTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbto ON rqbto.id = rq.optionTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbtaw ON rqbtaw.id = rq.answerTextId ");
        sql.append(" LEFT JOIN res_question_big_text rqbtay ON rqbtay.id = rq.analysisTextId ");
        sql.append(" WHERE (rq.groupOrder != 1 OR rq.orderInGroup != 1) AND rq.status = 1 AND rq.flagPublic = 1 AND rq.checkStatus = 20 ");
        sql.append(" AND rq.questionCode = :questionCode ");
        sql.append(" ORDER BY rq.groupOrder, rq.orderInGroup ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("questionCode", questionCode);

        // SQL执行
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 保存资源信息
     * 
     * @param resQuestion 资源对象
     * @return 是否成功
     */
    @Override
    public boolean saveQuestionInfo(ResQuestion resQuestion) {
        boolean success = false;
        try {
            success = super.saveOne(resQuestion);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

}