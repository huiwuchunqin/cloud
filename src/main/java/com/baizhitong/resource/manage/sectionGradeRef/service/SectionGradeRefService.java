package com.baizhitong.resource.manage.sectionGradeRef.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.vo.share.SectionGradeVo;

/**
 * 学段年级关系业务接口
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:48:13
 */
public interface SectionGradeRefService {
    /**
     * 学段年级设置列表
     * 
     * @param sectionCode 学段代码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:35:04
     */
    public List<SectionGradeVo> sectionGradeRefSetList(String sectionCode) throws Exception;

    /**
     * 保存学段年级关系
     * 
     * @param sectionCode 学段代码
     * @param gradeCodeArray 年级编码数组
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 下午2:00:07
     */
    public void saveSectionGradeList(String sectionCode, String[] gradeCodeArray) throws Exception;

    /**
     * 
     * (根据学段查询学段年级关系列表)<br>
     * 
     * @param sectionCode
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryListBySectionCode(String sectionCode) throws Exception;

}
