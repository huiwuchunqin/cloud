package com.baizhitong.resource.dao.res;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.resource.model.res.ResQuestion;

/**
 * 资源_试题数据接口
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
public interface ResQuestionDao {
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
    public Page<Map<String, Object>> selectQuestionPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String kpCode, String questionName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 获取试题信息
     * 
     * @param questionId 试题ID
     * @return 信息
     * @throws Exception 异常
     */
    public Map<String, Object> selectQuestionInfo(Integer questionId) throws Exception;

    /**
     * 根据题目编码获取组合题包含的所有子题目
     * 
     * @param questionCode 题目编码
     * @return 组合题包含的所有子题目
     */
    public List<Map<String, Object>> selectQuestionChildList(String questionCode);

    /**
     * 保存资源信息
     * 
     * @param resQuestion 资源对象
     * @return 是否成功
     */
    public boolean saveQuestionInfo(ResQuestion resQuestion);
}
