package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelResTbv;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 资源与教材版本关系表
 * 
 * @author lusm 2015/12/10
 */
public interface RelResTbvDao {
    /**
     * 保存资源与教材版本关系
     * 
     * @param relResTbv 对象
     * @return 是否成功
     */
    public boolean saveRelResTbv(RelResTbv relResTbv);

    /**
     * 更新资源与教材版本关系
     * 
     * @param relResTbv 对象
     * @return 是否成功
     */
    public boolean updateRelResTbv(RelResTbv relResTbv);

    /**
     * 保存资源与教材版本关系
     * 
     * @param relResTbv 对象
     * @return 试卷ID
     */
    public int saveRelResTbvAndReturnId(RelResTbv relResTbv);

    /**
     * 删除资源教材版本关系
     * 
     * @param resId 资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 上午11:48:27
     */
    public int deleteRelResTbv(Integer resId);

    /**
     * 保存资源教材版本关系列表
     * 
     * @param list 对象试题
     * @return
     * @author guohao
     */
    public int saveRelResTbvList(List<RelResTbv> list) throws InvocationTargetException, IllegalAccessException;

    /**
     * 删除资源与教材版本关系
     * 
     * @param id 资源id
     * @param tbvCodeList 教材版本代码集合
     */
    public void deleteRelResTbvByParam(Integer id, List<String> tbvCodeList);

    /**
     * 获取资源与教材版本关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与教材版本关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResTbvInfo(Integer resTypeL1, Integer resId) throws Exception;
}