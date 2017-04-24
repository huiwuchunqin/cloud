package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelResSubject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 资源与学科关系表
 * 
 * @author shancy 2015/12/02
 */
public interface RelResSubjectDao {
    /**
     * 保存资源与学科关系
     * 
     * @param relResSubject 对象
     * @return 是否成功
     */
    public boolean saveRelResSubject(RelResSubject relResSubject);

    /**
     * 更新资源与学科关系
     * 
     * @param relResSubject 对象
     * @return 是否成功
     */
    public boolean updateRelResSubject(RelResSubject relResSubject);

    /**
     * 保存资源与学科关系
     * 
     * @param relResSubject 对象
     * @return 资源ID
     */
    public int saveRelResSubjectAndReturnId(RelResSubject relResSubject);

    /**
     * 删除资源学科关系
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 上午11:44:39
     */
    public int delResResSubject(Integer resId);

    /**
     * 保存资源与学科关系列表
     * 
     * @param list
     * @return
     */
    public int saveRelResSubjectList(List<RelResSubject> list) throws InvocationTargetException, IllegalAccessException;

    /**
     * 删除资源与学科关系
     * 
     * @param id 资源id
     * @param subjectCodeList 学科代码集合
     */
    public void deleteRelResSubjectByParam(Integer id, List<String> subjectCodeList);

    /**
     * 获取资源与学科关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与学科关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResSubjectInfo(Integer resTypeL1, Integer resId) throws Exception;

}