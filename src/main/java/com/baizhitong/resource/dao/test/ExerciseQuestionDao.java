package com.baizhitong.resource.dao.test;

import java.util.List;
import java.util.Map;

/**
 * 
 * 作业题目DAO接口
 * 
 * @author creator ZhangQiang 2017年4月7日 上午10:24:13
 * @author updater
 *
 * @version 1.0.0
 */
public interface ExerciseQuestionDao {
    
    /**
     * 
     * (根据试卷编码和教师信息查询一份试卷教师自己创建的题目)<br>
     * @param testCode 试卷编码
     * @param makerCode 创建人编码
     * @param makerOrgCode 创建人机构编码
     * @return 题目列表
     */
    List<Map<String, Object>> selectQuestionListByMakerCode(String testCode, String makerCode, String makerOrgCode);
    
}
