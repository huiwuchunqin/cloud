package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelResSection;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by guohao on 2015/12/19.
 */
public interface RelResSectionDao {

    /**
     * 保存资源与学段关系liberty
     * 
     * @param list
     * @return
     */
    public int saveRelResSectionList(List<RelResSection> list) throws InvocationTargetException, IllegalAccessException;

    /**
     * 删除资源与学段关系
     * 
     * @param id 资源id
     * @param sectionCodeList 学段code集合
     */
    public void deleteRelResSectionByParam(Integer id, List<String> sectionCodeList);

    /**
     * 获取资源与学段关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与学段关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResSectionInfo(Integer resTypeL1, Integer resId) throws Exception;

    /**
     * 删除资源学段关系
     * 
     * @param resId 资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 上午11:44:39
     */
    public int delResResSection(Integer resId);

    /**
     * 保存学段资源关系
     * 
     * @param section
     * @return
     * @author gaow
     * @date:2015年12月23日 上午10:15:14
     */
    public boolean saveRelResSection(RelResSection section);
}
