package com.baizhitong.resource.dao.res.mysql;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.res.ResExerciseDao;
import com.baizhitong.resource.model.res.ResExercise;
import com.baizhitong.utils.StringUtils;

/**
 * 资源_练习数据MySql接口实现
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
@Service("resExerciseMySqlDao")
public class ResExerciseMySqlDaoImpl extends BaseMySqlDao<ResExercise, Integer> implements ResExerciseDao {

    /**
     * 获取练习分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param exerciseName 练习名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 练习分页集合
     * @throws Exception 异常
     */
    @Override
    public Page<Map<String, Object>> selectExercisePage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String exerciseName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT re.id, re.resName, re.browseCount, re.downloadCount, re.referCount, re.favoriteCount, re.goodCount, re.badCount, ");
        sql.append(" re.commentCount, re.resDesc, re.canRandomInType, re.canRandomInQuestion, re.createTime, scs.name AS subjectName, scg.name AS gradeName, ");
        sql.append(" sctv.name AS textbookVerName ");
        sql.append(" FROM res_exercise re ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = re.resTypeL1 AND rrs.resId = re.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = re.resTypeL1 AND rrg.resId = re.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = re.resTypeL1 AND rrt.resId = re.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = re.resTypeL1 AND rrt2.resId = re.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode ");
        sql.append(" WHERE re.flagDelete = 0 AND re.flagAllowBrowse = 1 AND re.checkStatus = 20 AND re.flagBuildFinished = 1 ");
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode)) {
            sql.append(" AND rrg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }
        // 判断版本编码是否为空
        if (!StringUtils.isEmpty(versionCode)) {
            sql.append(" AND rrt.tbvCode = :versionCode ");
        }
        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND re.resTypeL2 = :resTypeL2 ");
        }
        // 判断练习名称是否为空
        if (!StringUtils.isEmpty(exerciseName)) {
            sql.append(" AND re.docName LIKE '%" + exerciseName + "%' ");
        }
        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(tbcCode)) {
            sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
        }

        // 排序方式(null:默认,10:最热,20:最新)
        if (!StringUtils.isEmpty(sort) && "10".equals(sort)) {
            sql.append(" ORDER BY re.browseCount DESC, re.id ");
        } else if (!StringUtils.isEmpty(sort) && "20".equals(sort)) {
            sql.append(" ORDER BY re.createTime DESC, re.id ");
        } else {
            sql.append(" ORDER BY re.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", versionCode);
        map.put("tbcCode", tbcCode);
        map.put("exerciseName", exerciseName);
        map.put("resTypeL2", resTypeL2);
        map.put("sort", sort);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, pageNo, pageSize);
    }

    /**
     * 获取练习信息
     * 
     * @param exerciseId 练习ID
     * @return 信息
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectExerciseInfo(Integer exerciseId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT re.id, re.resName, re.browseCount, re.downloadCount, re.referCount, re.favoriteCount, re.goodCount, re.badCount, ");
        sql.append(" re.commentCount, re.resDesc, re.canRandomInType, re.canRandomInQuestion, re.createTime, scs.name AS subjectName, scg.name AS gradeName, ");
        sql.append(" sctv.name AS textbookVerName ");
        sql.append(" FROM res_exercise re ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = re.resTypeL1 AND rrs.resId = re.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = re.resTypeL1 AND rrg.resId = re.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = re.resTypeL1 AND rrt.resId = re.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" WHERE re.id = :exerciseId AND re.flagDelete = 0 AND re.flagAllowBrowse = 1 AND re.checkStatus = 20 AND re.flagBuildFinished = 1 ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exerciseId", exerciseId);

        // 执行SQL
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 根据resID获取资源
     * 
     * @param resID 资源id
     * @return 返回资源对象
     */
    @Override
    public ResExercise selectResExercise(Integer resID) {
        return super.get(resID);
    }

    /**
     * 更新资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    @Override
    public boolean updateExerciseInfo(ResExercise resExercise) {
        int count = 0;
        try {
            count = super.update(resExercise);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 保存资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    @Override
    public boolean saveExerciseInfo(ResExercise resExercise) {
        boolean success = false;
        try {
            success = super.saveOne(resExercise);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return success;
    }

}