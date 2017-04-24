package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelResKp;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 资源与知识点关系表
 * 
 * @author lusm 2015/12/10
 */
public interface RelResKpDao {
    /**
     * 保存资源与知识点关系
     * 
     * @param relResKp 对象
     * @return 是否成功
     */
    public boolean saveRelResKp(RelResKp relResKp);

    /**
     * 更新资源与知识点关系
     * 
     * @param relResKp 对象
     * @return 是否成功
     */
    public boolean updateRelResKp(RelResKp relResKp);

    /**
     * 保存资源与知识点关系
     * 
     * @param relResKp 对象
     * @return 资源ID
     */
    public int saveRelResKpAndReturnId(RelResKp relResKp);

    /**
     * 删除资源知识点关系
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午12:01:41
     */
    public int delRelResKp(Integer resId);

    /**
     * 保存资源知识点关系列表
     * 
     * @param list
     * @return
     */
    public int saveRelResKpList(List<RelResKp> list) throws InvocationTargetException, IllegalAccessException;

    /**
     * 删除资源与知识点集合
     * 
     * @param id 资源id
     * @param kpCodeList 知识点集合
     */
    public void deleteRelResKpByParam(Integer id, List<String> kpCodeList);

    /**
     * 删除资源与知识点集合
     * 
     * @param id 资源id
     * @param kpCode 知识点编码
     */
    public void deleteRelResKpByParam(Integer id, String kpCode);

    /**
     * 获取资源与知识点关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与知识点关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResKpInfo(Integer resTypeL1, Integer resId) throws Exception;

    /**
     * 根据资源ID和知识点编码获取资源与知识点关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @param kpCode 知识点编码
     * @return 资源与知识点关系信息
     */
    public Map<String, Object> selectRelResKpMap(Integer resTypeL1, Integer resId, String kpCode);
}