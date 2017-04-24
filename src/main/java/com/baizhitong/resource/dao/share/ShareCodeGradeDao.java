package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareCodeGrade;

/**
 * 年级数据接口
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public interface ShareCodeGradeDao {
    /**
     * 获取年级集合
     * 
     * @param sectionCode 学段编码
     * @return 集合
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectGradeList(String sectionCode) throws Exception;

    /************************************************** |以上已确认| **************************************************/
    /**
     * 获取年级集合
     * 
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 下午5:01:58
     */
    public List<Map<String, Object>> selectGradeWithPrefix(String prefix) throws Exception;

    /**
     * 分页查询年级信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月14日 下午4:55:00
     */
    public Page<Map<String, Object>> selectGradePage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception;

    /**
     * 获取资源相关年级
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateGrade(Integer resId, Integer resTypeL1);

    /**
     * 
     * 查询年级学科
     * 
     * @param gradeCode 年级
     * @return
     */
    public List<Map<String, Object>> getGradeSubject(String gradeCode);

    /**
     * 查询年级学科分页信息
     * 
     * @param gradeCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<Map<String, Object>> getGradeSubjectPage(String gradeCode, Integer pageNo, Integer pageSize);

    /**
     * 查询年级 ()<br>
     * 
     * @param gradeCode
     * @return
     */
    public ShareCodeGrade getGrade(String gradeCode);

    /**
     * 查询学科所对应的年级 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    public List<Map<String, Object>> getSubjectGrade(String subjectCode);

    /**
     * 查询所有年级 ()<br>
     * 
     * @return
     */
    public List<ShareCodeGrade> getAllGradeList();

}
