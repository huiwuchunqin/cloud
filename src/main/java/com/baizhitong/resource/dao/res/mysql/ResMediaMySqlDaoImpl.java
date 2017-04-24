package com.baizhitong.resource.dao.res.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.res.ResMediaDao;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.utils.StringUtils;

/**
 * 资源_音视频媒体数据MySql接口实现
 * 
 * @author creator cuidongcheng 2015/12/03
 * @author updater
 */
@Service("resMediaMySqlDao")
public class ResMediaMySqlDaoImpl extends BaseMySqlDao<ResMedia, Integer> implements ResMediaDao {

    /**
     * 获取视频分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param kpCode 知识点编码
     * @param mediaName 视频名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 视频分页集合
     */
    @Override
    public Page<Map<String, Object>> selectMediaPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String kpCode, String mediaName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rm.id, rm.resName, rm.mediaDuration, rm.thumbnailPath, rm.trialTimeRate, rm.browseCount, rm.downloadCount, rm.goodCount, ");
        sql.append(" rm.commentCount, rm.uploadTime, scs.name AS subjectName, scg.name AS gradeName, sctv.name AS textbookVerName ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rm.resTypeL1 AND rrt.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rm.resTypeL1 AND rrk.resId = rm.id ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rrk.kpCode ");
        sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rm.resTypeL1 AND rrt2.resId = rm.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode ");
        sql.append(" WHERE rm.flagDelete = 0 AND rm.flagAllowBrowse = 1 AND rm.checkStatus = 20 AND rm.resStatus = 20 ");
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
            sql.append(" AND rm.resTypeL2 = :resTypeL2 ");
        }
        // 判断视频名称是否为空
        if (!StringUtils.isEmpty(mediaName)) {
            sql.append(" AND rm.mediaName LIKE '%" + mediaName + "%' ");
        }
        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(tbcCode)) {
            sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            sql.append(" AND rrk.kpCode LIKE '" + kpCode + "%' ");
        }

        // 排序方式(null:默认,10:最热,20:最新)
        if (!StringUtils.isEmpty(sort) && "10".equals(sort)) {
            sql.append(" ORDER BY rm.browseCount DESC, rm.id ");
        } else if (!StringUtils.isEmpty(sort) && "20".equals(sort)) {
            sql.append(" ORDER BY rm.uploadTime DESC, rm.id ");
        } else {
            sql.append(" ORDER BY rm.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", versionCode);
        map.put("tbcCode", tbcCode);
        map.put("kpCode", kpCode);
        map.put("mediaName", mediaName);
        map.put("resTypeL2", resTypeL2);
        map.put("sort", sort);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, pageNo, pageSize);
    }

    /**
     * 获取视频信息
     * 
     * @param mediaId 视频ID
     * @return 信息
     */
    @Override
    public Map<String, Object> selectMediaInfo(Integer mediaId) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rm.id, rm.resName, rm.mediaDuration, rm.coverPath, rm.lowPath, rm.highPath, rm.p2pPath, rm.trialTimeRate, rm.sourceName, ");
        sql.append(" rm.creator, rm.createTime, rm.browseCount, rm.downloadCount, rm.goodCount, rm.commentCount, rm.shareLevel, rm.resDesc, rm.flagUnionRes, ");
        sql.append(" scs.code AS subjectCode, scs.name AS subjectName, scg.code AS gradeCode, scg.name AS gradeName, ");
        sql.append(" sctv.code AS textbookVerCode, sctv.name AS textbookVerName, skpt.code AS knowledgePointCode, skpt.name AS knowledgePointName, ");
        sql.append(" stc.code AS textbookChapterCode, stc.name AS textbookChapterName ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rm.resTypeL1 AND rrt.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rm.resTypeL1 AND rrk.resId = rm.id ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rrk.kpCode ");
        sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rm.resTypeL1 AND rrt2.resId = rm.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode ");
        sql.append(" WHERE rm.id = :mediaId AND rm.flagDelete = 0 AND rm.flagAllowBrowse = 1 AND rm.checkStatus = 20 AND rm.resStatus = 20 ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mediaId", mediaId);

        // 执行SQL
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 获取相关视频信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param knowledgePointCode 知识点编码
     * @param textbookChapterCode 教材章节编码
     * @return 相关视频信息
     */
    @Override
    public List<Map<String, Object>> selectRelevantMediaInfo(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String knowledgePointCode, String textbookChapterCode) {
        return null;
    }

    /**
     * 更新resmedia
     * 
     * @param resMedia 资源对象
     * @return 是否成功
     */
    @Override
    public boolean updateMediaInfo(ResMedia resMedia) {
        int count = 0;
        try {
            count = super.update(resMedia);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 根据resID获取resMedia
     * 
     * @param resID 资源id
     * @return resMedia对象
     */
    @Override
    public ResMedia selectResMedia(Integer resID) {

        return super.get(resID);
    }

    /**
     * 保存资源信息
     * 
     * @param resMedia 资源对象
     * @return 是否成功
     */
    @Override
    public boolean saveMediaInfo(ResMedia resMedia) {
        boolean success = false;
        try {
            success = super.saveOne(resMedia);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public Integer saveMediaAndReturnId(ResMedia resMedia) {
        return null;
    }

    /**
     * 获取首页视频资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param mediaName 视频名称
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页视频资源集合信息
     * @exception 异常
     */
    @Override
    public List<Map<String, Object>> selectMediaResList(String sectionCode, String subjectCode, String mediaName,
                    Boolean isHotSort, Boolean isNewSort) throws Exception {
        return null;
    }

    /**
     * 根据crcCode获取视频
     * 
     * @param crcCode 文件唯一码
     * @return
     */
    @Override
    public ResMedia selectResMediaByCrcCode(String crcCode, String creator) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("resCode", crcCode);
        return super.findUnique(queryRule);
    }

    @Override
    public Page<Map<String, Object>> getAllMediaInfo(Map<String, Object> param, Integer pageSize, Integer pageNo) {
        return null;
    }

    @Override
    public Map<String, Object> getResMediaById(Integer id) throws Exception {
        return null;
    }

    @Override
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception {
        return null;
    }

    /**
     * 获取用户资源分页集合
     * 
     * @param userCode 用户编码
     * @param resStatus 资源状态
     * @param checkStatus 审核状态
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 用户资源分页集合
     * @exception 异常
     */
    public Page<Map<String, Object>> selectSpaceUserResPage(String userCode, Integer resStatus, Integer checkStatus,
                    String sort, Integer pageNo, Integer pageSize) throws Exception {
        return null;
    }

    @Override
    public int examine(Integer resId, String status) throws Exception {
        return 0;
    }

    @Override
    public int updateSameResCodeMedia(ResMedia media) {
        return 0;
    }

    @Override
    public Page<Map<String, Object>> getUnUsualMediaInfo(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> getGarbageMediaInfo(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public int physicalDeleteMedia(String ids) {
        return 0;
    }

    @Override
    public int recoveryMediaList(String ids) {
        return 0;
    }

    @Override
    public Page<Map<String, Object>> selectPageByResSetting(Map<String, Object> param, Integer rows, Integer page) {
        return null;
    }

    @Override
    public ResMedia selectResMedia(String resCode) {
        return null;
    }

    @Override
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip) {
        return 0;
    }

    @Override
    public int deleteMedia(String ids, String modifier, String modifierIP) {
        return 0;
    }

}