package com.baizhitong.resource.manage.grade.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;

/**
 * 年级业务接口
 * 
 * @author gaow
 * @date:2015年12月21日 上午9:53:20
 */
public interface GradeService {
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
     * 查询年级列表信息
     * 
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:41:23
     */
    public List<ShareCodeGradeVo> selectGradeList(String sectionCode) throws Exception;

    /**
     * 获取年级相关资源
     * 
     * @param resId 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:23:28
     */
    public ShareCodeGradeVo getResRelateGrade(Integer resId, Integer resTypeL1) throws Exception;

    /**
     * 查询学科年级 ()<br>
     * 
     * @param subjectCode
     * @return
     * @throws Exception
     */
    public List<ShareCodeGradeVo> getSubjectGrade(String subjectCode) throws Exception;
}
