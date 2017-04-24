package com.baizhitong.resource.manage.furtherEducation.service;

import java.util.List;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;

/**
 * 
 * 机构升学接口 IFurtherEducationService TODO
 * 
 * @author creator zhangqd 2016年9月9日 下午2:41:27
 * @author updater
 *
 * @version 1.0.0
 */
public interface IFurtherEducationService {

    /**
     * 
     * (更新机构学年学期等信息)<br>
     * 
     * @param orgCode 机构编码
     * @return 操作结果
     */
    ResultCodeVo updateStudyYearTerm(String orgCode);

    /**
     * 查询需要更新的行政班级 ()<br>
     * 
     * @param orgCode
     * @return
     */
    List getAdminClassToUpdate(String orgCode);
    
    /**
     * 查询有任教信息需要更新的行政班级
     * ()<br>
     * @param orgCode
     * @return
     */
    List getAdminClassWidthTeaInfo(String orgCode);

    /**
     * 更新行政班级 ()<br>
     * 
     * @param orgCode机构编码
     * @return
     */
    public ResultCodeVo updateAdminClass(String orgCode);

  /**
   * 更新任教信息
   * ()<br>
   * @param adminClassCodes 行政班级编码
   * @param newGradeCode    新的年级编码
   * @param orgCode         机构编码
   * @return
   */
    public ResultCodeVo updateTeachingInfo();

}
