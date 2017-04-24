package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelResTbc;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 资源与教材章节关系表
 * 
 * @author lusm 2015/12/10
 */
public interface RelResTbcDao {
    /**
     * 保存资源与教材章节关系
     * 
     * @param relResTbc 对象
     * @return 是否成功
     */
    public boolean saveRelResTbc(RelResTbc relResTbc);

    /**
     * 更新资源与教材章节关系
     * 
     * @param relResTbc 对象
     * @return 是否成功
     */
    public boolean updateRelResTbc(RelResTbc relResTbc);

    /**
     * 保存资源与教材章节关系
     * 
     * @param relResTbc 对象
     * @return 资源ID
     */
    public int saveRelResTbcAndReturnId(RelResTbc relResTbc);

    /**
     * 删除资源章节关系
     * 
     * @param resId 资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 上午11:50:59
     */
    public int delRelResTbc(Integer resId);

    /**
     * 保存资源与教材章节关系列表
     * 
     * @param list 对象列表
     * @return
     */
    public int saveRelResTbcList(List<RelResTbc> list) throws InvocationTargetException, IllegalAccessException;

    /**
     * 删除资源与教材章节关系
     * 
     * @param id 资源id
     * @param chapterList 资源与章节列表
     */
    public void deleteRelResTbcByParam(Integer id, List<String> chapterList);

    /**
     * 删除资源与教材章节关系
     * 
     * @param id 资源id
     * @param chapterList 章节编码
     */
    public void deleteRelResTbcByParam(Integer id, String chapterCode);

    /**
     * 获取资源与教材章节关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与教材章节关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResTbcInfo(Integer resTypeL1, Integer resId) throws Exception;

    /**
     * 根据资源ID和教材章节编码获取资源与教材章节关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @param tbcCode 教材章节编码
     * @return 资源与教材章节关系信息
     */
    public Map<String, Object> selectRelResTbcMap(Integer resTypeL1, Integer resId, String tbcCode);
}