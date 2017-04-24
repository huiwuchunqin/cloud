package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.share.ShareCodeSubject;

/**
 * 学科数据接口
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public interface ShareCodeSubjectDao {
    /**
     * 获取学科集合
     * 
     * @param sectionCode 学段编码
     * @return 集合
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectSubjectList(String sectionCode) throws Exception;

    /************************************************** |以上已确认| **************************************************/
    /**
     * 获取学科集合
     * 
     * @return 集合
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectSubjectWithPrefix(String prefix) throws Exception;

    /**
     * 分页查询学科信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月14日 下午4:55:00
     */
    public Page<Map<String, Object>> selectSubjectPage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception;

    /**
     * 获取资源相关学科
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateSubject(Integer resId, Integer resTypeL1);

    /**
     * 查询学科 ()<br>
     * 
     * @param subjectCode 学科编码
     * @return
     */
    public ShareCodeSubject getSubject(String subjectCode);

    /**
     * 查询最大的编码 ()<br>
     * 
     * @param sectionCode 学段编码
     * @return
     */
    public String getMaxCode(String sectionCode);

    /**
     * 保存学科 ()<br>
     * 
     * @param subject
     */
    public void saveSubject(ShareCodeSubject subject);

    /**
     * 查询下一个排序序号 ()<br>
     * 
     * @return
     */
    public Integer getNextDispOrder();

    /**
     * 查询学段下同名的学科 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     */
    public List<Map<String, Object>> getSameNameSubject(String sectionCode, String subjectCode);

    /**
     * 查询所有学科 ()<br>
     * 
     * @return
     */
    public List<ShareCodeSubject> getSubjectList();
}
