package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareCodeSection;

/**
 * 学段数据接口
 * 
 * @author creator Cuidc 2015/12/03
 * @author updater
 */
public interface ShareCodeSectionDao {

    /**
     * 获取学段集合
     * 
     * @return 集合
     * @throws Exception 异常
     */
    public List<ShareCodeSection> selectSectionList() throws Exception;

    /************************************************** |以上已确认| **************************************************/
    /**
     * 分页查询学段信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月14日 下午4:55:00
     */
    public Page<Map<String, Object>> selectSectionPage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception;

    /**
     * 查询学段学科
     * 
     * @param sectionCode
     * @return
     * @author gaow
     * @date:2015年12月15日 上午10:36:06
     */
    public List<Map<String, Object>> getSectionSubject(String sectionCode);

    /**
     * 查询学段年级
     * 
     * @param sectionCode
     * @return
     * @author gaow
     * @date:2015年12月15日 上午10:36:06
     */
    public List<Map<String, Object>> getSectionGrade(String sectionCode);

    /**
     * 查询学段学科
     * 
     * @param sectionCode 学段
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @author gaow
     * @date:2015年12月15日 上午10:36:06
     */
    public Page<Object> getSectionSubjectPage(String sectionCode, Integer pageNo, Integer pageSize);

    /**
     * 查询学段年级
     * 
     * @param sectionCode
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @author gaow
     * @date:2015年12月15日 上午10:36:06
     */
    public Page<Map<String, Object>> getSectionGradePage(String sectionCode, Integer pageSize, Integer pageNo);

    /**
     * 获取资源相关学段
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateSection(Integer resId, Integer resTypeL1);

    /**
     * 查询学段 ()<br>
     * 
     * @param sectionCode 学段编码
     * @return
     */
    public ShareCodeSection getSection(String sectionCode);

}
