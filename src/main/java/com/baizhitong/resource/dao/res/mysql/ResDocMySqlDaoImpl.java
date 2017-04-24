package com.baizhitong.resource.dao.res.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.res.ResDocDao;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.utils.StringUtils;

/**
 * 资源_文档数据MySql接口实现
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
@Service("resDocMySqlDao")
public class ResDocMySqlDaoImpl extends BaseMySqlDao<ResDoc, Integer> implements ResDocDao {
    /**
     * 获取文档分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param kpCode 知识点编码
     * @param docName 文档名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 文档分页集合
     * @throws Exception 异常
     */
    @Override
    public Page<Map<String, Object>> selectDocPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String kpCode, String docName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rd.id, rd.resName, rd.suffix, rd.coverPath, rd.flagDynamicPPT, rd.browseCount, rd.downloadCount, rd.referCount, ");
        sql.append(" rd.favoriteCount, rd.goodCount, rd.badCount, rd.commentCount, rd.resDesc, rd.uploadTime, scs.name AS subjectName, scg.name AS gradeName, ");
        sql.append(" sctv.name AS textbookVerName ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rd.resTypeL1 AND rrk.resId = rd.id ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rrk.kpCode ");
        sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rd.resTypeL1 AND rrt2.resId = rd.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode ");
        sql.append(" WHERE rd.flagDelete = 0 AND rd.flagAllowBrowse = 1 AND rd.checkStatus = 20 AND rd.resStatus = 20 ");
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
            sql.append(" AND rd.resTypeL2 = :resTypeL2 ");
        }
        // 判断文档名称是否为空
        if (!StringUtils.isEmpty(docName)) {
            sql.append(" AND rd.docName LIKE '%" + docName + "%' ");
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
            sql.append(" ORDER BY rd.browseCount DESC, rd.id ");
        } else if (!StringUtils.isEmpty(sort) && "20".equals(sort)) {
            sql.append(" ORDER BY rd.uploadTime DESC, rd.id ");
        } else {
            sql.append(" ORDER BY rd.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", versionCode);
        map.put("tbcCode", tbcCode);
        map.put("kpCode", kpCode);
        map.put("docName", docName);
        map.put("resTypeL2", resTypeL2);
        map.put("sort", sort);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, pageNo, pageSize);
    }

    /**
     * 获取文档信息
     * 
     * @param docId 文档ID
     * @return 信息
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectDocInfo(Integer docId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rd.id, rd.resName, rd.suffix, rd.pages, rd.coverPath, rd.convertedPath, rd.flagDynamicPPT, rd.sourceName, rd.creator, ");
        sql.append(" rd.createTime, rd.browseCount, rd.downloadCount, rd.referCount, rd.favoriteCount, rd.goodCount, rd.badCount, rd.commentCount, ");
        sql.append(" rd.resDesc, rd.shareLevel, rd.downloadPoints, ");
        sql.append(" scs.code AS subjectCode, scs.name AS subjectName, scg.code AS gradeCode, scg.name AS gradeName, ");
        sql.append(" sctv.code AS textbookVerCode, sctv.name AS textbookVerName, skpt.code AS knowledgePointCode, skpt.name AS knowledgePointName, ");
        sql.append(" stc.code AS textbookChapterCode, stc.name AS textbookChapterName ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rd.resTypeL1 AND rrk.resId = rd.id ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rrk.kpCode ");
        sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rd.resTypeL1 AND rrt2.resId = rd.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode ");
        sql.append(" WHERE rd.id = :docId AND rd.flagDelete = 0 AND rd.flagAllowBrowse = 1 AND rd.checkStatus = 20 AND rd.resStatus = 20 ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("docId", docId);

        // 执行SQL
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 获取相关文档信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param knowledgePointCode 知识点编码
     * @param textbookChapterCode 教材章节编码
     * @return 相关文档信息
     * @throws Exception 异常
     */
    @Override
    public List<Map<String, Object>> selectRelevantDocInfo(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String knowledgePointCode, String textbookChapterCode) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT DISTINCT TOP 5 * FROM ( ");
        sql.append(" SELECT TOP 5 rd.id, rd.resName, rd.browseCount, rd.uploadTime ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rd.resTypeL1 AND rrt2.resId = rd.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode ");
        sql.append(" WHERE scs.code = :subjectCode AND scg.code = :gradeCode AND sctv.code = :textbookVerCode AND stc.code = :textbookChapterCode ");
        sql.append(" ORDER BY rd.browseCount DESC, rd.uploadTime DESC ");
        sql.append(" UNION ALL ");
        sql.append(" SELECT TOP 5 rd.id, rd.resName, rd.browseCount, rd.uploadTime ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rd.resTypeL1 AND rrk.resId = rd.id ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rrk.kpCode ");
        sql.append(" WHERE scs.code = :subjectCode AND scg.code = :gradeCode AND sctv.code = :textbookVerCode AND skpt.code = :knowledgePointCode ");
        sql.append(" ORDER BY rd.browseCount DESC, rd.uploadTime DESC ");
        sql.append(" ) rd_relevant ");
        sql.append(" ORDER BY rd_relevant.browseCount DESC, rd_relevant.uploadTime DESC ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("textbookVerCode", textbookVerCode);
        map.put("knowledgePointCode", knowledgePointCode);
        map.put("textbookChapterCode", textbookChapterCode);

        // SQL执行
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 根据resID获取资源
     * 
     * @param resID 资源id
     * @return 返回资源对象
     */
    @Override
    public ResDoc selectResDoc(Integer resID) {
        return super.get(resID);

    }

    /**
     * 更新资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    @Override
    public boolean updateDocInfo(ResDoc resDoc) {
        int count = 0;
        try {
            count = super.update(resDoc);
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
    public boolean saveDocInfo(ResDoc resDoc) {
        boolean success = false;
        try {
            success = super.saveOne(resDoc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 获取首页文档资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param docName 文档名称
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页文档资源集合信息
     * @exception 异常
     */
    @Override
    public List<Map<String, Object>> selectDocResList(String sectionCode, String subjectCode, String docName,
                    Boolean isHotSort, Boolean isNewSort) throws Exception {
        return null;
    }

    @Override
    public Page getAllDocInfo(Map param, Integer pageNo, Integer pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 根据crcCode获取文档
     * 
     * @param crcCode 文件唯一码
     * @return
     */
    @Override
    public ResDoc selectResDocByCrcCode(String crcCode, String creator) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("resCode", crcCode);
        return super.findUnique(queryRule);
    }

    @Override
    public Integer saveDocAndReturnId(ResDoc doc) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map getResDocById(Integer id) throws Exception {
        return null;
    }

    @Override
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception {
        // TODO Auto-generated method stub
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
    public int updateSameCodeDoc(ResDoc doc) {
        return 0;
    }

    @Override
    public Page<List<Map<String, Object>>> getAllRes(String sectionCode, String subjectCode, String gradeCode,
                    Integer shareCheckStatus, String orgName, String userName, Integer resTypeL1,
                    String uploadStartTime, String uploadEndTime, Integer shareCheckLevel, String resName,
                    String orgCode, Integer pageNo, Integer rows) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> getAllCanRecommendInfo(Map<String, Object> param) throws Exception {
        return null;
    }

    @Override
    public Page<Map<String, Object>> getUnUsualDocInfo(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public int recoveryDocList(String ids) {
        return 0;
    }

    @Override
    public int physicalDeleteDoc(String ids) {
        return 0;
    }

    @Override
    public int deleteDoc(String ids, String modifier, String modifierIP) {
        return 0;
    }

    @Override
    public ResDoc selectResDoc(String resCode) {
        return null;
    }

    @Override
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip) {
        return 0;
    }

}