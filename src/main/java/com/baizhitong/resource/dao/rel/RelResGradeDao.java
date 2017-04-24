package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelResGrade;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 资源与年级关系表
 * 
 * @author lusm 2015/12/10
 */
public interface RelResGradeDao {
    /**
     * 保存资源与年级关系
     * 
     * @param relResGrade 对象
     * @return 是否成功
     */
    public boolean saveRelResGrade(RelResGrade relResGrade);

    /**
     * 更新资源与年级关系
     * 
     * @param relResGrade 对象
     * @return 是否成功
     */
    public boolean updateRelResGrade(RelResGrade relResGrade);

    /**
     * 保存资源与年级关系
     * 
     * @param relResGrade 对象
     * @return 资源ID
     */
    public int saveRelResGradeAndReturnId(RelResGrade relResGrade);

    /**
     * 删除资源年级关系
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午12:11:27
     */
    public int delResResGrade(Integer resId);

    /**
     * 保存资源与年级关系
     * 
     * @param list
     * @return
     */
    public int saveRelResGradeList(List<RelResGrade> list) throws InvocationTargetException, IllegalAccessException;

    /**
     * 删除资源与年级关系列表
     * 
     * @param id 资源id
     * @param gradeCodeList 年级集合
     */
    public void deleteRelResGradeByParam(Integer id, List<String> gradeCodeList);

    /**
     * 获取资源与年级关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与年级关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResGradeInfo(Integer resTypeL1, Integer resId) throws Exception;
}