package com.baizhitong.resource.manage.subject.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;

/**
 * 学科业务接口
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:51:40
 */
public interface SubjectService {
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
     * 查询学科列表信息
     * 
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:41:23
     */
    public List<ShareCodeSubjectVo> selectSubjectList(String sectionCode) throws Exception;

    /**
     * 获取资源相关学科
     * 
     * @param resId
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:25:25
     */
    public ShareCodeSubjectVo getResRelateSubject(Integer resId, Integer resTypeL1) throws Exception;

    /**
     * 保存学科 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param name 名称
     * @param description 备注
     * @return
     */
    public ResultCodeVo saveSubject(String sectionCode, String name, String description);
}
