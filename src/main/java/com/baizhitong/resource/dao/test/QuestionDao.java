package com.baizhitong.resource.dao.test;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.test.Question;

/**
 * 
 * QuestionDao
 * 
 * @author creator BZT 2016年8月9日 下午3:49:33
 * @author updater
 *
 * @version 1.0.0
 */
public interface QuestionDao {
    /**
     * 根据题目编号查询题目 ()<br>
     * 
     * @param questionCode
     * @return
     */
    public Question findbyQuestionCode(String questionCode);

    /**
     * 
     * 改变题目审核状态
     * 
     * @param questionCode 题目编码
     * @param checkStatus 审核状态
     * @param modifierIP 更新者IP
     * @param modifier 更新者姓名
     */
    public int updateCheckStatus(String questionCode, String checkStatus,String modifierIP,String modifier);

    /**
     * 查询题目信息 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getQuestionPage(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 删除题目 ()<br>
     * 
     * @param ids
     * @return
     */
    public int deleteQuestion(String ids, String modifier, String modifierIP);

    /**
     * 
     * (查询学科统计信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    public List<Map<String, Object>> selectSubjectReportInfo(Map<String, Object> param);

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    public Map<String, Object> selectSubjectReportTotalNum(Map<String, Object> param);

    /**
     * 
     * (批量操作题目)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip);

    /**
     * 
     * (查询学科使用情况统计信息)<br>
     * 
     * @param param 查询参数
     * @return 学科使用情况统计列表
     */
    public List<Map<String, Object>> selectSubjectUsageReportInfo(Map<String, Object> param);

    /**
     * 
     * (查询学科使用情况统计总数)<br>
     * 
     * @param param 查询参数
     * @return 学科使用情况统计总数
     */
    public Map<String, Object> selectSubjectUsageReportTotalNum(Map<String, Object> param);

    /**
     * 
     * (更新题目的共享级别)<br>
     * @param questionCode 题目编码
     * @param shareLevel 共享级别
     * @param shareCheckStatus 共享审核状态
     * @param modifier 更新者
     * @param modifierIP 更新者IP
     * @return 更新记录数
     */
    public int updateShareLevel(String questionCode, Integer shareLevel, Integer shareCheckStatus, String modifier,
                    String modifierIP);
    
}
