package com.baizhitong.resource.dao.res.mysql;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.res.ResTestpaperDao;
import com.baizhitong.resource.model.res.ResTestpaper;

/**
 * 资源_试卷数据MySql接口实现
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
@Service("resTestpaperMySqlDao")
public class ResTestpaperMySqlDaoImpl extends BaseMySqlDao<ResTestpaper, Integer> implements ResTestpaperDao {

    /**
     * 获取试卷分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param testpaperName 试卷名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 试卷分页集合
     * @throws Exception 异常
     */
    @Override
    public Page<Map<String, Object>> selectTestpaperPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String testpaperName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception {
        return null;
    }

    /**
     * 获取试卷信息
     * 
     * @param testpaperId 试卷ID
     * @return 信息
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectTestpaperInfo(Integer testpaperId) throws Exception {
        return null;
    }

    /**
     * 根据试卷ID获取试卷信息
     * 
     * @param testpaperId 试卷ID
     * @return 信息
     * @throws Exception 异常
     */
    public Map<String, Object> selectTestpaper(Integer testpaperId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * ");
        sql.append(" FROM res_exercise re ");
        sql.append(" WHERE re.id = :exerciseId ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exerciseId", testpaperId);

        // SQL执行
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 获取试卷预览信息
     * 
     * @param testpaperId 试卷ID
     * @return 信息
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectTestpaperPreviewInfo(Integer testpaperId) throws Exception {
        return null;
    }

    /**
     * 生成试卷
     * 
     * @param testpaperId 试卷ID
     * @param testpaperName 试卷名称
     * @param kpCode 知识点编码
     * @return
     * @throws Exception 异常
     */
    @Override
    public void generateTestpaper(Integer testpaperId, String testpaperName, String kpCode) throws Exception {

    }

    /**
     * 根据resID获取资源对象
     * 
     * @param resID 资源ID
     * @return 资源对象
     */
    @Override
    public ResTestpaper selectResTestpaper(Integer resID) {
        return super.get(resID);
    }

    /**
     * 更新资源
     * 
     * @param resTestpaper 资源对象
     * @return 是否成功
     */
    @Override
    public boolean updateTestpaperInfo(ResTestpaper resTestpaper) {
        int count = 0;
        try {
            count = super.update(resTestpaper);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 创建试卷
     * 
     * @param resTestpaper 试卷对象
     * @return 试卷ID
     * @throws Exception 异常
     */
    @Override
    public Integer saveTestpaperInfo(ResTestpaper resTestpaper) throws Exception {
        return super.saveAndReturnId(resTestpaper);
    }

    /**
     * 根据学段编码、学科编码、基础题型编码获取题目学科题型信息
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param questionTypeCode 基础题型编码
     * @return 题目学科题型信息
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectQuestionTypeSubject(String sectionCode, String subjectCode,
                    String questionTypeCode) throws Exception {
        return null;
    }

}