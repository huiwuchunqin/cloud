package com.baizhitong.resource.manage.res.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.lesson.LessonDao;
import com.baizhitong.resource.dao.lesson.LessonHomeSetDao;
import com.baizhitong.resource.manage.res.service.LessonHomeSetService;

/**
 * 课程封面设置接口 LessonHomeSetServiceImpl
 * 
 * @author creator gaow 2017年3月20日 下午2:40:24
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class LessonHomeSetServiceImpl extends BaseService implements LessonHomeSetService {

    /** 课时首页推荐设置数据接口 */
    @Autowired
    private LessonHomeSetDao lessonHomeSetDao;
    /** 课时数据接口 */
    @Autowired
    private LessonDao        lessonDao;

    /**
     * 查询首页课程 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Page<Map<String, Object>> selectPage(Map<String, Object> param, Integer page, Integer rows) {
        return lessonHomeSetDao.selectPage(param, page, rows); 
    }

    /**
     * 是否使用 ()<br>
     * 
     * @param id
     * @param available
     * @return
     */
    @Override
    public ResultCodeVo update(Integer id, Integer available) {
        if (available == 1) {
            long availableCount = lessonHomeSetDao.selectAvailableCount();
            if (availableCount >= 6) {
                return ResultCodeVo.errorCode("设置显示的课程已达上限，不能继续操作！");
            }
        }
        String msg = "";
        if (available == 1) {
            msg = "设置成使用,";
        } else {
            msg = "取消使用,";
        }
        return lessonHomeSetDao.update(id, available) > 0 ? ResultCodeVo.rightCode(msg + "操作成功")
                        : ResultCodeVo.errorCode(msg + "操作失败");
    }

    /**
     * 
     * 设置为课程首页
     * 
     * @param lessonName
     *        课程名称
     * @param lessonCode
     *        课程编码
     * @param lessonId
     *        课程id
     * @param orgCode
     *        机构编码
     * @param sectionCode
     *        学段编码
     * @param gradeCode
     *        年级编码
     * @param subjectCode
     *        学科编码
     * @param coverPath
     *        封面地址
     * @param thumbnailPath
     *        缩略图地址
     */
    @Override
    public ResultCodeVo setCourseHome(String lessonName, String lessonCode, Integer lessonId, String orgCode,
                    String sectionCode, String gradeCode, String subjectCode, String coverPath, String thumbnailPath,
                    boolean available) {
        int addNum=lessonHomeSetDao.setCourseHome(lessonName, lessonCode, lessonId, orgCode, sectionCode, gradeCode, subjectCode,
                        coverPath, thumbnailPath, getUserInfoVo().getUserName(), available);
        if(0<addNum){
            return ResultCodeVo.rightCode("设置成功");
        }else{
            return ResultCodeVo.errorCode("设置失败");
        }
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    @Override
    public ResultCodeVo delete(Integer id) {
        return lessonHomeSetDao.delete(id) > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }

    /**
     * 
     * (查询可以设置在首页显示的课程信息)<br>
     * @param param 查询参数
     * @param page 当前页码
     * @param rows 每页大小
     * @return 课程信息列表
     */
    @Override
    public Page<Map<String, Object>> queryLessonHomeSettingPage(Map<String, Object> param, Integer page, Integer rows) {
        return lessonDao.selectLessonHomeSettingPage(param, page, rows);
    }

}
